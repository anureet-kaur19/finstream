FROM arm64v8/eclipse-temurin:21-jdk-alpine
EXPOSE 8084

COPY target/storage-service-*.jar /storage-service.jar

ENTRYPOINT ["java", "-jar", "storage-service.jar"]