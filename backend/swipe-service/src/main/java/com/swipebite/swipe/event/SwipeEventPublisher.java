
package com.swipebite.swipe.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipebite.swipe.entity.Swipe;

@Service
public class SwipeEventPublisher {

    private static final String TOPIC = "swipe-created";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public SwipeEventPublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishSwipeCreated(Swipe swipe) {

        SwipeCreatedEvent event = new SwipeCreatedEvent(
                swipe.getId(),
                swipe.getCustomerId(),
                swipe.getFoodId(),
                swipe.getRestaurantId(),
                swipe.getSwipeDirection(),
                swipe.getCreatedAt()
        );

        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, message)
            .whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("SWIPE EVENT PUBLISHED SUCCESSFULLY: " + message);
                } else {
                    System.out.println("SWIPE EVENT FAILED: " + ex.getMessage());
                }
            });

        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Unable to publish swipe event", e);
        }
    }
}
