
package com.swipebite.swipe.dto;

public class SwipeRequest {

    private Long customerId;
    private Long foodId;
    private Long restaurantId;
    private String swipeDirection;

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
}
