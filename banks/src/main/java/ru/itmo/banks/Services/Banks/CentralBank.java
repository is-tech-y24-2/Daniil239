package ru.itmo.banks.Services.Banks;

import ru.itmo.banks.Services.Transaction;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.*;

import java.util.*;

public class CentralBank {

    private ArrayList<Transaction> transactions;
    private ArrayList<Bank> banks = new ArrayList<Bank>();
    public String name;
    public int dayPerMonth;

    public CentralBank(String name)
    {
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

    public Bank CreateBank(String name, int money) throws AlreadyExistException, NotEnoughInformationException {
        Bank newBank = new BankBuilder().SetName(name).SetMoney(money).GetResult();
        if (banks.contains(newBank)) throw new AlreadyExistException(String.format("Bank %s already exist", name));
        banks.add(newBank);
        return newBank;
    }

    public void SendMoneyBetweenBanks(Bank sender, Bank recipient, float sum) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var newTransaction = new Transaction(sender.getBankAccount(), recipient.getBankAccount(), sum);
        newTransaction.Execute();
        transactions.add(newTransaction);
    }

    public void SleepDays(int days) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {

        for (int i = 1; i <= days; i++)
        {
            dayPerMonth++;
            for (Bank bank : banks) bank.SleepDay();

            if (dayPerMonth % 30 == 0)
            {
                dayPerMonth = 0;
                for (Bank bank : banks) bank.Payments();
            }
        }
    }

    public void SleepMonths(int months) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        SleepDays(months * 30);
    }

    public void SleepYears(int years) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        SleepDays(years * 365);
    }
}
