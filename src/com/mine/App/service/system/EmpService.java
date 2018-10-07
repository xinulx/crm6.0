package com.mine.App.service.system;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.App.common.util.Constant;
import com.mine.App.dao.system.EmpDao;

@Service
public class EmpService implements Constant{
	private Logger logger=Logger.getLogger(EmpService.class);

	@Autowired
	private EmpDao empDao;

	public Map getEmpInfoForList(Map model){
		logger.info("查询员工信息...");
		try{
			Map<String,Object> menuItem=null;

			Map paramMap=new HashMap();
			Integer index=(Integer) model.get("pageIndex");
			Integer size=(Integer) model.get("pageSize");
			if(model.get("qryType") !=null){
				paramMap.put("qryType",model.get("qryType"));
			}
			if(index>0){
				paramMap.put("index", index*size);//每页开始行号
			}else{
				paramMap.put("index", 0);//每页开始行号
			}
			paramMap.put("pageSize", size);//每页开始行号
			// paramMap.put("sortField", model.get("sortField").toString());//排序字段
			// paramMap.put("sortOrder", model.get("sortOrder").toString());//排序方式：降序、升序

			if(model.get("deptNo")!=null){
				paramMap.put("deptNo", model.get("deptNo").toString());
			}else{
				paramMap.put("deptNo", null);//排序方式：降序、升序
			}

			if(model.get("empId")!=null&&!"".equals(model.get("empId"))){
				paramMap.put("empId", model.get("empId").toString());
			}else{
				paramMap.put("empId", null);
			}

			if(model.get("hireDate")!=null&&!"".equals(model.get("hireDate"))){
				paramMap.put("hireDate", model.get("hireDate").toString());
			}else{
				paramMap.put("hireDate", null);//排序方式：降序、升序
			}

			List empList=this.empDao.getEmpInfoForList(paramMap);

			List rstList=new ArrayList();
			for(Object m:empList){
				Map map=(Map)m;
				menuItem=new HashMap();
				menuItem.put("EMPNO", map.get("EMPNO"));
				menuItem.put("EMPNAME", map.get("EMPNAME"));
				menuItem.put("AGE", map.get("AGE"));
				menuItem.put("GENDER", map.get("GENDER"));
				menuItem.put("HIREDATE", map.get("HIREDATE"));
				menuItem.put("DEPTNO", map.get("DEPTNO"));
				menuItem.put("DEPTNAME", map.get("DEPTNAME"));
				menuItem.put("ORGID", map.get("ORGID"));
				menuItem.put("ORGNAME", map.get("ORGNAME"));
				menuItem.put("BIRTHDATE", map.get("BIRTHDATE"));
				menuItem.put("SALARY", map.get("SALARY"));
				menuItem.put("EMPDESC", map.get("EMPDESC"));
				menuItem.put("VFLAG", (String)map.get("VFLAG")=="1"?"无效":"有效");
				menuItem.put("CREATETIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)map.get("CREATETIME")));

				rstList.add(menuItem);
			}
			//查询员工总数
			Integer rstListCnt=this.empDao.getEmpInfoForListCnt(paramMap);
			HashMap result = new HashMap();
			result.put("data", rstList);
			result.put("total", rstListCnt);
			return result;
		}catch (Exception e) {
			logger.info("查询员工信息失败..."+e.getMessage()+e);
			return null;
		}
	}

	public String getEmpCode(String userId){
		return this.empDao.getEmpCode(userId);
	}

	public String getEmpCodeNbr(String empNo){
		return this.empDao.getEmpCodeNbr(empNo);
	}

	public List<Map> querySelectOptForList(Map paramMap){
		try {
			return this.empDao.querySelectOptForList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveNewUserProf(Map<String,Object> paramsMap){
		this.empDao.saveNewUserProf(paramsMap);
	}

	public void deleteEmpRowsForList(String[] param){
		if(param!=null){
			for(String empNo:param){
				this.empDao.deleteEmpRowsForOne(empNo);
			}
		}
	}

	public void saveUpdateUserProf(Map<String, Object> paramsMap) {
		this.empDao.saveUpdateUserProf(paramsMap);
	}

	public List<Map<String, String>> queryEmpInfotoExcel(Map map) {
		return this.empDao.queryEmpInfotoExcel(map);
	}
}
