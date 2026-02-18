# ReportsUEM - University Incident Management System

### Overview

ReportsUEM is a desktop application designed to streamline the reporting, tracking, and management of infrastructure incidents across the Universidad Europea campuses.

Built with a focus on UX and efficiency, the system allows students and maintenance staff to register new issues, monitor real-time status updates, and manage personal accounts. This project was developed as the Final Capstone for the Higher National Diploma (HND) in Software Development (DAM).

### Tech Stack

* Language: Java 17+
* GUI Framework: Java Swing
* Database: MySQL 8.0
* Architecture: MVC (Model-View-Controller) pattern
* Persistence: JDBC (Java Database Connectivity)

### Key Features

* Incident Lifecycle Management: Create, filter (by status/date/relevance), and track maintenance tickets.
* Personalized Dashboard: My Incidents section and a Favorites system for quick access to critical reports.
* Secure Authentication: User registration and a password recovery workflow.
* Optimized Performance: Lightweight desktop client with efficient SQL querying.

### System Requirements

* OS: Windows 10/11
* Java: JDK 17 or higher
* Hardware: 4GB RAM | 500MB Disk Space
* Database: Local or remote MySQL 8.0 server

### Installation and Execution

1. Clone the Repository:
git clone .(https://github.com/macaaroncc/ReportsUEM_University_Incident_Management.git)

cd ud13-pi-final-purpurina
3. Database Setup:
Import the estructura.sql file into your MySQL Server.
Configure your database credentials in modelo/ConexionBD.java.
4. Run the App:
Open the project in IntelliJ IDEA or Eclipse.
Run the main entry point: controlador.Main.java.

### Project Structure

controlador/  # Navigation logic and event handling
modelo/       # DB Connection and Data Access Objects (DAO)
vista/        # GUI Components (Java Swing)
recursos/     # Assets, icons, and UI styling
estructura.sql # SQL Database Schema

### Development Team

Aaron, Bea, Haowen, Chen

### Academic Context

* Institution: Universidad Europea de Madrid (UEM)
* Degree: Higher National Diploma (HND) in Multi-platform Application Development (DAM)
* Course: 2024 / 2025
* Supervisors: Pedro Camacho & Irene del Rincón

---

¿Quieres que te redacte también un pequeño mensaje en inglés para enviarlo a empresas de Edimburgo o Glasgow presentando este proyecto?
