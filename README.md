Student Grade Management System

A desktop-based Student Grade Management System developed using Java Swing, JDBC, MySQL, and IntelliJ IDEA. The application allows users to manage student records, calculate grades, track pass/fail status, search students, sort scores, and view academic statistics.

Features

Add student records

Automatically generate Student IDs (ST001, ST002, etc.)

Update student information

Delete student records

Search students by ID or name

Sort students by score

Automatic grade calculation

Automatic PASS/FAIL status

Student statistics dashboard

Input validation

MySQL database integration

Java Swing graphical user interface

Grade System
Score	Grade
90–100	A+
80–89	A
70–79	B
60–69	C
50–59	D
0–49	F
Pass/Fail
Score	Status
40–100	PASS
0–39	FAIL
Technologies Used

Java

Java Swing

JDBC

MySQL

IntelliJ IDEA

SQL

Project Structure
StudentGradeManagementSystem/
│
├── src/
│   ├── StudentGradeTracker.java
│   └── DatabaseConnection.java
│
└── README.md


DatabaseConnection.java is required for establishing the connection between the Java application and MySQL database.

Requirements

Before running the project, install:

Java JDK

IntelliJ IDEA

MySQL Server

MySQL JDBC Driver / MySQL Connector/J

Database Setup

Create the database in MySQL:

CREATE DATABASE student_grade_db;


Select the database:

USE student_grade_db;


Create the students table:

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    score DOUBLE NOT NULL
);

Database Connection

The project uses JDBC to connect Java with MySQL.

Your DatabaseConnection.java should contain your own database credentials.

Example:

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/student_grade_db";

    private static final String USER = "root";

    private static final String PASSWORD =
            "your_password";

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}


Important: Do not upload your real MySQL password to GitHub.

MySQL JDBC Driver Setup in IntelliJ IDEA

This project does not require Maven.

In IntelliJ IDEA:

Download MySQL Connector/J.

Open the project in IntelliJ IDEA.

Go to File → Project Structure.

Select Modules → Dependencies.

Click + → JARs or Directories.

Select the MySQL Connector/J .jar file.

Apply the changes.

Run StudentGradeTracker.java.

How to Run
1. Start MySQL

Make sure your MySQL server is running.

2. Create the Database

Run the database and table SQL commands provided above.

3. Configure Database Connection

Open:

DatabaseConnection.java


Set:

private static final String USER = "root";
private static final String PASSWORD = "your_password";


according to your MySQL installation.

4. Configure MySQL Connector/J

Add the MySQL JDBC .jar file to the IntelliJ project dependencies.

5. Run the Application

Open:

StudentGradeTracker.java


and run the main() method.

Application Functions
Add Student

Enter:

Student Name

Score

Then click Add Student.

The system automatically generates a Student ID.

Update Student

Select a student from the table, modify the name or score, and click Update.

Delete Student

Select a student and click Delete. A confirmation dialog appears before deletion.

Search Student

Use the search field to search by:

Student ID

Student Name

Sort Students

Click Sort by Score to display students from highest score to lowest score.

Statistics Dashboard

The application displays:

Total Students

Average Score

Highest Score

Lowest Score

Passed Students

Failed Students

Validation

The application validates student input before storing it in the database.

Student Name

Cannot be empty

Must contain at least 2 characters

Allows letters, spaces, ., ', and -

Score

Cannot be empty

Must be numeric

Must be between 0 and 100

Database Operations

The application performs standard CRUD operations:

Operation	SQL
Create	INSERT
Read	SELECT
Update	UPDATE
Delete	DELETE
Screenshots

You can add screenshots of the application here.

Example:

![Student Grade Management System]<img width="960" height="540" alt="Screenshot 2026-09-25 135926" src="https://github.com/user-attachments/assets/c85dccae-db6e-42d4-9e0a-fa834720bcd9" />

Future Improvements

Possible future enhancements:

Login system

Admin dashboard

Multiple subjects

GPA calculation

Attendance management

PDF report generation

Excel/CSV export

Graphs and charts

Dark mode

Student profiles

Author

Your Name

GitHub: Your GitHub Profile

License

This project is created for educational purposes.
