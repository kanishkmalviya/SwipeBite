
package com.swipebite.notification.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipebite.notification.entity.UserNotification;

@Service
public class NotificationEventPublisher {

    private static final String TOPIC = "notification-created";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public NotificationEventPublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishNotificationEvent(UserNotification notification) {

        try {
            NotificationEvent event = new NotificationEvent(
                    notification.getUserId(),
                    notification.getTitle(),
                    notification.getMessage(),
                    notification.getNotificationType(),
                    notification.getReferenceId()
            );

            String message = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(TOPIC, message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Error publishing notification event", e);
        }
    }
}
