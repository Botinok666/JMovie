package com.sfedu.JMovie.api.security;

import org.springframework.security.core.AuthenticationException;

public class SecurityContextException extends AuthenticationException {
    public SecurityContextException(String msg, Throwable t) {
        super(msg, t);
    }
    public SecurityContextException(String msg) {
        super(msg);
    }
}
