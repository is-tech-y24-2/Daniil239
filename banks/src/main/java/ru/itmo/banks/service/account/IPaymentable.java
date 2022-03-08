package ru.itmo.banks.service.account;

import ru.itmo.banks.service.bank.Bank;
import ru.itmo.banks.tool.bankException.SpecificExceptions.AlreadyExecutedException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.NotEnoughMoneyException;

public interface IPaymentable {
    void payments(Bank bank) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException;
}
