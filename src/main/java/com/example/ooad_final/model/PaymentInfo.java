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
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentInfoId;

    private Date registerDate;
    private PaymentTariff paymentTariff;
    private PaymentPeriod paymentPeriod;
    private PaymentType paymentType;
    @Column(nullable = true)
    private String cardNum;
    private Boolean isBlocked;

    @OneToMany(mappedBy = "paymentInfo", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<Payment> payments = new ArrayList<>();

}
