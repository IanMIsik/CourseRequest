package com.teachingplatform.service;

public class ServiceException extends Exception {

        // Constructor that takes a simple error message
    public ServiceException(String message) {
        super(message);
    }

    // Constructor that takes a message and the original exception
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that takes the original exception
    public ServiceException(Throwable cause) {
        super(cause);
    }
}

