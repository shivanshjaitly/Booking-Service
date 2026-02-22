package com.codekerdos.booking.repository;

import com.codekerdos.booking.entity.User;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.address")
    List<User> findAllWithAddress();
}
