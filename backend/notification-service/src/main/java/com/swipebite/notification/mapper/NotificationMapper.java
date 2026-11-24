
package com.swipebite.notification.mapper;

import org.springframework.stereotype.Component;

import com.swipebite.notification.dto.NotificationRequest;
import com.swipebite.notification.dto.NotificationResponse;
import com.swipebite.notification.entity.UserNotification;

@Component
public class NotificationMapper {

    public UserNotification toEntity(NotificationRequest request) {

        UserNotification notification = new UserNotification();

        notification.setUserId(request.getUserId());
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setNotificationType(request.getNotificationType());
        notification.setReferenceId(request.getReferenceId());

        return notification;
    }

    public NotificationResponse toResponse(UserNotification notification) {

        NotificationResponse response = new NotificationResponse();

        response.setId(notification.getId());
        response.setUserId(notification.getUserId());
        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());
        response.setNotificationType(notification.getNotificationType());
        response.setReferenceId(notification.getReferenceId());
        response.setRead(notification.isRead());
        response.setCreatedAt(notification.getCreatedAt());

        return response;
    }
}
