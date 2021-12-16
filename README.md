# beer-catalog-api

## Description

This REST API represents a beer catalogue composed of different manufacturers (providers). Beer consumers can look up the beer catalogue in order to inspire future purchases. 
For each manufacturer, we want to at least keep track of their name and nationality and for each beer we want to at least have the following information: beer name, graduation, type, description  and the manufacturer. 

## Core technologies

*Language*
- Java 11

*Back-end*
- Spring Boot 2.3.0

*Unit Testing*
- JUnit 4.13
- Mockito 3.3.3

*Dependency management tool*
- Maven 3.5.0

*Containerization*
- Docker-compose 2.3

*API Documentation*
- Swagger 2.9.2

## Build setup

- Clone this repo to your local machine. This application can be executed either with Docker or with Maven:

### With Docker

```
$ docker-compose up
```
The previous command allows to have Spring Boot and MySQL containers up and running.

### With Maven

```
$ mvn spring-boot:run
```

- Open your browser and test the application on *http://localhost:8080/beer-catalog/api/swagger-ui.html*. The API Documentation (Endpoints, Models...) is included there.

## Technical approach

Mandatory tasks

1 - REST API implementation without persistence
2 - REST API implementation with persistence

In order to implement the mandatory tasks, [Repository Pattern](https://martinfowler.com/eaaCatalog/repository.html) has been used to have a common domain repository layer for both In Memory (Mandatory task 1) and JPA (Mandatory task 2) implementations. In this way, the domain repository is agnostic of all the implementations details (such as DB specific methods) and new datasources (such as MongoDB or Redis) can be easily added without changing the logic of the interface clients. So, we have applied the Dependency Inversion Principle of SOLID to achieve loose coupling when interacting with the data layer. The implementation by default is JPA, but it can be replaced with other implementations when injecting the dependency in the service layer.

Some technical considerations:
- The Pagination and Sorting logic is only implemented for the JPA repository. Reading the requirements, I understand that the JPA implementation is the only that needs to support these advanced features. 

Bonus steps
- Pagination to collection endpoints
- Beer Search Funcionality
- Punk API integration as an external source of information

Some technical considerations:
- The Beer model contains the attributes 'externalId' and 'externalBeerType', which represent the 'id' and the 'beer type' of the External API (in this case, Punk API). Punk API is added as the first manufacturer of the catalog (see the initialization DB script).
- The Beer search logic is performed by all the attributes. The filter criteria met by the beer retrieved is the following one: The exact name, the description containing the text sent, the graduation between the min and max level (if there is no min level, the retrieved beers will be the ones below the upper limit, and if there is no max level, the retrieved beers will be the ones above the lower limit), and the exact manufacturer id and the beer type id.
- If there no beers retrieved from our DB after performing a search request, the filter criteria consulting Punk API is the following one: The name containing the text sent, and the graduation between the min and max level (if there is no min level, the retrieved beers will be the ones below the upper limit, and if there is no max level, the retrieved beers will be the ones above the lower limit).



## Validate Use Cases

- Manufacturer Creation
- Beer Type Creation
- Beer Creation
- Beer find by id
- Beer find by params
- Update beer 
- delete beer

#### Scenario 1
- Given the providers defined in the table of the exercise, iterate 10 message deliveries and show the providers used for the destination 0034666111222.
- **Expected result**: Given that there are 2 providers (P1 and P3) with the prefix 0034, and they both have the same cost (1), then a random distribution is applied for these two providers. So, most likely distribution will be 5 messages sent by P1 and 5 messages by P3.

#### Scenario 2
- Given the providers defined in the table of the exercise, iterate 10 message deliveries and show the providers used for the destination 0033777111222.
- **Expected result**: Given that there is only 1 provider (P3) with this prefix 0033, P3 will send the 10 messages.

### With Swagger

- Go to message-sender-controller endpoint on *http://localhost:8080/swagger-ui.html*
- Execute the requests below for both scenarios 10 times and verify that the actual result matches the expected result.

#### Scenario 1
POST http://localhost:8080/api/v1/message/send

**Request Body**
```json
{
  "textToSend": "text test",
  "toMobileNumber": "0034666111222"
}
```
**Expected response body** for approximately half of the requests should be something like:
```json
{
  "id": "33c415a4-5bf5-4426-b2d2-bc83abd2b8b1",
  "providerName": "P1"
}
```
And for the other half:
```json
{
  "id": "840c0f5e-f71a-44d2-93f8-dedd724f763a",
  "providerName": "P3"
}
```

#### Scenario 2
POST http://localhost:8080/api/v1/message/send

**Request Body**:
```json
{
  "textToSend": "text test",
  "toMobileNumber": "0033777111222"
}
```
**Expected Response body**:
```json
{
  "id": "88ba0a57-e202-48ac-aa2e-3ceda0ebb6ea",
  "providerName": "P3"
}
```


### With Postman

- Import [this](https://www.getpostman.com/collections/9e4645b9a9ef475846c2) Postman collection, which contains all the REST API requests and execute like specified in the previous section.

### Unit tests

```
# Run the unit tests for all submodules

$ mvn test
```
## Possible improvements for a future version
- Nationality, which is a Manufacturer field, could be defined as an Enumerator in order to limit the range of possible values.
- Tests on repository layer
- 2 Missing bonus steps
- Implement pagination and sortin in inmemory repository implementation.


