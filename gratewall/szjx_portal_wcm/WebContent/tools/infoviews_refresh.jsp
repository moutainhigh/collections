<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%-- ----- LUCENE IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@ page import="com.trs.components.infoview.InfoViewMgr" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;
%>
<%

boolean bRender = currRequestHelper.getBoolean("render", false);
String result = null;
if (bRender){
	IInfoViewService service = (IInfoViewService) DreamFactory
			.createObjectById("IInfoViewService");
	InfoViewMgr mgr = (InfoViewMgr) DreamFactory
			.createObjectById("InfoViewMgr");
	
	InfoViews infoviews = service.getInfoViews(null);
	int nFailCount = 0;
	int nSuccCount = 0;
	String sFails = "";
	String sSuccs = "";
	FilesMan fman = FilesMan.getFilesMan();
	for (int i = 0; i < infoviews.size(); i++) {
		// 获取Infoview对象
		InfoView infoview = (InfoView) infoviews.getAt(i);
		if (infoview == null) {
			continue;
		}
		// else
		try {
			// 模拟一次文件上传操作，将现有的xsn文件拷贝到UP目录
			String sFakeUploadFile = fman.copyWCMFile(infoview
					.getInfoPathFile(), FilesMan.FLAG_UPLOAD);
			// System.out.println("-->sFakeUploadFile: " + sFakeUploadFile);
			infoview.setInfoPathFile(sFakeUploadFile);
			// 保存
			mgr.save(infoview);
			sSuccs += "\n表单[" + infoview.getName() + "-" + infoview.getId()
					+ "]刷新成功";
			nSuccCount++;

		} catch (Exception ex) {
			ex.printStackTrace();
			sFails += "\n表单[" + infoview.getName() + "-" + infoview.getId()
					+ "]刷新失败，原因为：" + ex.getMessage();
			nFailCount++;
		}
	}

	result = "==成功刷新[" + nSuccCount + "]个表单==" + sSuccs;
	result += "\n\n==刷新[" + nFailCount + "]个表单失败==" + sFails;
}	
//*/
%>
<HTML>
<HEAD>
<TITLE>旧版本的升级-表单列表刷新</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</HEAD>

<BODY>
<table border="0" cellspacing="12" cellpadding="0" style="font-family: 'Courier New'">
<tbody>
	<tr>
		<td style="color: red; font-weight: bold">注意事项</td>
	</tr>
	<tr>
		<td>
		<ul>
			<li>此操作将对WCM系统中的所有表单进行刷新，即表单创建时由infopath解析出来的数据进行重新生成
			<li>请妥善使用此操作，因为其不可逆
			<li>请在执行此操作前对 <font size="" color="red">WCMDATA/Infoview</font> 下的数据进行备份，以防刷新失败时恢复
		</ul>
		</td>
	</tr>
</tbody>
</table>
<div>
<form method=get action="infoviews_refresh.jsp">
	<table border="0" cellspacing="5" cellpadding="8">
	<tbody>
		<tr>
			<td colspan="2" align="center"><input type="submit" value="开始执行" style="width: 120px;"><input type="hidden" name="render" value="1"></td>
		</tr>
	</tbody>
	</table>
</form>
</div>
<br>
<%
	if(result != null){
%>
<div>
	<pre>
<%=result%>
	</pre>
</div>
<%	
	}
%>
<br>
</BODY>
</HTML>