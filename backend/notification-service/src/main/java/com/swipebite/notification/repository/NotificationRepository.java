
package com.swipebite.notification.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.swipebite.notification.entity.UserNotification;

public interface NotificationRepository
        extends JpaRepository<UserNotification, Long> {

    List<UserNotification> findByUserId(Long userId);

    List<UserNotification> findByUserIdAndIsRead(
            Long userId, boolean isRead);
}
