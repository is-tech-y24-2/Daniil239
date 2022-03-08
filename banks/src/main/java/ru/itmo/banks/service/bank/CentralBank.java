package ru.itmo.banks.service.bank;

import ru.itmo.banks.service.Transaction;
import ru.itmo.banks.tool.bankException.SpecificExceptions.*;

import java.util.ArrayList;

public class CentralBank {

    private final ArrayList<Transaction> transactions;
    private final ArrayList<Bank> banks = new ArrayList<Bank>();
    public String name;
    public int dayPerMonth;

    public CentralBank(String name) {
        this.name = name;
        transactions = new ArrayList<Transaction>();
        dayPerMonth = 0;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Bank> getBanks() {
        return banks;
    }

    public int getDayPerMonth() {
        return dayPerMonth;
    }

    public Bank createBank(String name, int money) throws AlreadyExistException, NotEnoughInformationException {
        Bank newBank = new BankBuilder().setName(name).setMoney(money).getResult();
        if (banks.contains(newBank)) throw new AlreadyExistException(String.format("Bank %s already exist", name));
        banks.add(newBank);
        return newBank;
    }

    public void sendMoneyBetweenBanks(Bank sender, Bank recipient, float sum) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var newTransaction = new Transaction(sender.getBankAccount(), recipient.getBankAccount(), sum);
        newTransaction.execute();
        transactions.add(newTransaction);
    }

    public void sleepDays(int days) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {

        for (int i = 1; i <= days; i++) {
            dayPerMonth++;
            for (Bank bank : banks) bank.sleepDay();

            if (dayPerMonth % 30 == 0) {
                dayPerMonth = 0;
                for (Bank bank : banks) bank.payments();
            }
        }
    }

    public void sleepMonths(int months) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        sleepDays(months * 30);
    }

    public void sleepYears(int years) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        sleepDays(years * 365);
    }
}
