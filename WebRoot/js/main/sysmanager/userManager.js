/**
 * @author db2admin
 * 用户管理
 */
var UserManager=function(){
	//可以设置通用变量、常量等属性
};
$(document).ready(function(){
	mini.parse();
	mini.layout();
	mini.get('hireDate').setValue(new Date());
	grid = mini.get("userGrid");
	grid.load();
	/**树节点单击事件：只对叶子节点进行查询*/
	mini.get("tree").on("nodeselect", function (e) {
		if (e.isLeaf) {
			grid.load({ deptNo: e.node.id });
		} else {
			//目录节点直接清空
			grid.setData([]);
			grid.setTotalCount(0);
		}
	});
});
//数据加载
UserManager.prototype.loadData = function () {
    grid.load();
};
//删除行
UserManager.prototype.removeRow = function () {
    var rows = grid.getSelecteds();
    if(rows.length===0){
    	mini.alert("请至少选择一条记录！");
		return;
    }
    if (rows.length > 0) {
//        grid.removeRows(rows, true);//删除dom行，前端处理
    	mini.confirm('你确定删除选中的所有记录吗？', '安全提示', function(e){
    		var paramList='';
    		for(var i=0 ; i < rows.length; i++ ){
    			if(i===(rows.length-1)){
    				paramList+=rows[i].EMPNO
    			}else{
    				paramList+=rows[i].EMPNO+',';    				
    			}
    		}
    		//ajax提交，验证执行结果
    		if(e==='ok'){
    			$.ajax({
        			type: "POST",
        	        async: true,//加载异步数据
        	        url:'empAction.shtml?method=deleteEmpRowsForList&objType=F02&time='+new Date().getTime(),
        	        data: {paramList:paramList},
        	        dataType: "json",
        	        success: function(data,result,ajax){
//        				console.log(data.data);
        				if(data.data===1){
        					mini.alert("删除成功！");
        					userManager.loadData();
        				}
        			}
        		});
    		}
    	});
    }
};
//查询机构或部门信息
UserManager.prototype.queryOrgInfo=function (){
	var orgNamedom=mini.get("orgName");
	if(orgNamedom.value){//条件查询展开
		mini.get("tree").load("systemAction.shtml?method=loadSystemAllMenu&param=00010121&type=systemMenuItem&orgIdOrName="+orgNamedom.value);
		mini.get("tree").expandAll();
	}else{//未输入条件或重置时不展开
		mini.get("tree").load("systemAction.shtml?method=loadSystemAllMenu&param=00010121&type=systemMenuItem&orgIdOrName="+orgNamedom.value);
	}		
};
//重置查询表单
UserManager.prototype.resetForm=function(){
//	alert(mini.get('empId').getValue());
//	alert(mini.get('hireDate').getValue());
	mini.get('empId').setValue("");
	mini.get('hireDate').setValue("");
};
//跳转至新增页面
UserManager.prototype.toAddUserInfoPage=function(){
	var url="pages/index/system/new/toAddNewUserPage.html";
	var returnValue=window.showModalDialog(url,window,"center:yes;dialogWidth:800px;dialogHeight:330px;help:no;resizable:yes;location:yes;scroll:no;status:no");
//	var returnValue=window.showModalDialog(url,window,"center=yes;dialogWidth=800px;dialogHeight=330px;help=no;resizable=yes;location=yes;scroll=no;status=no");
	if(returnValue=="USER_ADD_END"){
		userManager.loadData();
	}
};
UserManager.prototype.importExcel = function(){
	mini.open({
        url:  'pages/index/system/importWindow.html',
        title: "数据导入", width: 1250, height: 700,
        onload: function () {
            var iframe = this.getIFrameEl();
            var data = { action: "new"};
        },
        ondestroy: function (action) {
            grid.reload();
        }
    });
};
UserManager.prototype.exportExcel = function(){
	var rows = grid.getSelecteds ();
	if(rows.length == 0){
		mini.alert("请选择要导出的数据！");
		return;
	}
	var list = new Array();
	for(var i =0;i<rows.length; i ++){
		list.push(rows[i]['EMPNO']);
	}
	window.location.href="empAction.shtml?method=exportExcel&params="+list;
};
//跳转至修改页面
UserManager.prototype.toUpdateUserInfo=function(){
//	var curr_row_data=grid.getSelected ();//获取单行，如果选择了多了将默认为最后点击那行的数据，不选择返回未定义
	var curr_row_data=grid.getSelecteds ();//获取多行，如果选择了多了将数据格式化为数组，每个元素为一条行数据，不选择返回空数组
//	console.log(curr_row_data);
	if(curr_row_data.length===0){
		mini.alert("请至少选择一条记录！");
		return;
	}else if(curr_row_data.length>1){
		mini.alert("您只能选择一条记录！");
		return;
	}
	var url="pages/index/system/update/toUpdateUserPage.html";
	var returnValue=window.showModalDialog(url,curr_row_data,"center=yes;dialogWidth=750px;dialogHeight=350px;help=no;resizable=no;location=yes;scroll=no;status=no");
	if(returnValue=="USER_UPDATE_END"){
		userManager.loadData();
	}
};
/**
 * 初始化机构和部门下拉框
 * @param tableName表名，colunmName列名,objType业务类型
 */
UserManager.prototype.initSelectOpt=function(tableName,colunmName,objType,id,params){
	try {
		$.ajax({
	        type: "POST",
	        async: true,
	        url: "empAction.shtml?method=initSelectOpt&time="+new Date().getTime(),
	        data: {tableName:tableName,colunmName:colunmName,objType:objType,params:params},
	        dataType: "json",
	        success: function(data,result,ajax){
	        	$("#"+id).empty();
				var list=data[0].data;
				$('#'+id).append('<option value="">-请选择-</option>');
				for(var i=0;i<list.length;i++){
					$('#'+id).append('<option value="'+list[i].code+'">'+list[i].name+'</option>');
				}
			}
		});
	} catch (e) {
		alert("初始化下拉菜单异常->"+e.message);
	}
};
/**
 * 选择机构时过滤部门下拉选项
 * @param orgId
 */
UserManager.prototype.filterDept=function(orgId){
	var params=$("#orgId").val();
	$("#deptNo").empty();
	userManager.initSelectOpt("DIC_DEPT", "DEPTNO", "T02","deptNo",params);
};
//根据员工信息查询员工
UserManager.prototype.queryEmpInfoForNewForm=function(){
	//获取参数
	var empId=$(mini.get('empId').el).find('input[name=empId]').val();
	var hireDate=$(mini.get('hireDate').el).find('input[name=hireDate]').val();
	var operType="formQuery";
	//重载表格
	grid.load({ empId: empId,hireDate:hireDate ,operType:operType});
};

/**
 * 创建用户管理对象
 * @author {@link UserManager}
 */
var userManager=new UserManager();