package com.example.ooad_final.service;

import com.example.ooad_final.model.Payment;
import com.example.ooad_final.model.PaymentInfo;
import com.example.ooad_final.request.PaymentInfoRequest;
import com.example.ooad_final.request.PaymentPeriod;
import com.example.ooad_final.request.PaymentRequest;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public interface PaymentService {

    boolean registerPayment(PaymentInfoRequest paymentInfoRequest);

    boolean payManual(PaymentRequest paymentRequest);

    boolean payAutomatic(PaymentRequest paymentRequest);

    void createPayments();
}
