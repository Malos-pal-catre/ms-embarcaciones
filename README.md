# ms-embarcaciones

Microservicio encargado de la gestión de las embarcaciones de pesca artesanal registradas en la **Caleta Lo Abarca**. Forma parte del sistema de gestión de subasta artesanal desarrollado con arquitectura de microservicios Spring Boot.

## ¿Qué hace?

Administra el registro y ciclo de vida de las embarcaciones. Almacena su matrícula, nombre, eslora (largo en metros), zona de pesca autorizada y el pescador propietario. Garantiza que ninguna embarcación supere los 18 metros de eslora, cumpliendo la normativa de pesca artesanal que diferencia esta actividad de la pesca industrial.

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/embarcaciones` | Lista todas las embarcaciones |
| GET | `/api/embarcaciones/{id}` | Busca una embarcación por ID |
| GET | `/api/embarcaciones/matricula/{matricula}` | Busca por matrícula |
| GET | `/api/embarcaciones/pescador/{pescadorId}` | Lista embarcaciones de un pescador |
| GET | `/api/embarcaciones/activas` | Lista solo las embarcaciones activas |
| GET | `/api/embarcaciones/zona?zona=` | Lista embarcaciones activas por zona |
| POST | `/api/embarcaciones` | Registra una nueva embarcación |
| PUT | `/api/embarcaciones/{id}` | Actualiza los datos de una embarcación |
| DELETE | `/api/embarcaciones/{id}` | Elimina una embarcación |

## Ejemplo de uso

**Registrar embarcación:**
```json
POST /api/embarcaciones
{
  "matricula": "MAT-001",
  "nombre": "Don Segundo",
  "eslora": 12.5,
  "zonaAutorizada": "zona-1",
  "pescadorId": 1,
  "activa": true
}
```

**Respuesta:**
```json
{
  "id": 1,
  "matricula": "MAT-001",
  "nombre": "Don Segundo",
  "eslora": 12.5,
  "zonaAutorizada": "zona-1",
  "pescadorId": 1,
  "activa": true
}
```

## Validaciones de negocio

- La matrícula debe ser única en el sistema
- La eslora no puede superar los **18 metros** (límite pesca artesanal)
- El `pescadorId` debe referenciar un pescador existente en `ms-pescadores`

## Tecnologías

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Boot Validation
- PostgreSQL (Neon)
- Lombok

## Configuración

Crear el archivo `src/main/resources/application.properties` con:

```properties
spring.application.name=ms-embarcaciones
server.port=8082

spring.datasource.url=jdbc:postgresql://<HOST>/embarcaciones_db?sslmode=require&channelBinding=require
spring.datasource.username=<USUARIO>
spring.datasource.password=<PASSWORD>
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Cómo correr

```bash
mvnw.cmd spring-boot:run
```

El servicio queda disponible en `http://localhost:8082`

## Estructura del proyecto

```
ms-embarcaciones/
├── controller/    → EmbarcacionController (endpoints REST)
├── service/       → EmbarcacionService (lógica de negocio)
├── repository/    → EmbarcacionRepository (JPA + @Query)
├── model/         → Embarcacion (entidad JPA)
├── dto/           → RequestDTO, ResponseDTO, Mapper
└── exception/     → GlobalExceptionHandler, RecursoNoEncontradoException
```

## Parte del sistema

Este microservicio es parte del sistema **Caleta Lo Abarca** junto a:
`ms-pescadores` · `ms-especies` · `ms-capturas` · `ms-auth` · `ms-subastas` · `ms-compradores` · `ms-pagos` · `ms-bodega` · `ms-vedas` · `ms-reportes`
