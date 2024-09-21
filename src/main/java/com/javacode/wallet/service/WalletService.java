package com.javacode.wallet.service;

import com.javacode.wallet.repository.WalletRepository;
import com.javacode.wallet.model.Transaction;
import com.javacode.wallet.model.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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

    public boolean isAmountValid(Transaction transaction, Wallet wallet) {
        if (transaction.getOperationType().getOperation().equals("WITHDRAW")) {
            return transaction.getAmount() <= wallet.getBalance() && transaction.getAmount() >= 0;
        } else {
            return transaction.getAmount() > 0;
        }
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

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Transaction makeTransaction(Transaction transaction, Optional<String> walletId) throws IllegalArgumentException, NullPointerException {
        long newBalance;
        Wallet wallet = null;
        System.out.println("mistake before find");
        wallet = walletId.map(s -> (walletRepository.findById(convertToUuid(s))).orElseThrow()).orElseGet(this::createWallet);
        if (isAmountValid(transaction, wallet)) {
            switch(transaction.getOperationType().getOperation()) {
                case "DEPOSIT":
                    newBalance = wallet.getBalance() + transaction.getAmount();
                    wallet.setBalance(newBalance);
                    break;
                case "WITHDRAW":
                    newBalance = wallet.getBalance() - transaction.getAmount();
                    wallet.setBalance(newBalance);
                    break;
            }
            transaction.setWallet(wallet);
            wallet.addTransaction(transaction);
            System.out.println("mistake before saving");
            walletRepository.saveAndFlush(wallet);
            return transaction;
        } else {
            throw new IllegalArgumentException("Invalid transaction");
        }
    }

    public List<Wallet> getAllWallets() {
      return walletRepository.findAll();
    }



}
