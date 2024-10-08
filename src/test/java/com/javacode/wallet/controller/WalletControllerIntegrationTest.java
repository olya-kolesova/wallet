package com.javacode.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javacode.wallet.model.Wallet;
import jakarta.annotation.PostConstruct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @MockBean
    WalletController walletController;

    @Test
    public void givenWalletId_whenMakingGetRequest_thenReturnWallet() throws JsonProcessingException {
        Wallet testWallet = new Wallet(UUID.fromString("bbb70edb-15ab-420f-86f0-eeb1baa7d78b"), 1000);
        when(walletController.getWalletById(testWallet.getId().toString())).thenReturn(new ResponseEntity<>(testWallet, HttpStatus.OK));
        get(uri + "/api/v1/wallet/" + testWallet.getId()).then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(testWallet.getId().toString()))
            .body("balance", equalTo((int) testWallet.getBalance()))
            .body("transactions", equalTo(new ArrayList<>()));


        Wallet result = get(uri + "/api/v1/wallet/" + testWallet.getId()).then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(Wallet.class);
        assertThat(result).isEqualTo(testWallet);

        String responseString = get(uri + "/api/v1/wallet/" + testWallet.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        assertThat(responseString).isNotEmpty();
    }

}
