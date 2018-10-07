/**
 * @author db2admin
 * 新增员工信息
 */
var AddNewUserPage=function(){
	//可以设置通用变量、常量等属性
};
$(document).ready(function(){
	addNewUserPage.initSelectOpt("ORGINFO", "ORGID", "T01","orgId","");
	addNewUserPage.initSelectOpt("DIC_DEPT", "DEPTNO", "T02","deptNo","");
	$("#saveBtn").click(function(){
		addNewUserPage.validateSubmit();
	});
    $.validator.setDefaults({
		submitHandler: function(){
			addNewUserPage.saveNewUserInfo();
		}
	});
	addNewUserPage.getRandomEmpCode();
	addNewUserPage.getEmpCodeNbr();
	//select文本框
	$(".sewvtop").click(function(){
		$(".sewvbm").slideToggle(150);
		if($(this).find("em").hasClass("lbaxztop")){
			$(this).find("em").addClass("lbaxztop2");
			$(this).find("em").removeClass("lbaxztop");
		}else{
			$(this).find("em").addClass("lbaxztop");
			$(this).find("em").removeClass("lbaxztop2");
		}
	});
	
	$(".sewvbm>li").click(function(){
		var selva=$(this).text();
		$(".sewvtop>span").text(selva);
		$(this).parent("ul").hide();
		
		if($(this).parents(".sewv").find("em").hasClass("lbaxztop")){
			$(this).parents(".sewv").find("em").addClass("lbaxztop2");
			$(this).parents(".sewv").find("em").removeClass("lbaxztop");
		}else{
			$(this).parents(".sewv").find("em").addClass("lbaxztop");
			$(this).parents(".sewv").find("em").removeClass("lbaxztop2");
		}
	});

});
AddNewUserPage.prototype.validateSubmit = function(){
    $("#userAddForm").validate({
    	rules:{
            'empNo': "required",
            'empName': "required",
            'empCode': "required",
            'age': "required",
            'birthDate': "required",
            'hireDate': "required",
            'orgId': "required",
            'deptNo': "required",
            'gender': "required",
            'salary': "required"
        },
        messages:{
        	'empNo': "不能为空",
        	'empName': "不能为空",
            'empCode': "不能为空",
            'age': "不能为空",
            'birthDate': "不能为空",
            'hireDate': "不能为空",
            'orgId': "不能为空",
            'deptNo': "不能为空",
            'gender': "不能为空",
            'salary': "不能为空"
        }
    });
};
//生成串码
AddNewUserPage.prototype.getRandomEmpCode=function(){
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
//自动生成员工编码
AddNewUserPage.prototype.getEmpCodeNbr=function(){
	$.ajax({
		type:'post',
		async:false,
		dataType:'json',
		url:'../../../../empAction.shtml?method=getEmpCodeNbr',
		data:{},
		success:function(data,result,ajax){
			$('#empNo').val(data.empNo);
		}
	});
};
/**
 * 初始化机构和部门下拉框
 * @param tableName表名，colunmName列名,objType业务类型
 */
AddNewUserPage.prototype.initSelectOpt=function(tableName,colunmName,objType,id,params){
	try {
		$.ajax({
	        type: "POST",
	        async: true,
	        url: "../../../../empAction.shtml?method=initSelectOpt&time="+new Date().getTime(),
	        data: {tableName:tableName,colunmName:colunmName,objType:objType,params:params},
	        dataType: "json",
	        success: function(data,result,ajax){
				var list=data[0].data;
				var options = '<option value="">-请选择-</option>'
				for(var i=0;i<list.length;i++){
					options +='<option value="'+list[i].code+'">'+list[i].name+'</option>';
				}
				$('#'+id).append(options);
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
AddNewUserPage.prototype.filterDept=function(orgId){
	var params=$("#orgId").val();
	$("#deptNo").empty();
	addNewUserPage.initSelectOpt("DIC_DEPT", "DEPTNO", "T02","deptNo",params);
};
//ajax提交基本信息
AddNewUserPage.prototype.saveNewUserInfo=function(){
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
		async: true,
        url: "../../../../empAction.shtml?method=saveNewUserInfo&time="+new Date().getTime()+"&objType=F01",
        data: formData,
        dataType: "json",
        success: function(data,result,ajax){
        	$("#msg").hide();
			$(".btnSelf").attr("disabled",false);
			alert('保存成功！');
			window.returnValue="USER_ADD_END";
			window.close();
		},
		error: function(){
	        alert(arguments[1]);
		}
	});
};
/**
 * 创建添加管理对象
 * @author {@link addNewUserPage.js}
 */
var addNewUserPage=new AddNewUserPage();