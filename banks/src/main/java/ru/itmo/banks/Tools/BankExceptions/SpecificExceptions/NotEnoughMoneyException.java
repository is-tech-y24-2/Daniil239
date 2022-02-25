package ru.itmo.banks.Tools.BankExceptions.SpecificExceptions;

import ru.itmo.banks.Tools.BankExceptions.BankException;

public class NotEnoughMoneyException extends BankException {
    public NotEnoughMoneyException(){
    }

    public NotEnoughMoneyException(String message){
        super(message);
    }

    public NotEnoughMoneyException(String message, Exception innerException){
        super(message, innerException);
    }
}
