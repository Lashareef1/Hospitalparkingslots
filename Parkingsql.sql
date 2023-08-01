CREATE DATABASE  parking;

use parking;

CREATE TABLE registration (
userid varchar(225) NOT NULL,
Name varchar(225) NOT NULL,
Email varchar(225) NOT NULL,
MobileNo varchar(225) NOT NULL,
dob DATE NOT NULL,
Category varchar(255),
Username varchar(225) NOT NULL,
SecurityQuestions1 VARCHAR(255) NOT NULL,
SecurityAnswer1 VARCHAR(255) NOT NULL,
SecurityQuestions2  VARCHAR(255) NOT NULL,
SecurityAnswer2 VARCHAR(255) NOT NULL,
Password varchar(225) NOT NULL,
oldPassword1 varchar(225) ,
oldPassword2 varchar(225) ,
PRIMARY KEY (userid),
UNIQUE KEY userName (userName),
UNIQUE KEY email (email)

);

use parking;
CREATE TABLE bookings (
    bookingId varchar(225) NOT NULL ,
    vehiclenumber VARCHAR(20),
    startdate DATE,
    enddate DATE,
    day int(10),
    vehiclecategory VARCHAR(20),
    slotnumber VARCHAR(20),
    chargesperday int(10),
    Totalamount varchar(20),
    Renewal VARCHAR(225),
    UserId VARCHAR(225),
      PenaltyAmount varchar(225),
      RefundAmount varchar(225),
    UNIQUE KEY vehiclenumber(vehiclenumber),
     PRIMARY KEY(bookingId)
    );
    
use parking;
CREATE TABLE Adminbooking (
 AdminId varchar(225) NOT NULL ,
 slotnumber VARCHAR(220),
 vehiclenumber VARCHAR(20),
 Indate date,
 Intime VARCHAR(45),
 Outtime varchar(45),
 Outdate date,
 vehiclecategory VARCHAR(20),
 chargesperhour int ,
 Totalamount varchar(225),
 PenaltyAmount varchar(225),
 UNIQUE KEY vehiclenumber(vehiclenumber),
     PRIMARY KEY(AdminId)
);


