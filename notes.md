Perfect 👍
Below is your **Class Notes – DOC Ready (Professional + Interview Ready)** covering:

* Delete Booking using orphanRemoval
* Fetch Join for Lazy loading
* Basic Authentication using Spring Security
* CSRF handling
* Best practices

You can copy this directly into Word / PDF and present in class.

---

# Class: Delete Booking + Fetch Join + Basic Authentication

---

# 1. Objective of This Class

In this class we implemented:

1. Delete booking using orphanRemoval
2. Proper handling of LAZY loading using Fetch Join
3. Basic Authentication using Spring Security
4. CSRF understanding
5. Production-ready UI integration

This class bridges JPA relationships and security fundamentals.

---

# 2. Problem 1: Delete Booking

We already defined relationship:

```
User → OneToMany → Booking
```

Inside User entity:

```
@OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
)
private List<Booking> bookings = new ArrayList<>();
```

---

# 3. What is orphanRemoval?

Definition:

orphanRemoval = true ensures that if a child entity is removed from the parent collection, it is deleted from the database.

Example:

```
user.getBookings().remove(booking);
```

After saving user:

```
DELETE FROM bookings WHERE id = ?
```

Without orphanRemoval:

* Booking would remain in DB
* It would become an orphan record

---

# 4. Delete Booking Controller Logic

Controller method:

```
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

    userRepository.save(user);

    return "redirect:/users";
}
```

Key Concept:

We remove booking from parent collection.
Because orphanRemoval = true, Hibernate deletes it automatically.

---

# 5. Problem 2: LazyInitializationException

When using:

```
fetch = FetchType.LAZY
```

If Thymeleaf tries to access:

```
user.address.city
```

After transaction closes, Hibernate throws:

LazyInitializationException

Thymeleaf wraps it as:

TemplateInputException

---

# 6. Correct Solution: Fetch Join

Instead of making everything EAGER, we use Fetch Join.

Repository method:

```
@Query("""
       SELECT DISTINCT u 
       FROM User u 
       LEFT JOIN FETCH u.address 
       LEFT JOIN FETCH u.bookings
       """)
List<User> findAllWithAddressAndBookings();
```

Why DISTINCT?

Because join with bookings creates duplicate users in result set.

Controller:

```
model.addAttribute("users", 
    userRepository.findAllWithAddressAndBookings());
```

Now address and bookings are loaded before view rendering.

---

# 7. Why Not Use EAGER?

If we use:

```
fetch = FetchType.EAGER
```

Problems:

* Loads unnecessary data
* Performance issues
* N+1 problem
* Bad practice in large applications

Best practice:
Use LAZY + Fetch Join.

---

# 8. Basic Authentication (Spring Security)

We added dependency:

```
spring-boot-starter-security
```

Spring Boot automatically:

* Secures all endpoints
* Generates default login
* Uses default user credentials

---

# 9. application.properties Configuration

```
spring.security.user.name=user
spring.security.user.password=password
```

Correct property name:

spring.security.user.name
NOT spring.username

---

# 10. What Happens After Adding Security?

When opening:

```
http://localhost:8080/users
```

Spring shows login page.

Only authenticated users can access application.

---

# 11. Custom Security Configuration

Optional SecurityConfig:

```
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
```

This:

* Allows CSS without authentication
* Secures everything else
* Disables CSRF for demo purposes

---

# 12. What is CSRF?

CSRF = Cross Site Request Forgery

Spring Security protects POST requests.

If CSRF is enabled, forms must include:

```
<input type="hidden" 
       th:name="${_csrf.parameterName}" 
       th:value="${_csrf.token}" />
```

For demo class:
We disabled CSRF.

In production:
We should NOT disable CSRF.

---

# 13. Architecture Overview After This Class

Application now supports:

* User CRUD
* Address management
* Booking management
* Delete booking (orphanRemoval)
* Fetch join optimization
* Basic authentication
* Secured endpoints
* Styled UI

---

# 14. Interview Questions

Q1. What is orphanRemoval?

It deletes child entity when removed from parent collection.

---

Q2. Difference between Cascade REMOVE and orphanRemoval?

Cascade REMOVE:
Deletes children when parent is deleted.

orphanRemoval:
Deletes child when removed from collection.

---

Q3. What is LazyInitializationException?

It occurs when a lazily loaded association is accessed outside active Hibernate session.

---

Q4. How to solve LazyInitializationException?

* Use Fetch Join
* Use DTO
* Use transactional boundary

Not recommended:
Making everything EAGER.

---

Q5. Why use DISTINCT in Fetch Join?

To prevent duplicate parent records when joining collections.

---

Q6. What happens when we add Spring Security dependency?

Spring Boot auto-configures:

* Login page
* Authentication
* Password encoder
* Secures all endpoints

---

# 15. Final Outcome of This Class

We now have:

* Real relationship management
* Proper deletion strategy
* Performance-safe loading
* Security layer
* Production-ready UI

This marks transition from beginner Spring Boot to intermediate / enterprise-level design.

---

If you want next class notes ready as well, tell me:

Next Topic:

* Pagination & Sorting
* Derived Query Methods
* Custom Queries
* DTO Pattern
* Advanced Spring Security
