package com.common.web.controller;

import com.common.web.enums.ExceptionEnum;
import com.common.web.exception.CustomWrapperException;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor
@Controller
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (Integer.parseInt(status.toString()) == HttpStatus.NOT_FOUND.value()) {
            throw new CustomWrapperException(ExceptionEnum.NOT_FOUND);
        }
        throw new CustomWrapperException(ExceptionEnum.INTERNAL_SERVER);
    }

}
