/**
 * @author db2admin
 * 修改员工信息
 */
var UpdateUserPage=function(){
	//可以设置通用变量、常量等属性
};
$(document).ready(function(){
	$("#msg").hide();
	updateUserPage.initSelectOpt("ORGINFO", "ORGID", "T01","orgId","");
	updateUserPage.initSelectOpt("DIC_DEPT", "DEPTNO", "T02","deptNo","");
	updateUserPage.loadUserInfo();
	updateUserPage.validateSubmit();
    $.validator.setDefaults({
		submitHandler: function(){
			updateUserPage.saveUpdateUserInfo();
		}
	});
});
UpdateUserPage.prototype.validateSubmit = function(){
	$("#saveBtn").click(function(){
        $("#userAddForm").validate({
        	rules: {
	            'empNo': "required",'empName': "required",
	            'empCode': "required",'age': "required",
	            'birthDate': "required",'hireDate': "required",
	            'orgId': "required",'deptNo': "required",
	            'gender': "required",'salary': "required"
	        },
            messages: {
	        	'empNo': "不能为空",'empName': "不能为空",
	            'empCode': "不能为空",'age': "不能为空",
	            'birthDate': "不能为空",'hireDate': "不能为空",
	            'orgId': "不能为空",'deptNo': "不能为空",
	            'gender': "不能为空",'salary': "不能为空"
            }
        });
   }); 
};
UpdateUserPage.prototype.getRandomEmpCode=function(){
	$.ajax({
		type:'post',
		async:false,
		dataType:'json',
		url:'../../../../empAction.shtml?method=getRandomEmpCode',
		data:{},
		success:function(data,result,ajax){
			$('#empCode').val(data.empCode);
		}
	});
};
UpdateUserPage.prototype.loadUserInfo=function(){
	var userData = window.dialogArguments[0];
	updateUserPage.getRandomEmpCode();
	$("#empNo").val(userData.EMPNO);
	$("#empName").val(userData.EMPNAME);
	$("#age").val(userData.AGE);
	$("#birthDate").val(userData.BIRTHDATE);
	$("#hireDate").val(userData.HIREDATE);
	$('#orgId option[value='+userData.ORGID+']').attr('selected',true);
	$('#deptNo option[value='+userData.DEPTNO+']').attr('selected',true);
	$("#gender option[value="+userData.GENDER+"]").attr("selected",true);
	$("#salary").val(userData.SALARY);
	$("#empDesc").val(userData.EMPDESC);
};
/**
 * 初始化机构和部门下拉框
 * @param tableName表名，colunmName列名,objType业务类型
 */
UpdateUserPage.prototype.initSelectOpt=function(tableName,colunmName,objType,id,params){
	try {
		$.ajax({
	        type: "POST",
	        async: false,
	        url: "../../../../empAction.shtml?method=initSelectOpt&time="+new Date().getTime(),
	        data: {tableName:tableName,colunmName:colunmName,objType:objType,params:params},
	        dataType: "json",
	        success: function(data,result,ajax){
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
UpdateUserPage.prototype.filterDept=function(orgId){
	var params=$("#orgId").val();
	$("#deptNo").empty();
	updateUserPage.initSelectOpt("DIC_DEPT", "DEPTNO", "T02","deptNo",params);
};
//ajax提交基本信息
UpdateUserPage.prototype.saveUpdateUserInfo=function(){
	$("#msg").show();
	var empCode=$("#empCode").val();
	var empNo=$("#empNo").val();
	var empName=$("#empName").val();
	var age=$("#age").val();
	var birthDate=$("#birthDate").val();
	var hireDate=$("#hireDate").val();
	var orgId=$("#orgId").val();
	var deptNo=$("#deptNo").val();
	var gender=$("#gender").val();
	var salary=$("#salary").val();
	var empDesc=$("#empDesc").val();
	
	var formData=new Object();
	
	formData.empCode=empCode;
	formData.empNo=empNo;
	formData.empName=empName;
	formData.age=age;
	formData.birthDate=birthDate;
	formData.hireDate=hireDate;
	formData.orgId=orgId;
	formData.deptNo=deptNo;
	formData.gender=gender;
	formData.salary=salary;
	formData.empDesc=empDesc;
	
	$(".btnSelf").attr("disabled",true);
	
	$.ajax({
		type: "post",
		async:true,
        url: "../../../../empAction.shtml?method=saveUpdateUserInfo&time="+new Date().getTime()+"&objType=F02",
        data: formData,
        dataType: "json",
        success: function(data,result,ajax){
        	$("#msg").hide();
			$(".btnSelf").attr("disabled",false);
			alert('保存成功！');
			window.returnValue="USER_UPDATE_END";
			window.close();
		},
		error: function(){
	        alert(arguments[1]);
		}
	});
};
/**
 * 创建添加管理对象
 * @author {@link updateUserPage.js}
 */
var updateUserPage=new UpdateUserPage();