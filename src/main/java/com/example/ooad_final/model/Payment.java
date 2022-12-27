package com.example.ooad_final.model;

import com.example.ooad_final.request.PaymentPeriod;
import com.example.ooad_final.request.PaymentTariff;
import com.example.ooad_final.request.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Date paymentDate;
    private Long paymentSum;

    private Boolean isPaid;
    private int paymentTries;

    @ManyToOne
    @JoinColumn(name = "paymentInfoId")
    private PaymentInfo paymentInfo;

}
