package com.library.exception;

public class AuthenticationException extends Exception {

    private static final String INCORRECT_PASSWORD = "Login or password is incorrect";

    public AuthenticationException() {
        super(INCORRECT_PASSWORD);
    }
}
