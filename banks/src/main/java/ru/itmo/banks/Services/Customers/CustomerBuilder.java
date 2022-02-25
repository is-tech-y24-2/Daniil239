package ru.itmo.banks.Services.Customers;

import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotEnoughInformationException;

public class CustomerBuilder {
    private String name = null;
    private String secondName = null;
    private String address = "default";
    private String passportNumber = "default";

    public CustomerBuilder()
    {
    }

    public CustomerBuilder SetName(String name)
    {
        this.name = name;
        return this;
    }

    public CustomerBuilder SetSecondName(String secondName)
    {
        this.secondName = secondName;
        return this;
    }

    public CustomerBuilder SetAddress(String address)
    {
        this.address = address;
        return this;
    }

    public CustomerBuilder SetPassportNumber(String passportNumber)
    {
        this.passportNumber = passportNumber;
        return this;
    }

    public Customer GetResult() throws NotEnoughInformationException {
        if ((name == null) || (secondName == null)) throw new NotEnoughInformationException("Name and second name must be filled");
        return new Customer(name, secondName, address, passportNumber);
    }
}
