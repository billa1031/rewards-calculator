# Rewards Calculator

#####The project has one end point to retrieve the customer rewards based on customer Id
```
 http://localhost:8080/customers/{customerId}/rewards
```
- The package name is structured as com."org"."domain"
- The endpoint check if customer is available in the system. If so responds with rewards per each month. If not send Bad Request response.
- Uses H2 in-memory database to prepopulate the data.
- Data base objects are added under src/main/java/entity/ and objects representing data in the application are added under src/main/java/model/
- loads rewards threshold config ($100 & $50) from classpath property file.
- results are uploaded under src/main/resources/results/

