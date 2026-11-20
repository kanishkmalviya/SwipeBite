
package com.swipebite.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swipebite.user.AddressMapper;
import com.swipebite.user.dto.AddressRequest;
import com.swipebite.user.dto.AddressResponse;
import com.swipebite.user.entity.Address;
import com.swipebite.user.service.AddressService;

@RestController
@RequestMapping("/api/users/{userId}/addresses")
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public AddressController(AddressService addressService,  AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> addAddress( @PathVariable Long userId, @RequestBody AddressRequest request) {

        Address address = addressMapper.toEntity(request);
        Address savedAddress = addressService.addAddress(userId, address);

        return new ResponseEntity<>(
                addressMapper.toResponse(savedAddress),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddresses(
            @PathVariable Long userId) {

        List<AddressResponse> addresses =
                addressService.getUserAddresses(userId)
                .stream()
                .map(addressMapper::toResponse)
                .toList();

        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress( @PathVariable Long userId, @PathVariable Long addressId,
            @RequestBody AddressRequest request) {

        Address updatedAddress = addressMapper.toEntity(request);

        Address savedAddress = addressService.updateAddress(
                userId, addressId, updatedAddress);

        return ResponseEntity.ok(addressMapper.toResponse(savedAddress));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId) {

        addressService.deleteAddress(userId, addressId);

        return ResponseEntity.ok("Address deleted successfully");
    }
}
