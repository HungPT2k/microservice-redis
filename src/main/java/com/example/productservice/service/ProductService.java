package com.example.productservice.service;


import com.example.productservice.model.MessageDTO;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import com.example.redisserver.configRedis.MessageDTO1;
import com.example.redisserver.configRedis.Publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.sql.Timestamp;
import java.util.Optional;


@Service
public class ProductService  {

    @Autowired
    private ProductRepository productRepository;
    public Optional<Product> findById(MessageDTO m){
      Long id= Long.valueOf(m.getParameters().get("id"));
      return   productRepository.findById(id);
    }
    public Product createProduct(MessageDTO m){
        Product product = (Product) m.getObject();
        Product product1 =new Product();
        product1.setId(product.getId());
        product1.setName(product.getName());
        return productRepository.save(product1);

    }
    public Product updateProduct(MessageDTO m){
        Long id= Long.valueOf(m.getParameters().get("id"));
        Product product = (Product) m.getObject();
        Optional<Product> productOptional =productRepository.findById(id);
        if(productOptional.isPresent()){
            productOptional.get().setName(product.getName());
            productOptional.get().setId(product.getId());
            return productRepository.save(productOptional.get());
        }
        return null;
    }
}
