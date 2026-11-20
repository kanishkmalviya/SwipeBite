
package com.swipebite.restaurant.event;

import java.time.LocalDateTime;

public class RestaurantCreatedEvent {

    private Long restaurantId;
    private String restaurantName;
    private Long ownerId;
    private String email;
    private LocalDateTime createdAt;

    public RestaurantCreatedEvent() {
    }

    public RestaurantCreatedEvent(Long restaurantId, String restaurantName,
            Long ownerId, String email, LocalDateTime createdAt) {

        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.ownerId = ownerId;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
