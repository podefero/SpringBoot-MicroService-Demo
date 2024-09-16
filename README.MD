Order Processing System Demo using Java Spring Boot, Docker, Kubernetes, Kafka, AWS, Jenkins, and MongoDB

This project demonstrates a real-time order processing system built with microservices architecture. It utilizes Java Spring Boot for building microservices, Docker for containerization, Kubernetes for orchestration, Kafka for messaging, AWS Lambda for serverless functions, Jenkins for CI/CD, and MongoDB for data persistence.

# Configurations
This will go over configurations for each service, what files they use and what we need in those files

## Springboot - application.properties
The file is located in src/main/resources

It's important to note docker network uses the docker name and docker port.
`mongo:27017` instead of `localhost:27017`
```properties
spring.application.name=order-system

spring.thymeleaf.check-template-location=false

# MongoDB configuration
spring.data.mongodb.uri=mongodb://root:example@mongo:27017/
spring.data.mongodb.database=order-service

# swagger-ui custom path
springdoc.swagger-ui.path=/swagger-ui.html

#Kafka configuration

#Bootstrap Servers
spring.kafka.bootstrap-servers=broker:9092

#Consumer Config
spring.kafka.consumer.group-id=order-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.enable-auto-commit=true
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer

#Produce Config
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
```
We are using default serializer and deserializer for kafka.
## Springboot - Dockerfile
Here create a dockerfile in the root directory of project

if building with mvn you should have a .jar file in target. 
```dockerfile
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

```
To build docker image with mvm 

`./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=springio/gs-spring-boot-docker -DskipTests`

You can include tests if you like just remove --DskipTests

## docker-compose.yml
Create docker-compose.yml file in root directory of project
```yaml
  version: '3.1'

  services:
    broker:
      image: apache/kafka:latest
      container_name: broker
      environment:
        KAFKA_NODE_ID: 1
        KAFKA_PROCESS_ROLES: broker,controller
        KAFKA_LISTENERS: PLAINTEXT://broker:9092,CONTROLLER://localhost:9093
        KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://broker:9092
        KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER
        KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
        KAFKA_CONTROLLER_QUORUM_VOTERS: 1@localhost:9093
        KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
        KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
        KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
        KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
        KAFKA_NUM_PARTITIONS: 3

    mongo:
      image: mongo
      restart: always
      ports:
        - "27017:27017"
      environment:
        MONGO_INITDB_ROOT_USERNAME: root
        MONGO_INITDB_ROOT_PASSWORD: example

    mongo-express:
      image: mongo-express
      restart: always
      ports:
        - "8081:8081"
      environment:
        ME_CONFIG_MONGODB_ADMINUSERNAME: root
        ME_CONFIG_MONGODB_ADMINPASSWORD: example
        ME_CONFIG_MONGODB_URL: mongodb://root:example@mongo:27017/
        ME_CONFIG_BASICAUTH: false

    app:
      image: springio/gs-spring-boot-docker:latest  # Your Spring Boot image
      container_name: app
      depends_on:
        - broker
        - mongo
      ports:
        - "8080:8080"

```

# Docker commands

```bash
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=springio/gs-spring-boot-docker -DskipTests
docker compose up --build
```