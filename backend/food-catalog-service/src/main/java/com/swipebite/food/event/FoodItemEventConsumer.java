package com.swipebite.food.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FoodItemEventConsumer {

    @KafkaListener(
        topics = "food-item-created",
        groupId = "food-catalog-group"
    )
    public void consumeFoodItemEvent(String message) {

        System.out.println("========== FOOD EVENT RECEIVED ==========");

        System.out.println("Event Details: " + message);

        System.out.println("=========================================");
    }
}