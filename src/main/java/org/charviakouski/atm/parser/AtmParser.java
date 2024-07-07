package org.charviakouski.atm.parser;

import org.charviakouski.atm.entity.Atm;

import java.math.BigDecimal;

public class AtmParser {

    public static Atm atmParser(String atmString) {
        Atm atm = new Atm();
        String[] atmData = atmString.split("\\s");
        atm.setCash(new BigDecimal(atmData[2]).setScale(2));
        return atm;
    }

}
