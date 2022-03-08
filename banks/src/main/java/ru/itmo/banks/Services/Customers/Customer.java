package ru.itmo.banks.Services.Customers;

import ru.itmo.banks.Services.Account.Credit;
import ru.itmo.banks.Services.Account.Debit;
import ru.itmo.banks.Services.Account.Deposit;
import ru.itmo.banks.Services.Banks.Bank;
import ru.itmo.banks.Tools.BankExceptions.BankException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.DoesNotExistException;

import java.util.ArrayList;

public class Customer {
    private final ArrayList<Credit> credits;
    private final ArrayList<Debit> debits;
    private final ArrayList<Deposit> deposits;
    private final String name;
    private final String secondName;
    private String address;
    private String passportNumber;

    public Customer(String customerName, String customerSecondName, String customerAddress, String customerPassportNumber) {
        name = customerName;
        secondName = customerSecondName;
        address = customerAddress;
        passportNumber = customerPassportNumber;
        credits = new ArrayList<Credit>();
        debits = new ArrayList<Debit>();
        deposits = new ArrayList<Deposit>();
    }


    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public ArrayList<Credit> getCredits() {
        return credits;
    }

    public ArrayList<Debit> getDebits() {
        return debits;
    }

    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    public boolean equals(Customer other) {
        return name == other.name && secondName == other.secondName;
    }

    public void setAddress(String newAddress) throws DoesNotExistException {
        if (!isAddressExist(newAddress)) throw new DoesNotExistException("This address doesn't exist");
        address = newAddress;
    }

    public void setPassportNumber(String newPassportNumber) throws DoesNotExistException {
        if (!isPassportNumberExist(newPassportNumber))
            throw new DoesNotExistException("This passport number doesn't exist");
        passportNumber = newPassportNumber;
    }

    public void addCredit(Credit credit) {
        credits.add(credit);
    }

    public void addDebit(Debit debit) {
        debits.add(debit);
    }

    public void addDeposit(Deposit deposit) {
        deposits.add(deposit);
    }

    public boolean isReliable() {
        return !((address == "default") || (passportNumber == "default"));
    }

    public void sendNotification(BankException exception) {
        // Some kind of implementation
    }

    public void sendNotification(String message) {
        // Some kind of implementation
    }

    public void subscribe(Bank bank) {
        bank.addSubscriber(this);
    }

    private boolean isAddressExist(String newAddress) {
        // can add: throw new DoesNotExistException("This address doesn't exist");
        return true;
    }

    private boolean isPassportNumberExist(String newPassportNumber) {
        if (newPassportNumber.length() != 10) return false;
        for (char c : newPassportNumber.toCharArray()) {
            if (c < '0' || c > '9') return false;
        }

        return true;
    }
}
