# Proyecto con PostgreSQL y pgAdmin usando Docker

Este proyecto incluye un entorno de desarrollo que utiliza PostgreSQL como base de datos y `pgAdmin` para la gestión de la base de datos. Todo está configurado a través de `Docker` para facilitar la instalación y configuración.

## Requisitos previos

- [Docker](https://www.docker.com/products/docker-desktop) debe estar instalado en tu máquina.

## Instrucciones

1. Clona el repositorio:

   ```bash
   git clone https://github.com/tu-usuario/tu-proyecto.git
   cd tu-proyecto

2. Levanta los servicios de PostgreSQL y pgAdmin usando Docker Compose:

    ```bash
    docker-compose up
   
- Esto descargará las imágenes necesarias y levantará los contenedores.

3. Accede a pgAdmin desde el navegador en la URL: http://localhost:5050 usando las siguientes credenciales:

- Email: admin@admin.com
- Contraseña: admin

4. En pgAdmin, crea una nueva conexión de base de datos con los siguientes parámetros:

- Host: postgres-db
- Port: 5432
- Username: postgres
- Password: admin

## Levantar el proyecto
- Por ultimo levanta el proyecto desde el IDE y puedes navegar hacia la siguiente direccion para probar los controladores: http://localhost:8080/swagger-ui/index.html
 