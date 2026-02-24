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

## 🧪 Pruebas
Para ejecutar las pruebas:
```bash
./mvnw test
```

## 🚢 Despliegue
Puedes usar los scripts `deploy.sh` o `deploy.ps1` para desplegar el proyecto.

## 🤝 Contribución
1. Haz un fork del repositorio.
2. Crea una nueva rama para tu feature o fix.
3. Envía un pull request.

## 📄 Licencia
Este proyecto está bajo la licencia MIT.

## 📬 Contacto
Para dudas o sugerencias, contacta a los administradores del proyecto.

