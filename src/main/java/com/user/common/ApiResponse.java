package com.user.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {

	// code 可為Message code for前端快速format用
    private int code;
    private String message;
    private T data;
    
    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public ApiResponse(HttpStatus httpStatus, String message, T data) {
        this.code = httpStatus.value();
        this.message = message;
        this.data = data;
    }
}
