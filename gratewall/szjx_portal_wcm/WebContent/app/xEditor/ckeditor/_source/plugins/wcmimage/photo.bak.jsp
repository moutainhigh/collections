<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../../../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
try{
%>
<%@include file="../../../../../include/public_server.jsp"%>
<%@include file="../../../../../include/convertor_helper.jsp"%>

<%
	MethodContext oMethodContext1 = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	//对象定义
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	//指定要调用的服务/方法名
	String sServiceId = "wcm6_viewdocument", sMethodName = "query";
	//指定参数
	HashMap parameters = new HashMap();
	parameters.put("ChannelIds", "82");
	//执行服务请求
	ViewDocuments result = (ViewDocuments) processor.excute(sServiceId,sMethodName, parameters);
		//System.out.println(result.getAt(0));
		//System.out.println("hello");
	//int nCurrDocId = 0;
	//if (oMethodContext1 != null) {
	//	nCurrDocId = oMethodContext1.getValue("CurrDocId", 0);
	//}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="photo_lib.css" rel="stylesheet" type="text/css" />
	<title>Photo_Lib</title>
</head>

<body>
	<div id="" class="div_header">
		<div id="" class="div_input">
			图片库
		</div>
		<div id="" class="div_input_img">
			
		</div>
		<div id="" class="div_input">
				尺寸大小
		</div>
		<div id="" class="div_input_img">
			
		</div>
		
		<div id="" class="div_search" contentEditable="true">
			
		</div>
	</div>
	<div id="" class="div_box">
		<ul id="" class="">


<%
	//遍历结果集
	ViewDocuments objs = (ViewDocuments)result;
	for (int i = 0; i <= result.size(); i++) {
		try{
			ViewDocument obj = (ViewDocument)objs.getAt(i - 1);
			if (obj == null)
				continue;
			Document currDocument = Document.findById(obj.getDocId());
			if(currDocument == null){
				throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("../../../../../photo/photo_list_editor_query.jsp.doc_notfound", "没有找到指定ID为[{0}]的文档!"), new int[]{ obj.getDocId()}));
			}
			int nDocId = currDocument.getId();
			int nRowId = obj.getChnlDocProperty("recid",0);		
			ChnlDoc chnldoc = ChnlDoc.findById(nRowId);
			if(chnldoc == null){
				throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("photo_list_editor_query.jsp.chnldoc_notfound", "没有找到指定ID为[{0}]的chnldoc!"), new int[]{nRowId}));
			}
			int nModal = chnldoc.getPropertyAsInt("MODAL",0);
			System.out.println(nModal);
			String sFileNameLike = currDocument.getPropertyAsString("docrelwords");
			String sDefault = currDocument.getAttributeValue("srcfile");
			String sPicUrl = currDocument.getRelateWords();
			String sFileName = mapfile(sFileNameLike,0,sDefault);			
			//boolean bIsSelected = nCurrDocId==nDocId || strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = obj.getRightValue(loginUser).toString();
%>

			<li class="" id="thumb_item_<%=nRowId%>">
				<div id="" class="div_img">
					<img src="<%=sFileName%>" style="cursor:hand;height:94px;width:112px;"/>
				</div>
				<div id="" class="">
					<span class="span_font" title="<%=CMyString.filterForHTMLValue(currDocument.getTitle())%>"><%=CMyString.filterForHTMLValue(currDocument.getTitle())%></span>
					<input type="checkbox" name="" class='img_checkbox'>
				</div>
			</li>

<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>

<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("photo_list_editor_query.jsp.label.runtimeexception", "watermark_query.jsp运行期异常!"), tx);
}
%>

		</ul>
	</div>
	<!-- <div id="" class="div_foot">
		页码
	</div>
 --></body>
</html>

<%!
	private String mapfile(String sFileName,int temp,String _default) throws WCMException{
		if(sFileName == null || (sFileName.trim()).equals("")){
				return "../../../../../images/photo/pic_notfound.gif";
			}
			String[] fn = sFileName.split(",");
			String r = "";
			if(fn.length <= temp){
				r = "../../../../../../file/read_image.jsp?FileName=" +  _default;
				return r;
			}
			r = fn[temp];
			return "/webpic/" + r.substring(0,8) + "/" + r.substring(0,10) + "/" + r;	
	}
%>