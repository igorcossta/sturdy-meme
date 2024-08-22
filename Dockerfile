FROM maven:3.9.9-eclipse-temurin-22 as build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]