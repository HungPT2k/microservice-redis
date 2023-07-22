package com.example.productservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@ComponentScan( "com.example.redisserver")
@Service
public class receiver1 implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("receive2.............");
    }
}
