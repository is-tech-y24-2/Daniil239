package ru.itmo.banks.Services.Banks;

import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotEnoughInformationException;

public class BankBuilder {
    private String name = null;
    private int money = 0;
    private float debitBalanceInterest = 0.01f;
    private float depositBalanceInterest = 0.05f;
    private int creditLimit = 50000;
    private int commission = 100;
    private int depositTermInDays = 50;
    private int notReliableMaxTransactionMoney = 50000;

    public BankBuilder SetName(String name)
    {
        this.name = name;
        return this;
    }

    public BankBuilder SetMoney(int money)
    {
        this.money = money;
        return this;
    }

    public BankBuilder SetDebitBalanceInterest(float debitBalanceInterest)
    {
        this.debitBalanceInterest = debitBalanceInterest;
        return this;
    }

    public BankBuilder SetDepositBalanceInterest(float depositBalanceInterest)
    {
        this.depositBalanceInterest = depositBalanceInterest;
        return this;
    }

    public BankBuilder SetCreditLimit(int creditLimit)
    {
        this.creditLimit = creditLimit;
        return this;
    }

    public BankBuilder SetCommission(int commission)
    {
        this.commission = commission;
        return this;
    }

    public BankBuilder SetDepositTermInDays(int depositTermInDays)
    {
        this.depositTermInDays = depositTermInDays;
        return this;
    }

    public BankBuilder SetNotReliableMaxTransactionMoney(int money)
    {
        this.notReliableMaxTransactionMoney = money;
        return this;
    }

    public Bank GetResult() throws NotEnoughInformationException {
        if ((name == null) || (money == 0)) throw new NotEnoughInformationException("Name and money must be filled");
        return new Bank(name, money, debitBalanceInterest, depositBalanceInterest, creditLimit, commission, depositTermInDays, notReliableMaxTransactionMoney);
    }
}
