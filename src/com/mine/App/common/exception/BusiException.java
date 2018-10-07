package com.mine.App.common.exception;

import java.util.Map;

public class BusiException extends BaseException {
    private static final long serialVersionUID = -6623924982542131371L;

    public BusiException() {
    }

    public BusiException(String errorCode) {
        super(errorCode);
    }

    public BusiException(Throwable e) {
        super(e);
    }

    public BusiException(String errCode, String msg) {
        super(errCode, msg);
    }

    public BusiException(String errCode, String msg, String detailMsg) {
        super(errCode, msg, detailMsg);
    }

    public BusiException(String errCode, String msg, String detailMsg, String channel) {
        super(errCode, msg, detailMsg, channel);
    }

    public BusiException(String errCode, String msg, Map<String, String> map) {
        super(errCode, msg, map);
    }

    public BusiException(String errCode, String msg, String channel, Map<String, String> map) {
        super(errCode, msg, channel, map);
    }

    public BusiException(String msg, Throwable e) {
        super(msg, e);
    }

    public BusiException(String errCode, String msg, Throwable e) {
        super(errCode, msg, e);
    }

    public BusiException(String errCode, String msg, String channel, Throwable e) {
        super(errCode, msg, channel, e);
    }
}
