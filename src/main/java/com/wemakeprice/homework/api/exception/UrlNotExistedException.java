package com.wemakeprice.homework.api.exception;

public class UrlNotExistedException extends RuntimeException{

    public UrlNotExistedException(String message) {
        super(message);
    }
}
