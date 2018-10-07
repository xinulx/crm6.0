package com.mine.App.controller.cust;

import com.mine.App.common.Base.BaseController;
import com.mine.svc.common.util.CrossEntity;
import com.mine.svc.common.util.PropertiesConfig;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustAction extends BaseController {

    private Logger logger=Logger.getLogger(this.getClass());

    public void pageQryCustInfo(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        logger.info("pageQryCustInfo--> 客户信息查询...");
        Map<String,Object> inParam = new HashMap<>();
        Map<String,Object> requestInfo = new HashMap<>();
        inParam.put("SVC_CODE","sCustInfoPageQry");

        String custIdId = req.getParameter("custId");
        String custIdName = req.getParameter("custIdName");
        //分页
        int pageIndex = Integer.parseInt(req.getParameter("pageIndex"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));

        requestInfo.put("CUST_ID",custIdId);
        requestInfo.put("CUST_NAME",custIdName);
        requestInfo.put("START_NUM",pageIndex * pageSize);
        requestInfo.put("END_NUM",pageSize * (pageIndex + 1));
        inParam.put("REQUEST_INFO",requestInfo);
        String serviceName = PropertiesConfig.getConfigUrl("HTTP_REQUEST_CFG");
        Map<String, Object> outMap = CrossEntity.callHttpService(serviceName, inParam);
        HashMap result = new HashMap();
        result.put("data", outMap.get("CUST_INFO"));

        // layer-table模拟时添加的,上面data在miniui和layui中名字一致
        result.put("count", outMap.get("CUST_INFO_COUNT"));
        result.put("code", 0);

        // miniui需要添加的
        result.put("total", outMap.get("CUST_INFO_COUNT"));
        res.getWriter().print(JSONObject.fromObject(result));
        res.getWriter().close();
    }
}
