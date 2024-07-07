package org.charviakouski.atm.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Atm implements Serializable {
    private BigDecimal cash;

    public Atm() {
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    @Override
    public String toString() {
        StringBuilder AtmString = new StringBuilder();
        AtmString.append("cash = ")
                .append((cash != null) ? cash : "null");
        return AtmString.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode(); // fixme
        result = prime * result + (cash != null ? cash.hashCode() : 0);
        return result;
    }

    @Override
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
        Atm ComparableAtm = (Atm) ComparableObject;
        if (cash != null ? cash.equals(ComparableAtm.cash) : ComparableAtm.cash != null) {
            return false;
        }
        return true;
    }

}
