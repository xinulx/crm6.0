package com.mine.App.common.Base;

import com.mine.App.common.exception.BusiException;
import com.mine.App.common.util.DataHelper;
import com.mine.svc.common.base.JXml;
import com.mine.svc.common.base.MBean;
import com.mine.svc.common.mybatis.SqlClientTemplate;
import com.mine.svc.common.util.CrossEntity;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 重写handleRequestInternal 添加日志埋点
 */
public class BaseController extends MultiActionController {

    @Resource(name = "sqlClientTemplate")
    public SqlClientTemplate sqlClientTemplate;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        // 添加日志埋点
        Map<String, Object> logInfoMap = new HashMap<>();
        logInfoMap.put("LOG_ACCEPT", CrossEntity.getLogSerialNo());
        logInfoMap.put("REQ_METHOD", request.getMethod());
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, Object> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            String headerValue = "";
            while (headerValues.hasMoreElements()) {
                headerValue += headerValues.nextElement() + ",";
            }
            if (headerValue.length() > 0) {
                headerValue = headerValue.substring(0, headerValue.length() - 1);
            }
            headers.put(headerName, headerValue);
        }
        logInfoMap.put("REQ_HEADER", headers.toString());
        String url = request.getRequestURI() + "?method=" + request.getParameter("method");
        logInfoMap.put("REQ_URL", url);
        logInfoMap.put("LOGIN_NO", request.getSession().getAttribute("userId"));
        logInfoMap.put("SVC_TYPE", "ACTION");
        logInfoMap.put("IS_SUCCESS", "Y");
        try {
            DataHelper dh = new DataHelper(request, response);
            Map<String, Object> params = dh.getMapByEnu(request);
            MBean mBean = new MBean();
            mBean.setBody(params);
            String xml = JXml.toXml(mBean.toString());
            xml = xml.replaceAll("<", "&lt;");
            xml = xml.replaceAll(">", "&gt;");
            logInfoMap.put("PARAM_CONTENT", xml);
            logInfoMap.put("REMARK", "成功");
            sqlClientTemplate.insert("SVC_OPR_SQL.insertSysLog", logInfoMap);
            return super.handleRequestInternal(request, response);
        } catch (BusiException e) {
            logInfoMap.put("IS_SUCCESS", "N");
            logInfoMap.put("REMARK", "请求发生异常" + e.getErrCode() + e.getErrMsg() + e.getErrList());
            sqlClientTemplate.insert("SVC_OPR_SQL.insertSysLog", logInfoMap);
            throw new BusiException("000000", "服务请求异常：" + e.getErrMsg());
        }
    }
}
