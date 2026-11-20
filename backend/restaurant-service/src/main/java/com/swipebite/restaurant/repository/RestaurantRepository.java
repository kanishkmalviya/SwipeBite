
package com.swipebite.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.swipebite.restaurant.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}
