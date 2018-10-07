//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mine.App.common.exception;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ErrorCode {
    private static String ERROR_MESSAGE = "jcfmessages";
    public static final String UNKNOW = "未知异常信息";
    private static Map<String, String> errMsgs = null;
    public static final String SUCCESSS = "000000";
    public static final String ERROR = "999999";
    public static final String ERR_PARA_NULL = "900050";
    public static final String ERR_CONFIG_DB_GET_CONN = "900100";
    public static final String ERR_CONFIG_DB_SQL_EXEC = "900105";
    public static final String ERR_BEAN_NOT_FOUND = "900200";
    public static final String ERR_BEAN_UDDI_NOT_FOUND = "900210";
    public static final String ERR_BEAN_UDDI_CMD_NULL = "900211";
    public static final String ERR_BEAN_UDDI_DATA_NULL = "900212";
    public static final String ERR_BEAN_UDDI_CMD_NOPRESS = "900213";
    public static final String ERR_BEAN_UDDI_SAVE_HOST = "900214";
    public static final String ERR_BEAN_UDDI_LOAD_HOST = "900215";
    public static final String ERR_BEAN_UDDI_NOT_UDDI = "900216";
    public static final String ERR_BEAN_UDDI_IS_UDDI = "900217";
    public static final String ERR_BEAN_REMOTE_CALL = "900250";
    public static final String ERR_BEAN_NOT_FOUND_HOST = "900255";
    public static final String ERR_HTTP_INVOKE = "900300";
    public static final String ERR_HTTP_RES_OUTPUT = "900301";
    public static final String ERR_HTTP_INVOKE_CC = "900302";
    public static final String ERR_HTTP_PARA_ENCODE = "900305";
    public static final String DES_INIT_PWD_ERROR = "900400";
    public static final String HSF_ZK_NOT_HOST = "900500";
    public static final String HSF_ZK_HOST_ERROR = "900501";
    public static final String HSF_ZK_NOT_AVAILABLE_PORT = "900502";
    public static final String JAXB_PARSE = "900503";
    public static final String FILE_READ = "900504";
    public static final String CACHE_ERROR = "900505";
    public static final String I18N_LANG_ERROR = "900506";
    public static final String REDIS_ERROR = "900507";
    public static final String REDIS_CFG_ERROR = "900508";
    public static final String ERR_REFLECT_INSTANTIATION = "910001";
    public static final String ERR_REFLECT_ACCESS = "910002";
    public static final String ERR_REFLECT_ARGUMENT = "910003";
    public static final String ERR_REFLECT_INVOCATIONTARGET = "910004";
    public static final String ERR_REFLECT_INTROSPECTION = "910005";
    public static final String ERR_REFLECT_CLASS_NOT_FOUND = "910006";
    public static final String ERR_EMERG_SWITCH = "910010";
    public static final String ERR_EMERG_REBOOT = "910011";
    public static final String ERR_INVOKE_METHOD_NULL = "920010";
    public static final String ERR_INVOKE_METHOD_ACCESS = "920012";
    public static final String ERR_INVOKE_METHOD_ARGS = "920014";
    public static final String ERR_INVOKE_METHOD = "920016";
    public static final String SC_BEAN_NAME_IS_NULL = "920020";
    public static final String SC_METHOD_NAME_IS_NULL = "920022";
    public static final String SC_BEAN_NOT_FOUN_METHOD = "920024";
    public static final String SC_BEAN_NOT_FOUN = "920026";
    public static final String SC_BEAN_NOT_FOUN_DTO = "920028";
    public static final String RS_SERVICE_NMAE_IS_NULL = "920030";
    public static final String RS_NOT_FOUND_BEAN = "920032";
    public static final String SC_NOT_FOUND_BY_NAME = "920034";
    public static final String CLASS_NOT_FOUND = "921001";
    public static final String CLASS_ERROR = "921003";
    public static final String CLASS_NO_CONSTRUCTOR = "921005";
    public static final String CLASS_CONSTRUCTOR = "921007";
    public static final String CLASS_NEW_INSTANCE = "921009";
    public static final String SERVICE_TYPE_NOT_ATOM = "921050";
    public static final String SERVICE_TYPE_NOT_COMP = "921052";
    public static final String ERR_SINGLE_TABLE_DTO_NULL = "930100";
    public static final String ERR_SINGLE_TABLE_COLS_NULL = "930102";
    public static final String DB_CONNECTION_INIT = "930200";
    public static final String DB_CURR_CONN_NULL = "930201";
    public static final String SQL_EXEC_ERROR = "930202";
    public static final String SQL_EXEC_MORE_ROWS = "930204";
    public static final String DB_SQL_BUILDER = "930206";
    public static final String DB_URL_NAME = "930207";
    public static final String DB_USER_NAME = "930208";
    public static final String DB_PASSWARD = "930209";
    public static final String DB_JNDI_ERROR = "930210";
    public static final String DB_JNDI_CLOSE_ERROR = "930211";
    public static final String DB_NO_VALID_DATASOURCE = "930212";
    public static final String DB_GET_CONN_ERROR = "930213";
    public static final String DB_CLOSE_CONN_ERROR = "930214";
    public static final String DB_GET_SEQ_ERROR = "930215";
    public static final String DB_BASEDAO_CONFIG = "930216";
    public static final String DB_CHANGE_PROPERTY_NF_DS = "930230";
    public static final String DB_CHANGE_PROPERTY_DS_ERR = "930232";
    public static final String DS_NULL = "930300";
    public static final String DS_GET_CONNECTION = "930302";
    public static final String PROC_EXEC_ERROR = "930520";
    public static final String PROC_EXEC_MORE_ONE = "930522";
    public static final String PROC_EXEC_NO_OUTPUT = "930524";
    public static final String SQL_EXEC_INSERT = "931100";
    public static final String SQL_EXEC_INSERT_BATCH = "931101";
    public static final String SQL_EXEC_UPDATE = "931200";
    public static final String SQL_EXEC_UPDATE_BATCH = "931201";
    public static final String SQL_EXEC_DELETE = "931300";
    public static final String SQL_EXEC_DELETE_BATCH = "931301";
    public static final String SQL_EXEC_SELECT_SINGLE = "931400";
    public static final String SQL_EXEC_SELECT = "931401";
    public static final String SQL_EXEC_SELECT_PAGE = "931402";
    public static final String XML_PARSE_ERROR = "932000";
    public static final String JSON_PARSE_ERROR = "932001";
    public static final String TO6V_PARSE_ERROR = "932003";
    public static final String NOT_JSON_XML_ERROR = "932004";
    public static final String ROUTE_ANNON_FUN_NOTEXISTS_ERROR = "934000";
    public static final String ROUTE_ANNON_FUN_ACCESS_ERROR = "934001";
    public static final String ROUTE_ANNON_FUN_INVOKE_ERROR = "934002";

    public ErrorCode() {
    }

    public static Map<String, String> getMap(String resourceFileName) {
        ResourceBundle bundle = ResourceBundle.getBundle(resourceFileName);
        Enumeration<String> keys = bundle.getKeys();

        HashMap map;
        String key;
        String value;
        for(map = new HashMap(64); keys.hasMoreElements(); map.put(key, value)) {
            key = (String)keys.nextElement();
            value = bundle.getString(key);
            if(value == null) {
                value = "";
            } else {
                try {
                    value = new String(value.getBytes("ISO8859-1"), "GBK");
                } catch (UnsupportedEncodingException var7) {
                    value = "";
                }
            }
        }

        return map;
    }

    public static String mm(String code) {
        String str = (String)errMsgs.get(code);
        return str == null?"":str;
    }

    public static String mm(String code, String... args) {
        String str = (String)errMsgs.get(code);
        return str == null?"":MessageFormat.format(str, args);
    }

    public static BaseException ne(Class<? extends BaseException> c, String code) {
        String msg = mm(code);

        try {
            BaseException e = (BaseException)c.getConstructor(new Class[]{String.class, String.class}).newInstance(new Object[]{code, msg});
            return e;
        } catch (Throwable var4) {
            return new BaseException(code, msg);
        }
    }

    public static BaseException ne(Class<? extends BaseException> c, Throwable throwable, String code) {
        String msg = mm(code);

        try {
            BaseException e = (BaseException)c.getConstructor(new Class[]{String.class, String.class, Throwable.class}).newInstance(new Object[]{code, msg, throwable});
            return e;
        } catch (Throwable var5) {
            return new BaseException(code, msg);
        }
    }

    public static BaseException ne(Class<? extends BaseException> c, String code, String... args) {
        String msg = mm(code, args);

        try {
            BaseException e = (BaseException)c.getConstructor(new Class[]{String.class, String.class}).newInstance(new Object[]{code, msg});
            return e;
        } catch (Throwable var5) {
            return new BaseException(code, msg);
        }
    }

    public static BaseException ne(Class<? extends BaseException> c, Throwable throwable, String code, String... args) {
        String msg = mm(code, args);

        try {
            BaseException e = (BaseException)c.getConstructor(new Class[]{String.class, String.class, Throwable.class}).newInstance(new Object[]{code, msg, throwable});
            return e;
        } catch (Throwable var6) {
            return new BaseException(code, msg);
        }
    }

    public static DTO newDTO(String code) {
        String msg = mm(code);
        if("".equals(msg)) {
            msg = "未知异常";
        }

        return new DTO(code, msg);
    }

    public static <T extends DTO> T newDTO(String code, T t) {
        String msg = mm(code);
        if("".equals(msg)) {
            msg = "未知异常";
        }

        t.setRtnCode(code);
        t.setRtnMsg(msg);
        return t;
    }

    public static DTO press(Throwable e) {
        return press(e, false);
    }

    public static DTO press(Throwable e, boolean print) {
        if(print) {
            e.printStackTrace();
        }

        if(e instanceof JCFThrowable) {
            return new DTO(((JCFThrowable)e).getErrCode(), ((JCFThrowable)e).getErrMsg(), ((JCFThrowable)e).getErrDtlMsg());
        } else {
            for(Throwable c = e.getCause(); c != null; c = c.getCause()) {
                if(c instanceof JCFThrowable) {
                    return new DTO(((JCFThrowable)c).getErrCode(), ((JCFThrowable)c).getErrMsg(), ((JCFThrowable)c).getErrDtlMsg());
                }
            }

            return new DTO("999999", e.getMessage());
        }
    }

    static {
        errMsgs = getMap(ERROR_MESSAGE);
    }
}
