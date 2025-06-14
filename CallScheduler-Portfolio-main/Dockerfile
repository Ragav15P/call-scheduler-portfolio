# Start with a base image containing Java runtime
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build output (JAR file) into the container. Adjsut the name as needed.
COPY target/personalportfolio-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which your Spring Boot application will run
#EXPOSE 9090

# Run the JAR file
CMD ["java", "-jar", "app.jar"]