package com.keeboot.utils.exception;

import com.keeboot.AutomationException;

public class NoSuchTaskException extends AutomationException {

    private static final long serialVersionUID = -1985117339792858897L;

    public NoSuchTaskException() {
        super();
    }

    public NoSuchTaskException(String message) {
        super(message);
    }

    public NoSuchTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
