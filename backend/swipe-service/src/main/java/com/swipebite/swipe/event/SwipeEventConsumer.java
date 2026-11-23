
package com.swipebite.swipe.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SwipeEventConsumer {

    @KafkaListener(
            topics = "swipe-created",
            groupId = "swipe-service-group"
    )
    public void consumeSwipeEvent(String message) {

        System.out.println("========== SWIPE EVENT RECEIVED ==========");
        System.out.println("Event Details: " + message);
        System.out.println("==========================================");
    }
}
