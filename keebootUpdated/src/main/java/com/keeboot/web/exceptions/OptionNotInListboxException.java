package com.keeboot.web.exceptions;

import com.keeboot.web.KeebootDriver;
import com.keeboot.web.WebException;

public class OptionNotInListboxException extends WebException {

    private static final long serialVersionUID = 4926417034544326093L;

    public OptionNotInListboxException(String message) {
        super(message);
    }

    public OptionNotInListboxException(String message, KeebootDriver driver) {
        super(message, driver);
    }
}
