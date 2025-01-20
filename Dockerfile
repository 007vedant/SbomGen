# Building the application using Maven
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Metadata about the image
LABEL maintainer="https://github.com/007vedant"
LABEL version="1.0.0"
LABEL description="SBOMGen command line tool for generating SBOMs and searching packages"
LABEL license="Apache License 2.0"

# Setting up the working directory in the container
WORKDIR /app

# Copying Maven configuration and source files
COPY pom.xml ./
COPY src ./src

# Building the application
RUN mvn clean package -DskipTests

# Using JDK 21 runtime as the base image
FROM openjdk:21-slim

# Installing dependencies and Syft
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    && curl -sSfL https://raw.githubusercontent.com/anchore/syft/main/install.sh | sh -s -- -b /usr/local/bin \
    && apt-get remove -y curl \
    && apt-get autoremove -y \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Verifying Syft installation
RUN syft --version

# Copying the packaged JAR file into the container
COPY target/SbomGen-1.0-SNAPSHOT-jar-with-dependencies.jar /app/sbomgen.jar

# Copying the wrapper script into the container
COPY run-sbomgen.sh /app/run-sbomgen.sh

# Making the wrapper script executable
RUN chmod +x /app/run-sbomgen.sh

# Setting up the entry point to the shell script
ENTRYPOINT ["/app/run-sbomgen.sh"]