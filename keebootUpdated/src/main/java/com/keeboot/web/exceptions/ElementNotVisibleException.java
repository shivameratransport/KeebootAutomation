package com.keeboot.web.exceptions;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.WebException;

public class ElementNotVisibleException extends WebException {
    private static final long serialVersionUID = 7724792038612608062L;

    public ElementNotVisibleException(String message) {
        super(message);
    }

    public ElementNotVisibleException(String message, KeebootDriver driver) {
        super(message, driver);
    }
}
