package io.github.jhipster.application.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Dog.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Dog.class.getName() + ".centerNames", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Dog.class.getName() + ".breedingNames", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Dog.class.getName() + ".famillyNames", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Dog.class.getName() + ".weights", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Dog.class.getName() + ".healthSheets", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Dog.class.getName() + ".dateBilans", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Race.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Race.class.getName() + ".names", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Color.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Color.class.getName() + ".names", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Weight.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Center.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Center.class.getName() + ".locations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Breeding.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Breeding.class.getName() + ".locations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Familly.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Familly.class.getName() + ".locations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Location.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Location.class.getName() + ".centers", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Location.class.getName() + ".breedings", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Location.class.getName() + ".famillies", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.HealthSheet.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Categorie.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Categorie.class.getName() + ".names", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Categorie.class.getName() + ".criteria", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Criteria.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Criteria.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Criteria.class.getName() + ".bilanChecks", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BilanType.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BilanType.class.getName() + ".bilanChecks", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BilanCheck.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BilanCheck.class.getName() + ".bilanTypes", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BilanCheck.class.getName() + ".criteria", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
