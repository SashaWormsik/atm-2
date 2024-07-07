package org.charviakouski.atm.parser;

import org.charviakouski.atm.entity.Card;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CardParser {

    public static Card cardParser(String cardString) throws ParseException {
        Card card = new Card();
        String[] cardData = cardString.split("\\s");
        card.setCardNumber(cardData[0]);
        card.setCodePIN(Integer.parseInt(cardData[1].substring(4)));
        card.setBalance(new BigDecimal(cardData[2].substring(8)).setScale(2));
        card.setBlocked(Boolean.parseBoolean(cardData[3].substring(9)));
        card.setAttemptCounter(Integer.parseInt(cardData[4].substring(15)));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = cardData[5].substring(9);
        if (dateString.equals("null")) {
            card.setLockDate(null);
        } else {
            card.setLockDate(formatter.parse(cardData[5].substring(9)));
        }
        return card;
    }

}
