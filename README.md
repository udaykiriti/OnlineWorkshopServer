<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Online Workshop Server - README</title>
</head>
<body>

<h1>Online Workshop Server</h1>

<p>This repository hosts the backend server for the Online Workshop application, designed to facilitate real-time interactions, content delivery, and user management for online workshops.</p>

<h2>🚀 Features</h2>
<ul>
  <li><strong>User Authentication:</strong> Secure login and registration mechanisms.</li>
  <li><strong>Session Management:</strong> Real-time session tracking and management.</li>
  <li><strong>Content Delivery:</strong> Efficient streaming and distribution of workshop materials.</li>
  <li><strong>Admin Dashboard:</strong> Tools for administrators to monitor and manage ongoing workshops.</li>
</ul>

<h2>🛠️ Technologies Used</h2>
<ul>
  <li><strong>Java:</strong> Primary programming language for backend development.</li>
  <li><strong>Spring Boot:</strong> Framework for building the backend services.</li>
  <li><strong>WebSocket:</strong> For real-time communication between server and clients.</li>
  <li><strong>MySQL:</strong> Database for storing user data and workshop information.</li>
</ul>

<h2>📂 Project Structure</h2>
<ul>
  <li><strong>src/</strong>: Contains the main application code.</li>
  <li><strong>.mvn/</strong>: Maven wrapper for project build management.</li>
  <li><strong>pom.xml</strong>: Maven project configuration file.</li>
  <li><strong>mvnw</strong>: Unix shell script for Maven wrapper.</li>
  <li><strong>mvnw.cmd</strong>: Windows batch script for Maven wrapper.</li>
</ul>

<h2>📦 Setup Instructions</h2>
<ol>
  <li>
    <strong>Clone the Repository:</strong>
    <pre><code>git clone https://github.com/udaykiriti/onlineworkshop-server.git
cd onlineworkshop-server
    </code></pre>
  </li>
  <li>
    <strong>Build the Project:</strong>
    <pre><code>./mvnw clean install
    </code></pre>
  </li>
  <li>
    <strong>Run the Application:</strong>
    <pre><code>./mvnw spring-boot:run
    </code></pre>
    <p>The server will start on <code>http://localhost:8080</code>.</p>
  </li>
</ol>

<h2>📄 Endpoints</h2>
<ul>
  <li><strong>POST /api/register</strong>: Register a new user.</li>
  <li><strong>POST /api/login</strong>: Authenticate and log in a user.</li>
  <li><strong>GET /api/workshops</strong>: Retrieve a list of available workshops.</li>
  <li><strong>GET /api/workshop/{id}</strong>: Get details of a specific workshop.</li>
  <li><strong>POST /api/workshop/{id}/join</strong>: Join a specific workshop session.</li>
</ul>

<h2>⚙️ Configuration</h2>
<p>Configuration settings can be found in <code>src/main/resources/application.properties</code>. Ensure to set up your database connection and other necessary parameters before running the application.</p>

<h2>🧪 Testing</h2>
<p>Unit and integration tests are located in the <code>src/test/java</code> directory. To run the tests:</p>
<pre><code>./mvnw test
</code></pre>

<h2>📌 Notes</h2>
<ul>
  <li>Ensure that your MySQL database is running and accessible.</li>
  <li>For production environments, consider configuring a reverse proxy and securing the application with HTTPS.</li>
</ul>

<h2>📢 Contributing</h2>
<p>Contributions are welcome! Please fork the repository, create a new branch, and submit a pull request with your changes.</p>

</body>
</html>
