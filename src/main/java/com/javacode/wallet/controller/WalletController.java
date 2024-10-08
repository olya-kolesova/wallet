package com.javacode.wallet.controller;

import com.javacode.wallet.TransactionBuilder;
import com.javacode.wallet.TransactionResponse;
import com.javacode.wallet.model.Transaction;
import com.javacode.wallet.model.Wallet;
import com.javacode.wallet.service.TransactionService;
import com.javacode.wallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
public class WalletController {

    private final TransactionService transactionService;
    private final WalletService walletService;

    public WalletController(TransactionService transactionService, WalletService walletService) {
        this.transactionService = transactionService;
        this.walletService = walletService;
    }


    @GetMapping("api/v1/wallet/list")
    public ResponseEntity<List<Wallet>> getAllWallets(){
        List<Wallet> list = walletService.getAllWallets();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @PostMapping("api/v1/wallet")
    public ResponseEntity<Object> requestTransaction(@RequestBody TransactionBuilder transactionBuilder) {
        if (transactionService.isOperationValid(transactionBuilder.getOperationType())) {
            Optional<String> walletId = Optional.ofNullable(transactionBuilder.getWalletId());
            Transaction transaction = transactionBuilder.build();
           Transaction returnedTransaction = walletService.makeTransaction(transaction, walletId);
            transactionService.saveTransaction(transaction);
            Wallet wallet = returnedTransaction.getWallet();
            TransactionResponse transactionResponse = new TransactionResponse(wallet.getId().toString(),
                wallet.getBalance(), transaction.getAmount(), transaction.getOperationType().getOperation());
            return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("api/v1/wallet/{WALLET_UUID}")
    public ResponseEntity<Object> getWalletById(@PathVariable String WALLET_UUID) {
        try {
            UUID walletId = walletService.convertToUuid(WALLET_UUID);
            try {
                Wallet wallet = walletService.findWalletById(walletId);
                return new ResponseEntity<>(wallet, HttpStatus.OK);
            } catch (IllegalArgumentException exception) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("api/v1/transaction/list")
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
