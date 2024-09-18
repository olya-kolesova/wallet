package com.javacode.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("api/v1/wallet")
    public ResponseEntity<Object> requestTransaction(@RequestBody Transaction transaction) {
        transactionService.saveTransaction(transaction);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("api/v1/wallet/list")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> list = transactionService.getAllTransactions();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
