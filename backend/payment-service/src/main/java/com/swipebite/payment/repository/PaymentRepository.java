
package com.swipebite.payment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swipebite.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCustomerId(Long customerId);

    Optional<Payment> findByOrderId(Long orderId);

    boolean existsByOrderId(Long orderId);
}