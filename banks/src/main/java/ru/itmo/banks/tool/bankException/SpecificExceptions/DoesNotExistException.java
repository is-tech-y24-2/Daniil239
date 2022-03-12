package ru.itmo.banks.tool.bankException.SpecificExceptions;

import ru.itmo.banks.tool.bankException.BankException;

public class DoesNotExistException extends BankException {
    public DoesNotExistException() {
    }

    public DoesNotExistException(String message) {
        super(message);
    }

    public DoesNotExistException(String message, Exception innerException) {
        super(message, innerException);
    }
}
