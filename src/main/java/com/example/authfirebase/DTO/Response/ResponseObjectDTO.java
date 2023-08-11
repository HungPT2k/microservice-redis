package com.example.authfirebase.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObjectDTO {
    private String status;
    private String message;
    private Object data; // kiểu dữ liệu nói chung, chứa dc nhiều loại: data, user, message..

}
