package org.charviakouski.atm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Card implements Serializable {

    private static final Integer INITIAL_NUMBER_OF_PIN_CODE_ATTEMPTS = 3;

    private String cardNumber;
    private Integer codePIN;
    private BigDecimal balance;
    private Boolean isBlocked;
    private Integer attemptCounter = INITIAL_NUMBER_OF_PIN_CODE_ATTEMPTS;
    private Date lockDate;

    public Card() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCodePIN() {
        return codePIN;
    }

    public void setCodePIN(Integer codePIN) {
        this.codePIN = codePIN;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Integer getAttemptCounter() {
        return attemptCounter;
    }

    public void setAttemptCounter(Integer attemptCounter) {
        this.attemptCounter = attemptCounter;
    }

    public Date getLockDate() {
        return lockDate;
    }

    public void setLockDate(Date lockDate) {
        this.lockDate = lockDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder CardString = new StringBuilder();
        CardString.append(cardNumber != null ? cardNumber : "null").append(" ")
                .append("pin:").append((codePIN != null) ? codePIN : "null").append(" ")
                .append("balance:").append((balance != null) ? balance : "null").append(" ")
                .append("blocking:").append((isBlocked != null) ? isBlocked : "null").append(" ")
                .append("attemptCounter:").append((attemptCounter != null) ? attemptCounter : "null").append(" ")
                .append("lockDate:").append((lockDate != null) ? dateFormat.format(lockDate) : "null");
        return CardString.toString();

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode(); // fixme
        result = prime * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = prime * result + (codePIN != null ? codePIN.hashCode() : 0);
        result = prime * result + (balance != null ? balance.hashCode() : 0);
        result = prime * result + (isBlocked != null ? isBlocked.hashCode() : 0);
        result = prime * result + (attemptCounter != null ? attemptCounter.hashCode() : 0);
        result = prime * result + (lockDate != null ? lockDate.hashCode() : 0);
        return result;
    }

    public boolean equals(Object ComparableObject) {
        if (this == ComparableObject) {
            return true;
        }
        if (ComparableObject == null || this.getClass() != ComparableObject.getClass()) {
            return false;
        }
        if (!super.equals(ComparableObject)) {
            return false;
        }
        Card ComparableCard = (Card) ComparableObject;
        if (cardNumber != null ? cardNumber.equals(ComparableCard.cardNumber) : ComparableCard.cardNumber != null) {
            return false;
        }
        if (codePIN != null ? codePIN.equals(ComparableCard.codePIN) : ComparableCard.codePIN != null) {
            return false;
        }
        if (balance != null ? balance.equals(ComparableCard.balance) : ComparableCard.balance != null) {
            return false;
        }
        if (isBlocked != null ? isBlocked.equals(ComparableCard.isBlocked) : ComparableCard.isBlocked != null) {
            return false;
        }
        if (attemptCounter != null ? attemptCounter.equals(ComparableCard.attemptCounter) : ComparableCard.attemptCounter != null) {
            return false;
        }
        if (lockDate != null ? lockDate.equals(ComparableCard.lockDate) : ComparableCard.lockDate != null) {
            return false;
        }
        return true;
    }
}
