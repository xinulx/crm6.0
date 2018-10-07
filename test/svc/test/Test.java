package svc.test;

import com.mine.svc.common.util.CrossEntity;

import java.util.Map;

/**
 * Created by wangshibao on 2017/6/28.
 */
public class Test {

    public static  void main(String[] args){
        System.out.println( String.class.getName());

        // 获取微信菜单信息
        String serviceName = "http://localhost:9296/api/menu/queryMenu";
        Map<String, Object> stringObjectMap = CrossEntity.callHttpWXService(serviceName, null);
        System.out.println(stringObjectMap);
    }
}
