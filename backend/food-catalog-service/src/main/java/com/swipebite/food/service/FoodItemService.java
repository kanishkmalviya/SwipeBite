
package com.swipebite.food.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.swipebite.food.event.FoodItemEventPublisher;
import com.swipebite.food.dto.FoodItemRequest;
import com.swipebite.food.dto.FoodItemResponse;
import com.swipebite.food.entity.FoodItem;
import com.swipebite.food.mapper.FoodItemMapper;
import com.swipebite.food.repository.FoodItemRepository;

@Service
public class FoodItemService {

	private final FoodItemRepository foodItemRepository;
	private final FoodItemMapper foodItemMapper;
	private final FoodItemEventPublisher foodItemEventPublisher;

	public FoodItemService(
	        FoodItemRepository foodItemRepository,
	        FoodItemMapper foodItemMapper,
	        FoodItemEventPublisher foodItemEventPublisher) {

	    this.foodItemRepository = foodItemRepository;
	    this.foodItemMapper = foodItemMapper;
	    this.foodItemEventPublisher = foodItemEventPublisher;
	}
	
	
	public FoodItemResponse addFoodItem(FoodItemRequest request) {

	    FoodItem foodItem = foodItemMapper.toEntity(request);

	    FoodItem savedFood = foodItemRepository.save(foodItem);

	    foodItemEventPublisher.publishFoodItemCreated(savedFood);

	    return foodItemMapper.toResponse(savedFood);
	}
    // Get Food Item By ID
    public FoodItemResponse getFoodById(Long id) {

        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        return foodItemMapper.toResponse(foodItem);
    }

    // Get All Food Items
    public List<FoodItemResponse> getAllFoodItems() {

        return foodItemRepository.findAll()
                .stream()
                .map(foodItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get Food Items By Restaurant ID
    public List<FoodItemResponse> getFoodByRestaurantId(Long restaurantId) {

        return foodItemRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(foodItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Update Food Item
    public FoodItemResponse updateFoodItem(Long id, FoodItemRequest request) {

        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        foodItem.setRestaurantId(request.getRestaurantId());
        foodItem.setFoodName(request.getFoodName());
        foodItem.setDescription(request.getDescription());
        foodItem.setPrice(request.getPrice());
        foodItem.setCategory(request.getCategory());
        foodItem.setImageUrl(request.getImageUrl());
        foodItem.setVegetarian(request.isVegetarian());
        foodItem.setAvailable(request.isAvailable());

        FoodItem updatedFood = foodItemRepository.save(foodItem);

        return foodItemMapper.toResponse(updatedFood);
    }

    // Delete Food Item
    public void deleteFoodItem(Long id) {

        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        foodItemRepository.delete(foodItem);
    }

    // Update Food Availability
    public FoodItemResponse updateAvailability(Long id, boolean available) {

        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        foodItem.setAvailable(available);

        FoodItem updatedFood = foodItemRepository.save(foodItem);

        return foodItemMapper.toResponse(updatedFood);
    }
}
