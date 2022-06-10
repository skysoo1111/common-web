package com.common.web.advice;

import com.common.web.enums.ExceptionEnum;
import com.common.web.exception.CommonException;
import com.common.web.exception.CustomException;
import com.common.web.exception.CustomWrapperException;
import com.common.web.prop.ErrorProperty;
import com.common.web.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandleControllerAdvice {
    private final ErrorProperty errorProperty;

    @ExceptionHandler({CommonException.class})
    public ResponseEntity<CommonResponse> handleCommonException(CustomException e) {
        return this.sendError(e);
    }

    @ExceptionHandler(value = CustomWrapperException.class)
    public ResponseEntity<CommonResponse> handleCustomWrapperException(CustomWrapperException e) {
        return this.sendError(e.getExceptionEnum());
    }
    @ExceptionHandler(value = {
            MissingServletRequestParameterException.class,
            MissingRequestHeaderException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            BindException.class
    })
    public ResponseEntity<CommonResponse> handleBadRequestException(Exception e) {
        return this.sendError(ExceptionEnum.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
    })
    public ResponseEntity<CommonResponse> handleBadRequestException(MethodArgumentNotValidException e) {
        String code = "";
        try {
            List<ObjectError> errors = e.getBindingResult()
                    .getAllErrors();
            code = errors.stream().min((a, b) -> {
                String s1 = a.getDefaultMessage();
                String s2 = b.getDefaultMessage();
                return s1.compareTo(s2);
            })
                    .get()
                    .getDefaultMessage();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return this.sendError(Integer.valueOf(ExceptionEnum.BAD_REQUEST.getCode()), code, ExceptionEnum.BAD_REQUEST.getMessage());
    }

    @ExceptionHandler(value = {
            ConstraintViolationException.class,
    })
    public ResponseEntity<CommonResponse> handleConstraintViolationExceptionException(ConstraintViolationException e) {
        ConstraintViolation<?> constraintViolation = e.getConstraintViolations().stream().findFirst().get();
        if (constraintViolation == null) {
            return this.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "9999", HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
        return this.sendError(ExceptionEnum.BAD_REQUEST, constraintViolation.getMessage());
    }

    /**
     * 알수없는 오류 발생 시
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<CommonResponse> handleException(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        log.error(errors.toString());
        return this.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "9999", HttpStatus.INTERNAL_SERVER_ERROR.name());
    }

    /**
     * 코드만 전달될 때
     */
    private ResponseEntity<CommonResponse> sendError(int statusCode, String code, String message) {
        return ResponseEntity
                .status(statusCode)
                .body(
                        CommonResponse.builder()
                                .code(code)
                                .message(message)
                                .detailMessage(errorProperty.getErrors().get(code))
                                .build()
                );
    }

    /**
     * 코드만 전달될 때
     */
    private ResponseEntity<CommonResponse> sendError(ExceptionEnum exceptionEnum) {
        return ResponseEntity
                .status(Integer.parseInt(exceptionEnum.getCode()))
                .body(
                        CommonResponse.builder()
                                .code(exceptionEnum.getCode())
                                .message(exceptionEnum.getMessage())
                                .build()
                );
    }

    /**
     * 코드만 전달될 때
     */
    private ResponseEntity<CommonResponse> sendError(ExceptionEnum exceptionEnum, String detailMessageCode) {
        return ResponseEntity
                .status(Integer.parseInt(exceptionEnum.getCode()))
                .body(
                        CommonResponse.builder()
                                .code(exceptionEnum.getCode())
                                .message(exceptionEnum.getMessage())
                                .detailMessage(errorProperty.getErrors().get(detailMessageCode))
                                .build()
                );
    }

    private ResponseEntity<CommonResponse> sendError(CustomException e) {
        return ResponseEntity
                .status(200)
                .body(
                        CommonResponse.builder()
                                .code(e.getCode())
                                .message(e.getMessage())
                                .detailMessage(e.getDetailMessage() != null ? e.getDetailMessage() : errorProperty.getErrors().get(e.getCode()))
                                .data(e.getData())
                                .build()
                );
    }

}
