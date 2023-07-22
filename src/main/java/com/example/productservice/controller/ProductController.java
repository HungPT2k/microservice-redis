package com.example.productservice.controller;



import com.example.productservice.config.receiver;
import com.example.productservice.feignCacheApi.CacheApi;
import com.example.productservice.model.Product;
import com.example.productservice.model.ResponseCommon;
import com.example.productservice.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.redisserver.configRedis.Publisher;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping( value = "/product")

public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private receiver receiver;
    @Autowired
    private CacheApi cacheApi;




    @GetMapping("/all")
    public List<Product> findAll(){
        return productRepository.findAll();
    }
//    @GetMapping("/get/")
//    public String getValueCache(@RequestParam(name = "cacheName") String cacheName, @RequestParam(name = "key") String key){
//        return cacheApi.getValueByKey(cacheName,key);
//    }
//    @GetMapping("/get/{id}")
//    public Optional<Product> findById(@PathVariable (name = "id") Long id){
//            cacheApi.readCache(productRepository.findById(id).orElse(null));
//        return productRepository.findById(id);
//    }
    @GetMapping("/get/{id}")
    public ResponseCommon findById(@PathVariable (name = "id") Long id) throws InterruptedException {
     return    receiver.findById(id);
    }
    @PostMapping("/update")
    public ResponseCommon updateProduct(@RequestParam(value = "id") Long id,@RequestBody Product product) throws InterruptedException {
        return receiver.updateProduct(id,product);
    }
}
