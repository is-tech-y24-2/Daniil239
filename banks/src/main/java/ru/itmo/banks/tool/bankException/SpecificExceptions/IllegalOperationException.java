package ru.itmo.banks.tool.bankException.SpecificExceptions;

import ru.itmo.banks.tool.bankException.BankException;

public class IllegalOperationException extends BankException {
    public IllegalOperationException() {
    }

    public IllegalOperationException(String message) {
        super(message);
    }

    public IllegalOperationException(String message, Exception innerException) {
        super(message, innerException);
    }
}
