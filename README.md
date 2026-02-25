# 🎬 CineVault - Cinema Booking API

CineVault is a high-performance, backend-only Cinema Booking System built with **Spring Boot 3**, **MongoDB**, and **Stripe**. It manages the complex lifecycle of movie ticketing, including theater management, automated seat tracking, and high-concurrency handling using MongoDB's atomic document operations.

---

## 🚀 Key Features

* **Atomic Reservations:** Utilizes MongoDB's `findAndModify` to ensure zero double-bookings. Seat selection is handled as an atomic operation at the database level.
* **Theater & Screen Management:** Configure theaters and screens with defined seat capacities.
* **Movie & Show Scheduling:** Dynamic scheduling of movies across different screens and time slots.
* **Secure Payments:** Full integration with the **Stripe API** for real-time, secure credit card transactions.
* **Global Exception Handling:** Custom centralized error management for consistent API responses (404, 409, 402, 400 errors).
* **DTO Pattern:** Clean separation of internal entities and external API responses using **ModelMapper** to prevent data leakage.
* **Automated Validation:** Robust input validation using Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@Email`, etc.).

---

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
* **Database:** MongoDB (NoSQL)
* **Payment Gateway:** Stripe API
* **Object Mapping:** ModelMapper
* **Build Tool:** Maven
* **Lombok:** To reduce boilerplate code (Getters/Setters).

---

## 📂 Project Structure

```text
org.MovieBookingApp
├── 📁 controller      # REST API Endpoints (Booking, Movie, User, etc.)
├── 📁 dto             # Data Transfer Objects
│   ├── 📁 requestDto  # Data entering the API
│   └── 📁 responseDto # Data leaving the API
├── 📁 entities        # MongoDB @Document models
├── 📁 enums           # BookingStatus, PaymentMethod, etc.
├── 📁 exceptions      # Global Exception Handler & Custom Exceptions
├── 📁 repository      # MongoRepository Interfaces
└── 📁 service         # Business Logic & Implementation
    └── 📁 serviceImpl # Concrete service logic

⚡ Concurrency & Locking Mechanism
In a high-traffic cinema system, two users might try to book the same seat at the exact same millisecond.

CineVault's Solution:
Instead of traditional Row Locking used in SQL, we leverage MongoDB's Document-Level Atomicity.

The Operation: We use mongoTemplate.findAndModify.

The Logic: The query searches for the specific Show ID where the requested seatIds are NOT in the bookedSeatIds array ($nin).

The Update: If the query matches (meaning seats are available), it pushes the new seats into the array in a single atomic step.

Result: If the query fails to find the document (meaning someone else just took those seats), the system throws a SeatNotAvailableException immediately.

💳 Payment Workflow (Stripe)
The system uses a secure flow to handle payments without storing sensitive card details locally:

Tokenization: The client (Frontend/Postman) sends card details to Stripe and receives a temporary token (e.g., tok_visa).

Request: The token is sent to the CineVault backend via the /bookings endpoint.

Processing: PaymentGatewayService charges the token in INR.

Manual Rollback: If the Stripe charge fails, the system automatically "pulls" (removes) the reserved seats back out of the Show document to make them available for others again.

🧱 Entity Relationship OverviewEntityDescriptionTheaterRepresents a physical cinema location.ScreenA specific hall inside a theater.MovieMetadata about a film (Title, Genre, Rating).ShowsLinks a Movie to a Screen at a specific time. Tracks bookedSeatIds.UsersThe customer making bookings.BookingThe transaction linking a User, a Show, and specific Seats.PaymentRecords the Stripe transaction ID and status.

🛣️ API Endpoints
1. Theater & Screen
POST /theaters/add: Register a new cinema location.

POST /screens/add: Add a screen to a theater with total seat capacity.

2. Movie & Show
POST /movies/add: Add a movie (Title, Genre, Duration, Rating).

POST /shows/add: Schedule a movie on a screen at a specific time.

GET /shows/{showId}: Check show details and seat availability.

3. User Management
POST /users: Register a new user.

GET /users/{email}: Retrieve user details by email.

4. Booking (Core Logic)
POST /bookings: Validates seats -> Atomic Reservation -> Stripe Charge -> Save Booking -> Save Payment.

GET /bookings/{bookingId}: Retrieve booking receipt and ticket details.

⚙️ Setup & Installation
Clone the Repo:

Bash
git clone <repository-url>
Configure MongoDB: Ensure MongoDB is running on localhost:27017 or update application.properties:

Properties
spring.data.mongodb.uri=mongodb://localhost:27017/MovieBookingDB
Configure Stripe:
Add your Stripe Secret Key from the Stripe Dashboard:

Properties
stripe.api.key=sk_test_your_key_here
Build and Run:

Bash
mvn clean install
mvn spring-boot:run
✅ Example Booking Request
POST /bookings

JSON
{
  "userId": "65b1c...",
  "showId": "65b1d...",
  "seatIds": ["A1", "A2"],
  "paymentToken": "tok_visa"
}
Note: Use tok_visa for successful test payments and tok_chargeDeclined to test failures.
