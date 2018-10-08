package com.keeboot.web.exceptions;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.WebException;

public class ElementNotEnabledException extends WebException {

    private static final long serialVersionUID = 6579447002670243452L;

    public ElementNotEnabledException(String message) {
        super(message);
    }

    public ElementNotEnabledException(String message, KeebootDriver driver) {
        super(message, driver);
    }
}
