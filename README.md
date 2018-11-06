## Cars app with REST Spring Boot, Spring Security, JWT, Spring Data JPA, H2, Maven and Swagger

This is a simple REST API spring boot maven project for CRUD Operations to store/retrieve/remove cars details (brand, model, year of production, car Specifications) and using Swagger to test the Restful endpoints.

## Architecture

The application follows the REST architectural style

 1. **Controller:** is the presentation layer where the end points are located
 2. **Service:** is the service layer where the business logic resides
 3. **Repository:** is the persistence layer where the CRUD repository is located
 
## Technologies

The application is created using the following technologies

1. Spring Boot

	+ spring-boot-starter-web
	+ spring-boot-starter-actuator
	+ spring-boot-starter-data-jpa
	+ spring-boot-starter-security
	+ spring-boot-starter-validation
	+ spring-boot-starter-test
	+ spring-boot-devtools

2. Java 8
3. Maven Dependency Management
4. H2 In-Memory database
5. Json Web Tokens (JWT)
6. Swagger 2

## Unit and Integration Tests

 1. **For the Controller:** it uses the Spring Boot Test framework with MockMvc, Mockito and hamcrest matchers
 2. **For the Service:** it uses the Spring Boot Test framework with Mockito and MockBean
 3. **For the Repository:** it uses the Spring Boot Test framework
 4. **For the Integration Test:** It uses the Spring Boot Test framework with MockMvc and hamcrest matchers

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You need to install the following software

 * Java JDK 1.8+

 * Maven 3.0+

### Installing

Steps to Setup the app

1. **Clone the application**

	```bash
	git clone https://github.com/SayedBaladoh/spring-boot-security-data-jpa-h2-rest-swagger-cars-app.git
	cd polling-app-server
	```

2. **Create database schema**

	The schema will be generated automatically. 

3. **Initialize tables data**

	The database tables will be populated with some data by default. If you want to change the default data

	+ open `src/main/resources/data.sql` file

	+ change the `sql` queries

4. **Change database username and password as per you want**

	The default database username is `sa` with empty `` password. If you want to change the database `username` and `password`

	+ open `src/main/resources/application.properties` file

	+ change `spring.datasource.username` and `spring.datasource.password` properties as per you want

5. **Change server port as per you want**

	The server will start on port `8080` by default. If you want to change the default `port`

	+ open `src/main/resources/application.properties` file

	+ change `server.port` property and remove comment `#` before it

6. **Run the tests**

	You can run the automated tests by typing the following command -

	```bash
	mvn clean
	mvn test
	```

7. **Run the app**

	You can run the spring boot app by typing the following command -

	```bash
	mvn spring-boot:run
	```

	The server will start on port `8080` by default, So you'll be able to access the complete application on `http://localhost:8080`. 
	If you changed the port in  `src/main/resources/application.properties` file, use your custom port `http://localhost:port`.

8. **Package the app**

	You can also package the application in the form of a `jar` file and then run it like so -

	```bash
	mvn clean package
	java -jar target/cars-0.0.1-SNAPSHOT.jar

### Running

To access the app use the following endpoints

1. **Metrics to monitor the app**

	+ View availble metrics `http://localhost:8080/cars/actuator/`

	+ View app info `http://localhost:8080/cars/actuator/info`
	
	+ Health check `http://localhost:8080/cars/actuator/health`

2. **Launch the H2 Console**
	
	To see the database web console

	+ `http://localhost:8080/cars/h2/` use `sa` as a username and empty password

3. **Documentation and Examples: Swagger UI**

	Use Swagger to view the available Restful endpoints, how to use and test them. 

	+ `http://localhost:8080/cars/swagger-ui.html`
	
	For the services that required authenticated users you must login first to get access token `Bearer Token` and pass it to the `Authorization` request header as `Bearer token`

	You can use username `sayedbaladoh` and password `sayedbaladoh` for login or signup first with a new user.

## About me

I am Sayed Baladoh - Phd. Senior Software Engineer. I like software development. You can contact me via:

* [LinkedIn+](https://www.linkedin.com/in/sayed-baladoh-227aa66b/)
* [Mail](sayedbaladoh@yahoo.com)
* [Phone](+20 1004337924)

_**Any improvement or comment about the project is always welcome! As well as others shared their code publicly I want to share mine! Thanks!**_

## License

   Licensed under the Apache License, Version 2.0 (the "License").
   You may obtain a copy of the License at

       `http://www.apache.org/licenses/LICENSE-2.0`

## Acknowledgments

Thanks for reading. Share it with someone you think it might be helpful
