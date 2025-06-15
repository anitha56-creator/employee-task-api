FROM eclipse-temurin:17-jdk-alpine AS build

RUN mkdir -p /EmployeeTaskAPI

WORKDIR /EmployeeTaskAPI
COPY employee-task-api /EmployeeTaskAPI


RUN chmod +x ./mvnw
RUN ./mvnw -e clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

COPY --from=build /EmployeeTaskAPI/target/employee-task-api-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

ENTRYPOINT ["java","-jar","employee-task-api-0.0.1-SNAPSHOT.jar"]