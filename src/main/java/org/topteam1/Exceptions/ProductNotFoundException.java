package org.topteam1.Exceptions;

public class ProductNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Товар не найден (id - %d)";

    public ProductNotFoundException(int id) {
        super(MESSAGE.formatted(id));
    }
}
