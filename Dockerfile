FROM openjdk:11
WORKDIR /app
COPY /target/student-service-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-jar", "student-service-0.0.1-SNAPSHOT.jar"]