/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  malesko
 * Created: Jun 4, 2025
 */

CREATE TABLE IF NOT EXISTS Channel (
    IDChannel INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Link VARCHAR(255) NOT NULL,
    Description TEXT,
    PicturePath VARCHAR(255),
    PublishedDate VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS UserRole (
    IDRole INT PRIMARY KEY AUTO_INCREMENT,
    RoleName VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO UserRole (RoleName) SELECT 'ADMIN' WHERE NOT EXISTS (SELECT 1 FROM UserRole WHERE RoleName='ADMIN');
INSERT INTO UserRole (RoleName) SELECT 'USER' WHERE NOT EXISTS (SELECT 1 FROM UserRole WHERE RoleName='USER');

CREATE TABLE IF NOT EXISTS "User" (
    IDUser INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(100) NOT NULL,
    IDRole INT NOT NULL,
    FOREIGN KEY (IDRole) REFERENCES UserRole(IDRole)
);

INSERT INTO "User" (Username, Password, IDRole)
SELECT 'admin', 'admin', 1
WHERE NOT EXISTS (
    SELECT 1 FROM "User" WHERE Username = 'admin'
);

