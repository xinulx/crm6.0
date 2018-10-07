$(function(){
    $("#userId").keyup(function(){
        initUserInfoList();
    });
    $("#userId").click(function(){
        initUserInfoList();
    });
    mini.parse();
    tree1 = mini.get("tree1");
    tree2 = mini.get("tree2");
    $("#menuDiv .menuList:eq(1) button").click(function(e){
        mini.alert("别激动，还没开发完！");
        if($(e.target).hasClass('rightMove')){
            var node = tree1.getSelectedNode();
            // console.log(node);
        }else if($(e.target).hasClass('allRightMove')){
            var nodes = tree1.getCheckedNodes(true);
            // console.log(nodes);
        }else if($(e.target).hasClass('leftMove')){
            var node = tree2.getSelectedNode();
            // console.log(node);
        }else if($(e.target).hasClass('allLeftMove')){
            var nodes = tree2.getCheckedNodes(true);
            // console.log(nodes);
        }
    });
});
function onDrawNode(e) {
    var tree = e.sender;
    var node = e.node;

    var isLeaf = tree.isLeaf(node);

    //所有子节点加上超链接
    if (isLeaf == true) {
        e.nodeHtml = '<a href="javascript:void(0);">' + node.text + '</a>';
    }

    //父节点高亮显示；子节点斜线、蓝色、下划线显示
    if (isLeaf == false) {
        e.nodeStyle = 'font-weight:bold;';
    } else {
        e.nodeStyle = "font-style:none;"; //nodeStyle
        e.nodeCls = "blueColor";            //nodeCls
    }

    //修改默认的父子节点图标
    if (isLeaf == false) {
        e.iconCls = "folder";
    } else {
        e.iconCls = "file";
    }

    //父节点的CheckBox全部隐藏
    if (isLeaf == false) {
        // e.showCheckBox = false;
    }
}
function initUserInfoList(){
    $.post(
        'account.shtml?method=initUserInfoList',
        {userId:$("#userId").val()},
        function(result){
            result = JSON.parse(result);
            var tr = '<tr><th>用户ID</th><th>用户名</th></tr>';
            if(result != null && result.length > 0){
                for(var i = 0;i<result.length;i++){
                    tr += '<tr><td>'+result[i]['userId']+'</td><td>' +result[i]['userName']+ '</td></tr>';
                }
                $('#userInfo').empty().append(tr);
                showRoles();
                $('#userInfo tr:gt(0)').click(function(){
                    var userId = $(this).find('td:eq(0)').text();
                    var userName = $(this).find('td:eq(1)').text();
                    $("#hasRoles").show().find("#userName").text(userName);
                    hideMenu();
                    $.post(
                        "roleAction.shtml?method=initRoleInfo",
                        {userId:userId},
                        function(result){
                            result = JSON.parse(result);
                            if(result != null && result.length > 0){
                                var li = '';
                                for(var i = 0;i<result.length;i++){
                                    li += '<li class="element" roleCode="'+result[i]['roleCode']+'">' +result[i]['ROLENAME']+'</li>';
                                }
                                $('#rolesList').empty().append(li).find('li').click(function(){
                                    $("#menuDiv").slideDown(800);
                                });
                                $(".element").click(function(){
                                    var flag = $(this).hasClass("checked");
                                    if(flag){
                                        $(this).removeClass("checked");
                                        $("#menuDiv").slideUp(800);
                                    }else{
                                        if($(".checked").length > 0){
                                            $("#menuDiv").slideUp(800);
                                        }
                                        $(this).addClass("checked").siblings().removeClass("checked");
                                        $("#menuDiv").slideDown(800);
                                    }
                                });
                            }else{
                                $('#rolesList').empty();
                            }
                        }
                    );
                }).css("cursor","pointer");
            }
        }
    );
}
function showRoles() {
    var cityObj = $("#userId");
    var cityOffset = $("#userId").offset();
    $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
        hideMenu();
    }
}