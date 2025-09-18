package com.user.rest.ResponseCommon;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.user.common.ApiResponse;

@ControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

	 @Override
	    public boolean supports(MethodParameter returnType, @SuppressWarnings("rawtypes") Class converterType) {
	        return !returnType.getParameterType().equals(ResponseEntity.class)
	            && !returnType.getParameterType().equals(ApiResponse.class);
	    }

    @SuppressWarnings("rawtypes")
	@Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof ApiResponse) {
            return body;
        }

        return new ApiResponse<>(200, "Success", body);
    }
}