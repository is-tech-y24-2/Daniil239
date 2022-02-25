package ru.itmo.banks.Services.Account;

import ru.itmo.banks.Services.Banks.Bank;
import ru.itmo.banks.Services.Customers.Customer;
import ru.itmo.banks.Services.Transaction;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotEnoughMoneyException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotReliableException;

public class Credit extends Account implements IPaymentable, ISleepDay{

    public Credit(Customer customer, Bank bank) {
        super(customer, bank);
    }

    public void WithdrawMoney(float money)
    {
        if (money > this.getBank().getNotReliableMaxTransactionMoney())
        {
            getCustomer().SendNotification(new NotReliableException("You can not take so much money, pls fill in personal information"));
            return;
        }

        if (this.getBalance() - money <= -this.getBank().getCreditLimit())
        {
            getCustomer().SendNotification(new NotEnoughMoneyException("Credit exhausted"));
        }
        else
        {
            this.setBalance(getBalance() - money);
        }
    }

    public void SendMoney(Account account, float money) throws NotEnoughMoneyException, IllegalOperationException {
        if (!this.isReliable() && (money > getBank().getNotReliableMaxTransactionMoney()))
        {
            getCustomer().SendNotification(new NotReliableException("You can not take so much money, pls fill in personal information"));
            return;
        }

        if (getBalance() - money <= -getBank().getCreditLimit())
        {
            getCustomer().SendNotification(new NotEnoughMoneyException("Credit exhausted"));
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
        if (getBalance() < 0) setVirtualBalance(getVirtualBalance() - (float)(profit / 30.0));
    }

    public void Payments(Bank bank) throws NotEnoughMoneyException, IllegalOperationException {
        var newTransaction = new Transaction(bank.getBankAccount(), this, getVirtualBalance());
        newTransaction.Execute();
        bank.AddTransaction(newTransaction);
        setVirtualBalance(0.f);
        if (getBalance() < -bank.getCreditLimit()) getCustomer().SendNotification(new NotEnoughMoneyException("You have not money after crediting"));
    }
}
