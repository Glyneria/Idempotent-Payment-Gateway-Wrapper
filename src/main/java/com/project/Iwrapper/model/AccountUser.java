package com.project.Iwrapper.model;

import jakarta.persistence.*;

@Entity
public class AccountUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ownerName;
    private String accountNumber;
    private double balance;

    protected AccountUser() {}

    public AccountUser(String ownerName, String accountNumber, double balance) {
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("Account[id=%d, ownerName='%s', accountNumber='%s' balance='%.2f",
            id, ownerName, accountNumber, balance);
    }

    public Long getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}
