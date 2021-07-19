<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.config.ConfigController" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.persistent.db.LicenseMgr" %>
<%@ page import="com.trs.infra.util.CMyException" %>
<%@include file="../include/public_server.jsp"%>

<%
	request.setCharacterEncoding("UTF-8");
	if(!loginUser.isAdministrator()){
		throw new WCMException(ExceptionNumber.ERR_UNKNOWN, LocaleServer.getString("config_manager_label_notadmin", "您不是管理员，不能执行此操作！"));
	}
	ConfigController oConfigControl = new ConfigController();
	oConfigControl.save(request);
	String sTab = request.getParameter("tabtype");
	String sFirst = CMyString.showNull(request.getParameter("first"));
	String licensePath = getLicenseFileName();
	String orignLicense = getFileContent(licensePath);
	String parameter_orignLicense = request.getParameter("orignlicense");
	String info="";
	// 对修改注册码的保存
	String license = request.getParameter("license");
	if(license!=null){
			if(!orignLicense.equals(parameter_orignLicense)){
				info=LocaleServer.getString("config_manager_dowith.jsp.label.info","原始注册码与当前集群节点的注册码不同，注册码修改失败");
			}else{
				CMyFile.writeFile(getLicenseFileName(),license);
			}
	}
%>
<script language="javascript">
<!--
	var info="<%=info%>";
	if(info.length>0)
		alert(String.format("系统配置修改成功，但是{0}",info));
	else{
		alert("修改成功");
	}
	window.location.href = "config_manager.jsp?tabtype=<%=CMyString.filterForJs(sTab)%>&first=<%=CMyString.filterForJs(sFirst)%>";
//-->
</script>
<%!
	public String getLicenseFileName()throws CMyException{
		String LICENSE = "license/LICENSE.trswcm";
		Class clazz = LicenseMgr.class;
		return clazz.getClassLoader().getResource(LICENSE).getFile();
	}
	public String getFileContent(String _fileName) throws CMyException{
		return CMyFile.readFile(_fileName).trim();
	}
%>