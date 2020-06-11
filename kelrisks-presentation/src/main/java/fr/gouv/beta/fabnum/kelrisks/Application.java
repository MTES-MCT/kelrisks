package fr.gouv.beta.fabnum.kelrisks;

import org.geolatte.geom.json.GeolatteGeomModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration of the business, persistence and security layers.
 */
@SpringBootApplication(scanBasePackages = {"fr.gouv.beta.fabnum"})
@EnableConfigurationProperties
@EnableJpaAuditing
public class Application extends SpringBootServletInitializer {
    
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        
        return application.sources(Application.class);
    }
    
    public static void main(String[] args) {
        
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        
        return new WebMvcConfigurer() {
            
            @Override
            public void addCorsMappings(CorsRegistry registry) {
    
                registry.addMapping("/**/api/**");
                registry.addMapping("/api/**");
                // TODO : enlever en prod !!!
                registry.addMapping("/mail");
            }
        };
    }
    
    @Bean
    public GeolatteGeomModule geolatteGeomModule() {
        
        return new GeolatteGeomModule();
    }
    
    @Bean
    public WebClient webClient() {
    
        return WebClient.create();
    }
}