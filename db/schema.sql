DROP DATABASE IF EXISTS complaint_management_system;

CREATE DATABASE complaint_management_system;
USE complaint_management_system;

-- Users table
CREATE TABLE users (
                       user_id VARCHAR(50) PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL,
                       number VARCHAR(50) NOT NULL,
                       role ENUM('EMPLOYEE', 'ADMIN') NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Complaints table with ON DELETE CASCADE for foreign keys
CREATE TABLE complaints (
                            complaint_id VARCHAR(50) PRIMARY KEY,
                            title VARCHAR(200) NOT NULL,
                            description TEXT NOT NULL,
                            department VARCHAR(50) NOT NULL,
                            priority ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
                            status ENUM('PENDING', 'IN_PROGRESS', 'RESOLVED', 'REJECTED') DEFAULT 'PENDING',
                            submitted_by VARCHAR(50) NOT NULL,
                            assigned_to VARCHAR(50),
                            admin_remarks TEXT,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (submitted_by) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
                            FOREIGN KEY (assigned_to) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);
