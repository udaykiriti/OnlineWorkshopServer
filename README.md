<h1 align="center"> Online Workshop Server</h1>

<p align="center">
    <br />
  <img src="https://media.giphy.com/media/l0MYt5jPR6QX5pnqM/giphy.gif" width="200" alt="3D Gear Spinning" />
</p>

---

# About This Repository

This repository contains the backend server for the Online Workshop application, built to enable seamless real-time interactions, content delivery, and user management for online workshops.

---

## Features

- **User Authentication:** Secure login and registration with Argon2 hashing.
- **Content Delivery:** Management and distribution of workshop materials (PDFs, Docs).
- **Attendance System:** Track and record student participation in workshops.
- **Admin & Faculty Dashboards:** Specialized tools for system management and workshop instruction.
- **Automated Seeding:** Automatic creation of default Admin and Faculty accounts.

---

## Technologies Used

- **Java 17:** Backend programming language.
- **Spring Boot 3.3.4:** Framework for building REST APIs.
- **MariaDB:** High-performance relational database storage.
- **Spring Data JPA:** For database abstraction and ORM (Hibernate).
- **Maven:** Project management and build tool.

---

## Default Credentials

On application startup, the following accounts are automatically created for testing:

| Role | Username | Password |
| :--- | :--- | :--- |
| **Admin** | `admin` | `admin123` |
| **Faculty** | `faculty` | `faculty123` |

---

## Setup Instructions

### Prerequisites
- **Java 17** or higher installed.
- **MariaDB** server running on `localhost:3306`.

### Running Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/udaykiriti/OnlineWorkshopServer.git
   cd OnlineWorkshopServer
   ```

2. **Configure Database:**
   The server is configured to connect to `workshop_db`. It will attempt to create the database automatically if it doesn't exist.

3. **Run the application:**
   ```bash
   chmod +x mvnw
   ./mvnw spring-boot:run
   ```

The server will be available at: `http://localhost:8081`

---

## API Base Endpoints

- `POST /api/auth/login` - User authentication.
- `POST /api/auth/signup` - New user registration.
- `GET /api/workshops` - List all workshops.
- `GET /api/users/{username}` - Fetch user profile.
- `POST /api/attendance/mark` - Record attendance.