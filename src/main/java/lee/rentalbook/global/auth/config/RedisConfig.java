package lee.rentalbook.global.auth.config;

import org.redisson.client.RedisConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;


/**
 * @file RedisConfig.java
 * @author KangminLee
 * @date 2025-01-13
 * @lastModified 2025-01-13
 */
@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value(("${spring.data.redis.port}"))
    private int port;

    /**
     * redis 서버와의 연결을 관리하는 빈 생성
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        //RedisStandaloneConfiguration => Redis 단일 인스턴스와 연결 설정
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        //LettuceConnectionFactory => Redis와 연결을 담당하는 ConnectionFactory 구현체로 Redis에 연결할 때 사용
        return new LettuceConnectionFactory(configuration);
    }

    /**
     * Redis 를 캐시 저장소로 사용하는 CacheManager 설정
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        //기본 캐시 구성 설정
        RedisCacheConfiguration configuration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))//캐시 키는 StringRedisSerializer로 직렬화
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));//캐시 값(value)는 GenericJackson2JsonRedisSerializer()로 직렬화

        //Redis 연결을 위한 RedisConnectionFactory 기본 캐시 구성을 사용해서 CacheManager 빈 생성
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .build();
    }

    /**
     * Redis 메세지 구독 및 처리 기능 빈 생성
     * Redis로부터 메시지 수신하고 처리 가능
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        //Redos구독 및 처리 기능
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    /**
     * Redis와 연결 설정하고 Redis를 캐시로 사용 및 메시지 시스템을 활용 가능하게 설정
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }


}
