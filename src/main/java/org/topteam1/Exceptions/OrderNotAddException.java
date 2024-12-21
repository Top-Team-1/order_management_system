package org.topteam1.Exceptions;

public class OrderNotAddException extends RuntimeException{
    public OrderNotAddException(String message) {
        super(message);
    }
}
