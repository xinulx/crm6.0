/**初始化页面信息*/
var Index = function () {
    /**
     * 最后一次移动鼠标的时间
     */
    this.lastMoveTime = (new Date()).getTime();

    this.timeId = null;
};
//登陆信息
$(document).ready(function () {
    index.getDate();
    setInterval(index.getDate, 1000);
    index.loadUserInfo();
    mini.parse();
    mini.layout();
    index.listenOnKey();
    $(document).mouseover(function (e) {
        index.doMouseOver();
    });
    //定时检测鼠标是否移动 每3秒检测一次
    index.timeId = setInterval(index.doInterval, 1000 * 3);
});
/**
 * 首页鼠标移动事件
 */
Index.prototype.doMouseOver = function () {
    var d = new Date();
    this.lastMoveTime = d.getTime();
};
/**
 * 定时检查是否正在操作
 */
Index.prototype.doInterval = function () {
    var d = new Date();
    //如果超过10分钟没有操作，则弹出提示
    if ((d.getTime() - index.lastMoveTime) / 1000 > 10 * 60) {
        clearInterval(index.timeId);
        mini.alert("操作已经过期，请重新登录！", "系统提示", function (action) {
            mini.loading('正在退出...', '提示');
            setTimeout(function () {
                location.href = 'loginAction.shtml?method=logout&param=010';
                window.CloseOwnerWindow(action);
            }, 1500);
        });
    }
};
//时间
Index.prototype.getDate = function () {
    var lastTime = new Date().getTime();
    var date = new Date().toLocaleString();
    $('#curr_date').text("日期："+ date);
};
//初始化登陆用户
Index.prototype.loadUserInfo = function () {
    var user, loginUser;
    $.ajax({
        type: "POST",
        async: false,//加载同步数据
        url: "loginAction.shtml?method=getUserInfo&time=" + new Date().getTime(),
        data: {},
        dataType: "json",
        success: function (data, result, ajax) {
            user = data.data.user;
            loginUser = data.data.loginUser;
        }
    });
    $('#ainiusername a').data('user', user);
    $('.x-userinfo').data('loginUser', loginUser);
    $('#ainiusername a').html('当前用户：'+user.userName + '    工号：' + user.userId);
    $('#roleName').text(loginUser.roleName);
    $('#userinfo input').attr('disabled', 'disabled');

    $('#userId').val(user.userId);
    $('#userName').val(user.userName);
    $('#orgId').val(user.orgId);
    $('#password').val(user.password);
    $('#loginNum').val(user.loginNum);

};
/**
 * 系统导航菜单跳转
 * @param node 菜单节点
 * @return
 */
Index.prototype.showTab = function (node) {
    var tabs = mini.get("mainTabs");
    // 用全局初始化，否则子页面直接取不到
    urlInfo = {};

    $.ajax({
        type: "post",
        async: false,
        url: "systemAction.shtml?method=getMenuUrl&type=a&menuId=" + node.id,
        dataType: "json",
        success: function (data, result, ajax) {
            urlInfo = data.data;
            urlInfo['module_code'] = node.id;
        }
    });
    if (!urlInfo.MENU_URL || urlInfo.MENU_URL == '/') {
        mini.showMessageBox(
            {
                title: "系统消息",
                message: "该功能尚未开发，请选择其他菜单！",
                buttons: ["关闭"],
                iconCls: "mini-messagebox-warning",
                html: "",
                callback: function (action) {
                }
            }
        );
        return;
    }
    var id = "tab$" + node.id;
    var tab = tabs.getTab(id);
    if (!tab) {
        tab = {};
        tab.name = id;
        tab.title = node.text;
        tab.showCloseButton = true;
        tab.url = "systemAction.shtml?method=toClickMenuPage&type=b&menuUrl="
            + urlInfo.MENU_URL + "&menuMethod=" + urlInfo.MENU_METHOD;
        tabs.addTab(tab);
    }
    tabs.activeTab(tab);
};
Index.prototype.onClick = function (e) {
    //将miniui解析成dom再转$可以实现$操作
    var dom = e.sender.el;
    var rid = dom.id;
    var tabs = mini.get("mainTabs");
    var text = dom.innerText;
    var id = "tab$" + rid;
    var tab = tabs.getTab(id);
    if (!tab) {
        tab = {};
        tab.name = id;
        tab.title = text;
        tab.showCloseButton = true;
        tab.url = "loginAction.shtml?method=openRightMenu&menu_type=" + rid.split(',')[0] + "&menu_id=" + rid.split(',')[1]
        + "&menuMethod=" + rid.split(",")[2];
        tabs.addTab(tab);
    }
    tabs.activeTab(tab);
};
Index.prototype.toCustView = function (custInfo) {
    parent.urlInfo = {PARENT_MENU_NAME: "客户管理", MENU_NAME: "客户视图"};
    var rid = custInfo["CUST_ID"]
    var tabs = mini.get("mainTabs");
    var text = "客户视图【" + custInfo["CUST_NAME"] + "】";
    var id = "tab$" + rid;
    var tab = tabs.getTab(custInfo["CUST_ID"]);
    if (!tab) {
        tab = {};
        tab.name = id;
        tab.title = text;
        tab.showCloseButton = true;
        tab.url = "pages/index/customer/custViewOfOne.html";
        tabs.addTab(tab);
    }
    tabs.activeTab(tab);
};
Index.prototype.onItemClick = function (e) {
    var item = e.item;
    var isLeaf = e.isLeaf;
    if (isLeaf) {
        index.showTab(item);
    }
};

function add() {
    mini.open({
        url: "pages/common/problemWindow.html",
        title: "问题或建议", width: 600, height: 400,
        onload: function () {
            var iframe = this.getIFrameEl();
            var data = {action: "new"};
            iframe.contentWindow.SetData(data);
        },
        ondestroy: function (action) {
            mini.alert("谢谢参与！");
        }
    });
}

// 监听键盘事件
Index.prototype.listenOnKey = function () {
    $(document).keydown(function (e) {
        if (e.ctrlKey && e.which == 13) {
            mini.prompt("请输入订单号：", "请输入",
                function (action, value) {
                    if (action == "ok") {
                        alert(value);
                    } else {
                        alert("取消");
                    }

                }
            );
        }
    })
}
//创建首页菜单操作对象
var index = new Index();