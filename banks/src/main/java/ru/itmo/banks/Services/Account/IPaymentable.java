package ru.itmo.banks.Services.Account;

import ru.itmo.banks.Services.Banks.Bank;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.AlreadyExecutedException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotEnoughMoneyException;

public interface IPaymentable {
    void Payments(Bank bank) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException;
}
