package com.javacode.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("api/v1/wallet") {
        public ResponseEntity<Object> requestTransaction(@RequestBody Transaction transaction) {
            transactionService.
            return new ResponseEntity<>(transactionCreated, HttpStatus.OK);
        }
    }
}
