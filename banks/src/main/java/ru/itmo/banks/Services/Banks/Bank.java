package ru.itmo.banks.Services.Banks;

import ru.itmo.banks.Services.Account.Account;
import ru.itmo.banks.Services.Account.AccountType;
import ru.itmo.banks.Services.Account.BankAccount;
import ru.itmo.banks.Services.Account.Credit;
import ru.itmo.banks.Services.Account.Debit;
import ru.itmo.banks.Services.Account.Deposit;
import ru.itmo.banks.Services.Customers.Customer;
import ru.itmo.banks.Services.Transaction;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.AlreadyExistException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotEnoughMoneyException;

import java.util.*;

public class Bank {

    private final AccountsInBank accounts = new AccountsInBank();
    private BankAccount bankAccount;
    private String name;
    private ArrayList<Transaction> transactions;
    private float debitBalanceInterest;
    private float depositBalanceInterest;
    private int creditLimit;
    private int commission;
    private int depositTermInDays;
    private int notReliableMaxTransactionMoney;
    private ArrayList<Customer> subscribers;

    public Bank(String bankName, float bankMoney, float bankDebitBalanceInterest, float bankDepositBalanceInterest, int bankCreditLimit, int bankCommission, int bankDepositTermInDays, int bankNotReliableMaxTransactionMoney)
    {
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

    public ArrayList<Customer> getSubscribers() {
        return subscribers;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public float getDebitBalanceInterest() {
        return debitBalanceInterest;
    }

    public float getDepositBalanceInterest() {
        return depositBalanceInterest;
    }

    public int getCommission() {
        return commission;
    }

    public String getName() {
        return name;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public int getDepositTermInDays() {
        return depositTermInDays;
    }

    public int getNotReliableMaxTransactionMoney() {
        return notReliableMaxTransactionMoney;
    }

    public void AddAccount(Customer customer, AccountType accountType) throws AlreadyExistException
    {
        switch (accountType)
        {
            case Credit:
                if (accounts.getCreditCustomers().contains(customer))
                    throw new AlreadyExistException(String.format("%s already have credit in %s", customer.getName(), name));
                accounts.AddCredit(customer, this);
                break;
            case Debit:
                if (accounts.getDebitCustomers().contains(customer))
                    throw new AlreadyExistException(String.format("%s already have debit in %s", customer.getName(), name));
                accounts.AddDebit(customer, this);
                break;
            case Deposit:
                if (accounts.getDepositCustomers().contains(customer))
                    throw new AlreadyExistException(String.format("%s already have deposit in %s", customer.getName(), name));
                accounts.AddDeposit(customer, this);
                break;
        }
    }

    public void WithdrawMoneyFromAccount(Account account, float money) throws NotEnoughMoneyException, IllegalOperationException {
        var newTransaction = new Transaction(account, bankAccount, money);
        newTransaction.Execute();
        transactions.add(newTransaction);
        if (account.getBalance() < 0) account.getCustomer().SendNotification(new NotEnoughMoneyException("Negative balance"));
    }

    public void PutMoneyIntoAccount(Account account, float money) throws NotEnoughMoneyException, IllegalOperationException {
        var newTransaction = new Transaction(bankAccount, account, money);
        newTransaction.Execute();
        transactions.add(newTransaction);
    }

    public void CancelTransaction(Transaction transaction) throws IllegalOperationException {
        transaction.Cancel();
    }

    public void ReportChanges(String message)
    {
        for (Customer subscriber : subscribers)
        {
            subscriber.SendNotification(message);
        }
    }

    public void SetDebitBalanceInterest(float newDebitBalanceInterest)
    {
        debitBalanceInterest = newDebitBalanceInterest;
        ReportChanges(String.format("Debit balance interest changes on %s", debitBalanceInterest));
    }

    public void SetDepositBalanceInterest(float newDepositBalanceInterest)
    {
        depositBalanceInterest = newDepositBalanceInterest;
        ReportChanges(String.format("Deposit balance interest changes on %s", newDepositBalanceInterest));
    }

    public void SetCreditLimit(int newCreditLimit)
    {
        creditLimit = newCreditLimit;
        ReportChanges(String.format("Credit limit changes on %s", newCreditLimit));
    }

    public void SetCommission(int newCommission)
    {
        commission = newCommission;
        ReportChanges(String.format("Commission changes on %s", newCommission));
    }

    public void SetDepositTermInDays(int newDepositTermInDays)
    {
        depositTermInDays = newDepositTermInDays;
        ReportChanges(String.format("Deposit term changes on %s days", newDepositTermInDays));
    }

    public void AddSubscriber(Customer newSubscriber){
        subscribers.add(newSubscriber);
    }

    public void AddTransaction(Transaction newTransaction){
        transactions.add(newTransaction);
    }

    public void SleepDay()
    {
        for (Credit credit : accounts.getCredits())
        {
            credit.SleepDay(commission);
        }

        for (Debit debit : accounts.getDebits())
        {
            debit.SleepDay(debitBalanceInterest);
        }

        for (Deposit deposit : accounts.getDeposits())
        {
            deposit.SleepDay(depositBalanceInterest);
        }
    }

    public void Payments() throws NotEnoughMoneyException, IllegalOperationException {
        for (Credit credit : accounts.getCredits())
        {
            credit.Payments(this);
        }

        for (Debit debit : accounts.getDebits())
        {
            debit.Payments(this);
        }

        for (Deposit deposit : accounts.getDeposits())
        {
            deposit.Payments(this);
        }
    }

    public boolean Equals(Bank other)
    {
        return name == other.name;
    }
}
