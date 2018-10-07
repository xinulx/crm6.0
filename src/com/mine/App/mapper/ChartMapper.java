package com.mine.App.mapper;

import java.util.List;
import java.util.Map;

public interface ChartMapper {

    /** 访问统计查询 */
    public List<Map<String,Object>> queryAccessLog(Map<String,Object> inParam);
}
