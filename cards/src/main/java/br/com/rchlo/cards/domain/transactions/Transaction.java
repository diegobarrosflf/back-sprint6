package br.com.rchlo.cards.domain.transactions;

import br.com.rchlo.cards.application.transactions.InvalidTransactionStatusException;
import br.com.rchlo.cards.domain.cards.Card;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private BigDecimal amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;

    @ManyToOne
    private Card card;

    /** @deprecated */
    protected Transaction() {
    }

    public Transaction(BigDecimal amount, String description, Card card) {
        this.amount = amount;
        this.description = description;
        this.card = card;
        this.uuid = UUID.randomUUID().toString();
        this.status = Status.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public String getUuid() {
        return uuid;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Card getCard() {
        return card;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void cancel() {
        if (!Transaction.Status.CREATED.equals(this.status)) {
            throw new InvalidTransactionStatusException();
        }
        this.status = Transaction.Status.CANCELED;
    }

    public void confirm() {
        if (!Transaction.Status.CREATED.equals(this.status)) {
            throw new InvalidTransactionStatusException();
        }
        this.status = Status.CONFIRMED;
    }

    public enum Status {

        CREATED,
        CONFIRMED,
        CANCELED;

    }
}
