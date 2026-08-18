# 📋 Documentación de Endpoints - ResuelveYa API

**Base URL:** `http://localhost:8080`

**Versión:** v1

---

## 🔐 Autenticación

Todos los endpoints protegidos requieren incluir el token JWT en el header:

```
Authorization: Bearer <token>
```

**Tokens válidos obtienen:**
- `token`: JWT con roles incluidos
- `tipo`: "Bearer"
- `expiresIn`: 3600000 ms (1 hora)
- `email`: Email del usuario
- `rol`: Rol asignado (ADMIN, CLIENTE, TECNICO)

---

## 🔓 Autenticación (Sin protección)

### 1. Registro de usuario
- **Método:** `POST`
- **Ruta:** `/api/v1/auth/registro`
- **Autenticación:** ❌ No requerida
- **Autorización:** ❌ No requerida

**Body (Request):**
```json
{
  "nombre": "string (max 80 caracteres, obligatorio)",
  "email": "string (formato email, max 100, obligatorio)",
  "password": "string (debe contener mayúscula, minúscula y número, obligatorio)",
  "telefono": "string (max 9 caracteres, opcional)",
  "rol": "enum: ADMIN | CLIENTE | TECNICO (obligatorio)"
}
```

**Response (200):**
```json
{
  "id": "long",
  "nombre": "string",
  "email": "string",
  "telefono": "string",
  "rol": "string (ADMIN | CLIENTE | TECNICO)"
}
```

---

### 2. Iniciar sesión (Login)
- **Método:** `POST`
- **Ruta:** `/api/v1/auth/login`
- **Autenticación:** ❌ No requerida
- **Autorización:** ❌ No requerida

**Body (Request):**
```json
{
  "email": "string (obligatorio)",
  "password": "string (obligatorio)"
}
```

**Response (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "expiresIn": 3600000,
  "email": "usuario@mail.com",
  "rol": "ADMIN"
}
```

**Error (401):**
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Credenciales inválidas"
}
```

---

## 👥 Usuarios (Protegido - Requiere ADMIN)

### 3. Obtener todos los usuarios
- **Método:** `GET`
- **Ruta:** `/api/v1/usuarios`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Parámetros:** Ninguno

**Response (200):**
```json
[
  {
    "id": "long",
    "nombre": "string",
    "email": "string",
    "telefono": "string",
    "rol": "string (ADMIN | CLIENTE | TECNICO)"
  },
  ...
]
```

---

### 4. Obtener usuario por ID
- **Método:** `GET`
- **Ruta:** `/api/v1/usuarios/{id}`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Parámetros:**
- `id` (Path): ID del usuario (long)

**Response (200):**
```json
{
  "id": "long",
  "nombre": "string",
  "email": "string",
  "telefono": "string",
  "rol": "string"
}
```

---

### 5. Crear usuario
- **Método:** `POST`
- **Ruta:** `/api/v1/usuarios`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Body (Request):**
```json
{
  "nombre": "string (max 80, obligatorio)",
  "email": "string (email válido, max 100, obligatorio)",
  "password": "string (mayúscula + minúscula + número, obligatorio)",
  "telefono": "string (max 9, opcional)",
  "rol": "enum: ADMIN | CLIENTE | TECNICO (obligatorio)"
}
```

**Response (201):**
```json
{
  "id": "long",
  "nombre": "string",
  "email": "string",
  "telefono": "string",
  "rol": "string"
}
```

---

### 6. Actualizar usuario
- **Método:** `PUT`
- **Ruta:** `/api/v1/usuarios/{id}`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Parámetros:**
- `id` (Path): ID del usuario (long)

**Body (Request):**
```json
{
  "nombre": "string (max 80, obligatorio)",
  "email": "string (email válido, max 100, obligatorio)",
  "password": "string (mayúscula + minúscula + número, obligatorio)",
  "telefono": "string (max 9, opcional)",
  "rol": "enum: ADMIN | CLIENTE | TECNICO (obligatorio)"
}
```

**Response (200):**
```json
{
  "id": "long",
  "nombre": "string",
  "email": "string",
  "telefono": "string",
  "rol": "string"
}
```

---

### 7. Eliminar usuario
- **Método:** `DELETE`
- **Ruta:** `/api/v1/usuarios/{id}`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Parámetros:**
- `id` (Path): ID del usuario (long)

**Response:** 204 No Content

---

### 8. Buscar usuarios por nombre
- **Método:** `GET`
- **Ruta:** `/api/v1/usuarios/buscar/nombre`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Parámetros:**
- `nombre` (Query): Nombre a buscar (string, obligatorio)

**Response (200):**
```json
[
  {
    "id": "long",
    "nombre": "string",
    "email": "string",
    "telefono": "string",
    "rol": "string"
  },
  ...
]
```

---

### 9. Buscar usuario por email
- **Método:** `GET`
- **Ruta:** `/api/v1/usuarios/buscar/email`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Parámetros:**
- `email` (Query): Email a buscar (string, obligatorio)

**Response (200):**
```json
{
  "id": "long",
  "nombre": "string",
  "email": "string",
  "telefono": "string",
  "rol": "string"
}
```

---

### 10. Consultar usuarios con paginación
- **Método:** `GET`
- **Ruta:** `/api/v1/usuarios/consulta`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Parámetros (Query):**
- `nombre` (Query): Filtro opcional por nombre
- `page` (Query): Número de página (default: 0)
- `size` (Query): Cantidad por página (default: 5)
- `sort` (Query): Campo para ordenar (default: "nombre")

**Response (200):**
```json
{
  "content": [
    {
      "id": "long",
      "nombre": "string",
      "email": "string",
      "telefono": "string",
      "rol": "string"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5,
    "sort": ["nombre"]
  },
  "totalElements": "long",
  "totalPages": "int",
  "first": "boolean",
  "last": "boolean",
  "hasNext": "boolean",
  "hasPrevious": "boolean"
}
```

---

## 🔧 Técnicos (Protegido)

### 11. Obtener todos los técnicos
- **Método:** `GET`
- **Ruta:** `/api/v1/tecnicos`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ (Rol requerido: verificar SecurityConfig)

**Parámetros:** Ninguno

**Response (200):**
```json
[
  {
    "id": "long",
    "nombre": "string",
    "email": "string",
    "telefono": "string",
    "aniosExperiencia": "int",
    "calificacionPromedio": "double (0-5)",
    "especialidadId": "long",
    "nombreEspecialidad": "string"
  },
  ...
]
```

---

### 12. Obtener técnico por ID
- **Método:** `GET`
- **Ruta:** `/api/v1/tecnicos/{id}`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ (Rol requerido: verificar SecurityConfig)

**Parámetros:**
- `id` (Path): ID del técnico (long)

**Response (200):**
```json
{
  "id": "long",
  "nombre": "string",
  "email": "string",
  "telefono": "string",
  "aniosExperiencia": "int",
  "calificacionPromedio": "double",
  "especialidadId": "long",
  "nombreEspecialidad": "string"
}
```

---

### 13. Crear técnico
- **Método:** `POST`
- **Ruta:** `/api/v1/tecnicos`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Body (Request):**
```json
{
  "nombre": "string (max 100, obligatorio)",
  "email": "string (email válido, max 100, obligatorio)",
  "telefono": "string (max 20, obligatorio)",
  "aniosExperiencia": "int (>= 0, obligatorio)",
  "calificacionPromedio": "double (0-5, obligatorio)",
  "especialidadId": "long (obligatorio)"
}
```

**Response (201):**
```json
{
  "id": "long",
  "nombre": "string",
  "email": "string",
  "telefono": "string",
  "aniosExperiencia": "int",
  "calificacionPromedio": "double",
  "especialidadId": "long",
  "nombreEspecialidad": "string"
}
```

---

### 14. Actualizar técnico
- **Método:** `PUT`
- **Ruta:** `/api/v1/tecnicos/{id}`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Parámetros:**
- `id` (Path): ID del técnico (long)

**Body (Request):**
```json
{
  "nombre": "string (max 100, obligatorio)",
  "email": "string (email válido, max 100, obligatorio)",
  "telefono": "string (max 20, obligatorio)",
  "aniosExperiencia": "int (>= 0, obligatorio)",
  "calificacionPromedio": "double (0-5, obligatorio)",
  "especialidadId": "long (obligatorio)"
}
```

**Response (200):**
```json
{
  "id": "long",
  "nombre": "string",
  "email": "string",
  "telefono": "string",
  "aniosExperiencia": "int",
  "calificacionPromedio": "double",
  "especialidadId": "long",
  "nombreEspecialidad": "string"
}
```

---

### 15. Eliminar técnico
- **Método:** `DELETE`
- **Ruta:** `/api/v1/tecnicos/{id}`
- **Autenticación:** ✅ Requerida
- **Autorización:** ✅ Rol ADMIN

**Parámetros:**
- `id` (Path): ID del técnico (long)

**Response:** 204 No Content

---

## 👤 Clientes (Sin protección específica - OG Controller)

### 16. Obtener todos los clientes
- **Método:** `GET`
- **Ruta:** `/api/clientes`
- **Autenticación:** ❌ No requerida (Verificar)
- **Autorización:** ❌ No requerida (Verificar)

**Response (200):**
```json
[
  {
    "id": "long",
    "usuario": {
      "id": "long",
      "nombre": "string",
      "email": "string",
      "telefono": "string",
      "rol": "string"
    },
    "direccionHogar": "string"
  },
  ...
]
```

---

### 17. Obtener cliente por ID
- **Método:** `GET`
- **Ruta:** `/api/clientes/{id}`
- **Autenticación:** ❌ No requerida
- **Autorización:** ❌ No requerida

**Parámetros:**
- `id` (Path): ID del cliente (long)

**Response (200):**
```json
{
  "id": "long",
  "usuario": {
    "id": "long",
    "nombre": "string",
    "email": "string",
    "telefono": "string",
    "rol": "string"
  },
  "direccionHogar": "string"
}
```

---

### 18. Crear cliente
- **Método:** `POST`
- **Ruta:** `/api/clientes`
- **Autenticación:** ❌ No requerida
- **Autorización:** ❌ No requerida

**Body (Request):**
```json
{
  "usuario": {
    "id": "long (opcional si es nuevo)",
    "nombre": "string",
    "email": "string",
    "telefono": "string",
    "rol": "CLIENTE"
  },
  "direccionHogar": "string"
}
```

**Response (201):**
```json
{
  "id": "long",
  "usuario": {...},
  "direccionHogar": "string"
}
```

---

### 19. Actualizar cliente
- **Método:** `PUT`
- **Ruta:** `/api/clientes/{id}`
- **Autenticación:** ❌ No requerida
- **Autorización:** ❌ No requerida

**Parámetros:**
- `id` (Path): ID del cliente (long)

**Body (Request):**
```json
{
  "nombre": "string",
  "email": "string",
  "telefono": "string",
  "direccionHogar": "string"
}
```

**Response (200):**
```json
{
  "id": "long",
  "usuario": {...},
  "direccionHogar": "string"
}
```

---

### 20. Eliminar cliente
- **Método:** `DELETE`
- **Ruta:** `/api/clientes/{id}`
- **Autenticación:** ❌ No requerida
- **Autorización:** ❌ No requerida

**Parámetros:**
- `id` (Path): ID del cliente (long)

**Response:** 204 No Content

---

## 🎓 Especialidades (Sin protección específica)

### 21. Obtener todas las especialidades
- **Método:** `GET`
- **Ruta:** `/api/especialidades`
- **Autenticación:** ❌ No requerida
- **Autorización:** ❌ No requerida

**Response (200):**
```json
[
  {
    "id": "long",
    "nombre": "string",
    "descripcion": "string"
  },
  ...
]
```

---

### 22. Obtener especialidad por ID
- **Método:** `GET`
- **Ruta:** `/api/especialidades/{id}`
- **Autenticación:** ❌ No requerida
- **Autorización:** ❌ No requerida

**Parámetros:**
- `id` (Path): ID de la especialidad (long)

**Response (200):**
```json
{
  "id": "long",
  "nombre": "string",
  "descripcion": "string"
}
```

---

### 23. Crear especialidad
- **Método:** `POST`
- **Ruta:** `/api/especialidades`
- **Autenticación:** ❌ No requerida (Probablemente requiera ADMIN)
- **Autorización:** ❌ No requerida

**Body (Request):**
```json
{
  "nombre": "string",
  "descripcion": "string"
}
```

**Response (201):**
```json
{
  "id": "long",
  "nombre": "string",
  "descripcion": "string"
}
```

---

### 24. Actualizar especialidad
- **Método:** `PUT`
- **Ruta:** `/api/especialidades/{id}`
- **Autenticación:** ❌ No requerida
- **Autorización:** ❌ No requerida

**Parámetros:**
- `id` (Path): ID de la especialidad (long)

**Body (Request):**
```json
{
  "nombre": "string",
  "descripcion": "string"
}
```

**Response (200):**
```json
{
  "id": "long",
  "nombre": "string",
  "descripcion": "string"
}
```

---

### 25. Eliminar especialidad
- **Método:** `DELETE`
- **Ruta:** `/api/especialidades/{id}`
- **Autenticación:** ❌ No requerida
- **Autorización:** ❌ No requerida

**Parámetros:**
- `id` (Path): ID de la especialidad (long)

**Response:** 204 No Content

---

## 📝 Enums y Constantes

### Rol (enum)
```
ADMIN      - Administrador del sistema
CLIENTE    - Cliente que solicita servicios
TECNICO    - Técnico que brinda servicios
```

### Códigos de Error HTTP
```
200 OK              - Solicitud exitosa
201 Created         - Recurso creado exitosamente
204 No Content      - Eliminación exitosa
400 Bad Request     - Datos inválidos
401 Unauthorized    - Token inválido o no autenticado
403 Forbidden       - Usuario autenticado pero sin permisos
404 Not Found       - Recurso no encontrado
500 Internal Server - Error del servidor
```

---

## 🔒 Seguridad - Notas importantes

1. **Token JWT:** Válido por 1 hora (3600000 ms)
2. **Contraseña:** Debe contener:
   - Al menos una mayúscula (A-Z)
   - Al menos una minúscula (a-z)
   - Al menos un número (0-9)
3. **Email:** Debe ser único en el sistema
4. **Rol ADMIN:** Requerido para operaciones CRUD en usuarios
5. **Header Authorization:** Formato: `Bearer <token>` (importante incluir "Bearer ")

---

## 🧪 Ejemplo de flujo completo

### 1. Registro
```bash
curl -X POST http://localhost:8080/api/v1/auth/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Pérez",
    "email": "juan@example.com",
    "password": "Password123",
    "telefono": "987654321",
    "rol": "ADMIN"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan@example.com",
    "password": "Password123"
  }'
```

Respuesta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "expiresIn": 3600000,
  "email": "juan@example.com",
  "rol": "ADMIN"
}
```

### 3. Usar token para acceder a endpoint protegido
```bash
curl -X GET http://localhost:8080/api/v1/usuarios \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

## 📞 Estado actual de endpoints

| Endpoint | Estado | Notas |
|----------|--------|-------|
| `/api/v1/auth/registro` | ✅ Funcional | Sin autenticación |
| `/api/v1/auth/login` | ✅ Funcional | Retorna JWT con roles |
| `/api/v1/usuarios` (GET) | ✅ Funcional | Requiere ADMIN |
| `/api/v1/usuarios/{id}` | ✅ Funcional | Requiere ADMIN |
| `/api/v1/usuarios` (POST) | ✅ Funcional | Requiere ADMIN |
| `/api/v1/usuarios/{id}` (PUT) | ✅ Funcional | Requiere ADMIN |
| `/api/v1/usuarios/{id}` (DELETE) | ✅ Funcional | Requiere ADMIN |
| `/api/v1/usuarios/buscar/*` | ✅ Funcional | Requiere ADMIN |
| `/api/v1/tecnicos` | ✅ Funcional | Verificar permisos |
| `/api/clientes` | ✅ Funcional | Sin autenticación |
| `/api/especialidades` | ✅ Funcional | Sin autenticación |

---

**Última actualización:** 2026-08-08
**Documentación generada por:** GitHub Copilot CLI

