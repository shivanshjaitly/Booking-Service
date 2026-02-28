Below is a **single-page summary of all localhost URLs** for testing your current `UserController` (Thymeleaf-based).
Assumption: Application runs on **[http://localhost:8080](http://localhost:8080)**

Base Path: `/users`

---

# BOOKING SERVICE – TESTING URL SUMMARY

---

## 1. View All Users

```
GET http://localhost:8080/users
```

Purpose:

* Loads users.html
* Shows all users
* Address displayed (if exists)

---

## 2. Open Create User Form

```
GET http://localhost:8080/users/new
```

Purpose:

* Opens user-form.html

---

## 3. Save User

```
POST http://localhost:8080/users
```

Form fields:

```
name=Shivansh
email=shivansh@gmail.com
password=123456
```

Expected:

* Redirect to `/users`
* User added in DB

---

## 4. Open Address Form

(Example: User ID = 1)

```
GET http://localhost:8080/users/1/address
```

Purpose:

* Opens address-form.html

---

## 5. Save Address for User

```
POST http://localhost:8080/users/1/address
```

Form fields:

```
city=Lucknow
state=UP
country=India
```

Expected:

* Redirect to `/users`
* Address shown in table

---

## 6. Open Booking Form

```
GET http://localhost:8080/users/1/booking
```

Purpose:

* Opens booking-form.html

---

## 7. Save Booking

```
POST http://localhost:8080/users/1/booking
```

Form fields:

```
bookingDate=2026-03-01
status=CONFIRMED
```

Expected:

* Booking saved in DB

---

## 8. Delete Booking (orphanRemoval Test)

IMPORTANT
If you kept mapping as:

```java
@PostMapping("/{userId}/booking/{bookingId}/delete")
```

Then URL:

```
POST http://localhost:8080/users/1/booking/2/delete
```

Purpose:

* Removes booking from user
* orphanRemoval deletes booking from DB

---

## 9. Sorting Users

```
GET http://localhost:8080/users/sorted
```

Purpose:

* Users sorted by name ASC

---

## 10. Pagination

Page 0:

```
GET http://localhost:8080/users/paginated?page=0&size=5
```

Page 1:

```
GET http://localhost:8080/users/paginated?page=1&size=5
```

Purpose:

* Displays limited number of users
* Tests Pageable functionality

---

# Complete Testing Flow (Recommended Order)

1. Create 3–5 users
2. Add address to one user
3. Add 2 bookings
4. Delete one booking (test orphanRemoval)
5. Test sorting
6. Test pagination

---

# What Should Work

* No Thymeleaf errors
* Address safely handled
* Sorting correct
* Pagination correct
* Booking deletion removes DB record
* No LazyInitializationException

---

This is your full Class-21 and Class-22 manual testing checklist in one place.

If you want, next I can give:

Microservice phase testing URLs (when Inventory starts).
