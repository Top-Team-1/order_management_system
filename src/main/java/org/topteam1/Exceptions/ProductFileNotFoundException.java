package org.topteam1.Exceptions;

public class ProductFileNotFoundException extends RuntimeException {
    public ProductFileNotFoundException(String message) {
        super(message);
    }
}
