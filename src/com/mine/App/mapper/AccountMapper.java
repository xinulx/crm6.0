package com.mine.App.mapper;

import java.util.List;
import java.util.Map;

public interface AccountMapper {

    public List<Map<String,Object>> qryAccountInfo(Map params);

    Integer qryAccountInfoCount(Map params);

    void delete(Map itemMap);

    Integer selectOne(Map itemMap);

    void update(Map itemMap);

    void insert(Map itemMap);

    List<Map<String,Object>> qryUserInfoList(String userId);
}
