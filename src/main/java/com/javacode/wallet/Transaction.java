package com.javacode.wallet;

import jakarta.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long walletId;

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

}
