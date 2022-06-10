package com.common.web.advice;

import com.common.web.response.CommonResponse;
import lombok.NoArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@NoArgsConstructor
@RestControllerAdvice
public class ResponseControllerAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getDeclaringClass().getName().contains("springdoc");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (request.getURI()
                .getPath()
                .contains("actuator") || body instanceof CommonResponse) {
            return body;
        }
        return CommonResponse.builder()
                .code("0000")
                .message("Success")
                .data(body)
                .build();

    }
}
