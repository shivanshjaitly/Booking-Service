package com.codekerdos.booking.controller;

import com.codekerdos.booking.entity.Address;
import com.codekerdos.booking.entity.Booking;
import com.codekerdos.booking.entity.User;
import com.codekerdos.booking.repository.AddressRepository;
import com.codekerdos.booking.repository.BookingRepository;
import com.codekerdos.booking.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("users", userRepository.findAll());
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

}
