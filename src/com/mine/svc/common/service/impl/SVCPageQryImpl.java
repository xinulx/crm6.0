package com.mine.svc.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mine.svc.common.base.MBean;
import com.mine.svc.common.service.ISVCPageQry;
import com.mine.svc.common.service.base.BaseService;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshibao on 2017/7/14.
 */
public class SVCPageQryImpl extends BaseService implements ISVCPageQry {

    private Logger logger = Logger.getLogger(SVCPageQryImpl.class);

    @Override
    public MBean svcPageQry(MBean mBean) {
        MBean outMBean = new MBean();
        Map result = new HashMap();
        Map params = (Map) mBean.getBodyObject("QRY_PARAMS");
        Integer count = sqlClientTemplate.selectOne("SVC_OPR_SQL.qrySvcCount",params);
        Integer pageIndex = Integer.valueOf(params.get("pageIndex").toString());
        Integer pageSize = Integer.valueOf(params.get("pageSize").toString());
        params.put("rownum",pageIndex == 0?0:pageIndex*pageSize);
        params.put("pageSize",pageSize);
        List<Map> objects = sqlClientTemplate.selectList("SVC_OPR_SQL.qrySvcInfoList", params);
        result.put("total",count);
        result.put("data",objects);
        outMBean.setBody("PAGE_QRY_INFO_LIST.PAGE_QRY_INFO",result);
        return outMBean;
    }

    @Override
    public MBean saveSvcRowInfo(MBean mBean) throws SQLException {
        MBean outMBean = new MBean();
        Map params = (Map) mBean.getBodyObject("QRY_PARAMS");
        String save_svc_row = params.get("SAVE_SVC_ROW").toString();
        JSONArray objects = JSON.parseArray(save_svc_row);
        List<Map> rows = (List) objects;
        logger.debug("新增服务列表---------------\n"+rows);
        SqlSessionFactory sqlSessionFactory = sqlClientTemplate.getSqlSessionFactory();
        // 批量执行器
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            if (rows != null) {
                for (int i = 0, size = rows.size(); i < size; i++) {
                    Map itemMap = rows.get(i);

                    String state = String.valueOf(itemMap.get("_state"));
                    if("removed".equals(state)){
                        sqlSession.delete("SVC_OPR_SQL.delSvcInfoByOfferId",itemMap);
                        continue;
                    }

                    // 是否设置鉴权
                    if(itemMap.get("auth_flag") == null || "".equals(itemMap.get("auth_flag"))){
                        itemMap.put("auth_flag","0");
                    }

                    Integer count = sqlSession.selectOne("SVC_OPR_SQL.qrySvcCountByOfferId", itemMap);
                    if(count > 0){
                        sqlSession.update("SVC_OPR_SQL.updateSvcInfo", itemMap);
                    }else{
                        sqlSession.insert("SVC_OPR_SQL.insertSvcInfo", itemMap);
                    }
                }
                sqlSession.flushStatements();
                sqlSession.commit();
                sqlSession.clearCache();
            }
        } finally {
            sqlSession.close();
        }
        return outMBean;
    }

    @Override
    public MBean exportServList(MBean mBean) throws IOException{
        MBean outMBean = new MBean();
        Map params = (Map) mBean.getBodyObject("QRY_PARAMS");
        logger.debug("导出excel入参====================" + params);

        HttpServletResponse response = getResponse();
        HttpServletRequest request = getRequest();
        request.setCharacterEncoding("utf-8");
        OutputStream out=response.getOutputStream();
        String fname = "服务清单";
        response.reset();//清空输出流
        response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
        fname = java.net.URLEncoder.encode(fname,"UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+new String(fname.getBytes("ISO-8859-1"),"UTF-8")+".xls");
        response.setContentType("application/ms-excel");//定义输出类型
        String json = params.get("columns").toString();
        List<Map> rows = (List)JSON.parseArray(json);
        params.put("rownum",0);
        params.put("pageSize",9999);
        List list = sqlClientTemplate.selectList("SVC_OPR_SQL.qrySvcInfoList",params);
        try {
            // 获得开始时间
            long start = System.currentTimeMillis();
            // 创建Excel工作薄
            WritableWorkbook workbook = Workbook.createWorkbook(out);
//            // 添加第一个工作表并设置第一个Sheet的名字
            WritableSheet sheet = workbook.createSheet("grid1", 0);
            Label label;
            //写出列名
            for(int i=0;i<rows.size();i++){
                Map hm = rows.get(i);
                Iterator iterator = hm.keySet().iterator();
                label = new Label(i,0,hm.get("header").toString());
                sheet.addCell(label);
            }
            // 写出数据
            for (int i = 1; i < list.size(); i++) {
                HashMap hm1 = (HashMap)list.get(i);
                for (int k = 1; k < hm1.size(); k++) {
                    for(int j=0;j<rows.size();j++){
                        Map hm = rows.get(j);
                        String key =hm.get("field").toString();
                        String value=String.valueOf(hm1.get(key));
                        label = new Label(j,i,value);
                        sheet.addCell(label);
                    }
                }
            }

             //写入数据
            workbook.write();
             // 关闭文件
            workbook.close();
            out.close();
             long end = System.currentTimeMillis();
            logger.debug("----完成该操作共用的时间是:"+(end-start)/1000);
        } catch (Exception e) {
            logger.debug("---出现异常---");
            e.printStackTrace();
        }
        return outMBean;
    }

    @Override
    public void insertSvcLog(Map<String, Object> logInfo) {
        if(logInfo != null){
            sqlClientTemplate.insert("SVC_OPR_SQL.saveSvcOprLogInfo",logInfo);
        }
    }
}
