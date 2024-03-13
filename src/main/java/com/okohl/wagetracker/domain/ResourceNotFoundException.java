package com.okohl.wagetracker.domain;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String string) {
        super(string);
    }
}
