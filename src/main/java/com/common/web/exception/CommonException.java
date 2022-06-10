package com.common.web.exception;


import lombok.Getter;

@Getter
public abstract class CommonException extends RuntimeException {
    private static final long serialVersionUID = 2091972500749549823L;
    protected String code;
    protected String message;
    private Object data;
    private String detailMessage;

    public CommonException(String code) {
        this.code = code;
    }

    public CommonException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonException(String code, String message, String detailMessage) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public CommonException(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonException(String code, String message, Object data, String detailMessage) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.detailMessage = detailMessage;
    }
}

