# API Rancho - Endpoints y Ejemplos de Uso

## 1. Autenticación (`/auth`)

### POST /auth/generarusuarios
Genera usuarios automáticamente a partir del personal registrado.

**Ejemplo:**
```
POST /auth/generarusuarios
```

---

### POST /auth/login
Inicia sesión y retorna un token JWT.

**Body:**
```json
{
  "nombreusuario": "usuario",
  "constrasena": "password"
}
```

**Respuesta:**
```json
{
  "token": "jwt_token",
  "usuario": "usuario",
  "rol": "rol"
}
```

---

### POST /auth/cambiar-contrasena
Cambia la contraseña de un usuario.

**Body:**
```json
{
  "nombreusuario": "usuario",
  "nuevaContrasena": "nuevaPassword"
}
```

---

## 2. Configuraciones (`/api/configuraciones`)

### PUT /api/configuraciones/modificar
Modifica la hora de una configuración.

**Body:**
```json
{
  "tipoConfiguracion": "horaApertura",
  "hora": "08:00:00"
}
```

---

### GET /api/configuraciones/buscar?tipoConfiguracion=horaApertura
Obtiene la hora configurada para un tipo.

---

## 3. Personal (`/api/personal`)

### PUT /api/personal
Actualiza la unidad de un personal.

**Body:**
```json
{
  "cedula": "1234567890",
  "unidad": "Nueva Unidad"
}
```

---

### PUT /api/personal/novedad
Actualiza la novedad de un personal.

**Body:**
```json
{
  "cedula": "1234567890",
  "novedad": "NINGUNA"
}
```

---

### PUT /api/personal/subunidad
Actualiza la subunidad de un personal.

**Body:**
```json
{
  "cedula": "1234567890",
  "subunidad": "Subunidad A"
}
```

---

### GET /api/personal/unidades
Obtiene todas las unidades únicas.

---

### GET /api/personal/buscar?cedula=1234567890
Busca un personal por cédula.

---

### POST /api/personal/agregar
Agrega un nuevo personal y crea su usuario.

**Body:**
```json
{
  "cedula": "1234567890",
  "apellidonombre": "Juan Pérez",
  "unidad": "Unidad Militar",
  "grado": "Teniente",
  "novedad": "NINGUNA",
  "celular": "0999999999",
  "subunidad": "Subunidad A"
}
```

---

## 4. QR (`/api/qr`)

### POST /api/qr/info
Devuelve la información del personal y sus raciones del día a partir del QR.

**Body:**
```json
{
  "qrData": "valor_del_qr"
}
```

---

### POST /api/qr/consumir-racion
Marca una ración como consumida para el QR y la fecha de hoy.

**Body:**
```json
{
  "idqr": 1,
  "racion": "desayuno"
}
```
- Solo puede consumirse si el valor de la ración es 1.

---

## 5. Raciones (`/api/raciones`)

### GET /api/raciones?fecha=YYYY-MM-DD&unidad=Unidad1
Obtiene las raciones de una unidad en una fecha.

---

### POST /api/raciones
Crea o actualiza raciones.

**Body:**
```json
[
  {
    "idPersonal": 1,
    "idqr": 1,
    "desayuno": 1,
    "almuerzo": 0,
    "merienda": 0
  }
]
```

---

### GET /api/raciones/consumidas?fecha=YYYY-MM-DD
Devuelve la cantidad de raciones consumidas por unidad en una fecha.

**Respuesta:**
```json
{
  "GAE 44": 50,
  "EWIAS": 100
}
```

---

## 6. Precios (`/api/precios`)

### GET /api/precios
Obtiene todos los precios de todas las comidas.

**Ejemplo de respuesta:**
```json
[
  { "comida": "desayuno", "precio": 1.5 },
  { "comida": "almuerzo", "precio": 2.0 },
  { "comida": "merienda", "precio": 1.8 }
]
```

---

### PUT /api/precios/modificar
Modifica o crea el precio de una comida.

**Body:**
```json
{
  "comida": "almuerzo",
  "precio": 2.5
}
```

**Ejemplo de respuesta:**
```json
{
  "comida": "almuerzo",
  "precio": 2.5
}
```

---
