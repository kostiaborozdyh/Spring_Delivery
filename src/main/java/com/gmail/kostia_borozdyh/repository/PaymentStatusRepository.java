package com.gmail.kostia_borozdyh.repository;

import com.gmail.kostia_borozdyh.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Integer> {
    PaymentStatus getPaymentStatusById(Integer id);
}
