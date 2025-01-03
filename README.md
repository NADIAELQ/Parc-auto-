# Fleet Management System Project

This repository contains a Fleet Management Project developed using Spring framework with JWT security and REST API. The project aims to provide a comprehensive solution for managing a fleet of vehicles, including their drivers, availability, conformity and associated tasks.

# Setup and Installation

To set up and run the Fleet Management Project locally, follow these steps:

    Clone the repository to your local machine

Install the necessary dependencies

Configure the database connection details in the application.properties file.

Build and run the project using your preferred IDE 

    Once the project is up and running, you can access the APIs at (http://localhost:8080/swagger-ui/index.html)
    or use Postman 


To use the Fleet Management Project, follow the API documentation available at (http://localhost:8080/swagger-ui/index.html) to understand the available endpoints, request formats, and responses. You will need to authenticate and obtain a JWT token to access the protected APIs.

by default an admin account is created with credententiels :

      Username: admin
      
      Password: admin
      
ONLY the user with admin role has the right to create another admin account.

Some endpoints may have role restrictions or other restrictions 

there are 3 roles :

    - Admin

    - Trip Manager
    
    - Driver
      
