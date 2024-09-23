package com.javacode.wallet.service;

import com.javacode.wallet.repository.WalletRepository;
import com.javacode.wallet.model.Transaction;
import com.javacode.wallet.model.Wallet;
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
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
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

    @Transactional
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        return saveWallet(wallet);
    }

    public UUID convertToUuid(String uuid) throws IllegalArgumentException {
        return UUID.fromString(uuid);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Wallet findWalletById(UUID uuid) throws NoSuchElementException {
        return walletRepository.findById(uuid).orElseThrow(
            () -> new NoSuchElementException("Wallet not found"));
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Transaction makeTransaction(Transaction transaction, Optional<String> walletId) throws IllegalArgumentException, NoSuchElementException {
        long newBalance;
        Wallet wallet = null;
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
            walletRepository.save(wallet);
            return transaction;
        } else {
            throw new IllegalArgumentException("Invalid transaction");
        }
    }

    public List<Wallet> getAllWallets() {
      return walletRepository.findAll();
    }

}
