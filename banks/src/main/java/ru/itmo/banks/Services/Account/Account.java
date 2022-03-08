package ru.itmo.banks.Services.Account;

import ru.itmo.banks.Services.Banks.Bank;
import ru.itmo.banks.Services.Customers.Customer;

public class Account extends BankAccount {

    private final Customer customer;
    private float virtualBalance;

    public Account(Customer AccountCustomer, Bank originalBank) {
        super(originalBank);
        customer = AccountCustomer;
        virtualBalance = 0;
    }

    public Customer getCustomer() {
        return customer;
    }

    public float getVirtualBalance() {
        return virtualBalance;
    }

    public void setVirtualBalance(float virtualBalance) {
        this.virtualBalance = virtualBalance;
    }

    public boolean isReliable() {
        return getCustomer().isReliable();
    }
}
