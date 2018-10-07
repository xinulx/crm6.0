package com.mine.App.dao.system;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.mine.App.common.Base.IbaseDao;
import com.mine.App.mapper.EmpMapper;

@Repository
public class EmpDao extends IbaseDao<EmpMapper> {
	
	private Logger logger=Logger.getLogger(EmpDao.class);
	
	public List<?> getEmpInfoForList(Map<?, ?> paramMap){
		logger.info("正在查询员工信息...");
		List<?> list=this.mapper.getEmpInfoForList(paramMap);
		return list;
	}
	
	public Integer getEmpInfoForListCnt(Map<?, ?> map){
		logger.info("正在查询员工信息总数...");
		Integer listCnt=this.mapper.getEmpInfoForListCnt(map);
		return listCnt;
	}
	
	public String getEmpCode(String userId){
		return this.mapper.getEmpCode(userId);
	}
	
	public String getEmpCodeNbr(String empNo){
		return this.mapper.getEmpCodeNbr(empNo);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> querySelectOptForList(Map<?, ?> paramMap){
		return this.mapper.querySelectOptForList(paramMap);
	}
	
	public void saveNewUserProf(Map<String,Object> paramsMap){
		this.mapper.saveNewUserProf(paramsMap);
	}
	
	public void deleteEmpRowsForOne(String empNo){
		this.mapper.deleteEmpRowsForOne(empNo);
	}

	public void saveUpdateUserProf(Map<String, Object> paramsMap) {
		this.mapper.saveUpdateUserProf(paramsMap);
	}

	public List<Map<String, String>> queryEmpInfotoExcel(Map<?, ?> map) {
		return this.mapper.queryEmpInfotoExcel(map);
	}
}
