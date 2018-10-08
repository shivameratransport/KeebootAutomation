package com.keeboot.utils.exception;

import com.keeboot.AutomationException;

public class DataProviderInputFileNotFound extends AutomationException {
    private static final long serialVersionUID = 8734638785785664287L;

    public DataProviderInputFileNotFound(String message) {
        super(message);
    }

    public DataProviderInputFileNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
