
package com.swipebite.notification.dto;

public class NotificationRequest {

    private Long userId;
    private String title;
    private String message;
    private String notificationType;
    private Long referenceId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }
}
