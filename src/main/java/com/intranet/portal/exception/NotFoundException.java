package com.intranet.portal.exception;

import org.springframework.http.HttpStatus;
import tools.jackson.core.ObjectReadContext;

public class NotFoundException extends BaseException {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}