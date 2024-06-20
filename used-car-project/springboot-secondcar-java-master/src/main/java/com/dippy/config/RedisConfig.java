package com.dippy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/**
 * redis配置<br>
 * 集群下启动session共享，需打开@EnableRedisHttpSession<br>
 * 单机下不需要
 */
//@EnableRedisHttpSession
@Configuration
public class RedisConfig {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean("redisTemplate")
	public RedisTemplate redisTemplate(@Lazy RedisConnectionFactory connectionFactory) {
		RedisTemplate RedisTemplate = new RedisTemplate();
		GenericToStringSerializer<String> keySerializer = new GenericToStringSerializer<String>(String.class);
		RedisTemplate.setKeySerializer(keySerializer);
		RedisTemplate.setHashKeySerializer(keySerializer);

		/**
		 * 序列化方式1 -- 不推荐--目前项目中使用这种方式
		 * GenericJackson2JsonRedisSerializer
		 * 序列化时，会保存序列化的对象的包名和类名，反序列化时以这个作为标示就可以反序列化成指定的对象。
		 *  反序列化带泛型的数组类会报转换异常，解决办法存储以JSON字符串存储
		 */
		GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
		RedisTemplate.setValueSerializer(valueSerializer);
		RedisTemplate.setHashValueSerializer(valueSerializer);


		/**
		 * 序列化方式2 -- FastJson
		 *  GenericFastJsonRedisSerializer
		 */
		// GenericFastJsonRedisSerializer valueSerializer = new GenericFastJsonRedisSerializer ();
		// RedisTemplate.setValueSerializer(valueSerializer);
		// RedisTemplate.setHashValueSerializer(valueSerializer);

		/**
		 * 序列化方式3 -- 不推荐
		 * Jackson2JsonRedisSerializer
		 * 序列化带泛型的数据时，会以map的结构进行存储，反序列化是不能将map解析成对象 -- 解决方案：序列化存储时，转成JSON字符串
		 */
		// 报错使用如下序列化方式 Could not read JSON: Could not resolve type id '1' as a subtype of `java.lang.Object`
		// Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		// RedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		// RedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

		RedisTemplate.setConnectionFactory(connectionFactory);

		return RedisTemplate;
	}

}
