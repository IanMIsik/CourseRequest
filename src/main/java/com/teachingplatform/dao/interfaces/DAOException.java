package com.teachingplatform.dao.interfaces;

public class DAOException extends Exception {

    // Constructor that takes a simple error message
    public DAOException(String message) {
        super(message);
    }

    // Constructor that takes a message and the original exception
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that takes the original exception
    public DAOException(Throwable cause) {
        super(cause);
    }
}
