#
# Build stage
#
FROM maven:3.8.2-openjdk-17-slim AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/e-commerce-api-0.0.1-SNAPSHOT.jar e-commerce-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","e-commerce-api.jar"]