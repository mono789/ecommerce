# Ejemplos para Probar Endpoints en Swagger

## Acceso a Swagger UI
- **URL:** http://localhost:8080/swagger-ui.html
- **Ejecutar aplicación:** `docker-compose up -d`

---

## 1. ENDPOINTS DE CATEGORÍAS

### 1.1 POST /api/v1/categories (Crear Categoría)

**Ejemplo 1:**
```json
{
  "name": "Deportes y Fitness",
  "description": "Equipos deportivos, ropa deportiva y suplementos para mantenerse en forma"
}
```

**Ejemplo 2:**
```json
{
  "name": "Libros y Educación",
  "description": "Libros físicos, digitales, cursos online y material educativo"
}
```

**Ejemplo 3:**
```json
{
  "name": "Salud y Belleza",
  "description": "Productos de cuidado personal, cosméticos y suplementos de salud"
}
```

### 1.2 GET /api/v1/categories/{id} (Obtener Categoría)

**Parámetros:**
- `id`: 1, 2, 3, etc. (usar IDs de las categorías creadas)

### 1.3 PUT /api/v1/categories/{id} (Actualizar Categoría)

**Para ID = 7:**
```json
{
  "name": "Deportes Extremos",
  "description": "Equipos especializados para deportes de alto riesgo y aventura extrema"
}
```

---

## 2. ENDPOINTS DE PRODUCTOS

### 2.1 POST /api/v1/products (Crear Producto)

**Ejemplo 1 - Bicicleta:**
```json
{
  "name": "Bicicleta de Montaña Trek X-Caliber 8",
  "description": "Bicicleta de montaña con marco de aluminio, 29 pulgadas, 12 velocidades Shimano",
  "price": 3200000,
  "stock": 8,
  "brand": "Trek",
  "categoryIds": [7]
}
```

**Ejemplo 2 - Libro:**
```json
{
  "name": "Cien Años de Soledad - Gabriel García Márquez",
  "description": "Novela clásica del realismo mágico, Premio Nobel de Literatura",
  "price": 45000,
  "stock": 50,
  "brand": "Editorial Sudamericana",
  "categoryIds": [8]
}
```

**Ejemplo 3 - Producto de Belleza:**
```json
{
  "name": "Sérum Facial Vitamina C",
  "description": "Sérum antioxidante con 20% de vitamina C para iluminar y rejuvenecer la piel",
  "price": 120000,
  "stock": 25,
  "brand": "La Roche-Posay",
  "categoryIds": [9]
}
```

**Ejemplo 4 - Producto con múltiples categorías:**
```json
{
  "name": "Smartwatch Fitness Garmin Forerunner 245",
  "description": "Reloj inteligente para corredores con GPS, monitor cardíaco y métricas avanzadas",
  "price": 1450000,
  "stock": 12,
  "brand": "Garmin",
  "categoryIds": [7, 9]
}
```

**Ejemplo 5 - Suplemento:**
```json
{
  "name": "Proteína Whey Gold Standard 5lb",
  "description": "Proteína en polvo sabor chocolate, 24g de proteína por porción, certificada NSF",
  "price": 280000,
  "stock": 35,
  "brand": "Optimum Nutrition",
  "categoryIds": [7, 9]
}
```

**Ejemplo 6 - Curso Online:**
```json
{
  "name": "Curso Completo de Python para Data Science",
  "description": "Curso online de 40 horas sobre programación Python aplicada a ciencia de datos",
  "price": 350000,
  "stock": 999,
  "brand": "Platzi",
  "categoryIds": [8]
}
```

### 2.2 GET /api/v1/products/{id} (Obtener Producto)

**Parámetros:**
- `id`: 9, 10, 11, etc. (usar IDs de los productos nuevos creados)

### 2.3 PUT /api/v1/products/{id} (Actualizar Producto)

**Para ID = 9:**
```json
{
  "name": "Bicicleta de Montaña Trek X-Caliber 9 - Edición 2024",
  "description": "Bicicleta de montaña premium con marco de aluminio Alpha Gold, 29 pulgadas, 12 velocidades Shimano XT",
  "price": 3800000,
  "stock": 5,
  "brand": "Trek",
  "categoryIds": [7]
}
```

---

## 3. ENDPOINT ESPECIAL DE BÚSQUEDA

### 3.1 POST /api/v1/products/search (Búsqueda Avanzada)

**Ejemplo 1 - Búsqueda básica:**
```json
{
  "page": 0,
  "size": 10
}
```

**Ejemplo 2 - Filtrar por nombre:**
```json
{
  "name": "iPhone",
  "page": 0,
  "size": 5
}
```

**Ejemplo 3 - Filtrar por rango de precios:**
```json
{
  "minPrice": 1000000,
  "maxPrice": 5000000,
  "page": 0,
  "size": 10
}
```

**Ejemplo 4 - Filtrar por stock mínimo:**
```json
{
  "minStock": 10,
  "page": 0,
  "size": 10
}
```

**Ejemplo 5 - Filtrar por marca:**
```json
{
  "brand": "Apple",
  "page": 0,
  "size": 10
}
```

**Ejemplo 6 - Búsqueda combinada:**
```json
{
  "name": "MacBook",
  "brand": "Apple",
  "minPrice": 2000000,
  "maxPrice": 8000000,
  "minStock": 5,
  "page": 0,
  "size": 5
}
```

**Ejemplo 7 - Productos económicos:**
```json
{
  "maxPrice": 1000000,
  "page": 0,
  "size": 10
}
```

**Ejemplo 8 - Paginación segunda página:**
```json
{
  "page": 1,
  "size": 3
}
```

---

## 4. ORDEN SUGERIDO PARA PRUEBAS

### Paso 1: Crear Categorías Nuevas
1. Crear "Deportes y Fitness" con POST /categories
2. Crear "Libros y Educación" con POST /categories  
3. Crear "Salud y Belleza" con POST /categories

### Paso 2: Verificar Categorías
4. Obtener categoría ID=7 con GET /categories/7
5. Actualizar categoría ID=7 con PUT /categories/7

### Paso 3: Crear Productos Nuevos
6. Crear Bicicleta Trek con POST /products (categoryIds: [7])
7. Crear Libro García Márquez con POST /products (categoryIds: [8])
8. Crear Sérum Vitamina C con POST /products (categoryIds: [9])
9. Crear Smartwatch Garmin con POST /products (categoryIds: [7,9])

### Paso 4: Verificar Productos
10. Obtener producto ID=9 con GET /products/9
11. Actualizar producto ID=9 con PUT /products/9

### Paso 5: Probar Búsqueda Especial
12. Búsqueda básica con POST /products/search
13. Filtrar por marca "Trek" con POST /products/search
14. Filtrar por rango de precios 40K-300K con POST /products/search
15. Búsqueda compleja con productos deportivos

---

## 5. RESPUESTAS ESPERADAS

### Categoría creada exitosamente:
```json
{
  "id": 7,
  "name": "Deportes y Fitness",
  "description": "Equipos deportivos, ropa deportiva y suplementos para mantenerse en forma"
}
```

### Producto creado exitosamente:
```json
{
  "id": 9,
  "name": "Bicicleta de Montaña Trek X-Caliber 8",
  "description": "Bicicleta de montaña con marco de aluminio, 29 pulgadas, 12 velocidades Shimano",
  "price": 3200000,
  "stock": 8,
  "brand": "Trek",
  "categories": [
    {
      "id": 7,
      "name": "Deportes y Fitness"
    }
  ]
}
```

### Respuesta de búsqueda:
```json
{
  "content": [
    {
      "getId": 1,
      "getName": "iPhone 14 Pro 128GB",
      "getPrice": 4299000,
      "getStock": 25,
      "getBrand": "Apple",
      "getCategoryNames": "",
      "getCategoryIds": ""
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "number": 0,
  "size": 10
}
```

---

## 6. CÓDIGOS DE ESTADO HTTP

- **200 OK:** Operación exitosa (GET, PUT)
- **201 Created:** Recurso creado exitosamente (POST)
- **400 Bad Request:** Datos inválidos en la solicitud
- **404 Not Found:** Recurso no encontrado
- **500 Internal Server Error:** Error del servidor

---

## 7. NOTAS IMPORTANTES

1. **IDs Auto-generados:** Los IDs se generan automáticamente, usa los que retorna la API
2. **Precios en COP:** Todos los precios están en pesos colombianos
3. **Categorías Obligatorias:** Los productos deben tener al menos una categoría
4. **Datos Iniciales:** La aplicación carga automáticamente 6 categorías y 8 productos al iniciar
5. **Relación Many-to-Many:** Un producto puede pertenecer a múltiples categorías
6. **Validaciones:** Todos los campos obligatorios deben ser proporcionados
7. **Paginación:** El endpoint de búsqueda soporta paginación y ordenamiento