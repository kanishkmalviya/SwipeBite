
package com.swipebite.notification.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.swipebite.notification.dto.NotificationRequest;
import com.swipebite.notification.dto.NotificationResponse;
import com.swipebite.notification.entity.UserNotification;
import com.swipebite.notification.event.NotificationEventPublisher;
import com.swipebite.notification.mapper.NotificationMapper;
import com.swipebite.notification.repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationEventPublisher eventPublisher;

    public NotificationService(
            NotificationRepository notificationRepository,
            NotificationMapper notificationMapper,
            NotificationEventPublisher eventPublisher) {

        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.eventPublisher = eventPublisher;
    }

    public NotificationResponse createNotification(NotificationRequest request) {

        UserNotification notification = notificationMapper.toEntity(request);

        UserNotification saved =
                notificationRepository.save(notification);

        eventPublisher.publishNotificationEvent(saved);

        return notificationMapper.toResponse(saved);
    }

    public NotificationResponse getNotificationById(Long id) {

        UserNotification notification = notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));

        return notificationMapper.toResponse(notification);
    }

    public List<NotificationResponse> getUserNotifications(Long userId) {

        return notificationRepository.findByUserId(userId)
                .stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getUnreadNotifications(Long userId) {

        return notificationRepository.findByUserIdAndIsRead(userId, false)
                .stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public NotificationResponse markAsRead(Long id) {

        UserNotification notification = notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));

        notification.setRead(true);

        UserNotification updated =
                notificationRepository.save(notification);

        return notificationMapper.toResponse(updated);
    }

    public void deleteNotification(Long id) {

        UserNotification notification = notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));

        notificationRepository.delete(notification);
    }
}
