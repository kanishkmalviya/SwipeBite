
package com.swipebite.food.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swipebite.food.dto.FoodItemRequest;
import com.swipebite.food.dto.FoodItemResponse;
import com.swipebite.food.service.FoodItemService;

@RestController
@RequestMapping("/api/foods")
public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    // Add Food Item
    @PostMapping
    public ResponseEntity<FoodItemResponse> addFoodItem(
            @RequestBody FoodItemRequest request) {

        return ResponseEntity.ok(foodItemService.addFoodItem(request));
    }

    // Get Food Item By ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodItemResponse> getFoodById(
            @PathVariable Long id) {

        return ResponseEntity.ok(foodItemService.getFoodById(id));
    }

    // Get All Food Items
    @GetMapping
    public ResponseEntity<List<FoodItemResponse>> getAllFoodItems() {

        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }

    // Get Food Items By Restaurant ID
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodItemResponse>> getFoodByRestaurantId(
            @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                foodItemService.getFoodByRestaurantId(restaurantId));
    }

    // Update Food Item
    @PutMapping("/{id}")
    public ResponseEntity<FoodItemResponse> updateFoodItem(
            @PathVariable Long id,
            @RequestBody FoodItemRequest request) {

        return ResponseEntity.ok(
                foodItemService.updateFoodItem(id, request));
    }

    // Delete Food Item
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoodItem(
            @PathVariable Long id) {

        foodItemService.deleteFoodItem(id);

        return ResponseEntity.ok("Food item deleted successfully");
    }

    // Update Food Availability
    @PatchMapping("/{id}/availability")
    public ResponseEntity<FoodItemResponse> updateAvailability(
            @PathVariable Long id,
            @RequestParam boolean available) {

        return ResponseEntity.ok(
                foodItemService.updateAvailability(id, available));
    }
}
