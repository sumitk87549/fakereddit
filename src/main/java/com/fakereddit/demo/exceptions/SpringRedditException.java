package com.fakereddit.demo.exceptions;

public class SpringRedditException extends RuntimeException{

    public SpringRedditException(String exMessage, Exception ex) {
        super(exMessage,ex);
    }

    public SpringRedditException(String exMessage) {
        super(exMessage);
    }
}
