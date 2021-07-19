<%
	//增加广告管理选件的校验Enable
	String sAdMainConfigValue = ConfigServer.getServer().getSysConfigValue("AD_PLUGIN_ENABLED","false");
	String sEpressMainConfigValue = ConfigServer.getServer().getSysConfigValue("EPRESS_PLUGIN_ENABLED","false");
%>
<script language="javascript">
<!--
	var bAdMainEnable = "true"==("<%=sAdMainConfigValue%>");
	var bEpressMainEnable = "true"==("<%=sEpressMainConfigValue%>");
//-->
</script>
<script language="javascript">
<!--
	var g_IsRegister = {
		//文字库是否注册的标记
		'0' : true,

		//图片库是否注册的标记
		'1' : true,
		'photo': true,

		//视频库是否注册的标记
		'2' : true,
		'video': true,

		//我定制的栏目
		'3' : true,

		//资源库是否注册的标记
		'4' : true,
		'metadata': true,

		'infoview': true,
		'comment':  true,

		//我的工作列表栏目
		'100' : true,

		//SCM是否注册的标记
		'SCM': true
	};

	var RegisterMgr = {
		isRegister : function(sPlugin){
			return g_IsRegister[sPlugin];
		}
	};
<%
	if(!PluginConfig.isStartPhoto()){
		out.println("g_IsRegister['1'] = g_IsRegister['photo'] = false;");
	}
//去掉WCM6.5里视频库的License入口控制	
//if(!PluginConfig.isStartVideo()){
		//out.println("g_IsRegister['2'] = g_IsRegister['video'] = false;");
	//}

	CMyBitsValue bitsValue = new CMyBitsValue(MyPlugin.getPluginCode());
	if (!bitsValue.getBit(2)) {
		out.println("g_IsRegister['4'] = g_IsRegister['metadata'] = false;");
	}
	if (!bitsValue.getBit(3)) {
		out.println("g_IsRegister['infoview'] = false;");
	}

	if(!bitsValue.getBit(7)){
		out.println("g_IsRegister['SCM'] = false;");
	}

	IComponentEntryConfigService configSrv = (IComponentEntryConfigService) DreamFactory
			.createObjectById("IComponentEntryConfigService");

	EntryConfig oCommentConfig = configSrv
			.getTypedEntryConfig(ComponentEntryConfigConstants.TYPE_COMMENT);
	if(!oCommentConfig.isEnable()){
		out.println("g_IsRegister['comment'] = false;");
	}
%>

	var g_IsDeployed = {
		//文字库是否部署的标记
		'0' : true,
		
		//图片库是否部署的标记
		'1' : false,
		'photo': false,

		//视频库是否部署的标记
		'2' : false,
		'video': false,
		
		//元数据管理选件是否部署的标记
		'4' : false,
		'metadata' : false,
		
		//政府信息公开目录是否部署的标记
		'govinfo' : false,

		'infoview': true,

		'SCM': true
	};

	var DeployMgr = {
		isDeploy : function(sPlugin){
			return g_IsDeployed[sPlugin];
		}
	};

<%
	try{
		IImageLibConfig oImageLibConfigImpl = new ImageLibConfigImpl();
		if(!oImageLibConfigImpl.isCmdUsed()){
			Class.forName("magick.Magick");
		}
		out.println("g_IsDeployed['1'] = g_IsDeployed['photo'] = true;");
	}catch(Throwable tx){
	}
	
	try{
		Class.forName("com.trs.components.video.VSConfig");
		out.println("g_IsDeployed['2'] = g_IsDeployed['video'] = true;");
	}catch(Throwable tx){
	}
	
	try{
		Class.forName("com.trs.components.metadata.service.MetaDataDefServiceProvider");
		out.println("g_IsDeployed['4'] = g_IsDeployed['metadata'] = true;");
	}catch(Throwable tx){
	}

	try{
		Class.forName("com.trs.components.metadata.service.MailConfigsHelper");
		out.println("g_IsDeployed['govinfo'] = true;");
	}catch(Throwable tx){
	}

	try{
		Class.forName("com.trs.scm.service.SCMMicroContentServiceProvider");
		out.println("g_IsDeployed['SCM'] = true;");
	}catch(Throwable tx){
	}
%>

	var RegsiterMgr = {
		isValidPlugin : function(sPlugin){
			return RegisterMgr.isRegister(sPlugin) && DeployMgr.isDeploy(sPlugin);
		}
	};
//-->
</script>