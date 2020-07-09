package fr.gouv.beta.fabnum.kelrisks;

import fr.gouv.beta.fabnum.commun.metier.util.SecurityHelper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import org.geolatte.geom.json.GeolatteGeomModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
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
                //                registry.addMapping("/mail");
            }
        };
    }
    
    @Bean
    public GeolatteGeomModule geolatteGeomModule() {
        
        return new GeolatteGeomModule();
    }
    
    @Bean
    public WebClient webClient(final ClientHttpConnector clientHttpConnector, final ExchangeStrategies exchangeStrategies) {
        
        return WebClient.builder()
                       .clientConnector(clientHttpConnector)
                       .exchangeStrategies(exchangeStrategies)
                       .build();
    }
    
    @Bean
    public ExchangeStrategies exchangeStrategies(@Value("${webclient.maxInMemorySizeInMB}") final int mb) {
        
        return ExchangeStrategies.builder()
                       .codecs(configurer -> configurer
                                                     .defaultCodecs()
                                                     .maxInMemorySize(mb * 1024 * 1024))
                       .build();
    }
    
    @Bean
    public ClientHttpConnector clientHttpConnector(@Value("${webclient.enable-keep-alive}") final boolean keepAlive,
                                                   @Value("${webclient.read-timeout-in-seconds}") final int readTimeout,
                                                   @Value("${webclient.write-timeout-in-seconds}") final int writeTimeout) {
    
        return new ReactorClientHttpConnector(HttpClient.from(TcpClient.create()
                                                                      .option(ChannelOption.SO_KEEPALIVE, keepAlive)
                                                                      .doOnConnected(connection -> connection
                                                                                                           .addHandlerLast(new ReadTimeoutHandler(readTimeout))
                                                                                                           .addHandlerLast(new WriteTimeoutHandler(writeTimeout)))));
    }
    
    @Bean(name = "securityHelperEncoder")
    public SecurityHelper securityHelperEncoder(@Value("${kelrisks.app.security.passphrase}") final String passphrase) {
        
        return new SecurityHelper(passphrase);
    }
}