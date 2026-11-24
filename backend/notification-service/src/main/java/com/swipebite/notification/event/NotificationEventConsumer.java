
package com.swipebite.notification.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swipebite.notification.dto.NotificationRequest;
import com.swipebite.notification.service.NotificationService;

@Service
public class NotificationEventConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public NotificationEventConsumer(
            NotificationService notificationService,
            ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order-created",
            groupId = "notification-service-group")
    public void consumeOrderEvent(String message) {
        processEvent(message, "Order Placed",
                "Your order has been placed successfully",
                "ORDER");
    }

    @KafkaListener(topics = "payment-updated",
            groupId = "notification-service-group")
    public void consumePaymentEvent(String message) {
        processEvent(message, "Payment Updated",
                "Your payment status has been updated",
                "PAYMENT");
    }

    @KafkaListener(topics = "delivery-updated",
            groupId = "notification-service-group")
    public void consumeDeliveryEvent(String message) {
        processEvent(message, "Delivery Updated",
                "Your delivery status has been updated",
                "DELIVERY");
    }

    private void processEvent(String message, String title,
            String notificationMessage, String type) {

        try {
            JsonNode node = objectMapper.readTree(message);

            JsonNode customerNode = node.get("customerId");

            if (customerNode == null || customerNode.isNull()) {
                return;
            }

            Long userId = customerNode.asLong();

            Long referenceId = null;

            if (node.hasNonNull("orderId")) {
                referenceId = node.get("orderId").asLong();
            } else if (node.hasNonNull("paymentId")) {
                referenceId = node.get("paymentId").asLong();
            } else if (node.hasNonNull("deliveryId")) {
                referenceId = node.get("deliveryId").asLong();
            }

            NotificationRequest request = new NotificationRequest();

            request.setUserId(userId);
            request.setTitle(title);
            request.setMessage(notificationMessage);
            request.setNotificationType(type);
            request.setReferenceId(referenceId);

            notificationService.createNotification(request);

            System.out.println("Notification created for user: " + userId);

        } catch (Exception e) {
            System.out.println("Error processing notification event: "
                    + e.getMessage());
        }
    }
}
