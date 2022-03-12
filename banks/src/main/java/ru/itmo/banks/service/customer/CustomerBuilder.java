package ru.itmo.banks.service.customer;

import ru.itmo.banks.tool.bankException.SpecificExceptions.NotEnoughInformationException;

public class CustomerBuilder {
    private String name = null;
    private String secondName = null;
    private String address = "default";
    private String passportNumber = "default";

    public CustomerBuilder() {
    }

    public CustomerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public CustomerBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public CustomerBuilder setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    public Customer getResult() throws NotEnoughInformationException {
        if ((name == null) || (secondName == null))
            throw new NotEnoughInformationException("Name and second name must be filled");
        return new Customer(name, secondName, address, passportNumber);
    }
}
