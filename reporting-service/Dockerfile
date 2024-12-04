FROM arm64v8/eclipse-temurin:21-jdk-alpine
EXPOSE 8083

COPY target/reporting-service-*.jar /reporting-service.jar

ENTRYPOINT ["java", "-jar", "reporting-service.jar"]