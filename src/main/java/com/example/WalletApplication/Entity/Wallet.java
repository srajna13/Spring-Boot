package com.example.WalletApplication.Entity;

import java.util.*;

import com.example.WalletApplication.Exception.InsufficientBalanceException;

import jakarta.persistence.CascadeType;

// import javax.management.RuntimeErrorException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private double balance=0.0;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transactions> transactions=new ArrayList<>();

    public Wallet(){};
    public Wallet(String username){
        this.username=username;
        this.balance=0.0;
    }

    public void addFunds(double amount){
        this.balance+=amount;
        // this.type=
        Transactions t = new Transactions("Added", amount,this);
        t.setWallet(this); 
        transactions.add(t);
    }

    public void makePayment(double amount) throws RuntimeException{
        if(amount>balance){
            throw new InsufficientBalanceException("Insufficient Balance to perform this transaction!");
        }

        this.balance-=amount;
        transactions.add(new Transactions("Paid", amount, this));
    }
}