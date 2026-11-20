
package com.swipebite.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swipebite.restaurant.dto.RestaurantAddressRequest;
import com.swipebite.restaurant.dto.RestaurantAddressResponse;
import com.swipebite.restaurant.entity.RestaurantAddress;
import com.swipebite.restaurant.mapper.RestaurantAddressMapper;
import com.swipebite.restaurant.service.RestaurantAddressService;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/address")
public class RestaurantAddressController {

    private final RestaurantAddressService addressService;
    private final RestaurantAddressMapper addressMapper;

    public RestaurantAddressController(
            RestaurantAddressService addressService,
            RestaurantAddressMapper addressMapper) {

        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    // Add Address
    @PostMapping
    public ResponseEntity<RestaurantAddressResponse> addAddress(
            @PathVariable Long restaurantId,
            @RequestBody RestaurantAddressRequest request) {

        RestaurantAddress address = addressMapper.toEntity(request);

        RestaurantAddress savedAddress =
                addressService.addAddress(restaurantId, address);

        return ResponseEntity.ok(addressMapper.toResponse(savedAddress));
    }

    // Get Address
    @GetMapping
    public ResponseEntity<RestaurantAddressResponse> getAddress(
            @PathVariable Long restaurantId) {

        RestaurantAddress address =
                addressService.getAddress(restaurantId);

        return ResponseEntity.ok(addressMapper.toResponse(address));
    }

    // Update Address
    @PutMapping
    public ResponseEntity<RestaurantAddressResponse> updateAddress(
            @PathVariable Long restaurantId,
            @RequestBody RestaurantAddressRequest request) {

        RestaurantAddress updatedAddress =
                addressMapper.toEntity(request);

        RestaurantAddress savedAddress =
                addressService.updateAddress(restaurantId, updatedAddress);

        return ResponseEntity.ok(addressMapper.toResponse(savedAddress));
    }

    // Delete Address
    @DeleteMapping
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long restaurantId) {

        addressService.deleteAddress(restaurantId);

        return ResponseEntity.ok("Restaurant address deleted successfully");
    }
}
