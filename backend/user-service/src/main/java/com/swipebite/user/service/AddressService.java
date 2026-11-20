
package com.swipebite.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swipebite.user.entity.Address;
import com.swipebite.user.entity.User;
import com.swipebite.user.repository.AddressRepository;
import com.swipebite.user.repository.UserRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository,
                          UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    //  For Add new address
    public Address addAddress(Long userId, Address address) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        address.setUser(user);

        return addressRepository.save(address);
    }

    // Get all addresses of a user
    
    public List<Address> getUserAddresses(Long userId) {

        return addressRepository.findByUser_Id(userId);
    }

    // Update address
    
    public Address updateAddress(Long userId, Long addressId,
                                 Address updatedAddress) {

        Address existingAddress = addressRepository
                .findByIdAndUser_Id(addressId, userId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        existingAddress.setHouseNumber(updatedAddress.getHouseNumber());
        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setArea(updatedAddress.getArea());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setPincode(updatedAddress.getPincode());
        existingAddress.setAddressType(updatedAddress.getAddressType());
        existingAddress.setDefault(updatedAddress.isDefault());

        return addressRepository.save(existingAddress);
    }

    // Delete address
    
    public void deleteAddress(Long userId, Long addressId) {

        Address address = addressRepository
                .findByIdAndUser_Id(addressId, userId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        addressRepository.delete(address);
    }
}
