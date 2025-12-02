# 1. Imagen con Maven + JDK para construir el jar
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos solo los archivos de configuración primero (para cacheo)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Descargamos dependencias sin compilar código aún
RUN ./mvnw dependency:go-offline || true

# Copiamos el código completo
COPY src ./src

# Construimos el jar (spring boot)
RUN ./mvnw clean package -DskipTests

# 2. Imagen final liviana
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiamos el jar que construyó la etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
