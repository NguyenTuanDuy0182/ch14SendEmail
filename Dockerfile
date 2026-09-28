# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Download dependencies (this helps cache dependencies in Docker)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application (package it into a .war file)
RUN mvn clean package -DskipTests

# Stage 2: Setup the runtime environment
# Tomcat 10.1 is required for Jakarta EE 10 / Servlet 6.1
FROM tomcat:10.1-jdk21

# (Optional) Remove default Tomcat webapps to keep it clean
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the compiled .war file from the builder stage
# We rename it to ROOT.war so it serves as the root application at /
COPY --from=builder /app/target/ch13EmailList-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose the default Tomcat port
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
