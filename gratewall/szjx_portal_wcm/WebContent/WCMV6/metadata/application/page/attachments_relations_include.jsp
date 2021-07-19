<%
	int nIsReadOnly = currRequestHelper.getInt("isReadOnly", 0);
	boolean bIsReadOnly = (nIsReadOnly==1);
	String sReadOnly = (bIsReadOnly)?"readonly":"";
	boolean bEnablePicLib = PluginConfig.isStartPhoto();
	boolean bEnableFlashLib = PluginConfig.isStartVideo();
	//初始化（获取数据）
	int nDocumentId	  = currRequestHelper.getInt("DocumentId", 0);
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	//
	IDocumentService currDocumentService = (IDocumentService)DreamFactory.createObjectById("IDocumentService");
	Document currDocument = null;
	Channel currChannel = null;
	Channel docChannel = null;
	boolean isNewsOrPics = false;//栏目类型是否是图片或头条新闻
	//获得currDocument对象
	if(nDocumentId > 0){
		currDocument  = Document.findById(nDocumentId);
		if(currDocument == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nDocumentId+"]的文档失败！");
		}
	}else{
		if(bIsReadOnly){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "未指定查看文档的ID！");
		}
		currDocument = Document.createNewInstance();
	}
//工作流相关处理
	int nFlowDocId = currRequestHelper.getInt("FlowDocId", 0);
	if(nFlowDocId>0){
		//校验权限
		WCMProcessServiceHelper.validateWorkFlowRight(loginUser, currDocument, nFlowDocId, FlowNode.ACTION_EDIT, "修改");
	}
	//获得currChannel对象
	if(nChannelId>0){
		currChannel = Channel.findById(nChannelId);	
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取栏目ID为["+nChannelId+"]的栏目失败！");
		}
		if(currChannel.isDeleted()){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,currChannel+"已被删除!请刷新您的栏目树.");
		}
		isNewsOrPics = (currChannel.getType() == Channel.TYPE_TOP_NEWS || currChannel.getType() == Channel.TYPE_TOP_PICS);
	}
	//获得docChannel对象
	docChannel = currChannel;
	if(nDocumentId > 0)
		docChannel = currDocument.getChannel();
	if(currChannel==null&&docChannel!=null){
		currChannel = docChannel;
	}
	if(currChannel==null&&docChannel==null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "未指定新建文档所属栏目！");
	}
	//权限校验
	if(nDocumentId > 0){		
		if(nFlowDocId == 0 && !bIsReadOnly && !DocumentAuthServer.hasRight(loginUser, docChannel, currDocument, WCMRightTypes.DOC_EDIT)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "您没有权限修改ID为["+nDocumentId+"]的文档！");
		}
		else if(nFlowDocId == 0 && !DocumentAuthServer.hasRight(loginUser, docChannel, currDocument, WCMRightTypes.DOC_BROWSE)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "您没有权限查看ID为["+nDocumentId+"]的文档！");
		}
	}else{
		if(currChannel != null && !AuthServer.hasRight(loginUser, currChannel, WCMRightTypes.DOC_ADD)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "您没有权限新建文档！");
		}
	}
	//锁定校验
	if(nDocumentId > 0){
		if(!bIsReadOnly&&!currDocument.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "文档["+currDocument.getTitle()+"]["+currDocument.getId()+"]被用户［"+currDocument.getLockerUserName()+"］锁定！您不能修改！");
		}
	}
	if (currDocument.getStatusId() < 0){
		throw new WCMException("ID为["+nDocumentId+"]的文档在废稿箱中，您暂时无法对其进行操作！");
	}
	String sOfficeSid = currRequestHelper.getString("OfficeSid");
	String[] officeInfo = null;
	if(sOfficeSid!=null){
		officeInfo = (String[])session.getAttribute(sOfficeSid);
	}
	String sTitle = "";//文档标题
	String sTitleColor = "";//文档标题颜色#000000
	boolean bAttachPic = false;//文档附图
	int nDocType = Document.DOC_TYPE_HTML;//默认文档类型为HTML
	String sContent = "";//文档内容
	String sDocLink = "";//链接型文档链接
	String sDocFileName = "";//文件型文档文件名
	String sChannelName = "";//所属栏目名称
	String sOutlineTitle = "";//首页标题
	String sDocSubTitle = "";//副标题
	String sDocKeyWords = "";//关键字
	String sDocAbstract = "";//摘要
	String sDocAuthor = "";//作者
	String sRelTime = "";//文档撰写时间
	//TODO
	ConfigServer oConfigServer = ConfigServer.getServer();
	String sCanNewDocSource = oConfigServer.getSysConfigValue("ENABLE_SOURCE_EDIT", "true");
	boolean bCanNewDocSource = "true".equalsIgnoreCase(sCanNewDocSource)||"1".equals(sCanNewDocSource);
	//置顶信息
	boolean bIsCanTop = false;//是否在当前栏目有置顶权限
	//有修改文档的权限时才可做置顶设置
	bIsCanTop = AuthServer.hasRight(loginUser, currChannel, WCMRightTypes.DOC_EDIT);
	boolean bTopped = false;//是否置顶
	boolean bTopForever = false;//是否永久置顶
	int nTopPosition = 0;//置顶位置
	CMyDateTime dtTopValidTime = null;//置顶有效时间
	Channels quoteChannels = null;//文档引用栏目
	int nCurrDocSource = 0;//文档来源
	Documents toppedDocuments = null;//currChannel中已置顶的文档
	//获取文档所属频道docChannel的扩展字段
	ContentExtFields currExtendedFields = null;
	if(docChannel != null){
		currExtendedFields = getChannelService().getExtFields(docChannel, new WCMFilter("", "", "LogicFieldDesc"));
	}
	if(nDocumentId == 0){
		//新建文档设置缺省值
		currDocument.setType(Document.TYPE_HTML);
		currDocument.setChannel(nChannelId);
		if(officeInfo!=null){
			sTitle = PageViewUtil.toHtmlValue(officeInfo[0]);
			sContent = PageViewUtil.toHtmlValue(officeInfo[1]);
		}
		sRelTime = CMyDateTime.now().toString("yyyy-MM-dd HH:mm");
		dtTopValidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
		//获得当前文档所属栏目名称
//		sChannelName = (docChannel!=null)?docChannel.getName():"";
		sChannelName = (docChannel!=null)?CMyString.filterForHTMLValue(docChannel.getDesc()):"";
	}else{//修改文档获取原始值
		//获得当前文档的标题
		sTitle = PageViewUtil.toHtmlValue(currDocument.getTitle());
		//获得文档标题的颜色,缺省为#000000
		sTitleColor = CMyString.showNull(currDocument.getTitleColor());
//		if("".equals(sTitleColor))sTitleColor = "#000000";
		//文档附图
		bAttachPic = currDocument.isAttachPic();
		//获取当前文档的类型
		nDocType = currDocument.getType();
		//获得当前文档的内容
		switch(nDocType){
			case Document.DOC_TYPE_HTML:
				sContent = (nDocumentId>0)?PageViewUtil.toHtmlValue(currDocument.getHtmlContentWithImgFilter(
                    null, false)):"";
				break;
			case Document.DOC_TYPE_NORMAL:
				sContent = (nDocumentId>0)?PageViewUtil.toHtmlValue(currDocument.getContent()):"";
				break;
			case Document.DOC_TYPE_LINK:
				sDocLink = PageViewUtil.toHtmlValue(currDocument.getPropertyAsString("DOCLINK"));
				break;
			case Document.DOC_TYPE_FILE:
				sDocFileName = PageViewUtil.toHtmlValue(currDocument.getPropertyAsString("DOCFILENAME"));
				break;
		}
		//获得当前文档所属栏目名称
//		sChannelName = (docChannel!=null)?docChannel.getName():"";
		sChannelName = (docChannel!=null)?CMyString.filterForHTMLValue(docChannel.getDesc()):"";
		//获得当前文档的首页标题
		sOutlineTitle = PageViewUtil.toHtmlValue(currDocument.getPropertyAsString("DocPeople"));
		//获得当前文档的副标题
		sDocSubTitle = PageViewUtil.toHtmlValue(currDocument.getSubTitle());
		//获得当前文档的关键字
		sDocKeyWords = PageViewUtil.toHtmlValue(currDocument.getKeywords());
		//获得当前文档的摘要
		sDocAbstract = PageViewUtil.toHtmlValue(currDocument.getAbstract());
		//文档来源
		nCurrDocSource = currDocument.getSourceId();
		//获得当前文档的作者
		sDocAuthor = PageViewUtil.toHtmlValue(currDocument.getAuthorName());
		//文档撰写时间
		sRelTime = CMyString.showNull(currDocument.getReleaseTime().toString("yyyy-MM-dd HH:mm"));
		//文档被引用到的频道
		quoteChannels = currDocumentService.getQuoteChannels(currDocument);
		//获取置顶信息
		if(bIsCanTop){
			bTopped = currDocumentService.isDocumentTopped(currDocument, currChannel);
			if(currChannel != null) {
				WCMFilter filter = new WCMFilter("", "DOCORDERPRI>0", "", "DocId, DocTitle, DocChannel");
				IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
				toppedDocuments = currChannelService.getDocuments(currChannel, filter);
				nTopPosition = toppedDocuments.indexOf(currDocument)+1;
				//currDocumentService.getTopPosition(currDocument, currChannel);
				dtTopValidTime = currDocumentService.getTopTime(currDocument, currChannel);
			}
			if(bTopped && dtTopValidTime == null)
				bTopForever = true;
			if(dtTopValidTime == null){
				dtTopValidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
			}
		}
	}
	//判断是否可发布
	IPublishFolder folder = (IPublishFolder)PublishElementFactory.makeElementFrom(docChannel);
	IPublishContent content =  PublishElementFactory.makeContentFrom(currDocument, folder);
	boolean bIsCanPub = false;
	if (DocumentAuthServer.hasRight(loginUser, docChannel, currDocument,
                WCMRightTypes.DOC_PUBLISH)) {
		//=======由于直接发布不考虑状态，需要需要将逻辑直接暴露在特殊使用场景中=====
		// 1. 栏目如果不允许发布，不能发布细览
        if (docChannel.isCanPub()) {
		// 2. 只要设置细览模板就可以发布
            bIsCanPub = (content.getDetailTemplate() != null);
        }
	}

	//文档发布属性所需参数
	int nDocTemplateId = 0;
	String sDocTemplateName = "";
	String strExecTime = "";
	boolean bDefineSchedule = false;
	if(nDocumentId > 0) {
		com.trs.service.ITemplateService currTemplateService = ServiceHelper.createTemplateService();
		Template  docTemplate = currTemplateService.getDetailTemplate(content);
		if(docTemplate != null) {
			nDocTemplateId = docTemplate.getId();
		}
		sDocTemplateName = getTemplateAsString(docTemplate);

		WCMContentPublishConfig currConfig = new WCMContentPublishConfig(loginUser,content);
		Schedule currSchedule = currConfig.getSchedule();
		bDefineSchedule = (currSchedule != null);
		if(bDefineSchedule) {
			strExecTime = currSchedule.getExeTime().toString("yyyy-MM-dd HH:mm");
		} else {
			CMyDateTime timeNow = CMyDateTime.now();
			strExecTime = timeNow.toString("yyyy-MM-dd HH:mm");
		}
	}
	else {
		CMyDateTime timeNow = CMyDateTime.now();
		strExecTime = timeNow.toString("yyyy-MM-dd HH:mm");
	}

	String strDBName = DBManager.getDBManager().getDBType().getName();
%>
<%!
	private String getAppendixsXml(Document _currDocument,int nAppendixType) throws WCMException{
		try{
			AppendixMgr m_oAppendixMgr = (AppendixMgr) DreamFactory
					.createObjectById("AppendixMgr");
			// 3.执行操作（获取指定文档的附件）
			Appendixes appendixes = m_oAppendixMgr.getAppendixes(_currDocument,nAppendixType ,null);
            // 将附件转换成为XML
            AppendixToXML appendixToXML = new AppendixToXML();
			return PageViewUtil.toHtmlValue(appendixToXML.toXmlString(null, appendixes));
		}catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "转换Appendixs集合对象为XML字符串失败！", ex);
		}
	}
//	private String getRelationsXML(Document _currDocument) throws WCMException{
	private String getRelationsXML(User loginUser, String docIds) throws WCMException{
		try{
			/*
			RelationMgr m_oRelationMgr = (RelationMgr) DreamFactory
                .createObjectById("RelationMgr");
			Relations relations = m_oRelationMgr.getRelations(_currDocument,null);
            RelationToXML relationToXML = new RelationToXML();
			return PageViewUtil.toHtmlValue(relationToXML.toXmlString(null, relations));
			*/
			Documents docs = Documents.findByIds(loginUser, docIds);
			StringBuffer sResult = new StringBuffer(300);
			sResult.append("<RELATIONS>");
			for (int i = 0, nSize = docs.size(); i < nSize; i++) {
				Document doc = (Document) docs.getAt(i);
				if (doc == null)
					continue;
				sResult.append("<RELATION>");
				sResult.append("<RELDOC Id='");
				sResult.append(doc.getId());
				sResult.append("' ChannelId='");
				sResult.append(doc.getChannelId());
				sResult.append("' Title='" + doc.getTitle() + "'/>");
				sResult.append("</RELATION>");
			}
			sResult.append("</RELATIONS>");
			return PageViewUtil.toHtmlValue(sResult.toString());
		}catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "转换Relations集合对象为XML字符串失败！", ex);
		}
	}
	
	private String getAllDocSourceJs(User _user) throws WCMException {
		return getAllDocSourceJs(_user,0);
	}
	private String getAllDocSourceJs(User _user,int _nSiteId) throws WCMException {
		String sSelectFields = "SOURCEID,SRCNAME,SRCDESC";
		WCMFilter filter = new WCMFilter("", "", "SRCDESC ASC", sSelectFields);
		if(_nSiteId > 0){
			filter.setWhere("SiteId="+_nSiteId);
		}
		Sources currSources = Sources.openWCMObjs(_user, filter);
		int nSize = currSources.size();
		Source currSource = null;
		Security currSecurity = null;
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i=0; i<nSize; i++){
			currSource = (Source) currSources.getAt(i);
			int nSourceId = currSource.getId();
			if( currSource == null ){
				continue;
			}
			currSecurity = currSource.getSecurity();
			if(i!=0){
				sb.append(",{'value':");
			}
			else{
				sb.append("{'value':");
			}
			sb.append(nSourceId);
			sb.append(",'label':\"");
			sb.append(currSource.getDesc());
			sb.append("\"}");
		}
		sb.append("]");
		currSources.clear();
		return sb.toString();
	}
	private String getDocSourceName(User _user, int _nCurrSourceId) throws WCMException {
		return getDocSourceName(_user,_nCurrSourceId,0);
	}
	private String getDocSourceName(User _user, int _nCurrSourceId,int _nSiteId) throws WCMException {
		if(_nCurrSourceId==0)return "";
		String sSelectFields = "SOURCEID,SRCNAME,SRCDESC";
		WCMFilter filter = new WCMFilter("", "", "SRCDESC ASC", sSelectFields);
		if(_nSiteId > 0){
			filter.setWhere("SiteId="+_nSiteId);
		}

		Sources currSources = Sources.openWCMObjs(_user, filter);

		int nSize = currSources.size();
		Source currSource = null;
		for(int i=0; i<nSize; i++){
			currSource = (Source) currSources.getAt(i);
			if( currSource == null ){
				continue;
			}
			int nSourceId = currSource.getId();
			if(nSourceId == _nCurrSourceId)return currSource.getDesc();
		}
		
		currSources.clear();
		return "";
	}
	private String showDocSource(User _user, int _nCurrSourceId) throws WCMException {
		return showDocSource(_user,_nCurrSourceId,0);
	}
	private String showDocSource(User _user, int _nCurrSourceId,int _nSiteId) throws WCMException {
		String sSelectFields = "SOURCEID,SRCNAME,SRCDESC";
		WCMFilter filter = new WCMFilter("", "", "SRCDESC ASC", sSelectFields);
		if(_nSiteId > 0){
			filter.setWhere("SiteId="+_nSiteId);
		}

		Sources currSources = Sources.openWCMObjs(_user, filter);

		/*
		if(currSources == null || currSources.isEmpty()){
			return "";
		}*/

		int nSize = currSources.size();
		Source currSource = null;
		Security currSecurity = null;
		StringBuffer sb = new StringBuffer();
		sb.append("<SELECT name=\"DocSource\" id=\"DocSource\" STYLE=\"WIDTH:150px\" pattern=\"string\" elname=\"来源\" ignore='1' " );
		sb.append(" value=\"");
		sb.append(_nCurrSourceId);
		sb.append("\">");
		sb.append("<option value='0' sourceDesc='--请选择--'>--请选择--</option>");
		for(int i=0; i<nSize; i++){
			currSource = (Source) currSources.getAt(i);
			int nSourceId = currSource.getId();
			if( currSource == null ){
				continue;
			}

			currSecurity = currSource.getSecurity();
			sb.append("<option value=\"");
			sb.append(nSourceId);
			sb.append("\" security=\"");
			sb.append(currSecurity == null?"":String.valueOf(currSecurity.getId()));
			sb.append("\"");
			if(nSourceId == _nCurrSourceId)	sb.append(" selected ");
			sb.append(" sourceDesc='").append(currSource.getDesc()).append("'");
			sb.append(">");
			sb.append(currSource.getDesc());
			sb.append("</option>");
		}
		sb.append("</SELECT>");
		
		currSources.clear();

		return sb.toString();
	}
	private String getDocType(int nDocType){
		switch(nDocType){
			case Document.DOC_TYPE_HTML:
				return "HTML";
			case Document.DOC_TYPE_NORMAL:
				return "纯文本";
			case Document.DOC_TYPE_LINK:
				return "链接";
			case Document.DOC_TYPE_FILE:
				return "文件";
		}
		return "";
	}
	private String getDocTypeSelector(){
		String sRetVal = "<select id='DocType' name='DocType' onChange='SwitchDocEditPanel(this.value);' class='select'>";
		sRetVal += "<option value='"+Document.DOC_TYPE_HTML+"'>HTML</option>";
		sRetVal += "<option value='"+Document.DOC_TYPE_NORMAL+"'>纯文本</option>";
		sRetVal += "<option value='"+Document.DOC_TYPE_LINK+"'>链接</option>";
		sRetVal += "<option value='"+Document.DOC_TYPE_FILE+"'>文件</option>";
		sRetVal += "</select>";
		return sRetVal;
	}
	private String getQuoteChannelIds(Channels quoteChannels){
		String sQuoteChannelIds = "";
		Channel tmpQuoteChannel = null;
		for(int i=0;quoteChannels!=null&&i<quoteChannels.size();i++){
			if(!sQuoteChannelIds.equals("")){
				sQuoteChannelIds += ',';
			}
			tmpQuoteChannel = (Channel)quoteChannels.getAt(i);
			sQuoteChannelIds += tmpQuoteChannel.getId();
		}
		return sQuoteChannelIds;
	}
	private String showExtendFields(ContentExtFields currExtendedFields,Document currDocument,String strDBName,boolean bReadOnly) throws Throwable{
		String sRetVal = "";
		if(currExtendedFields != null && !currExtendedFields.isEmpty()){
			ContentExtField currExtendedField = null;
			String sExtFieldName = "";
			String sExtFieldValue = "";
			for(int i=0; i<currExtendedFields.size(); i++){
				try{
					currExtendedField = (ContentExtField)currExtendedFields.getAt(i);
					if(currExtendedField == null) continue;
					sExtFieldName = PageViewUtil.toHtml(currExtendedField.getName());

					if(currExtendedField.getType().getType() == java.sql.Types.TIMESTAMP) {
						CMyDateTime tmpDateTime = currDocument.getPropertyAsDateTime(currExtendedField.getName());
						if(tmpDateTime == null) {
							sExtFieldValue = "";
						} else {
							sExtFieldValue= PageViewUtil.toHtmlValue(tmpDateTime.toString("yyyy-MM-dd HH:mm"));
						}
					} else {
						sExtFieldValue= PageViewUtil.toHtmlValue(currDocument.getPropertyAsString(currExtendedField.getName()));
					}
					sRetVal += "<TR class='attr_extendfield_row' valign='middle' title='"+sExtFieldName+"["+PageViewUtil.toHtml(toDescription(currExtendedField.getType(), strDBName))+"]'>";
					sRetVal += "<TD class='attr_extendfield_column' style='width:80px;'>";
					sRetVal += PageViewUtil.toHtml(currExtendedField.getDesc());
					sRetVal += "</TD>";
					sRetVal += "<TD class='attr_extendfield_column'>";
					sRetVal += getHtml(currExtendedField,sExtFieldValue,bReadOnly);
					sRetVal += "</TD>";
					sRetVal += "</TR>";
				} catch(Exception ex){
					throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+(i+1)+"]个扩展字段的属性失败！", ex);
				}//end try-catch
			}//end for
		}
		else{
			sRetVal += "<TR class='attr_extendfield_row' valign='middle'>";
			sRetVal += "<TD class='attr_extendfield_column' title='' colSpan='2'>";
			sRetVal += "无扩展字段";
			sRetVal += "</TD>";
			sRetVal += "</TR>";
		}
		return sRetVal;
	}
	private String showExtendFields(ContentExtFields currExtendedFields,Document currDocument,String strDBName) throws Throwable{
		return showExtendFields(currExtendedFields,currDocument,strDBName,false);
	}
	private String showQuoteChannels(Channels quoteChannels) throws Throwable{
		//引用频道的显示
		String sRetVal = "";
		int nMinTRCount = 1, nQuoteChnlSize = 0 ;
		Channel quoteChannel = null;	
		if(quoteChannels != null){
			nQuoteChnlSize = quoteChannels.size();
			for(int i=0; i<nQuoteChnlSize; i++){	
				quoteChannel = (Channel)quoteChannels.getAt(i);
				if(quoteChannel == null)continue;
				sRetVal += "<TR id='quotechanel_row_"+quoteChannel.getId()+"' class='attr_quotechanel_row' valign='middle'>";
				sRetVal += "<TD>";
				sRetVal += "<input type='checkbox' name='cb_quotechannel' value='"+quoteChannel.getId()+"'>";
				sRetVal += "</TD>";
				sRetVal += "<TD class='attr_quotechanel_column'>";
				sRetVal += quoteChannel.getDesc();
				sRetVal += "</TD>";
				sRetVal += "</TR>";
			}//end for
		}//end if
		for(int i=nQuoteChnlSize; i<nMinTRCount; i++){
			sRetVal += "<TR class='attr_quotechanel_row' valign='middle'>";
			sRetVal += "<TD>";
			sRetVal += "<input type='checkbox' name='cb_quotechannel' value='0' disabled>";
			sRetVal += "</TD>";
			sRetVal += "<TD class='attr_quotechanel_column'>";
			sRetVal += "</TD>";
			sRetVal += "</TR>";
		}//end for
		return sRetVal;
	}
	private String showToppedDocs(Documents toppedDocuments,int nDocumentId) throws Throwable{
		String sRetVal = "";
		Document aToppedDoc = null;
		int nToppedDocSize = (toppedDocuments == null || toppedDocuments.isEmpty())? 0 : toppedDocuments.size();
		for(int i=0; i<nToppedDocSize; i++){
			aToppedDoc = (Document) toppedDocuments.getAt(i);
			if(aToppedDoc == null) continue;
			if(aToppedDoc.getId()==nDocumentId){
				sRetVal += "<TR align='center' valign='middle' class='attr_topsort_row current_doc_row' DocumentId='"+aToppedDoc.getId()+"'>";
				sRetVal += "<TD class='attr_topsort_title'>";
				sRetVal += "当前文档";
				sRetVal += "</TD>";
				sRetVal += "</TR>";
			}
			else{
				sRetVal += "<TR align='center' valign='middle' class='attr_topsort_row' DocumentId='"+aToppedDoc.getId()+"'>";
				sRetVal += "<TD class='attr_topsort_title'>";
				sRetVal += CMyString.truncateStr(aToppedDoc.getTitle(), 55, "...");
				sRetVal += "</TD>";
				sRetVal += "</TR>";
			}
		}//end for
		return sRetVal;
	}
	private String getExtendFieldNames(ContentExtFields currExtendedFields,Document currDocument,String strDBName) throws Throwable{
		String sRetVal = "";
		if(currExtendedFields != null && !currExtendedFields.isEmpty()){
			ContentExtField currExtendedField = null;
			String sExtFieldName = "";
			String sExtFieldValue = "";
			for(int i=0; i<currExtendedFields.size(); i++){
				try{
					currExtendedField = (ContentExtField)currExtendedFields.getAt(i);
					if(currExtendedField == null) continue;
					if(!sRetVal.equals(""))sRetVal += ",";
					sRetVal += "'"+currExtendedField.getName()+"'";
				} catch(Exception ex){
					throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+(i+1)+"]个扩展字段的属性失败！", ex);
				}//end try-catch
			}//end for
		}
		return sRetVal;
	}
	private String getTemplateAsString(Template _template) {
		if(_template == null) {
			return "无";
		}
		return _template.getName() + "&nbsp;";
	}
	public IChannelService getChannelService(){
		return (IChannelService)DreamFactory.createObjectById("IChannelService");
	}
	private String getPattern(int _nType){
		switch(_nType){
			case Types.BIGINT:
			case Types.INTEGER:
			case Types.SMALLINT:
			case Types.NUMERIC:
				return "int";
			case Types.FLOAT:
			case Types.DOUBLE:			
				return "float";
			case Types.DATE:
			case Types.TIMESTAMP:
				return "time";
				//return " ";
			case Types.CLOB:
				return "";
			default:
				return "string";
		
		}
	}
	private String getHtml(ContentExtField _currField) throws Exception{
		return getHtml(_currField,"",false);
	}
	private String getHtml(ContentExtField _currField,String _sValue,boolean bReadOnly) throws Exception{
		if(bReadOnly){
			return _sValue;
		}
		if(_currField == null || _currField.getType() == null) return "";
		int nDataType = _currField.getType().getType();
		String sHtml = "";
		//时间型使用控件显示
		if(nDataType == java.sql.Types.DATE || nDataType == java.sql.Types.TIMESTAMP) {
			sHtml = "<SCRIPT>TRSCalendar.drawWithTime(\""+_currField.getName()+"\", \""+_sValue+"\", null, true, true);</SCRIPT>";
			return sHtml;
		}

		String sPattern = getPattern(nDataType);
		String sElementInfo = " ID=\""+_currField.getName()+"\" NAME="+_currField.getName()+" validation=\"type:'"+sPattern+"',desc:'扩展字段["+_currField.getDesc()+"]'";
		if("string".equals(sPattern)) {
			sElementInfo += ",max_len:'"+_currField.getMaxLength()+"'";
		} else if("int".equals(sPattern)&&
			( nDataType==java.sql.Types.BIGINT || nDataType==java.sql.Types.NUMERIC )) {
			sElementInfo += ",max:'9223372036854775807',min:'-9223372036854775807'";
		} else if("int".equals(sPattern)&&nDataType == java.sql.Types.INTEGER) {
			sElementInfo += ",max:'2147483647',min:'-2147483647'";
		} else if("int".equals(sPattern)&&nDataType == java.sql.Types.SMALLINT) {
			sElementInfo += ",max:'32767',min:'-32767'";
		}
		sElementInfo += "\"";
		if(nDataType == java.sql.Types.LONGVARCHAR || nDataType == java.sql.Types.VARCHAR)
			sHtml = "<TEXTAREA rows=5 " + sElementInfo + " class=\"attr_textarea\" style=\"width:150px;border:0;\">"+_sValue+"</TEXTAREA>";
		else
			sHtml = "<INPUT " + sElementInfo + " value=\""+_sValue+"\" class=\"attr_input_text\" style=\"width:150px;\">";
		return sHtml;
	}
	private String getHtml(ContentExtField _currField,String _sValue) throws Exception{
		return getHtml(_currField,_sValue,false);
	}

	// 将字段类型转换成中文
	private String toDescription(DataType _currDataType, String _strDBName){
		if(_currDataType == null || _strDBName == null) {
			return "";
		}
		int nDataType = _currDataType.getType();
		switch(nDataType) {
			case java.sql.Types.FLOAT :
				if(_strDBName.equalsIgnoreCase("Oracle")) {
					return "整数型";
				}
				return "小数型";
			case java.sql.Types.SMALLINT:
			case java.sql.Types.INTEGER :
			case java.sql.Types.NUMERIC :
				return "整数型";
			case java.sql.Types.VARCHAR :
				return "文本型";
			case java.sql.Types.TIMESTAMP :
				return "时间型";
			default :
				return _currDataType.getName();
		}
	}
	private IPublishElement findPublishElement(int nObjectType,int nObjectId)
			throws WCMException {
		IPublishElement publishElement = PublishElementFactory.lookupElement(
				nObjectType, nObjectId);
		if (publishElement == null) {
			throw new WCMException("指定的对象[Type=" + nObjectType + ", Id="
					+ nObjectId + "]不存在！");
		}
		return publishElement;
	}
%>