
package com.swipebite.delivery.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipebite.delivery.entity.Delivery;

@Service
public class DeliveryEventPublisher {

    private static final String TOPIC = "delivery-updated";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public DeliveryEventPublisher(KafkaTemplate<String, String> kafkaTemplate,
                                  ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishDeliveryEvent(Delivery delivery) {

        try {
            DeliveryEvent event = new DeliveryEvent(
                    delivery.getId(),
                    delivery.getOrderId(),
                    delivery.getCustomerId(),
                    delivery.getDeliveryPartnerId(),
                    delivery.getDeliveryStatus()
            );

            String message = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(TOPIC, message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to publish delivery event", e);
        }
    }
}
