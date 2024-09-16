# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /order-system

# Copy the .jar file from the target folder to the container
COPY target/*.jar order-system.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","order-system.jar"]
