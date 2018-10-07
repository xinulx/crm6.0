var bootPATH = __CreateJSPath("boot.js");

mini_debugger = true;                                   //

var skin = getCookie("miniuiSkin") || 'blue2010';            //skin cookie   cupertino
var mode = getCookie("miniuiMode") || 'medium';               //mode cookie     medium

//miniui
document.write('<script src="' + bootPATH + 'jquery-1.6.2.min.js" type="text/javascript"></sc' + 'ript>');
document.write('<script src="' + bootPATH + 'miniui/miniui.js" type="text/javascript" ></sc' + 'ript>');
document.write('<link href="' + bootPATH + 'miniui/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />');
document.write('<link href="' + bootPATH + 'miniui/themes/default/miniui.css" rel="stylesheet" type="text/css" />');
document.write('<link href="' + bootPATH + 'miniui/themes/default/flatstyle.css" rel="stylesheet" type="text/css" />');

//common
document.write('<link href="' + bootPATH + 'miniui/css/common.css" rel="stylesheet" type="text/css" />');
document.write('<script src="' + bootPATH + 'miniui/js/common.js" type="text/javascript" ></sc' + 'ript>');

//skin
if (skin) document.write('<link href="' + bootPATH + 'miniui/themes/' + skin + '/skin.css" rel="stylesheet" type="text/css" />');

//mode
if (mode) document.write('<link href="' + bootPATH + 'miniui/themes/default/' + mode + '-mode.css" rel="stylesheet" type="text/css" />');

//icon
document.write('<link href="' + bootPATH + 'miniui/themes/icons.css" rel="stylesheet" type="text/css" />');

////////////////////////////////////////////////////////////////////////////////////////
function getCookie(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}

function __CreateJSPath(js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}

/**
 * MBean js中和java MBean相对应的数据结构
 */
var MBean = function(){
    this.ROOT = {},
        this.ROOT.HEADER = {},
        this.ROOT.HEADER.UUID = "UUID_" + new Date().getTime(),
        this.ROOT.HEADER.TIME = new Date().toLocaleString(),
        this.ROOT.BODY = {},
        this.ROOT.BODY.REQUEST_INFO = {},
        this.ROOT.BODY.OPR_INFO = {},
        this.ROOT.RESULT = {},
        this.ROOT.BODY.OUT_DATA = {},

        this.setRoot = function(path,obj){
            path = "ROOT." + path;
            var paths = path.split(".");
            var relPath = paths[paths.length-1];
            var tmp = this;
            for(var i = 0; i < paths.length - 1 ; i ++){
                tmp = tmp[paths[i]];
            }
            tmp[relPath] = obj; // relPath节点可能存在，可能不存在
        },

        this.addRoot = function(path,obj){
            path = "ROOT." + path;
            var paths = path.split(".");
            var relPath = paths[paths.length-1];
            var tmp = this;
            for(var i = 0; i < paths.length - 1 ; i ++){
                tmp = tmp[paths[i]];
            }
            var _tmp = tmp[relPath];
            if(_tmp != undefined && _tmp != null ){ // 节点存在
                if(typeof _tmp == 'string' && _tmp.constructor == String){
                    var _tmp_ = new Array();
                    _tmp_[0] = _tmp
                    _tmp_[1] = obj;
                }else if(typeof _tmp == 'object' && _tmp.constructor == Object){
                    var _tmp_ = new Array();
                    _tmp_[0] = _tmp
                    _tmp_[1] = obj;
                }else if(typeof _tmp == 'array' && _tmp.constructor == Array){
                    var _tmp_ = new Array();
                    _tmp_.push( _tmp );
                    _tmp_.push( obj );
                }else if(!isNaN(_tmp)){
                    var _tmp_ = new Array();
                    _tmp_[0] = _tmp
                    _tmp_[1] = obj;
                }
                tmp[relPath] = _tmp_;
            }else{ // 节点不存在
                tmp[relPath] = obj ;
            }
        },

        // 值覆盖
        this.setBody = function(path,obj){
            path = "ROOT.BODY." + path;
            var paths = path.split(".");
            var relPath = paths[paths.length-1];
            var tmp = this;
            for(var i = 0; i < paths.length - 1 ; i ++){
                tmp = tmp[paths[i]];
            }
            tmp[relPath] = obj; // relPath节点可能存在，可能不存在
        },
        // 值保留并生成复数数组
        this.addBody = function(path,obj){
            path = "ROOT.BODY." + path;
            var paths = path.split(".");
            var relPath = paths[paths.length-1];
            var tmp = this;
            for(var i = 0; i < paths.length - 1 ; i ++){
                tmp = tmp[paths[i]];
            }
            var _tmp = tmp[relPath];
            if(_tmp != undefined && _tmp != null ){ // 节点存在
                if(typeof _tmp == 'string' && _tmp.constructor == String){
                    var _tmp_ = new Array();
                    _tmp_[0] = _tmp
                    _tmp_[1] = obj;
                }else if(typeof _tmp == 'object' && _tmp.constructor == Object){
                    var _tmp_ = new Array();
                    _tmp_[0] = _tmp
                    _tmp_[1] = obj;
                }else if(typeof _tmp == 'array' && _tmp.constructor == Array){
                    var _tmp_ = new Array();
                    _tmp_.push( _tmp );
                    _tmp_.push( obj );
                }else if(!isNaN(_tmp)){
                    var _tmp_ = new Array();
                    _tmp_[0] = _tmp
                    _tmp_[1] = obj;
                }
                tmp[relPath] = _tmp_;
            }else{ // 节点不存在
                tmp[relPath] = obj ;
            }
        },
        this.clear=function(){
            this.ROOT = {},
                this.ROOT.HEADER = {},
                this.ROOT.HEADER.UUID = "UUID_" + new Date().getTime(),
                this.ROOT.HEADER.TIME = new Date().toLocaleString(),
                this.ROOT.BODY = {},
                this.ROOT.BODY.REQUEST_INFO = {},
                this.ROOT.BODY.OPR_INFO = {},
                this.ROOT.RESULT = {},
                this.ROOT.BODY.OUT_DATA = {}
        }
};

