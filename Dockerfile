## Build the application
FROM maven:3.9.5-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

## Run the application
FROM openjdk:21-oracle
WORKDIR /app
COPY --from=build /app/target/calendarapi-0.0.1-SNAPSHOT.jar ./events-api.jar
EXPOSE 9090

ENV DB_URI mongodb+srv://admin:LG1KL55u6FRb4iMk@cluster0.vm2ap.mongodb.net/?retryWrites=true&w=majority
ENV SECRET_KEY 432646294A404E635166546A576E5A7234753778214125442A472D4B61506453

CMD [ "java", "-jar", "events-api.jar" ]