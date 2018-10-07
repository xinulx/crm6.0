package com.mine.App.dao.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mine.App.common.Base.IbaseDao;
import com.mine.App.mapper.MenuMapper;

@Repository
public class MenuDao extends IbaseDao<MenuMapper> {
	
	public List<?> getMenuInfoList(Map<?, ?> paramMap) {
		return this.mapper.getMenuInfoList(paramMap);
	}

	public Map<?, ?> queryMenuInfoById(String menuId) {
		return this.mapper.queryMenuInfoById(menuId);
	}

	public Map<String, String> addMenu(Map<String, String> params) {
		Map<String, String> rtMap = new HashMap<String, String>();
		Integer count = this.mapper.queryMenuInfoCountByCode(params);
		if(count > 0){
			rtMap.put("code", "300");
			rtMap.put("info", "菜单编码已存在！");
		}else{
			String v = (String) params.get("MENU_LVL");
			if(v.equals("0")){
				params.put("MENU_TYPE", "99");
			}else if(v.equals("1")){
				params.put("MENU_TYPE", "14");
			}else if(v.equals("2")){
				params.put("MENU_TYPE", "11");
			}else if(v.equals("3")){
				params.put("MENU_TYPE", "12");
			}
			this.mapper.addMenu(params);
			rtMap.put("code", "200");
			rtMap.put("info", "菜单保存成功！");
		}
		return rtMap;
	}

	public Integer delMenu(Map<?, ?> params) {
		return this.mapper.delMenu(params);
	}

	public Map<String, String> updateMenu(Map<String, String> params) {
		Map<String, String> rtMap = new HashMap<String, String>();
		String v = (String) params.get("MENU_LVL");
		if(v.equals("0")){
			params.put("MENU_TYPE", "99");
		}else if(v.equals("1")){
			params.put("MENU_TYPE", "14");
		}else if(v.equals("2")){
			params.put("MENU_TYPE", "11");
		}else if(v.equals("3")){
			params.put("MENU_TYPE", "12");
		}
		this.mapper.updateMenu(params);
		rtMap.put("code", "200");
		rtMap.put("info", "菜单保存成功！");
		return rtMap;
	}
	
	
}
