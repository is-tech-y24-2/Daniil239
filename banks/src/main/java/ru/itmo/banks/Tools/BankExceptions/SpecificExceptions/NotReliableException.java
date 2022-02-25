package ru.itmo.banks.Tools.BankExceptions.SpecificExceptions;

import ru.itmo.banks.Tools.BankExceptions.BankException;

public class NotReliableException extends BankException {
    public NotReliableException(){
    }

    public NotReliableException(String message){
        super(message);
    }

    public NotReliableException(String message, Exception innerException){
        super(message, innerException);
    }
}
