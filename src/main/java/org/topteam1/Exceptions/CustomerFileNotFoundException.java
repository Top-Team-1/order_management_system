package org.topteam1.Exceptions;

public class CustomerFileNotFoundException extends RuntimeException {
    public CustomerFileNotFoundException(String message) {
        super(message);
    }
}
