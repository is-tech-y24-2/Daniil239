package ru.itmo.banks.service.bank;

import ru.itmo.banks.service.account.Credit;
import ru.itmo.banks.service.account.Debit;
import ru.itmo.banks.service.account.Deposit;
import ru.itmo.banks.service.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class AccountsInBank {
    private final List<Customer> creditCustomers;
    private final List<Customer> debitCustomers;
    private final List<Customer> depositCustomers;
    private final List<Credit> credits;
    private final List<Debit> debits;
    private final List<Deposit> deposits;

    public AccountsInBank() {
        credits = new ArrayList<Credit>();
        debits = new ArrayList<Debit>();
        deposits = new ArrayList<Deposit>();
        creditCustomers = new ArrayList<Customer>();
        debitCustomers = new ArrayList<Customer>();
        depositCustomers = new ArrayList<Customer>();
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public List<Debit> getDebits() {
        return debits;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public List<Customer> getCreditCustomers() {
        return creditCustomers;
    }

    public List<Customer> getDebitCustomers() {
        return debitCustomers;
    }

    public List<Customer> getDepositCustomers() {
        return depositCustomers;
    }

    public void addCredit(Customer customer, Bank bank) {
        var newCredit = new Credit(customer, bank);
        credits.add(newCredit);
        creditCustomers.add(customer);
        customer.addCredit(newCredit);
    }

    public void addDebit(Customer customer, Bank bank) {
        var newDebit = new Debit(customer, bank);
        debits.add(newDebit);
        debitCustomers.add(customer);
        customer.addDebit(newDebit);
    }

    public void addDeposit(Customer customer, Bank bank) {
        var newDeposit = new Deposit(customer, bank, bank.getDepositTermInDays());
        deposits.add(newDeposit);
        depositCustomers.add(customer);
        customer.addDeposit(newDeposit);
    }
}
