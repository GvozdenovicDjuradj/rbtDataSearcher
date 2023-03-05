package com.rbt.searcher.error;

public class ResourceNotFoundException extends RbtException {

    public ResourceNotFoundException(String message) {
        this(404, message);
    }
    public ResourceNotFoundException(int code, String message) {
        super(code, message);
    }

}
