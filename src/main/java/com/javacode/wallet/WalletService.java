package com.javacode.wallet;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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

    public void makeTransaction(Wallet wallet, Transaction transaction) {
        switch(transaction.getOperationType()) {
            case DEPOSIT:
                wallet.setBalance(wallet.getBalance() + transaction.getAmount());
            case WITHDRAW:
                wallet.setBalance(wallet.getBalance() - transaction.getAmount());
        }
        updateWallet(wallet);
    }

    public List<Wallet> getAllWallets() {
      return walletRepository.findAll();
    }



}
