package com.mine.App.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mine.App.common.Base.BaseController;
import com.mine.App.common.util.DataHelper;
import com.mine.App.service.system.AccountService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class AccountController extends BaseController {

    private Logger logger=Logger.getLogger(this.getClass().getName());

    @Autowired
    private AccountService accountService;

    /**
     * 分页查询账户信息
     * @param req,res
     */
    public void qryAccountInfo(HttpServletRequest req,HttpServletResponse res) {
        logger.debug("==============查询账户信息=============");
        // 入参
        try {
            DataHelper dh = new DataHelper(req,res);
            Map params = dh.getMapByEntry(req);
            //分页
            int pageIndex = Integer.parseInt(req.getParameter("pageIndex"));
            int pageSize = Integer.parseInt(req.getParameter("pageSize"));
            params.put("rownum",pageIndex == 0?0:pageIndex*pageSize);
            params.put("pageSize",pageSize);

            Map rstMap = this.accountService.qryAccountInfo(params);
            res.getWriter().print(JSONObject.fromObject(rstMap));
            res.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 账户信息保存 删除/新增/修改
     * @param req
     * @param res
     */
    public void saveAccountInfo(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        String jsonStr = req.getParameter("data");
        JSONArray objects = JSON.parseArray(jsonStr);
        List<Map> rows = (List) objects;
        logger.debug("正在更新账户列表---------------\n"+rows);
        try {
            if (rows != null) {
                for (int i = 0, size = rows.size(); i < size; i++) {
                    Map itemMap = rows.get(i);

                    String state = String.valueOf(itemMap.get("_state"));
                    if("removed".equals(state)){
                        accountService.delete(itemMap);
                        continue;
                    }

                    Integer count = accountService.selectOne(itemMap);
                    if(count > 0){
                        accountService.update(itemMap);
                    }else{
                        accountService.insert(itemMap);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initUserInfoList(HttpServletRequest req,HttpServletResponse res){

        String userId = req.getParameter("userId");
        List<Map<String,Object>> userInfo = this.accountService.qryUserInfoList(userId);
        try {
            res.getWriter().print(net.sf.json.JSONArray.fromObject(userInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
