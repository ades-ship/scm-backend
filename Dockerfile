# Use the latest official OpenJDK image
FROM openjdk:latest

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/v1-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
