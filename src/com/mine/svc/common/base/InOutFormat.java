//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mine.svc.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.dom4j.DocumentException;

public class InOutFormat {
    public static final String BODY = "BODY";
    public static final String HEADER = "HEADER";
    public static final String TRACE_ID = "TRACE_ID";
    public static final String CALL_ID = "CALL_ID";
    public static final String KEEP_LIVE = "KEEP_LIVE";
    public static final String CLIENT_IP = "CLIENT_IP";
    private static Logger log = Logger.getLogger(InOutFormat.class);

    public InOutFormat() {
    }

    public static InOutFormat.ParaType getInOutType(String in) {
        return in.charAt(0) == 60 ? InOutFormat.ParaType.XML : (in.charAt(0) == 123 ? (in.indexOf("\"HEADER\":") > 0 && in.indexOf("\"BODY\":") > 0 ? InOutFormat.ParaType.JSON6V : InOutFormat.ParaType.JSON5V) : InOutFormat.ParaType.UNKNOWN);
    }

    public static String formatIn(String instr, InOutFormat.ParaType type) {
        String json6v = null;

        try {
            if (type == InOutFormat.ParaType.XML) {
                json6v = to6VJson(JXml.toJson(instr));
            } else if (type == InOutFormat.ParaType.JSON5V) {
                json6v = to6VJson(instr);
            } else if (type == InOutFormat.ParaType.JSON6V) {
                json6v = instr;
            }
        } catch (DocumentException var4) {
            throw new RuntimeException("解析XML入参失败,XML格式不正确！");
        } catch (Exception var5) {
            throw new RuntimeException(var5.getMessage());
        }

        if (json6v == null) {
            throw new RuntimeException("解析为6系格式失败");
        } else {
            return json6v;
        }
    }

    public static String formatIn(String in) {
        try {
            return formatIn(in, getInOutType(in));
        } catch (Exception var2) {
            log.error("formatIn error:", var2);
            return null;
        }
    }

    public static String formatOutNoError(String in, InOutFormat.ParaType outType) {
        try {
            return formatOut(in, outType);
        } catch (Throwable var3) {
            log.error("formatOutNoError error:", var3);
            return outType == InOutFormat.ParaType.JSON5V ? "{\"ROOT\":{\"RETURN_CODE\":\"10010020003000002\",\"RETURN_MSG\":\"convert to 5v json error\",\"PROMPT_MSG\":\"convert to 5v json error\",\"USER_MSG\":\"参数转换出错\",\"DETAIL_MSG\":\"convert to 5v json error\"}}" : (outType == InOutFormat.ParaType.JSON6V ? "{\"ROOT\":{\"BODY\":{\"RETURN_CODE\":\"10010020003000001\",\"RETURN_MSG\":\"convert to 6v json error\",\"USER_MSG\":\"参数转换出错\",\"DETAIL_MSG\":\"convert to 6v json error\",\"PROMPT_MSG\":\"convert to 6v json error\"}}}" : (outType == InOutFormat.ParaType.XML ? "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><RETURN_CODE type=\"string\">10010020003000003</RETURN_CODE><RETURN_MSG type=\"string\">convert to xml error</RETURN_MSG><PROMPT_MSG type=\"string\">convert to xml error</PROMPT_MSG><USER_MSG type=\"string\">参数转换出错</USER_MSG><DETAIL_MSG type=\"string\">convert to xml error</DETAIL_MSG></ROOT>" : null));
        }
    }

    public static String formatOut(String in, InOutFormat.ParaType outType) throws Exception {
        if (outType == InOutFormat.ParaType.JSON5V) {
            return to5VJson(in);
        } else if (outType == InOutFormat.ParaType.JSON6V) {
            return in;
        } else if (outType == InOutFormat.ParaType.XML) {
            return JXml.toXml(to5VJson(in));
        } else {
            throw new RuntimeException("不能转换非XML和JSON类型");
        }
    }

    public static String to5VJson(String inJson) {
        JSONObject json = JSONObject.parseObject(inJson);
        JSONObject jsonRoot = (JSONObject) json.get("ROOT");
        JSONObject jsonBody = (JSONObject) jsonRoot.get("BODY");
        JSONObject jsonObject5v = new JSONObject(true);
        jsonObject5v.put("ROOT", jsonBody);
        SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect};
        return JSON.toJSONString(jsonObject5v, features);
    }

    public static String to6VJson(String inJson) {
        return to6VJson((JSONObject) JSONObject.parse(inJson));
    }

    public static String to6VJson(JSONObject inJson) {
        Set<String> keys = inJson.keySet();
        if (keys.size() != 1) {
            log.error(inJson + "缺少跟节点，无法转换为6系json");
            return null;
        } else {
            String jsonRoot = (String) keys.iterator().next();
            JSONObject jsonObjectRoot = (JSONObject) inJson.get(jsonRoot);
            if (jsonObjectRoot.size() == 2 && jsonObjectRoot.containsKey("HEADER") && jsonObjectRoot.containsKey("BODY") && "ROOT".equals(jsonRoot)) {
                return inJson.toString();
            } else {
                JSONObject jsonObjectHeader = (JSONObject) inJson.get("HEADER");
                if (jsonObjectHeader == null) {
                    jsonObjectHeader = new JSONObject(true);
                }

                Object objectCommInfo = jsonObjectRoot.get("COMMON_INFO");
                JSONObject outJson;
                if (objectCommInfo != null && objectCommInfo instanceof JSONObject) {
                    outJson = (JSONObject) objectCommInfo;
                    jsonObjectHeader.put("TRACE_ID", outJson.get("TRACE_ID"));
                    jsonObjectHeader.put("PARENT_CALL_ID", outJson.get("CALL_ID"));
                    jsonObjectHeader.put("KEEP_LIVE", outJson.get("KEEP_LIVE"));
                    jsonObjectHeader.put("CLIENT_IP", outJson.get("CLIENT_IP"));
                }

                inJson.remove(jsonRoot);
                inJson.put("BODY", jsonObjectRoot);
                inJson.put("HEADER", jsonObjectHeader);
                outJson = new JSONObject(true);
                outJson.put("ROOT", inJson);
                SerializerFeature[] features = new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect};
                return JSON.toJSONString(outJson, features);
            }
        }
    }

    public static enum ParaType {
        XML,
        JSON5V,
        JSON6V,
        UNKNOWN;
        private ParaType() {}
    }

    public static void main(String[] args) throws Exception {
        String xmlStr = "<a>a</a>";
        String json = InOutFormat.formatIn(xmlStr, ParaType.XML);
        System.out.println(json);
        String str = InOutFormat.formatIn(xmlStr);
        System.out.println(str);
        String xml = InOutFormat.formatOut("{\"ROOT\":{\"BODY\":{\"a\":\"a\"},\"HEADER\":{}}}",ParaType.JSON6V);
        System.out.println(xml);
        // 系统5V没有BODY节点
        String XML = InOutFormat.formatOut("{\"ROOT\":{\"BODY\":{\"a\":\"a\"},\"HEADER\":{}}}",ParaType.JSON5V);
        System.out.println("HEHE-------------------"+XML);
        JSONObject obj = (JSONObject) JSONObject.parse(json);
        // 6系方法入参为JSONObject
        System.out.println(InOutFormat.to6VJson(obj));
        System.out.println("--------------test end------------------");
    }
}
