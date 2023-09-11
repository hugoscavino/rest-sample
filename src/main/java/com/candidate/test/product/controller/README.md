# Swagger UI

    http://localhost:8081/swagger-ui.html

## Supporting Stories

### Add product to warehouse
     Given: there is an existing warehouse
     When: a product is added to that warehouse
     Then: a record will exist in the database showing that new product is available from that warehouse

### Update product name
     Given: there is an existing product
     When: a product name is changed
     Then: the new name will be updated in the database

### Transfer product from one warehouse to another
     Given: there are two unique warehouses
     When: a product is transferred from one warehouse to another
     Then: the product will no longer be found in the original warehouse,
   	        and it will only be in the warehouse it was transferred to

### Mechanism to retrieve products filtered by a warehouse
     Given: there are several products in the database
     When: a call is made to filter those products by one of their properties
     Then: a filtered list is returned giving back only those products matching the filter criteria

### Mechanism to retrieve products filtered given by wildcard name criteria
     Given: there are several products with different names
     When: a call is made to filter those products based on their name
     Then: a filtered list is returned giving back only those products matching the filter criteria

#### Delete a product</strong><br>
     Given: a product in the database
     When: a call is made to delete the product
     Then: the product is no longer available to be retrieved in the system