/**
 * @author db2admin
 * 菜单管理
 */
var MenuManager=function(){
	//可以设置通用变量、常量等属性
	this.nodeId = null;
	this.form = null;
	this.operType = null;
	this.node = null;
	this.data = null;
};
$(document).ready(function(){
	mini.parse();
	mini.layout();
	grid = mini.get("treeGrid");
	grid.load();
	gridImpl = mini.get("treeGridImpl");
	gridImpl.load();
	menuManager.form = new mini.Form("#form");
	menuManager.form.setEnabled(false);
	/**树节点单击事件：只对叶子节点进行查询*/
	mini.get("tree").on("nodeselect", function (e) {
		menuManager.node = e.node;
		menuManager.form.setIsValid(true);
		if (e.isLeaf) {
		} else {
		}
		$.ajax({
			type:'post',
			url:'menuAction.shtml?method=queryMenuInfoById&menuId='+e.node.id,
			dataType:'json',
			success:function(result){
//				mini.hideMessageBox(msgId);
//				console.log(result.data);
				var data = result.data;
				menuManager.nodeId = data.MENU_ID;
				menuManager.data = data;
				data = mini.decode(data);   //反序列化成对象
				menuManager.form.loading();
				menuManager.form.setData(data); // 返回键名与表单name一致
				menuManager.form.unmask();
				menuManager.form.setEnabled(false);
				if(!data.MENU_PARENT_NAME){
					$("input[name=MENU_PARENT_NAME]").val('根目录');
				}
			}
		});
	});
	var tabs = mini.get("tabs");
	$("body").click(function(e){
		var $e = $(e.target).hasClass("mini-tab-text");
		var currentActiveTab = tabs.getActiveTab();
		if($e){
			if(currentActiveTab.iconCls == 'icon-node'){
				mini.get("start").show();
				mini.get("impl").show();
			}else if(currentActiveTab.iconCls == 'icon-collapse'){
				mini.get("start").hide();
				mini.get("impl").hide();
			}
//			if(tabs.getTabs()[0] == currentActiveTab){
//				mini.get("start").hide();
//				mini.get("impl").hide();
//			}else{
//				mini.get("start").show();
//				mini.get("impl").show();
//			}
		}
	});
    mini.get("tree").selectNode ( mini.get("tree").getRootNode ()['children'][0] );
});
MenuManager.prototype.queryMenuInfo=function (){
	var orgNamedom=mini.get("orgName");
	if(orgNamedom.value){//条件查询展开
		mini.get("tree").load("menuAction.shtml?method=loadSystemAllMenu&param=00010121&type=systemMenuItem&orgIdOrName="+orgNamedom.value);
		mini.get("tree").expandAll();
	}else{//未输入条件或重置时不展开
		mini.get("tree").load("menuAction.shtml?method=loadSystemAllMenu&param=00010121&type=systemMenuItem&orgIdOrName="+orgNamedom.value);
	}		
};
MenuManager.prototype.del=function (){
	mini.confirm("你确定删除当前菜单么?", "操作提示", function(e){
		if(e != 'ok'){
			return;
		}
		if(menuManager.nodeId){
			mini.mask({
	            el: $("#formTbale")[0],
	            cls: 'mini-mask-loading',
	            html: '正在处理...'
	        });
			$.ajax({
				type:'post',
				url:'menuAction.shtml?method=delMenu&menuId='+menuManager.nodeId,
				dataType:'json',
				success:function(result){
//					console.log(menuManager.node);
					mini.unmask($("#formTbale")[0]);
					if(result.data == '1'){
						mini.get("tree").removeNode(menuManager.node);
						menuManager.form.clear();
						mini.showTips({
				           content: "<b>成功</b> <br/>删除成功！",
				           state: "success",
				           x: "Center",
				           y: "Top",
				           timeout: 1500
				       });
					}else{
						mini.showTips({
				            content: "<b>失败</b> <br/>包含子菜单，不能删除！",
				            state: "warning",
				            x: "Center",
				            y: "Top",
				            timeout: 1500
				        });
					}
				}
			});
		}else{
			mini.alert('请选择菜单');
		}
	});
};
MenuManager.prototype.update=function (){
	menuManager.form.setChanged(true);
	menuManager.operType = "update";
	if(menuManager.nodeId){
		menuManager.form.setEnabled(true);
//		mini.getbyName("MENU_PARENT_ID").setReadOnly(true);
		mini.getbyName("MENU_PARENT_NAME").setReadOnly(true);
		$("#operation").show();
	}else{
		mini.alert('请选择菜单');
	}
};
MenuManager.prototype.startCase=function (){
	$("#treeGrid").slideDown('slow');
	$("#treeGridImpl").slideUp('slow');
};
MenuManager.prototype.implCase=function (){
	$("#treeGridImpl").slideDown('slow');
	$("#treeGrid").slideUp('slow');
};
MenuManager.prototype.onButtonEdit = function(e){
    mini.open({
        url: "/crm/pages/common/SelectTreeWindow.html?id="+menuManager.nodeId,
        showMaxButton: true,
        title: "选择上级菜单",
        width: 350,
        height: 350,
        showModal: true,
        ondestroy: function (action) {                    
            if (action == "ok") {
                var iframe = this.getIFrameEl();
                var data = iframe.contentWindow.GetData();
                data = mini.clone(data);
                if (data) {
                	mini.getbyName("MENU_PARENT_ID").setValue(data.id);
                    mini.getbyName("MENU_PARENT_NAME").setValue(data.name);
                }
            }
        }
    });        
};
MenuManager.prototype.add=function (){
	if(!menuManager.nodeId){
		mini.alert('请选择菜单');
		return;
	}
	menuManager.form.clear();
	menuManager.form.setData({MENU_PARENT_ID:menuManager.node.id,MENU_PARENT_NAME:menuManager.node.name}); // 返回键名与表单name一致
	mini.getbyName("MENU_PARENT_ID").setReadOnly(true);
	mini.getbyName("MENU_PARENT_NAME").setReadOnly(true);
	menuManager.form.setEnabled(true);
	menuManager.operType = "add";
	$("#operation").show();
};
MenuManager.prototype.save=function (){
	menuManager.form.validate();
	if (menuManager.form.isValid() == false) return;
	mini.confirm("您确定要保存？","菜单信息",function(event){
		if(event == 'ok'){
			mini.mask({
	            el: $("#formTbale")[0],
	            cls: 'mini-mask-loading',
	            html: '正在处理...'
	        });
			var data = menuManager.form.getData();
//				console.log(data);
			if(menuManager.operType == 'add'){
				$.ajax({
					type:'post',
					url:'menuAction.shtml?method=addMenu&pid='+menuManager.nodeId,
					dataType:'json',
					data:data,
					success:function(result){
				        mini.unmask($("#formTbale")[0]);
						var code = result.data.code;
						var newNode = {id:data.MENU_ID,name:data.MENU_NAME};
						if(code == '200'){
							$("#operation").hide();
							mini.get("tree").addNode ( newNode, "add", menuManager.node );
							mini.get("tree").expandNode ( menuManager.node );
							mini.get("tree").selectNode ( newNode );
							mini.showTips({
					           content: "<b>成功</b> <br/>"+result.data.info,
					           state: "success",
					           x: "Center",
					           y: "Top",
					           timeout: 1500
					       });
						}else{
							mini.showTips({
					            content: "<b>失败</b> <br/>"+result.data.info,
					            state: "warning",
					            x: "Center",
					            y: "Top",
					            timeout: 1500
					        });
						}
					}
				});
			}else if(menuManager.operType == 'update'){
//					mini.get("tree").addNode({id:data.MENU_ID,name:data.MENU_NAME}, "add", menuManager.node);
				$.ajax({
					type:'post',
					url:'menuAction.shtml?method=updateMenu&pid='+menuManager.nodeId,
					dataType:'json',
					data:data,
					success:function(result){
				        mini.unmask($("#formTbale")[0]);
//						console.log(result);
						var code = result.data.code;
						if(code == '200'){
							$("#operation").hide();
							mini.get("tree").updateNode ( menuManager.node, {name:data.MENU_NAME} );
							mini.get("tree").selectNode ( menuManager.node );
							mini.showTips({
					           content: "<b>成功</b> <br/>"+result.data.info,
					           state: "success",
					           x: "Center",
					           y: "Top",
					           timeout: 1500
					       });
						}else{
							mini.showTips({
					            content: "<b>失败</b> <br/>"+result.data.info,
					            state: "warning",
					            x: "Center",
					            y: "Top",
					            timeout: 1500
					        });
						}
					}
				});
			}else{
				mini.alert('操作异常！');
			}
		}
	});
//	}
//	else{
//		mini.alert('您没有做任何修改');
//	}
};
MenuManager.prototype.updateError = function(e) {
    var id = e.sender.name + "_error";
    var el = document.getElementById(id);
    if (el) {
        el.innerHTML = e.errorText;
    }
}
MenuManager.prototype.onValidation=function(e) {                  
    menuManager.updateError(e);
}
MenuManager.prototype.cancel=function (){
	$("#operation").hide();
	menuManager.form.setIsValid(true);
//	menuManager.form.reset();
	menuManager.form.setData(menuManager.data);
	menuManager.form.setEnabled(false);
};
MenuManager.prototype.switchTab=function (){
	var combo1 = mini.get("combo1");
	var value = combo1.getValue();
	if(value != ''){
		var tabs = mini.get("tabs");
		var tabsArr = tabs.getTabs();
		if(value == 'tabs1'){
			tabs.activeTab(tabsArr[0]);
			mini.get("start").hide();
			mini.get("impl").hide();
		}else{
			tabs.activeTab(tabsArr[1]);
			mini.get("start").show();
			mini.get("impl").show();
		}
	}
};
/**
 * @author {@link MenuManager}
 */
var menuManager=new MenuManager();