package com.mine.App.dao.system;

import com.mine.App.common.Base.IbaseDao;
import com.mine.App.mapper.AccountMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AccountDao extends IbaseDao<AccountMapper>{

    public List<Map<String,Object>> qryAccountInfo(Map params){
        return this.mapper.qryAccountInfo(params);
    }

    public Integer qryAccountInfoCount(Map params) {
        return this.mapper.qryAccountInfoCount(params);
    }

    public void delete(Map itemMap) {
        this.mapper.delete(itemMap);
    }

    public Integer selectOne(Map itemMap) {
        return this.mapper.selectOne(itemMap);
    }

    public void update(Map itemMap) {
        this.mapper.update(itemMap);
    }

    public void insert(Map itemMap) {
        this.mapper.insert(itemMap);
    }

    public List<Map<String, Object>> qryUserInfoList(String userId) {
        return this.mapper.qryUserInfoList(userId);
    }
}
