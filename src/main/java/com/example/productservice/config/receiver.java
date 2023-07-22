package com.example.productservice.config;

import com.example.productservice.constant.CommonConstant;
import com.example.productservice.model.MessageDTO;
import com.example.productservice.model.Product;
import com.example.productservice.model.ResponseCommon;
import com.example.productservice.service.ProductService;
import com.example.redisserver.configRedis.MessageDTO1;
import com.example.redisserver.configRedis.Publisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

@ComponentScan("com.example.redisserver")
@Service
public class receiver implements MessageListener { // mỗi class subscriber một chanel
    @Autowired
    private ProductService productService;
    @Autowired
    private Publisher publisher;
    ObjectMapper mapper = new ObjectMapper();
    private static final Queue<MessageDTO> messageDTOQueue = new PriorityQueue<>();

        // Nhận message của chanel đã subscriber
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            MessageDTO messageDTO = mapper.readValue(message.getBody(), MessageDTO.class);
            System.out.println(" Receive mess by " + messageDTO.getNameServer() + " starting " + messageDTO.getMessDetail());
            messageDTOQueue.add(messageDTO); // add vào queue để message k bị mất

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ResponseCommon executeQuery() { // mỗi message ứng với một mehtod sẽ đi vào từng case, đầu vào là một MessageDTO
        MessageDTO m = messageDTOQueue.poll();
        if (!(m == null)) {
            System.out.println(m.toString());

            switch (Objects.requireNonNull(m).getMethod()) {
                case CommonConstant.Method.FINDBYID:
                    System.out.println("findByid .............ok");
                    return new ResponseCommon("200", "Get product by id 1", productService.findById(m));
                case CommonConstant.Method.UPDATEPRODUCT:
                    System.out.println("update .............ok");
                    return new ResponseCommon("200", "update  product", productService.createProduct(m));

                default:
                    System.out.println("Name method not find..........");
                    return new ResponseCommon("404", "not found method", null);
            }

        }
        System.out.println("queue empty...........");
        return new ResponseCommon("404", "empty queue", null);
    }
            // method sẽ gọi đến redis queue trước khi dc thực thi
    public ResponseCommon findById(Long id) throws InterruptedException {
        MessageDTO1 messageDTO = new MessageDTO1();
        messageDTO.setNameServer("Product-server");
        messageDTO.setMethod("findById");
        messageDTO.setMessDetail("get product by id");
        messageDTO.setHttp("GET");
        messageDTO.setObject(null);
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id", id.toString());
        messageDTO.setParameters(parameters);
        publisher.publishAtoB(messageDTO);
        //  publisher.publishBtoA(messageDTO);
        Thread.sleep(5); // đợi để message được xử lý
        return executeQuery();
    }

    public ResponseCommon updateProduct(Long id, Product product) throws InterruptedException {
        MessageDTO1 messageDTO = new MessageDTO1();
        messageDTO.setNameServer("Product-server");
        messageDTO.setMethod("updateProduct");
        messageDTO.setMessDetail("Update product by id");
        messageDTO.setHttp("UPDATE");
        messageDTO.setObject(product);
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id", id.toString());
        messageDTO.setParameters(parameters);
        publisher.publishAtoB(messageDTO);
        //  publisher.publishBtoA(messageDTO);
        Thread.sleep(5);
        return executeQuery();
    }
}
