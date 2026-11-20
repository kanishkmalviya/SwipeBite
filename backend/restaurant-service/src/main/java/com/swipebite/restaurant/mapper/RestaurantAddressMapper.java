
package com.swipebite.restaurant.mapper;

import org.springframework.stereotype.Component;

import com.swipebite.restaurant.dto.RestaurantAddressRequest;
import com.swipebite.restaurant.dto.RestaurantAddressResponse;
import com.swipebite.restaurant.entity.RestaurantAddress;

@Component
public class RestaurantAddressMapper {

    public RestaurantAddress toEntity(RestaurantAddressRequest request) {

        RestaurantAddress address = new RestaurantAddress();

        address.setHouseNumber(request.getHouseNumber());
        address.setStreet(request.getStreet());
        address.setArea(request.getArea());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());

        return address;
    }

    public RestaurantAddressResponse toResponse(RestaurantAddress address) {

        RestaurantAddressResponse response = new RestaurantAddressResponse();

        response.setId(address.getId());
        response.setRestaurantId(address.getRestaurant().getId());
        response.setHouseNumber(address.getHouseNumber());
        response.setStreet(address.getStreet());
        response.setArea(address.getArea());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setPincode(address.getPincode());

        return response;
    }
}
