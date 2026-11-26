
package com.swipebite.order.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipebite.order.entity.Order;
import com.swipebite.order.repository.OrderRepository;

@Service
public class PaymentEventConsumer {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    public PaymentEventConsumer(
            OrderRepository orderRepository,
            ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
        topics = "payment-updated",
        groupId = "order-service-group"
    )
    public void consumePaymentEvent(String message) {
        System.out.println("ORDER SERVICE RECEIVED EVENT: " + message);

        try {
            PaymentEvent event = objectMapper.readValue(
                    message, PaymentEvent.class);

            Order order = orderRepository.findById(event.getOrderId())
                    .orElseThrow(() ->
                        new RuntimeException("Order not found"));

            order.setPaymentStatus(event.getPaymentStatus());
            
                       

            orderRepository.save(order);

            System.out.println(
                "Order payment status updated: "
                + event.getOrderId()
                + " -> "
                + event.getPaymentStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
