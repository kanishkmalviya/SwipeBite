
package com.swipebite.restaurant.mapper;

import org.springframework.stereotype.Component;

import com.swipebite.restaurant.dto.RestaurantRequest;
import com.swipebite.restaurant.dto.RestaurantResponse;
import com.swipebite.restaurant.entity.Restaurant;

@Component
public class RestaurantMapper {

    public Restaurant toEntity(RestaurantRequest request) {

        Restaurant restaurant = new Restaurant();

        restaurant.setRestaurantName(request.getRestaurantName());
        restaurant.setOwnerId(request.getOwnerId());
        restaurant.setEmail(request.getEmail());
        restaurant.setPhoneNumber(request.getPhoneNumber());
        restaurant.setCuisineType(request.getCuisineType());
        restaurant.setDescription(request.getDescription());

        return restaurant;
    }

    public RestaurantResponse toResponse(Restaurant restaurant) {

        RestaurantResponse response = new RestaurantResponse();

        response.setId(restaurant.getId());
        response.setRestaurantName(restaurant.getRestaurantName());
        response.setOwnerId(restaurant.getOwnerId());
        response.setEmail(restaurant.getEmail());
        response.setPhoneNumber(restaurant.getPhoneNumber());
        response.setCuisineType(restaurant.getCuisineType());
        response.setDescription(restaurant.getDescription());
        response.setStatus(restaurant.getStatus());
        response.setCreatedAt(restaurant.getCreatedAt());
        response.setUpdatedAt(restaurant.getUpdatedAt());

        return response;
    }
}
