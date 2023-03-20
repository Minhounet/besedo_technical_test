package com.qmt.besedo.exception;

public class InvalidOperatorException extends RuntimeException {
    public InvalidOperatorException(String operator) {
        super("Invalid operator name: " + operator);
    }
}
