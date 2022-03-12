package ru.itmo.banks;

import ru.itmo.banks.service.account.AccountType;
import ru.itmo.banks.service.account.Credit;
import ru.itmo.banks.service.account.Debit;
import ru.itmo.banks.service.account.Deposit;
import ru.itmo.banks.service.bank.Bank;
import ru.itmo.banks.service.bank.CentralBank;
import ru.itmo.banks.service.customer.Customer;
import ru.itmo.banks.service.customer.CustomerBuilder;
import ru.itmo.banks.tool.bankException.SpecificExceptions.AlreadyExistException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.NotEnoughInformationException;
import ru.itmo.banks.tool.bankException.SpecificExceptions.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws NotEnoughInformationException, AlreadyExistException, NotEnoughMoneyException, IllegalOperationException {

        List<Bank> banks = new ArrayList<Bank>();
        List<Customer> customers = new ArrayList<Customer>();

        Scanner in = new Scanner(System.in);
        String request;
        String ans;
        var centralBank = new CentralBank("central");

        while (true) {

            System.out.print("What you want to do: ");
            request = in.nextLine();
            switch (request) {
                case "Create bank":
                    System.out.print("Name: ");
                    ans = in.nextLine();
                    if (banks.contains(centralBank.createBank(ans, 1000000))) {
                        System.out.print("this bank already exist");
                        break;
                    }
                    banks.add(centralBank.createBank(ans, 1000000));
                    break;

                case "Create customer":
                    System.out.print("Name: ");
                    String name = in.nextLine();
                    System.out.print("Second name: ");
                    String secondName = in.nextLine();
                    System.out.print("Address: ");
                    String address = in.nextLine();
                    System.out.print("Passport number: ");
                    String passportNumber = in.nextLine();
                    if (customers.contains(new CustomerBuilder().setName(name).setSecondName(secondName).setAddress(address).setPassportNumber(passportNumber).getResult())) {
                        System.out.print("this customer already exist");
                        break;
                    }
                    customers.add(new CustomerBuilder().setName(name).setSecondName(secondName).setAddress(address).setPassportNumber(passportNumber).getResult());
                    break;

                case "Add account":
                    System.out.print("In what bank: ");
                    String rBank = in.nextLine();
                    for (Bank bank : banks) {
                        if (bank.getName().equals(rBank)) {
                            System.out.print("What customer: ");
                            String rCustomer = in.nextLine();
                            for (Customer customer : customers) {
                                if (customer.getName().equals(rCustomer)) {
                                    System.out.print("What account type: ");
                                    String rAccount = in.nextLine();
                                    switch (rAccount) {
                                        case "Credit":
                                            bank.addAccount(customer, AccountType.Credit);
                                            break;
                                        case "Debit":
                                            bank.addAccount(customer, AccountType.Debit);
                                            break;
                                        case "Deposit":
                                            bank.addAccount(customer, AccountType.Deposit);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                    }
                    break;

                case "Get balance":
                    System.out.print("What customer: ");
                    String rCustomer = in.nextLine();
                    for (Customer customer : customers) {
                        if (customer.getName().equals(rCustomer)) {
                            for (Credit credit : customer.getCredits())
                                System.out.println(credit.getBalance() + " on credit");
                            for (Debit debit : customer.getDebits())
                                System.out.println(debit.getBalance() + " on debit");
                            for (Deposit deposit : customer.getDeposits())
                                System.out.println(deposit.getBalance() + " on deposit");
                        }
                    }
                    break;

                case "Sleep":
                    System.out.print("How many days: ");
                    try {
                        centralBank.sleepDays(in.nextInt());
                    } catch (Exception e) {
                        System.out.print("Game over");
                        return;
                    }
                    break;

                case "Put money":
                    System.out.print("What customer: ");
                    String lCustomer = in.nextLine();
                    for (Customer customer : customers) {
                        if (customer.getName().equals(lCustomer)) {
                            System.out.print("What account type: ");
                            String lAccount = in.nextLine();
                            switch (lAccount) {
                                case "Credit":
                                    System.out.print("How many: ");
                                    customer.getCredits().get(0).putMoney(in.nextFloat());
                                    break;
                                case "Debit":
                                    System.out.print("How many: ");
                                    customer.getDebits().get(0).putMoney(in.nextFloat());
                                    break;
                                case "Deposit":
                                    System.out.print("How many: ");
                                    customer.getDeposits().get(0).putMoney(in.nextFloat());
                                    break;
                                default:
                                    System.out.print("what?");
                                    break;
                            }
                        }
                    }
                    break;

                case "What i have":
                    for (Bank bank : banks) System.out.println(bank.getName());
                    for (Customer customer : customers) System.out.println(customer.getName());
                    break;

                case "Stop":
                    return;

                default:
                    System.out.println("done");
            }
        }
    }
}
