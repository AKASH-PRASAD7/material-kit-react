# Project Name: Scala Play Framework Backend with React Frontend

![preview](/client/public/assets/images/minimal-free-preview.jpg)

## Overview

This is a simple full-stack project consisting of a **backend** built using the **Scala Play Framework** and a **frontend** developed using **Vite, React, and Material UI (MUI)**. The backend interacts with a PostgreSQL database hosted on **Neon Cloud** for user authentication, blog management, product management, and a dashboard API for data visualization.

---

## Table of Contents

- [Technologies Used](#technologies-used)
- [Features](#features)
- [Project Structure](#project-structure)
- [Backend Setup](#backend-setup)
- [Frontend Setup](#frontend-setup)
- [Routes](#routes)
- [Database Schema](#database-schema)
- [Running the Project](#running-the-project)

---

## Technologies Used

### Backend:

- **Scala Play Framework**: A web framework for building scalable applications in Scala.
- **PostgreSQL**: A relational database used for storing user, blog, and product data.
- **Neon Cloud**: Cloud provider for hosting the PostgreSQL database.

### Frontend:

- **Vite**: Fast development build tool optimized for modern web projects.
- **React**: JavaScript library for building the user interface.
- **Material UI (MUI)**: React components for faster and easier web development.

---

## Features

### Backend Features:

- **User Authentication**:
  - Sign Up, Sign In
  - Role-based authorization (admin, sales, operations, user)
- **Product Management**:
  - Create and list products
- **Blog Management**:
  - Create and list blogs
- **Dashboard API**:
  - Provides hardcoded data for frontend dashboard
- **Database Connection Test**:
  - Route to check database connectivity

### Frontend Features:

- **User-Friendly Interface** built using React and Material UI
- **Interacts with Backend** via API for handling users, products, blogs, and dashboard data
- **Role-Based Access Control** for different user roles
- **Authentication**:
  - Sign Up, Sign In, Sign Out

---

## Project Structure

```
/backend               # Scala Play Backend folder
    /app               # Contains all the controllers, models, utilities
    /conf              # Configuration files, including routes file
    /public            # Public assets
    /test              # Test cases for backend functionality
/frontend              # React Frontend folder
    /src               # All React components, pages, and hooks
    /public            # Static assets for frontend
```

---

## Backend Setup

### Prerequisites:

- Scala (2.13+)
- SBT (Scala Build Tool)
- PostgreSQL
- Play Framework
- Neon Cloud PostgreSQL account (for hosting the database)

### Steps:

1. **Clone the Project**:

   ```bash
   git clone <repository_url>
   cd backend
   ```

2. **Set Up PostgreSQL**:

   - Create a PostgreSQL database using **Neon Cloud**.
   - Note the database credentials: `DB_URL`, `DB_USER`, `DB_PASSWORD`.

3. **Configure Database**:

   - Update the `application.conf` in the `backend/conf/` directory:
     ```hocon
     db.default.driver = org.postgresql.Driver
     db.default.url = "jdbc:postgresql://<DB_URL>/<DB_NAME>"
     db.default.username = "<DB_USER>"
     db.default.password = "<DB_PASSWORD>"
     ```

4. **Run the Backend**:

   ```bash
   sbt run
   ```

5. **Access the API**:
   The backend API should now be available at `http://localhost:9000`.

---

## Frontend Setup

### Prerequisites:

- Node.js (16+)
- npm (Node package manager)

### Steps:

1. **Navigate to Frontend Folder**:

   ```bash
   cd frontend
   ```

2. **Install Dependencies**:

   ```bash
   npm install
   ```

3. **Run the Frontend**:

   ```bash
   npm run dev
   ```

4. **Access the Frontend**:
   The frontend should be running on `http://localhost:3000`.

---

## Routes

### User Routes

- **POST /api/signup**: Register a new user
- **POST /api/signin**: Sign in an existing user
- **GET /api/users**: Retrieve all users (accessible by admin only)
- **PUT /api/user/update**: Update a user’s role (accessible by admin only)

### Product Routes

- **POST /api/products**: Create a new product
- **GET /api/products**: Retrieve all products

### Blog Routes

- **POST /api/blogs**: Create a new blog post
- **GET /api/blogs**: Retrieve all blog posts

### Dashboard Route

- **GET /api/dashboard**: Retrieve hardcoded dashboard data (accessible by admin and operations roles only)

### Database Test Route

- **GET /api/test-connection**: Test connection to the database

---

## Running the Project

### Backend:

- Ensure that you’ve configured the PostgreSQL database properly.
- Run the backend using the following command:
  ```bash
  sbt run
  ```

### Frontend:

- Navigate to the `client/` folder and start the frontend:
  ```bash
  npm run dev
  ```

You should now have both the backend and frontend running. Access the frontend via `http://localhost:3039`, and it will interact with the backend hosted on `http://localhost:9000`.

---
