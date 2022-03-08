package ru.itmo.banks.Tools.BankExceptions.SpecificExceptions;

import ru.itmo.banks.Tools.BankExceptions.BankException;

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
