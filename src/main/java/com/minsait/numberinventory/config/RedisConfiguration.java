package com.minsait.numberinventory.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.minsait.numberinventory.model.Numero;
import com.minsait.numberinventory.repository.NumeroRepository;
import com.minsait.numberinventory.service.Reciever;

@Configuration
public class RedisConfiguration {
	
	@Value("${spring.data.redis.host}")
	private String redisHost;
	
	@Bean
	public JedisConnectionFactory connectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(redisHost);
		configuration.setPort(6379);
		return new JedisConnectionFactory(configuration);
	}
	
    @Bean
    public RedisTemplate<String, Numero> template() {
        RedisTemplate<String, Numero> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Numero.class));
        return template;
    }
    
    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("pub-sub:channel");
    }
    
    
    @Bean
    public MessageListenerAdapter messageListenerAdapter(NumeroRepository numeroRepository) {
    	return new MessageListenerAdapter(new Reciever(numeroRepository));
    }
    
    @Bean
    public RedisMessageListenerContainer redisMessageContainer(NumeroRepository numeroRepository) {
    	RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    	container.setConnectionFactory(connectionFactory());
    	container.addMessageListener(messageListenerAdapter(numeroRepository), topic());
    	return container;
    }    
}