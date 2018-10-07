//基于ppm2.2框架进行简单封装 zhaow 2012-5-24
ppm2 = {
    version : 2.0
}

ppm2.ajax = {
    ppm2QueryUrl : null,

    //ajax表单提交
    submit : function(formId_, url_, callback_) {
        if (!!!url_) {
            url_ = document.getElementById(formId_).action;
        }
        if (callback_ == null) {
            callbackFun_ = new Function();
        } else {
            callbackFun_ = callback_;
        }

        var splitFlag_ = "&";
        if (url_.indexOf("?") < 0)
            splitFlag_ = "?";
        var options  = {
            url:url_,
            type:'post',
            success: ppm2.ajax.submitCallback,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            error:function() {
                alert("系统异常.");
            }
        };
        $('#' + formId_).ajaxSubmit(options);
    },

    submitCallback : function(responseText, statusText) {
        var jsonBean;
        eval('jsonBean='+responseText);
        if (jsonBean.callBack != undefined && jsonBean.callBack != "") {
            eval("callbackFun_=" + jsonBean.callBack);
        }

        var resultType = parseInt(jsonBean.resultType);
        var info_ = jsonBean.info;
        //如果是自定义提示，则直接调用
        if (resultType == -1) {
            alert(jsonBean.info);
        } else if (resultType == -2) {
            callbackFun_.call(this, jsonBean);
        } else if (resultType == 1) { //提交成功！
            if (info_ == undefined || info_ == "") {
                info_ = "\u63D0\u4EA4\u6210\u529F\uFF01";
            }
            alert(info_);
            callbackFun_.call(this, jsonBean);
        } else if (resultType == 2) {  //提交失败
            if (info_ == undefined || info_ == "") {
                info_ = "\u63D0\u4EA4\u5931\u8D25\uFF01";
            }
            alert(info_);
        } else if (resultType == 3) {//保存成功
            if (info_ == undefined || info_ == "") {
                info_ = "\u4FDD\u5B58\u6210\u529F\uFF01";
            }
            alert(info_);

            callbackFun_.call(this, jsonBean);
        } else if (resultType == 4) {//保存失败！
            if (info_ == undefined || info_ == "") {
                info_ = "\u4FDD\u5B58\u5931\u8D25\uFF01";
            }
            alert(info_);
        } else if (resultType == 5) {//删除成功！
            if (info_ == undefined || info_ == "") {
                info_ = "\u5220\u9664\u6210\u529F\uFF01";
            }
            alert(info_);
            callbackFun_.call(this, jsonBean);
        } else if (resultType == 6) {//删除失败！
            if (info_ == undefined || info_ == "") {
                info_ = "\u5220\u9664\u5931\u8D25\uFF01";
            }
            alert(info_);
        }
    },
    //ajax 查询 formId_ : form的id;  url_:可选，如果不填，则默认为form的action;
    query : function(formId_, url_) {
        $.ajaxSettings.traditional=true;

        try {
            ppm2.tool.trim();
        } catch(e){}

        ppm2.ajax.ppm2QueryUrl = url_;
        var form_ = document.getElementById(formId_);

        if (!!!url_) {
            url_ = form_.action;
        }
        url_ = url_.replace(".service?", ".aa?");
        ajaxAnywhere.formName = formId_;
        document.getElementById(formId_).action = url_;

        ajaxAnywhere.submitAJAX();
    }
}

ppm2.tool = {
    //获取复选框的值
    //selectId:复选框id; isNotice:是否提示；noOneinfo:如果一条数据都未选中的提示信息
    getTableSelectValue : function(selectId,separate) {
        var objs = document.getElementsByName(selectId);
        if(!!!objs)
            return "";

        if (!!!separate)
            separate = ",";

        var values = "";
        for(var i=0;i<objs.length;i++){
            if (objs[i].checked) {
                values += objs[i].value + separate;
            }
        }

        if (values != "")
            values = values.substr(0, values.length-1);

        return values;
    },

    compareDate : function(date1InputId, date2InputId) {
        var date1 = document.getElementById(date1InputId).value;
        var date2 = document.getElementById(date2InputId).value;
        if (!!!date1 || !!!date2)
            return true;

        if (!!!date1 && !!!!date2)
            return false;

        if (!!!date2)
            return true;

        var d1Arr = date1.split("-");
        var d2Arr = date2.split("-");
        var d1 = new Number(d1Arr[0]) * 10000 + new Number(d1Arr[1]) * 100 + new Number(d1Arr[2]);
        var d2 = new Number(d2Arr[0]) * 10000 + new Number(d2Arr[1]) * 100 + new Number(d2Arr[2]);
        return d2 >= d1;
    },

    getUrlValue : function(paramKey, localUrl) {
        url = localUrl;
        if (!!!localUrl)
            url = location.href;

        var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
        var paraObj = {}
        for (i=0; j=paraString[i]; i++) {
            paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);
        }
        var returnValue = paraObj[paramKey.toLowerCase()];
        if (typeof(returnValue)=="undefined") {
            return "";
        } else {
            return returnValue;
        }
    },

    trim : function() { //去除所有文本框输入值的前后空格
        $('input[type=text]:not(:disabled)').each(function(){
            $(this).val($.trim($(this).val()));
        });
    },
    // 获取表单数据
    getFormJSONData:function(formId){
        if(formId == 'undefined' || formId == undefined || formId == null || formId == ''){
            alert('form id is null or not exist ,please check it !');
            return;
        }
        var params = new Object();
        var inputElements = $('#'+formId+' input');
        var selectElements = $('#'+formId+' select');
        var textareaElements = $('#'+formId+' textarea');
        inputElements.each(function(){
            params[this.name] = this.value;
        });
        selectElements.each(function(){
            params[this.name] = this.value;
            params[this.name+'Text'] = $(this).find('option[value='+this.value+']').text();
        });
        textareaElements.each(function(){
            params[this.name] = this.value;
        });
        return params;
    }
}

//日期
ppm2.calendar = {
    init : function(id,dateFmt,minDate,maxDate,isShowClear,readOnly) {
        var param = {el:id};
        param.dateFmt = !!!dateFmt? 'yyyy-MM-dd':dateFmt;
        param.minDate = !!!minDate? '1900-01-01 00:00:00':minDate;
        param.maxDate = !!!maxDate? '2099-12-31 23:59:59':maxDate;
        param.isShowClear = !!!isShowClear? true:isShowClear;
        param.readOnly = !!!readOnly? false:readOnly;
        WdatePicker(param);
    },
    init2 : function(obj,dateFmt) {
        var param = {el:obj.id};
        param.dateFmt = !!!dateFmt? 'yyyy-MM-dd':dateFmt;
        WdatePicker(param);
    }
}