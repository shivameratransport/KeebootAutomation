package com.keeboot.web.exceptions;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.WebException;

public class ElementNotDisabledException extends WebException {
    private static final long serialVersionUID = 624614577584686540L;

    public ElementNotDisabledException(String message) {
        super(message);
    }

    public ElementNotDisabledException(String message, KeebootDriver driver) {
        super(message, driver);
    }
}
