package com.javacode.wallet;

public class TransactionBuilder {

    private String walletId;
    private Wallet wallet;
    private String operationType;
    private long amount;


    private TransactionBuilder() {
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
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


    Transaction build() {
        return new Transaction(wallet, operationType, amount);
    }


}
