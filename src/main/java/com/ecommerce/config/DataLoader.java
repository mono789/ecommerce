package com.ecommerce.config;

import com.ecommerce.entity.*;
import com.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Componente para cargar datos iniciales en la base de datos
 * Configurado con datos de Colombia
 * 
 * @author Developer
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("Cargando datos iniciales de Colombia...");
            loadInitialData();
            log.info("Datos iniciales de Colombia cargados exitosamente");
        } else {
            log.info("Los datos iniciales ya existen, omitiendo carga");
        }
    }
    
    private void loadInitialData() {
        // Crear usuarios colombianos
        List<User> users = createColombianUsers();
        userRepository.saveAll(users);
        
        // Crear categorías
        List<Category> categories = createCategories();
        categoryRepository.saveAll(categories);
        
        // Crear productos con precios en pesos colombianos
        List<Product> products = createColombianProducts(categories);
        productRepository.saveAll(products);
        
        // Crear órdenes
        List<Order> orders = createColombianOrders(users);
        orderRepository.saveAll(orders);
        
        // Crear items de orden
        createOrderItems(orders, products);
    }
    
    private List<User> createColombianUsers() {
        return Arrays.asList(
            User.builder()
                .firstName("Juan Carlos")
                .lastName("Rodríguez")
                .email("juan.rodriguez@gmail.com")
                .phone("+573001234567")
                .address("Carrera 15 # 93-47, Chapinero")
                .city("Bogotá")
                .country("Colombia")
                .active(true)
                .build(),
                
            User.builder()
                .firstName("María Fernanda")
                .lastName("Gómez")
                .email("maria.gomez@hotmail.com")
                .phone("+573012345678")
                .address("Calle 70 # 52-20, Laureles")
                .city("Medellín")
                .country("Colombia")
                .active(true)
                .build(),
                
            User.builder()
                .firstName("Carlos Andrés")
                .lastName("Vargas")
                .email("carlos.vargas@yahoo.com")
                .phone("+573023456789")
                .address("Avenida 6N # 28-10, San Fernando")
                .city("Cali")
                .country("Colombia")
                .active(true)
                .build(),
                
            User.builder()
                .firstName("Ana Lucía")
                .lastName("Morales")
                .email("ana.morales@outlook.com")
                .phone("+573034567890")
                .address("Carrera 51B # 79-200, Riomar")
                .city("Barranquilla")
                .country("Colombia")
                .active(true)
                .build(),
                
            User.builder()
                .firstName("Diego Fernando")
                .lastName("Herrera")
                .email("diego.herrera@gmail.com")
                .phone("+573045678901")
                .address("Calle 1 # 9-62, Manga")
                .city("Cartagena")
                .country("Colombia")
                .active(true)
                .build()
        );
    }
    
    private List<Category> createCategories() {
        return Arrays.asList(
            Category.builder()
                .name("Tecnología")
                .description("Productos tecnológicos y electrónicos de última generación")
                .active(true)
                .build(),
                
            Category.builder()
                .name("Smartphones")
                .description("Teléfonos móviles inteligentes de todas las marcas")
                .active(true)
                .build(),
                
            Category.builder()
                .name("Computadoras")
                .description("Portátiles, equipos de escritorio y accesorios computacionales")
                .active(true)
                .build(),
                
            Category.builder()
                .name("Audio y Video")
                .description("Audífonos, parlantes, equipos de sonido y entretenimiento")
                .active(true)
                .build(),
                
            Category.builder()
                .name("Gaming")
                .description("Consolas, videojuegos y accesorios para gamers")
                .active(true)
                .build(),
                
            Category.builder()
                .name("Hogar Inteligente")
                .description("Dispositivos inteligentes para automatización del hogar")
                .active(true)
                .build()
        );
    }
    
    private List<Product> createColombianProducts(List<Category> categories) {
        return Arrays.asList(
            Product.builder()
                .name("iPhone 14 Pro 128GB")
                .description("Smartphone Apple con chip A16 Bionic, cámara de 48MP y pantalla ProMotion de 6.1 pulgadas")
                .price(new BigDecimal("4299000")) // ~$1100 USD en pesos colombianos
                .stock(25)
                .imageUrl("https://example.com/iphone14pro.jpg")
                .brand("Apple")
                .model("iPhone 14 Pro")
                .weight(new BigDecimal("0.206"))
                .dimensions("15.4 x 7.3 x 0.8 cm")
                .active(true)
                .featured(true)
                .categories(java.util.Set.of(categories.get(0), categories.get(1)))
                .build(),
                
            Product.builder()
                .name("MacBook Air M2 13\"")
                .description("Portátil Apple con chip M2, 8GB RAM, 256GB SSD y pantalla Liquid Retina")
                .price(new BigDecimal("5499000")) // ~$1400 USD en pesos colombianos
                .stock(15)
                .imageUrl("https://example.com/macbookair.jpg")
                .brand("Apple")
                .model("MacBook Air M2")
                .weight(new BigDecimal("1.24"))
                .dimensions("30.4 x 21.5 x 1.13 cm")
                .active(true)
                .featured(true)
                .categories(java.util.Set.of(categories.get(0), categories.get(2)))
                .build(),
                
            Product.builder()
                .name("Samsung Galaxy S23 256GB")
                .description("Smartphone Android con procesador Snapdragon 8 Gen 2, cámara de 50MP y pantalla Dynamic AMOLED")
                .price(new BigDecimal("3499000")) // ~$900 USD en pesos colombianos
                .stock(40)
                .imageUrl("https://example.com/galaxys23.jpg")
                .brand("Samsung")
                .model("Galaxy S23")
                .weight(new BigDecimal("0.168"))
                .dimensions("14.6 x 7.1 x 0.8 cm")
                .active(true)
                .featured(false)
                .categories(java.util.Set.of(categories.get(0), categories.get(1)))
                .build(),
                
            Product.builder()
                .name("AirPods Pro 2da Generación")
                .description("Audífonos inalámbricos con cancelación activa de ruido y audio espacial personalizado")
                .price(new BigDecimal("899000")) // ~$230 USD en pesos colombianos
                .stock(60)
                .imageUrl("https://example.com/airpodspro.jpg")
                .brand("Apple")
                .model("AirPods Pro 2nd Gen")
                .weight(new BigDecimal("0.056"))
                .dimensions("6.1 x 4.5 x 2.2 cm")
                .active(true)
                .featured(true)
                .categories(java.util.Set.of(categories.get(0), categories.get(3)))
                .build(),
                
            Product.builder()
                .name("PlayStation 5 Digital Edition")
                .description("Consola de videojuegos Sony de nueva generación sin unidad de disco")
                .price(new BigDecimal("2299000")) // ~$590 USD en pesos colombianos
                .stock(12)
                .imageUrl("https://example.com/ps5digital.jpg")
                .brand("Sony")
                .model("PlayStation 5 Digital")
                .weight(new BigDecimal("3.9"))
                .dimensions("39 x 26 x 9.2 cm")
                .active(true)
                .featured(true)
                .categories(java.util.Set.of(categories.get(0), categories.get(4)))
                .build(),
                
            Product.builder()
                .name("Echo Dot 5ta Generación")
                .description("Altavoz inteligente Amazon con Alexa para control de hogar inteligente")
                .price(new BigDecimal("199000")) // ~$50 USD en pesos colombianos
                .stock(80)
                .imageUrl("https://example.com/echodot.jpg")
                .brand("Amazon")
                .model("Echo Dot 5th Gen")
                .weight(new BigDecimal("0.304"))
                .dimensions("10.0 x 10.0 x 8.9 cm")
                .active(true)
                .featured(false)
                .categories(java.util.Set.of(categories.get(0), categories.get(5)))
                .build(),
                
            Product.builder()
                .name("Lenovo ThinkPad E14 Gen 4")
                .description("Portátil empresarial con procesador AMD Ryzen 5, 8GB RAM, 512GB SSD")
                .price(new BigDecimal("2799000")) // ~$720 USD en pesos colombianos
                .stock(20)
                .imageUrl("https://example.com/thinkpad.jpg")
                .brand("Lenovo")
                .model("ThinkPad E14 Gen 4")
                .weight(new BigDecimal("1.64"))
                .dimensions("32.4 x 23.5 x 1.79 cm")
                .active(true)
                .featured(false)
                .categories(java.util.Set.of(categories.get(0), categories.get(2)))
                .build(),
                
            Product.builder()
                .name("JBL Flip 6 Portable")
                .description("Parlante Bluetooth portátil resistente al agua con sonido JBL Pro")
                .price(new BigDecimal("549000")) // ~$140 USD en pesos colombianos
                .stock(35)
                .imageUrl("https://example.com/jblflip6.jpg")
                .brand("JBL")
                .model("Flip 6")
                .weight(new BigDecimal("0.55"))
                .dimensions("17.8 x 6.8 x 7.2 cm")
                .active(true)
                .featured(false)
                .categories(java.util.Set.of(categories.get(0), categories.get(3)))
                .build()
        );
    }
    
    private List<Order> createColombianOrders(List<User> users) {
        return Arrays.asList(
            Order.builder()
                .orderNumber("COL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .user(users.get(0))
                .total(new BigDecimal("5198000")) // iPhone + AirPods
                .subtotal(new BigDecimal("5198000"))
                .taxes(new BigDecimal("0")) // Sin impuestos separados 
                .shippingCost(new BigDecimal("0")) // Envío gratis
                .status(Order.OrderStatus.DELIVERED)
                .shippingAddress("Carrera 15 # 93-47, Chapinero, Bogotá")
                .billingAddress("Carrera 15 # 93-47, Chapinero, Bogotá")
                .notes("Entrega exitosa en portería del edificio")
                .build(),
                
            Order.builder()
                .orderNumber("COL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .user(users.get(1))
                .total(new BigDecimal("3548000")) // Samsung + JBL
                .subtotal(new BigDecimal("4048000"))
                .taxes(new BigDecimal("0"))
                .shippingCost(new BigDecimal("25000")) // Envío a Medellín
                .status(Order.OrderStatus.SHIPPED)
                .shippingAddress("Calle 70 # 52-20, Laureles, Medellín")
                .billingAddress("Calle 70 # 52-20, Laureles, Medellín")
                .notes("En tránsito con Servientrega")
                .build(),
                
            Order.builder()
                .orderNumber("COL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .user(users.get(2))
                .total(new BigDecimal("2348000")) // PlayStation + Echo Dot
                .subtotal(new BigDecimal("2498000"))
                .taxes(new BigDecimal("0"))
                .shippingCost(new BigDecimal("30000")) // Envío a Cali
                .status(Order.OrderStatus.PROCESSING)
                .shippingAddress("Avenida 6N # 28-10, San Fernando, Cali")
                .billingAddress("Avenida 6N # 28-10, San Fernando, Cali")
                .notes("Preparando pedido para envío")
                .build(),
                
            Order.builder()
                .orderNumber("COL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .user(users.get(3))
                .total(new BigDecimal("2819000")) // Lenovo ThinkPad
                .subtotal(new BigDecimal("2799000"))
                .taxes(new BigDecimal("0"))
                .shippingCost(new BigDecimal("20000")) // Envío a Barranquilla
                .status(Order.OrderStatus.CONFIRMED)
                .shippingAddress("Carrera 51B # 79-200, Riomar, Barranquilla")
                .billingAddress("Carrera 51B # 79-200, Riomar, Barranquilla")
                .notes("Pedido confirmado, procesando pago")
                .build()
        );
    }
    
    private void createOrderItems(List<Order> orders, List<Product> products) {
        // Orden 1: iPhone + AirPods (Juan Carlos - Bogotá)
        orderItemRepository.saveAll(Arrays.asList(
            OrderItem.builder()
                .order(orders.get(0))
                .product(products.get(0)) // iPhone 14 Pro
                .quantity(1)
                .unitPrice(products.get(0).getPrice())
                .totalPrice(products.get(0).getPrice())
                .notes("Color: Morado Intenso")
                .build(),
            OrderItem.builder()
                .order(orders.get(0))
                .product(products.get(3)) // AirPods Pro
                .quantity(1)
                .unitPrice(products.get(3).getPrice())
                .totalPrice(products.get(3).getPrice())
                .notes("Con estuche de carga MagSafe")
                .build()
        ));
        
        // Orden 2: Samsung + JBL (María Fernanda - Medellín)
        orderItemRepository.saveAll(Arrays.asList(
            OrderItem.builder()
                .order(orders.get(1))
                .product(products.get(2)) // Samsung Galaxy S23
                .quantity(1)
                .unitPrice(products.get(2).getPrice())
                .totalPrice(products.get(2).getPrice())
                .notes("Color: Negro Fantasma")
                .build(),
            OrderItem.builder()
                .order(orders.get(1))
                .product(products.get(7)) // JBL Flip 6
                .quantity(1)
                .unitPrice(products.get(7).getPrice())
                .totalPrice(products.get(7).getPrice())
                .notes("Color: Azul")
                .build()
        ));
        
        // Orden 3: PlayStation + Echo Dot (Carlos Andrés - Cali)
        orderItemRepository.saveAll(Arrays.asList(
            OrderItem.builder()
                .order(orders.get(2))
                .product(products.get(4)) // PlayStation 5 Digital
                .quantity(1)
                .unitPrice(products.get(4).getPrice())
                .totalPrice(products.get(4).getPrice())
                .notes("Consola nueva sellada")
                .build(),
            OrderItem.builder()
                .order(orders.get(2))
                .product(products.get(5)) // Echo Dot
                .quantity(1)
                .unitPrice(products.get(5).getPrice())
                .totalPrice(products.get(5).getPrice())
                .notes("Color: Antracita")
                .build()
        ));
        
        // Orden 4: Lenovo ThinkPad (Ana Lucía - Barranquilla)
        orderItemRepository.saveAll(Arrays.asList(
            OrderItem.builder()
                .order(orders.get(3))
                .product(products.get(6)) // Lenovo ThinkPad
                .quantity(1)
                .unitPrice(products.get(6).getPrice())
                .totalPrice(products.get(6).getPrice())
                .notes("Configuración empresarial con Windows 11 Pro")
                .build()
        ));
    }
} 