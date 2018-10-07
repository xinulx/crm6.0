package com.mine.svc.common.util;

import com.mine.App.common.exception.BusiException;
import com.mine.App.common.util.DataHelper;
import com.mine.App.common.util.StringUtils;
import com.mine.svc.common.base.AuthInfo;
import com.mine.svc.common.base.MBean;
import com.mine.svc.common.base.ResponseVo;
import com.mine.svc.common.service.ISVCPageQry;
import net.sf.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangqi on 2013/6/27.
 */
@WebServlet(name = "SvcChannelRequest", urlPatterns = "/svcChannel.do")
public class SvcChannelRequest extends HttpServlet {

    public SvcChannelRequest() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.getSession().setAttribute("response", response);
        String jsonStr = request.getParameter("SVC_CONTENT");
        String svcType = request.getParameter("SVC_TYPE");
        MBean mbean = new MBean(jsonStr);
        System.out.println("服务入参：" + mbean.toString());
        try {
            ResponseVo responseVo = this.callService(mbean, request);
            if ("PAGE_QRY".equals(svcType)) {
                Map out = (Map) responseVo.getSvcInfo();
                MBean outBean = (MBean) out.get("SVC_OUT_CONTENT");
                JSONObject jsonObject = JSONObject.fromObject(outBean.getBodyObject("PAGE_QRY_INFO_LIST.PAGE_QRY_INFO"));
                response.getWriter().print(jsonObject);
            } else {
                response.getWriter().print(responseVo);
            }
            response.flushBuffer();
            response.getWriter().close();
        } catch (BusiException e) {
            System.out.println("服务调用失败:" + e.getErrCode() + "-" + e.getErrMsg());
        }
    }

    public ResponseVo callService(MBean mBean, HttpServletRequest request) {
        ResponseVo responseVo = new ResponseVo();
        if (mBean == null) {
            throw new BusiException("000000000", "服务请求参数mBean为空或不存在");
        }
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        String serviceName = StringUtils.objToString(mBean.getBodyStr("REQUEST_INFO.SVC_NAME"));
        MBean inservoffer = new MBean();
        inservoffer.setBody("SVC_NAME", serviceName);
        inservoffer.setBody("START_STATUS", "1");
        inservoffer.setBody("SEND_STATUS", "1");
        inservoffer.setBody("AUTH_FLAG", "1");
        ISVCPageQry sVCPageQry = (ISVCPageQry) webApplicationContext.getBean("sVCPageQry");
        Map<String, Object> svcObj = sVCPageQry.qryServOffer(inservoffer.getBody());

        Map<String, Object> logInfo = (Map<String, Object>) mBean.getBody().get("OPR_INFO");
        logInfo.put("LOGIN_ACCEPT", CrossEntity.getLoginAcceptNo());
        logInfo.put("LOG_SERIAL_NO", CrossEntity.getLogSerialNo());
        logInfo.put("LOGIN_NO", request.getSession().getAttribute("userId"));

        String beanName = "";
        String methodName = "";
        if (svcObj == null || StringUtils.isEmpty(svcObj.get("service_name").toString())) {
            throw new BusiException("000000001", "请求服务名为空或不存在");
        }
        serviceName = svcObj.get("service_name").toString();
        String serviceNameTemp = "";
        try {
            Map<String, Object> obj = new HashMap<>();
            obj.put("AUTH_INFO", request.getSession().getAttribute("AUTH_INFO"));
            responseVo.setAuthInfo((AuthInfo) request.getSession().getAttribute("AUTH_INFO"));
            DataHelper dh = new DataHelper(request);
            Map params = dh.getMapByEntry(request);
            params.put("LOGIN_NO", logInfo.get("LOGIN_NO"));
            String servicePath[] = serviceName.split("\\_");
            beanName = servicePath[servicePath.length - 2];
            methodName = servicePath[servicePath.length - 1];
            if (beanName.endsWith("Svc")) {// 服务 bean
                serviceNameTemp = serviceName.replace("Svc", "");
                serviceNameTemp = serviceNameTemp.replaceAll("\\_", "\\.");
                beanName = beanName.replace("Svc", "");
                beanName = beanName.substring(1, 2).toLowerCase() + "" + beanName.substring(2);
                Object bean = webApplicationContext.getBean(beanName);
                String clazzName = bean.getClass().getName().split("\\$")[0];
                System.out.println("调用服务：" + serviceNameTemp + "\n实际调用--" + clazzName);
                Class claszz = bean.getClass();
                mBean.setBody("QRY_PARAMS", params);
                Object o = claszz.getMethod(methodName, MBean.class).invoke(bean, mBean);
                responseVo.setSvcOutMsg(o);
                obj.put("OUT_DATA", o);
                MBean outMBean = (MBean) o;
                obj.put("SVC_OUT_CONTENT", outMBean);
                obj.put("REQ_FLAG", responseVo.getSvcStatus());
                responseVo.setResponseInfo(outMBean.toString());
                responseVo.setSvcInfo(obj);

                logInfo.put("IS_SUCCESS", "Y");
                logInfo.put("REMARK", "SERVLET");
                logInfo.put("SVC_TYPE", "A");
                logInfo.put("CRT_DATE", new Date(System.currentTimeMillis()));
                logInfo.put("PARAM_CONTENT", mBean.toString());
                sVCPageQry.insertSvcLog(logInfo);
            } else if (beanName.endsWith("Ws")) {// webservice bean
                System.err.println("you will execute a webservice !");
                logInfo.put("IS_SUCCESS", "Y");
                logInfo.put("REMARK", "WEBSERVICE");
                logInfo.put("SVC_TYPE", "WS");
                logInfo.put("CRT_DATE", new Date(System.currentTimeMillis()));
                logInfo.put("PARAM_CONTENT", mBean.toString());
                sVCPageQry.insertSvcLog(logInfo);
                return responseVo;
            } else {
                responseVo.setSvcStatus("0");
                responseVo.setSvcOutMsg("配置的服务名格式不正确，请检查");

                logInfo.put("IS_SUCCESS", "N");
                logInfo.put("REMARK", "配置的服务名格式错误");
                logInfo.put("SVC_TYPE", "ERR");
                logInfo.put("CRT_DATE", new Date(System.currentTimeMillis()));
                logInfo.put("PARAM_CONTENT", mBean.toString());
                sVCPageQry.insertSvcLog(logInfo);
                return responseVo;
            }
        } catch (Exception e) {
            logInfo.put("IS_SUCCESS", "N");
            logInfo.put("REMARK", "配置的服务名格式错误");
            logInfo.put("SVC_TYPE", "ERR");
            logInfo.put("CRT_DATE", new Date(System.currentTimeMillis()));
            logInfo.put("PARAM_CONTENT", mBean.toString());
            sVCPageQry.insertSvcLog(logInfo);

            // 打印堆栈错误信息，这样好排查原因
            responseVo.setSvcStatus("0");
            Map errMap = new HashMap();
            errMap.put("STACK_TRACE", e.getStackTrace());
            errMap.put("FILLIN_STACK_TRACE", e.fillInStackTrace());
            errMap.put("CAUSE", e.getCause());
            errMap.put("INIT_CAUSE", e.initCause(new Throwable()));
            errMap.put("MESSAGE", e.getMessage());
            responseVo.setSvcOutMsg("调用服务[" + serviceNameTemp + "出错");
            responseVo.setSvcInfo(errMap);
            logInfo.put("IS_SUCCESS", "N");
            logInfo.put("REMARK", "服务发生异常：" + responseVo.toString());
            logInfo.put("SVC_TYPE", "EXC");
            logInfo.put("CRT_DATE", new Date(System.currentTimeMillis()));
            logInfo.put("PARAM_CONTENT", mBean.toString());
            sVCPageQry.insertSvcLog(logInfo);

            throw new BusiException("000000002", "请求服务名格式不正确，请检查");
        }
        return responseVo;
    }
}
