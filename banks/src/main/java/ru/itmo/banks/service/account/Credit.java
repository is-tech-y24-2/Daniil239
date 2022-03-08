package ru.itmo.banks.service.account;

import ru.itmo.banks.service.bank.Bank;
import ru.itmo.banks.service.customer.Customer;
import ru.itmo.banks.service.Transaction;
import ru.itmo.banks.tool.bankException.SpecificExceptions.AlreadyExecutedException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.NotEnoughMoneyException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.NotReliableException;

public class Credit extends Account implements IPaymentable, ISleepDay {

    public Credit(Customer customer, Bank bank) {
        super(customer, bank);
    }

    public void withdrawMoney(float money) {
        if (money > this.getBank().getNotReliableMaxTransactionMoney()) {
            getCustomer().sendNotification(new NotReliableException("You can not take so much money, pls fill in personal information"));
            return;
        }

        if (this.getBalance() - money <= -this.getBank().getCreditLimit()) {
            getCustomer().sendNotification(new NotEnoughMoneyException("Credit exhausted"));
        } else {
            this.setBalance(getBalance() - money);
        }
    }

    public void sendMoney(Account account, float money) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        if (!this.isReliable() && (money > getBank().getNotReliableMaxTransactionMoney())) {
            getCustomer().sendNotification(new NotReliableException("You can not take so much money, pls fill in personal information"));
            return;
        }

        if (getBalance() - money <= -getBank().getCreditLimit()) {
            getCustomer().sendNotification(new NotEnoughMoneyException("Credit exhausted"));
        } else {
            var newTransaction = new Transaction(this, account, money);
            newTransaction.execute();
            getBank().addTransaction(newTransaction);
        }
    }

    public void putMoney(float money) {
        setBalance(getBalance() + money);
    }

    public boolean equals(Credit other) {
        return getCustomer().equals(other.getCustomer());
    }

    public void sleepDay(float profit) {
        if (getBalance() < 0) setVirtualBalance(getVirtualBalance() - (float) (profit / 30.0));
    }

    public void payments(Bank bank) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var newTransaction = new Transaction(bank.getBankAccount(), this, getVirtualBalance());
        newTransaction.execute();
        bank.addTransaction(newTransaction);
        setVirtualBalance(0.f);
        if (getBalance() < -bank.getCreditLimit())
            getCustomer().sendNotification(new NotEnoughMoneyException("You have not money after crediting"));
    }
}
