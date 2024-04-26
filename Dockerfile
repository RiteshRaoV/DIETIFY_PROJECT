FROM maven:3.8.4-openjdk-17-slim AS builder
# Set the working directory in the container
WORKDIR /app
# Copy the entire project (source code)
COPY . .
# Run Maven clean and package
RUN mvn clean package -DskipTests
# Start a new stage for the final image
FROM openjdk:17-slim
# Set the working directory in the container
WORKDIR /app
# Copy the JAR file from the builder stage to the final image
COPY --from=builder /app/target/*.jar app.jar
# Expose the port your app runs on
EXPOSE 1111
# Command to run the application
CMD ["java", "-jar", "app.jar"]