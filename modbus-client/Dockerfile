FROM maven:3.8.6-jdk-11
WORKDIR /app
COPY /heilaiq-modbus-interview /app/heilaiq-modbus-interview
COPY /lib /app/lib
COPY pom.xml /app/
RUN mvn install

FROM openjdk:11-jre-slim
COPY /heilaiq-modbus-interview/target/ /app/heilaiq-modbus-interview/target/
EXPOSE 502
ENTRYPOINT ["java", "-jar", "/app/heilaiq-modbus-interview/target/release-heilaiq-interview-1.0-SNAPSHOT.jar"]
