
package com.swipebite.order.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @KafkaListener(
        topics = "order-created",
        groupId = "order-service-group"
    )
    public void consumeOrderEvent(String message) {

        System.out.println("========== ORDER EVENT RECEIVED ==========");

        System.out.println("Event Details: " + message);

        System.out.println("==========================================");
    }
}
