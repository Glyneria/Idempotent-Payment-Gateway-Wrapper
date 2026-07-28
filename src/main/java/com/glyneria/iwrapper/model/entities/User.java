package com.glyneria.iwrapper.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Builder
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String ownerName;
    private String accountNumber;
    private double balance;

    public void setBalance(double balance) { this.balance = balance; }
    @Override
    public String toString() {
        return String.format("Account[id=%d, ownerName='%s', accountNumber='%s' balance='%.2f",
            id, ownerName, accountNumber, balance);
    }
    public UUID getId() { return id; }
    public String getOwnerName() { return ownerName; }
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
}
