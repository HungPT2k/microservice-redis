package com.example.authfirebase.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private String status;
    private String message;
    private Object data; // kiểu dữ liệu nói chung, chứa dc nhiều loại: data, user, message..

}
