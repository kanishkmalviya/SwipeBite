
package com.swipebite.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.swipebite.restaurant.entity.RestaurantAddress;

public interface RestaurantAddressRepository extends JpaRepository<RestaurantAddress, Long> {

    Optional<RestaurantAddress> findByRestaurant_Id(Long restaurantId);

}
