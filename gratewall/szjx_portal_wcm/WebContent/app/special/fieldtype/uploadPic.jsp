<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.wcm.photo.IImageLibConfig" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.presentation.plugin.PluginConfig"%>
<%!		
		private String dealWithUploadPic(WidgetInstance oWidgetInstance,WidgetParameter _currWidgetParameter,boolean _bAdd)throws WCMException{
			String sDefaultValue = _currWidgetParameter.getDefaultValue();
			String sParameterValue="";
			String sParamName = _currWidgetParameter.getWidgetParamName();
			String srcStr = "../images/wt.gif";
			if(_bAdd){
				sParameterValue = sDefaultValue;
			}else{
				sParameterValue = getParameterValue(oWidgetInstance, _currWidgetParameter);
				if(CMyString.isEmpty(sParameterValue)){
					srcStr = "../images/wt.gif";
				}
				else{
					srcStr = "../../../file/read_image.jsp?FileName="+sParameterValue;
				}
			}
			//拼接html片段
			StringBuffer sb = new StringBuffer();
			sb.append("<DIV id='"+sParamName+"_row'class='row'><DIV class='left'><DIV class='pic_show'style='overflow:hidden;'>");

			sb.append("<DIV id='flashcontent_box'class='hide'><DIV id='flashcontent'class='flashcontent'></DIV></DIV>");

			sb.append("<IMG style='height:80px;' id='pic_element' src='"+srcStr+"' ><br/><SPAN id='pic_size'>");
			
			sb.append("</SPAN></DIV></DIV><DIV class='label hide'>上传图片：</DIV>");
			sb.append("<DIV class='right'><SCRIPT language='javascript'>new com.trs.ui.XAppendix({disabled:0,name:'");
			sb.append(CMyString.filterForJs(sParamName));
			sb.append("',value:'");
			sb.append(CMyString.filterForJs(sParameterValue));
			sb.append("',pathFlag:'");
			sb.append(CMyString.filterForJs(FilesMan.FLAG_WEBFILE));
			sb.append("',validation:'type:\"string\",required:\"0\",desc:\"附件管理\",allowExt:\"jpg,gif,png,bmp,swf\"'}).render();");

			sb.append("</SCRIPT><BUTTON id='btn-pic-edit' class='btn-pic-edit' disabled");

			sb.append(" onclick=\"pic_edit('"+sParamName+"')\" type='submit'></BUTTON></DIV></DIV>");
			return sb.toString();
		}
%>
<style type="text/css">
	#pic_element{
		display:block;
		margin:0 auto;
	}
	#LOGO_TITLE{
		width:150px;
	}
	.pic_show{
		text-align:center;
		overflow:auto;
		height:120px;
	}
	.left{
		float:left;
		width:76%;
		padding:5px;
		border-right:1px solid gray;
		height:125px;
	}
	.right{
		float:left;
		padding-left:10px;
	}	
	.XAppendix{
		display:block;
	}
	.XAppendix .appendix-text{
		display:none;
	}
	.XAppendix .appendix-browser{
		background:url('../images/widget/upload.gif');
		width:75px;
		height:25px;
		margin:5px 0;
	}
	.XAppendix .appendix-delete{
		background:url('../images/widget/delete.gif');
		width:75px;
		height:25px;
		margin:5px 0;
	}
	.btn-pic-edit{
		background:url('../images/widget/edit.gif');
		width:75px;
		height:25px;
		margin:5px 0;
		border:0px;
	}
	.btn-pic-edit.disabled{
		background:url('../images/widget/edit_gray.gif');
		width:75px;
		height:25px;
		margin:5px 0;
		border:0px;
	}
	.pic_info{
		padding-left:10px;
		display:inline;
		color:blue;
	}
	.pic_reminder{
		padding-left:10px;
		color:blue;
		display:inline;
	}
	.hide{
		display:none !important;
	}
</style>
<script>
var bEnablePicLib = <%=CMyString.filterForJs(""+PluginConfig.isStartPhoto())%>;//判断是否存在图片库选件
function validData(){	
	var components = com.trs.ui.ComponentMgr.getAllComponents();
	for (var i = 0; i < components.length; i++){
		var validation = components[i].getProperty('validation');
		if(!validation) continue;
		if(!ValidationHelper.validByValidation(validation, components[i].getValue())) return false;
	}
	return true;
};

function makeData(fn){
	if(!appNameAfterEdit){
		com.trs.ui.XAppendixMgr.upload(
			function(){
				//校验
				if(!validData()){
					return false;
				}
				//调用父页面的保存
				fn('data');
			},
			function(){
				Ext.Msg.alert(arguments[0]);
			}
		);
	}else{
		// 在组装数据之前需要，拷贝编辑后的文件到指定WEBPIC的目录下
		var parame = "fileName="+$(appNameAfterEdit).value+"&pathFlag=W0";
		YUIConnect.setForm(appNameAfterEdit+"-frm", true, Ext.isSecure);
		YUIConnect.asyncRequest('POST',"logo_update_after_edit.jsp?"+parame,{
			"upload":function(_transport){
				var sResponseText = _transport.responseText;
				eval("var result="+sResponseText);
				$(appNameAfterEdit).value = result["Message"];
				com.trs.ui.XAppendixMgr.upload(
					function(){
						//校验
						if(!validData()){
							return false;
						}
						//调用父页面的保存
						fn('data');
					},
					function(){
						Ext.Msg.alert(arguments[0]);
					}
				);
			}
		});
	}
}

/*AjaxCaller.onSuccess = function(transport, json){
	
	var cbr = wcm.CrashBoarder.get(window);
	cbr.notify(true);
	cbr.close();
};*/
Event.observe(window, 'load', function(){
	initValidation1();
	//validData();
});
/*
*初始化校验处理
*/
function initValidation1(){
	ValidationHelper.addValidListener(function(){
		//wcmXCom.get('').enable();
	},"data");
	ValidationHelper.addInvalidListener(function(){
		//wcmXCom.get('').disable();
	},"data");
	ValidationHelper.initValidation();
};

/*
* 显示flash图像
*/
function showFlash(swfPath){
	var flashvars = {
		autoPlay:"false",
		logoAlpha:0,
		isAutoBandWidthDetection:"false",
		videoSource : swfPath
	};
	swfobject.embedSWF(swfPath, "flashcontent", 
				   400, 100, "9.0.124", false, flashvars, {allowFullScreen:"true",wmode:"opaque"}, {});
	Element.addClassName(pic_elem,"hide");//.style.display="none";
	Element.removeClassName(flash_elem,"hide");
	//$("flashcontent").style.display="";
	disabledEditButton(true);
	$("pic_size").innerHTML = "";

	Element.removeClassName($("WIDTH_row"),"hide");
	Element.removeClassName($("HEIGHT_row"),"hide");
	if(!dimension)return;
	// 设置flash的宽度，高度取默认值
	$("WIDTH").value=dimension.width;
}


var dimension = null;
/*
* 编辑图片成功后设置新的图片
*/
var srcStr = "../images/wt.gif";
function refesh(_sFileName,_el,_path){
	/*if(isFlash(_sFileName)){
		showFlash(_path);
		return;
	}*/
	Element.removeClassName(pic_elem,"hide");
	Element.addClassName(flash_elem,"hide");
	Element.addClassName($("WIDTH_row"),"hide");
	Element.addClassName($("HEIGHT_row"),"hide");
	pic_elem.style.display="";
	// 更换显示的图片
	if(_sFileName.indexOf("http")>=0)
		pic_elem.src = _sFileName;
	else
		pic_elem.src="../../../file/read_image.jsp?FileName="+_sFileName;
	// 更新附件名称
	if($(_el))
		$(_el).value=_sFileName;
	// 重置图片的信息
	currImg.src=pic_elem.src;
}

/*
* 判断一个文件是否是flash文件

function isFlash(_sFileName){
	return _sFileName.substring(_sFileName.lastIndexOf(".")+1).toLowerCase()=="swf";
}*/

/*
* 设置图片的合适的size
*/
function setSuitSize(){
	if(currImg.width>400)
		pic_elem.style.width="320px";
	else
		pic_elem.style.width=currImg.width+"px";
}
/*
*	判断是否有图片之后的操作
*/
function hasPic(_boolean){
	if(!_boolean){
		Element.removeClassName(pic_elem,"hide");
		Element.addClassName(flash_elem,"hide");
		pic_elem.src=srcStr;
		// 显示无图片
	}
	currImg.src=pic_elem.src;
	disabledEditButton(!_boolean);
	Element.addClassName($("WIDTH_row"),"hide");
	Element.addClassName($("HEIGHT_row"),"hide");
}

var appNameAfterEdit = null;
/*
*	图片编辑接口
*/
function pic_edit(sParamName){
	 if(!bEnablePicLib){
		Ext.Msg.error("缺少编辑图片的相关类，请先确认是否正确安装了图片库选件！");
		return;
	}
	appNameAfterEdit = sParamName;
	var oImg = currImg;
	var nWidth	= window.screen.width - 12;
	var nHeight = window.screen.height - 60;
	var parameters = "photo="+encodeURIComponent(oImg.src);
	if(suit_width>0){
		parameters+="&Width="+suit_width;
	}
	var sUrl = WCMConstants.WCM6_PATH + "photo/photo_compress.jsp?"+parameters;
	var dialogArguments = window;
	var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
	bound = window.showModalDialog(sUrl, dialogArguments, sFeatures);
	if(!bound)
		return;
	refesh(bound.FN,sParamName);
}

/*
*	当前图片
*/
var currImg = new Image();
var pic_elem;
var flash_elem;
Event.observe(currImg,"load",function(){
	initCurrImg();
});
Event.observe(window,"load",function(){
	init();
});

/*
*	文档载入初始化
*
*/
function init(){
	// 初始化信息显示
	pic_elem=$("pic_element");
	flash_elem = $("flashcontent_box");
	if(!flash_elem){return;}
	if(pic_elem.src.indexOf("=") < 0){
		hasPic(false);
	}
	if(pic_elem.src.indexOf("images/wt.gif")>0){
		disabledEditButton(true);
	}
	else{
		disabledEditButton(false);
	}
	setSuitableSize();
	var fileName = pic_elem.getAttribute("filename");
	
	refesh(pic_elem.src);
	
	// 附件组件事件绑定
	var appendixs = com.trs.ui.XAppendixMgr.getAllComponents();
	for(var i=0;i<appendixs.length;i++){
		appendixs[i].addListener({
			'change' : function(){
				var sValue = this.getValue();
				//validdata
				if(!validData()){
					hasPic(false);
					return false;
				}
				// 上传操作
				this.upload(function(sValue){
					hasPic(sValue && sValue.length>0);
					//发送请求，获取flash文件的网络地址
					refesh(sValue,null,arguments[1]);
				},function(){
					alert(arguments[0].toString());
				});
			},
			'delete' : function(){ 
					hasPic(false);
			}
		});
	}
	//$('<%=CMyString.filterForJs(FilesMan.FLAG_WEBFILE)%>'+'-browser-btn').title = "允许上传jpg,gif,png,bmp,swf格式的文件!";
}
var picEditBtns=null;
function disabledEditButton(_TrueOrFalse){
	if(!picEditBtns)
		picEditBtns = document.getElementsByClassName("btn-pic-edit");
	for(var i=0;i<picEditBtns.length;i++){
		if(_TrueOrFalse){
			picEditBtns[i].disabled="disabled";
			Element.addClassName(picEditBtns[i],"disabled");;
		}else{
			picEditBtns[i].disabled="";
			Element.removeClassName(picEditBtns[i],"disabled");;
		}
	}
}

/*
*	初始化当前图片
*/
function initCurrImg(){
	var pic_size_el=$("pic_size");
	if(!pic_size_el|| !pic_elem){
		setTimeout(initCurrImg,10);
		return;
	}
	pic_size_el.innerHTML="当前图片尺寸："+currImg.width+"×"+currImg.height+" px";
	setSuitSize();
}
var suit_width = 0;

// 设置合适的图片大小信息
function setSuitableSize(){
	try{
		dimension = top.PageController.getMainWin().PageController.execute('getCurrentWidgetDimension');
		$("suitabled_size").innerHTML=dimension.width+" px"
		suit_width = dimension.width;
	}catch(error){
	}

}

//-->
</script>