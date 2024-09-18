package com.javacode.wallet;

public record TransactionResponse(String walletId, long walletBalance, long amount, String operationType) {
}
