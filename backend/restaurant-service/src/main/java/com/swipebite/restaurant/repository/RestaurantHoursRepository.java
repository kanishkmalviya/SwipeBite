
package com.swipebite.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.swipebite.restaurant.entity.RestaurantHours;

public interface RestaurantHoursRepository extends JpaRepository<RestaurantHours, Long> {

    List<RestaurantHours> findByRestaurant_Id(Long restaurantId);
    
    void deleteByRestaurant_Id(Long restaurantId);

}
