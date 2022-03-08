package ru.itmo.banks.Services.Banks;

import ru.itmo.banks.Services.Account.Credit;
import ru.itmo.banks.Services.Account.Debit;
import ru.itmo.banks.Services.Account.Deposit;
import ru.itmo.banks.Services.Customers.Customer;

import java.util.ArrayList;

public class AccountsInBank {
    private final ArrayList<Customer> creditCustomers;
    private final ArrayList<Customer> debitCustomers;
    private final ArrayList<Customer> depositCustomers;
    private final ArrayList<Credit> credits;
    private final ArrayList<Debit> debits;
    private final ArrayList<Deposit> deposits;

    public AccountsInBank() {
        credits = new ArrayList<Credit>();
        debits = new ArrayList<Debit>();
        deposits = new ArrayList<Deposit>();
        creditCustomers = new ArrayList<Customer>();
        debitCustomers = new ArrayList<Customer>();
        depositCustomers = new ArrayList<Customer>();
    }

    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    public ArrayList<Debit> getDebits() {
        return debits;
    }

    public ArrayList<Credit> getCredits() {
        return credits;
    }

    public ArrayList<Customer> getCreditCustomers() {
        return creditCustomers;
    }

    public ArrayList<Customer> getDebitCustomers() {
        return debitCustomers;
    }

    public ArrayList<Customer> getDepositCustomers() {
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
