package br.com.carrental.config;

import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.guava.GuavaCache;

/**
 * Configuration class that enables caching
 *
 * @author Micael
 */

@Configuration
@EnableCaching
public class CacheConfig {
    public final static String CACHE_USERS = "users";
    public final static String CACHE_USER_ID = "usersById";

    @Bean
    public Cache cacheUsers() {
        return new GuavaCache(CACHE_USERS, CacheBuilder
                .newBuilder()
                .maximumSize(100)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build());
    }

    @Bean
    public Cache cacheUsersId() {
        return new GuavaCache(CACHE_USER_ID, CacheBuilder
                .newBuilder()
                .maximumSize(100)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build());
    }
}
