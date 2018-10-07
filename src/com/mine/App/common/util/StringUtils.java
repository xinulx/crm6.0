package com.mine.App.common.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    //private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
    /**
     * 解析文件路径，classpath前缀
     */
    public static final String FILE_PRE_CLASSPATH = "classpath:";

    /**
     * 解析文件路径，classpath前缀
     */
    public static final String FILE_PRE_WEBROOT = "webroot:";

    /**
     * 文件路径替换
     */
    public static final String FILE_SEPARATOR_REG = File.separator.equals("/") ? File.separator : File.separator
            + File.separator;

    /**
     * Groovy脚本请求文件包分隔符
     */
    public static final String FILE_PACKAGE_SEPARATOR = "\\.";

    /**
     * 验证字符串是否为空
     *
     * @param str 验证对象
     * @return 非空:true,null:false,空:false
     */
    public static boolean isNotEmpty(String str) {
        return str != null && str.trim().length() > 0;
    }

    /**
     * 验证字符串是否为长整数
     *
     * @param str 验证对象
     * @return 是:true,其他:false
     */
    public static boolean isLongNumber(String str) {
        try {
            Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * 验证字符串是否为数字
     *
     * @param str 验证对象
     * @return 是:true,其他:false
     */
    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String replace(String str, String[] args) {
        String result = str;
        for (int i = 0; i < args.length; i++) {
            result = result.replaceAll("\\{" + i + "\\}", args[i]);
        }

        return result;
    }

    /**
     * 验证字符串数组是否包含指定的字符串
     *
     * @param str 验证对象
     * @return 是:true,其他:false
     */
    public static boolean isContains(String str, String[] args) {
        boolean isContain = false;
        for (String string : args) {
            if (string.equals(str)) {
                isContain = true;
                break;
            }
        }
        return isContain;
    }

    /**
     * 对象为NULL时，转换为空
     *
     * @param obj 验证对象
     * @return 空对象
     */
    public static String convertEmptyWhenNull(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }

    /**
     * 验收数字是否为正数
     *
     * @param str 验证对象
     * @return 正数:true,其他:false
     */
    public static boolean isPositiveNum(String str) {
        Pattern pattern = null;
        if (str == null) {
            return false;
        }
        pattern = Pattern.compile("^\\d+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 验证两个对象是否相等
     *
     * @param obj1 对象一
     * @param obj2 对象二
     * @return 相等 true，不相等：false
     */
    public static boolean isEqual(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        } else if (obj1 == null) {
            return false;
        } else if (obj2 == null) {
            return false;
        } else {
            return obj1.equals(obj2);
        }
    }

    /**
     * 验证对象是否为空或NULl
     *
     * @param str 验证对象
     * @return 处理结果 空/Null:true,否则:false
     */
    public static boolean isEmptyOrNull(Object str) {
        if ("".equals(str) || null == str) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符串是否为空字符串或NULl
     *
     * @param str 验证对象
     * @return 处理结果 空/Null:true,否则:false
     */
    public static boolean isSpaceOrNull(String str) {
        if (null == str || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 验证对象不为空也不为NULL
     *
     * @param str 验证对象
     * @return 处理结果 空/Null:true,否则:false
     */
    public static boolean isNotEmptyOrNull(Object str) {
        if (!"".equals(str) && null != str) {
            return true;
        }
        return false;
    }

    /**
     * 验证对象是否为null
     *
     * @param str 验证对象
     * @return 处理结果 null:true,否则:false
     */
    public static boolean isNull(Object str) {
        if (null == str || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符串为数据
     *
     * @param str 字符串
     * @return 数字：true，其他：false
     */
    public static boolean isNumeric(String str) {
        if (isSpaceOrNull(str)) {
            return false;
        }
        Pattern pattern = null;
        pattern = Pattern.compile("^-?\\d+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 自定义正则表达式进行各类验证
     *
     * @param str   待验证字符串
     * @param regex
     * @return 验证通过：true, 不通过：false
     */
    public static boolean regularValid(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (!m.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 获取一个字符串的后两位的ASCII码，取每个ASCII码的最后一位作为返回字符串
     *
     * @param str
     * @return
     */
    public static String getAsciiSuffixByLastTwo(String str) {
        if (null == str || "".equals(str)) {
            return null;
        }
        if (str.length() < 2) {
            return null;
        }

        return String.valueOf(Integer.valueOf(str.charAt(str.length() - 2)) % 10)
                + String.valueOf(Integer.valueOf(str.charAt(str.length() - 1)) % 10);
    }

    /**
     * 将字符串分解
     *
     * @param str      字符串
     * @param separate 分隔符
     * @return 分隔数组
     */
    public static String[] splitToArr(String str, String separate) {
        if (isEmptyOrNull(str)) {
            return new String[0];
        } else {
            return str.split(separate);
        }
    }

    /**
     * 将java对象转换为String，对象为null时返回null
     *
     * @param value 要转换的对象
     * @return String
     */
    public static final String castToString(Object value) {

        if (value == null)
            return "";
        else
            return value.toString();
    }

    public static String objToString(Object obj) {
        if (obj == null) {
            return null;
        }
        return String.valueOf(obj).trim();
    }

    /**
     * 禁止实例化
     */
    private StringUtils() {
    }

    /**
     * @Description: TODO(按字节截取字符串)
     * @author: tongxiling
     * @version: 2015-8-4 下午01:49:58
     */
    public static String SplitString(String str, int bytes) {
        int count = 0; // 统计字节数
        StringBuffer reStr = new StringBuffer(); // 返回字符串
        if (str == null) {
            return "";
        }
        char[] tempChar = str.toCharArray();
        for (int i = 0; i < tempChar.length && count < bytes; i++) {
            String s1 = str.valueOf(tempChar[i]);
            byte[] b = s1.getBytes();
            count += b.length;
            if (count <= bytes) {
                reStr.append(tempChar[i]);
            }
        }
        return reStr.toString();
    }
}
