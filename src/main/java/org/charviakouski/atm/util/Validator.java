package org.charviakouski.atm.util;

import java.util.regex.Pattern;

public final class Validator {

    public static final String REG_CARD_NUMBER = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
    public static final String REG_PIN_CODE = "\\d{4}";
    public static final String REG_MONEY_TRANSACTIONS = "\\d*";

    private Validator() {
    }

    public static boolean isCardNumberValid(String cardNumber) {
        Pattern pattern = Pattern.compile(REG_CARD_NUMBER);
        return pattern.matcher(cardNumber).matches();
    }

    public static boolean isPinCodeValid(String pinCode) {
        Pattern pattern = Pattern.compile(REG_PIN_CODE);
        return pattern.matcher(pinCode).matches();
    }

    public static boolean isMoneyTransactionValid(String money) {
        Pattern pattern = Pattern.compile(REG_MONEY_TRANSACTIONS);
        return pattern.matcher(money).matches();
    }
}
