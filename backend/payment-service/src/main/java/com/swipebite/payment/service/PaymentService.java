
package com.swipebite.payment.service;

import java.util.List;
import java.util.UUID;
import com.swipebite.payment.event.PaymentEventPublisher;

import org.springframework.stereotype.Service;

import com.swipebite.payment.dto.PaymentRequest;
import com.swipebite.payment.dto.PaymentResponse;
import com.swipebite.payment.entity.Payment;
import com.swipebite.payment.exception.ResourceNotFoundException;
import com.swipebite.payment.mapper.PaymentMapper;
import com.swipebite.payment.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;


private final PaymentEventPublisher paymentEventPublisher;

public PaymentService(PaymentRepository paymentRepository,
                      PaymentMapper paymentMapper,
                      PaymentEventPublisher paymentEventPublisher) {

    this.paymentRepository = paymentRepository;
    this.paymentMapper = paymentMapper;
    this.paymentEventPublisher = paymentEventPublisher;
}


    public PaymentResponse createPayment(PaymentRequest request) {

        if (request.getOrderId() == null || request.getCustomerId() == null) {
            throw new IllegalArgumentException("Order ID and Customer ID are required");
        }

        if (request.getAmount() == null || request.getAmount().signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (paymentRepository.existsByOrderId(request.getOrderId())) {
            throw new IllegalArgumentException("Payment already exists for this order");
        }

        Payment payment = paymentMapper.toEntity(request);
        Payment savedPayment = paymentRepository.save(payment);
        

        paymentEventPublisher.publishPaymentEvent(savedPayment);

        return paymentMapper.toResponse(savedPayment);
        

    }

    public PaymentResponse getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        return paymentMapper.toResponse(payment);
    }

    public PaymentResponse getPaymentByOrderId(Long orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        return paymentMapper.toResponse(payment);
    }

    public List<PaymentResponse> getCustomerPayments(Long customerId) {

        return paymentRepository.findByCustomerId(customerId)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    public PaymentResponse updatePaymentStatus(Long id, String status) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        if (!status.equals("SUCCESS") && !status.equals("FAILED")) {
            throw new IllegalArgumentException("Status must be SUCCESS or FAILED");
        }

        payment.setPaymentStatus(status);

        if (status.equals("SUCCESS")) {
            payment.setTransactionId(UUID.randomUUID().toString());
        }

        Payment updatedPayment = paymentRepository.save(payment);


        paymentEventPublisher.publishPaymentEvent(updatedPayment);

        return paymentMapper.toResponse(updatedPayment);
        
        
           }
}
