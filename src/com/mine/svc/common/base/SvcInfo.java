package com.mine.svc.common.base;

import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * Created by wangshibao on 2017/6/27.
 */
public class SvcInfo implements Serializable {

    private Logger logger = Logger.getLogger(this.getClass());

    private static final long serialVersionUID = 1L;

    private final static String SVC_REQUEST_START = "------------START SVC REQUEST------------";

    public SvcInfo() {
        logger.debug(SVC_REQUEST_START);
    }

    private boolean processFlag = false; // 是否流程 默认否

    private boolean wsFlag = false; // 是否web服务 默认否

    private AuthInfo authInfo; // 认证信息

    private Object svcMsg ; // 入参消息

    private String svcStatus = "1"; // 请求状态 默认'1'-成功 '0'-失败

    private Object svcInfo ;// 包含请求的所有信息

    private String requestInfo; // 入参报文

    private String method = "POST"; // 请求方式 默认"POST"

    private String actionId; // 动作 指明操作模块动作ID

    private String functionId; // 功能ID

    private String opCode; // 操作码

    private String userId; // 操作人

    private BusiModel busiModel; // 业务模型参数

    private AcceptInfo acceptInfo; //流水信息

    private FootBillInfo footBillInfo; // 日志埋单

    private ChartSheet chartSheet; // 图形报表

    private ProcessInfo processInfo; // 流程信息

    public boolean isProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(boolean processFlag) {
        this.processFlag = processFlag;
    }

    public boolean isWsFlag() {
        return wsFlag;
    }

    public void setWsFlag(boolean wsFlag) {
        this.wsFlag = wsFlag;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

    public Object getSvcMsg() {
        return svcMsg;
    }

    public void setSvcMsg(Object svcMsg) {
        this.svcMsg = svcMsg;
    }

    public String getSvcStatus() {
        return svcStatus;
    }

    public void setSvcStatus(String svcStatus) {
        this.svcStatus = svcStatus;
    }

    public Object getSvcInfo() {
        return svcInfo;
    }

    public void setSvcInfo(Object svcInfo) {
        this.svcInfo = svcInfo;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BusiModel getBusiModel() {
        return busiModel;
    }

    public void setBusiModel(BusiModel busiModel) {
        this.busiModel = busiModel;
    }

    public AcceptInfo getAcceptInfo() {
        return acceptInfo;
    }

    public void setAcceptInfo(AcceptInfo acceptInfo) {
        this.acceptInfo = acceptInfo;
    }

    public FootBillInfo getFootBillInfo() {
        return footBillInfo;
    }

    public void setFootBillInfo(FootBillInfo footBillInfo) {
        this.footBillInfo = footBillInfo;
    }

    public ChartSheet getChartSheet() {
        return chartSheet;
    }

    public void setChartSheet(ChartSheet chartSheet) {
        this.chartSheet = chartSheet;
    }

    public ProcessInfo getProcessInfo() {
        return processInfo;
    }

    public void setProcessInfo(ProcessInfo processInfo) {
        this.processInfo = processInfo;
    }

    @Override
    public String toString() {
        return "SvcInfo{" +
                "processFlag=" + processFlag +
                ", wsFlag=" + wsFlag +
                ", authInfo=" + authInfo +
                ", svcMsg=" + svcMsg +
                ", svcStatus='" + svcStatus + '\'' +
                ", svcInfo=" + svcInfo +
                ", requestInfo='" + requestInfo + '\'' +
                ", method='" + method + '\'' +
                ", actionId='" + actionId + '\'' +
                ", functionId='" + functionId + '\'' +
                ", opCode='" + opCode + '\'' +
                ", userId='" + userId + '\'' +
                ", busiModel=" + busiModel +
                ", acceptInfo=" + acceptInfo +
                ", footBillInfo=" + footBillInfo +
                ", chartSheet=" + chartSheet +
                ", processInfo=" + processInfo +
                '}';
    }
}
