package com.mine.App.service;

import com.mine.App.common.pagenation.Pagination;
import com.mine.App.common.util.Constant;
import com.mine.App.dao.CommonDao;
import com.mine.svc.common.base.JXml;
import com.mine.svc.common.base.MBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonService implements Constant{
	private Logger logger=Logger.getLogger(CommonService.class);
	
	@Autowired
	private CommonDao commonDao;
	
	public List getMenuInfoForList(String param,String type){
		Map<String,Object> menuItem=null;
		Map paramMap=new HashMap();
		paramMap.put("param", param);
		paramMap.put("type", type);
		List menuList=this.commonDao.getMenuInfoList(paramMap);
		
		List rstList=new ArrayList();
		for(Object m:menuList){
			Map map=(Map)m;
			menuItem=new HashMap();
			menuItem.put("id", map.get("MENU_ID"));
			menuItem.put("text", map.get("MENU_NAME"));
			String pid=(String) map.get("MENU_PARENT_ID");
			if(pid!=null&&!pid.equals("000000")&&!pid.equals("")){
				menuItem.put("pid", pid);
			}
			rstList.add(menuItem);
		}
		return rstList;
	}

	public List getOrgInfoForTreeList(String param,String type,String orgIdOrName){
		List<Map<String,Object>>  treeList= new ArrayList<Map<String,Object>>();
		Map paramMap=new HashMap();
		paramMap.put("param", param);
		paramMap.put("type", type);
		paramMap.put("orgIdOrName", orgIdOrName);
		treeList=this.commonDao.getMenuInfoTreeList(paramMap);
		return treeList;
	}

	public List getOrgInfoForList(String param,String type,String orgIdOrName,String userRole){
		Map<String,Object> menuItem=null;
		Map paramMap=new HashMap();
		paramMap.put("param", param);
		paramMap.put("type", type);
		paramMap.put("orgIdOrName", orgIdOrName);
		//查询一级、二级机构信息目录
		List orgList=this.commonDao.getOrgInfoList(paramMap);
		List<Map<String,Object>> rstList=new ArrayList<>();
		if(orgList.size()==0){
			logger.info("没有查询到机构信息");
		}else{
			//生成1级和二级目录
			for(Object m:orgList){
				Map map=(Map)m;
				menuItem=new HashMap();
				menuItem.put("id", map.get("ORGID"));
				menuItem.put("name", map.get("ORGNAME"));
				String lv=(String) map.get("LEVEL");
				if(lv.equals("2")){
					menuItem.put("pid", "JG001001");//目录树最顶级
				}else if(lv.equals("1")){
					menuItem.put("expanded", true);//展开根节点
				}
				
				rstList.add(menuItem);
			}
		}
		
		//查询机构下所有部门
		List deptList=this.commonDao.getDeptInfoList(paramMap);
		if(deptList.size()==0){
			logger.info("没有查询到部门信息");
		}else{
			//生成三级目录,二级特殊
			for(Object m:deptList){
				Map map=(Map)m;
				menuItem=new HashMap();
				menuItem.put("id", map.get("DEPTNO"));
				menuItem.put("name", map.get("DEPTNAME"));
				menuItem.put("pid", map.get("ORGID"));
				
				rstList.add(menuItem);
				
				//存在的三级目录需要将其父级目录也包含在集合中，这里先去查询
				Map currOrg=this.commonDao.getOrgInfoByOrgId((String)map.get("ORGID"));
				
				Map menuItem2=new HashMap();
				menuItem2.put("id", currOrg.get("ORGID"));
				menuItem2.put("name", currOrg.get("ORGNAME"));
				menuItem2.put("pid", "JG001001");

				if(!"JG001001".equals(currOrg.get("ORGID")) && !rstList.contains(menuItem2)){
					//如果当前机构已经不包含，则重新生成父级目录
					rstList.add(menuItem2);
				}

			}
		}
		
		//没有查询到父级目录但是查询到了子集目录，将根节点插入集合
		if(orgList.size()==0 || deptList.size()==0){
			Map menuItem3=new HashMap();
			menuItem3.put("id", "JG001001");
			menuItem3.put("name", "宝通信息技术有限公司");
			menuItem3.put("expanded", true);
			if(!rstList.contains(menuItem3)){
				rstList.add(menuItem3);
			}
			
		}

		// 或取所有员工
		if("1".equals(userRole)){
            List<Map<String,Object>> roleUserTemp=new ArrayList<>();//需要添加的节点
            List<Map<String,Object>> roleUserRemove=new ArrayList<>();// 需要移除的节点
            for(Map<String,Object> map : rstList){
                if(map.get("id").toString().contains("BM")){
                    List<Map<String,Object>> roleUser=new ArrayList<>();
                    roleUser = this.commonDao.qryRoleUser(map.get("id").toString());
                    for(Map<String,Object> user : roleUser){
                        Map userTemp=new HashMap();
                        userTemp.put("pid",map.get("id").toString());
                        userTemp.put("id","ROLE_USER-"+user.get("EMPNO"));
                        userTemp.put("name",user.get("EMPNAME"));
                        roleUserTemp.add(userTemp);
                    }
                    if(roleUser.size() == 0){
                        roleUserRemove.add(map);
                    }
                }else{
                    // 移除节点
                    roleUserRemove.add(map);
                }
            }
            rstList.addAll(roleUserTemp);
            rstList.removeAll(roleUserRemove);
        }
		return rstList;
	}
	
	public Map<String,Object> getCurrMenuUrl(String menuId){
		return this.commonDao.getCurrMenuUrl(menuId);
	}
	
	public String getCurrLoginUrl(){
		return this.commonDao.getCurrLoginUrl();
	}

	public Integer queryOrgListCount(Map params) {
		return this.commonDao.queryOrgListCount(params);
	}

	public List<Map> queryOrgListPagenation(Pagination pagination,Map params) {
		return this.commonDao.queryOrgListPagenation(pagination,params);
	}

	public Integer queryOrgEmpListCount(Map map) {
		return this.commonDao.queryOrgEmpListCount(map);
	}

	public List<Map> queryOrgEmpListPagenation(Pagination pagination, Map map) {
		return this.commonDao.queryOrgEmpListPagenation(pagination,map);
	}

	public void addProblem(Map params) {
		this.commonDao.addProblem(params);
	}

	public List queryProblems() {
		return this.commonDao.queryProblems();
	}

    public Map querySysLogInfoList(Map params) {
		Map<String,Object> rtMap = new HashMap<String,Object>();
		List<Map<String,Object>> resultList = this.commonDao.querySysLogInfoList(params);
		Integer count = this.commonDao.querySysLogInfoListCnt(params);
		rtMap.put("data",resultList);
		rtMap.put("total",count);
		return rtMap;
    }

    public Map queryServLogInfoList(Map params) {
		Map<String,Object> rtMap = new HashMap<String,Object>();
		List<Map<String,Object>> resultList = this.commonDao.queryServLogInfoList(params);
		Integer count = this.commonDao.queryServLogInfoListCnt(params);
		rtMap.put("data",resultList);
		rtMap.put("total",count);
		return rtMap;
    }

    public Map queryUserLogInfoList(Map params) {
        Map<String,Object> rtMap = new HashMap<String,Object>();
        List<Map<String,Object>> resultList = this.commonDao.queryUserLogInfoList(params);
        Integer count = this.commonDao.queryUserLogInfoListCnt(params);
        rtMap.put("data",resultList);
        rtMap.put("total",count);
        return rtMap;
    }
}
