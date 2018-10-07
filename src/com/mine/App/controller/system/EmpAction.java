package com.mine.App.controller.system;

import com.mine.App.common.Base.BaseController;
import com.mine.App.common.util.DataHelper;
import com.mine.App.common.util.ExcelUtil;
import com.mine.App.common.util.StringUtils;
import com.mine.App.model.User;
import com.mine.App.service.system.EmpService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpAction extends BaseController {
	@Autowired
	private EmpService empService;

	private Logger logger=Logger.getLogger(this.getClass().getName());
     private String viewName;
     //依赖注入一个名为viewName的参数,例如一个JSP文件，作为展示model的视图
     public String getViewName (){
           return this.viewName;
     }
     public void setViewName (String viewName){
           this. viewName =viewName;
     }
     
     /**
      * 分页查询员工信息
      * @param req,res
      */
     @SuppressWarnings({ "rawtypes", "unchecked" })
	public void loadEmpList(HttpServletRequest req,
                 HttpServletResponse res) throws IOException, ServletException {
    	 res.setContentType("text/html;charset=utf-8");
    	 res.setCharacterEncoding("utf-8");
    	 req.setCharacterEncoding("utf-8");
    	 logger.info("--> 正在查询所有员工...");
         Map model = new HashMap();
         model.put("msg", "查询成功!");
         
         /**查询条件*/
         String deptNo = req.getParameter("deptNo");//机构目录树节点查询
         String empId = req.getParameter("empId");//表单查询
         String hireDate = req.getParameter("hireDate");//表单查询
         String operType = req.getParameter("operType");//表单查询
		 String qryType = req.getParameter("qryType");
         
         //分页
         int pageIndex = Integer.parseInt(req.getParameter("pageIndex"));
         int pageSize = Integer.parseInt(req.getParameter("pageSize"));        
         //字段排序
         String sortField = req.getParameter("sortField");
         String sortOrder = req.getParameter("sortOrder");
         
         //判断是否表单查询,如果是则提取历史查询状态
         if(!StringUtils.isEmpty(empId)
        		 ||!StringUtils.isEmpty(hireDate)
        		 ||"formQuery".equalsIgnoreCase(operType)){
        	 deptNo=(String) req.getSession().getAttribute("deptNo");
        	 pageIndex=(Integer) req.getSession().getAttribute("pageIndex");
        	 pageSize=(Integer) req.getSession().getAttribute("pageSize");
        	 sortField=(String) req.getSession().getAttribute("sortField");
        	 sortOrder=(String) req.getSession().getAttribute("sortOrder");
         }
         

         //将查询状态存储至session
         req.getSession().setAttribute("deptNo", deptNo);
         req.getSession().setAttribute("pageIndex", pageIndex);
         req.getSession().setAttribute("pageSize", pageSize);
         req.getSession().setAttribute("sortField", sortField);
         req.getSession().setAttribute("sortOrder", sortOrder);
         
         //封装查询参数
         model.put("pageIndex", pageIndex);
         model.put("pageSize", pageSize);
         model.put("sortField", sortField);
         model.put("sortOrder", sortOrder);
         model.put("deptNo", deptNo);
         model.put("empId", empId);
         model.put("hireDate", hireDate);
         model.put("qryType",qryType);
         
         Map rstMap =new HashMap();
         rstMap=this.empService.getEmpInfoForList(model);  
         //也可以直接返回集合
//       res.getWriter().print(JSONArrary.fromObject(rstMap));
         res.getWriter().print(JSONObject.fromObject(rstMap));
         res.getWriter().close();
     }
     /**
      * 获取随机员工串码，串码池必须指定该串码的长度为固定的15位；不足15位0填充
      * @return none
      */
     @SuppressWarnings({ "rawtypes", "unchecked" })
	public void getRandomEmpCode(HttpServletRequest req,
             HttpServletResponse res) throws IOException, ServletException{
    	logger.info("getRandomEmpCode:获取串码...");
    	try {
    		//获取登陆人的账号信息
    		User user=(User) req.getSession().getAttribute("loginUser");
    		String roleId=user.getRoleLv();
    		if(!"0".equals(roleId)){//生成通用串码
    			String empCode=this.empService.getEmpCode("userId");
    			if(empCode!=null&&empCode.length()<15){
    				int lt=empCode.length();
    				for(int i=0;i<(15-lt);i++ ){
    					empCode="0"+empCode;
    				}
    				Map map=new HashMap();
    				map.put("empCode", empCode);
    				res.getWriter().print(JSONObject.fromObject(map));
    			}else{
    				res.getWriter().print("获取串码失败！");
    			}
    		}else{//生成特殊串码：针对公司综管处以外的部门进行串码扫描
    			
    		}
		} catch (Exception e) {
			logger.info("getRandomEmpCode:获取串码失败!"+e.getMessage(),e);
			e.printStackTrace();
			res.getWriter().print("获取串码失败！");
		}finally{
			if(res.getWriter()!=null){
				res.getWriter().close();
			}
		}
     }

	/**
	 * ext分页测试
	 * @param req
	 * @param res
	 * @throws IOException
	 * @throws ServletException
	 */
	public void extPageingDemo(HttpServletRequest req,
							HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/html;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		logger.info("--> 正在查询所有员工...");
		//分页
		int pageIndex = Integer.parseInt(req.getParameter("start"));
		int pageSize = Integer.parseInt(req.getParameter("limit"));

		Map model = new HashMap();
		model.put("pageIndex", pageIndex);
		model.put("pageSize", pageSize);
		Map rstMap =new HashMap();
		rstMap=this.empService.getEmpInfoForList(model);
		rstMap.put("results",rstMap.get("total"));
		rstMap.put("rows",rstMap.get("data"));
		rstMap.put("success", true);
		res.getWriter().print(JSONObject.fromObject(rstMap));
		res.getWriter().close();
	}
     
     /**
      * 自动生成员工编码
      * @param req
      * @param res
      * @throws IOException
      * @throws ServletException
      */
     @SuppressWarnings({ "rawtypes", "unchecked" })
	public void getEmpCodeNbr(HttpServletRequest req,
             HttpServletResponse res) throws IOException, ServletException{
    	logger.info("getEmpCodeNbr 自动生成员工编码..");
    	try {
			//支取通用编码
    		String empNo=this.empService.getEmpCodeNbr("empNo");
    		if(empNo!=null&&empNo.length()<8){
				int lt=empNo.length();
				for(int i=0;i<(8-lt);i++ ){
					empNo="0"+empNo;
				}
				Map map=new HashMap();
				map.put("empNo", empNo);
				res.getWriter().print(JSONObject.fromObject(map));
			}else{
				res.getWriter().print("获取串码失败！");
			}
		} catch (Exception e) {
			logger.info("获取员工编码失败!"+e.getMessage(),e);
		}
     }
     
     /**
      * 下拉框操作公共方法
      * @param req
      * @param res
      * @throws IOException
      * @throws ServletException
      */
     @SuppressWarnings({ "rawtypes", "unchecked" })
	public void initSelectOpt(HttpServletRequest req,
             HttpServletResponse res) throws IOException, ServletException{
    	 String tableName=req.getParameter("tableName");
    	 String colunmName=req.getParameter("colunmName");
    	 String objType=req.getParameter("objType");
    	 String params=req.getParameter("params");
    	 
    	 Map paramMap=new HashMap();
    	 paramMap.put("tableName", tableName);
    	 paramMap.put("colunmName", colunmName);
    	 paramMap.put("objType", objType);
    	 paramMap.put("params", params);
    	 //此处逻辑放在sql处理，简化代码，优化性能
//    	 if("T01".equalsIgnoreCase(objType)){//查询机构
//    		 
//    	 }else if("T02".equalsIgnoreCase(objType)){//查询部门
//    		 
//    	 }
    	 List<Map> rstMap=this.empService.querySelectOptForList(paramMap);
    	 DataHelper dh=new DataHelper(req, res);
    	 dh.responseData(rstMap);
    	 dh.closeResponse();
     }
     
     /**
      * 保存新增用户信息
      * @param req
      * @param res
      * @throws IOException
      * @throws ServletException
      */
     @SuppressWarnings({ "rawtypes", "unused" })
	public void saveNewUserInfo(HttpServletRequest req,
             HttpServletResponse res) throws IOException, ServletException{
    	 try {
    		 String onj=req.getParameter("objType");
    		 Map map=new HashMap();
    		 map=req.getParameterMap();
    		 Map<String,Object> paramsMap=new HashMap<String, Object>();
    		 paramsMap.put("empCode", ((String[]) map.get("empCode"))[0]);
    		 paramsMap.put("empNo", ((String[]) map.get("empNo"))[0]);
    		 paramsMap.put("empName", ((String[]) map.get("empName"))[0]);
    		 paramsMap.put("age", ((String[]) map.get("age"))[0]);
    		 paramsMap.put("hireDate", ((String[]) map.get("hireDate"))[0]);
    		 paramsMap.put("birthDate", ((String[]) map.get("birthDate"))[0]);
    		 paramsMap.put("orgId", ((String[]) map.get("orgId"))[0]);
    		 paramsMap.put("deptNo", ((String[]) map.get("deptNo"))[0]);
    		 paramsMap.put("empDesc", ((String[]) map.get("empDesc"))[0]);
    		 paramsMap.put("gender", ((String[]) map.get("gender"))[0]);
    		 paramsMap.put("salary", ((String[]) map.get("salary"))[0]);
    		 this.empService.saveNewUserProf(paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("保存新增信息失败！"+e.getMessage(),e);
		}
    	DataHelper dh=new DataHelper(req,res);
    	dh.responseData(1);
    	dh.closeResponse();
     }
     
     /**
      * 保存修改用户信息
      * @param req
      * @param res
      * @throws IOException
      * @throws ServletException
      */
     @SuppressWarnings({ "rawtypes", "unused" })
	public void saveUpdateUserInfo(HttpServletRequest req,
             HttpServletResponse res) throws IOException, ServletException{
    	 DataHelper dh=new DataHelper(req,res);
    	 Map map1 = dh.getMapByEnu(req);
    	 Map map2 = dh.getMapByEntry(req);
    	 try {
    		 String onj=req.getParameter("objType");
    		 Map map=new HashMap();
    		 map=req.getParameterMap();
    		 Map<String,Object> paramsMap=new HashMap<String, Object>();
    		 paramsMap.put("empCode", ((String[]) map.get("empCode"))[0]);
    		 paramsMap.put("empNo", ((String[]) map.get("empNo"))[0]);
    		 paramsMap.put("empName", ((String[]) map.get("empName"))[0]);
    		 paramsMap.put("age", ((String[]) map.get("age"))[0]);
    		 paramsMap.put("hireDate", ((String[]) map.get("hireDate"))[0]);
    		 paramsMap.put("birthDate", ((String[]) map.get("birthDate"))[0]);
    		 paramsMap.put("orgId", ((String[]) map.get("orgId"))[0]);
    		 paramsMap.put("deptNo", ((String[]) map.get("deptNo"))[0]);
    		 paramsMap.put("empDesc", ((String[]) map.get("empDesc"))[0]);
    		 paramsMap.put("gender", ((String[]) map.get("gender"))[0]);
    		 paramsMap.put("salary", ((String[]) map.get("salary"))[0]);
    		 this.empService.saveUpdateUserProf(paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("保存修改信息失败！"+e.getMessage(),e);
		}
    	dh.responseData(1);
    	dh.closeResponse();
     }
     
     /**
      * 删除记录
      * @param req
      * @param res
      * @throws IOException
      * @throws ServletException
      */
     public void deleteEmpRowsForList(HttpServletRequest req,
             HttpServletResponse res) throws IOException, ServletException{
    	 DataHelper dh=new DataHelper(req,res);
    	 //编码数组
    	 String[] param=req.getParameter("paramList").split(",");
    	 try {
    		 this.empService.deleteEmpRowsForList(param);
    		 dh.responseData(1);//成功标识
    		 dh.closeResponse();
    	 } catch (Exception e) {
			e.printStackTrace();
			logger.info("删除失败！"+e.getMessage(),e);
		}
     }
     
     /**
 	 * 导出excel
 	 * @param request
 	 * @param response
 	 */
 	@SuppressWarnings({ "rawtypes" })
 	public void exportExcel(HttpServletRequest request, HttpServletResponse response){
 		try {
 			logger.info("exportExcel:Excel导出!");
 			DataHelper dh=new DataHelper(request,response);
 			Map map=(Map) dh.getMapByEntry(request);
 			//获取导出的数据
 			List<Map<String,String>> dataMap = this.empService.queryEmpInfotoExcel(map);
 			ExcelUtil excel = new ExcelUtil();
 			
 			//初始化数据
 			String title ="员工信息";
 			String [] headers={"工号","姓名","年龄","性别","入职日期","部门","机构","薪资","创建日期","描述"};
 			String [] keys ={"EMPNO","EMPNAME","AGE","GENDER","HIREDATE","DEPTNAME","ORGNAME","SALARY","CREATETIME","EMPDESC"};
 			
 			String fname="员工信息";
 			OutputStream os = response.getOutputStream();//取得输出流
 		    response.reset();//清空输出流
 		    
 		    //下面是对中文文件名的处理
 		    response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
 		    //fname = java.net.URLEncoder.encode(fname,"UTF-8");
 		    String newExportFileName = new String(fname.getBytes("GBK"), "ISO-8859-1");
 	        response.setContentType("application/vnd.ms-excel");
 	        response.setHeader("Content-Disposition","attachment; filename=\"" + newExportFileName+ ".xls\"");
// 		    response.setHeader("Content-Disposition","attachment;filename="+fname+".xls");
// 		    response.setContentType("application/msexcel");//定义输出类型
 			excel.exportExcel(title, headers,keys, dataMap,os);
 			os.flush();
 			os.close();
 		} catch (Exception e) {
 			logger.error("exportExcel:Excel导出失败!"+e.getMessage());
 		}
 	}
}