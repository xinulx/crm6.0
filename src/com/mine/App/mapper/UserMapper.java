package com.mine.App.mapper;

import java.util.List;
import java.util.Map;

import com.mine.App.model.User;
import com.mine.App.model.UserInfo;
/**
 * mapper接口
 * @author db2admin
 *
 */
public interface UserMapper {
	/**用户登录信息查询*/
	public UserInfo getUserInfoByUserId(Map<String, Object> map);
	
	public void updateLoginNum(String userId);
	
	public void updatePasswordErrorNum(String userId);
	
	public void emptyPasswordErrorNum(String userId);
	
	public User queryLoginUser(Map<String, Object> map);
	
	public void insertLoginLog(Map map);
	
	public Map queryRightMenu(Map map);
	
	public void updatePasswordByUser(Map map);

	public List<Map<String, List>> getList(Map map);

	List<Map<String,Object>> queryRoleList(String userId);
}