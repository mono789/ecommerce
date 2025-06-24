# 🛒 E-commerce API - Prueba Técnica

Aplicación Spring Boot profesional para e-commerce con **PostgreSQL**, búsqueda avanzada, **patrón Command** y documentación completa con Swagger.

## 📋 Características Implementadas

✅ **6 Endpoints CRUD** funcionando con JPA  
✅ **Relaciones JPA** - One-to-many y Many-to-many  
✅ **Endpoint especial** de búsqueda con patrón REQUEST  
✅ **Query nativa** con countQuery y proyección a interfaz  
✅ **MapStruct** para mapeo de entidades  
✅ **Swagger** con documentación profesional  
✅ **PostgreSQL** como base de datos principal  
✅ **Docker** para fácil despliegue  
✅ **Perfiles organizados** en archivos separados  
✅ **Patrón Command** para desacoplamiento  

## 🏗️ Arquitectura con Patrón Command

### **Flujo de Ejecución**
```
1. Request (DTO de entrada)
   ↓
2. Command.builder()... (construcción manual)
   ↓
3. Command (DTO interno)
   ↓
4. Service.method(command)
   ↓
5. Repository (query nativa)
   ↓
6. Response (DTO de salida)
```

### **Estructura de Commands**
```
src/main/java/com/ecommerce/dto/
├── command/
│   ├── ProductSearchCommand.java     # Búsqueda avanzada
│   ├── UserCreateCommand.java        # Creación de usuarios
│   ├── ProductCreateCommand.java     # Creación de productos
│   └── CategoryCreateCommand.java    # Creación de categorías
├── request/                          # DTOs de entrada
└── response/                         # DTOs de respuesta
```

### **Construcción Manual con Builder Pattern**
```java
// En el Controller - Construcción manual campo por campo
@PostMapping("/search")
public ResponseEntity<Page<ProductSearchProjection>> searchProducts(
        @RequestBody ProductSearchRequest request, Pageable pageable) {
    
    // Validación del request
    if (request == null) {
        throw new IllegalArgumentException("Request cannot be null");
    }
    
    // Construcción manual del Command usando Builder Pattern
    var command = ProductSearchCommand.builder()
        .name(request.getName())
        .description(request.getDescription())
        .brand(request.getBrand())
        .minPrice(request.getMinPrice())
        .maxPrice(request.getMaxPrice())
        .minStock(request.getMinStock())
        .active(request.getActive())
        .featured(request.getFeatured())
        .searchText(request.getSearchText())
        .categoryIds(request.getCategoryIds())
        .categoryNames(request.getCategoryNames())
        .sortBy(request.getSortBy())
        .sortDirection(request.getSortDirection())
        .build();
    
    var results = productService.searchProducts(command, pageable);
    return ResponseEntity.ok(results);
}
```

## 🚀 Inicio Rápido

### Opción 1: Docker Compose (Recomendado)

```bash
# Clonar repositorio
git clone <repositorio-url>
cd ecommerce-api

# Ejecutar con Docker Compose
docker-compose up -d

# Ver logs
docker-compose logs -f ecommerce-api
```

### Opción 2: Instalación Local

#### Prerequisitos
- Java 17+
- Maven 3.8+
- PostgreSQL 12+ (para perfil dev)

#### Ejecutar según el perfil:

**Desarrollo con PostgreSQL:**
```bash
# 1. Configurar PostgreSQL
createdb -U postgres ecommerce_dev
psql -U postgres -c "CREATE USER ecommerce_user WITH PASSWORD 'ecommerce_password';"
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE ecommerce_dev TO ecommerce_user;"

# 2. Ejecutar aplicación
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Desarrollo local rápido con H2:**
```bash
# Sin configuración de BD necesaria
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

**Testing:**
```bash
mvn test -Dspring.profiles.active=test
```

## 🌐 Acceso a la Aplicación

- **API Base URL**: `http://localhost:8080/api/v1`
- **Swagger UI**: `http://localhost:8080/api/v1/swagger-ui.html`
- **API Docs**: `http://localhost:8080/api/v1/api-docs`
- **H2 Console** (perfiles local/test): `http://localhost:8080/api/v1/h2-console`
- **pgAdmin** (Docker): `http://localhost:5050`
  - Email: `admin@ecommerce.com`
  - Password: `admin123`

## 🗄️ Configuración de pgAdmin (Docker)

### **Acceso Inicial**
1. Acceder a `http://localhost:5050`
2. **Login:**
   - Email: `admin@ecommerce.com`
   - Password: `admin123`

### **Configurar Conexión a PostgreSQL**
Una vez dentro de pgAdmin:

1. **Clic derecho en "Servers"** → **"Register"** → **"Server..."**

2. **Pestaña "General":**
   - **Name**: `E-commerce PostgreSQL`

3. **Pestaña "Connection":**
   - **Host name/address**: `postgres` ⚠️ *(importante: no localhost)*
   - **Port**: `5432`
   - **Maintenance database**: `ecommerce_dev`
   - **Username**: `ecommerce_user`
   - **Password**: `ecommerce_password`
   - ✅ **Save password**: marcar

4. **Clic en "Save"**

### **Verificar Datos**
Después de conectar, navegar a:
```
E-commerce PostgreSQL → Databases → ecommerce_dev → Schemas → public → Tables
```

**Tablas disponibles:**
- `users` (5 usuarios de Colombia)
- `categories` (6 categorías)
- `products` (8 productos con precios COP)
- `product_categories` (relaciones many-to-many)
- `orders` (4 órdenes)
- `order_items` (items de las órdenes)

### **Consultas de Ejemplo**
```sql
-- Ver usuarios por ciudad
SELECT city, COUNT(*) as usuarios FROM users GROUP BY city;

-- Ver productos con sus categorías
SELECT p.name, p.price, STRING_AGG(c.name, ', ') as categorias
FROM products p
JOIN product_categories pc ON p.id = pc.product_id
JOIN categories c ON pc.category_id = c.id
GROUP BY p.id, p.name, p.price;

-- Ver órdenes con envíos
SELECT u.first_name, u.city, o.total_amount, o.shipping_cost
FROM orders o
JOIN users u ON o.user_id = u.id
ORDER BY o.created_at DESC;
```

### **Solución de Problemas**
- **Error de conexión**: Verificar que el contenedor `postgres` esté corriendo
- **Host incorrecto**: Usar `postgres`, no `localhost` (red Docker)
- **Base de datos no existe**: Esperar que la aplicación Spring Boot cree las tablas

## ⚙️ Perfiles de Configuración

La aplicación utiliza **archivos de configuración separados** para cada perfil:

### 📁 Estructura de Configuración
```
src/main/resources/
├── application.yml              # Configuración base común
├── application-dev.yml          # Desarrollo con PostgreSQL
├── application-docker.yml       # Docker Compose
├── application-prod.yml         # Producción
├── application-test.yml         # Testing con H2
└── application-local.yml        # Desarrollo local con H2
```

### 🔧 Perfiles Disponibles

| Perfil | Base de Datos | Uso | Activación |
|--------|---------------|-----|------------|
| **dev** | PostgreSQL (local) | Desarrollo principal | `spring.profiles.active=dev` |
| **local** | H2 (memoria) | Desarrollo rápido | `spring.profiles.active=local` |
| **docker** | PostgreSQL (container) | Docker Compose | `SPRING_PROFILES_ACTIVE=docker` |
| **prod** | PostgreSQL (remota) | Producción | `spring.profiles.active=prod` |
| **test** | H2 (memoria) | Testing | Automático en tests |

## 🔍 Endpoint Especial - Búsqueda Avanzada

### POST `/api/v1/products/search`

Búsqueda avanzada con **patrón Command**, **query nativa**, **countQuery** optimizada y **proyección a interfaz**.

#### **Flujo del Patrón Command:**
```java
// 1. Request del cliente
ProductSearchRequest request = {...};

// 2. Controller construye Command manualmente con Builder Pattern
var command = ProductSearchCommand.builder()
    .name(request.getName())
    .description(request.getDescription())
    .minPrice(request.getMinPrice())
    .maxPrice(request.getMaxPrice())
    .categoryIds(request.getCategoryIds())
    .sortBy(request.getSortBy())
    .build();

// 3. Service ejecuta con Command
Page<ProductSearchProjection> results = productService.searchProducts(command, pageable);
```

#### **Ejemplo de Request:**
```json
{
  "name": "iPhone",
  "description": "smartphone",
  "brand": "Apple",
  "minPrice": 1000000,
  "maxPrice": 5000000,
  "minStock": 1,
  "active": true,
  "featured": true,
  "searchText": "teléfono",
  "categoryIds": [1, 2],
  "categoryNames": ["Smartphones", "Tecnología"],
  "sortBy": "price",
  "sortDirection": "asc"
}
```

**Características:**
- ✅ Patrón Command: Request → Command → Service
- ✅ Paginación con `Pageable`
- ✅ Query nativa PostgreSQL con JOINs
- ✅ CountQuery separada para optimización
- ✅ Mapeo a `ProductSearchProjection` (interfaz)
- ✅ Búsqueda de texto con scoring de relevancia
- ✅ Filtros múltiples dinámicos
- ✅ Ordenamiento configurable
- ✅ Desacoplamiento total

## 📁 Endpoints CRUD con Patrón Command

### 👤 Usuarios (Con Command)
- `GET /api/v1/users` - Listar usuarios
- `GET /api/v1/users/{id}` - Obtener usuario
- `POST /api/v1/users` - Crear usuario (**usa UserCreateCommand**)
- `PUT /api/v1/users/{id}` - Actualizar usuario (**usa UserCreateCommand**)
- `DELETE /api/v1/users/{id}` - Eliminar usuario

### 🏷️ Categorías (Con Command)
- `GET /api/v1/categories` - Listar categorías
- `GET /api/v1/categories/{id}` - Obtener categoría
- `POST /api/v1/categories` - Crear categoría (**usa CategoryCreateCommand**)
- `PUT /api/v1/categories/{id}` - Actualizar categoría (**usa CategoryCreateCommand**)
- `DELETE /api/v1/categories/{id}` - Eliminar categoría

### 📦 Productos (Con Command)
- `GET /api/v1/products` - Listar productos
- `GET /api/v1/products/{id}` - Obtener producto
- `POST /api/v1/products` - Crear producto (**usa ProductCreateCommand**)
- `PUT /api/v1/products/{id}` - Actualizar producto (**usa ProductCreateCommand**)
- `DELETE /api/v1/products/{id}` - Eliminar producto
- `POST /api/v1/products/search` - **Búsqueda avanzada** (**usa ProductSearchCommand**)

## 🏗️ Arquitectura con Commands

### **Capas de la Aplicación**
```
┌─────────────────────────────────────────┐
│              CONTROLLER                 │
│  - Recibe ProductSearchRequest          │
│  - Construye ProductSearchCommand       │
│  - Llama al Service                     │
└─────────────────┬───────────────────────┘
                  │ Command.builder()...build()
                  ▼
┌─────────────────────────────────────────┐
│               SERVICE                   │
│  - Recibe ProductSearchCommand          │
│  - Ejecuta lógica de negocio            │
│  - Llama al Repository                  │
└─────────────────┬───────────────────────┘
                  │ command properties
                  ▼
┌─────────────────────────────────────────┐
│             REPOSITORY                  │
│  - Query nativa con @Param              │
│  - CountQuery separada                  │
│  - Mapeo a ProductSearchProjection      │
└─────────────────────────────────────────┘
```

## 📊 Datos de Prueba

La aplicación incluye datos de prueba (cargados en perfiles dev, local, docker):

### 👥 Usuarios (5)
- **Juan Carlos Rodríguez** - Bogotá, +573001234567
- **María Fernanda Gómez** - Medellín, +573012345678
- **Carlos Andrés Vargas** - Cali, +573023456789
- **Ana Lucía Morales** - Barranquilla, +573034567890
- **Diego Fernando Herrera** - Cartagena, +573045678901

### 📱 Productos (8) - Precios en COP
- **iPhone 14 Pro**: $4.299.000 COP
- **MacBook Air M2**: $5.499.000 COP
- **Samsung Galaxy S23**: $3.499.000 COP
- **AirPods Pro 2da Gen**: $899.000 COP
- **PlayStation 5 Digital**: $2.299.000 COP
- **Echo Dot 5ta Gen**: $199.000 COP
- **Lenovo ThinkPad E14**: $2.799.000 COP
- **JBL Flip 6**: $549.000 COP

### 📦 Órdenes (4)
- Órdenes distribuidas entre diferentes ciudades colombianas
- Envíos con **Servientrega**
- Costos variables según destino

## 🔗 Relaciones JPA

### One-to-Many
- `User` → `Orders` (Un usuario puede tener múltiples órdenes)
- `Order` → `OrderItems` (Una orden puede tener múltiples items)
- `Product` → `OrderItems` (Un producto puede estar en múltiples items)

### Many-to-Many
- `Product` ↔ `Categories` (Un producto puede tener múltiples categorías)

## 🛠️ Tecnologías

- **Framework**: Spring Boot 3.2.0
- **Java**: 17
- **Base de Datos**: PostgreSQL 15 / H2 (desarrollo)
- **ORM**: Spring Data JPA / Hibernate
- **Mapeo**: MapStruct 1.5.5 (solo para entidades)
- **Patrón**: Command Pattern con Builder Manual
- **Documentación**: SpringDoc OpenAPI 3
- **Build**: Maven
- **Testing**: JUnit 5 + Testcontainers
- **Containerización**: Docker + Docker Compose

## 🔧 Ejemplos de Uso con Commands

### **Crear Usuario**
```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Carlos",
    "lastName": "Mendoza", 
    "email": "carlos@example.com",
    "phone": "+573001234567",
    "address": "Carrera 15 #93-47",
    "city": "Bogotá",
    "country": "Colombia"
  }'
```

### **Búsqueda Avanzada de Productos**
```bash
curl -X POST http://localhost:8080/api/v1/products/search?page=0&size=10 \
  -H "Content-Type: application/json" \
  -d '{
    "searchText": "iPhone",
    "minPrice": 1000000,
    "maxPrice": 5000000,
    "categoryNames": ["Smartphones"],
    "sortBy": "price",
    "sortDirection": "asc"
  }'
```

## 📈 Monitoreo y Logs

### Logs por Perfil
- **dev**: `logs/ecommerce-dev.log`
- **local**: `logs/ecommerce-local.log`
- **docker**: `logs/ecommerce-docker.log`
- **prod**: `/var/logs/ecommerce-api.log`

### Actuator Endpoints
```bash
# Health check
curl http://localhost:8080/api/v1/actuator/health

# Métricas (dev/local)
curl http://localhost:8080/api/v1/actuator/metrics

# Info de la aplicación
curl http://localhost:8080/api/v1/actuator/info
```

## 🚀 Despliegue en Producción

### Variables de Entorno Requeridas
```bash
DB_HOST=your-postgres-host
DB_PORT=5432
DB_NAME=ecommerce_prod
DB_USERNAME=your-username
DB_PASSWORD=your-password
SPRING_PROFILES_ACTIVE=prod
```

### Configuración de Seguridad
- Usuario no-root en contenedor
- Healthchecks configurados
- Timeouts optimizados
- Pool de conexiones configurado
- Compresión HTTP habilitada
- HTTP/2 habilitado
- Métricas Prometheus