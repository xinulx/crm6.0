package com.mine.App.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.App.dao.system.MenuDao;

@Service
public class MenuService {
	private Logger logger = Logger.getLogger(EmpService.class);

	@Autowired
	private MenuDao menuDao;

	public List getMenuInfoList(String param, String type, String menuIdOrName) {
		Map<String, Object> menuItem = null;
		Map paramMap = new HashMap();
		paramMap.put("param", param);
		paramMap.put("type", type);
		paramMap.put("menuIdOrName", menuIdOrName);
		List list = this.menuDao.getMenuInfoList(paramMap);
		return list;
	}

	/**
	 * 递归菜单
	 * @param list
	 * @return
	 */
	private static List<Map<String, Object>> getMenuInfo(List<Map<String, Object>> list, String menuId) {
		// 菜单集合
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// 结果集合
		if (list != null && list.size() > 0) {
			for (Map<String, Object> menu : list) {
				if (menuId.equals(menu.get("pid").toString())) { // 判断当前菜单的父级关联编码与menuId是否相等，是则表示隶属于该节点下的子节点
					// 组装节点信息
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", menu.get("id"));
					map.put("pid", menu.get("pid"));
					map.put("name", menu.get("name"));
					// 子节点
					List<Map<String, Object>> subMenu = getMenuInfo(list, menu.get("id").toString());
					if (subMenu != null && subMenu.size() > 0) {// 如果节点下有子节点，则将子节点存入孩子节点集合
						map.put("item", subMenu);
					}
					result.add(map);
				}
			}
		}
		return result;
	}

	public Map queryMenuInfoById(String menuId) {
		return this.menuDao.queryMenuInfoById(menuId);
	}

	public Map addMenu(Map params) {
		
		return this.menuDao.addMenu(params);
	}

	public Integer delMenu(Map params) {
		return this.menuDao.delMenu(params);
	}

	public Map updateMenu(Map params) {
		return this.menuDao.updateMenu(params);
	}
}
