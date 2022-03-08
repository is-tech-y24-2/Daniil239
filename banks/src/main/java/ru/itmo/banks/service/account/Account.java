package ru.itmo.banks.service.account;

import ru.itmo.banks.service.bank.Bank;
import ru.itmo.banks.service.customer.Customer;

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
