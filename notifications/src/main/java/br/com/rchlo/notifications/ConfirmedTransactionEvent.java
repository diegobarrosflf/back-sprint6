package br.com.rchlo.notifications;

import java.math.BigDecimal;

public class ConfirmedTransactionEvent {

    private String description;
    private BigDecimal amount;
    private String fullNameCustomer;
    private String emailCustomer;

    public ConfirmedTransactionEvent() {

    }

    public ConfirmedTransactionEvent(String description, BigDecimal amount, String fullNameCustomer, String emailCustomer) {
        this.description = description;
        this.amount = amount;
        this.fullNameCustomer = fullNameCustomer;
        this.emailCustomer = emailCustomer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFullNameCustomer() {
        return fullNameCustomer;
    }

    public void setFullNameCustomer(String fullNameCustomer) {
        this.fullNameCustomer = fullNameCustomer;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }
}
