package com.javacode.wallet;

import jakarta.persistence.*;
import java.util.UUID;


@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID walletId;

    @Enumerated(EnumType.STRING)
    private Operation operationType;

    private Long amount;


    public enum Operation {
        DEPOSIT("DEPOSIT"),
        WITHDRAW("WITHDRAW");

        private final String operation;

        Operation(String operation) {
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }
    }

    public Transaction() {}

    public Transaction(String operation, Long amount) {
        this.operationType = Operation.valueOf(operation);
        this.amount = amount;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public Operation getOperationType() {
        return operationType;
    }

    public void setOperationType(Operation operationType) {
        this.operationType = operationType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
