package com.mine.svc.common.service;

import com.mine.svc.common.base.MBean;

public interface ICommonLogger {

    /** 访问日志埋点 */
    public MBean insertAccessLog(MBean logInfo);
}
