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
    private String address;
    private String passportNumber;
    private String name;
    private String secondName;

    public Customer(String customerName, String customerSecondName, String customerAddress, String customerPassportNumber)
    {
        name = customerName;
        secondName = customerSecondName;
        address = customerAddress;
        passportNumber = customerPassportNumber;
        credits = new ArrayList<Credit>();
        debits = new ArrayList<Debit>();
        deposits = new ArrayList<Deposit>();
    }


    public String getName(){
        return name;
    }
    public String getSecondName(){
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

    public boolean equals(Customer other)
    {
        return name == other.name && secondName == other.secondName;
    }

    public void setAddress(String newAddress) throws DoesNotExistException
    {
        if (!IsAddressExist(newAddress)) throw new DoesNotExistException("This address doesn't exist");
        address = newAddress;
    }

    public void SetPassportNumber(String newPassportNumber) throws DoesNotExistException
    {
        if (!IsPassportNumberExist(newPassportNumber)) throw new DoesNotExistException("This passport number doesn't exist");
        passportNumber = newPassportNumber;
    }

    public void AddCredit(Credit credit)
    {
        credits.add(credit);
    }

    public void AddDebit(Debit debit)
    {
        debits.add(debit);
    }

    public void AddDeposit(Deposit deposit)
    {
        deposits.add(deposit);
    }

    public boolean isReliable()
    {
        return !((address == "default") || (passportNumber == "default"));
    }

    public void SendNotification(BankException exception)
    {
        // Some kind of implementation
    }

    public void SendNotification(String message)
    {
        // Some kind of implementation
    }

    public void Subscribe(Bank bank)
    {
        bank.AddSubscriber(this);
    }

    private boolean IsAddressExist(String newAddress)
    {
        // can add: throw new DoesNotExistException("This address doesn't exist");
        return true;
    }

    private boolean IsPassportNumberExist(String newPassportNumber)
    {
        if (newPassportNumber.length() != 10) return false;
        for (char c : newPassportNumber.toCharArray())
        {
            if (c < '0' || c > '9') return false;
        }

        return true;
    }
}
