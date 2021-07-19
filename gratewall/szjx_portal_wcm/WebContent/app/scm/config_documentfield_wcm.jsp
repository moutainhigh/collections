<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map.Entry" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContentTemplate" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%	
	JSPRequestProcessor oProcessor = new JSPRequestProcessor();
	HashMap hParameters = new HashMap();

	// 接收参数
	int nHostId = currRequestHelper.getInt("HostId",0);
	int nHostType = currRequestHelper.getInt("HostType",0);
	int nSiteType = currRequestHelper.getInt("SiteType",0);
	MetaView oMetaView = null;

	// 判断栏目对象或站点对象等是否存在
	if(nHostType == Channel.OBJ_TYPE){
		Channel oCurrChannel = Channel.findById(nHostId);
		if(oCurrChannel == null){
%>
			[{ErrorType:'1'}]
<%
			return;
		}
		//资源库的栏目的判断
		if(nSiteType == 4){
			hParameters.put("channelId",nHostId);
			oMetaView = (MetaView)oProcessor.excute("wcm6_MetaDataDef","getViewFromChannel", hParameters);
			hParameters.clear();
			oProcessor.reset();
		}
	}else if(nHostType == WebSite.OBJ_TYPE){
		WebSite oWebSite = WebSite.findById(nHostId);
		if(oWebSite == null){
%>
			[{ErrorType:'1'}]
<%
			return;
		}
	}
	hParameters.put("HostId", String.valueOf(nHostId));
	hParameters.put("HostType", String.valueOf(nHostType));

	String sMiroContentStyle = null;
	int nObjectId = 0;
	Object result = oProcessor.excute("wcm61_scmmicrocontenttemplate","findByHost", hParameters);
	if(result != null){
		nObjectId = ((SCMMicroContentTemplate) result).getId();
		sMiroContentStyle = ((SCMMicroContentTemplate) result).getMicroContentStyle();
	}
%>
	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配置从WCM发布微博时使用的文档字段</title>
<link rel="stylesheet" href="create_microblog.css">
<style type="text/css">
	*{
		font-size:12px;
		margin:0px;
		padding:0px;
	}	
	.row{
		margin:10px;
		float:left;
	}
	.label{
		line-height:18px;
		width:85px;
		text-align:right;
		vertical-align:top;
		display:inline-block;
	}

	select{
		width:340px;
		border:1px solid silver;
	}
	textarea{
		width:340px;
		height:200px;
		border:1px solid silver;
	}
</style>
</head>
<body>
<!--获取配置字段的详细信息-->
<%
	HashMap<String, String> oDefaultFields = new HashMap<String,String>();
	if(nSiteType != 0 && oMetaView != null){
		MetaViewFields oFieldsResult = oMetaView.getViewFields(User.getSystem(),null);
		MetaViewField oCurrMetaViewField = null;
		if(oFieldsResult != null && oFieldsResult.size() > 0){
			for(int i = 0; i < oFieldsResult.size(); i++){
				oCurrMetaViewField = (MetaViewField)oFieldsResult.getAt(i);
				if(oCurrMetaViewField != null){
					oDefaultFields.put(oCurrMetaViewField.getName(),oCurrMetaViewField.getAnotherName());
				}
			}
		}
	}else{
		//默认配置字段
		oDefaultFields.put("DOCTITLE","标题");
		oDefaultFields.put("SUBDOCTITLE","副标题");
		oDefaultFields.put("DOCABSTRACT","摘要");
		oDefaultFields.put("DOCKEYWORDS","关键字");
		oDefaultFields.put("DOCPUBURL","发布URL地址");
		oDefaultFields.put("DOCAUTHOR","作者");
		oDefaultFields.put("DOCSOURCENAME","来源");
		oDefaultFields.put("DOCPUBTIME","发布时间");
		oDefaultFields.put("DOCRELTIME","撰写时间");
	}

%>
	<div class="row">
		<span class="label">可选字段：</span>
		<span >
			<select name="viewFields" id="viewFields" onchange = "writeContent(this)">
				<option value=0>--请选择一个字段模板--</option>
				<%
				if(oDefaultFields.size() > 0 ){
					Iterator iter = oDefaultFields.entrySet().iterator();
					Entry oNext = null;
					String sKey = "", sValue="";
					while(iter.hasNext()){
						oNext = (Entry)iter.next();
						if(oNext != null){
							sKey= (String)oNext.getKey();
							sValue= (String)oNext.getValue();
				%>
							<option value="<%=CMyString.filterForHTMLValue(sKey.toUpperCase())%>" ><%=CMyString.filterForHTMLValue(sValue)%></option>
				<%
						}
					}
				}%>
			<!--以下文档字段不常用，屏蔽掉，如果以后需要添加，可以再使用。
				<option value="DOCCHANNEL" >所属栏目</option>
				<option value="DOCVERSION" >版本号</option>
				<option value="DOCTYPE" >类型</option>
				<option value="DOCRELWORDS" >相关词</option>
				<option value="DOCEDITOR" >修改者</option>
				<option value="DOCAUDITOR" >审稿人</option>
				<option value="DOCSECURITY" >安全级</option>
				<option value="DOCKIND" >分类对象</option>
				<option value="DOCPEOPLE" >人物</option>
				<option value="DOCPLACE" >地点</option>
				<option value="DOCWORDSCOUNT" >正文字数</option>
				<option value="DOCFLAG" >文档标志</option>
				<option value="HITSCOUNT" >文档点击数</option>
				<option value="DOCCONTENT" >正文内容</option>-->
            </select>
        </span>
    </div>
	<div class="row">
        <span class="label">微博样式：</span>
        <span>
            <textarea name="DocContent" id="DocContent" rows="6" cols="50"><%=CMyString.filterForHTMLValue(sMiroContentStyle)%></textarea>			
		</span>
    </div>
<script src="js/jquery-1.7.2.min.js"></script>
<script src="js/iframe_public.js"></script>
<script>
	function writeContent(oSelect){
		var fieldValue = oSelect.value;
		if(fieldValue != 0){
			fieldValue = "${" + fieldValue + "}";
			var docContent = document.getElementById("DocContent").value;
			docContent+= fieldValue;
			document.getElementById("DocContent").value = docContent;
		}
	}
	function docWordsLength(_docWords){
		var content=_docWords;
		var iLen=0;
		for (var i = 0; i < content.length; i++) {
			iLen += /[^\x00-\xff]/g.test(content.charAt(i)) ? 1 : 0.5;
		};
		iLen=Math.ceil(iLen);
		return iLen;
	}
	var m_cbCfg = {
		btns:[
			{//绘制确定按钮
				text: '确定',
				cmd:function(){
					//获取选中的栏目ID
					var hostId = <%=nHostId%>;
					var hostType = <%=nHostType%>;
					var objectId = <%=nObjectId%>;
					if(hostId == 0){
						alert("没有接收到对象ID！");
						return false;
					}
					var microStyle = document.getElementById("DocContent").value;
					if(microStyle == null || $.trim(microStyle).length == ""){
						alert("请选择文档字段！");
						return false;
					}
					// 获取所有文档字段值
					var docCount = $("#viewFields option").length;
					var docWords = "";
					for(var i = 1;i < docCount;i++){
						docWords += $("#viewFields option")[i].value + ",";
					}
					// 查找配置的字段
					var docWordMatch = "\\$\\{[A-Z]+\\}";
					var reg=new RegExp(docWordMatch,"mg");//创建正则表达式样式.
					var matchWords = new Array();
					matchWords = microStyle.match(reg);
					var content = microStyle;
					// 将配置的文档字段进行过滤，去掉文档字段，以计算其它字数
					if(matchWords != null && matchWords.length > 0){
						for(var j = 0;j < matchWords.length;j++){
							if(docWords.search(matchWords[j])){
								content = content.replace(matchWords[j],"");
							}
						}
					}
					if(docWordsLength($.trim(content)) > 140){
						alert("除文档字段外，输入的文字不能大于140！");
						return false;
					}
					var jsonParams = {ObjectId:objectId,HostId:hostId,HostType:hostType,MicroContentStyle:microStyle};
					this.notify(jsonParams);
				}
			},
			{//绘制取消按钮
				text:'取消',
				cmd: function(){
					this.close();
				}
			}
		]	
	};
</script>
</body>
</html>
