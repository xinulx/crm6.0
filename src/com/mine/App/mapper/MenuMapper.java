package com.mine.App.mapper;

import java.util.List;
import java.util.Map;
/**
 * mapper接口
 * @author db2admin
 *
 */
public interface MenuMapper {

	List getMenuInfoList(Map paramMap);

	Map queryMenuInfoById(String menuId);

	Integer queryMenuInfoCountByCode(Map params);

	void addMenu(Map params);

	Integer delMenu(Map params);

	void updateMenu(Map params);
	
}