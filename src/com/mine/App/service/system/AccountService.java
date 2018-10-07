package com.mine.App.service.system;

import com.mine.App.common.util.Constant;
import com.mine.App.common.util.MD5;
import com.mine.App.dao.system.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    public Map<String,Object> qryAccountInfo(Map params){
        Map<String,Object> rtMap = new HashMap<String,Object>();
        List<Map<String,Object>> resultList = this.accountDao.qryAccountInfo(params);
        Integer count = this.accountDao.qryAccountInfoCount(params);
        rtMap.put("data",resultList);
        rtMap.put("total",count);
        return rtMap;
    }

    public void delete(Map itemMap) {
        this.accountDao.delete(itemMap);
    }

    public Integer selectOne(Map itemMap) {
        return this.accountDao.selectOne(itemMap);
    }

    public void update(Map itemMap) {
        itemMap.put("PASSWORD", MD5.encode(Constant.PWD_STH,itemMap.get("PASSWORD").toString()));
        this.accountDao.update(itemMap);
    }

    public void insert(Map itemMap) {
        itemMap.put("PASSWORD", MD5.encode(Constant.PWD_STH,itemMap.get("PASSWORD").toString()));
        this.accountDao.insert(itemMap);
    }

    public List<Map<String, Object>> qryUserInfoList(String userId) {
        return this.accountDao.qryUserInfoList(userId);
    }
}
