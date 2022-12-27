package com.example.ooad_final.service;

import com.example.ooad_final.model.Payment;
import com.example.ooad_final.model.PaymentInfo;
import com.example.ooad_final.repository.PaymentInfoRepository;
import com.example.ooad_final.repository.PaymentRepository;
import com.example.ooad_final.request.PaymentInfoRequest;
import com.example.ooad_final.request.PaymentPeriod;
import com.example.ooad_final.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    @Autowired
    PaymentInfoRepository paymentInfoRepository;
    @Autowired
    PaymentRepository paymentRepository;
    public boolean registerPayment(PaymentInfoRequest paymentInfoRequest) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentTariff(paymentInfoRequest.getPaymentTariff());
        paymentInfo.setPaymentPeriod(paymentInfoRequest.getPaymentPeriod());
        paymentInfo.setPaymentType(paymentInfoRequest.getPaymentType());
        paymentInfo.setCardNum(paymentInfo.getCardNum());
        paymentInfo.setIsBlocked(false);
        paymentInfoRepository.save(paymentInfo);
        return true;
    }

    public boolean payManual(PaymentRequest paymentRequest) {
        if (paymentRequest.getCardNum().length() < 16){
            unsuccessfulPay(paymentRequest);
            return false;
        }
        // if payment and card info is valid try to pay
        pay(paymentRequest);
        return paymentRepository.findById(paymentRequest.getPaymentId()).orElseThrow().getIsPaid();
    }

    public boolean payAutomatic(PaymentRequest paymentRequest) {
        // if we pay automatic we always have valid card info
        paymentRequest.setCardNum(paymentInfoRepository.findById(paymentRequest.getPaymentInfoId()).orElseThrow().getCardNum());
        pay(paymentRequest);
        return paymentRepository.findById(paymentRequest.getPaymentId()).orElseThrow().getIsPaid();
    }

    private void pay(PaymentRequest paymentRequest) {
        // pay with bank service
        //...

        // this is result from bank service about that operation
        // (hardcode that we could pay and result is true)
        boolean bankServiceIsPaid = true;

        if (bankServiceIsPaid){
            Payment payment = paymentRepository.findById(paymentRequest.getPaymentId()).orElseThrow();
            payment.setIsPaid(true);
            paymentRepository.save(payment);
        } else {
            unsuccessfulPay(paymentRequest);
        }
    }

    private void unsuccessfulPay(PaymentRequest paymentRequest) {
        Payment payment = paymentRepository.findById(paymentRequest.getPaymentId()).orElseThrow();
        payment.setPaymentTries(payment.getPaymentTries() + 1);
        payment.setIsPaid(false);
        if (payment.getPaymentTries() == 3) {
            PaymentInfo paymentInfo = paymentInfoRepository.findById(paymentRequest.getPaymentInfoId()).orElseThrow();
            paymentInfo.setIsBlocked(true);
            paymentInfoRepository.save(paymentInfo);
        }
        paymentRepository.save(payment);
    }

    // 8 o'clock of every day we manage a payments for clents by their tarriffs and periods.
    @Scheduled(cron = "0 0 8 * * *", zone = "Europe/Paris")
    private void createPayments() {
        for (PaymentInfo paymentInfo : paymentInfoRepository.findAll()) {
            if (paymentInfo.getPaymentPeriod() == period(paymentInfo.getRegisterDate(),new Date())) {
                createPayment(paymentInfo);
            }
            // check if this client has unpaid payment yesterday
            for (Payment payment : paymentInfo.getPayments()){
                if (PaymentPeriod.DAY == period(payment.getPaymentDate(), new Date())) {
                    payment.setPaymentTries(payment.getPaymentTries() + 1);
                    sendNotificationFor(paymentInfo);
                }
            }
        }
    }
    private void createPayment(PaymentInfo paymentInfo) {
        Payment payment = new Payment();
        payment.setPaymentInfo(paymentInfo);
        payment.setPaymentDate(new Date());
        payment.setPaymentSum(paymentInfo.getPaymentTariff().toValue());
        payment.setIsPaid(false);
        paymentRepository.save(payment);
        sendNotificationFor(payment.getPaymentInfo());
    }

    private void sendNotificationFor(PaymentInfo paymentInfo) {
        // send some notification for client that he has new payment due to his tariff
    }

    private PaymentPeriod period(Date registerDate, Date date) {
        long diffInMillies = Math.abs(date.getTime() - registerDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (diff > 365) {
            return PaymentPeriod.YEAR;
        }
        else if (diff > 30) {
            return PaymentPeriod.MONTH;
        } else if (diff == 1) {
            return PaymentPeriod.DAY;
        }
        return null;
    }
}
