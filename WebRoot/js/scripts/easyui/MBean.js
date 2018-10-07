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