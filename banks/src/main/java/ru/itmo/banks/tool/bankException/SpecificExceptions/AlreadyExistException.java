package ru.itmo.banks.tool.bankException.SpecificExceptions;

import ru.itmo.banks.tool.bankException.BankException;

public class AlreadyExistException extends BankException {
    public AlreadyExistException() {
    }

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(String message, Exception innerException) {
        super(message, innerException);
    }
}
