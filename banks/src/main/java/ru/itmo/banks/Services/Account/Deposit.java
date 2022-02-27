package ru.itmo.banks.Services.Account;

import ru.itmo.banks.Services.Banks.Bank;
import ru.itmo.banks.Services.Customers.Customer;
import ru.itmo.banks.Services.Transaction;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.AlreadyExecutedException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotEnoughMoneyException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotReliableException;

public class Deposit extends Account implements IPaymentable, ISleepDay{

    private int termInDays;

    public Deposit(Customer customer, Bank bank, int termInDays)
    {
        super(customer, bank);
        this.termInDays = termInDays;
    }

    public int getTermInDays() {
        return termInDays;
    }

    public void setTermInDays(int termInDays) {
        this.termInDays = termInDays;
    }

    public void WithdrawMoney(float money)
    {
        if (!CanWork())
        {
            getCustomer().SendNotification(String.format("Too early, wait %s days", termInDays));
            return;
        }

        if (!this.isReliable() && (money > getBank().getNotReliableMaxTransactionMoney()))
        {
            getCustomer().SendNotification(new NotReliableException("You can not take so much money, pls fill in personal information"));
            return;
        }

        if (getBalance() < money)
        {
            getCustomer().SendNotification(new NotEnoughMoneyException("Not enough money"));
        }
        else
        {
            setBalance(getBalance() - money);
            getCustomer().SendNotification("You can take your money");
        }
    }

    public void SendMoney(Account account, float money) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        if (!CanWork())
        {
            getCustomer().SendNotification(String.format("Too early, wait %s days", termInDays));
            return;
        }

        if (money > getBank().getNotReliableMaxTransactionMoney())
        {
            getCustomer().SendNotification(new NotReliableException("You can not take so much money, pls fill in personal information"));
            return;
        }

        if (getBalance() < money)
        {
            getCustomer().SendNotification(new NotEnoughMoneyException("Not enough money"));
        }
        else
        {
            var newTransaction = new Transaction(this, account, money);
            newTransaction.Execute();
            getBank().AddTransaction(newTransaction);
        }
    }

    public void PutMoney(float money)
    {
        setBalance(getBalance() + money);
    }

    public boolean Equals(Credit other)
    {
        return getCustomer().equals(other.getCustomer());
    }

    public void SleepDay(float profit)
    {
        setVirtualBalance(getVirtualBalance() + (float)(getBalance() * profit / 365.0));
        termInDays--;
    }

    public void Payments(Bank bank) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var newTransaction = new Transaction(getBank().getBankAccount(), this, getVirtualBalance());
        newTransaction.Execute();
        getBank().AddTransaction(newTransaction);
    }

    private boolean CanWork()
    {
        return termInDays <= 0;
    }
}
