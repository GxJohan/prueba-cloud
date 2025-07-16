# Guía Simple: Desplegar Spring Boot en Google Cloud Run

## Preparación (3 archivos necesarios)

### 1. Modificar application.properties
Cambiar la línea del puerto a:
```
server.port=${PORT:8080}
```

### 2. Crear archivo Dockerfile
```dockerfile
# Build stage
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

### 3. Crear archivo .dockerignore
```
target/
*.class
.git
.idea
*.iml
```

## Comandos para desplegar (ejecutar en orden)

### Paso 1: Configurar tu proyecto GCP
```bash
gcloud auth login
gcloud config set project TU_ID_PROYECTO
```

### Paso 2: Activar servicios
```bash
gcloud services enable cloudbuild.googleapis.com run.googleapis.com
```

### Paso 3: Construir y subir imagen
```bash
gcloud builds submit --tag gcr.io/TU_ID_PROYECTO/prueba-cloud
```

### Paso 4: Desplegar
```bash
gcloud run deploy prueba-cloud --image gcr.io/TU_ID_PROYECTO/prueba-cloud --platform managed --region us-central1 --allow-unauthenticated
```

## Resultado
Al final obtendrás una URL como: https://prueba-cloud-xxxxx.run.app

Puedes probar tu API en: https://prueba-cloud-xxxxx.run.app/alumnos