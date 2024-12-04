FROM arm64v8/eclipse-temurin:21-jdk-alpine
EXPOSE 8082

COPY target/ingestion-service-*.jar /ingestion-service.jar

ENTRYPOINT ["java", "-jar", "ingestion-service.jar"]
