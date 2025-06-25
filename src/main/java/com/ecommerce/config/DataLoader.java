package com.ecommerce.config;

import com.ecommerce.entity.*;
import com.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Componente para cargar datos iniciales en la base de datos
 * Solo carga categorías y productos para la demo de 6 endpoints CRUD
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            log.info("Cargando datos iniciales de categorías y productos...");
            loadInitialData();
            log.info("Datos iniciales cargados exitosamente");
        } else {
            log.info("Los datos iniciales ya existen, omitiendo carga");
        }
    }
    
    private void loadInitialData() {
        // Crear categorías
        List<Category> categories = createCategories();
        categoryRepository.saveAll(categories);
        
        // Crear productos con precios en pesos colombianos
        List<Product> products = createColombianProducts(categories);
        productRepository.saveAll(products);
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
                .price(new BigDecimal("3799000")) // ~$970 USD en pesos colombianos
                .stock(30)
                .imageUrl("https://example.com/galaxys23.jpg")
                .brand("Samsung")
                .model("Galaxy S23")
                .weight(new BigDecimal("0.168"))
                .dimensions("14.6 x 7.1 x 0.76 cm")
                .active(true)
                .featured(true)
                .categories(java.util.Set.of(categories.get(0), categories.get(1)))
                .build(),
                
            Product.builder()
                .name("Sony WH-1000XM5")
                .description("Audífonos inalámbricos con cancelación de ruido líder en la industria")
                .price(new BigDecimal("1299000")) // ~$330 USD en pesos colombianos
                .stock(50)
                .imageUrl("https://example.com/sonywh1000xm5.jpg")
                .brand("Sony")
                .model("WH-1000XM5")
                .weight(new BigDecimal("0.250"))
                .dimensions("26.5 x 21.5 x 7.5 cm")
                .active(true)
                .featured(false)
                .categories(java.util.Set.of(categories.get(0), categories.get(3)))
                .build(),
                
            Product.builder()
                .name("PlayStation 5")
                .description("Consola de videojuegos de nueva generación con SSD ultra rápido y gráficos 4K")
                .price(new BigDecimal("2599000")) // ~$660 USD en pesos colombianos
                .stock(10)
                .imageUrl("https://example.com/ps5.jpg")
                .brand("Sony")
                .model("PlayStation 5")
                .weight(new BigDecimal("4.5"))
                .dimensions("39 x 26 x 10.4 cm")
                .active(true)
                .featured(true)
                .categories(java.util.Set.of(categories.get(0), categories.get(4)))
                .build(),
                
            Product.builder()
                .name("Amazon Echo Dot 5ta Gen")
                .description("Altavoz inteligente con Alexa para control de hogar inteligente")
                .price(new BigDecimal("199000")) // ~$50 USD en pesos colombianos
                .stock(100)
                .imageUrl("https://example.com/echodot.jpg")
                .brand("Amazon")
                .model("Echo Dot 5")
                .weight(new BigDecimal("0.304"))
                .dimensions("10 x 10 x 8.9 cm")
                .active(true)
                .featured(false)
                .categories(java.util.Set.of(categories.get(0), categories.get(5)))
                .build(),
                
            Product.builder()
                .name("Dell XPS 13 Plus")
                .description("Ultrabook premium con procesador Intel i7, 16GB RAM y pantalla 13.4\" 4K")
                .price(new BigDecimal("6299000")) // ~$1600 USD en pesos colombianos
                .stock(8)
                .imageUrl("https://example.com/dellxps13.jpg")
                .brand("Dell")
                .model("XPS 13 Plus")
                .weight(new BigDecimal("1.26"))
                .dimensions("29.6 x 19.9 x 1.55 cm")
                .active(true)
                .featured(true)
                .categories(java.util.Set.of(categories.get(0), categories.get(2)))
                .build(),
                
            Product.builder()
                .name("AirPods Pro 2da Gen")
                .description("Audífonos inalámbricos con cancelación activa de ruido y audio espacial")
                .price(new BigDecimal("999000")) // ~$250 USD en pesos colombianos
                .stock(40)
                .imageUrl("https://example.com/airpodspro.jpg")
                .brand("Apple")
                .model("AirPods Pro 2")
                .weight(new BigDecimal("0.061"))
                .dimensions("4.5 x 6.1 x 2.5 cm")
                .active(true)
                .featured(true)
                .categories(java.util.Set.of(categories.get(0), categories.get(3)))
                .build()
        );
    }
} 