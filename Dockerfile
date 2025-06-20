# === Stage 1: Build the application using Maven ===
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy only pom.xml first to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Now copy the rest of the source code
COPY src ./src

# Build the application
RUN mvn clean install -DskipTests

# === Stage 2: Create minimal runtime image ===
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the built JAR(s) from stage 1
COPY --from=build /app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]
