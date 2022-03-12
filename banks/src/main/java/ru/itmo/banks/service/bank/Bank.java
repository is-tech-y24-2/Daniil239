package ru.itmo.banks.service.bank;

import ru.itmo.banks.service.account.*;
import ru.itmo.banks.service.customer.Customer;
import ru.itmo.banks.service.Transaction;
import ru.itmo.banks.tool.bankException.SpecificExceptions.AlreadyExecutedException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.AlreadyExistException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private final AccountsInBank accounts = new AccountsInBank();
    private final BankAccount bankAccount;
    private final String name;
    private final List<Transaction> transactions;
    private final int notReliableMaxTransactionMoney;
    private final List<Customer> subscribers;
    private float debitBalanceInterest;
    private float depositBalanceInterest;
    private int creditLimit;
    private int commission;
    private int depositTermInDays;

    public Bank(String bankName, float bankMoney, float bankDebitBalanceInterest, float bankDepositBalanceInterest, int bankCreditLimit, int bankCommission, int bankDepositTermInDays, int bankNotReliableMaxTransactionMoney) {
        name = bankName;
        bankAccount = new BankAccount(this);
        bankAccount.setBalance(bankMoney);
        transactions = new ArrayList<Transaction>();
        debitBalanceInterest = bankDebitBalanceInterest;
        depositBalanceInterest = bankDepositBalanceInterest;
        creditLimit = bankCreditLimit;
        commission = bankCommission;
        depositTermInDays = bankDepositTermInDays;
        notReliableMaxTransactionMoney = bankNotReliableMaxTransactionMoney;
        subscribers = new ArrayList<Customer>();
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public List<Customer> getSubscribers() {
        return subscribers;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public float getDebitBalanceInterest() {
        return debitBalanceInterest;
    }

    public void setDebitBalanceInterest(float newDebitBalanceInterest) {
        debitBalanceInterest = newDebitBalanceInterest;
        reportChanges(String.format("Debit balance interest changes on %s", debitBalanceInterest));
    }

    public float getDepositBalanceInterest() {
        return depositBalanceInterest;
    }

    public void setDepositBalanceInterest(float newDepositBalanceInterest) {
        depositBalanceInterest = newDepositBalanceInterest;
        reportChanges(String.format("Deposit balance interest changes on %s", newDepositBalanceInterest));
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int newCommission) {
        commission = newCommission;
        reportChanges(String.format("Commission changes on %s", newCommission));
    }

    public String getName() {
        return name;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int newCreditLimit) {
        creditLimit = newCreditLimit;
        reportChanges(String.format("Credit limit changes on %s", newCreditLimit));
    }

    public int getDepositTermInDays() {
        return depositTermInDays;
    }

    public void setDepositTermInDays(int newDepositTermInDays) {
        depositTermInDays = newDepositTermInDays;
        reportChanges(String.format("Deposit term changes on %s days", newDepositTermInDays));
    }

    public int getNotReliableMaxTransactionMoney() {
        return notReliableMaxTransactionMoney;
    }

    public void addAccount(Customer customer, AccountType accountType) throws AlreadyExistException {
        switch (accountType) {
            case Credit:
                if (accounts.getCreditCustomers().contains(customer))
                    throw new AlreadyExistException(String.format("%s already have credit in %s", customer.getName(), name));
                accounts.addCredit(customer, this);
                break;
            case Debit:
                if (accounts.getDebitCustomers().contains(customer))
                    throw new AlreadyExistException(String.format("%s already have debit in %s", customer.getName(), name));
                accounts.addDebit(customer, this);
                break;
            case Deposit:
                if (accounts.getDepositCustomers().contains(customer))
                    throw new AlreadyExistException(String.format("%s already have deposit in %s", customer.getName(), name));
                accounts.addDeposit(customer, this);
                break;
        }
    }

    public void withdrawMoneyFromAccount(Account account, float money) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var newTransaction = new Transaction(account, bankAccount, money);
        newTransaction.execute();
        transactions.add(newTransaction);
        if (account.getBalance() < 0)
            account.getCustomer().sendNotification(new NotEnoughMoneyException("Negative balance"));
    }

    public void putMoneyIntoAccount(Account account, float money) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var newTransaction = new Transaction(bankAccount, account, money);
        newTransaction.execute();
        transactions.add(newTransaction);
    }

    public void cancelTransaction(Transaction transaction) throws IllegalOperationException {
        transaction.cancel();
    }

    public void reportChanges(String message) {
        for (Customer subscriber : subscribers) {
            subscriber.sendNotification(message);
        }
    }

    public void addSubscriber(Customer newSubscriber) {
        subscribers.add(newSubscriber);
    }

    public void addTransaction(Transaction newTransaction) {
        transactions.add(newTransaction);
    }

    public void sleepDay() {
        for (Credit credit : accounts.getCredits()) {
            credit.sleepDay(commission);
        }

        for (Debit debit : accounts.getDebits()) {
            debit.sleepDay(debitBalanceInterest);
        }

        for (Deposit deposit : accounts.getDeposits()) {
            deposit.sleepDay(depositBalanceInterest);
        }
    }

    public void payments() throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        for (Credit credit : accounts.getCredits()) {
            credit.payments(this);
        }

        for (Debit debit : accounts.getDebits()) {
            debit.payments(this);
        }

        for (Deposit deposit : accounts.getDeposits()) {
            deposit.payments(this);
        }
    }

    public boolean equals(Bank other) {
        return name == other.name;
    }
}
