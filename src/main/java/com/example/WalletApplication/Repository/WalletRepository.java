package com.example.WalletApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.WalletApplication.Entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet,Long>{
    
}