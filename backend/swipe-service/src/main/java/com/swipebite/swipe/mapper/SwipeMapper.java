
package com.swipebite.swipe.mapper;

import org.springframework.stereotype.Component;

import com.swipebite.swipe.dto.SwipeRequest;
import com.swipebite.swipe.dto.SwipeResponse;
import com.swipebite.swipe.entity.Swipe;

@Component
public class SwipeMapper {

    public Swipe toEntity(SwipeRequest request) {

        Swipe swipe = new Swipe();

        swipe.setCustomerId(request.getCustomerId());
        swipe.setFoodId(request.getFoodId());
        swipe.setRestaurantId(request.getRestaurantId());
        swipe.setSwipeDirection(request.getSwipeDirection());

        return swipe;
    }

    public SwipeResponse toResponse(Swipe swipe) {

        SwipeResponse response = new SwipeResponse();

        response.setId(swipe.getId());
        response.setCustomerId(swipe.getCustomerId());
        response.setFoodId(swipe.getFoodId());
        response.setRestaurantId(swipe.getRestaurantId());
        response.setSwipeDirection(swipe.getSwipeDirection());
        response.setCreatedAt(swipe.getCreatedAt());

        return response;
    }
}
