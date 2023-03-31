package com.sre.retrytimeout.bankingservice.controller;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;

@RestController
public class BankingService {

    final String SERVICE_NAME="BankingService";
    RestTemplate restTemplate=new RestTemplate();
    final String mambuServiceURL="http://localhost:8000/getbalance";
    @GetMapping("/getbalance")
    @Retry(name = SERVICE_NAME,fallbackMethod = "fallbackGetBalance")
    ResponseEntity<String> getBalance() {

        String externalReturn=restTemplate.getForObject(mambuServiceURL,String.class);

        System.out.println("BankingService: get Balance processing. ");

        return ResponseEntity.status(HttpStatus.OK)
                .body("BankingService getbalance succeeded. \n" +
                        " The response from Mambu is ::::"+externalReturn);
    }

    public ResponseEntity<String>fallbackGetBalance(Exception  exception) {

        System.out.println("Fallback for get balance invoked due to exception ");

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .body("FAllback handler of BankingService getBalance succeeded");
    }
}
