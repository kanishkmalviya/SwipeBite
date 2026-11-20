
package com.swipebite.restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swipebite.restaurant.dto.RestaurantRequest;
import com.swipebite.restaurant.dto.RestaurantResponse;
import com.swipebite.restaurant.entity.Restaurant;
import com.swipebite.restaurant.mapper.RestaurantMapper;
import com.swipebite.restaurant.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    public RestaurantController(RestaurantService restaurantService,
                                 RestaurantMapper restaurantMapper) {
        this.restaurantService = restaurantService;
        this.restaurantMapper = restaurantMapper;
    }

    // Register Restaurant
    @PostMapping("/register")
    public ResponseEntity<RestaurantResponse> registerRestaurant(
            @RequestBody RestaurantRequest request) {

        Restaurant restaurant = restaurantMapper.toEntity(request);

        Restaurant savedRestaurant =
                restaurantService.registerRestaurant(restaurant);

        return ResponseEntity.ok(
                restaurantMapper.toResponse(savedRestaurant));
    }

    // Get Restaurant By ID
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(
            @PathVariable Long id) {

        Restaurant restaurant = restaurantService.getRestaurantById(id);

        return ResponseEntity.ok(restaurantMapper.toResponse(restaurant));
    }

    // Get All Restaurants
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {

        List<RestaurantResponse> response =
                restaurantService.getAllRestaurants()
                .stream()
                .map(restaurantMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // Update Restaurant
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantRequest request) {

        Restaurant updatedRestaurant =
                restaurantMapper.toEntity(request);

        Restaurant savedRestaurant =
                restaurantService.updateRestaurant(id, updatedRestaurant);

        return ResponseEntity.ok(
                restaurantMapper.toResponse(savedRestaurant));
    }

    // Delete Restaurant
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(
            @PathVariable Long id) {

        restaurantService.deleteRestaurant(id);

        return ResponseEntity.ok("Restaurant deleted successfully");
    }
}
