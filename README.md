# foodorderingsystem
This is web service for restaurant ordering system.

This Java Servlet-based web service, which listens to GET requests from clients (see https://github.com/KarinaKosiorek/foodorderingsystem-client)
Data about food and client orders are organized in database FoodOrderingSystemDB on MySQL server.
Management of database is done via JDBC (MySQL driver) and Java Hibernate 5.
ORM database table mappings is done with Hibernate Annotations.

Service was tested and deployed on Tomcat 7.0.70 (foodorderingsystem.war located in /foodorderingsystem/target/),
and MySQL 5.7 server with authentication credentials:
user=root
password=root

Service responses to client request with plain text with the information of the result of requested actions.






