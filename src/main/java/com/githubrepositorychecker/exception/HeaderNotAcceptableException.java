package com.githubrepositorychecker.exception;

public class HeaderNotAcceptableException extends Exception {

    public HeaderNotAcceptableException() {
        super("Wrong header type, should be JSON, was XML");
    }
}
