
package com.swipebite.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swipebite.restaurant.exception.ResourceNotFoundException;
import com.swipebite.restaurant.exception.DuplicateResourceException;
import com.swipebite.restaurant.entity.Restaurant;
import com.swipebite.restaurant.entity.RestaurantHours;
import com.swipebite.restaurant.repository.RestaurantHoursRepository;
import com.swipebite.restaurant.repository.RestaurantRepository;

@Service
public class RestaurantHoursService {

    private final RestaurantHoursRepository hoursRepository;
    private final RestaurantRepository restaurantRepository;

    public RestaurantHoursService(
            RestaurantHoursRepository hoursRepository,
            RestaurantRepository restaurantRepository) {

        this.hoursRepository = hoursRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantHours addHours(Long restaurantId, RestaurantHours hours) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        boolean alreadyExists = hoursRepository.findByRestaurant_Id(restaurantId)
                .stream()
                .anyMatch(existing -> existing.getDayOfWeek() == hours.getDayOfWeek());

        if (alreadyExists) {
            throw new DuplicateResourceException("Hours already exist for this day");
        }

        hours.setRestaurant(restaurant);

        return hoursRepository.save(hours);
    }

    public List<RestaurantHours> getHours(Long restaurantId) {

        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        return hoursRepository.findByRestaurant_Id(restaurantId);
    }

    public RestaurantHours updateHours(
            Long restaurantId, Long hoursId, RestaurantHours updatedHours) {

        RestaurantHours hours = hoursRepository.findById(hoursId)
                .orElseThrow(() -> new RuntimeException("Hours not found"));

        if (!hours.getRestaurant().getId().equals(restaurantId)) {
            throw new ResourceNotFoundException("Hours do not belong to this restaurant");
        }

        boolean duplicateDay = hoursRepository.findByRestaurant_Id(restaurantId)
                .stream()
                .anyMatch(existing ->
                        !existing.getId().equals(hoursId)
                        && existing.getDayOfWeek() == updatedHours.getDayOfWeek());

        if (duplicateDay) {
            throw new DuplicateResourceException("Hours already exist for this day");
        }

        hours.setDayOfWeek(updatedHours.getDayOfWeek());
        hours.setOpeningTime(updatedHours.getOpeningTime());
        hours.setClosingTime(updatedHours.getClosingTime());
        hours.setClosed(updatedHours.isClosed());

        return hoursRepository.save(hours);
    }

    public void deleteHours(Long restaurantId, Long hoursId) {

        RestaurantHours hours = hoursRepository.findById(hoursId)
                .orElseThrow(() -> new RuntimeException("Hours not found"));

        if (!hours.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Hours do not belong to this restaurant");
        }

        hoursRepository.delete(hours);
    }
}
