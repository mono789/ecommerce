# 🛒 E-commerce API - Prueba Técnica

Aplicación Spring Boot profesional para e-commerce con **PostgreSQL**, búsqueda avanzada, **patrón Command** y documentación completa con Swagger.

## 📋 Características Implementadas

✅ **6 Endpoints CRUD** funcionando con JPA  
✅ **Relación Many-to-Many** (Product ↔ Category)  
✅ **Endpoint especial** de búsqueda con patrón REQUEST (POST con body)  
✅ **Query nativa** con countQuery y proyección a interfaz  
✅ **MapStruct** para mapeo de entidades  
✅ **Swagger** con documentación profesional  
✅ **PostgreSQL** como base de datos principal  
✅ **Docker** para fácil despliegue  
✅ **Patrón Command** para desacoplamiento  

## 🎯 API Endpoints (6 CRUD + 1 Especial)

### **📂 CATEGORÍAS (3 endpoints)**
1. `POST /categories` - Crear categoría
2. `GET /categories/{id}` - Obtener categoría por ID  
3. `PUT /categories/{id}` - Actualizar categoría

### **📦 PRODUCTOS (3 endpoints + 1 especial)**
4. `POST /products` - Crear producto
5. `GET /products/{id}` - Obtener producto por ID
6. `PUT /products/{id}` - Actualizar producto
7. **`POST /products/search`** - **Búsqueda especial con query nativa**

## 🏗️ Arquitectura con Patrón Command

### **Entidades Principales**
```
Product ↔ Category (Many-to-Many)
├── product_categories (tabla intermedia)
```

### **Flujo de Ejecución**
```
1. Request (DTO de entrada)
   ↓
2. Command.builder()... (construcción manual en Controller)
   ↓
3. Command (DTO interno)
   ↓
4. Service.method(command)
   ↓
5. MapStruct Mapper
   ↓
6. Repository (query nativa para búsqueda)
   ↓
7. Response (DTO de salida)
```

### **Estructura del Proyecto**
```
src/main/java/com/ecommerce/
├── entity/
│   ├── Product.java                  # Entidad producto con todas las propiedades
│   └── Category.java                 # Entidad categoría
├── dto/
│   ├── command/
│   │   ├── ProductSearchCommand.java # Command para búsqueda
│   │   ├── ProductCreateCommand.java # Command para crear productos
│   │   └── CategoryCreateCommand.java# Command para crear categorías
│   ├── request/                      # DTOs de entrada (3 clases)
│   │   ├── ProductCreateRequest.java
│   │   ├── ProductSearchRequest.java
│   │   └── CategoryCreateRequest.java
│   ├── response/                     # DTOs de respuesta (2 clases)
│   │   ├── ProductResponse.java
│   │   └── CategoryResponse.java
│   └── projection/
│       └── ProductSearchProjection.java # Interfaz para query nativa
├── controller/
│   ├── ProductController.java        # 4 endpoints (3 CRUD + 1 especial)
│   └── CategoryController.java       # 3 endpoints CRUD
├── service/
│   ├── ProductService.java           # Lógica de productos
│   └── CategoryService.java          # Lógica de categorías
├── repository/
│   ├── ProductRepository.java        # Query nativa simplificada
│   └── CategoryRepository.java       # Repositorio categorías
├── mapper/
│   ├── ProductMapper.java            # MapStruct para productos
│   └── CategoryMapper.java           # MapStruct para categorías
└── config/
    └── DataLoader.java               # Datos iniciales colombianos
```

### **Relación Many-to-Many**
```
Product ↔ Category
(product_categories table)

Un producto puede tener múltiples categorías
Una categoría puede tener múltiples productos
```

### **Query Nativa (PostgreSQL)**

```sql
-- Query principal
SELECT 
    p.product_id as id,
    p.name as name,
    p.description as description,
    p.price as price,
    p.stock as stock,
    p.image_url as imageUrl,
    p.brand as brand,
    p.model as model,
    p.weight as weight,
    p.dimensions as dimensions,
    p.active as active,
    p.featured as featured,
    p.created_at as createdAt,
    p.updated_at as updatedAt,
    '' as categoryNames,
    '' as categoryIds
FROM products p
WHERE p.active = true
    AND (:name IS NULL OR p.name ILIKE '%' || :name || '%')
    AND (:brand IS NULL OR p.brand ILIKE '%' || :brand || '%')
    AND (:minPrice IS NULL OR p.price >= :minPrice)
    AND (:maxPrice IS NULL OR p.price <= :maxPrice)
    AND (:minStock IS NULL OR p.stock >= :minStock)
ORDER BY p.name ASC

-- CountQuery separada
SELECT COUNT(p.product_id)
FROM products p
WHERE p.active = true
    AND (:name IS NULL OR p.name ILIKE '%' || :name || '%')
    AND (:brand IS NULL OR p.brand ILIKE '%' || :brand || '%')
    AND (:minPrice IS NULL OR p.price >= :minPrice)
    AND (:maxPrice IS NULL OR p.price <= :maxPrice)
    AND (:minStock IS NULL OR p.stock >= :minStock)
```

### **Endpoint Especial - Implementación del Patrón Command**
```java
@PostMapping("/search")
public ResponseEntity<Page<ProductSearchProjection>> searchProducts(
        @RequestBody ProductSearchRequest request) {
    
    // Construcción manual del Command usando Builder Pattern
    var command = ProductSearchCommand.builder()
        .name(request.getName())
        .brand(request.getBrand())
        .minPrice(request.getMinPrice())
        .maxPrice(request.getMaxPrice())
        .minStock(request.getMinStock())
        .build();
    
    // Query nativa con countQuery separada y mapeo a interfaz
    var results = productService.searchProducts(command, pageable);
    return ResponseEntity.ok(results);
}
```

## 🚀 Inicio Rápido

### Opción 1: Docker Compose (Recomendado)

```bash
# Clonar repositorio
git clone <repositorio-url>
cd "prueba tecnica"

# Ejecutar con Docker Compose
docker-compose up -d

# Ver logs
docker-compose logs -f ecommerce-api

# Acceder a Swagger
# http://localhost:8080/swagger-ui.html
```

### Opción 2: Instalación Local

#### Prerequisitos
- Java 17+
- Maven 3.8+
- PostgreSQL 12+

#### Ejecutar:

**Desarrollo con PostgreSQL:**
```bash
# 1. Configurar PostgreSQL
createdb -U postgres ecommerce_dev
psql -U postgres -c "CREATE USER ecommerce_user WITH PASSWORD 'ecommerce_password';"
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE ecommerce_dev TO ecommerce_user;"

# 2. Ejecutar aplicación
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Testing:**
```bash
mvn test -Dspring.profiles.active=test
```

## 🌐 Acceso a la Aplicación

- **API Base URL**: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api-docs`
- **pgAdmin** (Docker): `http://localhost:5050`
  - Email: `admin@ecommerce.com`
  - Password: `admin123`

### **🐘 Configuración de pgAdmin**

pgAdmin se ejecuta automáticamente con Docker Compose para gestión visual de PostgreSQL:

#### **Acceso inicial:**
1. Abrir: `http://localhost:5050`
2. **Login:**
   - Email: `admin@ecommerce.com`
   - Password: `admin123`

#### **Configurar servidor PostgreSQL:**
1. **Clic derecho en "Servers" → "Register" → "Server"**
2. **Pestaña "General":**
   - Name: `Ecommerce DB`
3. **Pestaña "Connection":**
   - Host name/address: `postgres` (nombre del contenedor)
   - Port: `5432`
   - Maintenance database: `ecommerce_db`
   - Username: `ecommerce_user`
   - Password: `ecommerce_password`
4. **Guardar**

#### **Acceso directo desde host:**
Si prefieres conectar desde herramientas externas:
- **Host**: `localhost`
- **Port**: `5433` (mapeado desde Docker)
- **Database**: `ecommerce_db`
- **User**: `ecommerce_user`
- **Password**: `ecommerce_password`

#### **🔧 Troubleshooting pgAdmin:**

**Si no puedes conectar a PostgreSQL desde pgAdmin:**
1. Verificar que ambos contenedores estén ejecutándose:
   ```bash
   docker-compose ps
   ```

2. Verificar conectividad de red:
   ```bash
   docker-compose exec pgadmin ping postgres
   ```

3. Revisar logs de pgAdmin:
   ```bash
   docker-compose logs pgadmin
   ```

4. Reiniciar pgAdmin si es necesario:
   ```bash
   docker-compose restart pgadmin
   ```

**Datos de conexión correctos para pgAdmin:**
- ⚠️ **Importante**: Usar `postgres` como host (nombre del contenedor)
- ⚠️ **No usar** `localhost` o `127.0.0.1` desde pgAdmin
- ⚠️ **Puerto interno**: `5432` (no 5433)

**Consultas útiles en pgAdmin:**
```sql
-- Ver todas las tablas
SELECT table_name FROM information_schema.tables 
WHERE table_schema = 'public';

-- Verificar datos iniciales
SELECT COUNT(*) as total_products FROM products;
SELECT COUNT(*) as total_categories FROM categories;

-- Ver relaciones many-to-many
SELECT p.name as producto, c.name as categoria
FROM products p
JOIN product_categories pc ON p.product_id = pc.product_id
JOIN categories c ON pc.category_id = c.category_id;
```

## 🗄️ Datos Iniciales

Se cargan automáticamente al iniciar la aplicación:

### **Categorías (6)**
- Tecnología
- Smartphones  
- Computadoras
- Audio y Video
- Gaming
- Hogar Inteligente

### **Productos (8 con precios en COP)**
- iPhone 14 Pro 128GB - $4,299,000
- MacBook Air M2 13" - $5,499,000
- Samsung Galaxy S23 256GB - $3,799,000
- Sony WH-1000XM5 - $1,299,000
- PlayStation 5 - $2,599,000
- Amazon Echo Dot 5ta Gen - $199,000
- Dell XPS 13 Plus - $6,299,000
- AirPods Pro 2da Gen - $999,000

## 🧪 Pruebas en Swagger

### **1. Crear Categoría**
```json
POST /categories
{
  "name": "Tecnología Avanzada",
  "description": "Productos tecnológicos de última generación"
}
```

### **2. Crear Producto**
```json
POST /products
{
  "name": "iPhone 15 Pro",
  "description": "Smartphone Apple con chip A17 Pro",
  "price": 4500000,
  "stock": 20,
  "brand": "Apple",
  "model": "iPhone 15 Pro",
  "weight": 0.187,
  "dimensions": "14.67 x 7.09 x 0.83 cm",
  "featured": true,
  "categoryIds": [1, 2]
}
```

### **3. Búsqueda Especial (Query Nativa)**
```json
POST /products/search
{
  "name": "iPhone",
  "brand": "Apple",
  "minPrice": 1000000,
  "maxPrice": 5000000,
  "minStock": 5,
  "page": 0,
  "size": 10
}
```

### **4. Obtener por ID**
```
GET /categories/1
GET /products/1
```

### **5. Actualizar**
```json
PUT /categories/1
{
  "name": "Tecnología Premium",
  "description": "Productos tecnológicos premium"
}

PUT /products/1
{
  "name": "iPhone 14 Pro Max",
  "description": "iPhone actualizado",
  "price": 4799000,
  "stock": 15,
  "brand": "Apple",
  "model": "iPhone 14 Pro Max",
  "weight": 0.240,
  "dimensions": "16.07 x 7.81 x 0.78 cm",
  "featured": true,
  "categoryIds": [1, 2]
}
```

## 🔧 Comandos Útiles

### **Docker**
```bash
# Iniciar servicios
docker-compose up -d

# Ver logs
docker-compose logs -f ecommerce-api

# Parar servicios
docker-compose down

# Reiniciar solo la app
docker-compose restart ecommerce-api

# Acceder a PostgreSQL
docker-compose exec postgres psql -U ecommerce_user -d ecommerce_db

# Reiniciar pgAdmin
docker-compose restart pgadmin

# Ver logs de pgAdmin
docker-compose logs pgadmin
```

### **Maven**
```bash
# Compilar
mvn clean compile

# Ejecutar tests
mvn test

# Compilar y ejecutar
mvn spring-boot:run

# Generar JAR
mvn clean package
```

### **PostgreSQL**
```bash
# Conectar a DB local
psql -h localhost -p 5432 -U ecommerce_user -d ecommerce_dev

# Ver tablas
\dt

# Ver datos
SELECT * FROM products;
SELECT * FROM categories;
SELECT * FROM product_categories;

# Probar query nativa
SELECT p.product_id, p.name, p.brand, p.price 
FROM products p 
WHERE p.active = true 
  AND p.name ILIKE '%iPhone%'
ORDER BY p.name;
```

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.2.0** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **PostgreSQL 15** - Base de datos principal
- **MapStruct 1.5.5** - Mapeo de entidades
- **SpringDoc OpenAPI 3** - Documentación Swagger
- **Lombok** - Reducción de boilerplate
- **Bean Validation** - Validaciones
- **Docker & Docker Compose** - Containerización
- **Maven** - Gestión de dependencias

## 📊 Funcionalidades Técnicas

### **Query Nativa**
- Búsqueda con filtros básicos pero efectivos
- Paginación con Pageable
- CountQuery separada para performance
- Mapeo automático a interfaz `ProductSearchProjection`
- Sintaxis PostgreSQL (ILIKE, parámetros nombrados)
- Ordenamiento fijo por nombre

### **Patrón Command Simplificado**
- Commands construidos manualmente con Builder
- Separación clara entre Request y Command
- Desacoplamiento entre Controllers y Services
- Solo campos necesarios en ProductSearchCommand

### **MapStruct**
- Mapeo automático entre DTOs y Entidades
- Configuraciones personalizadas con @Mapping
- Generación de código en tiempo de compilación
- Soporte para Product y Category

### **Validaciones**
- Bean Validation en DTOs
- Validaciones de negocio en entidades
- Manejo de errores con Spring Boot

### **Docker**
- Multi-stage build para optimización
- Usuario no-root para seguridad
- Variables de entorno configurables
- Health checks incluidos

## 🎯 Cumplimiento de Requisitos

✅ **6 Endpoints CRUD**: 3 para Category + 3 para Product  
✅ **Relaciones JPA**: Many-to-Many entre Product y Category  
✅ **Endpoint especial**: POST /products/search con patrón REQUEST  
✅ **Query nativa**: Con countQuery separada en PostgreSQL  
✅ **Proyección a interfaz**: ProductSearchProjection  
✅ **MapStruct**: Para Product y Category  
✅ **Swagger**: Documentación completa  
✅ **Tema e-commerce**: Productos y categorías tecnológicas  