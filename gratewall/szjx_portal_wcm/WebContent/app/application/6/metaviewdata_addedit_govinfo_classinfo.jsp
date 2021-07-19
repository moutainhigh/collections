<%
	String sErrorMsg = null;
	//Channel channel = null;

	//获取政府信息公开视图的Id
	//MetaView oMetaView = GovInfoViewFinder.findMetaViewOfGovInfo(loginUser);
	int nMetaViewId = m_nViewId;
	
	if(nChannelId>0){
		channel = Channel.findById(nChannelId);
	}else if(nObjectId>0){
		channel = findChannelByDocId(nObjectId);
	}else{
		channel = DefaultChannelMakerCenter.getInstance().makeDefaultChannel( loginUser, nMetaViewId);		
	}
	//当前视图所使用的栏目集合
	Channels oChannelsOfMetaView = null;
	if(channel == null){
		oChannelsOfMetaView = DefaultChannelMakerCenter.getInstance().getAvailableChannels(loginUser, nMetaViewId);
		if(oChannelsOfMetaView == null || oChannelsOfMetaView.isEmpty()){
			sErrorMsg = LocaleServer.getString("metaviewdata_addedit.jsp.label.defaultchannel", "没有找到用户默认的存储栏目！");
		}else if(oChannelsOfMetaView.size() == 1){
			//如果只剩下一个栏目，则这个栏目为目标栏目，而不需要进行选择
			channel = (Channel)oChannelsOfMetaView.getAt(0);
		}
	}
	if(channel != null){
		nChannelId = channel.getId();
	}
	int nOrgancat = obj.getPropertyAsInt("organcat",0);
	boolean bAdd = true;
	
	String sPublisher = ""; // 默认的发布机构	
	String sOrgancat = ""; // 默认的机构分类名称
	int nSubcat = 0;
	String sSubcat = "";
	String sShowSubcat = "";
	int nThemecat = 0;
	String sThemecat = "";
	String sShowThemecat = "";
	if(nOrgancat!=0){
		bAdd = false;
	}else{
		ClassInfo organCatClassInfo = UserRelateInfoMaker.makeDefaultClassInfo(LocaleServer.getString("metaviewdata_addedit.jsp.label.orgclass", "机构分类"), loginUser);
		if(organCatClassInfo == null){
			organCatClassInfo = UserRelateInfoMaker.makeDefaultClassInfo(LocaleServer.getString("metaviewdata_addedit.jsp.label.nextorgclassfiy", "下级单位机构分类"), loginUser);
		}
		if(organCatClassInfo != null){
			nOrgancat = organCatClassInfo.getId();
			sOrgancat = organCatClassInfo.getName();
			sPublisher = sOrgancat+"[id="+nOrgancat+"]";
		}
	}

	String sCurrClassInfoId = request.getParameter("ClassInfoId");
	int nCurrClassInfoId = 0;
	if(!CMyString.isEmpty(sCurrClassInfoId)){
		nCurrClassInfoId = Integer.parseInt(sCurrClassInfoId);
	}
	if(nCurrClassInfoId>0){
		ClassInfo currClassInfo = ClassInfo.findById(nCurrClassInfoId);
		if(currClassInfo == null){
			throw new WCMException(CMyString.format(LocaleServer.getString("metaviewdata_addedit.jsp.classfiynotfound", "系统没有找到指定的分类！[ID={0}]"), new int[]{nCurrClassInfoId}));
		}
		String currClassInfoName = currClassInfo.getName();
		int nRootId = currClassInfo.getRootId();
		ClassInfo rootClassInfo = ClassInfo.findById(nRootId);
		if(rootClassInfo != null){
			String sClassInfoName = rootClassInfo.getName();
			if("机构分类".equals(sClassInfoName)||"下级单位机构分类".equals(sClassInfoName)){
				nOrgancat = nCurrClassInfoId;
				sPublisher = currClassInfoName+"[id="+nOrgancat+"]";
			}else if("主题分类".equals(sClassInfoName)||"下级单位主题分类".equals(sClassInfoName)){
				nSubcat = nCurrClassInfoId;
				sShowSubcat = currClassInfoName+"[id="+nSubcat+"]";
			}else if("体裁分类".equals(sClassInfoName)||"下级单位体裁分类".equals(sClassInfoName)){
				nThemecat = nCurrClassInfoId;
				sShowThemecat = currClassInfoName+"[id="+nThemecat+"]";
			}
		}
	}
%>