FROM adoptopenjdk/openjdk8


EXPOSE 8090

ADD target/database-application.jar database-application.jar

ENTRYPOINT ["java", "-jar", "database-application.jar"]