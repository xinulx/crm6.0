;(function ($) {
    //获取URL参数
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    };
    //获取中文参数
    $.getUrlParamCN = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(unescape(r[2])); return null;
    };
    //设置中文参数
    $.setUrlParamCN = function(nameCN){
        return encodeURI(encodeURI(nameCN));
    };

    // 禁止复制
    $.forbiddenCopy = function (ele){
        //document.body.onselectstart=document.body.ondrag=function(){return false;};
        $('#'+ele)[0].onselectstart=$('#'+ele)[0].ondrag=function(){return false;};
        $('#'+ele).css('cursor','default');
    };

    // 实现屏幕打字效果
    $.fn.wordby = function( options ) {

        var s = $.extend({
            content: '',
            speed: 10,
            type: 'fade',
            fadeSpeed: 500,
            finished: function(){}
        }, options );

        var elem = $(this),
            letterArray = [],
            lbylContent = s.content,
            count = $(this).length;

        elem.empty();
        elem.attr('data-time', lbylContent.length * s.speed)

        for (var i = 0; i < lbylContent.length; i++) {
            letterArray.push(lbylContent[i]);
        }

        $.each(letterArray, function(index, value) {
            elem.append('<span style="display: none;">' + value + '</span>');

            setTimeout(function(){
                if (s.type == 'show') {
                    elem.find('span:eq(' + index + ')').show();
                } else if (s.type == 'fade') {
                    elem.find('span:eq(' + index + ')').fadeIn(s.fadeSpeed);
                }
                elem.css('color','#0000ff');
            }, index * s.speed);

        });

        setTimeout(function(){
            s.finished();
        }, lbylContent.length * s.speed);

    };

    $.extend({
        jsonAjax:function(serviceName,methodName,data,successfn,errorfn,ajaxfun) {
            if(null==serviceName||""==serviceName){
                alert("serviceName不能为空……");
                return;
            }
            if(null==methodName||""==methodName){
                alert("methodName不能为空……");
                return;
            }
            ajaxfun=(null == ajaxfun||"" == ajaxfun)?{}:ajaxfun;
            ajaxfun.async = (ajaxfun.async == null || ajaxfun.async == "" || typeof (ajaxfun.async) == "undefined") ? "true" : ajaxfun.async;
            ajaxfun.type = (ajaxfun.type == null || ajaxfun.type == "" || typeof (ajaxfun.type) == "undefined") ? "post" : ajaxfun.type;
            $.ajax({
                type : ajaxfun.type,
                async : ajaxfun.async,
                data : {
                    "data" : JSON.stringify(data)
                },
                url : "/crm/controlServlet.shtml?serviceName="+serviceName+"&methodName="+methodName,
                contentType : "application/x-www-form-urlencoded; charset=utf-8",
                dataType : "json",
                success : function(d) {
                    successfn(d);
                },
                error : function(e) {

                }
            });
        },
        /**
         * jquery序列化解析表单参数
         * @param form
         * @returns {Object}
         */
        getFormJSONData:function(form){
            try {
                if(form == undefined || form == null || form == '' || $.trim(form) == ''){
                    alert("请求错误\n form id is null");
                    return;
                }
                var formData=$("#"+form).serialize();

                //jquery序列化会把空格转换成"+"，此处再转回去
                formData = formData.replace(/\+/g,"");
                //.serialize()自动调用了encodeURIComponent方法将数据编码了 ，此处再解码，保证中文参数正确
                formData = decodeURIComponent(formData,true);

                var parameters=formData.split("&");

                var data=new Object();
                for(var i = 0; i < parameters.length; i ++){
                    var item=parameters[i].split("=");
                    if(item[1] != undefined && item[1] !=null && item[1] != "" && $.trim(item[1]) != ""){
                        data[item[0]]=$.trim(item[1]);
                    }
                }

                return data;
            } catch (e) {
                alert(e.message);
            }
        },
        getConfigCodes:function(id,codeType,colunmName,tableName){
            var data={};
            data.codeType=codeType;
            data.colunmName=colunmName;
            data.tableName=tableName;
            $.jsonAjax('common','getConfigCodes',data,function(backData){
                $("#"+id).empty();
                $("#"+id).append('<option value="">-请选择-</option>');
                var list=backData.codeList;
                for(var i = 0;i < list.length; i ++){
                    var option = '<option value="'+list[i]['code']+'">'+list[i]['name']+'</option>';
                    $("#"+id).append(option);
                }
            });
        }
    })
})(jQuery);
/**
 *  日期操作类
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
/**
 *转换日期对象为日期字符串
 * @param date 日期对象
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDate(date, isFull) {
    var pattern = "";
    if (isFull == true || isFull == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    } else {
        pattern = "yyyy-MM-dd";
    }
    return getFormatDate(date, pattern);
}
/**
 *转换当前日期对象为日期字符串
 * @param date 日期对象
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatNowDate(isFull) {
    return getSmpFormatDate(new Date(), isFull);
}

/**
 *转换long值为日期字符串
 * @param l long值
 * @param isFull 是否为完整的日期数据,
 *               为true时, 格式如"2000-03-05 01:05:04"
 *               为false时, 格式如 "2000-03-05"
 * @return 符合要求的日期字符串
 */
function getSmpFormatDateByLong(l, isFull) {
    return getSmpFormatDate(new Date(l), isFull);
}

/**
 *转换long值为日期字符串
 * @param l long值
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */
function getFormatDateByLong(l, pattern) {
    return getFormatDate(new Date(l), pattern);
}
/**
 *转换日期对象为日期字符串
 * @param l long值
 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
 * @return 符合要求的日期字符串
 */
function getFormatDate(date, pattern) {
    if (date == undefined) {
        date = new Date();
    }
    if (pattern == undefined) {
        pattern = "yyyy-MM-dd hh:mm:ss";
    }
    return date.format(pattern);
}