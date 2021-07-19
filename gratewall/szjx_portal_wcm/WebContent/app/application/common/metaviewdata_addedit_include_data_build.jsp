<%
	//初始化页面内部用到的局部变量
	int nFlowDocId = processor.getParam("FlowDocId", 0);	
	int nChannelId = processor.getParam("ChannelId", 0);	
	Channel channel = Channel.findById(nChannelId);
	int nObjectId = processor.getParam("ObjectId", 0);
	if(nObjectId == 0){
		nObjectId = processor.getParam("DocumentId", 0);
	}
	processor.setAppendParameters(new String[]{
		"ObjectId", String.valueOf(nObjectId),
		"ViewId", String.valueOf(m_nViewId)
	});
	MetaViewData obj = (MetaViewData) processor.excute("wcm61_MetaViewData", "findViewDataById");
	MetaView metaView = obj.getMetaView();
	Document document = obj.getDocument();
	int nCurrStatus = 0;
	if(document == null){
		document = Document.createNewInstance();
	}else{
		nCurrStatus = document.getStatusId();
	}
	//工作流流转方式设置相关局部变量
	int nDocId = document.getId();
	int nFlowId = 0;
	IWCMProcessService processService = ServiceHelper.createWCMProcessService();
	Flow flow = processService.getFlowOfEmployer(channel);
	if(flow != null) {
		nFlowId = flow.getId();
	}

	//栏目相关的初始化
	Channels channels = null;
	if(nChannelId == 0){
		MetaView metaview = MetaView.findById(m_nViewId);
		IMetaViewEmployerMgr oMetaViewEmployerMgr = (IMetaViewEmployerMgr)DreamFactory.createObjectById("IMetaViewEmployerMgr");
		channels = oMetaViewEmployerMgr.getEmployers(metaview, null);
		if(channels.size() == 0){
			String sInto = CMyString.format(LocaleServer.getString("metaviewdata_addedit.jsp.label1","ID为【{0}】的视图没有被任何栏目所使用！"),new Object[]{String.valueOf(m_nViewId)});

%>
			<script language="javascript">
				alert('<%=CMyString.filterForJs(sInto)%>');
			</script>
<%
		return;}
		//权限的过滤
		for(int j=channels.size()-1;j>=0;j--){
			Channel chl = (Channel)channels.getAt(j);
			boolean bAddRight = AuthServer.hasRight(loginUser, chl, WCMRightTypes.DOC_ADD);
			if(!bAddRight){
				channels.remove(chl, false);
			}
		}
		if(channels.size() == 0){
			String sErrorInto = CMyString.format(LocaleServer.getString("metaviewdata_addedit.jsp.label100","您没有权限在视图【{0}】所在的栏目新建文档！"),new Object[]{String.valueOf(m_nViewId)});
	%>
			<script language="javascript">
				alert('<%=CMyString.filterForJs(sErrorInto)%>');
			</script>
	<%
			return;
		}
		//20120606 by CC 添加直接从资源对象获取所属的栏目ID,关于channels.size() == 1的if判断是否需要值得讨论,直接使用else语句即可
		if(channels.size() == 1){
			Channel oChannel = (Channel)channels.getAt(0);
			nChannelId = oChannel.getId();	
		} else {
			nChannelId = obj.getChannelId();
		}
	}
	//是否显示存草稿的逻辑控制
	boolean bShowSaveDraftBtn = false;
	if(nObjectId == 0 || nCurrStatus == Status.STATUS_ID_DRAFT){
		bShowSaveDraftBtn = true;
	}
	int nChnlDocId = 0;
	if(nChannelId > 0 && nObjectId > 0){
		nChnlDocId = ChnlDoc.findByDocAndChnl(document,Channel.findById(nChannelId)).getId();
	}
	//如果文档在流转，并且登录用户不是该文档当前的工作流审核人员，则抛出异常，不允许修改
	//处于工作流中的文档只有当前审核人员才能修改
	if(nObjectId > 0){
		boolean bCanEdit = WCMProcessServiceHelper.validCanEditProcessObject(Document.OBJ_TYPE, nObjectId, loginUser);
		if(!bCanEdit){
	%>
		<script language="javascript">
			alert("<%=LocaleServer.getString("metaviewdata_addedit.jsp.ReviewRight","您不是当前审核人员，没有权限修改当前记录！")%>");
		</script>
	<%
			return;
		}
	}
	//图片库是否启用的判断
	boolean bPhotoPluginsEnable = PluginConfig.isStartPhoto();
	
	
	//by CC 2012-03-30 需要设置是否可以进行“保存并发布”显示。
	boolean bCanPub = false;
	Template oDetailTemplate = null;
	int nDetailTemplateId = 0;

	//1 首先判断当前栏目是否可以发布
	// 需要再进行一次判断。如：从分类法导航栏点击编辑文档时，nChannelId为0，需要再次赋值！
	if(channel == null) channel =  Channel.findById(nChannelId);

	//1.1 如果栏目允许发布，则可以发布细缆
	if(channel != null && channel.isCanPub()){

		//2 判断当前栏目是否有配置了细览模板！
		WCMFolderPublishConfig oWCMFolderPublishConfig = null;

		IPublishElement publishElement = PublishElementFactory.lookupElement(Channel.OBJ_TYPE, nChannelId);
		if (publishElement == null) {
			throw new WCMException(I18NMessage.get(
					PublishServiceProvider.class,
					"PublishServiceProvider.label8", "指定的对象[Type=")
					+ Channel.OBJ_TYPE
					+ ", Id="
					+ nChannelId
					+ I18NMessage.get(PublishServiceProvider.class,
							"PublishServiceProvider.label9", "]不存在！"));
		}

		oWCMFolderPublishConfig = new WCMFolderPublishConfig((IPublishFolder) publishElement);

		nDetailTemplateId = oWCMFolderPublishConfig.getDetailTemplateId();
		oDetailTemplate = Template.findById(nDetailTemplateId);
		//System.out.println("nDetailTemplateId===" + nDetailTemplateId + "; name===" + oDetailTemplate.getName());

		if(oDetailTemplate != null
			&& DocumentAuthServer.hasRight(loginUser, channel, document, WCMRightTypes.DOC_PUBLISH)){
			bCanPub = true;
		}
	}
%>