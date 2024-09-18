package com.javacode.wallet.service;

import com.javacode.wallet.repository.WalletRepository;
import com.javacode.wallet.model.Transaction;
import com.javacode.wallet.model.Wallet;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class WalletService {
    private WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet saveWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public void updateWallet(Wallet wallet) {
        walletRepository.save(wallet);
    }


    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        return saveWallet(wallet);
    }

    public UUID convertToUuid(String uuid) throws IllegalArgumentException {
        return UUID.fromString(uuid);
    }

    public Wallet findWalletById(UUID uuid) throws NoSuchElementException {
        return walletRepository.findById(uuid).orElseThrow(
            () -> new NoSuchElementException("Wallet not found"));
    }

    @Transactional
    public void makeTransaction(Wallet wallet, Transaction transaction) {
        long newBalance;
        if (transaction.getOperationType().getOperation().equals("DEPOSIT")) {
            newBalance = wallet.getBalance() + transaction.getAmount();
            wallet.setBalance(newBalance);
        } else {
            newBalance = wallet.getBalance() - transaction.getAmount();
            wallet.setBalance(newBalance);
        }
        updateWallet(wallet);
    }

    public List<Wallet> getAllWallets() {
      return walletRepository.findAll();
    }



}
