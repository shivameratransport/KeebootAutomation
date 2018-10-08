package com.keeboot.web.exceptions;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.WebException;

public class ElementNotHiddenException extends WebException {

    private static final long serialVersionUID = 1865273000586352087L;

    public ElementNotHiddenException(String message) {
        super(message);
    }

    public ElementNotHiddenException(String message, KeebootDriver driver) {
        super(message, driver);
    }
}
