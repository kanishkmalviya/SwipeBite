
package com.swipebite.food.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FoodItemCreatedEvent {

    private Long foodId;
    private Long restaurantId;
    private String foodName;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public FoodItemCreatedEvent() {
    }

    public FoodItemCreatedEvent(Long foodId, Long restaurantId,
                                String foodName, BigDecimal price,
                                LocalDateTime createdAt) {
        this.foodId = foodId;
        this.restaurantId = restaurantId;
        this.foodName = foodName;
        this.price = price;
        this.createdAt = createdAt;
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

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
