package ru.itmo.banks.Services;

import ru.itmo.banks.Services.Account.BankAccount;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.AlreadyExecutedException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotEnoughMoneyException;

public class Transaction {
    private static int counterForIdCreating = 100000;
    private final float money;
    private final BankAccount sender;
    private final BankAccount recipient;
    private final int id;
    private boolean activeCondition;

    public Transaction(BankAccount sender, BankAccount recipient, float money) {
        this.money = money;
        this.sender = sender;
        this.recipient = recipient;
        counterForIdCreating++;
        id = counterForIdCreating;
        activeCondition = false;
    }

    public static int getCounterForIdCreating() {
        return counterForIdCreating;
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

    public boolean isActiveCondition() {
        return activeCondition;
    }

    public void execute() throws IllegalOperationException, NotEnoughMoneyException, AlreadyExecutedException {
        if (activeCondition) throw new AlreadyExecutedException("Already executed");
        if (sender.getBalance() < money) throw new NotEnoughMoneyException("Not enough money for executing");
        sender.setBalance(sender.getBalance() - money);
        recipient.setBalance(recipient.getBalance() + money);
        activeCondition = true;
    }

    public void cancel() throws IllegalOperationException {
        if (!activeCondition) throw new IllegalOperationException("Can't cancel transaction twice");
        recipient.setBalance(recipient.getBalance() - money);
        sender.setBalance(sender.getBalance() + money);
        activeCondition = false;
    }
}
