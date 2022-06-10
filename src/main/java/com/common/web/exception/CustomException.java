package com.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.OK)
public class CustomException extends CommonException {

    public CustomException(String code) {
        super(code);
    }

    public CustomException(String code, String message) {
        super(code, message);
    }

    public CustomException(String code, String message, String detailMessage) {
        super(code, message, detailMessage);
    }

    public CustomException(String code, String message, Object data) {
        super(code, message, data);
    }

    public CustomException(String code, String message, Object data, String detailMessage) {
        super(code, message, data, detailMessage);
    }


}
