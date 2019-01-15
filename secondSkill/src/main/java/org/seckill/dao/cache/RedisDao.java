package org.seckill.dao.cache;

import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

//import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/8/10.
 */
@Configuration
@EnableCaching
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(RedisDao.class);
    private Jedis jedis;
//    private JedisPool jedisPool;
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
    private String host = "127.0.0.1";
    private int port = 6379;

    public RedisDao(){
    	jedis = new Jedis(this.host,this.port);
//    	jedisPool = new JedisPool(this.host,this.port);
        logger.info(String.format("host = %s; port = %d", this.host,this.port));
    }

    public Seckill getSeckill(long seckillId){
        try{
//            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:"+seckillId;
                //采用自定义序列化
                byte[] bytes = jedis.get(key.getBytes());
                //缓存中获取到
                if (bytes != null){
                    Seckill seckill =schema.newMessage();
                    //性能更好的序列化
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    return seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception ex){

        }
        return null;
    }

    public String  putSeckill(Seckill seckill){
        try{
//            Jedis  jedis = jedisPool.getResource();
            try {
                String key = "seckill:"+seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                jedis.setex(key.getBytes(),60*60,bytes);
            }finally {
                jedis.close();
            }
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
        }
        return null;
    }

//  /**
//  * 键的生成策略
//  * @return
//  */
// @Bean
// public KeyGenerator wiselyKeyGenerator() {
//     return new KeyGenerator() {
//         @Override
//         public Object generate(Object target, Method method, Object... params) {
//             StringBuilder sb = new StringBuilder();
//             sb.append(target.getClass().getName());
//             sb.append(method.getName());
//             for (Object obj : params) {
//                 sb.append(obj.toString());
//             }
//             return sb.toString();
//         }
//     };
// }

// @Bean
// public JedisConnectionFactory redisConnectionFactory() {
//     JedisConnectionFactory factory = new JedisConnectionFactory();
//     factory.setHostName(host);
//     factory.setPort(port);
//     factory.setTimeout(timeout);
//     factory.setPassword(password);
//     factory.setDatabase(database);
//     return factory;
// }

// /**
//  * 配置CacheManager 管理cache
//  * @param redisTemplate
//  * @return
//  */
// @Bean
// public CacheManager cacheManager(RedisTemplate redisTemplate) {
//     RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//     cacheManager.setDefaultExpiration(60*60); // 设置key-value超时时间
//     return cacheManager;
// }



//	@Bean
//	public RedisTemplate<Object, Object> redisTemplate(
//			RedisConnectionFactory connectionFactory) {
//		RedisTemplate<Object, Object> template = new RedisTemplate<>();
//		template.setConnectionFactory(connectionFactory);
//		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
//		Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(
//				Object.class);
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.setVisibility(PropertyAccessor.ALL,
//				JsonAutoDetect.Visibility.ANY);
//		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		serializer.setObjectMapper(mapper);
//		template.setValueSerializer(serializer);
//		// 使用StringRedisSerializer来序列化和反序列化redis的key值
//		template.setKeySerializer(new StringRedisSerializer());
//		template.afterPropertiesSet();
//		return template;
//	}

}
