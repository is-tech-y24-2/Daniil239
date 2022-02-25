package ru.itmo.banks.Tools.BankExceptions.SpecificExceptions;

import ru.itmo.banks.Tools.BankExceptions.BankException;

public class IllegalOperationException extends BankException {
    public IllegalOperationException(){
    }

    public IllegalOperationException(String message){
        super(message);
    }

    public IllegalOperationException(String message, Exception innerException){
        super(message, innerException);
    }
}
