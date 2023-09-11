# Objective

The objective of this exercise is to create a series of REST services that produce JSON data with accompanying data 
access and database functionality sufficient to complete the user story. There are no UI components and each story
will be accepted using a Postman.

# Setup

## Software

Mac or Windows

    1. Git - In windows you will need to install Git Bash
    2. PgAdmin or other Postgres database tool
    3. Postman or other REST client

## Installation

    Open a terminal session and go to the docker folder in this project
    run ./docker-create.sh this will create the database named product
    if you need to start the database (if database is already created) run ./docker-start.sh
    if you need to destroy the database run ./docker-destroy.sh

## Execution

The project may be run in any IDE that is capable of understanding Gradle project structures or manually
by `gradlew bootRun` or `gradlew.bat bootRun` depending on your operating system.

When the project is started a `product` schema will be created and the `src/main/resources/db/migration/R__create_product_schema.sql` 
database script will execute to perform any SQL statements needed for the exercise.

# Data Model

Product

        A product has a numeric identifier, a name with at least 4 characters and no more than 80, an optional description and a flag indicating if it is in stock.
        A product can only be sold from one warehouse at a time but may be transferred from one warehouse to another.
        
Warehouse

        A warehouse has a numeric identifier, a name with at least 10 characters and no more than 80 and a zip code.

# Stories

1 - Add product to warehouse
   
        Given: there is an existing warehouse
        When: a product is added to that warehouse
        Then: a record will exist in the database showing that new product is available from that warehouse

2 - Update product name
   
        Given: there is an existing product
        When: a product name is changed
        Then: the new name will be updated in the database

3 - Transfer product from one warehouse to another
        
        Given: there are two unique warehouses
        When: a product is transferred from one warehouse to another
        Then: the product will no longer be found in the original warehouse,
      	        and it will only be in the warehouse it was transferred to

4 - Mechanism to retrieve products filtered by a warehouse
        
        Given: there are several products in the database
	    When: a call is made to filter those products by one of their properties
	    Then: a filtered list is returned giving back only those products matching the filter criteria

5 - Mechanism to retrieve products filtered given by wildcard name criteria
        
        Given: there are several products with different names
	    When: a call is made to filter those products based on their name
	    Then: a filtered list is returned giving back only those products matching the filter criteria
	    
6 - Delete a product
        
        Given: a product in the database
	    When: a call is made to delete the product
	    Then: the product is no longer available to be retrieved in the system
