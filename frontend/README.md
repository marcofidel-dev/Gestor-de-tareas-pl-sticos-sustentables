# Frontend — Gestor de Tareas

Frontend estatico (HTML/CSS/JS puro, sin build) que consume la API REST del
backend Spring Boot (`../Gestor-de-tareas-pl-sticos-sustentables`).

## Estructura

```
frontend/
├── index.html        # pagina de login
├── css/styles.css
└── js/
    ├── config.js      # URL base de la API
    ├── auth.js        # llamada a /api/auth/login + manejo de sesion (localStorage)
    └── login.js        # logica de la pagina de login
```

## Como ejecutar

1. Levanta el backend (desde la carpeta del proyecto Spring Boot):
   ```bash
   mvn spring-boot:run
   ```
   Queda escuchando en `http://localhost:8080`.

2. Sirve esta carpeta con un servidor estatico (no abrir el `index.html` con
   doble clic / `file://`, porque el navegador bloquea las peticiones CORS
   desde ese origen). Cualquiera de estas opciones funciona:

   - **VS Code + extension "Live Server"** → clic derecho en `index.html` →
     "Open with Live Server" (usa el puerto `5500` por defecto).
   - **Python** (si lo tienes instalado):
     ```bash
     cd frontend
     python -m http.server 8000
     ```
     y abre `http://localhost:8000`.

   El backend ya tiene habilitados por defecto los orígenes
   `http://localhost:5500`, `http://127.0.0.1:5500`, `http://localhost:8000`
   y `http://127.0.0.1:8000` (ver `app.cors.allowed-origins` en
   `application.properties`). Si usas otro puerto, agrégalo a esa propiedad.

## Como probar el login

Como el registro de usuarios aun no tiene pantalla propia, crea un usuario de
prueba directamente contra la API (por ejemplo con `curl`):

```bash
curl -X POST http://localhost:8080/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{"nombreCompleto":"Ana Perez","correo":"ana@empresa.com","contrasena":"secreta123","confirmacionContrasena":"secreta123"}'
```

Luego inicia sesion en la pagina con `ana@empresa.com` / `secreta123`.

Al iniciar sesion correctamente, el token JWT se guarda en
`localStorage` (clave `gestorTareas.sesion`) junto con su fecha de
expiracion absoluta, listo para que las siguientes pantallas lo reutilicen
en el header `Authorization: Bearer <token>`.

## Pendiente (siguientes fases)

- Pantalla de registro.
- Pantalla/dashboard post-login (hoy solo se muestra un mensaje de bienvenida).
- Reutilizar el token guardado para llamar a endpoints protegidos.
