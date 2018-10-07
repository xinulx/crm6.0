package com.mine.App.mapper;

import java.util.List;
import java.util.Map;

/**
 * mapper接口
 * @author db2admin
 *
 */
public interface RoleMapper {
	public Integer queryRoleListCount(Map params);

	public List<Map> queryRoleListPagenation(Map map);

	public void addRole(Map params);

	Map qryRoleInfoById(Map params);

    void editRole(Map params);

	Integer qryRoleByIdOfEff(Map params);

	void delRoleById(Map params);

    List<Map<String,Object>> qryUserOfNoRole(Map params);

	void saveUserRoleInfo(Map params);

    List<Map<String,Object>> qryRoleInfoByOrgId(Map params);

	Integer qryRoleByRoleCode(Map params);

    List<Map<String,Object>> qryRoleInfoByUserId(String userId);

	int deleteGroupRoleById(Map<Object, Object> params);

    int updateRoleOrgId(Map map);

    List<Map<String,Object>> queryAllRole(Map map);

	List<Map<String,Object>> queryAllRoleUser(Map map);
}