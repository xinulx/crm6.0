package com.mine.svc.common.service;

import com.mine.svc.common.base.MBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by wangshibao on 2017/7/14.
 */
public interface ISVCPageQry {
    public Map qryServOffer(Map params);
    public MBean svcPageQry(MBean mBean);
    public MBean saveSvcRowInfo(MBean mBean) throws SQLException;
    public MBean exportServList(MBean mBean) throws IOException;
    public void insertSvcLog(Map<String, Object> logInfo);
}
