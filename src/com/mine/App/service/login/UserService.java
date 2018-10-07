package com.mine.App.service.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mine.App.common.util.Constant;
import com.mine.App.common.util.MD5;
import com.mine.App.dao.login.UserDao;
import com.mine.App.model.User;
import com.mine.App.model.UserInfo;

@Service
public class UserService implements Constant{
	
	@Autowired
	private UserDao userDao;

    public Map getUserInfo(String userId, String orgId, String roleId){
        //查询当前登录用户信息
        Map<String,Object> map=new HashMap<String, Object>();
        Map<String,Object> inmap=new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("orgId", orgId);
        map.put("roleId", roleId);
        UserInfo user=this.userDao.getUserInfoByUserId(map);
        User loginUser=this.userDao.queryLoginUser(map);
        map.put("loginUser", loginUser);
        map.put("user", user);
        return map;
    }

	public Map getUserInfoByUserId(String userId,String password){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", "用户名");
		map.put("id", "00000000");
        map.put("userId",userId);
		List<Map<String, List>> list = userDao.getList(map);
		if(userId!=null&&!userId.trim().equals("")){
			UserInfo user=this.userDao.getUserInfoByUserId(map);
			if(user!=null){
				if(user.getPwdErrorNum() >= 3){
					//更新登录次数
					this.userDao.updateLoginNum(userId);
					map.put("msg", MORE_LOGINNUM);
					map.put("flag", LOGIN_FAIL);
					return map;
				}
				if(user.getPassword().equals(MD5.encode(PWD_STH, password))){
					//清空密码错误次数
					this.userDao.emptyPasswordErrorNum(userId);
					List<Map<String,Object>> roleList=this.userDao.queryRoleList(userId);
					if(roleList == null || roleList.size() == 0){
						map.put("flag", LOGIN_FAIL);
						map.put("msg",USER_AUTHEN);
					}else{
                        //存储当前登录用户信息
                        map.put("msg", LOGIN_SUCCESS);
                        map.put("loading", LOADING_INFO);
                        map.put("roleList", roleList);
					}
				}else{
					//更新密码错误次数
					this.userDao.updatePasswordErrorNum(userId);
					map.put("flag", LOGIN_FAIL);
					map.put("msg", PASSWORD_ERROR);
				}
			}else{
				map.put("flag", LOGIN_FAIL);
				map.put("msg", USERNAME_ERROR);
			}
		}else{
			map.put("flag", LOGIN_FAIL);
			map.put("msg", EMPTY_USERNAME);
		}
		//更新登录次数
		this.userDao.updateLoginNum(userId);
		return map;
	}
	
	public void insertLoginLog(Map map){
		this.userDao.insertLoginLog(map);
	}
	
	/**
	 * 获取当前菜单路径
	 */
	public Map getUrlPath(String menu_id,String menu_type){
		Map paramsMap=new HashMap();
		if(menu_type!=null&&menu_id!=null){
			paramsMap.put("menu_type", menu_type);
			paramsMap.put("menu_id", menu_id);
		}else{
			paramsMap.put("error",MENU_ERROR);
			return paramsMap;
		}
		Map rstMap=this.userDao.queryIndexRightPath(paramsMap);
		if(rstMap!=null){
			//获取菜单信息成功
			String path=(String) rstMap.get("MENU_URL");
			paramsMap.put("msg", MENU_MSG);
			paramsMap.put("path", path);
		}else{
			paramsMap.put("msg", MENU_QUERYERROR);
		}
		return paramsMap;
	}
	
	/**
	 * 密码修改
	 * @param returnMap
	 * @param rstMap
	 * @return
	 */
	public Map excuteQueryByUser(Map returnMap,Map rstMap){
		String userId=(String) rstMap.get("userId");
		String old_password=(String) rstMap.get("old_password");
		String password=(String) rstMap.get("password");
		String final_password=(String) rstMap.get("final_password");
		String code=(String) rstMap.get("code");
		String sessionCode=(String) rstMap.get("sessionCode");
		
		Map msgMap=new HashMap();
		if(userId==null||userId.equals("")){
			msgMap.put("msg", EMPTY_USERID);
			return msgMap;
		}
		if(old_password==null||old_password.equals("")){
			msgMap.put("msg", EMPTY_OLD_PASSWORD);
			return msgMap;
		}
		if(password==null||password.equals("")){
			msgMap.put("msg", EMPTY_NEW_PASSWORD);
			return msgMap;
		}
		if(final_password==null||final_password.equals("")){
			msgMap.put("msg", EMPTY_FINAL_PASSWORD);
			return msgMap;
		}
		if(code==null||code.equals("")){
			msgMap.put("msg", EMPTY_CODE);
			return msgMap;
		}
		if(sessionCode==null||sessionCode.equals("")){
			msgMap.put("msg", TIMEOUT_SESSIONCODE);
			return msgMap;
		}
		Map map = new HashMap();
        map.put("userId",userId);
		UserInfo user=this.userDao.getUserInfoByUserId(map);
		if(user==null){
			msgMap.put("msg", ERROR_USERID);
			return msgMap;
		}
		if(!user.getPassword().equals(MD5.encode(PWD_STH, old_password))){
			msgMap.put("msg", ERROR_OLD_PASSWORD);
			return msgMap;
		}
		if(old_password.equals(password)){
			msgMap.put("msg", SAME_PWD);
			return msgMap;
		}
		if(!final_password.equals(password)){
			msgMap.put("msg", NOSAME_PWD);
			return msgMap;
		}
		if(!code.equals(sessionCode)){
			msgMap.put("msg", ERROR_CODE);
			return msgMap;
		}
		if(user.getPwdErrorNum()>=3){
			msgMap.put("msg", "该账号已被锁定，请联系管理员修改密码！");
			return msgMap;
		}
		returnMap.put("password", MD5.encode(PWD_STH, password));
		returnMap.put("old_password", MD5.encode(PWD_STH, old_password));
		this.userDao.updatePasswordByUser(returnMap);
		if(true){
			msgMap.put("msg", PWDMODI_SUCCESS);
			msgMap.put("flag", "OK");
		}
		
		return msgMap;
	}
	
	public List<Map<String,List>> getList(Map map){
		return this.userDao.getList(map);
	}
}
