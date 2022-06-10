package com.common.web.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RequestFilter implements Filter {
    private static final String FAVICON = "/favicon.ico";

    public RequestFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest)request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse)response);
        chain.doFilter(requestWrapper, responseWrapper);
        String contentType = ((HttpServletRequest)request).getHeader("Content-Type");
        if ((contentType == null || MimeTypeUtils.APPLICATION_JSON.toString().equalsIgnoreCase(contentType)) && !FAVICON.equals(requestWrapper.getRequestURI())) {
            log.info(String.format("%s\t%s", requestWrapper.getRequestURL().toString(), this.getRequestBody(requestWrapper).replaceAll("\n", "")));
            log.info(this.getResponseBody(responseWrapper).replaceAll("\n", ""));
        } else {
            this.getRequestBody(requestWrapper);
            this.getResponseBody(responseWrapper);
        }

    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                return new String(buf, 0, buf.length, StandardCharsets.UTF_8);
            }
        }

        return "";
    }

    private String getResponseBody(final HttpServletResponse response) throws IOException {
        String payload = null;
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, StandardCharsets.UTF_8);
                wrapper.copyBodyToResponse();
            }
        }

        return null == payload ? "" : payload;
    }
}
