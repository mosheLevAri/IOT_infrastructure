# Generic IOT infrastructures


## Overview

This project is a generic IoT infrastructure that provides a scalable and flexible solution for managing IoT devices and data.It utilizes various design patterns such as Factory, Singleton, and Observer, along with solid principles and object-oriented programming concepts.

The infrastructure consists of multiple modules working together to handle different aspects of IoT operations. it includes components such as a gateway server, request handler, command factory, and a thread pool for task execution. It utilizes two databases, SQL and MongoDB, to store and retrieve data.


## Flow
- The Gateway Servlet receives requests from clients through HTTP, UDP, or TCP protocols.
- The Gateway Servlet parses the request into JSON format.
- The parsed request is added as a new task to the Thread Pool.
- The Thread Pool assigns the task to a worker thread for execution.
- The Request Handler analyzes the request and maps it to the corresponding command in the Singleton Command 	Factory.
- The Singleton Command Factory creates an instance of the selected command.
- The executed command interacts with the MongoDB module for data storage and retrieval.
- The command performs specific operations based on the requested function, executing I/O operations on the MongoDB database.
- The result of the command execution is returned.
- The Response is prepared and sent back to the client.

![image](https://github.com/mosheLevAri/IOT_infrastructure/assets/95866297/7feafef3-85af-4790-bb68-d0d4bdbacf33)


## Modules
### 1. Company Servlet

- Purpose: Handles company and product data, storing it in an SQL database.

- Features:
	- Provides endpoints for adding and managing company and product information.
	- Handles HTTP requests related to company and product data.

### 2. Gateway Server
- Purpose: Acts as the main entry point for incoming HTTP requests, routing them to the appropriate module.

- Features:
	- Receives HTTP requests from clients.
	- Routes requests to the Request Handler for further processing.
	- Acts as a central hub for communication within the infrastructure.

### 3. Request Handler
- Purpose: Processes incoming requests and determines the appropriate command to execute.

- Features:
	- Analyzes request data and maps it to the corresponding command in the Singleton Command Factory.
	- Facilitates the execution of commands based on requested operations.

### 4. Singleton Command Factory
- Purpose: Provides a centralized factory for creating and executing commands.

- Features:
	- Maintains a collection of command creators mapped to specific keys.
	- Creates instances of commands based on the request key.

### 5. Basic Factory Methods
-Purpose: Implements various command methods used in the Singleton Command Factory.

- Features:
	- Interacts with the MongoDB module for data storage and retrieval.
	- Provides methods for different IoT operations such as company and product registrations, and IoT device 	management.


### 6. Thread Pool
- Purpose: Manages the execution of tasks in a concurrent and scalable manner.

- Features:
	- Utilizes a pool of worker threads for parallel processing.
	- Assigns tasks, such as executing commands, to worker threads.


### 7. MongoDB (Data Storage)
- Purpose: Handles data storage and retrieval using the MongoDB database.

- Features:
	- Performs CRUD operations for IoT devices, companies, and products.
	- Interacts with the MongoDB database to store and retrieve data.

### 8. SQL Module
-Purpose: Manages the SQL database and handles company and product data.

- Features:
	- Provides SQL database connectivity and CRUD operations.
	- Stores and retrieves company and product information.

### 9. Jar Loader (Future Enhancement)
- Purpose: Loads external JAR files dynamically at runtime.
- Features:
	- Implements a module directory monitor using the Observer pattern.
	- Detects changes in the module directory and dynamically loads new functionality into the Singleton 		Command Factory.



## Usage
To utilize the IoT infrastructure, follow these steps:

- Set up a Tomcat server and deploy the project.
- Configure the Gateway Server (GateWay class) to listen for incoming HTTP requests.
- Implement specific command methods in the Basic Factory Methods class for different IoT operations.
- Register the command methods in the initBasicMethodsToFactory method of the Basic Factory Methods class.
- Start the server and send HTTP requests to the appropriate endpoints, including the relevant data and company information.
- The Gateway Server will forward the requests to the Request Handler, which will execute the corresponding commands.
- The executed commands will interact with the MongoDB module to store and retrieve data.
- The SQL database will be updated by the Company Servlet with company and product data.
- The response will be generated based on the executed commands and returned



