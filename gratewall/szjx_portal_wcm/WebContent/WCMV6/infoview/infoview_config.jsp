<%@ page import="com.trs.components.common.conjection.service.IComponentEntryConfigService"%>
<%@ page import="com.trs.components.common.conjection.ComponentEntryConfigConstants"%>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig"%>
<%@ page import="com.trs.components.wcm.MyPlugin" %>
<%@ page import="com.trs.infra.util.CMyBitsValue" %>
<%@include file="../../include/public_server.jsp"%>

<%
	boolean bIsInfoviewEnable = false;
	//判断是否注册了表单选件
	CMyBitsValue bitsValue = new CMyBitsValue(MyPlugin.getPluginCode());
	boolean bRegisterForInfoview = bitsValue.getBit(3); //数位3指代表单选件

	EntryConfig currConfig = null;

	if(bRegisterForInfoview) {
		//获取表单选件是否已经部署并可用
		IComponentEntryConfigService configSrv = (IComponentEntryConfigService) DreamFactory
				.createObjectById("IComponentEntryConfigService");
		//CONFIG_TYPE = 10 表示配置项类型为表单
		int CONFIG_TYPE = 10;
		currConfig = configSrv.getTypedEntryConfig(CONFIG_TYPE);

		bIsInfoviewEnable = (currConfig != null && currConfig.isEnable());
	}
	try{
		Class.forName("com.trs.ajaxservice.InfoviewService");
	}catch(Throwable tx){
		//未正确部署表单
		//tx.printStackTrace();
		bIsInfoviewEnable = false;
		bRegisterForInfoview = false;
	}

%>