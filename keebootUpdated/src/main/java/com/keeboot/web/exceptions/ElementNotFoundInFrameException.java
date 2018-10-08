package com.keeboot.web.exceptions;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.WebException;

public class ElementNotFoundInFrameException extends WebException {
    private static final long serialVersionUID = 624614577584686540L;

    public ElementNotFoundInFrameException(String message) {
        super(message);
    }

    public ElementNotFoundInFrameException(String message, KeebootDriver driver) {
        super(message, driver);
    }
}
