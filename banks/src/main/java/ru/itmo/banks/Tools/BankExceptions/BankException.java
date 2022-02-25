package ru.itmo.banks.Tools.BankExceptions;

public class BankException extends Exception{
    public BankException(){
    }

    public BankException(String message){
        super(message);
    }

    public BankException(String message, Exception innerException){
        super(message, innerException);
    }
}
