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

}
