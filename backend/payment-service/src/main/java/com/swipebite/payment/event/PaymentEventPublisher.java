
package com.swipebite.payment.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipebite.payment.entity.Payment;

@Service
public class PaymentEventPublisher {

    private static final String TOPIC = "payment-updated";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PaymentEventPublisher(KafkaTemplate<String, String> kafkaTemplate,
                                 ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishPaymentEvent(Payment payment) {

        try {
            PaymentEvent event = new PaymentEvent(
                    payment.getId(),
                    payment.getOrderId(),
                    payment.getCustomerId(),
                    payment.getAmount(),
                    payment.getPaymentStatus()
            );

            String message = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(TOPIC, message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to publish payment event", e);
        }
    }
}
