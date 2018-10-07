package com.mine.App.dao.system;

import java.util.List;
import java.util.Map;

import com.mine.App.common.pagenation.Pagination;
import org.springframework.stereotype.Repository;

import com.mine.App.common.Base.IbaseDao;
import com.mine.App.mapper.OrgMapper;

@Repository
public class OrgDao extends IbaseDao<OrgMapper> {
	
	public void saveNewOrgInfo(Map<String,Object> paramsMap){
		this.mapper.saveNewOrgInfo(paramsMap);
	}

	public Map qryOrgInfoByOrgId(Map params){
		return this.mapper.qryOrgInfoByOrgId(params);
	}

	public void delOrgByorgId(Map<String, Object> inMap) {
		this.mapper.delOrgByorgId(inMap);
	}

	public void updateOrgInfoByOrgId(Map map) {
		this.mapper.updateOrgInfoByOrgId(map);
	}

    public List<Map<String, Object>> qryOrgInfoAll(Map map) {
		return this.mapper.qryOrgInfoAll(map);
    }

	public Integer queryOrgDeptListCount(Map map) {
		return this.mapper.queryOrgDeptListCount(map);
	}

	public List<Map> queryOrgDeptListPagenation(Pagination pagination, Map map) {
		map.put("startNum", (pagination.getCurrentPage()-1)*pagination.getPageSize());
		map.put("pageSize", pagination.getPageSize());
		return this.mapper.queryOrgDeptListPagenation(map);
	}
}
