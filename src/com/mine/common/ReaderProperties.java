package com.mine.common;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description: (读取文件中的测试用例)
 * @author: wangshibao
 * @version: 2014-8-29 下午02:32:33
 */

public final class ReaderProperties {
    
    public static Map getParam(String propertieName) {
        Properties prop = new Properties();
        InputStream in = Object.class.getResourceAsStream("/system/" + propertieName);
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>((Map) prop);
        return map;
    }

    /**
     *
     * @Description: (本方法用来获取存到txt文件中的json报文)
     * @author: tongxiling
     * @version: 2014-9-28 下午03:35:06
     */
    public static String getInputJson(String fileName) {
        File file = new File(fileName);
        StringBuffer sbuf = new StringBuffer();
        try {
            FileReader r = new FileReader(file);
            BufferedReader in = new BufferedReader(r);
            String s;
            while ((s = in.readLine()) != null){
                sbuf.append(s);
            }
            in.close();
            System.out.println(">>>>>>>>>>>>>" + sbuf.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sbuf.toString();
    }

    public static void main(String args[]) {
        System.out.println(getParam("app.properties").toString());
        System.out.println("------------------------------------------------");
        String Str = ReaderProperties.getInputJson("static/json/test.json");
        System.out.println("=======入参=======:" + Str);
    }
}
