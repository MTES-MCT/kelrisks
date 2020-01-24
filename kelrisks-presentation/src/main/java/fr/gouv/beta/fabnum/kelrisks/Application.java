package fr.gouv.beta.fabnum.kelrisks;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonDeserialiser;

import org.geolatte.geom.Geometry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bedatadriven.jackson.datatype.jts.JtsModule;

/**
 * Configuration of the business, persistence and security layers.
 */
@SpringBootApplication(scanBasePackages = {"fr.gouv.beta.fabnum"})
@EnableConfigurationProperties
@EnableJpaAuditing
public class Application extends SpringBootServletInitializer implements Jackson2ObjectMapperBuilderCustomizer {
    
    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        
        GeoJsonDeserialiser geoJsonDeserialiser = new GeoJsonDeserialiser();
        
        builder
                //        .failOnEmptyBeans(false) // prevent InvalidDefinitionException Error
                //        .modulesToInstall(new GeolatteGeomModule())
                .deserializerByType(Geometry.class, geoJsonDeserialiser);
    }
    
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
    public JtsModule jtsModule() { return new JtsModule(); }
}