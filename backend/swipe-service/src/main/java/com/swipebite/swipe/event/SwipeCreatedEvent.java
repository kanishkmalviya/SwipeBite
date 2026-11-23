
package com.swipebite.swipe.event;

import java.time.LocalDateTime;

public class SwipeCreatedEvent {

    private Long swipeId;
    private Long customerId;
    private Long foodId;
    private Long restaurantId;
    private String swipeDirection;
    private LocalDateTime createdAt;

    public SwipeCreatedEvent() {
    }

    public SwipeCreatedEvent(Long swipeId, Long customerId,
            Long foodId, Long restaurantId,
            String swipeDirection, LocalDateTime createdAt) {

        this.swipeId = swipeId;
        this.customerId = customerId;
        this.foodId = foodId;
        this.restaurantId = restaurantId;
        this.swipeDirection = swipeDirection;
        this.createdAt = createdAt;
    }

    public Long getSwipeId() {
        return swipeId;
    }

    public void setSwipeId(Long swipeId) {
        this.swipeId = swipeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getSwipeDirection() {
        return swipeDirection;
    }

    public void setSwipeDirection(String swipeDirection) {
        this.swipeDirection = swipeDirection;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
