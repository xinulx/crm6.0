package com.mine.svc.common.util;

import com.alibaba.fastjson.JSON;
import com.mine.App.common.exception.BusiException;
import com.mine.svc.HttpClientUtil;
import com.mine.svc.HttpRequestUtil;
import com.mine.svc.common.base.BusiEntity;
import com.mine.svc.common.base.DataBus;
import com.mine.svc.common.base.InOutFormat;
import com.mine.svc.common.base.MBean;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrossEntity extends BusiEntity {
    private static final Logger log = Logger.getLogger(CrossEntity.class);
    private static final String EntityId = "0002";

    /**
     * @param interfaceName
     * @param mBean
     * @return 返回出参BODY.OUT_DATA节点信息
     * @Description: 调用服务，处理异常及判断返回结果
     */
    public static Map callService(String interfaceName, MBean mBean) {
        return callService(interfaceName, mBean, HashMap.class);
    }

    /**
     * @param interfaceName
     * @param mBean
     * @param clazz
     * @return 返回出参BODY.OUT_DATA节点信息
     * @Description: 调用服务，处理异常及判断返回结果
     */
    public static <T> T callService(String interfaceName, MBean mBean, Class<T> clazz) {
        // HEADER透传
        if (DataBus.getMBean() != null) {
            MBean busBean = DataBus.getMBean();
            mBean.setHeader(busBean.getHeader());
        }

        log.info("服务：" + interfaceName);
        log.info("入参：" + mBean.toString());

        String outStr = null;


        MBean outBean = new MBean(outStr);
        String retCode = outBean.getBodyStr("RETURN_CODE");

        if ((!"0".equals(retCode)) && (!"000000".equals(retCode))) {
            System.err.println("RETURN_CODE:" + outBean.getBodyStr("RETURN_CODE"));
            System.err.println("DETAIL_MSG:" + outBean.getBodyStr("DETAIL_MSG"));
            DataBus.setErrorMBean(retCode, outBean);
            throw new BusiException(outBean.getBodyStr("RETURN_CODE"), outBean.getBodyStr("DETAIL_MSG"));
        }

        return (T) outBean.getBodyObject("OUT_DATA", clazz);
    }

    public static String getLoginAcceptNo() {
        String loginAccept = "";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmss");
        return "L" + sdf.format(new Date());
    }

    public static long getLogSerialNo() {
        String loginAccept = "";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
        Date date = new Date();
        String str = sdf.format(date) + String.valueOf(date.getTime()).substring(3);
        return Long.valueOf(str);
    }

    public static void main(String[] args) {
        getLoginAcceptNo();
        getLogSerialNo();
        System.err.println(new java.sql.Date(System.currentTimeMillis()));
    }

    public static MBean xml2MBean(String xml) {
//        String xml = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?><ROOT><MEANS><MEANS_NAME type=\"string\">积分 产品 分月返还</MEANS_NAME><CONTRACTCODE type=\"string\"></CONTRACTCODE><SMS_MSG type=\"string\">尊敬的客户，您办理的[act_name]营销活动已成功，电子券为[gift_name1]，电子券密码为[password1],请您在[exp_date1]前至&lt;请输入商家名称&gt;商场所属的&lt;请输入网点名称&gt;网点兑换商品，电子券不可退还，逾期兑换无效，电子券密码遗失可发送“DZQBF”至10086补发-安徽移动。</SMS_MSG><SEND_FLAG type=\"string\">Y</SEND_FLAG><IS_ACTLIB type=\"string\">0</IS_ACTLIB><MSG type=\"string\">尊敬的客户，您办理的[act_name]营销活动已成功，电子券为[gift_name1]，电子券密码为[password1],请您在[exp_date1]前至&lt;请输入商家名称&gt;商场所属的&lt;请输入网点名称&gt;网点兑换商品，电子券不可退还，逾期兑换无效，电子券密码遗失可发送“DZQBF”至10086补发-安徽移动。</MSG><A07><FEE_CODE type=\"string\">184</FEE_CODE><FEE_NAME type=\"string\">开户预存款</FEE_NAME><TAX_RATE type=\"string\">11</TAX_RATE><PAY_MONEY type=\"string\">12</PAY_MONEY><FEE_TYPE type=\"string\">2</FEE_TYPE></A07><MEANS_ID type=\"string\">201405131027183999</MEANS_ID><A11><RESOURCE_INFO><INDEX type=\"string\">1</INDEX><FEE_CODE type=\"string\">185</FEE_CODE><RESOURCE_FEE type=\"string\">1222</RESOURCE_FEE><RESOURCE_REAL_PRICE type=\"string\">1222</RESOURCE_REAL_PRICE><RESOURCE_NAME type=\"string\">I9108</RESOURCE_NAME><RESOURCE_MODEL type=\"string\">C0125563</RESOURCE_MODEL><IS_ACTLIB type=\"string\">0</IS_ACTLIB><RESOURCE_FAV_RATE type=\"string\">0</RESOURCE_FAV_RATE><RESOURCE_DESC type=\"string\">222</RESOURCE_DESC><RESOURCE_COST_PRICE type=\"string\">0</RESOURCE_COST_PRICE><FEE_TYPE type=\"string\">1</FEE_TYPE><RESOURCE_CODE type=\"string\">C0125563</RESOURCE_CODE><MATERIALCODE type=\"string\"></MATERIALCODE><FEE_NAME type=\"string\">手机终端费</FEE_NAME><RESOURCE_USE type=\"string\">0</RESOURCE_USE><TAX_RATE type=\"string\">17</TAX_RATE><BINDINGSVCNUM type=\"string\">15799881418</BINDINGSVCNUM><RESOURCE_NO type=\"string\">357806040613679</RESOURCE_NO></RESOURCE_INFO><IS_COUNTRY type=\"string\">N</IS_COUNTRY></A11><RESOURCE_USE type=\"string\">0</RESOURCE_USE><A10><TAX_RATE type=\"string\">11</TAX_RATE><MONTH_RETURN><FEE_CODE type=\"string\">180</FEE_CODE><INDEX type=\"string\">1</INDEX><GIVE_FEE_CODE type=\"string\">183</GIVE_FEE_CODE><STAGES_CYCLE type=\"string\">99</STAGES_CYCLE><FAV_RATE type=\"string\">0</FAV_RATE><OFFSET_MONTH type=\"string\"></OFFSET_MONTH><GIVE_DETAIL_CODE type=\"string\">1728</GIVE_DETAIL_CODE><STAGES_MONTH type=\"string\">12</STAGES_MONTH><EFF_DATE type=\"string\">2014-08-01</EFF_DATE><GIVE_FEE_NAME type=\"string\">8</GIVE_FEE_NAME><FEE_TYPE type=\"string\">2</FEE_TYPE><EFF_TYPE type=\"string\">0</EFF_TYPE><ADD_FEE_FLAG type=\"string\">N</ADD_FEE_FLAG><GIVE_FEE_TYPE type=\"string\">2</GIVE_FEE_TYPE><TOTAL_MONEY type=\"string\">120</TOTAL_MONEY><STAGES_MONEY type=\"string\">10</STAGES_MONEY><FEE_NAME type=\"string\">特殊增值业务</FEE_NAME><GIVE_MONEY type=\"string\">0</GIVE_MONEY><DETAIL_CODE type=\"string\">1560</DETAIL_CODE><PROD_PRCID type=\"string\">PRT11048_咪咕特会1元升级包</PROD_PRCID><IS_ADD_PROD type=\"string\">1</IS_ADD_PROD><PAY_MONEY type=\"string\">120</PAY_MONEY><STAGES_SPACE type=\"string\">0</STAGES_SPACE><EXP_DATE type=\"string\">2015-07-31</EXP_DATE></MONTH_RETURN><MODIFY_FLAG type=\"string\">N</MODIFY_FLAG></A10><A01><FEE_CODE type=\"string\">750</FEE_CODE><FEE_NAME type=\"string\">保留预存</FEE_NAME><DETAIL_CODE type=\"string\">1688</DETAIL_CODE><TAX_RATE type=\"string\">11</TAX_RATE><OFFSET_MONTH type=\"string\">12</OFFSET_MONTH><EFF_DATE type=\"string\">2016-07-01</EFF_DATE><ACCOUNT_TYPE type=\"string\">1</ACCOUNT_TYPE><FEE_TYPE type=\"string\">2</FEE_TYPE><EFF_TYPE type=\"string\">2</EFF_TYPE><PAY_MONEY type=\"string\">12</PAY_MONEY><ADD_FEE_FLAG type=\"string\">N</ADD_FEE_FLAG><ADD_A10 type=\"string\">Y</ADD_A10></A01><A00><IS_PURE type=\"string\">N</IS_PURE><FEE_CODE type=\"string\">181</FEE_CODE><FEE_NAME type=\"string\">预存款</FEE_NAME><DETAIL_CODE type=\"string\">532</DETAIL_CODE><TAX_RATE type=\"string\">11</TAX_RATE><ACCOUNT_TYPE type=\"string\">1</ACCOUNT_TYPE><FEE_TYPE type=\"string\">2</FEE_TYPE><PAY_MONEY type=\"string\">100</PAY_MONEY></A00><A06><LIMIT_COUNT type=\"string\">1</LIMIT_COUNT><PRC_INFO_LIST><PRC_INFO><INDEX type=\"string\">1</INDEX><FEE_INFO_LIST type=\"string\"></FEE_INFO_LIST><PROD_PRC_NAME type=\"string\">PR060279_最低消费10元（保留24个月）</PROD_PRC_NAME><CHOOSE_TYPE type=\"string\">0</CHOOSE_TYPE><ZK_LIST type=\"string\"></ZK_LIST><PROD_PRCID type=\"string\">PR060279</PROD_PRCID><EXP_OFFSET_MONTH type=\"string\">F</EXP_OFFSET_MONTH><EFF_TYPE type=\"string\">0</EFF_TYPE><EFF_DATE type=\"string\">2014-07-11</EFF_DATE><EXP_DATE type=\"string\">2016-06-30</EXP_DATE></PRC_INFO></PRC_INFO_LIST></A06><A23><GIFT_ITEM_LIST><GIFT_ITEM><INDEX type=\"string\">1</INDEX><GIFT_INFO><PRESENT_TYPE type=\"string\">Q</PRESENT_TYPE><REGION_NAME type=\"string\">[22]滁州</REGION_NAME><PRICE type=\"string\">20</PRICE><EXP_TYPE type=\"string\">1</EXP_TYPE><RESOURCE_NAME type=\"string\">滁州20元代金券</RESOURCE_NAME><PROV_DESC type=\"string\">滁州百大</PROV_DESC><EXP_VALUE type=\"string\">2014-07-11</EXP_VALUE><REGION_DESC type=\"string\">代金券滁州</REGION_DESC><AMOUNT type=\"string\">1</AMOUNT><EFF_TYPE type=\"string\">0</EFF_TYPE><PROV_CODE_NAME type=\"string\">合肥百大</PROV_CODE_NAME><RESOURCE_CODE type=\"string\">Q0100022</RESOURCE_CODE><EFF_DATE type=\"string\">2014-07-11</EFF_DATE></GIFT_INFO></GIFT_ITEM></GIFT_ITEM_LIST></A23><A04><GIFT_TYPE type=\"string\">9</GIFT_TYPE><GIFT_ITEM_LIST><GIFT_ITEM><INDEX type=\"string\">1</INDEX><GIFT_INFO><PRICE type=\"string\">1</PRICE><UNIT type=\"string\">个</UNIT><RESOURCE_NAME type=\"string\">05年NBA-腰包</RESOURCE_NAME><RESOURCE_CODE type=\"string\">40100002280034</RESOURCE_CODE><AMOUNT type=\"string\">1</AMOUNT><GIFT_BATCH type=\"string\">1</GIFT_BATCH></GIFT_INFO></GIFT_ITEM></GIFT_ITEM_LIST><MAX_MONEY type=\"string\">12</MAX_MONEY><CHOOSE_TYPE type=\"string\">0</CHOOSE_TYPE><GIFT_PRESENT_FLAG type=\"string\">Y</GIFT_PRESENT_FLAG></A04><TAX_FEE type=\"string\">null</TAX_FEE><CURRENT_PRICE type=\"string\">null</CURRENT_PRICE><TOTAL_MONEY type=\"string\">1466.0</TOTAL_MONEY><IN_TIME type=\"string\">2014-07-11 15:55:53</IN_TIME><PREORDER_ID type=\"string\"></PREORDER_ID><OP_TIME type=\"string\">2014-07-11 15:56:02</OP_TIME><ID_ICCID type=\"string\"></ID_ICCID><TAINFO_LIST></TAINFO_LIST><LOGIN_ACCEPT type=\"long\">10000013354603</LOGIN_ACCEPT><OP_CODE type=\"string\">1147</OP_CODE></MEANS></ROOT>";
        String json = InOutFormat.formatIn(xml, InOutFormat.ParaType.XML);
        MBean bean = new MBean(json);
        return bean;
    }

    public static Map<String, Object> callHttpService(String serviceName, Object params) {
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> root = new HashMap<>();
        Map<String, Object> rootJson = new HashMap<>();
        body.put("BODY", params);
        root.put("ROOT", body);
        rootJson.put("rootJson", root);
        JSONObject jsonParam = JSONObject.fromObject(rootJson);
        JSONObject responseJSONObject = HttpRequestUtil.httpPost(serviceName, jsonParam);
        if (responseJSONObject != null && "SUCCESS".equals(responseJSONObject.get("status"))) {
            System.out.println("http Post request process sucess" + responseJSONObject);
            return (Map<String, Object>) JSON.parse(responseJSONObject.toString());
        } else {
            System.out.println("http Post request process fail");
            throw new BusiException("3890220171111", "调用HTTP服务错误,请检查源服务或者网络后重试！");
        }
    }

    public static Map<String, Object> callHttpClientService(String serviceName, Map params) {
        System.out.println(params);
        Map<String, Object> rootJson = new HashMap<>();
        MBean mBean = new MBean();
        mBean.setBody(params);
        rootJson.put("rootJson", mBean.toString());
        String str = HttpClientUtil.doPost(serviceName, rootJson, "UTF-8", true);
        if (str != null) {
            System.out.println("http Post request result:" + str);
            return (Map) JSON.parse(str);
        } else {
            System.out.println("http Post request process fail");
            throw new BusiException("3890220171111", "调用HTTP服务错误,请检查源服务或者网络后重试！");
        }
    }
    /**
     *
     * @author wangshibao
     * @param serviceName
     * @param params
     * @return
     * @date   2018/4/23 20:27
     */
    public static Map<String, Object> callHttpWXService(String serviceName, Map params) {
        Map<String, Object> rootJson = new HashMap<>();
        MBean mBean = new MBean();
        mBean.setBody(params);
        rootJson.put("jsonStr", mBean.toString());
        String str = HttpClientUtil.doPost(serviceName, rootJson, "UTF-8", true);
        if (str != null) {
            System.out.println("http Post request result:" + str);
            return (Map) JSON.parse(str);
        } else {
            System.out.println("http Post request process fail");
            throw new BusiException("3890220171111", "调用微服务错误,请检查源服务或者网络后重试！");
        }
    }
}
