package org.charviakouski.atm.gui;

import org.charviakouski.atm.entity.Card;
import org.charviakouski.atm.service.Service;
import org.charviakouski.atm.util.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;

public class AtmGui {


    private final Service service = new Service();
    private Card card;

    public void clearAtmCache() {
        this.card = null;
    }

    public boolean enteringCardByNumber() throws IOException, ParseException {
        System.out.println("Введите номер карты!");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String cardNumber = bufferedReader.readLine();
        if (!Validator.isCardNumberValid(cardNumber)) {
            System.out.println("Вы ввели некоректный формат номера карты! Повторите попытку");
            return false;
        }
        this.card = service.findCardByNumber(cardNumber);
        if (this.card == null) {
            System.out.println("Данной карты нет в базе данных!");
            return false;
        }
        return true;
    }

    public boolean enteringPinCode() throws IOException {
        boolean resultOperation = false;
        if (checkCardBlocking()) {
            resultOperation = checkPinCode();
        } else {
            System.out.println("Карточка заблокирована");
        }
        return resultOperation;
    }

    private boolean checkPinCode() throws IOException {
        boolean resultOperation = false;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (this.card.getAttemptCounter() > 0) {
            System.out.println("Введите ПИН код:");
            String pinCodeForVerification = bufferedReader.readLine();
            if (Validator.isPinCodeValid(pinCodeForVerification)) {
                int pinCodeInt = Integer.parseInt(pinCodeForVerification);
                if (service.isCorrectPinCode(this.card, pinCodeInt)) {
                    resultOperation = true;
                    break;
                } else {
                    System.out.printf("Вы ввели неверный ПИН! Повторить попытку?\n"
                            + "У вас осталось %d попыток\n", this.card.getAttemptCounter());
                }
            } else {
                System.out.println("Вы ввели некоректный формат ПИН! Повторить попытку?\n");
            }
            if (this.card.getAttemptCounter() > 0 && !continueOrNot()) {
                break;
            }
        }
        if (this.card.getAttemptCounter() == 0) {
            service.blockCard(this.card);
            System.out.println("Карточка заблокирована");
            resultOperation = false;
        }
        return resultOperation;
    }

    public void startWorkWithCard() throws IOException {
        boolean continuationStatus = true;
        while (continuationStatus) {
            menu();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput = bufferedReader.readLine();
            switch (userInput) {
                case "1":
                    System.out.printf("Баланс вашего счёта равен: %.2f\n", this.card.getBalance());
                    continuationStatus = continueOrNot();
                    break;
                case "2":
                    System.out.print("Введите сумму пополнения: ");
                    String inputReplenishmentAmount = bufferedReader.readLine();
                    if (Validator.isMoneyTransactionValid(inputReplenishmentAmount)) {
                        BigDecimal replenishmentAmount = new BigDecimal(inputReplenishmentAmount);
                        boolean replenishmentCompleted = service.putMoneyInCard(replenishmentAmount, this.card);
                        System.out.printf("Операция %sБаланс Вашего счёта: %.2f\n",
                                replenishmentCompleted ? "выполнена!\n" : "не выполнена!\nСумма пополнения не должна превышать 1_000_000\n",
                                this.card.getBalance());
                    } else {
                        System.out.println("Неправильный формат");
                    }
                    continuationStatus = continueOrNot();
                    break;
                case "3":
                    System.out.print("Введите сумму вывода: ");
                    String inputWithdrawalAmount = bufferedReader.readLine();
                    if (Validator.isMoneyTransactionValid(inputWithdrawalAmount)) {
                        BigDecimal withdrawalAmount = new BigDecimal(inputWithdrawalAmount);
                        withdrawCash(withdrawalAmount);
                    } else {
                        System.out.println("Неправильный формат");
                    }
                    continuationStatus = continueOrNot();
                    break;
                case "0":
                    System.out.println("До новых встреч!");
                    continuationStatus = false;
                    break;
                default:
                    System.out.println("Wrong input");
                    continuationStatus = continueOrNot();
            }
        }
    }

    private boolean checkCardBlocking() throws IOException {
        boolean resultOperation = true;
        if (service.blockingDateEnded(this.card)) {
            this.service.removeBlocking(this.card);
            resultOperation = true;
        }
        if (this.card.isBlocked()) {
            resultOperation = false;
        }
        return resultOperation;
    }

    private void withdrawCash(BigDecimal withdrawalAmount) throws IOException {
        switch (service.takeMoneyFromCard(withdrawalAmount, this.card)) {
            case INSUFFICIENT_FUNDS_ON_THE_CARD:
                System.out.println("Операция не выполнена! Ваш баланс менее запрашиваемой суммы\n");
                break;
            case INSUFFICIENT_FUNDS_AT_THE_ATM:
                System.out.println("Операция не выполнена! Недостаточно средств в банкомате\n");
                break;
            case ENOUGH_FUNDS:
                System.out.printf("Операция выполнена.\nБаланс Вашего счёта: %.2f\n", this.card.getBalance());
                break;
        }
    }

    private void menu() {
        System.out.print("\tНажмите 1 узнать балланс счёта.\n" +
                "\tНажмите 2 пополнить Ваш счёт.\n" +
                "\tНажмите 3 снять наличные.\n" +
                "\tНажмите 0 для завершения работы.\n");
    }

    private boolean continueOrNot() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\tПродолжить?\n1 - ДА  \"другая кнопка\"- НЕТ");
        String userInput = bufferedReader.readLine();
        if (userInput.equals("1")) {
            return true;
        } else {
            System.out.println("До новых встреч!");
            return false;
        }
    }
}

