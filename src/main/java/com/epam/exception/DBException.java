package com.epam.exception;

public class DBException extends AppException {

    private static final long serialVersionUID = 2290686372552848281L;

    public DBException() {
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
