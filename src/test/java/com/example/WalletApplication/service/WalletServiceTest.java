package com.example.WalletApplication.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
// import static org.junit.jupiter.api.Assertions.*;

// import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import com.example.WalletApplication.Entity.Wallet;
import com.example.WalletApplication.Repository.WalletRepository;
import com.example.WalletApplication.Service.WalletService;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    // creating real object for test not feasible, rather create a mock object
    @Mock
    WalletRepository walletRepository;

    @InjectMocks
    WalletService walletService;

    @Test
    void createWalletTest(){
        String username="testuser";
        Wallet wallet= new Wallet(username);

        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);
        
        Wallet createdWallet = walletService.createWallet(username);

        // test wallet == wallet
        assertNotNull(createdWallet);
        assertEquals(username, createdWallet.getUsername());
        assertEquals(0.0, createdWallet.getBalance());
    }

    @Test
    void canMakePaymentTest(){
        Long walletId=1L;
        Wallet wallet= new Wallet("testuser");
        wallet.setBalance(1000.0);

        Mockito.when(walletRepository.existsById(walletId)).thenReturn(true);
        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet=walletService.makePayment(walletId,300.0);

        assertEquals(700.0, updatedWallet.getBalance());
    }

    @Test
    void canAddFunds(){
        Long walletId=1L;
        Wallet wallet= new Wallet("testuser");
        // wallet.setBalance(1000.0);

        // Mockito.when(walletRepository.existsById(walletId)).thenReturn(true);
        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet=walletService.addFunds(walletId,500.0);

        assertEquals(500.0, updatedWallet.getBalance());
    }
}
