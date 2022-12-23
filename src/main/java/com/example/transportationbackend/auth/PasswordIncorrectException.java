package com.example.transportationbackend.auth;

import javax.security.sasl.AuthenticationException;

public class PasswordIncorrectException extends AuthenticationException {
    public PasswordIncorrectException(String message){
        super(message);
    }
    public PasswordIncorrectException(){
        super("Password is incorrect");
    }
}
