# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

COPY ./target/ecommerce-0.0.1-SNAPSHOT.jar /app

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "ecommerce-0.0.1-SNAPSHOT.jar"]
