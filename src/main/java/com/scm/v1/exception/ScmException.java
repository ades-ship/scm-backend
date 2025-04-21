package com.scm.v1.exception;

public class ScmException extends RuntimeException {
    
    public ScmException(String message) {
        super(message);
    }

    public ScmException(String message, Throwable cause) {
        super(message, cause);
    }
}
