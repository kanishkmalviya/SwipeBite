
package com.swipebite.restaurant.mapper;

import org.springframework.stereotype.Component;

import com.swipebite.restaurant.dto.RestaurantHoursRequest;
import com.swipebite.restaurant.dto.RestaurantHoursResponse;
import com.swipebite.restaurant.entity.RestaurantHours;

@Component
public class RestaurantHoursMapper {

    public RestaurantHours toEntity(RestaurantHoursRequest request) {

        RestaurantHours hours = new RestaurantHours();

        hours.setDayOfWeek(request.getDayOfWeek());
        hours.setOpeningTime(request.getOpeningTime());
        hours.setClosingTime(request.getClosingTime());
        hours.setClosed(request.isClosed());

        return hours;
    }

    public RestaurantHoursResponse toResponse(RestaurantHours hours) {

        RestaurantHoursResponse response = new RestaurantHoursResponse();

        response.setId(hours.getId());
        response.setRestaurantId(hours.getRestaurant().getId());
        response.setDayOfWeek(hours.getDayOfWeek());
        response.setOpeningTime(hours.getOpeningTime());
        response.setClosingTime(hours.getClosingTime());
        response.setClosed(hours.isClosed());

        return response;
    }
}
