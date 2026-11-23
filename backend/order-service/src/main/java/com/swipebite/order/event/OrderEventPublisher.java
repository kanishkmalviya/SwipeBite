
package com.swipebite.order.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipebite.order.entity.Order;

@Service
public class OrderEventPublisher {

    private static final String TOPIC = "order-created";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderEventPublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishOrderCreated(Order order) {

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getCustomerId(),
                order.getRestaurantId(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getCreatedAt()
        );

        try {

            String message = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(TOPIC, message);

            System.out.println("Order Created Event Published");

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    "Unable to publish order event", e);
        }
    }
}
