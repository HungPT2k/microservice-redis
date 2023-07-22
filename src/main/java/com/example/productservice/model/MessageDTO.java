package com.example.productservice.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDTO implements Serializable {
    private String nameServer;
    private String messDetail;
    private String method;
    @JacksonXmlProperty(localName = "parameters")
    @JacksonXmlElementWrapper(localName = "parameters")
    private HashMap<String,String> parameters;
    @JsonProperty( "object" )
    private Object object;

}
