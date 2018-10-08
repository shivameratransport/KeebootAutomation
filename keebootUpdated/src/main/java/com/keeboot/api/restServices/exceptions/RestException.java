package com.keeboot.api.restServices.exceptions;

import com.keeboot.api.WebServiceException;

public class RestException extends WebServiceException {
    private static final long serialVersionUID = -8710980695994382082L;

    public RestException() {
        super("REST Error:");
    }

    public RestException(String message) {
        super("REST Error: " + message);
    }

    public RestException(String message, Throwable cause) {
        super("REST Error: " + message, cause);
    }

}
