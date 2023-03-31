package com.sre.externalservices.mambu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MambuService {


    @GetMapping("/getbalance")
    ResponseEntity<String> getBalance() {

        return ResponseEntity.status(HttpStatus.OK)
                .body("MambuService getbalance succeeded");
    }

}
