package ru.itmo.banks.service.account;

import ru.itmo.banks.service.bank.Bank;

public class BankAccount {
    private final Bank bank;
    private float balance;

    public BankAccount(Bank originalBank) {
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
