
package com.swipebite.payment.mapper;

import org.springframework.stereotype.Component;

import com.swipebite.payment.dto.PaymentRequest;
import com.swipebite.payment.dto.PaymentResponse;
import com.swipebite.payment.entity.Payment;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequest request) {

        Payment payment = new Payment();

        payment.setOrderId(request.getOrderId());
        payment.setCustomerId(request.getCustomerId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus("PENDING");

        return payment;
    }

    public PaymentResponse toResponse(Payment payment) {

        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getId());
        response.setOrderId(payment.getOrderId());
        response.setCustomerId(payment.getCustomerId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setTransactionId(payment.getTransactionId());
        response.setCreatedAt(payment.getCreatedAt());
        response.setUpdatedAt(payment.getUpdatedAt());

        return response;
    }
}
