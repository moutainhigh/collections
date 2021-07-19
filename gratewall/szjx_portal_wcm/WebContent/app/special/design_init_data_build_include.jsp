<%
	//JSPRequestProcessor参数
	HashMap parameters = new HashMap();

	//接受页面参数
	int	nHostId = processor.getParam("HostId", 0);
	int	nHostType = processor.getParam("HostType", 0);
	int	nTemplateId = processor.getParam("TemplateId", 0);
	int nSpecialId = processor.getParam("SpecialId", 0);
	int nChannelId = processor.getParam("nChannelId", 0);
	String sTitle = null;

	//初始化页面需要用到的参数信息
	//传入的专题对象的id
	if(nSpecialId != 0){
		Special oSpecial = Special.findById(nSpecialId);
		if(oSpecial == null){
			throw new WCMException(CMyString.format(LocaleServer.getString("design_init_data_build_include.jsp.pointed_specialobj_notfound", "指定的专题对象[{0}]不存在!"), new int[]{nSpecialId}));
		}
		sTitle = CMyString.format(LocaleServer.getString("design_init_data_build_include.jsp.design_special", "设计专题[{0}]"), new String[]{oSpecial.getSpecialName()});
		if(nHostId == 0){
			nHostId = oSpecial.getHostId();
		}
		if(nHostType == 0){
			nHostType = oSpecial.getHostType();
		}
		//运行编辑专题指定的模板，所以只有没有传入模板id的情况下，才使用专题的首页模板
		if(nTemplateId == 0){
			parameters.clear();
			parameters.put("objectId", String.valueOf(nHostId));
			WCMFolderPublishConfig oPublishConfig = (WCMFolderPublishConfig)processor.excute("publish", "getChannelPublishConfig", parameters);
			nTemplateId = oPublishConfig.getDefaultOutlineTemplateId();
		}
	}
	if(nChannelId == 0) {
		nChannelId = nHostId;
	}

	//获取当前模板
	Template currTemplate = Template.findById(nTemplateId);

	if(currTemplate == null){
		throw new WCMException(CMyString.format(LocaleServer.getString("design_init_data_build_include.jsp.pointed_template_notexists", "指定的模板对象[[{0}]不存在"), new int[]{nTemplateId}));
	}

	if(!currTemplate.getPropertyAsBoolean("Visual", false)){
		throw new WCMException(CMyString.format(LocaleServer.getString("design_init_data_build_include.jsp.cannot_use_visual_edit", "模板[[{0}]不是可视化模板，不能进行可视化编辑"), new Object[]{currTemplate}));
	}

	if(!currTemplate.canEdit(loginUser)){
	%>
		<script language="javascript">
		<!--
			alert('<%=CMyString.format(LocaleServer.getString("layout_addedit.jsp.locked", "当前对象被[{0}]锁定，您不能修改!"),  new Object[]{currTemplate.getLockerUser()})%>');
			window.close();
		//-->
		</script>
	<%
		return;
	}
	if(sTitle == null){
		sTitle = CMyString.format(LocaleServer.getString("design_init_data_build_include.jsp.design_template", "设计模板[[{0}]"), new String[]{currTemplate.getName()});
	}

	// 如果是细览模板，取系统配置，来获取默认的编辑文档
	if(currTemplate.getType() == PublishConstants.TEMPLATE_TYPE_DETAIL){
		nHostType =Document.OBJ_TYPE;
		nHostId = Integer.parseInt(ConfigServer.getServer().getSysConfigValue("SPECIAL_DETAIL_DOCID","617"));
		//判断当前系统配置的编辑对象是否存在
		Document document = Document.findById(nHostId);
		if(document==null){
			throw new WCMException(LocaleServer.getString("design_init_data_build_include.jsp.label.sys_config_notfound", "没有找到系统配置的文档！请重新设置系统配置项[SPECIAL_DETAIL_DOCID]"));
		}
	}
	
	//获取模板可视化的内容
	parameters.clear();
	parameters.put("objectId", String.valueOf(nHostId));
	parameters.put("objectType", String.valueOf(nHostType));
	parameters.put("templateId", String.valueOf(nTemplateId));
	String sVisualTemplateContent = (String)processor.excute("wcm61_visualtemplate", "parseTemplate", parameters);
%>