package org.example.shoppingmall.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestBean {
    private final HttpServletRequest request;

    public RequestBean(HttpServletRequest request) {
        this.request = request;
    }

    public String getRequestURI() {
        return request.getRequestURI();
    }
}