# 📚 Documentación Completa de Endpoints - API Delivery

**Base URL:** `http://localhost:8080`

---

## 📋 Índice
1. [Autenticación](#1-autenticación) 🔐 **NUEVO**
2. [Categorías](#2-categorías)
3. [Clientes](#3-clientes)
4. [Productos](#4-productos)
5. [Pedidos](#5-pedidos)
6. [Pagos](#6-pagos)
7. [Promociones](#7-promociones)
8. [Repartidores](#8-repartidores)
9. [Roles](#9-roles)
10. [Usuarios](#10-usuarios)
11. [Carrito](#11-carrito)
12. [Hello](#12-hello)

---

## 1. AUTENTICACIÓN (`/auth`) 🔐

### POST `/auth/register`
**Descripción:** Registrar nuevo usuario y obtener token JWT automáticamente

**Body (Request):**
```json
{
  "username": "nuevo_usuario",
  "password": "miPassword123",
  "rol": "CLIENTE",
  "empresa": "Al Toque Delivery"
}
```

**Roles disponibles:**
- `ADMIN` - Administrador del sistema
- `REPARTIDOR` - Repartidor
- `CLIENTE` - Cliente
- `GERENTE` - Gerente

**Empresas disponibles:**
- `Al Toque Delivery`
- `Rápido Express`
- `Delicias Express`

**Response (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "idUsuario": 24,
  "username": "nuevo_usuario",
  "rol": "CLIENTE",
  "empresa": "Al Toque Delivery",
  "activo": true,
  "mensaje": "Registro exitoso"
}
```

**Response (400 Bad Request):**
```json
{
  "mensaje": "Error: El username ya está en uso"
}
```

**Nota:** El usuario se crea automáticamente activo y se retorna un token JWT para iniciar sesión inmediatamente.

### POST `/auth/register/cliente`
**Descripción:** Registrar un cliente completo (crea usuario + cliente + dirección automáticamente)

**Body (Request):**
```json
{
  "username": "nuevo_cliente",
  "password": "miPassword123",
  "nombre": "Juan",
  "apellido": "Pérez",
  "dni": "12345678",
  "telefono": "987654321",
  "email": "juan.perez@email.com",
  "calle": "Av. Los Olivos",
  "numero": "123",
  "referencia": "Frente al parque",
  "distrito": "Los Olivos",
  "ciudad": "Lima",
  "empresa": "Al Toque Delivery"
}
```

**Campos opcionales:**
- `empresa`: Si no se proporciona, se usa la primera empresa disponible
- `ciudad`: Si no se proporciona, se usa "Lima" por defecto

**Response (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "idUsuario": 24,
  "username": "nuevo_cliente",
  "rol": "CLIENTE",
  "empresa": "Al Toque Delivery",
  "activo": true,
  "mensaje": "Registro de cliente exitoso"
}
```

**Response (400 Bad Request):**
```json
{
  "mensaje": "Error: El username ya está en uso"
}
```

**Nota:** Este endpoint crea automáticamente:
- ✅ Un usuario con rol CLIENTE
- ✅ Un cliente completo con todos sus datos
- ✅ Una dirección asociada al cliente
- ✅ La asociación entre usuario y cliente
- ✅ Retorna un token JWT para iniciar sesión inmediatamente

### POST `/auth/login`
**Descripción:** Iniciar sesión y obtener token JWT

**Body (Request):**
```json
{
  "username": "admin",
  "password": "password"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "idUsuario": 1,
  "username": "admin",
  "rol": "ADMIN",
  "empresa": "Al Toque Delivery",
  "activo": true,
  "mensaje": "Login exitoso"
}
```

**Response (401 Unauthorized):**
```json
{
  "mensaje": "Error: Credenciales inválidas"
}
```

**Nota:** Usa el token en el header `Authorization: Bearer {token}` para acceder a endpoints protegidos.

### POST `/auth/logout`
**Descripción:** Cerrar sesión

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "mensaje": "Logout exitoso"
}
```

### GET `/auth/validate`
**Descripción:** Validar si un token JWT es válido

**Headers:**
```
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "mensaje": "Token válido"
}
```

**Response (401 Unauthorized):**
```json
{
  "mensaje": "Token no proporcionado"
}
```

---

## 2. CATEGORÍAS (`/categorias`)

### GET `/categorias`
**Descripción:** Listar todas las categorías

**Response:**
```json
[
  {
    "idCategoria": 1,
    "nombre": "Comida Rápida",
    "descripcion": "Hamburguesas, papas fritas, pollo frito y más",
    "imagen": "https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=800&h=600&fit=crop"
  }
]
```

### POST `/categorias`
**Descripción:** Crear nueva categoría

**Body (Request):**
```json
{
  "nombre": "Comida Rápida",
  "descripcion": "Hamburguesas, papas fritas, pollo frito y más",
  "imagen": "https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=800&h=600&fit=crop"
}
```

**Nota:** `idCategoria` se genera automáticamente, no es necesario enviarlo.

### PUT `/categorias/{id}`
**Descripción:** Actualizar categoría

**Body (Request):**
```json
{
  "idCategoria": 1,
  "nombre": "Comida Rápida Actualizada",
  "descripcion": "Nueva descripción",
  "imagen": "https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=800&h=600&fit=crop"
}
```

### DELETE `/categorias/{id}`
**Descripción:** Eliminar categoría

**Response:** Sin contenido (204)

---

## 2. CLIENTES (`/clientes`)

### GET `/clientes`
**Descripción:** Listar todos los clientes

**Response:**
```json
[
  {
    "idCliente": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "telefono": "987654321",
    "email": "juan.perez@email.com",
    "foto": "https://i.pravatar.cc/300?img=12",
    "direccion": "Av. Los Olivos 123"
  }
]
```

### GET `/clientes/{id}`
**Descripción:** Obtener cliente por ID

**Response:**
```json
{
  "idCliente": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "telefono": "987654321",
  "email": "juan.perez@email.com",
  "foto": "https://i.pravatar.cc/300?img=12",
  "direccion": "Av. Los Olivos 123"
}
```

### POST `/clientes`
**Descripción:** Crear nuevo cliente

**Body (Request):**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "telefono": "987654321",
  "email": "juan.perez@email.com",
  "foto": "https://i.pravatar.cc/300?img=12",
  "direccion": "Av. Los Olivos 123"
}
```

### PUT `/clientes/{id}`
**Descripción:** Actualizar cliente

**Body (Request):**
```json
{
  "idCliente": 1,
  "nombre": "Juan Carlos",
  "apellido": "Pérez",
  "telefono": "987654321",
  "email": "juan.perez@email.com",
  "direccion": "Av. Los Olivos 123"
}
```

### DELETE `/clientes/{id}`
**Descripción:** Eliminar cliente

**Response:** Sin contenido (204)

---

## 3. PRODUCTOS (`/productos`)

### GET `/productos`
**Descripción:** Listar todos los productos

**Response:**
```json
[
  {
    "idProducto": 1,
    "nombre": "Hamburguesa Clásica",
    "descripcion": "Hamburguesa con carne, lechuga, tomate y queso",
    "precio": 15.50,
    "imagen": "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&h=600&fit=crop",
    "disponible": true,
    "categoria": "Comida Rápida",
    "empresa": "Al Toque Delivery"
  }
]
```

### GET `/productos/{id}`
**Descripción:** Obtener producto por ID

**Response:**
```json
{
  "idProducto": 1,
  "nombre": "Hamburguesa Clásica",
  "descripcion": "Hamburguesa con carne, lechuga, tomate y queso",
  "precio": 15.50,
  "imagen": "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&h=600&fit=crop",
  "disponible": true,
  "categoria": "Comida Rápida",
  "empresa": "Al Toque Delivery"
}
```

### POST `/productos`
**Descripción:** Crear nuevo producto

**Body (Request):**
```json
{
  "nombre": "Hamburguesa Clásica",
  "descripcion": "Hamburguesa con carne, lechuga, tomate y queso",
  "precio": 15.50,
  "imagen": "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&h=600&fit=crop",
  "disponible": true,
  "categoria": "Comida Rápida",
  "empresa": "Al Toque Delivery"
}
```

**Nota:** `categoria` y `empresa` deben ser los nombres exactos que existen en la base de datos.

### PUT `/productos/{id}`
**Descripción:** Actualizar producto

**Body (Request):**
```json
{
  "idProducto": 1,
  "nombre": "Hamburguesa Clásica Premium",
  "descripcion": "Hamburguesa con carne premium, lechuga, tomate y queso",
  "precio": 18.50,
  "disponible": true,
  "categoria": "Comida Rápida",
  "empresa": "Al Toque Delivery"
}
```

### DELETE `/productos/{id}`
**Descripción:** Eliminar producto

**Response:** Sin contenido (204)

---

## 4. PEDIDOS (`/pedidos`)

### GET `/pedidos`
**Descripción:** Listar todos los pedidos

**Response:**
```json
[
  {
    "idPedido": 1,
    "fecha": "2024-01-15T10:30:00",
    "estado": "ENTREGADO",
    "total": 35.50,
    "cliente": "Juan Pérez",
    "repartidor": "Pedro Sánchez",
    "detalles": [
      {
        "idDetalle": 1,
        "producto": "Hamburguesa Clásica",
        "cantidad": 1,
        "subtotal": 15.50
      }
    ]
  }
]
```

### GET `/pedidos/{id}`
**Descripción:** Obtener pedido por ID

**Response:**
```json
{
  "idPedido": 1,
  "fecha": "2024-01-15T10:30:00",
  "estado": "ENTREGADO",
  "total": 35.50,
  "cliente": "Juan Pérez",
  "repartidor": "Pedro Sánchez",
  "detalles": [
    {
      "idDetalle": 1,
      "producto": "Hamburguesa Clásica",
      "cantidad": 1,
      "subtotal": 15.50
    }
  ]
}
```

### POST `/pedidos/crear`
**Descripción:** Crear pedido desde el carrito usando el usuario autenticado (recomendado)

**Headers:**
```
Authorization: Bearer {token}
```

**Nota:** Este endpoint obtiene automáticamente el cliente asociado al usuario autenticado del token JWT.

**Response (201 Created):**
```json
{
  "idPedido": 1,
  "fecha": "2024-01-15T10:30:00",
  "estado": "PENDIENTE",
  "total": 35.50,
  "cliente": "Juan Pérez",
  "repartidor": null,
  "detalles": [...]
}
```

**Response (401 Unauthorized):**
```json
{
  "mensaje": "Usuario no autenticado. Por favor, inicia sesión."
}
```

**Response (400 Bad Request):**
```json
{
  "mensaje": "El usuario no tiene un cliente asociado."
}
```

### POST `/pedidos/crear/{idCliente}`
**Descripción:** Crear pedido desde el carrito especificando el ID del cliente

**Ejemplo:** `POST /pedidos/crear/1`

**Response:**
```json
{
  "idPedido": 1,
  "fecha": "2024-01-15T10:30:00",
  "estado": "PENDIENTE",
  "total": 35.50,
  "cliente": "Juan Pérez",
  "repartidor": null,
  "detalles": [...]
}
```

### PUT `/pedidos/{id}/estado/{nuevoEstado}`
**Descripción:** Actualizar estado del pedido

**Estados válidos:**
- `PENDIENTE`
- `PREPARANDO`
- `EN_CAMINO`
- `ENTREGADO`

**Ejemplo:** `PUT /pedidos/1/estado/EN_CAMINO`

**Response:**
```json
{
  "idPedido": 1,
  "fecha": "2024-01-15T10:30:00",
  "estado": "EN_CAMINO",
  "total": 35.50,
  "cliente": "Juan Pérez",
  "repartidor": "Pedro Sánchez",
  "detalles": [...]
}
```

### DELETE `/pedidos/{id}`
**Descripción:** Eliminar pedido

**Response:** Sin contenido (204)

---

## 5. PAGOS (`/pagos`)

### GET `/pagos`
**Descripción:** Listar todos los pagos

**Response:**
```json
[
  {
    "idPago": 1,
    "monto": 35.50,
    "metodo": "EFECTIVO",
    "estado": "COMPLETADO",
    "fechaPago": "2024-01-15T10:30:00",
    "idPedido": 1
  }
]
```

### GET `/pagos/{id}`
**Descripción:** Obtener pago por ID

**Response:**
```json
{
  "idPago": 1,
  "monto": 35.50,
  "metodo": "EFECTIVO",
  "estado": "COMPLETADO",
  "fechaPago": "2024-01-15T10:30:00",
  "idPedido": 1
}
```

### POST `/pagos`
**Descripción:** Registrar nuevo pago

**Body (Request):**
```json
{
  "monto": 35.50,
  "metodo": "EFECTIVO",
  "estado": "PENDIENTE",
  "idPedido": 1
}
```

**Nota:** `fechaPago` se genera automáticamente si no se envía.

**Métodos de pago válidos:**
- `EFECTIVO`
- `TARJETA`

**Estados válidos:**
- `PENDIENTE`
- `COMPLETADO`
- `CANCELADO`

### PUT `/pagos/{id}`
**Descripción:** Actualizar pago

**Body (Request):**
```json
{
  "idPago": 1,
  "monto": 35.50,
  "metodo": "TARJETA",
  "estado": "COMPLETADO",
  "fechaPago": "2024-01-15T10:30:00",
  "idPedido": 1
}
```

### DELETE `/pagos/{id}`
**Descripción:** Eliminar pago

**Response:** Sin contenido (204)

---

## 6. PROMOCIONES (`/promociones`)

### GET `/promociones`
**Descripción:** Listar todas las promociones

**Response:**
```json
[
  {
    "idPromocion": 1,
    "nombre": "Descuento Pizza",
    "descripcion": "20% de descuento en todas las pizzas",
    "descuento": 20.00,
    "fechaInicio": "2024-01-01",
    "fechaFin": "2024-01-31",
    "idProducto": 5
  }
]
```

### GET `/promociones/{id}`
**Descripción:** Obtener promoción por ID

**Response:**
```json
{
  "idPromocion": 1,
  "nombre": "Descuento Pizza",
  "descripcion": "20% de descuento en todas las pizzas",
  "descuento": 20.00,
  "fechaInicio": "2024-01-01",
  "fechaFin": "2024-01-31",
  "idProducto": 5
}
```

### POST `/promociones`
**Descripción:** Crear nueva promoción

**Body (Request):**
```json
{
  "nombre": "Descuento Pizza",
  "descripcion": "20% de descuento en todas las pizzas",
  "descuento": 20.00,
  "fechaInicio": "2024-01-01",
  "fechaFin": "2024-01-31",
  "idProducto": 5
}
```

**Nota:** `descuento` puede ser un porcentaje o monto fijo según tu lógica de negocio.

### PUT `/promociones/{id}`
**Descripción:** Actualizar promoción

**Body (Request):**
```json
{
  "idPromocion": 1,
  "nombre": "Descuento Pizza Actualizado",
  "descripcion": "25% de descuento en todas las pizzas",
  "descuento": 25.00,
  "fechaInicio": "2024-01-01",
  "fechaFin": "2024-02-28",
  "idProducto": 5
}
```

### DELETE `/promociones/{id}`
**Descripción:** Eliminar promoción

**Response:** Sin contenido (204)

---

## 7. REPARTIDORES (`/repartidores`)

### GET `/repartidores`
**Descripción:** Listar todos los repartidores

**Response:**
```json
[
  {
    "idRepartidor": 1,
    "nombre": "Pedro Sánchez",
    "telefono": "987654330",
    "placaMoto": "ABC-123",
    "licencia": "LIC-001",
    "disponible": true,
    "empresa": "Al Toque Delivery"
  }
]
```

### GET `/repartidores/{id}`
**Descripción:** Obtener repartidor por ID

**Response:**
```json
{
  "idRepartidor": 1,
  "nombre": "Pedro Sánchez",
  "telefono": "987654330",
  "placaMoto": "ABC-123",
  "licencia": "LIC-001",
  "disponible": true,
  "empresa": "Al Toque Delivery"
}
```

### POST `/repartidores`
**Descripción:** Crear nuevo repartidor

**Body (Request):**
```json
{
  "nombre": "Pedro Sánchez",
  "telefono": "987654330",
  "placaMoto": "ABC-123",
  "licencia": "LIC-001",
  "disponible": true,
  "empresa": "Al Toque Delivery"
}
```

**Nota:** `empresa` debe ser el nombre exacto que existe en la base de datos.

### PUT `/repartidores/{id}`
**Descripción:** Actualizar repartidor

**Body (Request):**
```json
{
  "idRepartidor": 1,
  "nombre": "Pedro Sánchez",
  "telefono": "987654331",
  "placaMoto": "ABC-123",
  "licencia": "LIC-001",
  "disponible": false,
  "empresa": "Al Toque Delivery"
}
```

### DELETE `/repartidores/{id}`
**Descripción:** Eliminar repartidor

**Response:** Sin contenido (204)

---

## 8. ROLES (`/roles`)

### GET `/roles`
**Descripción:** Listar todos los roles

**Response:**
```json
[
  {
    "idRol": 1,
    "nombre": "ADMIN",
    "descripcion": "Administrador del sistema con acceso completo"
  }
]
```

### POST `/roles`
**Descripción:** Crear nuevo rol

**Body (Request):**
```json
{
  "nombre": "ADMIN",
  "descripcion": "Administrador del sistema con acceso completo"
}
```

### PUT `/roles/{id}`
**Descripción:** Actualizar rol

**Body (Request):**
```json
{
  "idRol": 1,
  "nombre": "ADMIN",
  "descripcion": "Administrador del sistema con acceso completo y permisos extendidos"
}
```

### DELETE `/roles/{id}`
**Descripción:** Eliminar rol

**Response:** Sin contenido (204)

---

## 9. USUARIOS (`/usuarios`)

### GET `/usuarios`
**Descripción:** Listar todos los usuarios

**Response:**
```json
[
  {
    "idUsuario": 1,
    "username": "admin",
    "activo": true,
    "rol": "ADMIN",
    "empresa": "Al Toque Delivery"
  }
]
```

### GET `/usuarios/{id}`
**Descripción:** Obtener usuario por ID

**Response:**
```json
{
  "idUsuario": 1,
  "username": "admin",
  "activo": true,
  "rol": "ADMIN",
  "empresa": "Al Toque Delivery"
}
```

### GET `/usuarios/buscar/{username}`
**Descripción:** Buscar usuario por username

**Ejemplo:** `GET /usuarios/buscar/admin`

**Response:**
```json
{
  "idUsuario": 1,
  "username": "admin",
  "activo": true,
  "rol": "ADMIN",
  "empresa": "Al Toque Delivery"
}
```

### POST `/usuarios`
**Descripción:** Crear nuevo usuario

**Body (Request):**
```json
{
  "username": "nuevo_usuario",
  "activo": true,
  "rol": "CLIENTE",
  "empresa": "Al Toque Delivery"
}
```

**Nota:** `rol` y `empresa` deben ser los nombres exactos que existen en la base de datos. La contraseña se maneja por separado (no está en el DTO).

### PUT `/usuarios/{id}`
**Descripción:** Actualizar usuario

**Body (Request):**
```json
{
  "idUsuario": 1,
  "username": "admin_actualizado",
  "activo": false,
  "rol": "ADMIN",
  "empresa": "Al Toque Delivery"
}
```

### DELETE `/usuarios/{id}`
**Descripción:** Eliminar usuario

**Response:** Sin contenido (204)

---

## 10. CARRITO (`/carrito`)

### GET `/carrito`
**Descripción:** Obtener todos los items del carrito

**Response:**
```json
[
  {
    "idProducto": 1,
    "nombre": "Hamburguesa Clásica",
    "imagen": "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&h=600&fit=crop",
    "cantidad": 2,
    "precio": 15.50,
    "subtotal": 31.00
  }
]
```

### POST `/carrito`
**Descripción:** Agregar producto al carrito

**Body (Request):**
```json
{
  "idProducto": 1,
  "nombre": "Hamburguesa Clásica",
  "imagen": "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&h=600&fit=crop",
  "cantidad": 2,
  "precio": 15.50,
  "subtotal": 31.00
}
```

**Nota:** El `subtotal` se calcula como `cantidad × precio`.

### DELETE `/carrito/{idProducto}`
**Descripción:** Eliminar producto del carrito

**Ejemplo:** `DELETE /carrito/1`

**Response:** Sin contenido (204)

### DELETE `/carrito/vaciar`
**Descripción:** Vaciar todo el carrito

**Response:** Sin contenido (204)

### GET `/carrito/total`
**Descripción:** Calcular total del carrito

**Response:**
```json
45.50
```

**Nota:** Retorna un número (BigDecimal), no un objeto JSON.

---

## 11. HELLO (`/`)

### GET `/`
**Descripción:** Verificar que Spring Boot funciona

**Response:**
```
✅ Spring Boot está funcionando correctamente!
```

### GET `/saludo`
**Descripción:** Mensaje de saludo

**Response:**
```
Hola desde tu aplicación Spring Boot 🚀
```

---

## 📝 Notas Generales

### Headers Recomendados
```http
Content-Type: application/json
Accept: application/json
Authorization: Bearer {token}  # Para endpoints protegidos
```

### Autenticación JWT
Para acceder a endpoints protegidos, incluye el token JWT en el header:
```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Obtener token:**
1. Realiza un POST a `/auth/login` con username y password
2. Copia el token del campo `token` en la respuesta
3. Incluye el token en el header `Authorization` de todas las peticiones protegidas

### Códigos de Estado HTTP
- `200 OK` - Operación exitosa
- `201 Created` - Recurso creado exitosamente
- `204 No Content` - Operación exitosa sin contenido (DELETE)
- `400 Bad Request` - Error en la solicitud
- `404 Not Found` - Recurso no encontrado
- `500 Internal Server Error` - Error del servidor

### Formato de Fechas
- **LocalDateTime:** `"2024-01-15T10:30:00"`
- **LocalDate:** `"2024-01-15"`

### Tipos de Datos
- **BigDecimal:** Números decimales (precios, montos)
- **Long:** IDs y números grandes
- **Boolean:** Valores `true`/`false`
- **String:** Texto

---

## 🔗 Ejemplos de Uso con cURL

### Crear un producto
```bash
curl -X POST http://localhost:8080/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Hamburguesa Clásica",
    "descripcion": "Hamburguesa con carne, lechuga, tomate y queso",
    "precio": 15.50,
    "disponible": true,
    "categoria": "Comida Rápida",
    "empresa": "Al Toque Delivery"
  }'
```

### Obtener todos los clientes
```bash
curl -X GET http://localhost:8080/clientes
```

### Registro de Cliente Completo (Recomendado)
```bash
curl -X POST http://localhost:8080/auth/register/cliente \
  -H "Content-Type: application/json" \
  -d '{
    "username": "nuevo_cliente",
    "password": "miPassword123",
    "nombre": "Juan",
    "apellido": "Pérez",
    "dni": "12345678",
    "telefono": "987654321",
    "email": "juan.perez@email.com",
    "calle": "Av. Los Olivos",
    "numero": "123",
    "referencia": "Frente al parque",
    "distrito": "Los Olivos",
    "ciudad": "Lima",
    "empresa": "Al Toque Delivery"
  }'
```

### Registro de Usuario Simple
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "nuevo_usuario",
    "password": "miPassword123",
    "rol": "CLIENTE",
    "empresa": "Al Toque Delivery"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password"
  }'
```

### Crear pedido desde usuario autenticado (recomendado)
```bash
curl -X POST http://localhost:8080/pedidos/crear \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### Crear pedido especificando cliente
```bash
curl -X POST http://localhost:8080/pedidos/crear/1
```

### Actualizar estado de pedido (con autenticación)
```bash
curl -X PUT http://localhost:8080/pedidos/1/estado/EN_CAMINO \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### Logout
```bash
curl -X POST http://localhost:8080/auth/logout \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

**Última actualización:** 2024

