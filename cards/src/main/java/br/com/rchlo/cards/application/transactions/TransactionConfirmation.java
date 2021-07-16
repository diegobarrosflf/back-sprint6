package br.com.rchlo.cards.application.transactions;

import br.com.rchlo.cards.configs.StreamConfig;
import br.com.rchlo.cards.domain.cards.Card;
import br.com.rchlo.cards.domain.transactions.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransactionConfirmation {

    private final TransactionRepository transactionRepository;
    private final StreamConfig.ConfirmedTransactionSource confirmedTransactionSource;

    public TransactionConfirmation(TransactionRepository transactionRepository, StreamConfig.ConfirmedTransactionSource confirmedTransactionSource) {
        this.transactionRepository = transactionRepository;
        this.confirmedTransactionSource = confirmedTransactionSource;
    }

    @Transactional
    public ResponseEntity<Void> confirm(String uuid) {
        Transaction transaction = transactionRepository.findByUuid(uuid).orElseThrow(TransactionNotFoundException::new);
        transaction.confirm();

        Card card = transaction.getCard();
        card.deductFromLimit(transaction.getAmount());

        //lógica para enviar mensagem ao kafka
        final var customer = transaction.getCard().getCustomer();
        final var confirmedTransactionEvent = new ConfirmedTransactionEvent(
                transaction.getDescription(),
                transaction.getAmount(),
                customer.getFullName(),
                customer.getEmail());

        Message<ConfirmedTransactionEvent> message = MessageBuilder
                .withPayload(confirmedTransactionEvent)
                .build();
        confirmedTransactionSource.confirmedTransactionsTopic().send(message);

        return ResponseEntity.ok().build();
    }

}
