package com.common.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    INTERNAL_SERVER("500", "Internal Server Error"),
    BAD_REQUEST("400", "Invalid parameters"),
    UNAUTHORIZED("401", "Unauthorized"),
    ACCESS_DENY("403", "Access Deny"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Requested method is not supported");

    private String code;
    private String message;
}
