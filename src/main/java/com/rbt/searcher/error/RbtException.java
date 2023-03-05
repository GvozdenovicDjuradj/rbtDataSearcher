package com.rbt.searcher.error;

import lombok.Getter;

@Getter
public class RbtException extends RuntimeException{
    private final int code;


    public RbtException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("Error %d: %s", code, getMessage());
    }
}
