package org.topteam1.Exceptions;

public class OrderFileNotFoundException extends RuntimeException {
    public OrderFileNotFoundException(String message) {
        super(message);
    }
}
