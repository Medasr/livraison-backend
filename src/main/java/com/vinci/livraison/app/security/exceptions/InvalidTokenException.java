package com.vinci.livraison.app.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(String messsage) {
        super(messsage);
    }
}
