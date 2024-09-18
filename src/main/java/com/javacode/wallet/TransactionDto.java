package com.javacode.wallet;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class TransactionDto {
    private String operationType;
    private long amount;


    public TransactionDto(String operationType, long amount) {
        this.operationType = operationType;
        this.amount = amount;
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


}
