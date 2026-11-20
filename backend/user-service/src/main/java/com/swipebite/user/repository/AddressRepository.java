
package com.swipebite.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swipebite.user.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser_Id(Long userId);

    Optional<Address> findByIdAndUser_Id(Long id, Long userId);
}
