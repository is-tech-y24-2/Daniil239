package ru.itmo.banks.tool.bankException.SpecificExceptions;

import ru.itmo.banks.tool.bankException.BankException;

public class NotEnoughInformationException extends BankException {
    public NotEnoughInformationException() {
    }

    public NotEnoughInformationException(String message) {
        super(message);
    }

    public NotEnoughInformationException(String message, Exception innerException) {
        super(message, innerException);
    }
}
