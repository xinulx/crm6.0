package com.mine.App.mapper;

import java.util.List;
import java.util.Map;
/**
 * mapper接口
 * @author db2admin
 *
 */
public interface CommonMapper {
	/**用户菜单信息查询*/
	public List querySystemAllMenuForList(Map map);
	
	/**查询当前菜单地址*/
	public Map<String,Object> getCurrMenuUrl(String menuId);
	
	/**查询系统机构目录*/
	public List queryOrgInfoForList(Map map);
	
	/**查询系统部门目录*/
	public List queryDeptInfoForList(Map map);
	
	/**查询上级目录*/
	public Map queryOrgInfoByOrgId(String orgId);
	
	/**查询系统登录地址*/
	public String getCurrLoginUrl();

	public Integer queryOrgListCount(Map params);

	public List<Map> queryOrgListPagenation(Map map);

	public Integer queryOrgEmpListCount(Map map);

	public List<Map> queryOrgEmpListPagenation(Map map);

	public List<Map> getMenuInfoTreeList(Map map);

    void addProblem(Map params);

	List queryProblems();

    List<Map<String,Object>> querySysLogInfoList(Map params);

	Integer querySysLogInfoListCnt(Map params);

    List<Map<String,Object>> queryServLogInfoList(Map params);

	Integer queryServLogInfoListCnt(Map params);

	List<Map<String,Object>> queryUserLogInfoList(Map params);

	Integer queryUserLogInfoListCnt(Map params);

	List<Map<String,Object>> qryRoleUser(String id);
}