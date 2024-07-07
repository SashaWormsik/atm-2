package org.charviakouski.atm.dao.implementation;

import org.charviakouski.atm.entity.Card;
import org.charviakouski.atm.util.PathUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;

public class CardDao extends AbstractDao<Card> {

    private static final String CARDS_DATA_FILE = "./src/main/resources/data/cards.txt";

    @Override
    String getDataFile() {
        return CARDS_DATA_FILE;
    }

    @Override
    public void save(Card card) throws IOException {
        List<String> cardsList = read();
        ListIterator<String> cardsListIterator = cardsList.listIterator();
        while (cardsListIterator.hasNext()) {
            String tempCard = cardsListIterator.next();
            if (tempCard.contains(card.getCardNumber())) {
                cardsListIterator.set(card.toString());
            }
        }
        if (PathUtil.checkFilePath(CARDS_DATA_FILE)) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CARDS_DATA_FILE))) {
                for (String value : cardsList) {
                    writer.write(value + "\n");
                }
            } catch (IOException e) {
                throw new IOException("The problem with writing the file", e);
            }
        } else {
            throw new IOException("File not found");
        }
    }
}
