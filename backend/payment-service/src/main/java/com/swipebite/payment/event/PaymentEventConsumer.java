
package com.swipebite.payment.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventConsumer {

    @KafkaListener(
        topics = "payment-updated",
        groupId = "payment-service-group"
    )
    public void consumePaymentEvent(String message) {

        System.out.println("PAYMENT EVENT RECEIVED: " + message);
    }
}
