package com.zjm.springBoot.spring_boot_mode.redis;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{

    @Configuration
    static class LocalConfiguration {
        //从application.properties中获得以下参数
        @Value("${redis.host}")
        private String host;
        @Value("${redis.port}")
        private Integer port;
        @Value("${redis.password}")
        private String password;

        /**
         * 缓存管理器.
         * @param redisTemplate
         * @return
         */
        @Bean
        public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate) {
           CacheManager cacheManager = new RedisCacheManager(redisTemplate);
           return cacheManager;
        }
        @Bean
        public RedisTemplate<Serializable, Serializable> redisTemplate(
                JedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate<Serializable, Serializable>();
            //key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
            //所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现 ObjectRedisSerializer
            //或者JdkSerializationRedisSerializer序列化方式;
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            redisTemplate
                    .setValueSerializer(new JdkSerializationRedisSerializer());
            redisTemplate
                    .setHashValueSerializer(new JdkSerializationRedisSerializer());
            //以上4条配置可以不用
            redisTemplate.setConnectionFactory(redisConnectionFactory);
            return redisTemplate;
        }

        @Bean
        public JedisConnectionFactory redisConnectionFactory() {
            JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
            redisConnectionFactory.setHostName(host);
            redisConnectionFactory.setPort(port);
           // redisConnectionFactory.setPassword(password);

            return redisConnectionFactory;
        }
    }

    /**
     * 自定义key.
     * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key
     */
    @Override
    public KeyGenerator keyGenerator() {
       return new KeyGenerator() {
           @Override
           public Object generate(Object o, Method method, Object... objects){           
              StringBuilder sb = new StringBuilder();
              sb.append(o.getClass().getName());
              sb.append(method.getName());
              for (Object obj : objects) {
                  sb.append(obj.toString());
              }
              return sb.toString();
           }
       };
    }
    /**
     * 实例化 HashOperations 对象,可以使用 Hash 类型操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<Serializable, Object, Object> hashOperations(RedisTemplate<Serializable, Serializable> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 实例化 ValueOperations 对象,可以使用 String 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<Serializable, Serializable> valueOperations(RedisTemplate<Serializable, Serializable> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 实例化 ListOperations 对象,可以使用 List 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<Serializable, Serializable> listOperations(RedisTemplate<Serializable, Serializable> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 实例化 SetOperations 对象,可以使用 Set 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<Serializable, Serializable> setOperations(RedisTemplate<Serializable, Serializable> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<Serializable, Serializable> zSetOperations(RedisTemplate<Serializable, Serializable> redisTemplate) {
        return redisTemplate.opsForZSet();
    }
}