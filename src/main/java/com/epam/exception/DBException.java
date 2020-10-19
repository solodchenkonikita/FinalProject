package com.epam.exception;

/**
 * An exception that provides information on a database access error.
 *
 * @author Solodchenko Nikita
 *
 */
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
