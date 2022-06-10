package com.common.web.filter;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Order(-2147483638)
public class TransactionIdFilter implements Filter {
    public static final String HTTP_HEADER_TRANSACTION_ID = "X-TRANSACTION-ID";

    public TransactionIdFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String transactionId = (String) Optional.ofNullable(req.getHeader(HTTP_HEADER_TRANSACTION_ID)).orElse(UUID.randomUUID().toString());
        MDC.put(HTTP_HEADER_TRANSACTION_ID, transactionId);
        HttpServletResponse res = (HttpServletResponse)response;
        res.addHeader(HTTP_HEADER_TRANSACTION_ID, transactionId);
        chain.doFilter(req, res);
    }
}