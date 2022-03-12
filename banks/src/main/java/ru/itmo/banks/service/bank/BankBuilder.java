package ru.itmo.banks.service.bank;

import ru.itmo.banks.tool.bankException.SpecificExceptions.NotEnoughInformationException;

public class BankBuilder {
    private String name = null;
    private int money = 0;
    private float debitBalanceInterest = 0.01f;
    private float depositBalanceInterest = 0.05f;
    private int creditLimit = 50000;
    private int commission = 100;
    private int depositTermInDays = 50;
    private int notReliableMaxTransactionMoney = 50000;

    public BankBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BankBuilder setMoney(int money) {
        this.money = money;
        return this;
    }

    public BankBuilder setDebitBalanceInterest(float debitBalanceInterest) {
        this.debitBalanceInterest = debitBalanceInterest;
        return this;
    }

    public BankBuilder setDepositBalanceInterest(float depositBalanceInterest) {
        this.depositBalanceInterest = depositBalanceInterest;
        return this;
    }

    public BankBuilder setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    public BankBuilder setCommission(int commission) {
        this.commission = commission;
        return this;
    }

    public BankBuilder setDepositTermInDays(int depositTermInDays) {
        this.depositTermInDays = depositTermInDays;
        return this;
    }

    public BankBuilder setNotReliableMaxTransactionMoney(int money) {
        this.notReliableMaxTransactionMoney = money;
        return this;
    }

    public Bank getResult() throws NotEnoughInformationException {
        if ((name == null) || (money == 0)) throw new NotEnoughInformationException("Name and money must be filled");
        return new Bank(name, money, debitBalanceInterest, depositBalanceInterest, creditLimit, commission, depositTermInDays, notReliableMaxTransactionMoney);
    }
}
