# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src src

# Skip tests as they are not relevant for the build within docker (no database yet)
RUN mvn package -DskipTests

# Run stage
FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

COPY --from=build /app/target/*.war app.war

ENTRYPOINT ["java", "-jar", "app.war"]
