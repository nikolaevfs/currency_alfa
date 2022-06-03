FROM openjdk:11
EXPOSE 8080
ADD currency-0.0.1-SNAPSHOT.jar currency-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","currency-0.0.1-SNAPSHOT.jar"]