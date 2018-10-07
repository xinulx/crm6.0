package com.mine.App.dao.chart;

import com.mine.App.common.Base.IbaseDao;
import com.mine.App.mapper.ChartMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ChartDao extends IbaseDao<ChartMapper> {

    public List<Map<String,Object>> queryAccessLog(Map<String,Object> inParam){
        return this.mapper.queryAccessLog(inParam);
    }
}
