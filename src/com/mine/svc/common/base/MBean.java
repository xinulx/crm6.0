package com.mine.svc.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mine.App.common.util.Constant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class MBean implements Map, Serializable {

    public static final String OUT_DATA = "OUT_DATA";
    private static final String HEADER_NODE = "HEADER";
    private static final String HEADER_PATH = "ROOT.HEADER";
    private static final String BODY_PATH = "ROOT.BODY";
    private static final String ROUTE_KEY_PATH = "ROOT.HEADER.ROUTING.ROUTE_KEY";
    private static final String ROUTE_VALUE_PATH = "ROOT.HEADER.ROUTING.ROUTE_VALUE";
    private static final String NOHEAD_ROUTE_KEY_PATH = "ROUTING.ROUTE_KEY";
    private static final String NOHEAD_ROUTE_VALUE_PATH = "ROUTING.ROUTE_VALUE";
    private static final String NOHEAD_ROUTE_READ_WRITE_FLAG_PATH = "ROUTING.READ_WIRTE_FLAG";
    private static final String POOL_ID_PATH = "ROOT.HEADER.POOL_ID";
    private static final String ENV_ID_PATH = "ROOT.HEADER.ENV_ID";
    private static final String CONTACT_ID_PATH = "ROOT.HEADER.CONTACT_ID";
    private static final String CHANNEL_ID_PATH = "ROOT.HEADER.CHANNEL_ID";
    private static final String USERNAME_PATH = "ROOT.HEADER.USERNAME";
    private static final String PASSWORD_PATH = "ROOT.HEADER.PASSWORD";
    private static final String DB_ID = "DB_ID";
    private static final String DB_ID_PATH = "ROOT.HEADER.DB_ID";
    private static final String ENDUSRLOGINID_PATH = "ROOT.HEADER.ENDUSRLOGINID";
    private static final String ENDUSRLOGINKEY_PATH = "ROOT.HEADER.ENDUSRLOGINKEY";
    private static final String ENDUSRIP_PATH = "ROOT.HEADER.ENDUSRIP";
    public static final String PROVINCE_GROUP = "PROVINCE_GROUP";
    public static final String TENANT_ID = "TENANT_ID";
    private static final long serialVersionUID = -5053942806225519035L;
    private Map ROOT;
    private Map HEADER;
    private Map BODY;
    private Map RESULT;
    private boolean isChange;
    private boolean isOut;
    private String jsonString;

    public MBean() {
        isChange = false;
        isOut = false;
        ROOT = new LinkedHashMap();
        HEADER = new LinkedHashMap();
        set(ROOT, "ROOT.HEADER", HEADER);
        BODY = new LinkedHashMap();
        set(ROOT, "ROOT.BODY", BODY);
        if (Constant.VERSION < 250) {
            RESULT = new LinkedHashMap();
            set(ROOT, "ROOT.RESULT", RESULT);
        }
        set(ROOT, "ROOT.HEADER.ROUTING.ROUTE_KEY", "");
        set(ROOT, "ROOT.HEADER.ROUTING.ROUTE_VALUE", "");
        isChange = true;
    }

    public MBean(String in) {
        isChange = false;
        isOut = false;
        jsonString = in;
        Map tmpRoot = (Map) JSON.parseObject(in, LinkedHashMap.class);
        ROOT = new LinkedHashMap();
        HEADER = (Map) getObject(tmpRoot, "ROOT.HEADER");
        if (null == HEADER)
            HEADER = new LinkedHashMap();
        set(ROOT, "ROOT.HEADER", HEADER);
        BODY = (Map) getObject(tmpRoot, "ROOT.BODY");
        if (null == BODY)
            BODY = new LinkedHashMap();
        set(ROOT, "ROOT.BODY", BODY);
        if (Constant.VERSION < 250) {
            RESULT = (Map) getObject(tmpRoot, "ROOT.RESULT");
            if (null == RESULT)
                RESULT = new LinkedHashMap();
        }
    }

    public MBean(String in, boolean flag) {
        isChange = false;
        isOut = false;
        Map tmpRoot = (Map) JSON.parseObject(in, LinkedHashMap.class);
        ROOT = new LinkedHashMap();
        HEADER = (Map) getObject(tmpRoot, "ROOT.HEADER");
        if (null == HEADER)
            HEADER = new LinkedHashMap();
        set(ROOT, "ROOT.HEADER", HEADER);
        BODY = (Map) getObject(tmpRoot, "ROOT.BODY");
        if (null == BODY) {
            BODY = new LinkedHashMap();
            if (flag) {
                Map bodyMap = tmpRoot;
                if (tmpRoot.size() == 1) {
                    String jsonRoot = (String) tmpRoot.keySet().iterator().next();
                    Object bodyObject = tmpRoot.get(jsonRoot);
                    if (bodyObject != null && (bodyObject instanceof Map)) {
                        bodyMap = (Map) bodyObject;
                        bodyMap.remove("HEADER");
                    }
                }
                BODY.putAll(bodyMap);
            }
        }
        set(ROOT, "ROOT.BODY", BODY);
        if (Constant.VERSION < 250) {
            RESULT = (Map) getObject(tmpRoot, "ROOT.RESULT");
            if (null == RESULT)
                RESULT = new LinkedHashMap();
            set(ROOT, "ROOT.RESULT", RESULT);
        }
        isChange = true;
        jsonString = in;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean isOut) {
        isChange = true;
        this.isOut = isOut;
    }

    public void changed() {
        isChange = true;
    }

    public String getDbId() {
        return getHeaderStr("DB_ID");
    }

    public String getRouteKey() {
        return getHeaderStr("ROUTING.ROUTE_KEY");
    }

    public String getRouteValue() {
        return getHeaderStr("ROUTING.ROUTE_VALUE");
    }

    public String getReadWriteFlag() {
        return getHeaderStr("ROUTING.READ_WIRTE_FLAG");
    }

    /**
     * @deprecated Method setDbId is deprecated
     */

    public void setDbId(String dbId) {
        set(ROOT, "ROOT.HEADER.DB_ID", dbId);
    }

    public void setRoute(String routeKey, String routeValue) {
        setRouteKey(routeKey);
        setRouteValue(routeValue);
    }

    public void setRouteKey(String routeKey) {
        set(ROOT, "ROOT.HEADER.ROUTING.ROUTE_KEY", routeKey);
    }

    public void setRouteValue(String routeValue) {
        set(ROOT, "ROOT.HEADER.ROUTING.ROUTE_VALUE", routeValue);
    }

    public Map getHeader() {
        Map cloneHead = new LinkedHashMap();
        cloneHead.putAll(HEADER);
        return cloneHead;
    }

    public Map getBody() {
        return BODY;
    }

    public void setRoot(String path, Object value) {
        set(ROOT, path, value);
    }

    public String getHeaderStr(String path) {
        Object retObj = getObject(HEADER, path);
        return retObj != null ? retObj.toString() : null;
    }

    public boolean getHeaderBool(String path) {
        return "true".equalsIgnoreCase(getObject(HEADER, path).toString());
    }

    public Integer getHeaderInt(String path) {
        Object retObj = getObject(HEADER, path);
        if (retObj == null)
            return null;
        else
            return Integer.valueOf(retObj.toString());
    }

    public Double getHeaderDouble(String path) {
        Object retObj = getObject(HEADER, path);
        if (null == retObj)
            return null;
        else
            return Double.valueOf(Double.valueOf(retObj.toString()).doubleValue());
    }

    public Long getHeaderLong(String path) {
        Object o = getObject(HEADER, path);
        if (null == o)
            return null;
        if (o instanceof Long)
            return (Long) o;
        if (o instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) o;
            return Long.valueOf(bd.longValue());
        } else {
            return Long.valueOf(o.toString());
        }
    }

    public String getBodyStr(String path) {
        Object o = getObject(BODY, path);
        if (null == o)
            return null;
        if (o instanceof String)
            return (String) o;
        else
            return o.toString();
    }

    public boolean getBodyBool(String path) {
        return "true".equalsIgnoreCase(getObject(BODY, path).toString());
    }

    public Integer getBodyInt(String path) {
        Object retObj = getObject(BODY, path);
        return Integer.valueOf(retObj.toString());
    }

    public Double getBodyDouble(String path) {
        Object retObj = getObject(BODY, path);
        if (null == retObj)
            return null;
        else
            return Double.valueOf(Double.valueOf(retObj.toString()).doubleValue());
    }

    public Long getBodyLong(String path) {
        Object o = getObject(BODY, path);
        if (null == o)
            return null;
        if (o instanceof Long)
            return (Long) o;
        if (o instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) o;
            return Long.valueOf(bd.longValue());
        } else {
            return Long.valueOf(o.toString());
        }
    }

    public Object getBodyObject(String path) {
        return getObject(BODY, path);
    }

    public Object getBodyObject(String path, Class clazz) {
        Object o = getObject(BODY, path);
        if (null == o)
            return null;
        if (null == clazz)
            return o;
        if (o instanceof JSONObject) {
            JSONObject jsonObj = (JSONObject) o;
            return JSON.parseObject(jsonObj.toJSONString(), clazz);
        } else {
            return o;
        }
    }

    public List getBodyList(String path) {
        Object o = getObject(BODY, path);
        return (List) o;
    }

    public List getBodyList(String path, Class clazz) {
        Object o = getObject(BODY, path);
        if (null == o)
            return null;
        if (null == clazz)
            return (List) o;
        if (o instanceof JSONArray) {
            JSONArray jsonArr = (JSONArray) o;
            return JSON.parseArray(JSON.toJSONString(jsonArr), clazz);
        }
        if (o instanceof List)
            return (List) o;
        else
            throw new ClassCastException(
                    (new StringBuilder()).append("path=").append(path).append(" ").append(o.getClass().getName())
                            .append(" cast ").append(clazz.getName()).append(" error").toString());
    }

    public void setBody(String path, Object value) {
        if (null == path || "".equals(path))
            return;
        String tmpPath = path;
        if (path.startsWith("ROOT.BODY"))
            tmpPath = path.substring("ROOT.BODY".length() + 1);
        set(BODY, tmpPath, value);
    }

    public void addBody(String path, Object value) {
        if (null == path || "".equals(path))
            return;
        String tmpPath = path;
        if (path.startsWith("ROOT.BODY"))
            tmpPath = path.substring("ROOT.BODY".length() + 1);
        add(BODY, path, value);
    }

    /**
     * @deprecated Method getResultStr is deprecated
     */

    public String getResultStr(String path) {
        return (String) getObject(RESULT, path);
    }

    /**
     * @deprecated Method getResultBool is deprecated
     */

    public boolean getResultBool(String path) {
        return "true".equalsIgnoreCase(getObject(RESULT, path).toString());
    }

    /**
     * @deprecated Method getResultInt is deprecated
     */

    public Integer getResultInt(String path) {
        return (Integer) getObject(RESULT, path);
    }

    /**
     * @deprecated Method getResultDouble is deprecated
     */

    public Double getResultDouble(String path) {
        BigDecimal bd = (BigDecimal) getObject(RESULT, path);
        if (null == bd)
            return null;
        else
            return Double.valueOf(bd.doubleValue());
    }

    /**
     * @deprecated Method getResultLong is deprecated
     */

    public Long getResultLong(String path) {
        BigDecimal bd = (BigDecimal) getObject(RESULT, path);
        if (null == bd)
            return null;
        else
            return Long.valueOf(bd.longValue());
    }

    /**
     * @deprecated Method getResultObject is deprecated
     */

    public Object getResultObject(String path, Class clazz) {
        Object o = getObject(RESULT, path);
        if (o instanceof JSONObject) {
            JSONObject jsonObj = (JSONObject) o;
            if (null == jsonObj)
                return null;
            else
                return JSON.parseObject(jsonObj.toJSONString(), clazz);
        } else {
            return o;
        }
    }

    /**
     * @deprecated Method getResultList is deprecated
     */

    public List getResultList(String path, Class clazz) {
        Object o = getObject(RESULT, path);
        if (null == o)
            return null;
        if (o instanceof JSONArray) {
            JSONArray jsonArr = (JSONArray) o;
            if (null == jsonArr)
                return null;
            else
                return JSON.parseArray(JSON.toJSONString(jsonArr), clazz);
        }
        if (o instanceof List)
            return (List) o;
        else
            throw new ClassCastException(
                    (new StringBuilder()).append("path=").append(path).append(" ").append(o.getClass().getName())
                            .append(" cast ").append(clazz.getName()).append(" error").toString());
    }

    /**
     * @deprecated Method setResult is deprecated
     */

    public void setResult(Map result) {
        RESULT = result;
    }

    /**
     * @deprecated Method addResult is deprecated
     */

    public void addResult(String path, Object value) {
        add(RESULT, path, value);
    }

    public String getStr(String path) {
        Object retObj = getObject(ROOT, path);
        if (retObj == null)
            return null;
        if (retObj instanceof String)
            return (String) retObj;
        else
            return retObj.toString();
    }

    public boolean getBool(String path) {
        return "true".equalsIgnoreCase(getObject(ROOT, path).toString());
    }

    public Integer getInt(String path) {
        return (Integer) getObject(ROOT, path);
    }

    public Double getDouble(String path) {
        BigDecimal bd = (BigDecimal) getObject(ROOT, path);
        if (null == bd)
            return null;
        else
            return Double.valueOf(bd.doubleValue());
    }

    public Long getLong(String path) {
        Object o = getObject(ROOT, path);
        if (null == o)
            return null;
        if (o instanceof Long)
            return (Long) o;
        if (o instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) o;
            return Long.valueOf(bd.longValue());
        } else {
            Integer i = (Integer) o;
            return Long.valueOf(i.intValue());
        }
    }

    public Object getObject(String path) {
        return getObject(ROOT, path);
    }

    public Object getObject(String path, Class clazz) {
        Object o = getObject(ROOT, path);
        if (null == o)
            return null;
        if (null == clazz)
            return o;
        if (o instanceof JSONObject) {
            JSONObject jsonObj = (JSONObject) o;
            return JSON.parseObject(jsonObj.toJSONString(), clazz);
        } else {
            return o;
        }
    }

    public List getList(String path) {
        Object o = getObject(ROOT, path);
        return (List) o;
    }

    public List getList(String path, Class clazz) {
        Object o = getObject(ROOT, path);
        if (null == o)
            return null;
        if (null == clazz)
            return (List) o;
        if (o instanceof JSONArray) {
            JSONArray jsonArr = (JSONArray) o;
            return JSON.parseArray(JSON.toJSONString(jsonArr), clazz);
        }
        if (o instanceof List)
            return (List) o;
        else
            throw new ClassCastException(
                    (new StringBuilder()).append("path=").append(path).append(" ").append(o.getClass().getName())
                            .append(" cast ").append(clazz.getName()).append(" error").toString());
    }

    public void renameBody(String oldPath, String newPath) {
        if (null == oldPath || "".equals(oldPath))
            return;
        if (null == newPath || "".equals(newPath))
            return;
        String tmpOldPath = oldPath;
        if (oldPath.startsWith("ROOT.BODY"))
            tmpOldPath = oldPath.substring("ROOT.BODY".length() + 1);
        String tmpNewPath = newPath;
        if (newPath.startsWith("ROOT.BODY"))
            tmpNewPath = newPath.substring("ROOT.BODY".length() + 1);
        rename(BODY, tmpOldPath, tmpNewPath);
    }

    private void rename(Map map, String oldPath, String newPath) {
        Object o = getObject(map, oldPath);
        remove(map, oldPath);
        set(map, newPath, o);
    }

    private void remove(Map map, String path) {
        if (null == path || "".equals(path))
            return;
        isChange = true;
        Map subMap = map;
        String keys[] = path.split("\\.");
        if (keys.length < 2) {
            Object o = subMap.get(path);
            if (null == o) {
                return;
            } else {
                subMap.remove(path);
                return;
            }
        }
        int i;
        for (i = 0; i < keys.length - 1; i++) {
            if (null == subMap)
                return;
            subMap = (Map) subMap.get(keys[i]);
        }

        if (null == subMap) {
            return;
        } else {
            subMap.remove(keys[i]);
            return;
        }
    }

    private Object getObject(Map map, String path) {
        if (null == path || "".equals(path) || null == map)
            return null;
        Map subMap = map;
        String keys[] = path.split("\\.");
        if (keys.length < 2)
            return subMap.get(path);
        int i;
        for (i = 0; i < keys.length - 1; i++) {
            if (null == subMap)
                return null;
            subMap = (Map) subMap.get(keys[i]);
        }

        if (null == subMap)
            return null;
        else
            return subMap.get(keys[i]);
    }

    private void setHead(String path, Object value) {
        set(HEADER, path, value);
    }

    /**
     * @deprecated Method setResult is deprecated
     */

    public void setResult(String path, Object value) {
        set(RESULT, path, value);
    }

    public void setHeader(Map header) {
        HEADER = header;
        set(ROOT, "ROOT.HEADER", header);
    }

    public void setBody(Map body) {
        BODY = body;
        set(ROOT, "ROOT.BODY", body);
    }

    private void add(Map map, String path, Object value) {
        isChange = true;
        Object o = getObject(map, path);
        if (null == o)
            setBody(path, value);
        else if (o instanceof Collection) {
            ((Collection) o).add(value);
        } else {
            List l = new ArrayList(4);
            l.add(o);
            l.add(value);
            setBody(path, l);
        }
    }

    private void set(Map map, String path, Object value) {
        if (null == path || "".equals(path))
            return;
        isChange = true;
        Map subMap = map;
        String keys[] = path.split("\\.");
        if (keys.length < 2) {
            subMap.put(path, value);
            return;
        }
        int i = 0;
        Map tmp = null;
        Object o = null;
        int index = -1;
        int arrIndex = -1;
        String tmpKey = null;
        String tmpIndex = null;
        for (; i < keys.length - 1; i++) {
            tmpKey = keys[i];
            index = tmpKey.indexOf('[');
            if (index > 0) {
                tmpIndex = tmpKey.substring(index + 1);
                tmpIndex = tmpIndex.substring(0, tmpIndex.length() - 1);
                tmpKey = tmpKey.substring(0, index);
                arrIndex = Integer.parseInt(tmpIndex);
                o = subMap.get(tmpKey);
                if (null == o)
                    tmp = null;
                else if (o instanceof List)
                    tmp = (Map) ((List) o).get(arrIndex);
                else
                    tmp = null;
            } else {
                tmp = (Map) subMap.get(keys[i]);
            }
            if (null == tmp) {
                tmp = new LinkedHashMap();
                subMap.put(keys[i], tmp);
            }
            subMap = tmp;
        }

        subMap.put(keys[i], value);
    }

    public String toString() {
        if (!isChange)
            return jsonString;
        SerializerFeature features[] = {SerializerFeature.DisableCircularReferenceDetect};
        String dateFormat = "yyyyMMdd HHmmss";
        if (isOut) {
            Map outRoot = new LinkedHashMap(1);
            set(outRoot, "ROOT.BODY", BODY);
            jsonString = JSON.toJSONStringWithDateFormat(outRoot, dateFormat, features);
        } else {
            jsonString = JSON.toJSONStringWithDateFormat(ROOT, dateFormat, features);
        }
        isChange = false;
        return jsonString;
    }

    public int size() {
        return ROOT.size();
    }

    public boolean isEmpty() {
        return ROOT.isEmpty();
    }

    public boolean containsKey(Object key) {
        return ROOT.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return ROOT.containsValue(value);
    }

    public Object get(Object key) {
        return ROOT.get(key);
    }

    public Object put(Object key, Object value) {
        return null;
    }

    public Object remove(Object key) {
        return null;
    }

    public void putAll(Map map) {
    }

    public void clear() {
    }

    public Set keySet() {
        return null;
    }

    public Collection values() {
        return null;
    }

    public Set entrySet() {
        return null;
    }

//    public static void main(String[] args) {
//        String json = "{\n    \"ROOT\": {\n        \"HEADER\": {\n            \"POOL_ID\": \"1121312313.1231231\",\n            \"DB_ID\": \"111\",\n            \"ENV_ID\": \"222\",\n            \"TRACE_ID\": \"222\",\n            \"SUB_TRACE_ID\": \"222\",\n            \"CONTACT_ID\": \"11201312231431111110fhhh0M28\",\n            \"ROUTING\": {\n                \"ROUTE_KEY\": \"\",\n                \"ROUTE_VALUE\": \"\"\n            }\n        },\n        \"BODY\": {\n            \"BUSI_INFO\": {\n                \"NEW_CUST_FLAG\": \"0\",\n                \"ID_NO\": \"220100000000000002\",\n                \"PHONE_NO\": \"13834882898\",\n                \"MASTER_SERV_ID\": \"1001\",\n                \"CUST_ID\": \"19007814533629\",\n                \"CONTRACT_NO\": \"1000000000004\",\n                \"CUST_NAME\": \"测试\",\n                \"ID_TYPE\": \"1\",\n                \"ID_ICCID\": \"112121212121212121212\",\n                \"TYPE_CODE\": \"1\",\n                \"ID_TYPE_NAME\": \"身份证\",\n                \"PRODUCT_LIST\": [\n                    {\n                        \"EFF_DATE\": \"20140821152844\",\n                        \"EXP_DATE\": \"20991231235959\",\n                        \"BILL_TYPE\": \"0\",\n                        \"PRODPRC_INFO\": {\n                            \"PRODPRCINS_ID\": \"67000000416506\",\n                            \"PROD_PRCID\": \"BCAZ0214\",\n                            \"PROD_PRCNAME\": \"500元移动总机\",\n                            \"PRODPRCATTR_LIST\": [],\n                            \"PRODATTR_LIST\": [],\n                            \"SVCATTR_LIST\": [],\n                            \"PRODPRC\": {\n                                \"FEE_OPERATE_TYPE\": \"T\",\n                                \"OPERATE_TYPE\": \"A\",\n                                \"PARPRC_ID\": \"0\",\n                                \"RELPRCINS_ID\": \"0\",\n                                \"TRANPRCINS_ID\": \"0\",\n                                \"EFF_DATE\": \"20140821152844\",\n                                \"EXP_DATE\": \"20991231235959\",\n                                \"DEVELOP_NO\": \"aacs71\"\n                            }\n                        }\n                    }\n                ]\n            },\n            \"OPR_INFO\": {\n                \"ORDER_LINE_ID\": \"L0000000000001\",\n                \"LOGIN_ACCEPT\": \"10000000000001\",\n                \"CREATE_TIME\": \"20140922191527\",\n                \"GROUP_ID\": \"3\",\n                \"OP_NOTE\": \"3\",\n                \"OP_CODE\": \"1000\",\n                \"SYS_NOTE\": \"1000\",\n                \"SERVICE_NO\": \"13834882898\",\n                \"CUST_ID_TYPE\": \"2\",\n                \"CHANNEL_TYPE\": \"1000\",\n                \"CUST_ID_VALUE\": \"220100000000000002\",\n                \"LOGIN_NO\": \"crm600\",\n                \"BRAND_ID\": \"dn\"\n            },\n            \"COMMON_INFO\": {\n                \"CLASS_CODE\": \"2\",\n                \"PROVINCE_GROUP\": \"220000\",\n                \"SESSION_ID\": \"2\",\n                \"SER_NAME\": \"2\",\n                \"ROUND_AUDIT\": \"2\",\n                \"DEST_IP\": \"2\",\n                \"DEST_PORT\": \"2\",\n                \"SRC_PORT\": \"2\",\n                \"APP_NAME\": \"2\",\n                \"APP_ID\": \"2\",\n                \"FROM_SYS\": \"2\",\n                \"REGION_ID\": \"2\",\n                \"LOGIN_NO\": \"crm600\",\n                \"LOGIN_PWD\": \"2\",\n                \"IP_ADDRESS\": \"2\",\n                \"GROUP_ID\": \"2\"\n            }\n        }\n    }\n}";
//        MBean m = new MBean(json);
//        double retstr = m.getHeaderDouble("POOL_ID").doubleValue();
//        Map<String, Object> map = new HashMap();
//        List<String> lst = new ArrayList();
//        map.put("a", "a");
//        map.put("b", lst);
//        MBean in = new MBean();
//        in.setBody(map);
//        in.setBody("AA.BB.CC", 111);
//        System.out.println(in.getBodyInt("AA.BB.CC"));
//        in.addBody("AA.BB.CC", 111);
//        System.out.println(in.getBodyObject("AA.BB.CC"));
//        System.out.println(in.toString());
//        Long l = Long.valueOf(1111111111111111111L);
//        System.out.println(l.toString());
//    }
}
