
package com.swipebite.restaurant.service;

import org.springframework.stereotype.Service;

import com.swipebite.restaurant.entity.Restaurant;
import com.swipebite.restaurant.entity.RestaurantAddress;
import com.swipebite.restaurant.repository.RestaurantAddressRepository;
import com.swipebite.restaurant.repository.RestaurantRepository;

@Service
public class RestaurantAddressService {

    private final RestaurantAddressRepository addressRepository;
    private final RestaurantRepository restaurantRepository;

    public RestaurantAddressService(
            RestaurantAddressRepository addressRepository,
            RestaurantRepository restaurantRepository) {

        this.addressRepository = addressRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantAddress addAddress(Long restaurantId, RestaurantAddress address) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        if (addressRepository.findByRestaurant_Id(restaurantId).isPresent()) {
            throw new RuntimeException("Address already exists for this restaurant");
        }

        address.setRestaurant(restaurant);

        return addressRepository.save(address);
    }

    public RestaurantAddress getAddress(Long restaurantId) {

        return addressRepository.findByRestaurant_Id(restaurantId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }

    public RestaurantAddress updateAddress(Long restaurantId, RestaurantAddress updatedAddress) {

        RestaurantAddress address = getAddress(restaurantId);

        address.setHouseNumber(updatedAddress.getHouseNumber());
        address.setStreet(updatedAddress.getStreet());
        address.setArea(updatedAddress.getArea());
        address.setCity(updatedAddress.getCity());
        address.setState(updatedAddress.getState());
        address.setPincode(updatedAddress.getPincode());

        return addressRepository.save(address);
    }

    public void deleteAddress(Long restaurantId) {

        RestaurantAddress address = getAddress(restaurantId);

        addressRepository.delete(address);
    }
}
