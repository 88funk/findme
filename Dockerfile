# Stage 1: Build (optional, include only if building inside Docker)
# FROM maven:3.9.6-amazoncorretto-24 AS build
# WORKDIR /app
# COPY . .
# RUN mvn clean package -DskipTests -Dmaven.compiler.release=24 -Dmaven.compiler.compilerArgs=--enable-preview

# Final image
# Use Maven image to build the app
FROM maven:4.0.0-rc-5-sapmachine-21 AS build

WORKDIR /app

# Copy pom.xml and dependencies first
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a slim JDK image to run the jar
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy built jar from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Optional: support dynamic ports from Render
ENV PORT 8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

