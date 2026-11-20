
package com.swipebite.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swipebite.restaurant.exception.ResourceNotFoundException;
import com.swipebite.restaurant.exception.DuplicateResourceException;
import com.swipebite.restaurant.entity.Restaurant;
import com.swipebite.restaurant.repository.RestaurantAddressRepository;
import com.swipebite.restaurant.repository.RestaurantHoursRepository;
import com.swipebite.restaurant.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantAddressRepository addressRepository;
    private final RestaurantHoursRepository hoursRepository;

    public RestaurantService(
            RestaurantRepository restaurantRepository,
            RestaurantAddressRepository addressRepository,
            RestaurantHoursRepository hoursRepository) {

        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.hoursRepository = hoursRepository;
    }

    public Restaurant registerRestaurant(Restaurant restaurant) {

        if (restaurantRepository.existsByEmail(restaurant.getEmail())) {
            throw new DuplicateResourceException("Restaurant email already exists");
        }

        if (restaurantRepository.existsByPhoneNumber(restaurant.getPhoneNumber())) {
            throw  new DuplicateResourceException("Restaurant phone number already exists");
        }

        restaurant.setStatus("PENDING");

        return restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantById(Long id) {

        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {

        Restaurant restaurant = getRestaurantById(id);

        if (!restaurant.getEmail().equals(updatedRestaurant.getEmail())
                && restaurantRepository.existsByEmail(updatedRestaurant.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (!restaurant.getPhoneNumber().equals(updatedRestaurant.getPhoneNumber())
                && restaurantRepository.existsByPhoneNumber(updatedRestaurant.getPhoneNumber())) {
        throw 	new DuplicateResourceException("Phone number already exists");  
        }

        restaurant.setRestaurantName(updatedRestaurant.getRestaurantName());
        restaurant.setEmail(updatedRestaurant.getEmail());
        restaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
        restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        restaurant.setDescription(updatedRestaurant.getDescription());

        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {

        Restaurant restaurant = getRestaurantById(id);

        addressRepository.deleteByRestaurant_Id(id);
        hoursRepository.deleteByRestaurant_Id(id);

        restaurantRepository.delete(restaurant);
    }
}
