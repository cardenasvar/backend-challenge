FROM gradle:8.6-jdk21-alpine AS builder
WORKDIR /workspace
COPY . .
RUN gradle clean build -x test

FROM openjdk:21-jdk-slim
COPY --from=builder /workspace/build/libs/*.jar app.jar
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/operaciones-y-ejecucion/operaciones-de-mercado/v1/actuator/health || exit 1
ENTRYPOINT ["java","-jar","/app.jar"]