package ru.itmo.banks.Services.Account;

import ru.itmo.banks.Services.Banks.Bank;

public class BankAccount {
    private Bank bank;
    private float balance;
    public BankAccount(Bank originalBank)
    {
        bank = originalBank;
        balance = 0;
    }

    public Bank getBank() {
        return bank;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
