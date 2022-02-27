package ru.itmo.banks.Banks;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.itmo.banks.Services.Account.AccountType;
import ru.itmo.banks.Services.Account.Credit;
import ru.itmo.banks.Services.Account.Debit;
import ru.itmo.banks.Services.Account.Deposit;
import ru.itmo.banks.Services.Banks.Bank;
import ru.itmo.banks.Services.Banks.CentralBank;
import ru.itmo.banks.Services.Customers.Customer;
import ru.itmo.banks.Services.Customers.CustomerBuilder;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.*;

public class BanksTest {

        @Test
    public void AddingAccounts_CustomerHaveCredit() throws NotEnoughInformationException, AlreadyExistException {
        var centralBank = new CentralBank("central");
        Bank sber = centralBank.CreateBank("Sber", 1000000);
        Customer daniil = new CustomerBuilder().SetName("daniil").SetSecondName("alexandrov").SetAddress("isjn").GetResult();
        Assert.assertTrue(daniil.getCredits().isEmpty());
        sber.AddAccount(daniil, AccountType.Credit);
        Assert.assertFalse(daniil.getCredits().isEmpty());
    }

        @Test
    public void CreditingAndDebiting_CustomerHaveCommissionAndProfitWithTime() throws NotEnoughInformationException, AlreadyExistException, NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var centralBank = new CentralBank("central");
        Bank sber = centralBank.CreateBank("Sber", 1000000);
        Customer daniil = new CustomerBuilder().SetName("daniil").SetSecondName("alexandrov").SetAddress("isjn").GetResult();
        sber.AddAccount(daniil, AccountType.Credit);
        Credit credit = daniil.getCredits().get(0);
        sber.AddAccount(daniil, AccountType.Debit);
        Debit debit = daniil.getDebits().get(0);
        debit.PutMoney(100000);
        credit.WithdrawMoney(100);
        Assert.assertTrue(credit.getBalance() == -100);
        Assert.assertTrue(debit.getBalance() == 100000);
        centralBank.SleepMonths(2);
        Assert.assertTrue(credit.getBalance() < 100);
        Assert.assertTrue(100000 < debit.getBalance());
    }

        @Test
    public void SendingALotOfMoneyIfCustomerIsReliable_MoneySendsIfReliable() throws NotEnoughInformationException, AlreadyExistException, NotEnoughMoneyException, IllegalOperationException, DoesNotExistException, AlreadyExecutedException {
        var centralBank = new CentralBank("central");
        Bank sber = centralBank.CreateBank("Sber", 1000000);
        Customer daniil = new CustomerBuilder().SetName("daniil").SetSecondName("alexandrov").SetAddress("isjn").GetResult();
        sber.AddAccount(daniil, AccountType.Credit);
        Credit credit = daniil.getCredits().get(0);
        Customer miron = new CustomerBuilder().SetName("miron").SetSecondName("kek").SetAddress("rglifb").GetResult();
        sber.AddAccount(miron, AccountType.Debit);
        Debit debit = miron.getDebits().get(0);
        debit.PutMoney(100000);
        debit.SendMoney(credit, 99000);
        Assert.assertFalse(99000 == credit.getBalance());
        Assert.assertFalse(1000 == debit.getBalance());
        miron.SetPassportNumber("4017186690"); // now debit is reliable
        debit.SendMoney(credit, 99000);
        Assert.assertTrue(99000 == credit.getBalance());
        Assert.assertTrue(1000 == debit.getBalance());
    }

        @Test
    public void DepositTerm_CustomerCanWithdrawMoneyAfterDepositTerm() throws NotEnoughInformationException, AlreadyExistException, NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var centralBank = new CentralBank("central");
        Bank sber = centralBank.CreateBank("Sber", 1000000);
        sber.SetDepositTermInDays(2);
        Customer daniil = new CustomerBuilder().SetName("daniil").SetSecondName("alexandrov").SetAddress("isjn").GetResult();
        sber.AddAccount(daniil, AccountType.Deposit);
        Deposit deposit = daniil.getDeposits().get(0);
        deposit.PutMoney(100000);
        deposit.WithdrawMoney(1);
        Assert.assertTrue(100000 == deposit.getBalance());
        centralBank.SleepDays(2);
        deposit.WithdrawMoney(1);
        Assert.assertTrue(99999 == deposit.getBalance());
    }

        @Test
    public void SendingMoneyBetweenBanks_MoneySends() throws NotEnoughInformationException, AlreadyExistException, NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var centralBank = new CentralBank("central");
        Bank sber = centralBank.CreateBank("Sber", 1000000);
        Bank tinkoff = centralBank.CreateBank("Tinkoff", 1000000);
        centralBank.SendMoneyBetweenBanks(sber, tinkoff, 500000);
        Assert.assertTrue(500000 == sber.getBankAccount().getBalance());
        Assert.assertTrue(1500000 == tinkoff.getBankAccount().getBalance());
    }

        @Test
    public void CancelTransaction_MoneyReturnsAndTransactionIsNotActive() throws NotEnoughInformationException, AlreadyExistException, NotEnoughMoneyException, IllegalOperationException, AlreadyExecutedException {
        var centralBank = new CentralBank("central");
        Bank sber = centralBank.CreateBank("Sber", 1000000);
        Customer daniil = new CustomerBuilder().SetName("daniil").SetSecondName("alexandrov").SetAddress("isjn").GetResult();
        sber.AddAccount(daniil, AccountType.Credit);
        Credit credit = daniil.getCredits().get(0);
        credit.WithdrawMoney(100);
        float balanceBefore = credit.getBalance();
        centralBank.SleepMonths(1);
        Assert.assertFalse(balanceBefore == credit.getBalance());
        sber.getTransactions().get(sber.getTransactions().size() - 1).Cancel();
        Assert.assertTrue(balanceBefore == credit.getBalance());
        Assert.assertFalse(sber.getTransactions().get(sber.getTransactions().size() - 1).isActiveCondition());
    }
}
