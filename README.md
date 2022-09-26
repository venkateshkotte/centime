
# task2 - database-application

This Service Functionality
1. Provides an API to insert bulk data.
2. Provides an API to get specific data by id.
3. Provids an API to get parent child relational data.

Implemented using 
1. Spring boot - service developed using spring boot.
2. H2 Database - in memory database.
3. Spring JPA  - For database opeartions
4. Spring AOP  - For custom logging
5. Spring Cloud Sleuth -  For requests tracing.
6. log4j2 - for logging
7. Swagger UI - for documentation
8. @LogMethodParams - to log the method parameters.
9. Docker - created docker images and deployed in Amazon EC2.

Swagger UI URL
http://ec2-65-2-168-145.ap-south-1.compute.amazonaws.com:8080/swagger-ui.html#/dba-controller

SSH to EC2

ssh -i "mumbai-key-pair.pem" ec2-user@ec2-65-2-168-145.ap-south-1.compute.amazonaws.com

Logs location

/var/lib/docker/overlay2/bf14a500f1e68f1793e115623f601f3a0f77f2792cfee2cc8df0b55ed5b20fbc/merged/tmp/logs/database-application.log
 
