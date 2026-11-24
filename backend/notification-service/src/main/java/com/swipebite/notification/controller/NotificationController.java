
package com.swipebite.notification.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swipebite.notification.dto.NotificationRequest;
import com.swipebite.notification.dto.NotificationResponse;
import com.swipebite.notification.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @RequestBody NotificationRequest request) {

        return ResponseEntity.ok(
                notificationService.createNotification(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificationService.getNotificationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUserNotifications(userId));
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationResponse>> getUnreadNotifications(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUnreadNotifications(userId));
    }

 
    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationResponse> markAsRead(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificationService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);

        return ResponseEntity.ok("Notification deleted successfully");
    }
}
