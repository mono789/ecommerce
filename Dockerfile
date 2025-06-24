# Multi-stage build para optimizar el tamaño de la imagen
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jre-alpine

# Instalar curl para healthcheck
RUN apk add --no-cache curl

# Crear usuario no-root para seguridad
RUN addgroup -g 1001 -S spring && \
    adduser -S spring -u 1001 -G spring

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Crear directorio de logs y asignar permisos
RUN mkdir -p /app/logs && \
    chown -R spring:spring /app

# Cambiar al usuario no-root
USER spring

# Configurar zona horaria
ENV TZ=America/Bogota

# Variables de entorno por defecto
ENV JAVA_OPTS="-Xmx512m -Xms256m" \
    SPRING_PROFILES_ACTIVE=docker

# Exponer puerto
EXPOSE 8080

# Healthcheck
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/api/v1/actuator/health || exit 1

# Comando de entrada
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 