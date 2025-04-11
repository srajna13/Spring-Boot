package com.example.WalletApplication.Service;

// import org.springframework.beans.factory.annotation.Autowired;

import com.example.WalletApplication.Repository.WalletRepository;

import jakarta.persistence.EntityNotFoundException;

import com.example.WalletApplication.Entity.Wallet;
import com.example.WalletApplication.Exception.InsufficientBalanceException;
import com.example.WalletApplication.Exception.WalletNotFoundException;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WalletService {
    
    // Field injection:
    // @Autowired
    private final WalletRepository walletRepository;

    // constructor injection:
    public WalletService(WalletRepository walletRepository){
        this.walletRepository=walletRepository;
    }

    public Wallet createWallet(String username){
        return walletRepository.save(new Wallet(username));
    }

    // public Wallet getWalletByIdByName(String username) {
    //     return walletRepository.getWalletByName(username);
    // }

    public Wallet getWalletById(Long id) {
        return walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("Wallet not found for user with Id: " + id));
    }

    public List<Wallet> getAllWallets(){
        return walletRepository.findAll();
    }


    public Wallet addFunds(Long walletId, double amount) {
        Wallet wallet = getWalletById(walletId);
        wallet.addFunds(amount);
        return walletRepository.save(wallet);
    }

    public Wallet makePayment(Long walletId, double amount) {
        if (!walletRepository.existsById(walletId)) {
            throw new WalletNotFoundException("Wallet not found with id: " + walletId);
        }
        Wallet wallet = getWalletById(walletId);
        wallet.makePayment(amount);
        return walletRepository.save(wallet);
    }

    public void deleteWallet(Long walletId){
        if (!walletRepository.existsById(walletId)) {
            throw new WalletNotFoundException("Wallet not found with id: " + walletId);
        }
        walletRepository.deleteById(walletId);
    }
}
