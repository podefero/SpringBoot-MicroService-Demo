# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /order-system

# Copy the .jar file from the 'out' folder to the container
COPY out/*.jar order-system.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","order-system.jar"]
