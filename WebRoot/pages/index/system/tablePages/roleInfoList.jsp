<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/pages/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>角色管理</title>
    <meta name="keywords" content="首页菜单、导航">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta name="description" content="系统首页、菜单、导航">
    <meta name="author" content="wangshibao, email wangshibao@tydic.com">
    <meta name="robots" content="index,follow">
    <link rel="shortcut icon" type="image/ico" href="/favicon.ico" /> <!-- 添加 favicon icon -->
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <base target="_self" />
    <%@ include file="/pages/common/meta.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/pintuer/pintuer.css" />
    <script language="JavaScript" type="text/javascript" src="${ctx}/js/common/jquery.min.js"></script>
    <script language="JavaScript" type="text/javascript" src="${ctx}/pintuer/pintuer.js"></script>
    <script language="JavaScript" type="text/javascript" src="${ctx}/js/common/ajaxanywhere/aa.js"></script>
    <script language="JavaScript" type="text/javascript" src="${ctx}/js/common/ajaxsubmit.js"></script>
    <script language="JavaScript" type="text/javascript" src="${ctx}/js/common/ppmFrame.js"></script>
    <style type="text/css">
        .hover{
            cursor: pointer;
        }
    </style>
</head>
<body>
<form id="myform" name="myform" method="post" action="">
    <aa:zone name="ROLEINFO_LIST_ZONE">
        <div class="doc-demoview doc-viewad0 ">
            <div class="view-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>
                                索引
                            </th>
                            <th>
                                角色ID
                            </th>
                            <th>
                                角色编码
                            </th>
                            <th>
                                角色名称
                            </th>
                            <th>
                                管理级别
                            </th>
                            <th>
                                有效标识
                            </th>
                            <th>
                                操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty listOffers}">
                            <tr>
                                <td colspan="9" align="center"><span style="color: red;"><b>数据不存在！</b></span></td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty listOffers}">
                            <c:set value="0" var="i" />
                            <c:forEach items="${listOffers}" var="listOffers" varStatus="item">
                            <c:set value="${i+1}" var="i"></c:set>
                            <tr>
                                <td>
                                        ${i}
                                </td>
                                <td>
                                        ${listOffers.RID}
                                </td>
                                <td>${listOffers.ROLEID}</td>
                                <td>
                                        ${listOffers.ROLENAME}
                                </td>
                                <td>
                                        ${listOffers.ROLELV}
                                </td>
                                <td>
                                    <c:if test="${listOffers.VFLAG == '0'}">
                                        <span class="text-green">有效</span>
                                    </c:if>
                                    <c:if test="${listOffers.VFLAG != '0'}">
                                        <span class="text-red">禁用</span>
                                    </c:if>
                                </td>
                                <td height="30">
                                    <div class="button-toolbar" style="height: 22px;position: relative;top:-6px">
                                        <div class="button-group">
                                            <button type="button" class="button icon-edit update">
                                                修改
                                            </button>
                                            <button type="button" class="button icon-trash-o delete">
                                                删除
                                            </button>
                                        </div>
                                        <div class="button-group">
                                            <button type="button" class="button icon-user userRole">
                                                用户角色
                                            </button>
                                            <button type="button" class="button icon-users groupRole">
                                                组角色
                                            </button>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <hr class="bg-green" />
        <div align="right" class="pageFoot">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td height="30px">
                        <c:set var="ajaxInvoke" scope="request">pageQueryRoleInfoList</c:set>
                        <pg:pager url="" items="${FW__PAGER_RECORD_TOTAL}" maxPageItems="${10}"
                                  maxIndexPages="${10}" isOffset="${true}"
                                  export="offset,currentPageNumber=pageNumber" scope="request">
                            <%@ include file="../../../common/pagination_pintuer_ajax.jsp"%>
                        </pg:pager>
                    </td>
                </tr>
            </table>
        </div>
    </aa:zone>
</form>
<!-- 编辑弹窗 -->
<button class="button button-big bg-main dialogs updateBtn" data-toggle="click"
        data-target="#update" data-mask="1" data-width="30%" style="display: none">弹出对话框</button>
<div id="update">
    <div class="dialog">
        <div class="dialog-head" style="cursor: move">
            <span class="close rotate-hover"></span><strong>编辑角色</strong>
        </div>
        <div class="dialog-body">
            <iframe id="editRole" name="editRole" height="300" width="100%" frameborder="0" scrolling="auto" src=""></iframe>
        </div>
        <div class="dialog-foot">
            <button class="button dialog-close" id="cancelBtn">
                取消
            </button>
            <button class="button bg-green" id="submitBtn" onclick="document.getElementById('editRole').contentWindow.submit();">
                确认
            </button>
        </div>
    </div>
</div>
<!-- 删除提示弹窗 -->
<button class="button button-big bg-main dialogs deleteBtn" data-toggle="click"
        data-target="#delete" data-mask="1" data-width="30%" style="display: none">弹出对话框</button>
<div id="delete">
    <div class="dialog">
        <div class="alert alert-red">
            <span class="close rotate-hover"></span><strong>删除角色</strong>
            <p>请确认是否删除？</p>
            <button class="button bg dialog-close deleteClose">取消</button>
            <button class="button bg-red delBtn" onclick="delRoleById()">确认</button>
        </div>
    </div>
</div>
<!-- 操作结果提示 -->
<button class="button button-big bg-main dialogs oprTips" data-toggle="click"
        data-target="#oprTips" data-mask="1" data-width="30%" style="display: none">弹出对话框</button>
<div id="oprTips">
    <div class="dialog"></div>
</div>
<!-- 用户角色 -->
<button class="button button-big bg-main dialogs userRoleBtn" data-toggle="click"
        data-target="#userRole" data-mask="1" data-width="30%" style="display: none">弹出对话框</button>
<div id="userRole">
    <div class="dialog">
        <div class="dialog-head" style="cursor: move">
            <span class="close rotate-hover"></span><strong>角色分配</strong>
        </div>
        <div class="dialog-body">
            <div style="max-height: 200px;overflow: auto;" align="center">
                <button class="button text-blue bg-green icon-user"style="float: left;border-right: none;border-top-right-radius: 0;border-bottom-right-radius: 0;width: 10%">&nbsp;</button>
                <input id="userKey" name="userKey" type="text" class="input bg-yellow-light" placeholder="请输入账号或编码" style="width: 90%;float: left;border-left: none;border-top-left-radius: 0;border-bottom-left-radius: 0"/>
                <table class="table table-striped table-hover hover userTable" height="200"></table>
            </div>
        </div>
    </div>
</div>

<!-- 组角色 -->
<button class="button button-big bg-main dialogs groupRoleBtn" data-toggle="click"
        data-target="#groupRole" data-mask="1" data-width="60%" style="display: none">弹出对话框</button>
<div id="groupRole">
    <div class="dialog">
        <div class="dialog-head" style="cursor: move">
            <span class="close rotate-hover"></span><strong>已分配角色管理</strong>
        </div>
        <div class="dialog-body">
            <span style="color: #F7B824;">管理每个用户拥有的角色：删除，修改，添加！</span>
            <iframe id="editGroupRole" name="editGroupRole" height="300" width="100%" frameborder="0" scrolling="auto" src=""></iframe>
        </div>
    </div>
</div>

</body>
</html>
<script !src="">
    function delRoleById(){
        var rid = $(".deleteBtn").data("rid");
        $.post(
            "roleAction.shtml?method=delRoleById",
            {rid:parseInt(rid)},
            function(result){
                var data= JSON.parse(result).data;
                $("#oprTips .dialog").empty();
                if(data.code == '1000'){
                    aa('','');
                    $("#oprTips .dialog").append('<div class="alert alert-green">' +
                        '<span class="close rotate-hover"></span><strong>恭喜：</strong>操作成功。--[点击弹出边界外关闭]</div>');
                }else{
                    $("#oprTips .dialog").append('<div class="alert alert-yellow">' +
                        '<span class="close rotate-hover"></span><strong>对不起：</strong>'+data.desc+'--[点击弹出边界外关闭]</div>');
                }
                $(".deleteClose").trigger("click");
                $(".oprTips").trigger("click");
            }
        );
    }
    function pageQueryRoleInfoList(url){
        url= '/crm/roleAction.aa?method=queryRoleListPagenation&'+url.slice(1,url.length);
        ppm2.ajax.query("myform", url);
    }
    //~ ajax 提交后处理
    ajaxAnywhere.onAfterResponseProcessing = function() {
        if (ajaxAnywhere.isSuccess) {
            initEvent();
        }
    };

    // ~ 异常信息
    AjaxAnywhere.prototype.handleException = function(type, details) {
        alert("\u63d0\u4ea4\u5931\u8d25,\u8bf7\u91cd\u8bd5!" + details); // 提交失败,请重试!
    };
    function aa(roleName,roleId){
        $('form[name=myform]')[0].action = '/crm/roleAction.aa?method=queryRoleListPagenation&roleName='+roleName+"&roleId="+roleId;
        ajaxAnywhere.submitAJAX();
    }
    function initUserList(){
        $.post(
            "roleAction.shtml?method=qryUserOfNoRole",
            {userKey:$("#userKey").val(),roleId:$(".userRoleBtn").data("roleId")},
            function(result){
                var data = JSON.parse(result)[0].data;
                var tr = '';
                for(var i = 0 ; i < data.length ; i ++){
                    tr += '<tr>' +
                        '<td>'+ data[i]['USERID']+'</td>' +
                        '<td>'+ data[i]['USERNAME'] + '</td>' +
                        '<td><a href="javascript:saveUserRole(\''+data[i]['USERID']+''+'\');">确定</a></td>' +
                        '</tr>';
                }
                $(".userTable").empty().append(tr);
            }
        );
    }

    function saveUserRole(userId){
        var roleId = $(".userRoleBtn").data("roleId").trim();
        parent.parent.mini.confirm("确定授权当前角色给该用户吗？", "系统提示",
            function (action) {
                if (action == "ok") {
                    $.post(
                        "roleAction.shtml?method=saveUserRoleInfo",
                        {userId:userId,roleId:roleId},
                        function (result) {
                            $(".close").trigger("click");
                            parent.parent.mini.showTips({
                                content: "<b>成功</b> <br/>保存成功！",
                                state: "success",
                                x: "Center",
                                y: "Top",
                                timeout: 1500
                            });
                        }
                    );
                }
            }
        );

    }

    function initEvent(){
        $(".button-toolbar button").click(function(e){
            var $e = $(e.target);
            var rid = $e.parent().parent().parent().parent().find('td:eq(1)').text();
            var roleId = $e.parent().parent().parent().parent().find('td:eq(2)').text();
            if($e.hasClass("update")){
                $("#editRole")[0].src = 'pages/index/system/edit/toEditRolePage.html?rid=' + rid;
                $(".updateBtn").trigger("click");
            }else if($e.hasClass("delete")){
                $(".deleteBtn").trigger("click").data("rid",rid);
            }else if($e.hasClass("userRole")){
                $(".userRoleBtn").trigger("click").data("roleId",roleId);
                $("#userKey").keyup(function(){
                    initUserList();
                });
                initUserList();
            }else if($e.hasClass("groupRole")){
                $("#editGroupRole")[0].src = 'pages/index/system/edit/toEditGroupRolePage.html?roleId=' + roleId;
                $(".groupRoleBtn").trigger("click").data("roleId",roleId);
            }
        });
    }

    $(function(){
        initEvent();
    });
</script>