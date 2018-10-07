/**登陆*/
var Login=function(){};
$(document).ready(function(){
	$('body').css('background','#F4F4F4');
	mini.parse();
	mini.get("loginWindow").show();
	$(mini.get("username").el).find('input[name=username]').focus();
});
Login.prototype.onLoginClick=function(e) {
	var form = new mini.Form("#loginWindow");
	var data = form.getData();      //获取表单多个控件的数据
	var json = mini.encode(data);   //序列化成JSON
	if (form.isValid() == false) return;
	login.goIndex(data);
};
Login.prototype.oncheck=function(obj) {
    var $tr = $(obj).parent().parent();
    var userId = $tr.attr("userid");
    var roleId = $tr.attr("roleid");
    var orgId = $tr.attr("orgid");
    $.getJSON(
        'loginAction.shtml?method=toLoginAction',
        {userId:userId,roleId:roleId,orgId:orgId},
        function(data){
            if(data.user){
                mini.hideMessageBox(messageId);
                mini.get("loginWindow").hide();
                mini.loading("正在登录...","提示");
                setTimeout(function () {
                    window.location.href = "loginAction.shtml?method=goIndex&time="+new Date().getTime();
                }, 1500);
            }else{
                mini.alert("或取用户数据异常");
                return;
            }
        }
    );
};
Login.prototype.goIndex=function (data) {
    // 选择营业厅或营业单位
    $.getJSON(
        "loginAction.shtml?method=chkAndQryRole&time="+new Date().getTime(),
        data,
        function(result){
            var DATA=result.data;
			if(DATA['roleList'] != undefined){
			    var table = '<table id="roleInfo">';
			    for(var i = 0;i<DATA['roleList'].length;i++){
                    table += '<tr userId="'+data.username+'" roleId="'+DATA['roleList'][i]['ROLEID']+'" orgId="'+DATA['roleList'][i]['ORGID']+'">' +
                        '<td><input name="role" type="radio" onclick="login.oncheck(this)" /></td>'+
                        '<td>' +DATA['roleList'][i]['ROLENAME']+ '</td>'+
                        '<td>' +DATA['roleList'][i]['ORGNAME']+ '</td></tr>';
                }
                table += '</table>';
                messageId = mini.showMessageBox({
                    showHeader: true,
                    title: "选择角色",
                    showCloseButton:false,
                    html: table
                });
			}else{
				mini.alert(result.data.flag+" "+result.data.msg);
				return;
			}
        }
    );
};
Login.prototype.onResetClick=function(e) {
	var form = new mini.Form("#loginWindow");
	form.clear();
};
Login.prototype.isEmail=function(s) {
	if (s.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
		return true;
	else
		return false;
};
Login.prototype.onUserNameValidation=function(e) {
	if (e.isValid) {
//		if (isEmail(e.value) == false) {
//			e.errorText = "必须输入邮件地址";
//			e.isValid = false;
//		}
		if (e.value.length < 8) {
			e.errorText = "用户名不能少于8个字符";
			e.isValid = false;
		}
	}
};
Login.prototype.onPwdValidation=function(e) {
	if (e.isValid) {
		if (e.value.length < 6) {
			e.errorText = "密码不能少于6个字符";
			e.isValid = false;
		}
	}
};
/**修改密码*/
Login.prototype.toModiPwd=function(){
	var window=mini.get('toModiPwd');
	mini.get("loginWindow").hide();
	window.show();
};
Login.prototype.modiPwd=function(){
	var form = new mini.Form("#modiPwdForm");
	var data = form.getData();      //获取表单多个控件的数据
	var json = mini.encode(data);   //序列化成JSON
	if (form.isValid() == false) return;
	
	//发送修改请求
	$.getJSON(
			'loginAction.shtml?method=modiPasswordAction&type=t01&param=0021&data='+data,
			data,//此处用obj不用json
			function(data,result,ajax){
				if(data.data.flag){
					login.toLogin();
				}
				mini.alert(data.data.msg);
			}
	);
};
Login.prototype.onResetModiForm=function(){
	var form = new mini.Form("#modiPwdForm");
	form.clear();
};
Login.prototype.toLogin=function(){
	var window=mini.get('toModiPwd');
	mini.get("loginWindow").show();
	window.hide();
};
//创建登陆对象
var login=new Login();
$(document).keydown(function(event){
    if(event.keyCode == 13 ){
        login.onLoginClick();
    }
});