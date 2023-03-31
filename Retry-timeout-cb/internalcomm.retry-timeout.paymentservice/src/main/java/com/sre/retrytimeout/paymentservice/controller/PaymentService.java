package com.sre.retrytimeout.paymentservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.*;
@RestController
public class PaymentService {

    RestTemplate restTemplate=new RestTemplate();
final String PAYMENT_SERVICE = "PaymentService";

    final String bankingServiceURL="http://localhost:10181/getbalance";

    @GetMapping("/getbalance")
    @CircuitBreaker(name=PAYMENT_SERVICE,fallbackMethod = "fallbackGetBalance")
   // @Retry(name=PAYMENT_SERVICE,fallbackMethod="fallbackGetBalance")
   public ResponseEntity<String> getBalance() {

        System.out.println("Calling REST API getBalance of BankingService");

        String result=restTemplate.getForObject(bankingServiceURL,String.class);


        return ResponseEntity.status(HttpStatus.OK).body(
                "PaymentService getBalance succeeded.\n" +
                        " Response from Banking Service is ::: "+result);
    }

    //fall back handler
   public ResponseEntity<String>fallbackGetBalance(Exception  exception) {

        System.out.println("Fallback for get balance invoked due to exception ");

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                            .body("FAllback handler of PaymentService getBalance succeeded");
    }
}
