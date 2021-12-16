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
- Swagger 3.0.0

## Build setup

- Clone this repo to your local machine. This application can be executed either with Docker or with Maven:

### With Docker

```
$ docker-compose up
```
The previous command starts both Spring Boot and MySQL containers.

### With Maven

```
$ mvn spring-boot:run
```

- Open your browser and test the application on *http://localhost:8080/beer-catalog/api/swagger-ui.html*. The API Documentation (Endpoints, Models...) is included there.

## Technical approach

### Accomplished mandatory tasks

- REST API implementation without persistence
- REST API implementation with persistence

#### Some technical considerations:
- In order to implement the mandatory tasks, [Repository Pattern](https://martinfowler.com/eaaCatalog/repository.html) has been used to have a common domain repository layer for both In Memory (Mandatory task 1) and JPA (Mandatory task 2) implementations. In this way, the domain repository is agnostic of all the implementations details (such as DB specific methods) and new datasources (such as MongoDB or Redis) can be easily added without changing the logic of the interface clients. So, we have applied the Dependency Inversion Principle of SOLID to achieve loose coupling when interacting with the data layer. The implementation by default is JPA, but it can be replaced with other implementations when injecting the dependency in the service layer.
- The Pagination and Sorting logic is only implemented for the JPA repository. Reading the requirements, it's understood that the JPA implementation is the only that needs to support these advanced features. 

### Accomplished bonus steps
- Pagination to collection endpoints
- Beer Search Funcionality
- Punk API integration as an external source of information

#### Some technical considerations:
- The Beer model contains the attributes _externalId_ and _externalBeerType_, which represent the _id_ and the _beer type_ of the External API (in this case, [Punk API](https://punkapi.com/documentation/v2)). Punk API is added as the first manufacturer of the catalog (see the inilization DB script 'database.sql' in resources folder of the boot module). Also the the script _database.sql_ performs the following inserts to have some sample data when starting the application:
- - Manufacturers: _Punk API_, _Hijos de Rivera_, _Brauerei Gruppe_
- - Beer Types: _Lager_, _Ale_
- - Beers: _Estrella Galicia_, _Paulaner_
- The Beer search logic is performed by all the attributes. The beers retrieved fulfill the following filter criteria: The exact name, the description containing the text sent, the graduation between the min and max level (if there is no min level, the retrieved beers will be the ones below the upper limit, and if there is no max level, the retrieved beers will be the ones above the lower limit), and the exact manufacturer id and the beer type id.
- If there are no beers retrieved from our DB after performing a search request, Punk API will be consulting with the following filter criteria: The name containing the text sent, and the graduation between the min and max level (if there is no min level, the retrieved beers will be the ones below the upper limit, and if there is no max level, the retrieved beers will be the ones above the lower limit).
- The default Pagination and Sorting values of the API are: Current Page = 0, Page Size = 25 and Sort = id,desc.
- The main status codes of the API are
- - 200: _OK_
- - 400: _Bad Request_
- - 404: _Not Found_

## Validate Use Cases

Base URL: http://localhost:8080/beer-catalog/api 

#### Manufacturer creation
POST /manufacturer

**Request Body**
```json
{
  "name": "Mahou-San Miguel",
  "nationality": "Spanish"
}
```
**Expected response body**
```json
{
  "id": 4,
  "name": "Mahou-San Miguel",
  "nationality": "Spanish"
}
```
#### Beer Type creation
POST /beer/type

**Request Body**
```json
{
  "id": 3,
  "name": "Indian Pale Ale"
}
```
#### Beer creation
POST /beer

**Request Body**
```json
{
  "id": 3,
  "name": "Mahou Cinco Estrellas",
  "description": "Una cerveza de alta fermentación pero con todo el ADN de una Cinco Estrellas.",
  "graduation": 4.5,
  "beerTypeId": 3,
  "manufacturerId": 4
}
```
**Expected Request Body**
```json
{
  "id": 3,
  "name": "Mahou Cinco Estrellas",
  "description": "Una cerveza de alta fermentación pero con todo el ADN de una Cinco Estrellas.",
  "graduation": 4.5,
  "beerTypeId": 3,
  "manufacturerId": 4
}
```
#### Beer retrieval by ID
GET /beer/3

**Expected Response Body**
```json
{
  "id": 3,
  "name": "Mahou Cinco Estrellas",
  "description": "Una cerveza de alta fermentación pero con todo el ADN de una Cinco Estrellas.",
  "graduation": 4.5,
  "beerTypeId": 3,
  "manufacturerId": 4
}
```
#### Search functionality
#### Beer retrieval by Params (all beers sorted ascendently by graduation)
GET /beer/list?currentPage=0&pageSize=25&sort=graduation&sort=asc

**Expected Request Body**
```json
{
  "itemList": [
    {
      "id": 3,
      "name": "Mahou Cinco Estrellas",
      "description": "Una cerveza de alta fermentación pero con todo el ADN de una Cinco Estrellas.",
      "graduation": 4.5,
      "beerTypeId": 3,
      "manufacturerId": 4
    },
    {
      "id": 1,
      "name": "Estrella Galicia",
      "description": "Bright golden beer with especially bitter malts",
      "graduation": 4.7,
      "beerTypeId": 1,
      "manufacturerId": 2
    },
    {
      "id": 2,
      "name": "Paulaner",
      "description": "Pleasantly malty beer with subtle notes of hops",
      "graduation": 5.5,
      "beerTypeId": 2,
      "manufacturerId": 3
    }
  ],
  "currentPage": 0,
  "totalItems": 3,
  "totalPages": 1
}
```

#### Beer retrieval by Params (search by name, beer present in the DB)
GET /beer/list?currentPage=0&pageSize=25&sort=id&sort=desc&name=Paulaner
**Expected Request Body**
```json
{
  "itemList": [
    {
      "id": 2,
      "name": "Paulaner",
      "description": "Pleasantly malty beer with subtle notes of hops",
      "graduation": 5.5,
      "beerTypeId": 2,
      "manufacturerId": 3
    }
  ],
  "currentPage": 0,
  "totalItems": 1,
  "totalPages": 1
}
```

#### Beer retrieval by Params (search by name, beer NOT present in the DB)
GET /beer/3/list?currentPage=0&pageSize=2&sort=id&sort=desc&name=Berliner

**Expected Request Body**
```json
{
  "itemList": [
    {
      "name": "Berliner Weisse With Yuzu - B-Sides",
      "description": "Japanese citrus fruit intensifies the sour nature of this German classic.",
      "graduation": 4.2,
      "externalId": 3,
      "externalBeerType": "Japanese Citrus Berliner Weisse.",
      "manufacturerId": 1
    },
    {
      "name": "Berliner Weisse With Raspberries And Rhubarb - B-Sides",
      "description": "Tart, dry and acidic with a punch of summer berry as rhubarb crumble.",
      "graduation": 3.6,
      "externalId": 35,
      "externalBeerType": "Fruity Berliner Weisse.",
      "manufacturerId": 1
    }
  ],
  "currentPage": 0,
  "totalItems": 2,
  "totalPages": 1
}
```
#### Beer update
PUT /beer

**Request Body**
```json
{
  "id": 3,
  "name": "Mahou Cinco Estrellas Session IPA",
  "description": "La primera Cinco Estrellas que no es una lager, sino una ale;",
  "graduation": 5.0,
  "beerTypeId": 3,
  "manufacturerId": 4
}
```
**Expected Request Body**
```json
{
  "id": 3,
  "name": "Mahou Cinco Estrellas Session IPA",
  "description": "La primera Cinco Estrellas que no es una lager, sino una ale.",
  "graduation": 5,
  "beerTypeId": 3,
  "manufacturerId": 4
}
```
#### Beer deletion by ID
DELETE /beer/3

**Expected Response Body**
```
true
```
The request to get all the beers can be executed again to make sure that this new beer has been properly deleted.

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
- Validate the length of the text fields (name and description) and return a Bad Request if they are too long
- Tests on repository layer
- 2 Missing bonus steps
- Implement pagination and sortin in inmemory repository implementation.


