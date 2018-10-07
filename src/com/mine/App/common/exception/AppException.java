package com.mine.App.common.exception;

public class AppException extends BaseException {
    private static final long serialVersionUID = 5487627886134869465L;

    public AppException() {
    }

    public AppException(String errorCode) {
        super(errorCode);
    }

    public AppException(Throwable e) {
        super(e);
    }

    public AppException(String errCode, String msg) {
        super(errCode, msg);
    }

    public AppException(String errCode, String msg, String detailMsg) {
        super(errCode, msg, detailMsg);
    }

    public AppException(String msg, Throwable e) {
        super(msg, e);
    }

    public AppException(String errCode, String msg, Throwable e) {
        super(errCode, msg, e);
    }
}
