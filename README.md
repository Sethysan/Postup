# Postup 📬

[![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-11+-blue.svg)]()
[![Vue.js](https://img.shields.io/badge/Vue.js-2.x-brightgreen.svg)]()
[![Contributors](https://img.shields.io/github/contributors/sethysan/postup.svg)](https://github.com/sethysan/postup/graphs/contributors)
[![Stars](https://img.shields.io/github/stars/sethysan/postup.svg?style=social)](https://github.com/sethysan/postup/stargazers)

## Table of Contents

- [Postup 📬](#postup-)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Value Proposition](#value-proposition)
  - [Key Features](#key-features)
  - [Technology Stack](#technology-stack)
  - [Installation Instructions](#installation-instructions)
  - [Usage Guide](#usage-guide)
    - [Getting Started](#getting-started)
    - [Tips](#tips)
  - [Available Scripts](#available-scripts)
    - [Frontend Scripts](#frontend-scripts)
    - [Backend Scripts](#backend-scripts)
  - [Testing Instructions](#testing-instructions)
  - [Project Structure Overview](#project-structure-overview)
    - [Frontend (`postup-frontend/`)](#frontend-postup-frontend)
    - [Backend (`postup-backend/`)](#backend-postup-backend)
  - [Contributing Guidelines](#contributing-guidelines)
  - [License Information](#license-information)
  - [Acknowledgments](#acknowledgments)

## Introduction

Welcome to **Postup**! 🎉

Postup is a modern, user-friendly platform for sharing and discussing content. Whether you're collaborating on projects, sharing announcements, or engaging with a community, Postup brings people together through threaded discussions, direct messaging, and interactive forums.

## Value Proposition

Postup streamlines the process of sharing and discussing ideas in a user-friendly interface. It provides a centralized platform to manage all your posts efficiently, making collaboration and communication seamless.

## Key Features

- **Create, View, Edit & Delete Posts**: Easily manage your content.
- **User Authentication**: Secure login and registration system.
- **Direct Messaging**: Send and receive private messages with other users.
- **Replies and Threaded Discussions**: Engage in conversations through replies and threads.
- **Upvoting & Downvoting**: Interact with posts and replies through a voting system.
- **Forum Management**: Create and moderate forums to organize discussions.
- **User Roles & Permissions**: Admin and moderator roles for managing content and users.
- **Favorites**: Mark forums as favorites for quick access.
- **Search Functionality**: Search for forums and posts using keywords.
- **User-friendly Interface**: Minimalistic design so you can focus on your content.

## Technology Stack

This project is built using the following technologies:

- **Frontend**:
  - Vue.js 🖼️
  - Vuex ⚡ (State Management)
  - Axios 📡 (HTTP Client)
- **Backend**:
  - Java (Spring Boot) ☕
  - Spring Security (Authentication and Authorization)
  - JWT (JSON Web Tokens)
  - Maven (Build Tool)
- **Database**:
  - PostgreSQL 🐘
- **Authentication**:
  - BCrypt Password Encoder
  - JWT (JSON Web Tokens)

## Installation Instructions

Follow these steps to install and set up Postup locally:

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/Sethysan/Postup.git
   ```

2. **Navigate to the Project Directory**:

   ```bash
   cd Postup
   ```

3. **Install Dependencies**:

   - **Frontend (Vue.js)**:

     ```bash
     cd postup-frontend
     npm install
     ```

   - **Backend (Spring Boot)**:

     ```bash
     cd postup-backend
     mvn clean install
     ```

4. **Configure Environment Variables**:

   - Ensure that your PostgreSQL database is set up.
   - Update the backend `application.properties` file with your database connection details.

5. **Run the Application**:

   - **Backend**:

     ```bash
     mvn spring-boot:run
     ```

   - **Frontend**:

     ```bash
     npm run serve
     ```

6. **Access the Application**:

   - Backend API: [http://localhost:9000](http://localhost:9000)
   - Frontend App: [http://localhost:8080](http://localhost:8080) (or the port specified by Vue.js)

## Usage Guide

### Getting Started

Once the application is running, you can:

1. **Register a New Account**: Navigate to the registration page to create a new user account.
2. **Login**: Access your account using your credentials.
3. **Explore Forums**: Browse existing forums or create a new one to start discussions.
4. **Create a Post**: Click on "New Post" to create and share your content.
5. **Engage in Discussions**: Reply to posts and comments to engage with the community.
6. **Direct Messaging**: Send private messages to other users by navigating to the messages section.
7. **Manage Favorites**: Mark forums as favorites for quick access from your dashboard.

### Tips

- **Customize Your Profile**: Upload a profile picture to personalize your account.
- **Search Functionality**: Use the search bar to find forums and posts related to your interests.
- **Notifications**: Keep an eye on notifications for replies and messages.

## Available Scripts

### Frontend Scripts

- **Serve**: Runs the frontend in development mode.

  ```bash
  npm run serve
  ```

- **Build**: Compiles the frontend for production.

  ```bash
  npm run build
  ```

- **Lint**: Lints and fixes files.

  ```bash
  npm run lint
  ```

### Backend Scripts

- **Run**: Starts the Spring Boot application.

  ```bash
  mvn spring-boot:run
  ```

- **Build**: Builds the backend application.

  ```bash
  mvn clean install
  ```

- **Test**: Runs the backend tests.

  ```bash
  mvn test
  ```

## Testing Instructions

To run the backend tests:

```bash
mvn test
```

To run the frontend tests (if applicable):

```bash
npm run test
```

Ensure your database is running and properly configured before running the tests.

## Project Structure Overview

### Frontend (`postup-frontend/`)

- **`src/`**: Contains the source code for the frontend application.
  - **`components/`**: Reusable Vue components.
  - **`views/`**: Page-level components for routing.
  - **`router/`**: Vue Router configuration.
  - **`store/`**: Vuex state management.
  - **`assets/`**: Static assets like images and styles.
- **`public/`**: Public assets and the main HTML file.
- **`package.json`**: Node.js dependencies and scripts.

### Backend (`postup-backend/`)

- **`src/`**: Contains the source code for the backend application.
  - **`main/java/com/techelevator/`**:
    - **`controller/`**: REST controllers handling HTTP requests.
    - **`dao/`**: Data Access Objects for database interactions.
    - **`model/`**: Data models representing entities.
    - **`service/`**: Business logic and application services.
    - **`Application.java`**: Main application entry point.
  - **`main/resources/`**:
    - **`application.properties`**: Application configuration.
    - **`static/`**: Static resources.
- **`pom.xml`**: Maven configuration and dependencies.

## Contributing Guidelines

Contributions are welcome! To contribute:

1. **Fork the Repository**: Click the "Fork" button at the top right of the repository page.

2. **Clone Your Fork**:

   ```bash
   git clone https://github.com/your-username/Postup.git
   ```

3. **Create a New Branch**:

   ```bash
   git checkout -b feature/your-feature-name
   ```

4. **Make Your Changes**: Implement your feature or fix.

5. **Commit Your Changes**:

   ```bash
   git commit -m 'Add your feature'
   ```

6. **Push to Your Fork**:

   ```bash
   git push origin feature/your-feature-name
   ```

7. **Submit a Pull Request**: Go to the original repository and open a pull request.

Please ensure your code follows the project's coding standards and includes appropriate tests.

## License Information

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

- **Contributors**: [List of contributors](https://github.com/Sethysan/Postup/graphs/contributors)
- **References**:
  - [Spring Boot Documentation](https://spring.io/projects/spring-boot)
  - [Vue.js Documentation](https://vuejs.org/v2/guide/)
  - [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

⭐️ If you like Postup, give it a star — it helps!

_Note: If any information is missing or unclear, please refer to the project's repository or contact the maintainers._
