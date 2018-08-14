package com.song.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

/**
 * Created by song on 2018/8/14.
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 定义缓存数据key生成策略的bean：
     * 包名+类名+方法名+所有参数
     */
    @Bean("wiselyKeyGenerator")
    public KeyGenerator wiselyKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getSimpleName()+":");
                sb.append(method.getName()+":");
                for(Object obj : params){
                    sb.append(obj.toString()+":");
                }
                return sb.deleteCharAt(sb.length()-1).toString();
            }
        };
    }

    /**
     * 要启用spring缓存支持，需要创建一个 CacheManager的bean，CacheManager接口有很多实现，这里是redis的集成，用 RedisCacheManager这个实现类
     * 我们需要一个连接工厂以及一个spring和redis对话要用的RedisTemplate，这些都是redis缓存所必须的配置，把他们都放在自定义的CachingConfigurerSupport中
     */
//    @Bean
//    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate){
//        RedisCacheManager cacheManager = RedisCacheManager.create(redisTemplate);
//        return cacheManager;
//    }
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
        RedisCacheManager cacheManager = RedisCacheManager.create(factory);

        return cacheManager;
    }

//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }

}
