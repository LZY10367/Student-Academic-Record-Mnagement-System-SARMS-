CREATE DATABASE IF NOT EXISTS StudentAcademicRecordManagementSystem;

USE StudentAcademicRecordManagementSystem;

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Increased length for hashed passwords
    role_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(100),
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    dob DATE,
    major VARCHAR(100),
    grade FLOAT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE teachers (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(100),
    department VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    type VARCHAR(50),
    status ENUM('Pending', 'Approved', 'Rejected') NOT NULL,
    details TEXT,
    application_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE academic_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_name VARCHAR(100),
    score FLOAT,
    semester VARCHAR(20),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- Prepopulate roles
INSERT INTO roles (role_name) VALUES ('Manager'), ('Student'), ('Teacher');

-- Prepopulate demo users
INSERT INTO users (username, password, role_id) VALUES
    ('alice', SHA2('alice123', 256), 2), -- Student
    ('testmanager', SHA2('test123', 256), 1), -- Manager
    ('teststudent', SHA2('test123', 256), 2), -- Student
    ('testteacher', SHA2('test123', 256), 3); -- Teacher

-- Prepopulate students
INSERT INTO students (user_id, name, gender, dob, major, grade)
VALUES (1, 'Alice Smith', 'Female', '2003-05-12', 'Computer Science', 90.5);

-- Prepopulate applications
INSERT INTO applications (student_id, type, status, details, application_date)
VALUES (1, 'Transfer Major', 'Pending', 'Request to transfer to Data Science', '2025-06-27');

UPDATE users SET password = 'test123' WHERE username = 'testmanager';
UPDATE users SET password = 'test123' WHERE username = 'testteacher';
UPDATE users SET password = 'test123' WHERE username = 'teststudent';
UPDATE users SET password = 'test123' WHERE username = 'alice';
show DATABASES;
