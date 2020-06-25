package fr.gouv.beta.fabnum.kelrisks;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
public class CacheConfiguration {
    
    @Bean
    public CacheManager cacheManager() {
        
        return new ConcurrentMapCacheManager("avis");
    }
    
    @CacheEvict(allEntries = true, cacheNames = {"avis"})
    @Scheduled(fixedDelayString = "${application.cache.evict.delay}")
    public void cacheEvict() { }
    
    @Bean("customAvisKeyGenerator")
    public KeyGenerator keyGenerator() {
        
        return new CustomAvisKeyGenerator();
    }
    
    public static class CustomAvisKeyGenerator implements KeyGenerator {
        
        @Override
        public Object generate(Object target, Method method, Object... params) {
            
            return target.getClass().getSimpleName() + "."
                   + method.getName() + "_"
                   + ((List<ParcelleDTO>) params[0]).stream().map(parcelleDTO -> parcelleDTO.getId().toString()).collect(Collectors.joining(","));
        }
    }
}
