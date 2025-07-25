# CatApiWeb

CatApiWeb es una aplicación backend desarrollada en Spring Boot para gestionar y exponer información sobre gatos a través de una API.

## Características

- API RESTful para gestión de datos de gatos.
- Integración con bases de datos relacionales.
- Documentación clara y endpoints bien definidos (Swagger/OpenAPI).

## Requisitos

- [Java JDK](https://adoptium.net/) >= 17
- [Maven](https://maven.apache.org/) >= 3.8

## Instalación

```bash
git clone https://github.com/tuusuario/catapi-web-back.git
cd catapi-web-back/catapiweb
mvn clean install
```

## Uso

```bash
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

## Scripts útiles

- `mvn spring-boot:run` — Inicia el servidor.
- `mvn test` — Ejecuta los tests.
- `mvn package` — Empaqueta la aplicación.

## Estructura del proyecto

```
catapiweb/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/quiz.jm/catapiweb/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── CatApiWebApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│       └── java/
│           └── com/quiz.jm/catapiweb/
├── pom.xml
└── README.md
```

## Contribución

¡Las contribuciones son bienvenidas! Por favor, abre un issue o pull request.

## Configuración de `application.properties`

Crea el archivo `src/main/resources/application.properties` para definir la configuración de la base de datos y otros parámetros de la aplicación. Ejemplo básico para una base de datos H2:

```properties
spring.application.name=catapiweb
# TheCatAPI Configuration
thecatapi.base-url=https://api.thecatapi.com/v1
thecatapi.api-key=
spring.datasource.url=jdbc:h2:mem:catdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true

logging.level.com.yourcompany.catapi=DEBUG

jwt.secret= 
jwt.expiration=3600000
```

Ajusta estos valores según el motor de base de datos que utilices.