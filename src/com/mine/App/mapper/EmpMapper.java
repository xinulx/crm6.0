package com.mine.App.mapper;

import java.util.List;
import java.util.Map;
/**
 * mapper接口
 * @author db2admin
 *
 */
public interface EmpMapper {
	/**系统员工信息查询*/
	public List getEmpInfoForList(Map paramMap);
	
	/**系统员工信息查询总数*/
	public Integer getEmpInfoForListCnt(Map paramMap);
	
	/**生成通用员工随机串码*/
	public String getEmpCode(String userId);
	
	/**自动生成员工编码*/
	public String getEmpCodeNbr(String empNo);
	
	/**查询下拉菜单方法*/
	public List<Map> querySelectOptForList(Map paramMap);
	
	/**保存新增员工信息*/
	public void saveNewUserProf(Map<String,Object> paramsMap);
	
	/**删除员工*/
	public void deleteEmpRowsForOne(String empNo);
	
	/**修改员工基本信息*/
	public void saveUpdateUserProf(Map<String, Object> paramsMap);
	
	/**excel导出查询*/
	public List<Map<String, String>> queryEmpInfotoExcel(Map map);
}