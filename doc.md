CLASS-23 & 24

Spring Security and Password Management (Merged)

You can directly use this to teach in class.

After that, I will give you clean Git commands for a new branch.

---

# CLASS-23 & 24

Spring Security and Password Management
Booking Service

---

# 1. Objective of This Class

In this class, we transformed our Booking Service from:

Open and unprotected application

To:

Secure, authenticated, role-based application.

After this class, students understand:

* What is Authentication
* What is Authorization
* How Spring Security works internally
* How passwords are encrypted
* How login flow works
* How roles control access
* Why password encoding is mandatory

---

# 2. Why Security Is Needed

Before adding Spring Security:

* Anyone could access /users
* Anyone could modify data
* Passwords stored in plain text
* No role restriction
* No protection from unauthorized access

This is not acceptable in real-world applications.

Security provides:

* Identity verification
* Access control
* Encrypted password storage
* Protection of endpoints
* Controlled session management

---

# 3. Authentication vs Authorization

Authentication
= Who are you?

Authorization
= What are you allowed to access?

Example:

User logs in successfully → Authentication passed.

User tries to access /admin but has USER role → Authorization fails (403).

---

# 4. Architecture of Spring Security in Our Project

Flow:

Client (Browser)
→ Login Page
→ Spring Security Filter Chain
→ AuthenticationManager
→ CustomAuthenticationProvider
→ CustomUserDetailsService
→ UserRepository
→ Database

If password matches:

Authenticated user stored in SecurityContext.

---

# 5. Files Created / Modified

We added:

1. SecurityConfig.java
2. CustomAuthenticationProvider.java
3. CustomUserDetailsService.java
4. CustomUserDetails.java
5. login.html
6. LoginController.java
7. Updated UserRepository

---

# 6. SecurityConfig.java

Purpose:

* Define security rules
* Protect endpoints
* Configure login
* Register AuthenticationProvider
* Define PasswordEncoder

Key configuration:

* /login permitted
* /users/** requires ROLE_USER
* BCryptPasswordEncoder used
* CustomAuthenticationProvider registered

Why important:

SecurityConfig defines who can access what.

---

# 7. CustomUserDetailsService

Purpose:

Load user from database using email.

Method:

loadUserByUsername(String email)

Steps:

* Fetch user from DB
* Throw UsernameNotFoundException if not found
* Wrap user inside CustomUserDetails
* Return to Spring Security

Why important:

Spring Security does not know our database structure.
UserDetailsService connects Security with our DB.

---

# 8. CustomUserDetails

Purpose:

Convert our User entity into Spring Security format.

It provides:

* getUsername() → email
* getPassword() → encrypted password
* getAuthorities() → roles

Important:

We added:

"ROLE_" + role.getName()

Because Spring expects authorities in format:

ROLE_USER

---

# 9. CustomAuthenticationProvider

Purpose:

Validate user credentials manually.

Process:

1. Get email and raw password
2. Load user from DB
3. Compare raw password with encoded password
4. If match → return authenticated token
5. If not match → throw BadCredentialsException

Important:

We used:

passwordEncoder.matches(rawPassword, encodedPassword)

Never compare passwords using equals().

---

# 10. BCrypt Password Encoding

Why BCrypt?

* One-way hashing
* Salted
* Secure against rainbow table attacks
* Industry standard

Correct flow:

Raw password
→ passwordEncoder.encode(rawPassword)
→ Store in database

During login:

passwordEncoder.matches(raw, encoded)

Database should NEVER store plain password.

---

# 11. Database Requirements

roles table:

id | name
1  | USER

user_roles table:

user_id | role_id
1       | 1

Important:

Role name must be:

USER

Not:

ROLE_USER

Because we automatically add ROLE_ in CustomUserDetails.

---

# 12. Endpoint Security Rule

In SecurityConfig:

.requestMatchers("/users/**").hasRole("USER")

This means:

Only users with ROLE_USER can access /users.

If not → 403 Forbidden.

---

# 13. Testing Flow

Step 1
Access /users → redirected to /login

Step 2
Login with correct credentials → redirected to /users

Step 3
Wrong password → error shown

Step 4
Logout → session cleared

Step 5
Try /users without login → redirected to /login

---

# 14. Common Errors We Faced

1. Plain password stored → BCrypt error
2. Double encrypted password → invalid format
3. No role assigned → 403
4. Circular dependency in beans → fixed by moving AuthenticationProvider bean

Each error helped understand internal working.

---

# 15. What Students Must Remember

* Security is mandatory
* Always encrypt passwords
* Authentication is different from Authorization
* ROLE_ prefix matters
* Never store plain password
* Never disable security in production

---

# 16. Result of This Class

Booking Service is now:

* Authenticated
* Role-based
* Password encrypted
* Protected
* Production ready

Now we are ready for Microservices.

---

## PART 2 — NEW BRANCH COMMANDS

You want a new branch after finishing Class-23 & 24.

Step 1 — Commit current work

git add .
git commit -m "Completed Class 23 & 24 - Spring Security and Password Management"

Step 2 — Push current branch

git push

If upstream not set:

git push -u origin class-23-security

Step 3 — Create new branch for next class

git checkout -b class-25-inventory-microservice

Step 4 — Push new branch

git push -u origin class-25-inventory-microservice

Now you are on a clean new branch.

---

If you want next:

I can give you Class-25 (Inventory Microservice) DOC format with architecture first.
