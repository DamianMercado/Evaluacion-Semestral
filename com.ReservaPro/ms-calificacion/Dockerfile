# Usar imagen base de Java 17
FROM openjdk:17-jdk-slim

# Instalar curl para healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Crear directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR
COPY target/ms-calificacion-0.0.1-SNAPSHOT.jar app.jar

# Crear directorio para logs
RUN mkdir -p /app/logs

# Exponer el puerto
EXPOSE 8082

# Configurar variables de entorno
ENV SPRING_PROFILES_ACTIVE=docker
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8082/actuator/health || exit 1

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]