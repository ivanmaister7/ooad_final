package com.example.ooad_final.request;

import lombok.Data;

@Data
public class PaymentInfoRequest {
    private PaymentTariff paymentTariff;
    private PaymentPeriod paymentPeriod;
    private PaymentType paymentType;
    private String cardNum;
}
