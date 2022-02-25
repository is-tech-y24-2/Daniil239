package ru.itmo.banks.Services;

import ru.itmo.banks.Services.Account.BankAccount;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotEnoughMoneyException;

public class Transaction {
    private static int counterForIdCreating = 100000;
    private float money;
    private BankAccount sender;
    private BankAccount recipient;
    private int id;
    private boolean activeCondition;

    public Transaction(BankAccount sender, BankAccount recipient, float money)
    {
        this.money = money;
        this.sender = sender;
        this.recipient = recipient;
        counterForIdCreating++;
        id = counterForIdCreating;
        activeCondition = false;
    }

    public BankAccount getRecipient() {
        return recipient;
    }

    public BankAccount getSender() {
        return sender;
    }

    public float getMoney() {
        return money;
    }

    public int getId() {
        return id;
    }

    public static int getCounterForIdCreating() {
        return counterForIdCreating;
    }

    public boolean isActiveCondition() {
        return activeCondition;
    }

    public void Execute() throws IllegalOperationException, NotEnoughMoneyException
    {
        if (activeCondition) throw new IllegalOperationException("Already executed");
        if (sender.getBalance() < money) throw new NotEnoughMoneyException("Not enough money for executing");
        sender.setBalance(sender.getBalance() - money);
        recipient.setBalance(recipient.getBalance() + money);
        activeCondition = true;
    }

    public void Cancel() throws IllegalOperationException
    {
        if (!activeCondition) throw new IllegalOperationException("Can't cancel transaction twice");
        recipient.setBalance(recipient.getBalance() - money);
        sender.setBalance(sender.getBalance() + money);
        activeCondition = false;
    }
}
