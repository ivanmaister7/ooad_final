package com.example.ooad_final.repository;

import com.example.ooad_final.model.Payment;
import com.example.ooad_final.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
