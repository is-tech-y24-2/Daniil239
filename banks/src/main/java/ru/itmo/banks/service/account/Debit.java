package ru.itmo.banks.service.account;

import ru.itmo.banks.service.bank.Bank;
import ru.itmo.banks.service.customer.Customer;
import ru.itmo.banks.service.Transaction;
import ru.itmo.banks.tool.bankException.SpecificExceptions.AlreadyExecutedException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.NotEnoughMoneyException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.NotReliableException;

public class Debit extends Account implements IPaymentable, ISleepDay {
    public Debit(Customer customer, Bank bank) {
        super(customer, bank);
    }

    public void withdrawMoney(float money) {
        if (money > getBank().getNotReliableMaxTransactionMoney()) {
            getCustomer().sendNotification(new NotReliableException("You can not take so much money, pls fill in personal information"));
            return;
        }

        if (getBalance() < money) {
            getCustomer().sendNotification(new NotEnoughMoneyException("Not enough money"));
        } else {
            setBalance(getBalance() - money);
            getCustomer().sendNotification("You can take your money");
        }
    }

    public void sendMoney(Account account, float money) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        if (!this.isReliable() && (money > getBank().getNotReliableMaxTransactionMoney())) {
            getCustomer().sendNotification(new NotReliableException("You can not take so much money, pls fill in personal information"));
            return;
        }

        if (getBalance() < money) {
            getCustomer().sendNotification(new NotEnoughMoneyException("Not enough money"));
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
        setVirtualBalance(getVirtualBalance() + (float) (getBalance() * profit / 365.0));
    }

    public void payments(Bank bank) throws NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var newTransaction = new Transaction(bank.getBankAccount(), this, getVirtualBalance());
        newTransaction.execute();
        getBank().addTransaction(newTransaction);
    }
}
