package br.com.rchlo.notifications;

import br.com.rchlo.notifications.infra.email.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmedTransactionEventProcessor {

    private EmailSender emailSender;
    private NotificationsCreator notificationsCreator;

    @StreamListener(StreamConfig.ConfirmedTransactionSink.CONFIRMED_TRANSACTION_TOPIC)
    void confirmedTransaction(ConfirmedTransactionEvent confirmedTransactionEvent) {

        String notificationText = notificationsCreator.createFor(confirmedTransactionEvent);
        String customerEmail = confirmedTransactionEvent.getEmailCustomer();
        String subject = "Nova despesa: " + confirmedTransactionEvent.getDescription();
        emailSender.send(customerEmail, subject, notificationText);
    }


}
