package com.example.productservice.feignCacheApi;

import com.example.productservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@FeignClient (value = "feignCache",url = "http://localhost:8082/redis-cache")
public interface CacheApi {
    @PostMapping("/read")
    String readCache(Product p);
   @RequestMapping(method = RequestMethod.GET , value = "/get/")
    String getValueByKey(@RequestParam(name = "cacheName") String cacheName,@RequestParam(name = "key") String key);
}
