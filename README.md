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
- Docker-compose 1.21.2

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
Please note that if Maven is chosen as a build setup option a MySQL server needs to be up and running. Also, the DB params host, user and password need to be replaced in application.properties file of boot module with the new values. Lastly, execute there the inilization DB script 'database.sql' in resources folder of the boot module.

- Open your browser and test the application on *http://localhost:8080/beer-catalog/api/swagger-ui.html*. The API Documentation (Endpoints, Models...) is included there.

## Technical approach

### Accomplished mandatory tasks

- REST API implementation without persistence
- REST API implementation with persistence

#### Some technical considerations:
- In order to implement the mandatory tasks, [Repository Pattern](https://martinfowler.com/eaaCatalog/repository.html) has been used to have a common domain repository layer for both In Memory (Mandatory task 1) and JPA (Mandatory task 2) implementations. In this way, the domain repository is agnostic of all the implementations details (such as DB specific methods) and new datasources (such as MongoDB or Redis) can be easily added without changing the logic of the interface clients. So, we have applied the Dependency Inversion Principle of SOLID to achieve loose coupling when interacting with the data layer. The implementation by default is JPA, but it can be replaced with the In Memory implementation when injecting the dependency in the service layer.
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
- If there are no beers retrieved from our DB after performing a search request, Punk API will be consulted with the following filter criteria: The name containing the text sent, and the graduation between the min and max level (if there is no min level, the retrieved beers will be the ones below the upper limit, and if there is no max level, the retrieved beers will be the ones above the lower limit).
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
  "id": {manufacturer_created_id},
  "name": "Mahou-San Miguel",
  "nationality": "Spanish"
}
```
#### Beer Type creation
POST /beer/type

**Request Body**
```json
{
  "name": "Indian Pale Ale"
}
```
**Expected response body**
```json
{
  "id": {beer_type_created_id},
  "name": "Indian Pale Ale"
}
```
#### Beer creation
POST /beer

**Request Body**
```json
{
  "name": "Mahou Cinco Estrellas",
  "description": "Una cerveza de alta fermentación pero con todo el ADN de una Cinco Estrellas.",
  "graduation": 4.5,
  "beerTypeId": {beer_type_created_id},
  "manufacturerId": {manufacturer_created_id}
}
```
**Expected Request Body**
```json
{
  "id": {beer_created_id},
  "name": "Mahou Cinco Estrellas",
  "description": "Una cerveza de alta fermentación pero con todo el ADN de una Cinco Estrellas.",
  "graduation": 4.5,
  "beerTypeId": {beer_type_created_id},
  "manufacturerId": {manufacturer_created_id}
}
```
#### Beer retrieval by ID
GET /beer/{beer_created_id}

**Expected Response Body**
```json
{
  "id": {beer_created_id},
  "name": "Mahou Cinco Estrellas",
  "description": "Una cerveza de alta fermentación pero con todo el ADN de una Cinco Estrellas.",
  "graduation": 4.5,
  "beerTypeId": {beer_type_created_id},
  "manufacturerId": {manufacturer_created_id}
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
      "id": {beer_created_id},
      "name": "Mahou Cinco Estrellas",
      "description": "Una cerveza de alta fermentación pero con todo el ADN de una Cinco Estrellas.",
      "graduation": 4.5,
      "beerTypeId": {beer_type_created_id},
      "manufacturerId": {manufacturer_created_id}
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
GET /beer/list?currentPage=0&pageSize=2&sort=id&sort=desc&name=Berliner

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
  "id": {beer_created_id},
  "name": "Mahou Cinco Estrellas Session IPA",
  "description": "La primera Cinco Estrellas que no es una lager, sino una ale;",
  "graduation": 5.0,
  "beerTypeId": {beer_type_created_id},
  "manufacturerId": {manufacturer_created_id}
}
```
**Expected Request Body**
```json
{
  "id": {beer_created_id},
  "name": "Mahou Cinco Estrellas Session IPA",
  "description": "La primera Cinco Estrellas que no es una lager, sino una ale.",
  "graduation": 5,
  ""beerTypeId": {beer_type_created_id},
      "manufacturerId": {manufacturer_created_id}
}
```
#### Beer deletion by ID
DELETE /beer/{beer_created_id}

**Expected Response Body**
```
true
```
The request to get all the beers can be executed again to make sure that this new beer has been properly deleted.

### Unit tests

```
# Run the unit tests for all submodules

$ mvn test
```
## Possible improvements for a future version
- Nationality, which is a Manufacturer field, could be defined as an Enumerator in order to limit the range of possible values.
- Validate the length of the text fields (name and description) and return a Bad Request if they are too long.
- Add Unit Tests on repository layer.
- Implement the 2 missing bonus steps (application securization and file upload for Beer resource)
- Implement pagination and sorting for In Memory repository implementation.
