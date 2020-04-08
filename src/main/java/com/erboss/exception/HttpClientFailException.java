package com.erboss.exception;

public class HttpClientFailException extends Exception {

    private static final long serialVersionUID = -1368052202200712460L;

    public HttpClientFailException() {
        super("httpclient execute fail exception");
    }

    public HttpClientFailException(String errMsg) {
        super(errMsg);
    }

    public HttpClientFailException(Throwable cause) {
        super(cause);
    }

    public HttpClientFailException(String errMsg, Throwable cause) {
        super(errMsg, cause);
    }
}
