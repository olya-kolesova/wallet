package com.javacode.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Controller
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("api/v1/wallet")
    public ResponseEntity<Object> requestTransaction(@RequestBody TransactionDto transactionDto) {
        if (transactionService.isOperationValid(transactionDto.getOperationType())) {
            Transaction transaction = new Transaction(transactionDto.getOperationType(), transactionDto.getAmount());
            Transaction savedTransaction = transactionService.saveTransaction(transaction);
            return new ResponseEntity<>(savedTransaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("api/v1/wallet/{WALLET_UUID}")
    public ResponseEntity<Object> getTransactionById(@PathVariable String WALLET_UUID) {
        try {
            UUID walletId = transactionService.convertToUuid(WALLET_UUID);
            try {
                Transaction transaction = transactionService.findTransactionById(walletId);
                return new ResponseEntity<>(transaction, HttpStatus.OK);
            } catch (IllegalArgumentException exception) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("api/v1/wallet/list")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> list = transactionService.getAllTransactions();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("api/v1/wallet")
    public ResponseEntity<Object> deleteAllTransactions(){
        transactionService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
