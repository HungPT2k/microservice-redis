package com.example.productservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class redisconfig {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Bean
    RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.addMessageListener(messageListener(), topic());
        container.addMessageListener(messageListener1(), topic1());
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new receiver());
    }
    @Bean
    MessageListenerAdapter messageListener1() {
        return new MessageListenerAdapter(new receiver1());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("Product-server");
    }
    @Bean
    ChannelTopic topic1() {
        return new ChannelTopic("Customer-server");
    }

}
