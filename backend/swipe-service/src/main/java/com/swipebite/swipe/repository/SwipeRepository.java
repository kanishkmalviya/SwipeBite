
package com.swipebite.swipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swipebite.swipe.entity.Swipe;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Long> {

    List<Swipe> findByCustomerId(Long customerId);

    List<Swipe> findByCustomerIdAndSwipeDirection(
            Long customerId, String swipeDirection);

    boolean existsByCustomerIdAndFoodId(Long customerId, Long foodId);
}
