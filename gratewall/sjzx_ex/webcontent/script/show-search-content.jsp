<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.web.tag.util.DataForm" %>

<freeze:html title="show content" layout="">
<freeze:include href="/script/gwssi-xtree.js"/>
<freeze:include href="/script/struts-coolmenus3.js"/>

<style type="text/css">
DIV {
	scrollbar-face-color : #88BBFF;
	scrollbar-highlight-color : #99CCFF;
	scrollbar-3dlight-color : #4477ff;
	scrollbar-darkshadow-color : #4477ff;
	scrollbar-track-color : #339966;
	scrollbar-arrow-color : #0000ff;
	scrollbar-shadow-color : #88BBFF;
}

TD {
	font-size: 10pt;
	font-family: 宋体;
}
</style>


<script language="javascript">
// 取原始窗口的大小
var	bLayer = parent.document.all.innerWindow;
oldwidth = bLayer.clientWidth;
oldheight = bLayer.clientHeight;

var maxWidth = parent.document.body.clientWidth * 0.8
if( oldwidth < maxWidth ){
	parent.setInnerWindowWidth( maxWidth );
}

// 错误处理
function sh_errorHandle()
{
	return true;
}

onerror = sh_errorHandle;

// 设置标题
function setResultTitle( img, topic )
{
	// 生成标题
	var	title = '';
	if( img != null && img != '' ){
		title = '<img src="' + rootPath + img + '" width="12" height="12">';
	}
	
	title = title + topic;
	
	var o = document.getElementById('topic');
	o.innerHTML = title;
	
	// 计算宽度
	if( oldwidth > o.offsetWidth ){
		parent.setInnerWindowWidth( oldwidth );
	}
	else if( bLayer.clientWidth > o.offsetWidth + 10 ){
		oldwidth = o.offsetWidth + 10;
		parent.setInnerWindowWidth( oldwidth );
	}
}

// 设置内容
function resizeWindow( )
{
	var t = document.getElementById('topic');
	var o = document.getElementById('content');
	var h = o.offsetHeight + t.offsetHeight + 10;
	var	maxHeight = parseInt( parent.document.body.clientHeight * 0.8 );
	
	// 计算高度
	if( h > maxHeight && maxHeight > oldheight ){
		parent.setInnerWindowHeight( maxHeight );
	}
	else if( oldheight < h ){
		parent.setInnerWindowHeight( h );
	}
	
	// 宽度
	var w = o.offsetWidth + 20;
	var	maxWidth = parseInt( parent.document.body.clientWidth * 0.8 );
	if( w > oldwidth ){
		if( w > maxWidth ){
			oldwidth = maxWidth;
		}
		else{
			oldwidth = w;
		}
		
		parent.setInnerWindowWidth( oldwidth );
	}
	
	parent.setInnerWindowVisible();
}
</script>

<body bgcolor="#FFFFFF" style="font-size: 10pt; line-height: 15pt" leftmargin=0 topmargin=0 onload="setTimeout(resizeWindow,50)">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" style="table-layout:fixed; border: 1px solid #3366FF">
<tr height="18" bgcolor="3366FF">
  <td width="5px"></td>
  <td width="99%" style="color: #FFFFFF">
  <span name="topic" id="topic"></span>
  </td>
</tr>

<script language="javascript">
<%
String img = request.getParameter("topic-img-name");
String topicName = request.getParameter("topic-item-name");
if( topicName == null || topicName.length() == 0 ){
	topicName = "record:topic";
}

String topic = DataForm.getValue( request, topicName );
StringBuffer result = new StringBuffer();
result.append( "setResultTitle( '" );
if( img != null && img.length() != 0 ){
	result.append( img );
}

result.append( "', '" );
if( topic != null && topic.length() != 0 ){
	result.append( topic );
}

result.append( "');\n" );
out.write( result.toString() );
%>
</script>

<tr height="4px">
  <td></td><td></td>
</tr>
  <tr valign="top"><td></td><td>
  <div style='overflow:auto; width:100%; height:100%'>
  <span name="content" id="content">
  <%
	String contentName = request.getParameter("content-item-name");
	if( contentName == null || contentName.length() == 0 ){
		contentName = "record:content";
	}
	
	String content = DataForm.getValue( request, contentName );
	if( content == null || content.length() == 0 ){
		out.write( "<script language='javascript'>parent.hiddenInnerWindow();</script>\n" );
	}
	else{
		out.write( content );
	}
  %>
  </span>
  </div>
  </td></tr>
</table>
</body>

</freeze:html>

