package com.codekerdos.booking.controller;

import com.codekerdos.booking.entity.Address;
import com.codekerdos.booking.entity.Booking;
import com.codekerdos.booking.entity.User;
import com.codekerdos.booking.repository.AddressRepository;
import com.codekerdos.booking.repository.BookingRepository;
import com.codekerdos.booking.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BookingRepository bookingRepository;

    public UserController(UserRepository userRepository,
                          AddressRepository addressRepository,
                          BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.bookingRepository = bookingRepository;
    }

    // View all users
    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAllWithAddress());
        return "users";
    }

    // Show create user form
    @GetMapping("/new")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    // Save user
    @PostMapping
    public String saveUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    // Show address form
    @GetMapping("/{id}/address")
    public String showAddressForm(@PathVariable Long id, Model model) {

        Address address = new Address();
        model.addAttribute("address", address);
        model.addAttribute("userId", id);

        return "address-form";
    }

    // Save address
    @PostMapping("/{id}/address")
    public String saveAddress(@PathVariable Long id,
                              @ModelAttribute Address address) {

        User user = userRepository.findById(id).get();

        addressRepository.save(address);

        user.setAddress(address);
        userRepository.save(user);

        return "redirect:/users";
    }

    // Show booking form
    @GetMapping("/{id}/booking")
    public String showBookingForm(@PathVariable Long id, Model model) {

        Booking booking = new Booking();

        model.addAttribute("booking", booking);
        model.addAttribute("userId", id);

        return "booking-form";
    }

    // Save booking
    @PostMapping("/{id}/booking")
    public String saveBooking(@PathVariable Long id,
                              @ModelAttribute Booking booking) {

        User user = userRepository.findById(id).get();

        booking.setUser(user);

        bookingRepository.save(booking);

        return "redirect:/users";
    }

    @PostMapping("/users/{userId}/booking/{bookingId}/delete")
    public String deleteBooking(@PathVariable Long userId,
                                @PathVariable Long bookingId) {

        User user = userRepository.findById(userId).orElseThrow();

        Booking bookingToRemove = user.getBookings()
                .stream()
                .filter(b -> b.getId().equals(bookingId))
                .findFirst()
                .orElseThrow();

        user.removeBooking(bookingToRemove);

        userRepository.save(user); // orphanRemoval deletes booking

        return "redirect:/users";
    }

    @GetMapping("/sorted")
    public String getSortedUsers(Model model) {

        List<User> users =
                userRepository.findAllWithAddressSorted(
                        Sort.by(Sort.Direction.ASC, "name"));

        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping("/paginated")
    public String getPaginatedUsers(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage =
                userRepository.findAllWithAddress(pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());

        return "users";
    }


}
