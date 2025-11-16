package edu.pe.upeu.delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 🌍 Permitir Angular local
        config.addAllowedOriginPattern("http://localhost:4200");
        // 🌍 Si luego despliegas, agrega la URL de tu dominio:
        // config.addAllowedOriginPattern("https://deliveryaltoque.pe");

        config.addAllowedHeader("*");   // llama a todos los encabezados
        config.addAllowedHeader("Authorization"); // Header para JWT
        config.addAllowedMethod("*");   // GET, POST, PUT, DELETE, etc.
        config.setAllowCredentials(true); // Permite cookies/sesiones

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica a todos los endpoints

        return new CorsFilter(source);
    }
}
