
package com.swipebite.notification.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;

    @Column(length = 1000)
    private String message;

    private String notificationType;
    private Long referenceId;
    private boolean isRead = false;
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
