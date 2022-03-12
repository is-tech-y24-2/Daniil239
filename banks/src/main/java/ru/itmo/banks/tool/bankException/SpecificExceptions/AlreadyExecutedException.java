package ru.itmo.banks.tool.bankException.SpecificExceptions;

import ru.itmo.banks.tool.bankException.BankException;

public class AlreadyExecutedException extends BankException {
    public AlreadyExecutedException() {
    }

    public AlreadyExecutedException(String message) {
        super(message);
    }

    public AlreadyExecutedException(String message, Exception innerException) {
        super(message, innerException);
    }
}
