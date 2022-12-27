package com.example.ooad_final.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long paymentInfoId;
    private Long paymentId;
    private PaymentType paymentType;
    private String cardNum;
}
