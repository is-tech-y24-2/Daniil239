package ru.itmo.banks;

import ru.itmo.banks.Services.Account.*;
import ru.itmo.banks.Services.Banks.Bank;
import ru.itmo.banks.Services.Banks.CentralBank;
import ru.itmo.banks.Services.Customers.Customer;
import ru.itmo.banks.Services.Customers.CustomerBuilder;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.AlreadyExistException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.IllegalOperationException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotEnoughInformationException;
import ru.itmo.banks.Tools.BankExceptions.SpecificExceptions.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws NotEnoughInformationException, AlreadyExistException, NotEnoughMoneyException, IllegalOperationException {

        ArrayList<Bank> banks = new ArrayList<Bank>();
        ArrayList<Customer> customers = new ArrayList<Customer>();

        Scanner in = new Scanner(System.in);
        String request;
        String ans;
        var centralBank = new CentralBank("central");

        while (true){

            System.out.print("What you want to do: ");
            request = in.nextLine();
            switch (request){
                case "Create bank":
                    System.out.print("Name: ");
                    ans = in.nextLine();
                    if (banks.contains(centralBank.CreateBank(ans, 1000000))){
                        System.out.print("this bank already exist");
                        break;
                    }
                    banks.add(centralBank.CreateBank(ans, 1000000));
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
                    if(customers.contains(new CustomerBuilder().SetName(name).SetSecondName(secondName).SetAddress(address).SetPassportNumber(passportNumber).GetResult())){
                        System.out.print("this customer already exist");
                        break;
                    }
                    customers.add(new CustomerBuilder().SetName(name).SetSecondName(secondName).SetAddress(address).SetPassportNumber(passportNumber).GetResult());
                    break;

                case "Add account":
                    System.out.print("In what bank: ");
                    String rBank = in.nextLine();
                    for (Bank bank: banks){
                        if (bank.getName().equals(rBank)){
                            System.out.print("What customer: ");
                            String rCustomer = in.nextLine();
                            for (Customer customer: customers){
                                if(customer.getName().equals(rCustomer)){
                                    System.out.print("What account type: ");
                                    String rAccount = in.nextLine();
                                    switch (rAccount){
                                        case "Credit":
                                            bank.AddAccount(customer, AccountType.Credit);
                                            break;
                                        case "Debit":
                                            bank.AddAccount(customer, AccountType.Debit);
                                            break;
                                        case "Deposit":
                                            bank.AddAccount(customer, AccountType.Deposit);
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
                    for (Customer customer: customers){
                        if(customer.getName().equals(rCustomer)){
                            for (Credit credit : customer.getCredits()) System.out.println(credit.getBalance() + " on credit");
                            for (Debit debit : customer.getDebits()) System.out.println(debit.getBalance() + " on debit");
                            for (Deposit deposit : customer.getDeposits()) System.out.println(deposit.getBalance() + " on deposit");
                        }
                    }
                    break;

                case "Sleep":
                    System.out.print("How many days: ");
                    try{
                        centralBank.SleepDays(in.nextInt());
                    } catch (Exception e) {
                        System.out.print("Game over");
                        return;
                    }
                    break;

                case "Put money":
                    System.out.print("What customer: ");
                    String lCustomer = in.nextLine();
                    for (Customer customer: customers){
                        if(customer.getName().equals(lCustomer)){
                            System.out.print("What account type: ");
                            String lAccount = in.nextLine();
                            switch (lAccount){
                                case "Credit":
                                    System.out.print("How many: ");
                                    customer.getCredits().get(0).PutMoney(in.nextFloat());
                                    break;
                                case "Debit":
                                    System.out.print("How many: ");
                                    customer.getDebits().get(0).PutMoney(in.nextFloat());
                                    break;
                                case "Deposit":
                                    System.out.print("How many: ");
                                    customer.getDeposits().get(0).PutMoney(in.nextFloat());
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
