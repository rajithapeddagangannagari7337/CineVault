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

```

## ⚡ Concurrency & Locking Mechanism

In a high-traffic cinema system, two users may attempt to book the same seat at the same time.

### CineVault’s Solution

Instead of traditional row locking used in SQL databases, CineVault leverages **MongoDB’s document-level atomicity**.

### How It Works

- **Operation:**  
  `mongoTemplate.findAndModify`

- **Logic:**  
  The query searches for the specific `Show` ID where the requested `seatIds` are **not present** in the `bookedSeatIds` array (`$nin`).

- **Update:**  
  If the query matches (seats are available), the requested seats are pushed into the array in a **single atomic operation**.

- **Result:**  
  If the query fails, a `SeatNotAvailableException` is thrown immediately.

---

## 💳 Payment Workflow (Stripe)

The system follows a secure payment process without storing sensitive card details.

### Flow

1. **Tokenization**  
   The client sends card details to Stripe and receives a temporary token (e.g., `tok_visa`).

2. **Request**  
   The token is sent to the CineVault backend via the `/bookings` endpoint.

3. **Processing**  
   `PaymentGatewayService` charges the token in INR.

4. **Manual Rollback**  
   If the Stripe charge fails, the system removes the reserved seats from the `Show` document, making them available again.

---

## 🧱 Entity Relationship Overview

| Entity   | Description |
|--------|-------------|
| Theater | Physical cinema location |
| Screen  | A specific hall inside a theater |
| Movie   | Movie metadata (title, genre, rating, duration) |
| Show    | Links a movie to a screen at a specific time and tracks booked seats |
| User    | Customer making bookings |
| Booking | Transaction linking a user, show, and seats |
| Payment | Records Stripe transaction ID and status |

---

## 🛣️ API Endpoints

### 🎭 Theater & Screen

- **POST `/theaters/add`** – Register a new theater  
- **POST `/screens/add`** – Add a screen to a theater  

### 🎬 Movie & Show

- **POST `/movies/add`** – Add a movie  
- **POST `/shows/add`** – Schedule a movie  
- **GET `/shows/{showId}`** – Get show details and seat availability  

### 👤 User Management

- **POST `/users`** – Register a new user  
- **GET `/users/{email}`** – Retrieve user details  

### 🎟️ Booking (Core Logic)

- **POST `/bookings`** –  
  Validate seats → Atomic reservation → Stripe charge → Save booking → Save payment

- **GET `/bookings/{bookingId}`** – Retrieve booking details  

---

## ⚙️ Setup & Installation

### Clone the Repository
```bash
git clone <repository-url>
