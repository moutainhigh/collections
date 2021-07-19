/*****************知识状态***********************/
var doc_state={};
doc_state.unchecked="1";	//未审核
doc_state.checked="2";		//已审核
doc_state.published="3";	//已发布
doc_state.backmodify="4";	//退回修改


/****************************************/
var pathname = location.pathname ;
var appName = pathname.replace(/\/(\w+).*/g,'$1');
var rootPath=location.protocol+"//"+location.host+"/"+appName;
document.write('<link href="'+rootPath+'/page/script/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />');
document.write('<link href="'+rootPath+'/page/script/easyui/themes/icon.css" rel="stylesheet" type="text/css" />');
document.write('<link href="'+rootPath+'/page/css/knowledge.css" rel="stylesheet" type="text/css" />');
document.write('<link href="'+rootPath+'/page/script/ztree/css/zTreeStyle.css" rel="stylesheet" type="text/css" />');
document.write('<script src="'+rootPath+'/page/script/jquery-1.8.3.js" type="text/javascript"></script>');
document.write('<script src="'+rootPath+'/page/script/ztree/jquery.ztree.all-3.5.js" type="text/javascript"></script>');
document.write('<script src="'+rootPath+'/page/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>');
document.write('<script src="'+rootPath+'/page/script/easyui/jquery.easyui.min.js" type="text/javascript"></script>');
document.write('<script src="'+rootPath+'/page/script/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>');
document.write('<script src="'+rootPath+'/page/script/common/util.js" type="text/javascript"></script>');
