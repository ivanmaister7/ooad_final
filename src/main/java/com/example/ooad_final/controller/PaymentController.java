package com.example.ooad_final.controller;

import com.example.ooad_final.request.PaymentInfoRequest;
import com.example.ooad_final.request.PaymentRequest;
import com.example.ooad_final.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPaymentStuff(@RequestBody PaymentInfoRequest paymentInfoRequest) {
        return paymentService.registerPayment(paymentInfoRequest) ?
                new ResponseEntity<>("Ok", HttpStatus.CREATED) :
                new ResponseEntity<>("Can't create Payment for that credentials", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/pay/manual")
    public ResponseEntity<?> payManual(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.payManual(paymentRequest) ?
                new ResponseEntity<>("Ok", HttpStatus.CREATED) :
                new ResponseEntity<>("Can't pay manual for that credentials", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/pay/automatic")
    public ResponseEntity<?> payAutomatic(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.payAutomatic(paymentRequest) ?
                new ResponseEntity<>("Ok", HttpStatus.CREATED) :
                new ResponseEntity<>("Can't pay automatic for that credentials", HttpStatus.BAD_REQUEST);
    }


}
