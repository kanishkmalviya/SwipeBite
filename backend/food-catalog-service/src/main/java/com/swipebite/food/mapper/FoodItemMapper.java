
package com.swipebite.food.mapper;

import org.springframework.stereotype.Component;

import com.swipebite.food.dto.FoodItemRequest;
import com.swipebite.food.dto.FoodItemResponse;
import com.swipebite.food.entity.FoodItem;

@Component
public class FoodItemMapper {

    public FoodItem toEntity(FoodItemRequest request) {

        FoodItem foodItem = new FoodItem();

        foodItem.setRestaurantId(request.getRestaurantId());
        foodItem.setFoodName(request.getFoodName());
        foodItem.setDescription(request.getDescription());
        foodItem.setPrice(request.getPrice());
        foodItem.setCategory(request.getCategory());
        foodItem.setImageUrl(request.getImageUrl());
        foodItem.setVegetarian(request.isVegetarian());
        foodItem.setAvailable(request.isAvailable());

        return foodItem;
    }

    public FoodItemResponse toResponse(FoodItem foodItem) {

        FoodItemResponse response = new FoodItemResponse();

        response.setId(foodItem.getId());
        response.setRestaurantId(foodItem.getRestaurantId());
        response.setFoodName(foodItem.getFoodName());
        response.setDescription(foodItem.getDescription());
        response.setPrice(foodItem.getPrice());
        response.setCategory(foodItem.getCategory());
        response.setImageUrl(foodItem.getImageUrl());
        response.setVegetarian(foodItem.isVegetarian());
        response.setAvailable(foodItem.isAvailable());
        response.setCreatedAt(foodItem.getCreatedAt());
        response.setUpdatedAt(foodItem.getUpdatedAt());

        return response;
    }
}
