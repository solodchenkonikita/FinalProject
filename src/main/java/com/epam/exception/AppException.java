package com.epam.exception;

/**
 * An exception that provides information on an application error.
 *
 * @author Solodchenko Nikita
 *
 */

public class AppException extends Exception {

    private static final long serialVersionUID = -3590409690181988060L;

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
