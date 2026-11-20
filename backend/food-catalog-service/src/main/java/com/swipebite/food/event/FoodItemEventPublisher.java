
package com.swipebite.food.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipebite.food.entity.FoodItem;

@Service
public class FoodItemEventPublisher {

    private static final String TOPIC = "food-item-created";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public FoodItemEventPublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishFoodItemCreated(FoodItem foodItem) {

        try {

            FoodItemCreatedEvent event = new FoodItemCreatedEvent(
                    foodItem.getId(),
                    foodItem.getRestaurantId(),
                    foodItem.getFoodName(),
                    foodItem.getPrice(),
                    foodItem.getCreatedAt()
            );

            String eventMessage = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(TOPIC, eventMessage);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    "Unable to publish food item event", e);
        }
    }
}
