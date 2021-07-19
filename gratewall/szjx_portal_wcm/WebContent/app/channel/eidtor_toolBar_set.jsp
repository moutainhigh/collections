<%--
/** Title:			Document_addedit.jsp
 *  Description:
 *		栏目位置设置页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2006-03-28 19:56:25
 *  Vesion:			1.0
 *  Last EditTime:	2006-03-28 / 2006-03-28
 *	Update Logs:
 *		TRS WCM 5.2@2006-03-28 产生此文件
 *
 *  Parameters:
 *		see document_position_set.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.presentation.util.ResponseHelper" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_processor.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//1.初始化(获取数据)
	RequestHelper currRequestHelper = new RequestHelper(request, response, application);
	currRequestHelper.doValid();
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);	
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("flowemployee.object.not.found.channel", "没有找到ID为{0}的栏目"), new int[]{nChannelId}));
	}
	String sDefaultToolbar = "DocumentProps,Accessibility,Print,Cut,Copy,Paste,PasteText,PasteWord,Find,Replace,Undo,Redo,SelectAll,InlineLink,Link,Unlink,Image,PhotoLib,Flash,ImportFlash,FlashLib,Templates,Table,Comment,FitWindow,ContentLink,ContentUnLink,Smiley,SpecialChar,+1,FontName,FontSize,Bold,Italic,Underline,TextColor,BGColor,RemoveFormat,JustifyLeft,JustifyFull,JustifyCenter,JustifyRight,OrderedList,UnorderedList,Outdent,Indent,PageBreak,InsertSepTitle,AdInTrs,CKMSpellCheck,AutoSaveHistory";
	String sDefaultAdvToolbar = "DocumentProps,Accessibility,Print,Cut,Copy,Paste,PasteText,PasteWord,Find,Replace,Undo,Redo,SelectAll,InlineLink,Link,Unlink,Anchor,Image,PhotoLib,Flash,ImportFlash,FlashLib,Templates,Table,Comment,FitWindow,ContentLink,ContentUnLink,Rule,Smiley,SpecialChar,OrderedList,UnorderedList,Outdent,Indent,+1,Style,FontName,FontSize,Bold,Italic,Underline,StrikeThrough,Subscript,Superscript,TextColor,BGColor,RemoveFormat,JustifyLeft,JustifyCenter,JustifyRight,JustifyFull,PageBreak,InsertSepTitle,AdInTrs,CKMSpellCheck,AutoSaveHistory";
	String sResult[][]  = {{"DocumentProps",LocaleServer.getString("editor.DocumentProps", "页面属性"),"192"},{"Accessibility",LocaleServer.getString("editor.Accessibility", "无障碍校验"),"1408"},{"Print",LocaleServer.getString("editor.Print", "打印"),"1168"},{"Cut",LocaleServer.getString("editor.Cut", "剪切"),"1248"},{"Copy",LocaleServer.getString("editor.Copy", "复制"),"1232"},{"Paste",LocaleServer.getString("editor.Paste", "粘贴"),"1216"},{"PasteText",LocaleServer.getString("editor.PasteText", "粘贴为无格式文本"),"1200"},{"PasteWord",LocaleServer.getString("editor.PasteWord", "从MS WORD粘贴"),"1184"},{"Find",LocaleServer.getString("editor.Find", "查找"),"1104"},{"Replace",LocaleServer.getString("editor.Replace", "替换"),"1088"},{"Undo",LocaleServer.getString("editor.Undo", "撤销"),"1136"},{"Redo",LocaleServer.getString("editor.Redo", "重复"),"1120"},{"SelectAll",LocaleServer.getString("editor.SelectAll", "全选"),"1072"},{"InlineLink",LocaleServer.getString("editor.InlineLink", "插入内联文档链接"),"80"},{"Link",LocaleServer.getString("editor.Link", "插入超链接"),"816"},{"Unlink",LocaleServer.getString("editor.Unlink", "撤销超链接"),"800"},{"Image",LocaleServer.getString("editor.Image", "插入图片"),"768"},{"PhotoLib",LocaleServer.getString("editor.PhotoLib", "插入图片库图片"),"96"},{"Flash",LocaleServer.getString("editor.Flash", "插入多媒体"),"16"},{"ImportFlash",LocaleServer.getString("editor.ImportFlash", "源码方式引入外部视频"),"751"},{"FlashLib",LocaleServer.getString("editor.FlashLib", "插入视频库视频"),"160"},{"Templates",LocaleServer.getString("editor.Templates", "插入内容模板"),"1264"},{"Table",LocaleServer.getString("editor.Table", "插入表格"),"736"},{"Comment",LocaleServer.getString("editor.Comment", "插入批注"),"256"},{"FitWindow",LocaleServer.getString("editor.FitWindow", "全屏编辑"),"304"},{"ContentLink",LocaleServer.getString("editor.ContentLink", "热词替换"),"272"},{"ContentUnLink",LocaleServer.getString("editor.ContentUnLink", "取消当前热词替换"),"288"},{"Smiley",LocaleServer.getString("editor.Smiley", "插入表情图标"),"704"},{"SpecialChar",LocaleServer.getString("editor.SpecialChar", "插入特殊符号"),"688"},{"FontName",LocaleServer.getString("editor.FontName", "字体种类"),"1426"},{"FontSize",LocaleServer.getString("editor.FontSize", "字体大小"),"1442"},{"Bold",LocaleServer.getString("editor.Bold", "字体加粗"),"1040"},{"Italic",LocaleServer.getString("editor.Italic", "字体倾斜"),"1024"},{"Underline",LocaleServer.getString("editor.Underline", "下划线"),"1008"},{"TextColor",LocaleServer.getString("editor.TextColor", "字体颜色"),"640"},{"BGColor",LocaleServer.getString("editor.BGColor", "背景色"),"624"},{"RemoveFormat",LocaleServer.getString("editor.RemoveFormat", "清除格式"),"1056"},{"JustifyLeft",LocaleServer.getString("editor.JustifyLeft", "左对齐"),"880"},{"JustifyFull",LocaleServer.getString("editor.JustifyFull", "两端对齐"),"832"},{"JustifyCenter",LocaleServer.getString("editor.JustifyCenter", "居中对齐"),"864"},{"JustifyRight",LocaleServer.getString("editor.JustifyRight", "右对齐"),"848"},{"OrderedList",LocaleServer.getString("editor.OrderedList", "插入编号列表"),"944"},{"UnorderedList",LocaleServer.getString("editor.UnorderedList", "插入项目列表"),"928"},{"Outdent",LocaleServer.getString("editor.Outdent", "减少缩进量"),"912"},{"Indent",LocaleServer.getString("editor.Indent", "增加缩进量"),"896"},{"PageBreak",LocaleServer.getString("editor.PageBreak", "插入分页符"),"672"},{"AdInTrs",LocaleServer.getString("editor.AdInTrs", "插入广告位"),"240"},{"CKMSpellCheck",LocaleServer.getString("editor.CKMSpellCheck", "拼写检查"),"144"},{"AutoSaveHistory",LocaleServer.getString("editor.AutoSaveHistory", "恢复历史记录"),"32"},{"-",LocaleServer.getString("editor.Separate", "工具栏分隔符"),"1376"},{"Anchor",LocaleServer.getString("editor.Anchor", "插入锚点"),"784"},{"Rule",LocaleServer.getString("editor.Rule", "插入水平线"),"720"},{"Style",LocaleServer.getString("editor.Style", "插入样式"),"1458"},{"StrikeThrough",LocaleServer.getString("editor.StrikeThrough", "插入删除线"),"992"},{"Superscript",LocaleServer.getString("editor.Subscript", "插入上标"),"960"},{"Subscript",LocaleServer.getString("editor.Superscript", "插入下标"),"976"},{"+1",LocaleServer.getString("editor.Enter", "工具栏换行符"),"1360"},{"+2",LocaleServer.getString("editor.Enter", "工具栏换行符"),"1360"},{"InsertSepTitle",LocaleServer.getString("editor.pageTitle", "设置分页标题"),"528"}};
	String sUserName = loginUser.getName();
	String sTooolbar = currChannel.getPropertyAsString("Toolbar");
	String sSourceToolbar = sTooolbar;
	if(sTooolbar == null || sTooolbar.trim().length() == 0){
		sTooolbar = sDefaultToolbar;
	}else{
		String[] configGroup = sTooolbar.split("&");
		boolean hasFound = false;
		for(int i=0; i < configGroup.length;i++){
			if(configGroup[i].indexOf("=") < 0){
				sTooolbar =  configGroup[i];
				hasFound = true;
			}
			if(configGroup[i].indexOf(sUserName) >=0){
				sTooolbar = configGroup[i].split("=")[1];
				hasFound = true;
			}
			if(!hasFound) sTooolbar = sDefaultToolbar;
		}
	}
	String sAdvTooolbar = currChannel.getPropertyAsString("AdvToolbar");
	String sSourceAdvToolbar = sAdvTooolbar;
	if(sAdvTooolbar == null || sAdvTooolbar.trim().length() == 0){
		sAdvTooolbar = sDefaultAdvToolbar;
	}else{
		boolean hasFound = false;
		String[] configGroup = sAdvTooolbar.split("&");
		for(int i=0; i < configGroup.length;i++){
			if(configGroup[i].indexOf("=") < 0){
				sAdvTooolbar =  configGroup[i];
				hasFound = true;
			}
			if(configGroup[i].indexOf(sUserName) >=0){
				sAdvTooolbar = configGroup[i].split("=")[1];
				hasFound = true;
			}
		}
		if(!hasFound) sAdvTooolbar = sDefaultAdvToolbar;
	}
	String sTempDefaultAdvToolbar[] = sDefaultAdvToolbar.split(",");
//3.结束
	out.clear();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../js/data/locale/channel.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<!-- CarshBoard Inner Start -->
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<script src="../../app/js/source/wcmlib/core/LockUtil.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<link href="eidtor_toolBar_set.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Inner End -->
<script src="eidtor_toolBar_set.js"></script>
<script>
	window.m_cbCfg = {
		btns : [
			{
				text : wcm.LANG.CHANNEL_68 || '保存',
				cmd : function(){
					return saveData();
				},
				id: "btnSave"
			},
			{text : wcm.LANG.CHANNEL_69 || '关闭'}
		]
	};
</script>
</head>
<body>  
<table border=0 cellspacing=0 cellpadding=0 height="500px" style="table-layout:fixed;">
    <tr>
        <td>
            <table border=0 cellspacing=0 cellpadding=0 class="box">
                <tr style="vertical-align:top;">
                   <td style="width:16%;vertical-align:top;height:auto;"><div style="height:100%">
        <table class="TableContainer" border=0 cellspacing=0 cellpadding=0 style="height:400px;">
            <tr class="row_head">
                <td>
                    <div class="row_head_cell" WCMAnt:param="editor_toolbar_set.jsp.class">分类</div>
                </td>
            </tr>
            <tr>
                <td>
					<div style="position:relative;height:100%;">
                    <div class="row row_normal" id="Toolbar">
					<span style="width:20px;"><input type="radio" name="ToolbarButton" id="ToolbarButton" ></span>                
					<span class="tableAnotherName" WCMAnt:param="editor_toolbar_set.jsp.toolbar">简易工具栏</span>
					</div>
					<div class="row row_normal" id="advToolbar">
					<span style="width:20px;"><input type="radio" name="advToolbarButton" id="advToolbarButton"></span>                
					<span class="tableAnotherName" WCMAnt:param="editor_toolbar_set.jsp.advToolbar">高级工具栏</span>
					</div>
                </td>
            </tr>
        </table></div>
                    </td>
                      <td style="vertical-align:top;height:auto;"><div style="height:100%">
        <table class="FieldContainer" border=0 cellspacing=0 cellpadding=0 style="height:400px;">
            <tr class="row_head">
                <td>
                    <div class="row_head_cell" WCMAnt:param="editor_toolbar_set.jsp.sourceToolbar">待选工具栏</div>
                </td>
            </tr>
            <tr>
                <td>
					<div style="position:relative;height:100%;">
					<div id="FieldContainer" class="Container_content" style="position:absolute;width:100%;height:100%;">
					<%						
						if(sTempDefaultAdvToolbar.length > 0){
					%>
					<div id="ToobarItems">
					<%
						for(int i=0; i<sTempDefaultAdvToolbar.length;i++){
					%>
                     <div class="row row_normal">
						<input id="fck_<%=sTempDefaultAdvToolbar[i]%>" type="checkbox" name="ToolbarChecked" value="<%=sTempDefaultAdvToolbar[i]%>" display = "<%=display(sTempDefaultAdvToolbar[i],sResult)%>" scale="<%=getImageScale(sTempDefaultAdvToolbar[i],sResult)%>" onclick="PageContext.CheckConvert('<%=sTempDefaultAdvToolbar[i]%>','<%=display(sTempDefaultAdvToolbar[i],sResult)%>','<%=getImageScale(sTempDefaultAdvToolbar[i],sResult)%>')"/>
						<span onclick="PageContext.itemCheckConvert('<%=sTempDefaultAdvToolbar[i]%>','<%=display(sTempDefaultAdvToolbar[i],sResult)%>','<%=getImageScale(sTempDefaultAdvToolbar[i],sResult)%>')">
						<span for="fck_<%=sTempDefaultAdvToolbar[i]%>" style= "display:inline-block;background-position:0px <%=getImageScale(sTempDefaultAdvToolbar[i],sResult)%>px;" class="editor_toolbar_button">&nbsp;</span>
						<span for="fck_<%=sTempDefaultAdvToolbar[i]%>" title="<%=display(sTempDefaultAdvToolbar[i],sResult)%>" class="tableAnotherName"><%=display(sTempDefaultAdvToolbar[i],sResult)%></span>
						</span>
					</div>
					<%
							}
					%>
					</div>
					<%
								}
					%>					
					</div>
					</div>
                </td>
            </tr>
        </table></div>
                    </td>
                   <td style="width:40%;vertical-align:top;height:auto;"><div style="height:100%">
        <table class="ViewContainer" border=0 cellspacing=0 cellpadding=0 style="height:400px;">
            <tr class="row_head">
                <td id="viewHead">
                    <div class="row_head_cell" key="SearchField" WCMAnt:param="editor_toolbar_set.jsp.destToolbar">目标工具栏</div>
                </td>
            </tr>
            <tr>
                <td style="vertical-align:top;height:auto;">
					<div style="position:relative;height:100%;">
                    <div id="ViewContainer" class="Container_content" style="position:absolute;width:100%;height:100%;">
					</div>
					</div>
                </td>
            </tr>
        </table></div>
                    </td>
                </tr>
                <tr>
                    <td style="height:30px;">
		<div class="FieldCommand" id="FieldCommand">
             <input type="checkbox" name="hiddenAdv" id="hiddenAdv" value="">
			 <label for="hiddenAdv" WCMAnt:param="editor_toolbar_set.jsp.hiddenAdvance">禁用高级工具栏</label>        
        </div>  
					</td>
                    <td>
        <div class="FieldCommand" id="FieldCommand">
            <button type="button" id="fieldSelectAllBtn" WCMAnt:param="editor_toolbar_set.jsp.selAll">选择全部</button>
            <button type="button" id="fieldDeselectAllBtn" WCMAnt:param="editor_toolbar_set.jsp.deSelAll">取消全部</button> 
			 <button type="button" id="returnToDefault" WCMAnt:param="editor_toolbar_set.jsp.toDefault">还原默认值</button>
        </div>                    
                    </td>
                    <td>
        <div class="ViewCommand" id="ViewCommand">
            <button type="button" id="UpBtn" WCMAnt:param="editor_toolbar_set.jsp.upper">上移</button>
            <button type="button" id="DownBtn" WCMAnt:param="editor_toolbar_set.jsp.lower">下移</button>
            <button type="button" id="DeleteBtn" WCMAnt:param="editor_toolbar_set.jsp.delete">删除</button>
        </div>                    
                    </td>
                </tr>
				<tr>
					<td style="height:30px;" colspan="3">
					<span style="padding-left:3px;" WCMAnt:param="eidtor_toolBar_set.jsp.selectsynway">选择同步方式：</span>
		  <input type="radio" name="syn" id="noSyn" value="0" checked>
			 <label for="noSyn" WCMAnt:param="editor_toolbar_set.jsp.noSyn">不同步</label> 		
			 <input type="radio" name="syn" id="synToAll" value="1">
			 <label for="synToAll" WCMAnt:param="editor_toolbar_set.jsp.synToAll">同步到所有栏目</label> 		
			 <input type="radio" name="syn" id="synToCertain" value="2" onclick="showLink()">
			 <label for="synToCertain" WCMAnt:param="editor_toolbar_set.jsp.synToCertain">同步到指定栏目</label> <span style="padding-left:20px;"></span>
			 <a id="ChnlSlt" href="#" onclick="selectChnl(event)" style="display:none;" WCMAnt:param="eidtor_toolBar_set.jsp.selectchannel">选择栏目</a>
			 		</td>
				</tr>
            </table>
        </td>
    </tr>
</table>
<input type="hidden" id="ToolbarValue" name="Toolbar" value="<%=sTooolbar%>"/>
<input type="hidden" id="AdvToolbarValue" name="AdvToolbar" value="<%=sAdvTooolbar%>"/>
<input type="hidden" id="ToolbarDefault" name="ToolbarDefault" value="<%=sDefaultToolbar%>"/>
<input type="hidden" id="AdvToolbarDefault" name="AdvToolbarDefault" value="<%=sDefaultAdvToolbar%>"/>
<input type="hidden" id="UserName" name="UserName" value="<%=sUserName%>"/>
<input type="hidden" id="SourceToolbar" name="SourceToolbar" value="<%=sSourceToolbar%>"/>
<input type="hidden" id="SourceAdvToolbar" name="SourceAdvToolbar" value="<%=sSourceAdvToolbar%>"/>
<input type="hidden" name="SelectedIds" id="SelectedIds"/>
</body>
</html>
<%!
	private String display(String _sOriginalValue,String _sResult[][]){
		for(int i=0; i< _sResult.length; i++){
			if(_sOriginalValue.equals(_sResult[i][0])){
				return _sResult[i][1];
			}
		}
		return "";
	}
	private String getImageScale(String _sOriginalValue,String _sResult[][]){
		for(int i=0; i< _sResult.length; i++){
			if(_sOriginalValue.equals(_sResult[i][0])){
				return _sResult[i][2];
			}
		}
		return "1";
	}
%>