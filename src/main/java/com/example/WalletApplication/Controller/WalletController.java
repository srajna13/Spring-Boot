package com.example.WalletApplication.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.WalletApplication.Entity.ErrorResponse;
import com.example.WalletApplication.Entity.Wallet;
import com.example.WalletApplication.Exception.WalletNotFoundException;
import com.example.WalletApplication.Service.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService){
        this.walletService=walletService;
    }

    @PostMapping("/createWallet")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Wallet> createWallet(@RequestParam String username) {
        Wallet savedWallet=walletService.createWallet(username);
        return ResponseEntity.ok(savedWallet);
    }

    @GetMapping("/allWallets")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Wallet> getAllWallets() {
        return walletService.getAllWallets();
    }

    @GetMapping("/getWalletById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Wallet getWallet(@PathVariable("id") Long id) {
        return walletService.getWalletById(id);
    }
    
    @PostMapping("/{id}/add")
    @PreAuthorize("hasRole('USER')")
    public Wallet addFunds(@PathVariable Long id, @RequestParam double amount) {
        return walletService.addFunds(id, amount);
    }

    @PostMapping("/{id}/pay")
    @PreAuthorize("hasRole('USER')")
    public Wallet makePayment(@PathVariable Long id, @RequestParam double amount) {
        return walletService.makePayment(id, amount);
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteWallet(@PathVariable Long id) {
        walletService.deleteWallet(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User wallet deleted successfully!");
        return ResponseEntity.ok(response);
    }
}
