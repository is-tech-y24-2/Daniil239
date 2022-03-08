package ru.itmo.banks.tool.bankException.SpecificExceptions;

import ru.itmo.banks.tool.bankException.BankException;

public class NotReliableException extends BankException {
    public NotReliableException() {
    }

    public NotReliableException(String message) {
        super(message);
    }

    public NotReliableException(String message, Exception innerException) {
        super(message, innerException);
    }
}
