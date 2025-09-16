# README #
# Backend challenge #

API RESTful para cálculo financiero con porcentaje dinámico.

**Características:**
* Cálculo con porcentaje dinámico: suma de dos números con porcentaje obtenido de servicio externo
* Caché Redis: almacenamiento de porcentaje por 30 minutos con fallback
* Historial asincrono: registro de todas las operaciones en PostgreSQL

**Requisitos:**
* Java 21
* Docker y Docker Compose 
* Gradle 8.6+

**Tecnologías:**
* Springboot 3.5.5
* Base de datos PostreSql para historial
* Redis: caché distribuida
* SpringDoc OpenApi
* Docker

## Instalación y ejecución de la aplicación ##

### Opción 1: Usar imagen pre-construida (recomendado para pruebas rápidas)
**Clonar el repositorio:**
* git clone https://github.com/cardenasvar/backend-challenge.git
* cd backend-challenge/docker

**Ejecutar Docker compose**
* docker compose up -d

La aplicación estará disponible en: http://localhost:8080

### Opción 2: Desarrollo local (build desde código fuente)
**Clonar el repositorio:**
* git clone https://github.com/cardenasvar/backend-challenge.git
* cd backend-challenge

**Construir la aplicación:**
* gradlew clean build

**Ejecutar Docker compose**
* **docker compose up -d**

### Opción 3: Solo infraestrutura (db + redis)
* docker compose up -d db redis

### Ejecutar test:
* **gradlew test**
* **gradlew build jacocoTestReport**

***

## REST API 

### POST - Cálculo con porcentaje

`curl -X POST \
  http://localhost:8080/operaciones-y-ejecucion/operaciones-de-mercado/v1/calculadora-offertas/calculo-porcentaje \
  -H "Content-Type: application/json" \
  -d '{"num1": 100, "num2": 50}'`

### GET - Historial de solicitudes 

`curl "http://localhost:8080/operaciones-y-ejecucion/operaciones-de-mercado/v1/historial-operaciones?page=0&size=10"`

### GET - Documentación Swagger

`http://localhost:8080/operaciones-y-ejecucion/operaciones-de-mercado/v1/swagger-ui/index.html`

***

## Docker Compose 

| Servicio   | Puerto | Descripción            |
|------------|--------|------------------------|
| App        | 8080   | Aplicación Spring Boot |
| PostgreSQL | 5432   | Base de datos          |
| Redis      | 6379   | Caché en memoria       |

***

## Configuración

### Variables de entorno principales

* CONTEXT_PATH: /operaciones-y-ejecucion/operaciones-de-mercado/v1
* SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/challenge
* SPRING_DATA_REDIS_HOST: redis