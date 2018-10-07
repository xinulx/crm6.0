package com.mine.App.common.exception;

import java.io.Serializable;

public class DTO implements Serializable {
    private static final long serialVersionUID = -2147461113497269022L;
    protected String rtnCode = "000000";
    protected String rtnMsg = "成功";
    protected String rtnDetailMsg;

    public DTO() {
    }

    public DTO(String code) {
        this.rtnMsg = ErrorCode.mm(code);
        this.rtnCode = code;
    }

    public DTO(String code, String msg) {
        this.rtnCode = code;
        this.rtnMsg = msg;
    }

    public DTO(String code, String msg, String detailMsg) {
        this.rtnCode = code;
        this.rtnMsg = msg;
        this.rtnDetailMsg = detailMsg;
    }

    public String getRtnCode() {
        return this.rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return this.rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public String toString() {
        return "rtnCode=" + this.rtnCode + ";rtnMsg=" + this.rtnMsg + ";";
    }

    public String getRtnDetailMsg() {
        return this.rtnDetailMsg;
    }

    public void setRtnDetailMsg(String rtnDetailMsg) {
        this.rtnDetailMsg = rtnDetailMsg;
    }
}
