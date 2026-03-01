package com.codekerdos.booking.repository;

import com.codekerdos.booking.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // For normal listing
    @EntityGraph(attributePaths = {"address"})
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.address")
    List<User> findAllWithAddress();


    List<User> findByNameContaining(String name, Sort sort);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmailCustom(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE email = :email",
            nativeQuery = true)
    User findUserByEmailNative(@Param("email") String email);

    User findByEmailNamed(@Param("email") String email);


    // For sorting
    @EntityGraph(attributePaths = {"address"})
    @Query("SELECT u FROM User u")
    List<User> findAllWithAddressSorted(Sort sort);

    // For pagination
    @EntityGraph(attributePaths = {"address"})
    @Query(value = "SELECT u FROM User u",
            countQuery = "SELECT COUNT(u) FROM User u")
    Page<User> findAllWithAddress(Pageable pageable);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);

}
