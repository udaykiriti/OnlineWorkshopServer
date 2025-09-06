<h1 align="center"> Online Workshop Server</h1>

<p align="center">
    <br />
  <img src="https://media.giphy.com/media/l0MYt5jPR6QX5pnqM/giphy.gif" width="200" alt="3D Gear Spinning" />
</p>

---

#  About This Repository

This repository contains the backend server for the Online Workshop application, built to enable seamless real-time interactions, content delivery, and user management for online workshops.

---

##  Features

- **User Authentication:** Secure login and registration.
- **Session Management:** Real-time session tracking.
- **Content Delivery:** Efficient streaming and distribution of workshop materials.
- **Admin Dashboard:** Tools for admins to monitor and manage workshops.

---

##  Technologies Used

- **Java:** Backend programming language.
- **Spring Boot:** Framework for building REST APIs and services.
- **WebSocket:** Real-time communication with clients.
- **MySQL:** Persistent data storage for users and workshops.

---

##  Project Structure

- `src/` — Main application source code.
- `.mvn/` — Maven wrapper files.
- `pom.xml` — Maven configuration.
- `mvnw`, `mvnw.cmd` — Maven wrapper scripts.

---

##  Setup Instructions

Clone and run the project locally:

```bash
git clone https://github.com/udaykiriti/onlineworkshop-server.git
cd onlineworkshop-server
./mvnw clean install
./mvnw spring-boot:run
