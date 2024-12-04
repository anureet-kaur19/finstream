FROM arm64v8/eclipse-temurin:21-jdk-alpine
EXPOSE 8081

COPY target/analytics-service-*.jar /analytics-service.jar

ENTRYPOINT ["java", "-jar", "analytics-service.jar"]