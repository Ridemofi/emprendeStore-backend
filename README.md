# 🚀 EmprendeStore Backend

Este proyecto es el backend de EmprendeStore, una plataforma para la gestión de emprendimientos, ventas y usuarios. Está desarrollado en Java utilizando Spring Boot.

## ✨ Características principales
- 👤 Gestión de usuarios, productos, ventas, pedidos y ubicaciones.
- 🔒 Autenticación y autorización con JWT.
- 🌐 API RESTful para interacción con el frontend.
- 🧪 Pruebas unitarias y de integración.

## 🗂️ Estructura del proyecto
- `src/main/java/com/emprendeStore/`: Código fuente principal.
  - `application/`: ⚙️ Configuración y servicios de aplicación.
  - `domain/`: 🗃️ Modelos de dominio y repositorios.
  - `security/`: 🔐 Seguridad y autenticación.
  - `web/`: 🖥️ Controladores y DTOs.
- `src/main/resources/`: 📄 Archivos de configuración (por ejemplo, `application.yaml`).
- `src/test/java/com/emprendeStore/`: 🧪 Pruebas unitarias.

## 📋 Requisitos
- ☕ Java 17+
- 🐘 Maven
- 🐳 (Opcional) Docker para despliegue

## ⚙️ Instalación
1. Clona el repositorio:
   ```bash
   git clone <url-del-repositorio>
   ```
2. Accede al directorio del proyecto:
   ```bash
   cd emprendeStore-backend
   ```
3. Compila el proyecto:
   ```bash
   ./mvnw clean install
   ```
4. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

## ⚠️ Variables de entorno necesarias
Para que el proyecto inicie correctamente, asegúrate de definir las siguientes variables de entorno (por ejemplo, en tu sistema o en el archivo `application.yaml`):

- `DB_URL`: URL de la base de datos
- `DB_USERNAME`: Usuario de la base de datos
- `DB_PASSWORD`: Contraseña de la base de datos
- `JWT_SECRET`: Clave secreta para JWT
- `RESEND_API_KEY`: API key para el servicio de envío de correos (Resend)
- `CLOUDINARY_API_KEY`: API key para Cloudinary

Sin estas variables, la aplicación no funcionará correctamente.

## 🧪 Pruebas
Para ejecutar las pruebas:
```bash
./mvnw test
```

## 🚢 Despliegue
Puedes usar los scripts `deploy.sh` o `deploy.ps1` para desplegar el proyecto.

## 🌍 Página desplegada
Puedes acceder a la versión desplegada del backend en:

[https://ridemofi.me](https://ridemofi.me)

## 🤝 Contribución
1. Haz un fork del repositorio.
2. Crea una nueva rama para tu feature o fix.
3. Envía un pull request.

## 📄 Licencia
Este proyecto está bajo la licencia MIT.

## 📬 Contacto
Para dudas o sugerencias, contacta a los administradores del proyecto.
