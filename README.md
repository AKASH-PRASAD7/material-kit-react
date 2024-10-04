# Scala Play Framework Backend with React Frontend (Role-Based Access Control)

![preview](/client/public/assets/images/minimal-free-preview.jpg)

## Overview

This is a simple full-stack project consisting of a **backend** built using the **Scala Play Framework** and a **frontend** developed using **Vite, React, and Material UI (MUI)**. The backend interacts with a PostgreSQL database hosted on **Neon Cloud** for user authentication and role-based access control.

---

## Table of Contents

- [Problems](#problems)

  - [User Role Management](#user-role-management)
    - [User Roles and Access Permissions](#user-roles-and-access-permissions)
    - [Implementation Details](#implementation-details)

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Backend Setup](#backend-setup)
- [Frontend Setup](#frontend-setup)
- [Routes](#routes)
- [Database Schema](#database-schema)
- [Running the Project](#running-the-project)

---

## Problems

### User Role Management

In this project, a comprehensive user role management system has been implemented to ensure that users have appropriate access to various parts of the application based on their roles. This system enhances security and streamlines user interactions by restricting access to functionalities that are not relevant to a user's responsibilities.

#### **User Roles and Access Permissions:**

1. **Admin**

   - **Accessible Pages**:
     - `/users`: Manage all users, assign roles, and oversee user activities.
     - `/products`: Create, view, update, and delete products.
     - `/blog`: Create, view, update, and delete blog posts.
     - `/dashboard`: Access comprehensive dashboard data and analytics.
   - **Permissions**:
     - Full access to all backend APIs and frontend pages.
     - Ability to update user roles and manage user data.

2. **Operations**

   - **Accessible Pages**:
     - `/products`: View and manage product listings.
     - `/blog`: View and manage blog posts.
     - `/dashboard`: Access operational dashboard data.
   - **Permissions**:
     - View dashboard analytics relevant to operations.
     - Cannot manage user roles or access user management features.

3. **Sales**

   - **Accessible Pages**:
     - `/products`: View and manage product listings.
     - `/blog`: View and manage blog posts.
   - **Permissions**:
     - Limited access to dashboard data, focusing on sales-related metrics.
     - Cannot access user management or broader dashboard features.

4. **User**
   - **Accessible Pages**:
     - `/products`: View product listings.
   - **Permissions**:
     - Can view products and related details.
     - Cannot access blogs, dashboard, or user management features.

#### **Implementation Details:**

- **Role-Based Routing**: The frontend utilizes protected routes that check the user's role before granting access to specific pages. Unauthorized access attempts are redirected to appropriate fallback pages or shown error messages.
- **Backend Authorization**: The Scala Play Framework backend enforces role-based access control by verifying user roles before processing API requests. This ensures that even if frontend protections are bypassed, the backend remains secure.

- **Scalability**: The system is designed to easily incorporate additional roles or modify existing permissions as the application evolves.

---

## Features

### Authentication with Cookie-Based Sessions:

- **User Authentication**:
  - Sign In & Sign Up: Users can register or log in to the system. After successful login, an HTTP-only cookie is used to store session information, ensuring security against common attacks like XSS.
  - Cookie-Based Authentication: The Play Framework handles user authentication using cookies, which are automatically sent with every request to verify the user's session without exposing sensitive data.
- **Role Management and Authorization:**:

  - Update User Role (Admin Only): Only admin users have the capability to update the roles of other users. This feature is essential for controlling access to different parts of the system.

  - Role-Based Data Access: Depending on the user's role (admin, operations, sales, or user), different levels of access are granted
    - **Admin**: Access to all data (users, products, blogs, and dashboard).
    - **Operations**: Access to products, blogs, and the operational dashboard.
    - **Sales**: Access to products and blogs only.
    - **User**: Access only to the product listings.

- Data Retrieval Based on Role:
  - Get All Products, Blogs, Dashboard, and Users: Users can retrieve data depending on their role:
    - Admin can view all user data, products, blogs, and dashboard metrics.
    - Operations can access products, blogs, and operational dashboard data.
    - Sales can view products and blogs.
    - Users can only see product listings.

The backend ensures that users without the proper permissions cannot access restricted routes, while the frontend presents an interface tailored to the user's role.

## Technologies Used

### Backend:

- **Scala Play Framework**: A web framework for building scalable applications in Scala.
- **PostgreSQL**: A relational database used for storing user, blog, and product data.
- **Neon Cloud**: Cloud provider for hosting the PostgreSQL database.

### Frontend:

- **Vite**: Fast development build tool optimized for modern web projects.
- **React**: JavaScript library for building the user interface.
- **Material UI (MUI)**: React components for faster and easier web development.
- **Axios**: Promise-based HTTP client for making API requests.

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
   git clone https://github.com/AKASH-PRASAD7/material-kit-react.git
   cd backend
   ```

2. **Run the Backend**:

   ```bash
   sbt run
   ```

3. **Access the API**:
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
   The frontend should be running on `http://localhost:3039`.

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

## Database Schema

_Details about the database schema can be added here, including tables, relationships, and sample data._

---

## Running the Project

### Backend:

- Ensure that you’ve configured the PostgreSQL database properly.
- Run the backend using the following command:
  ```bash
  sbt run
  ```

### Frontend:

- Navigate to the `frontend/` folder and start the frontend:
  ```bash
  npm run dev
  ```

You should now have both the backend and frontend running. Access the frontend via `http://localhost:3039`, and it will interact with the backend hosted on `http://localhost:9000`.

---
