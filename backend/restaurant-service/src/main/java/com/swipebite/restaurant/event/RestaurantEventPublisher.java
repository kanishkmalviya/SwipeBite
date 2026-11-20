
package com.swipebite.restaurant.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipebite.restaurant.entity.Restaurant;

@Service
public class RestaurantEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "restaurant-created";

    public RestaurantEventPublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishRestaurantCreated(Restaurant restaurant) {

        RestaurantCreatedEvent event = new RestaurantCreatedEvent(
                restaurant.getId(),
                restaurant.getRestaurantName(),
                restaurant.getOwnerId(),
                restaurant.getEmail(),
                restaurant.getCreatedAt()
        );

        try {
            String eventMessage = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(TOPIC, eventMessage);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to create restaurant event", e);
        }
    }
}
