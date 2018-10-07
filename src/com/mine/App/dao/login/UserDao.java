package com.mine.App.dao.login;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mine.App.common.Base.IbaseDao;
import com.mine.App.mapper.UserMapper;
import com.mine.App.model.User;
import com.mine.App.model.UserInfo;

@Repository
public class UserDao extends IbaseDao<UserMapper> {
	
	private Logger logger=Logger.getLogger(UserDao.class);
	
	public UserInfo getUserInfoByUserId(Map<String, Object> map){
		return this.mapper.getUserInfoByUserId(map);
	}
	
	public void updateLoginNum(String userId){
		this.mapper.updateLoginNum(userId);
	}
	
	public void updatePasswordErrorNum(String userId){
		this.mapper.updatePasswordErrorNum(userId);
	}
	
	public void emptyPasswordErrorNum(String userId){
		this.mapper.emptyPasswordErrorNum(userId);
	}
	
	public User queryLoginUser(Map<String, Object> map){
		return this.mapper.queryLoginUser(map);
	}
	
	public void insertLoginLog(Map map){
		this.mapper.insertLoginLog(map);
	}
	
	public Map queryIndexRightPath(Map map){
		return this.mapper.queryRightMenu(map);
	}
	
	public void updatePasswordByUser(Map map){
		this.mapper.updatePasswordByUser(map);
	}
	
	public List<Map<String,List>> getList(Map map){
		return this.mapper.getList(map);
	}

	public List<Map<String, Object>> queryRoleList(String userId) {
		return this.mapper.queryRoleList(userId);
	}
}
