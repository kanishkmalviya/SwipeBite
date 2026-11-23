
package com.swipebite.delivery.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DeliveryEventConsumer {

    @KafkaListener(
        topics = "delivery-updated",
        groupId = "delivery-service-group"
    )
    public void consumeDeliveryEvent(String message) {

        System.out.println("DELIVERY EVENT RECEIVED: " + message);
    }
}
