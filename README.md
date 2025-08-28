📘 IGNOU Project — Student & Course Management System

A full-stack academic project built for IGNOU submission using HTML5, CSS3, JavaScript, Bootstrap for the frontend and Spring Boot + MySQL for the backend. The project integrates essential student-related modules such as login, enquiry, complaints, feedback, and SMS API notifications.

📝 Overview

This project is a Student & Course Management System built to automate common tasks in an educational institute. The frontend ensures a responsive and user-friendly UI, while the backend (Spring Boot) provides RESTful services connected to a MySQL database.

🎯 Learning Objectives

Develop modular full-stack applications combining frontend and backend technologies.

Build a responsive UI with Bootstrap.

Implement REST APIs in Spring Boot.

Work with MySQL relational database.

Integrate external services such as SMS API.

Follow best practices for Git & GitHub version control.
frontend/
├── index.html
├── css/
│ └── styles.css
├── js/
│ └── app.js
├── assets/
│ └── images/
└── README.md

├── frontend/
│ ├── index.html # Landing page
│ ├── css/ # Stylesheets
│ ├── js/ # JS logic (form validation, dynamic UI)
│ ├── pages/ # Login, Student Info, Enquiry, etc.
│ └── assets/ # Images, screenshots
│
├── backend/
│ ├── src/main/java/com/ignou/project/
│ │ ├── controllers/ # REST controllers
│ │ ├── models/ # Entity classes
│ │ ├── repositories/ # JPA repositories
│ │ └── services/ # Business logic
│ └── src/main/resources/
│ ├── application.properties # DB & SMS config
│ └── static/templates # Optional templates
│
├── database/
│ └── schema.sql # MySQL schema
│
├── docs/
│ ├── report.pdf # IGNOU report
│ └── references.md
│
├── assignments/
│ └── ... # IGNOU assignment files
│
└── README.md

📑 Features / Modules

Student Information System — Manage student records (CRUD).

Login Management System — Secure authentication using Spring Security (optional) or custom logic.

Enquiry Management System — Store and track student enquiries.

Complaint Management System — File, view, and resolve complaints.

Feedback Management System — Collect feedback from students/faculty.

Study Management System — Course materials, assignments, and schedules.

SMS API Integration — Send notifications (OTP, alerts, updates).

.

🛠️ Tech Stack

Frontend: HTML5, CSS3, JavaScript, Bootstrap

Backend: Java, Spring Boot (STS IDE)

Database: MySQL

API Integration: SMS API

Version Control: Git & GitHub🚀 Getting Started
Prerequisites

Java JDK 17+

Spring Tool Suite (STS) or IntelliJ IDEA

MySQL Server 8+

Maven (for Spring Boot)

Browser (Chrome/Firefox)


📖 Usage Guide

Login with provided credentials.

Add/manage student info via dashboard.

Submit enquiries, complaints, feedback via respective forms.

Check study material and assignments.

Receive SMS alerts after successful submissions.

🗄️ Database Schema

Tables:

students (id, name, course, contact, email, address, ... )

users (id, username, password, role)

enquiries (id, student_id, enquiry_text, date)

complaints (id, student_id, complaint_text, status)

feedback (id, student_id, feedback_text, rating)

study_materials (id, title, description, file_url)

✅ Testing & Verification


☁️ Deployment

Frontend: Host via GitHub Pages, Netlify, or as static resources in Spring Boot.

Backend: Deploy Spring Boot JAR/WAR to Heroku, Render, or any Java server.

Database: Use MySQL local/remote (AWS RDS, Railway, etc.).
