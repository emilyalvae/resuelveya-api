# 📋 Documentación de Endpoints - ResuelveYa API

**Base URL:** `http://localhost:8080`  
**Versión:** v1  

---

## 🔐 Autenticación y Seguridad

Todos los endpoints protegidos requieren incluir el token JWT en el header:

```http
Authorization: Bearer <token>
```

### Roles del Sistema:
- **`ADMIN`**: Acceso completo a gestión de usuarios y CRUD de categorías.
- **`TECNICO`**: Gestión de perfil profesional, métodos de pago (Yape/Plin) y CRUD de sus propios servicios ofrecidos.
- **`CLIENTE`**: Actualización de perfil personal, consulta de detalle completo de técnicos y publicación de reseñas.
- **`VISITANTE` (No autenticado)**: Búsqueda pública de servicios, listado público de técnicos y consulta de categorías.

---

## 🔓 1. Autenticación Pública

### 1.1. Registro de usuario
Permite el registro público seleccionando el rol (`CLIENTE` o `TECNICO`).
- **Método:** `POST`
- **Ruta:** `/api/v1/auth/registro`
- **Permisos:** Público (Sin autenticación)

**Body (Request):**
```json
{
  "nombre": "Carlos Gómez",
  "email": "carlos@ejemplo.com",
  "password": "Password123",
  "telefono": "987654321",
  "rol": "TECNICO"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "nombre": "Carlos Gómez",
  "email": "carlos@ejemplo.com",
  "telefono": "987654321",
  "fotoUrl": null,
  "direccion": null,
  "distrito": null,
  "ciudad": null,
  "rol": "TECNICO"
}
```

---

### 1.2. Iniciar sesión (Login)
- **Método:** `POST`
- **Ruta:** `/api/v1/auth/login`
- **Permisos:** Público (Sin autenticación)

**Body (Request):**
```json
{
  "email": "carlos@ejemplo.com",
  "password": "Password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "expiresIn": 3600000,
  "email": "carlos@ejemplo.com",
  "rol": "TECNICO"
}
```

---

## 🔍 2. Catálogo Público y Búsqueda (Visitantes)

### 2.1. Búsqueda pública de servicios
Permite buscar servicios en tiempo real examinando de forma simultánea el **título**, la **descripción** y la **categoría**. Oculta datos sensibles de contacto para visitantes.
- **Método:** `GET`
- **Ruta:** `/api/v1/public/servicios`
- **Permisos:** Público

**Parámetros de consulta (Query Params - Opcionales):**
- `q`: Término de búsqueda (ej. `"gas"`, `"fuga"`, `"mantenimiento"`).
- `categoriaId`: ID de la categoría (Long).
- `distrito`: Nombre del distrito o ciudad (ej. `"Surco"`).
- `precioMin`: Precio mínimo (Decimal).
- `precioMax`: Precio máximo (Decimal).
- `page`: Número de página (0-indexed, default: `0`).
- `size`: Elementos por página (default: `10`).
- `sort`: Campo de ordenamiento (ej. `precioEstimado,asc` o `createdAt,desc`).

**Ejemplo de Request:**
```http
GET /api/v1/public/servicios?q=fuga&distrito=Surco&page=0&size=10
```

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 10,
      "titulo": "Detección y reparación de fuga de gas",
      "descripcion": "Revisión integral de tuberías de cobre y válvulas de paso.",
      "precioEstimado": 80.00,
      "tiempoEstimado": "2 horas",
      "categoriaId": 2,
      "categoriaNombre": "Gasfitería",
      "tecnicoId": 5,
      "tecnicoNombre": "Carlos Gómez",
      "tecnicoFotoUrl": "https://mi-servidor.com/fotos/carlos.jpg",
      "tecnicoDistrito": "Santiago de Surco",
      "tecnicoCiudad": "Lima",
      "tecnicoCalificacionPromedio": 4.8
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 10,
  "number": 0
}
```

---

### 2.2. Directorio público de técnicos
- **Método:** `GET`
- **Ruta:** `/api/v1/public/tecnicos`
- **Permisos:** Público

**Parámetros de consulta:** `categoriaId`, `nombre`, `page`, `size`, `sort`.

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 5,
      "nombre": "Carlos Gómez",
      "fotoUrl": "https://mi-servidor.com/fotos/carlos.jpg",
      "distrito": "Santiago de Surco",
      "ciudad": "Lima",
      "presentacion": "Gasfitero técnico con más de 8 años de experiencia.",
      "aniosExperiencia": 8,
      "calificacionPromedio": 4.8,
      "totalResenias": 14,
      "especialidadId": 2,
      "especialidadNombre": "Gasfitería"
    }
  ]
}
```

---

## 👤 3. Perfil del Usuario Autenticado

Disponible para cualquier usuario logueado (`CLIENTE`, `TECNICO`, `ADMIN`).

### 3.1. Obtener mi perfil
- **Método:** `GET`
- **Ruta:** `/api/v1/perfil/me`
- **Permisos:** Requiere Token JWT (`authenticated`)

**Response (200 OK):**
```json
{
  "id": 5,
  "nombre": "Carlos Gómez",
  "email": "carlos@ejemplo.com",
  "telefono": "987654321",
  "fotoUrl": "https://mi-servidor.com/fotos/carlos.jpg",
  "direccion": "Av. Benavides 1234",
  "distrito": "Santiago de Surco",
  "ciudad": "Lima",
  "rol": "TECNICO",
  "presentacion": "Gasfitero certificado",
  "aniosExperiencia": 8,
  "calificacionPromedio": 4.8,
  "yapeNumero": "987654321",
  "plinNumero": "987654321",
  "titularPago": "Carlos Gómez R.",
  "especialidadId": 2,
  "especialidadNombre": "Gasfitería"
}
```

---

### 3.2. Actualizar mis datos personales
- **Método:** `PUT`
- **Ruta:** `/api/v1/perfil/me`
- **Permisos:** Requiere Token JWT (`authenticated`)

**Body (Request):**
```json
{
  "nombre": "Carlos Gómez Actualizado",
  "telefono": "987654321",
  "fotoUrl": "https://mi-servidor.com/fotos/carlos2.jpg",
  "direccion": "Av. Benavides 5678",
  "distrito": "Surco",
  "ciudad": "Lima"
}
```

---

## 🛠️ 4. Módulo de Técnicos (`ROLE_TECNICO`)

### 4.1. Configurar perfil profesional y métodos de pago
- **Método:** `PUT`
- **Ruta:** `/api/v1/tecnico/perfil`
- **Permisos:** Requiere rol `TECNICO`

**Body (Request):**
```json
{
  "presentacion": "Especialista en instalaciones sanitarias y de gas residencial.",
  "aniosExperiencia": 8,
  "especialidadId": 2,
  "yapeNumero": "987654321",
  "plinNumero": "987654321",
  "titularPago": "Carlos Gómez R.",
  "fotoUrl": "https://mi-servidor.com/fotos/carlos.jpg"
}
```

---

### 4.2. Listar mis servicios ofrecidos
- **Método:** `GET`
- **Ruta:** `/api/v1/tecnico/servicios`
- **Permisos:** Requiere rol `TECNICO`

---

### 4.3. Crear nuevo servicio ofrecido
- **Método:** `POST`
- **Ruta:** `/api/v1/tecnico/servicios`
- **Permisos:** Requiere rol `TECNICO`

**Body (Request):**
```json
{
  "titulo": "Instalación de terma a gas",
  "descripcion": "Instalación completa, verificación de presión y prueba de encendido.",
  "precioEstimado": 120.00,
  "tiempoEstimado": "3 horas",
  "categoriaId": 2,
  "activo": true
}
```

---

### 4.4. Modificar servicio ofrecido
- **Método:** `PUT`
- **Ruta:** `/api/v1/tecnico/servicios/{id}`
- **Permisos:** Requiere rol `TECNICO` (solo puede modificar sus propios servicios)

---

### 4.5. Eliminar servicio ofrecido
- **Método:** `DELETE`
- **Ruta:** `/api/v1/tecnico/servicios/{id}`
- **Permisos:** Requiere rol `TECNICO` (solo puede eliminar sus propios servicios)

---

## 📖 5. Ficha Técnica Completa y Reseñas

### 5.1. Consultar información completa de un técnico
Permite a usuarios registrados ver datos de contacto (teléfono, email), métodos de pago (Yape/Plin), catálogo de servicios y reseñas de clientes.
- **Método:** `GET`
- **Ruta:** `/api/v1/tecnicos/{id}/completo`
- **Permisos:** Requiere Token JWT (`authenticated`)

**Response (200 OK):**
```json
{
  "id": 5,
  "nombre": "Carlos Gómez",
  "email": "carlos@ejemplo.com",
  "telefono": "987654321",
  "fotoUrl": "https://mi-servidor.com/fotos/carlos.jpg",
  "direccion": "Av. Benavides 1234",
  "distrito": "Santiago de Surco",
  "ciudad": "Lima",
  "presentacion": "Gasfitero certificado con 8 años de experiencia.",
  "aniosExperiencia": 8,
  "calificacionPromedio": 4.8,
  "yapeNumero": "987654321",
  "plinNumero": "987654321",
  "titularPago": "Carlos Gómez R.",
  "especialidadId": 2,
  "especialidadNombre": "Gasfitería",
  "servicios": [
    {
      "id": 10,
      "titulo": "Detección y reparación de fuga de gas",
      "descripcion": "Revisión integral de tuberías de cobre.",
      "precioEstimado": 80.00,
      "tiempoEstimado": "2 horas",
      "activo": true,
      "categoriaId": 2,
      "categoriaNombre": "Gasfitería",
      "tecnicoId": 5,
      "tecnicoNombre": "Carlos Gómez",
      "createdAt": "2026-08-18T10:00:00"
    }
  ],
  "resenias": [
    {
      "id": 1,
      "calificacion": 5,
      "comentario": "Excelente servicio, muy puntual y solucionó la fuga de inmediato.",
      "clienteId": 8,
      "clienteNombre": "Ana Torres",
      "clienteFotoUrl": null,
      "tecnicoId": 5,
      "createdAt": "2026-08-18T11:30:00"
    }
  ]
}
```

---

### 5.2. Calificar y dejar reseña a un técnico
- **Método:** `POST`
- **Ruta:** `/api/v1/resenias`
- **Permisos:** Requiere rol `CLIENTE`

**Body (Request):**
```json
{
  "tecnicoId": 5,
  "calificacion": 5,
  "comentario": "Excelente trabajo y muy profesional."
}
```

---

### 5.3. Listar reseñas de un técnico
- **Método:** `GET`
- **Ruta:** `/api/v1/resenias/tecnico/{tecnicoId}`
- **Permisos:** Público

---

## 📁 6. Categorías / Especialidades

- `GET /api/v1/categorias`: Listar todas las categorías (Público).
- `GET /api/v1/categorias/{id}`: Obtener categoría por ID (Público).
- `POST /api/v1/categorias`: Crear categoría (**Requiere ADMIN**).
- `PUT /api/v1/categorias/{id}`: Modificar categoría (**Requiere ADMIN**).
- `DELETE /api/v1/categorias/{id}`: Eliminar categoría (**Requiere ADMIN**).

**Body (Request para POST / PUT):**
```json
{
  "nombre": "Electricidad",
  "descripcion": "Instalaciones eléctricas, cableado y mantenimiento de tableros."
}
```

---

## 👥 7. Gestión de Usuarios (**Requiere ADMIN**)

- `GET /api/v1/usuarios`: Listar todos los usuarios.
- `GET /api/v1/usuarios/{id}`: Obtener usuario por ID.
- `POST /api/v1/usuarios`: Crear usuario.
- `PUT /api/v1/usuarios/{id}`: Actualizar usuario.
- `DELETE /api/v1/usuarios/{id}`: Eliminar usuario.
- `GET /api/v1/usuarios/consulta?nombre=carlos&page=0&size=5`: Búsqueda paginada.

---

## 📊 Matriz de Permisos

| Endpoint | Rol Permitido |
|---|---|
| `/api/v1/auth/**` | Público |
| `/api/v1/public/**` | Público |
| `GET /api/v1/categorias/**` | Público |
| `POST, PUT, DELETE /api/v1/categorias/**` | `ROLE_ADMIN` |
| `/api/v1/usuarios/**` | `ROLE_ADMIN` |
| `/api/v1/tecnico/**` | `ROLE_TECNICO` |
| `POST /api/v1/resenias` | `ROLE_CLIENTE` |
| `/api/v1/perfil/**` | `ROLE_CLIENTE`, `ROLE_TECNICO`, `ROLE_ADMIN` |
| `GET /api/v1/tecnicos/{id}/completo` | `ROLE_CLIENTE`, `ROLE_TECNICO`, `ROLE_ADMIN` |
