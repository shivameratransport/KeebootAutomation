package com.keeboot.web.exceptions;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.WebException;

public class ElementAttributeValueNotMatchingException extends WebException {
    private static final long serialVersionUID = 3407361723082329697L;

    public ElementAttributeValueNotMatchingException(String message) {
        super(message);
    }

    public ElementAttributeValueNotMatchingException(String message, KeebootDriver driver) {
        super(message, driver);
    }
}
