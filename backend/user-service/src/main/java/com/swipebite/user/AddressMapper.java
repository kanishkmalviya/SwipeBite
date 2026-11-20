
package com.swipebite.user;

import org.springframework.stereotype.Component;

import com.swipebite.user.dto.AddressRequest;
import com.swipebite.user.dto.AddressResponse;
import com.swipebite.user.entity.Address;

@Component
public class AddressMapper {

    // AddressRequest to Address Entity
    public Address toEntity(AddressRequest request) {

        Address address = new Address();

        address.setHouseNumber(request.getHouseNumber());
        address.setStreet(request.getStreet());
        address.setArea(request.getArea());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
        address.setAddressType(request.getAddressType());
        address.setDefault(request.isDefault());

        return address;
    }

    // Address Entity to AddressResponse
    public AddressResponse toResponse(Address address) {

        AddressResponse response = new AddressResponse();

        response.setId(address.getId());
        response.setHouseNumber(address.getHouseNumber());
        response.setStreet(address.getStreet());
        response.setArea(address.getArea());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setPincode(address.getPincode());
        response.setAddressType(address.getAddressType());
        response.setDefault(address.isDefault());

        return response;
    }
}
