package com.common.web.exception;

import com.common.web.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomWrapperException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}

