package com.tool.redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

/**
 * @Description: 缓存管理器
 * @Author KerVinLi
 * @Version 1.0
 */
@Configuration
public class CacheManagerConfig {

    /**
     * 默认缓存管理器 缓存30分钟
     * @param factory
     * @return
     */
    @Primary
    @Scope("singleton")
    @Bean("defaultCacheManager")
    public CacheManager defaultCacheManager(RedisConnectionFactory factory){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30));
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * 缓存管理器 缓存10分钟
     * @param factory
     * @return
     */
    @Scope("singleton")
    @Bean("tenMinutesCacheMange")
    public CacheManager tenMinutesCacheMange(RedisConnectionFactory factory){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10));
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * 缓存管理器 缓存1天
     * @param factory
     * @return
     */
    @Scope("singleton")
    @Bean("oneDayCacheMange")
    public CacheManager oneDayCacheMange(RedisConnectionFactory factory){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1));
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
}
