package com.javacode.wallet;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean isOperationValid(String operation) {
        try {
            Transaction.Operation.valueOf(operation);
            return true;
        } catch (IllegalArgumentException | NullPointerException exception) {
            return false;
        }
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public UUID convertToUuid(String uuid) throws IllegalArgumentException {
        return UUID.fromString(uuid);
    }

    public Transaction findTransactionById(UUID walletId) {
        return transactionRepository.findById(walletId).orElseThrow(
            () -> new IllegalArgumentException("Transaction not found")
        );
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public void deleteAll() {
        transactionRepository.deleteAll();
    }

}
