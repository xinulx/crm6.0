package com.mine.svc.common.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import org.apache.log4j.Logger;
import org.dom4j.*;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JXml {

    public static final Logger log = Logger.getLogger(JXml.class);
    public static final String ROOT = "ROOT";
    public static final String U_INT = "int";
    public static final String U_LONG = "long";
    public static final String U_DOUBLE = "double";
    public static final String U_STRING = "string";

    public static JSONObject toJson(String xmlStr) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);

        JSONObject jsonroot = new JSONObject(true);
        JSONObject json = new JSONObject(true);

        jsonroot.put(doc.getRootElement().getName(), json);

        element2Json(doc.getRootElement(), json);

        return jsonroot;
    }

    public static String toXml(String jsonStr) {
        Map rootMap = (Map) JSONObject.parseObject(jsonStr, new TypeReference<LinkedHashMap<String, Object>>() {
        }, new Feature[]{Feature.OrderedField});

        Document document = DocumentHelper.createDocument();

        json2Element(rootMap, document, null);

        document.setXMLEncoding("GBK");
        return document.asXML();
    }

    public static void json2Element(Map<String, Object> pMap, Document doc, Element element) {
        for (Map.Entry entry : pMap.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                Element childElement = null;
                if ((element == null) && (pMap.size() == 1)) {
                    childElement = doc.addElement(key);
                } else {
                    childElement = element.addElement(key);
                }

                json2Element((Map) value, doc, childElement);
            } else {
                Iterator i$;
                if (value instanceof List) {
                    if ((element == null) && (pMap.size() == 1)) {
                        element = doc.addElement("ROOT");
                    }

                    for (i$ = ((List) value).iterator(); i$.hasNext(); ) {
                        Object lstvalue = i$.next();

                        if ((!(lstvalue instanceof Map)) && (!(lstvalue instanceof List))) {
                            createElement(element, key, lstvalue);
                        } else {
                            Element childElement = element.addElement(key);

                            json2Element((Map) lstvalue, doc, childElement);
                        }
                    }
                } else {
                    createElement(element, key, value);
                }
            }
        }
    }

    public static void createElement(Element parentElement, String name, Object value) {
        Element element = parentElement.addElement(name);
        element.setText(value.toString());

        if (value instanceof Integer) {
            element.addAttribute("type", "int");
        } else if (value instanceof Long) {
            element.addAttribute("type", "long");
        } else if (value instanceof BigDecimal) {
            element.addAttribute("type", "double");
        } else
            element.addAttribute("type", "string");
    }

    public static void element2Json(Element element, JSONObject json) {
        List<Element> chdEl = element.elements();
        if (chdEl.isEmpty()) {
            json.put(element.getName(), getValueByType(element));
        }

        for (Element e : chdEl)

            if (!(e.elements().isEmpty())) {
                JSONObject chdjson = new JSONObject(true);
                element2Json(e, chdjson);
                Object o = json.get(e.getName());
                if (o != null) {
                    JSONArray jsona = null;
                    if (o instanceof JSONObject) {
                        JSONObject jsono = (JSONObject) o;
                        json.remove(e.getName());
                        jsona = new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if (o instanceof JSONArray) {
                        jsona = (JSONArray) o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                } else if (!(chdjson.isEmpty())) {
                    json.put(e.getName(), chdjson);
                }

            } else {
                Object o = json.get(e.getName());
                if (o != null) {
                    JSONArray jsona = null;
                    if (o instanceof JSONObject) {
                        JSONObject jsono = (JSONObject) o;
                        json.remove(e.getName());
                        jsona = new JSONArray();
                        jsona.add(jsono);
                        jsona.add(getValueByType(e));
                    } else if (o instanceof JSONArray) {
                        jsona = (JSONArray) o;
                        jsona.add(getValueByType(e));
                    } else {
                        jsona = new JSONArray();
                        jsona.add(o);
                        jsona.add(getValueByType(e));
                    }
                    json.put(e.getName(), jsona);
                } else {
                    json.put(e.getName(), getValueByType(e));
                }
            }
    }

    public static Object getValueByType(Element element) {
        Attribute attr = element.attribute("type");
        String type = (attr == null) ? null : attr.getValue();
        String value = element.getText();
        value = (isEmpty(value)) ? "" : value;
        try {
            if ("int".equals(type)) {
                return Integer.valueOf((value.equals("")) ? 0 : Integer.valueOf(value).intValue());
            }
            if ("long".equals(type)) {
                return Long.valueOf((value.equals("")) ? 0L : Long.valueOf(value).longValue());
            }
            if ("double".equals(type))
                return Double.valueOf((value.equals("")) ? 0.0D : Double.valueOf(value).doubleValue());
        } catch (NumberFormatException e) {
            log.error("convet " + value + " to " + type + " error,will set value to 0 ");
            return Integer.valueOf(0);
        }

        return value;
    }

    public static boolean isEmpty(String str) {
        return ((str == null) || (str.trim().isEmpty()) || ("null".equals(str)));
    }

    public static void main(String[] args) throws DocumentException {
        System.out.println(JXml.toJson("<A>AAA</A>"));
        String json =
                "{\n" +
                        "    \"ROOT\": {\n" +
                        "        \"BODY\":{\n" +
                        "        \"BUSI_INFO\": {\n" +
                        "            \"MEANS\": {\n" +
                        "                \"MEAN\": [\n" +
                        "                    {\n" +
                        "                        \"A10\": {\n" +
                        "                            \"ATTR_CTRL\": {\n" +
                        "                                \"IS_MODIFY\": {\n" +
                        "                                    \"STAGES_MONTH\": \"Y\",\n" +
                        "                                    \"STAGES_MONEY\": \"Y\",\n" +
                        "                                    \"FAV_RATE\": \"Y\"\n" +
                        "                                }\n" +
                        "                            },\n" +
                        "                            \"MODIFY_FLAG\": \"Y\",\n" +
                        "                            \"TAX_RATE\": \"11\",\n" +
                        "                            \"GIVE_OTHERS_FLAG\": \"1\",\n" +
                        "                            \"BELONG_TO\": \"1\",\n" +
                        "                            \"MONTH_RETURN\": [\n" +
                        "                                {\n" +
                        "                                    \"INDEX\": \"1\",\n" +
                        "                                    \"IS_ADD_PROD\": \"0\",\n" +
                        "                                    \"TOTAL_MONEY\": \"180\",\n" +
                        "                                    \"STAGES_MONTH\": \"18\",\n" +
                        "                                    \"STAGES_MONEY\": \"10.00\",\n" +
                        "                                    \"STAGES_SPACE\": \"0\",\n" +
                        "                                    \"STAGES_CYCLE\": \"99\",\n" +
                        "                                    \"FAV_RATE\": \"30.00\",\n" +
                        "                                    \"DETAIL_CODE\": \"03030\",\n" +
                        "                                    \"PAY_MONEY\": \"126.00\",\n" +
                        "                                    \"GIVE_DETAIL_CODE\": \"03300\",\n" +
                        "                                    \"GIVE_MONEY\": \"54.00\",\n" +
                        "                                    \"EFF_TYPE\": \"2\",\n" +
                        "                                    \"OFFSET_MONTH\": \"6\",\n" +
                        "                                    \"FEE_CODE\": \"47\",\n" +
                        "                                    \"FEE_TYPE\": \"2\",\n" +
                        "                                    \"GIVE_FEE_CODE\": \"183\",\n" +
                        "                                    \"GIVE_FEE_NAME\": \"8\",\n" +
                        "                                    \"GIVE_FEE_TYPE\": \"2\",\n" +
                        "                                    \"ADD_FEE_FLAG\": \"N\",\n" +
                        "                                    \"FEE_NAME\": \"分月返还待转预存\",\n" +
                        "                                    \"IS_SYNEFF\": \"N\",\n" +
                        "                                    \"IS_A10MASTER\": \"N\",\n" +
                        "                                    \"EFF_DATE\": \"2019-09-01\",\n" +
                        "                                    \"PROD_PRCID\": \"\",\n" +
                        "                                    \"PRC_MASTERSERVER\": \"\",\n" +
                        "                                    \"MSG_STENCIL\": \"\",\n" +
                        "                                    \"EXP_DATE\": \"2021-02-28\"\n" +
                        "                                }\n" +
                        "                            ]\n" +
                        "                        },\n" +
                        "                        \"MEANS_ID\": \"201707031044211486\",\n" +
                        "                        \"MEANS_NAME\": \"分返延6\",\n" +
                        "                        \"ACT_ID\": \"201707031044211388\",\n" +
                        "                        \"ACT_NAME\": \"java一网通 \",\n" +
                        "                        \"IS_ACTLIB\": \"\",\n" +
                        "                        \"RESOURCE_USE\": \"\",\n" +
                        "                        \"CHECK_CANCEL\": \"\",\n" +
                        "                        \"ACTGROUP_TYPE\": \"00\",\n" +
                        "                        \"CONTRACTCODE\": \"\",\n" +
                        "                        \"SCOREPAY_FLAG\": \"\",\n" +
                        "                        \"SMS_MSG\": \"尊敬的用户:您在【time】成功订购【actName】活动的【meansName】的业务\",\n" +
                        "                        \"MSG\": \"尊敬的用户:您在【time】成功订购【actName】活动的【meansName】的业务\",\n" +
                        "                        \"SEND_FLAG\": \"Y\",\n" +
                        "                        \"TOTAL_MONEY\": \"126\"\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            },\n" +
                        "            \"OP_NOTE\": \"\",\n" +
                        "            \"AUTO_CONFIRM\": \"N\",\n" +
                        "            \"SALE_LOGIN_NO\": \"\",\n" +
                        "            \"SALE_LOGIN_NAME\": \"\",\n" +
                        "            \"OP_CODE\": \"4622\",\n" +
                        "            \"CHN_TYPE\": \"0\",\n" +
                        "            \"OPEN_FLAG\": \"N\",\n" +
                        "            \"LOGIN_NO\": \"M0AAA0002\",\n" +
                        "            \"GROUP_ID\": \"122000464\",\n" +
                        "            \"PHONE_NO\": \"22310007117710\",\n" +
                        "            \"MASTER_SERV_ID\": \"2222\"\n" +
                        "        },\n" +
                        "        \"COMMON_INFO\": {\n" +
                        "            \"SESSION_ID\": \"\",\n" +
                        "            \"PROVINCE_GROUP\": \"10017\",\n" +
                        "            \"TRACE_ID\": \"11*20170705105801*4622*M0AAA0002*652726\",\n" +
                        "            \"APP_ID\": \"\",\n" +
                        "            \"APP_NAME\": \"\",\n" +
                        "            \"BACK2\": \"\",\n" +
                        "            \"CALL_ID\": \"8B341A86F8B6D75B8AFECA8461701DAF\",\n" +
                        "            \"DEST_IP\": \"\",\n" +
                        "            \"DEST_PORT\": \"\",\n" +
                        "            \"FROM_SYS\": \"\",\n" +
                        "            \"ROUND_AUDIT\": \"\",\n" +
                        "            \"SER_NAME\": \"0\",\n" +
                        "            \"SRC_PORT\": \"\"\n" +
                        "        },\n" +
                        "        \"OPR_INFO\": {\n" +
                        "            \"REGION_ID\": \"22\",\n" +
                        "            \"CHANNEL_TYPE\": \"21J\",\n" +
                        "            \"LOGIN_NO\": \"M0AAA0002\",\n" +
                        "            \"LOGIN_PWD\": \"fb0bdec823cf4cdb\",\n" +
                        "            \"IP_ADDRESS\": \"10.152.30.77\",\n" +
                        "            \"GROUP_ID\": \"122000464\",\n" +
                        "            \"AUTHEN_CODE\": \"\",\n" +
                        "            \"AUTHEN_NAME\": \"\",\n" +
                        "            \"OP_CODE\": \"4622\",\n" +
                        "            \"CONTACT_ID\": \"1117070500000001367953419\"\n" +
                        "        },\n" +
                        "        \"HEAD\": {\n" +
                        "            \"DB_ID\": \"A2\"\n" +
                        "        },\n" +
                        "        \"HEADER\": {\n" +
                        "            \"DB_ID\": \"\"\n" +
                        "        }\n" +
                        "    }}\n" +
                        "}";
        System.out.println(json);
        System.out.println("==========mbean打印的=======");
        System.out.println(new MBean(json));
        System.out.println(JXml.toXml(json));


        String sql =
                "SELECT ID_NO,\n" +
                        "       LIMIT_LEVEL_ID,\n" +
                        "       PROD_ID,\n" +
                        "       PROD_PRCID,\n" +
                        "       FUNCTION_CODE,\n" +
                        "       TO_CHAR(EFF_DATE, 'YYYYMMDDHH24MISS') EFF_DATE,\n" +
                        "       TO_CHAR(EXP_DATE, 'YYYYMMDDHH24MISS') EXP_DATE,\n" +
                        "       LIMIT_TYPE,\n" +
                        "       LIMIT_FLAG,\n" +
                        "       LOGIN_NO,\n" +
                        "       LOGIN_ACCEPT,\n" +
                        "       TO_CHAR(OP_TIME, 'YYYYMMDDHH24MISS') OP_TIME,\n" +
                        "       NVL(TRIM(OP_CODE), '0000') OP_CODE,\n" +
                        "       RULE_ID,\n" +
                        "       REMARK,\n" +
                        "       LIMIT_DETAILMSG\n" +
                        "  FROM UR_USERBUSILIMIT_INFO\n" +
                        " WHERE ID_NO = :ID_NO < LONG >\n" +
                        "   AND EFF_DATE <= TO_DATE(:OP_TIME < CHAR [ 15 ] >, 'YYYYMMDDHH24MISS')\n" +
                        "   AND EXP_DATE > TO_DATE(:OP_TIME < CHAR [ 15 ] >, 'YYYYMMDDHH24MISS')\n" +
                        "   AND FUNCTION_CODE = :FUNCTION_CODE < CHAR [ 6\n" +
                        " ] >\n" +
                        "   AND LIMIT_TYPE = :LIMIT_TYPE < CHAR [ 2 ] >\n" +
                        "   AND PROD_ID = :PROD_ID < CHAR [ 21 ] >\n" +
                        "   AND PROD_PRCID = :PROD_PRCID < CHAR [ 21 ] >";
        System.out.println(sql);
    }
}
