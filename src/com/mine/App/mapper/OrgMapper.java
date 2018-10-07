package com.mine.App.mapper;

import java.util.List;
import java.util.Map;
/**
 * mapper接口
 * @author db2admin
 *
 */
public interface OrgMapper {
	
	/**保存新增机构信息*/
	public void saveNewOrgInfo(Map<String,Object> paramsMap);

	/**查询机构信息*/
	public Map qryOrgInfoByOrgId(Map params);

	/**删除机构信息*/
    void delOrgByorgId(Map<String, Object> inMap);

    void updateOrgInfoByOrgId(Map map);

    List<Map<String,Object>> qryOrgInfoAll(Map map);

    Integer queryOrgDeptListCount(Map map);

	List<Map> queryOrgDeptListPagenation(Map map);
}