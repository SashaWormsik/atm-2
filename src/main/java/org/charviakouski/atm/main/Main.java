package org.charviakouski.atm.main;

import org.charviakouski.atm.gui.AtmGui;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        AtmGui atmGui = new AtmGui();
        while (true) {
            try {
                atmGui.clearAtmCache();
                if (atmGui.enteringCardByNumber()) {
                    if (atmGui.enteringPinCode()) {
                        atmGui.startWorkWithCard();
                    }
                }
            } catch (IOException | ParseException e) {
                System.out.println("Внутренняя ошибка банкомата! ".concat(e.getMessage()));
            }
        }
    }
}
