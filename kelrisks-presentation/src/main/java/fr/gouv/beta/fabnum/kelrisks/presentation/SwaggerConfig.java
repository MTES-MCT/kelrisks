package fr.gouv.beta.fabnum.kelrisks.presentation;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket api(ServletContext servletContext) {
        
        return new Docket(DocumentationType.SWAGGER_2)
                       .pathProvider(new RelativePathProvider(servletContext) {
            
                           @Override
                           public String getApplicationBasePath() {
                
                               return "/";
                           }
                       })
                       .host("kelrisks.beta.gouv.fr")
                       .select()
                       .apis(RequestHandlerSelectors.basePackage("fr.gouv.beta.fabnum.kelrisks.presentation.rest"))
                       .paths(PathSelectors.any())
                       .build()
                       .apiInfo(apiInfo());
    }
    
    private ApiInfo apiInfo() {
        
        return new ApiInfo("Kelrisks API",
                           "Description de l'API.",
                           "v1.0",
                           "Terms of service",
                           new Contact("Kelrisks",
                                       "kelrisks.beta.gouv.fr",
                                       "thomas.bouchardon@developpement-durable.gouv.fr"),
                           "License of API",
                           "API license URL", Collections.emptyList());
    }
}