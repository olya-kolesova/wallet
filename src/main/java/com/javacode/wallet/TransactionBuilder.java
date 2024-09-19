package com.javacode.wallet;

import com.javacode.wallet.model.Transaction;
import com.javacode.wallet.model.Wallet;

import java.util.Optional;

public class TransactionBuilder {

    private Optional<String> walletId;
    private Wallet wallet;
    private String operationType;
    private long amount;


    public TransactionBuilder() {
    }

    public Optional<String> getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = Optional.ofNullable(walletId);
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }


    public Transaction build() {
        return new Transaction(wallet, operationType, amount);
    }

}
