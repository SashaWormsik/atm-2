package org.charviakouski.atm.service;

import org.charviakouski.atm.dao.BaseDao;
import org.charviakouski.atm.dao.implementation.AtmDao;
import org.charviakouski.atm.dao.implementation.CardDao;
import org.charviakouski.atm.entity.Atm;
import org.charviakouski.atm.entity.Card;
import org.charviakouski.atm.parser.AtmParser;
import org.charviakouski.atm.parser.CardParser;
import org.charviakouski.atm.util.StatusOperation;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Service {
    private static final BigDecimal MAXIMUM_AMOUNT_OF_FUNDS_REPLENISHMENT = new BigDecimal("1000000");
    private static final int LOCK_PERIOD = 24 * 60 * 60 * 1000;
    private static final int ONE = 1;
    private static final int ZERO = 0;
    private static final int INITIAL_NUMBER_OF_PIN_CODE_ATTEMPTS = 3;

    private final BaseDao<Card> cardDao = new CardDao();
    private final BaseDao<Atm> atmDao = new AtmDao();


    public Card findCardByNumber(String cardNumber) throws ParseException, IOException {
        List<String> cardList = cardDao.read();
        for (String tempCardData : cardList) {
            if (tempCardData.contains(cardNumber)) {
                return CardParser.cardParser(tempCardData);
            }
        }
        return null;
    }

    public boolean putMoneyInCard(BigDecimal replenishmentAmount, Card card) throws IOException {
        boolean statusOperation = false;
        Atm atm = getAtm();
        if (replenishmentAmount.compareTo(MAXIMUM_AMOUNT_OF_FUNDS_REPLENISHMENT) < ZERO) {
            card.setBalance(card.getBalance().add(replenishmentAmount));
            atm.setCash(atm.getCash().add(replenishmentAmount));
            atmDao.save(atm);
            cardDao.save(card);
            statusOperation = true;
        }
        return statusOperation;
    }

    public StatusOperation takeMoneyFromCard(BigDecimal withdrawalAmount, Card card) throws IOException {
        StatusOperation statusOperation;
        Atm atm = getAtm();
        if (card.getBalance().compareTo(withdrawalAmount) < ZERO) {
            statusOperation = StatusOperation.INSUFFICIENT_FUNDS_ON_THE_CARD;
        } else if (atm.getCash().compareTo(withdrawalAmount) < ZERO) {
            statusOperation = StatusOperation.INSUFFICIENT_FUNDS_AT_THE_ATM;
        } else {
            statusOperation = StatusOperation.ENOUGH_FUNDS;
            card.setBalance(card.getBalance().subtract(withdrawalAmount));
            atm.setCash(atm.getCash().subtract(withdrawalAmount));
            cardDao.save(card);
            atmDao.save(atm);
        }
        return statusOperation;
    }

    public boolean blockingDateEnded(Card card) {
        if (card.getLockDate() != null) {
            Date dateNow = new Date();
            long timeDifference = dateNow.getTime() - card.getLockDate().getTime();
            return timeDifference > (LOCK_PERIOD);
        }
        return false;
    }

    public void removeBlocking(Card card) throws IOException {
        card.setBlocked(false);
        card.setAttemptCounter(INITIAL_NUMBER_OF_PIN_CODE_ATTEMPTS);
        card.setLockDate(null);
        cardDao.save(card);
    }

    public void blockCard(Card card) throws IOException {
        card.setBlocked(true);
        card.setLockDate(new Date());
        cardDao.save(card);
    }

    public boolean isCorrectPinCode(Card card, Integer pinCode) throws IOException {
        boolean result;
        if (card.getCodePIN().equals(pinCode)) {
            card.setAttemptCounter(INITIAL_NUMBER_OF_PIN_CODE_ATTEMPTS);
            cardDao.save(card);
            result = true;
        } else {
            card.setAttemptCounter(card.getAttemptCounter() - ONE);
            cardDao.save(card);
            result = false;
        }
        return result;
    }

    private Atm getAtm() throws IOException {
        List<String> atmList = atmDao.read();
        return AtmParser.atmParser(atmList.get(0));
    }

}