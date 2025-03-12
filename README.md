# Wrkr - PhoneBook Maintenance TEST Project

## Overview
The `Wrkr - PhoneBook TEST` is a microservices-based application built using Java Spring Boot, JWT, REACT JS and MySQL.


**Overview & Preconditions:
ALL the requirements mentioned in the TEST REQUIREMENTS are met using Java, Spring Boot and APIs with JWT Security

In Future we can introduce the option of creating a new phone book through an UI with a custom title for the phonebook. 
For now for the test project, we will use 2 pre-created phone books. We need to create 2 empty tables manually in the DB (but contacts can be added dynamically with APIs)


## DEVELOPED AS PART OF THIS TEST PROJECT:

  MICROSERVICE APIs as per the REQUIREMENTS of the TEST:  {type} is the parameter specifying the DB or the phonebook in our project's context.

1) GET /api/phone-book/{type} - Fetches all contacts. 

2) POST /api/phone-book/{type} - Adds a new contact. [DOES COMPLETE VALIDATION BEFORE ADDING CONTACTS]

3) DELETE /api/phone-book/{type}/{id} - Deletes a contact. 

4) GET /api/phone-book/{type}/sort - [Gets sorted contacts]

5) GET /api/phone-book/compare - [Compares and gets unique contacts from phonebook tables present in the DB] 

All these APIs work only with AUTHORIZATION - JWT Authentication: The developed AuthMicroservice will handle authentication for all these APIs using URI filters.

TEST CLASSES FOR TESTING ALL APIs INCLUDING THE AUTHORIZATION MICROSERVICE ARE DONE PART OF THIS TEST.

GLOBAL ERROR HANDLING USING SPRING BOOT FRAMEWORK IS IMPLEMENTED IN PHONEBOOK MICROSERVICE TO PRODUCE MEANINGFUL STRUCTURED JSON RESPONSES FOR ERROR MESSAGES TOO.

## Use the enclosed DDL.sql to create tables in separate DBs or hosts for AUTH MICROSERVICE and PHONEBOOK MICROSERVICE, that work independent of each other. 


## APPLICATION FLOW OF THESE MICROSERVICE IN A REAL WORLD UI APPLICATION:

(a) Create an account for a user on the UI [UI is non existant at this point but backend API for user creation with validation is developed part of this test]

(b) Login using the created account on the UI [UI is non existent at this point but backend API for LOGIN is developed part of this test]

(c) The AUTH TOKEN gets created in the response to the client or the caller[LOGIN API developed and running on AUTH MICROSERVICE returns a BEARER TOKEN in the response] using a SECRET stored in the AUTH MICROSERVICE server (present in applicaiton.properties) - The same SECRET is also present in the client (PhoneBookMicroservice)

(d) From the UI, select a phone-book from the drop-down [UI is non existent at this point but backend APIs have the {type} parameter that determines the phone book]

(e) Depending on the type of phone book the contacts are fetched using PhoneBookMicroservice (as of now official and personal are the two phonebooks)

(f) Functionalities like ADD (after successful validation), SORT Contacts, DELETE Contacts are developed in PhoneBookMicroservice with APIs with JWT security [All these operations can be extended to dedicated buttons on the UI, which is non existent at this point]

(g) Also the COMPARE functionality of phonebooks is implemented with APIs at this point, and unique contacts are returned comparing phonebooks in the DB.

** Technically all microservices used in this application have a clear separation of concerns to this point

## INSTRUCTIONS TO INSTALL & RUN AS SIMPLE APIs or MICROSERVICES:

(a) Download the AuthMicroservice and PBookMicroservice into an IDE of your choice

(b) Do a mvn clean install of these 2 projects. They are not dependent on each other and can be run individucally too. 

(c) Setup the database as mentioned in the enclosed DDL.sql (just table creation would do - Data gets entered as the application runs via APIs used)

(d) Check the database and JWT settings in the application.properties file of each of these Microservices

(e) Now run the 2 Microservices from Maven or your IDE as SPRING BOOT applications and test the APIs starting with Registering a user like a real world applicaiton and logging in as seen in below section (which produces the AUTH TOKEN).

The JWT SECRET and Database Credentials are left as is in the application.properties - Add your specific configuration or can use the existing default ports and settings present as of now in application.properties. (make sure you change your database password)


## The developed AuthMicroservice and its APIs are purely independent MIcroservices running independent o the PhoneBookMicroservice that does the business logic.
** How JWT AUTH Microservice works here:
========================================
There are 2 APIs in AuthMicroservice
1) POST /auth/register - Register a new user account in WRKR servers
Content-Type: application/json

SAMPLE REQUESTS:

{
  "username": "wrkr1",
  "password": "newwrkrpassword",
  "role": "USER"
}

RESPONSE: 
User registered successfully

2) POST /auth/login - Authenticates the user account created earlier in user database - GENERATES a JWT token for use in SUBSEQUENT APIs of PBOOK MICROSERTICE (ALL THE BUSINESS APIs)
Content-Type: application/json

{
  "username": "wrkr1",
  "password": "newwrkrpassword"
}
RESPONSE: 
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwic3ViIjoidGFiY29ycDEiLCJpYXQiOjE3NDE1ODc0MTAsImV4cCI6MTc0MTU5MTAxMH0.wwPcLYClQOczD_oztByGcTbN6myfbtn-9EHvpGyt4Vs"
}
 

POST /api/phone-book/{type} Adds a new contact where type must be either official or personal. [DOES COMPLETE VALIDATION BEFORE ADDING]

URI: <HOST>/api/phone-book/personal AND/OR  <HOST>/api/phone-book/official
Content-Type: application/json
Authorization: Bearer <the generated token from previous login API step>

{
    "name": "Christy Samson",
    "phoneNumber": "3234698752"
}

All other business operation on the phonebook like viewing the phone book based on type, sorting a specific phonebook based on type, deleting a particular contact from a phonebook (type) based on ID, comparing the phonesbooks to display UNIQUE contacts are all done using Microservice APIs developed under PBookMicroservice.
 
 
[As of 10 MAR, was in the process of implementing these APIs and building a REACT UI using TSX and Vite - BUT DROPPED THAT PLAN AND INSTEAD ADDED TEST CLASSES FOR VALIDATIONS AND ALL APIS INCLUDING THE AUTH MICROSERVICE USED IN THIS TEST PROJECT]


##License
This project is for demonstration/assessment purposes and is not intended for production use.






 
