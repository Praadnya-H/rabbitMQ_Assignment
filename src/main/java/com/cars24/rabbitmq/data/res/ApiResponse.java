package com.cars24.rabbitmq.data.res;

import lombok.Data;

@Data
public class ApiResponse {
    private int status;
    private boolean success;
    private String message;
    private Object data;
    private String service;
}
