package com.mine.App.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseException extends RuntimeException implements JCFThrowable {
    private static final long serialVersionUID = 6660311097777385849L;
    protected StringBuffer backStacks = new StringBuffer();
    protected String errCode = "999999";
    protected String channel = "";
    protected String errMsg = "";
    protected String errDtlMsg = "";
    protected Map paraMap = new HashMap();
    protected List errList = null;

    public BaseException() {
    }

    public BaseException(String errCode) {
        super(errCode);
        String em = ErrorCode.mm(errCode);
        if(em == null) {
            this.errMsg = errCode;
        } else {
            this.errCode = errCode;
            this.errMsg = em;
        }

    }

    public BaseException(Throwable e) {
        super(e);
        this.backStacks.append(getStackTrace(e));
    }

    public BaseException(String errCode, String msg) {
        super("errCode:" + errCode + ",errMsg:" + msg);
        this.errCode = errCode;
        this.errMsg = msg;
    }

    public BaseException(String errCode, String msg, String errDtlMsg) {
        super("errCode:" + errCode + ",errMsg:" + msg + ",errDtlMsg:" + errDtlMsg);
        this.errCode = errCode;
        this.errMsg = msg;
        this.errDtlMsg = errDtlMsg;
    }

    public BaseException(String errCode, String msg, String errDtlMsg, String channel) {
        super("errCode:" + errCode + ",errMsg:" + msg + ",errDtlMsg:" + errDtlMsg + ",channel:" + channel);
        this.errCode = errCode;
        this.errMsg = msg;
        this.errDtlMsg = errDtlMsg;
        this.channel = channel;
    }

    public BaseException(String errCode, String msg, Map<String, String> map) {
        super("errCode:" + errCode + ",errMsg:" + msg);
        this.errCode = errCode;
        this.errMsg = msg;
        this.paraMap = map;
    }

    public BaseException(String errCode, String msg, String channel, Map<String, String> map) {
        super("errCode:" + errCode + ",errMsg:" + msg + ",channel:" + channel);
        this.errCode = errCode;
        this.errMsg = msg;
        this.paraMap = map;
        this.channel = channel;
    }

    public BaseException(String errCode, Throwable e) {
        super(errCode, e);
        String errMsg = ErrorCode.mm(errCode);
        if(errMsg != null) {
            ;
        }

        this.backStacks.append(getStackTrace(e));
    }

    public BaseException(String errCode, String msg, Throwable e) {
        super("errCode:" + errCode + ",errMsg:" + msg, e);
        this.errCode = errCode;
        this.errMsg = msg;
        this.backStacks.append(getStackTrace(e));
    }

    public BaseException(String errCode, String msg, String channel, Throwable e) {
        super("errCode:" + errCode + ",errMsg:" + ",channel:" + channel, e);
        this.errCode = errCode;
        this.errMsg = msg;
        this.channel = channel;
        this.backStacks.append(getStackTrace(e));
    }

    public final String getBackStacks() {
        return this.backStacks.toString();
    }

    public static final String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        e.printStackTrace(out);
        return sw.toString();
    }

    public static final String getStackTrace(BaseException e) {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        if(e.backStacks != null) {
            out.println(e.backStacks);
        }

        e.printStackTrace(out);
        return sw.toString();
    }

    public void printStackTrace(PrintStream out) {
        if(this.backStacks != null) {
            out.println(this.backStacks);
        }

        super.printStackTrace(out);
    }

    public void printStackTrace(PrintWriter out) {
        if(this.backStacks != null) {
            out.println(this.backStacks);
        }

        super.printStackTrace(out);
    }

    public void printStackTrace() {
        this.printStackTrace(System.err);
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public String getErrDtlMsg() {
        return this.errDtlMsg;
    }

    public Map getParaMap() {
        return this.paraMap;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel_id(String channel) {
        this.channel = channel;
    }

    public void addPara(String key, String value) {
        this.paraMap.put(key, value);
    }

    public void setErrDtlMsg(String errDtlMsg) {
        this.errDtlMsg = errDtlMsg;
    }

    public void setErrList(List list) {
        this.errList = list;
    }

    public List getErrList() {
        return this.errList;
    }
}
