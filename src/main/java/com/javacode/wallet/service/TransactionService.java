package com.javacode.wallet.service;

import com.javacode.wallet.model.Transaction;
import com.javacode.wallet.repository.TransactionRepository;
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

//    public boolean isAmountValid(long amount, long balance, String operation) {
//        if (operation.equals("WITHDRAW")) {
//            return amount <= balance && amount >= 0;
//        } else {
//            return amount >= balance;
//        }
//    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
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
