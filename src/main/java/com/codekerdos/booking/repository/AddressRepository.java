package com.codekerdos.booking.repository;

import com.codekerdos.booking.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
