package ru.itmo.banks.Tools.BankExceptions.SpecificExceptions;

import ru.itmo.banks.Tools.BankExceptions.BankException;

public class NotEnoughInformationException extends BankException {
    public NotEnoughInformationException(){
    }

    public NotEnoughInformationException(String message){
        super(message);
    }

    public NotEnoughInformationException(String message, Exception innerException){
        super(message, innerException);
    }
}
