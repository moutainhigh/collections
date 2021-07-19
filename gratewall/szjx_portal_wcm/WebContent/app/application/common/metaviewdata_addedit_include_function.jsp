<%!
	public IMetaDataDefCacheMgr geMetaDataDefCacheMgr()throws WCMException{
		return (IMetaDataDefCacheMgr) DreamFactory.createObjectById("IMetaDataDefCacheMgr");
	}

	private String getEnumValue(String sFieldName)throws WCMException{
		MetaViewField field = geMetaDataDefCacheMgr().getMetaViewField(m_nViewId, sFieldName);
		String enumValue = field.getEnmValue();
		if(enumValue == null) return "";
		return enumValue;
	}

	private String getKeyWord(Channel channel)throws WCMException{
		WebSite currWebSite = channel.getSite();
		int nSiteId = currWebSite.getId();
		String sWhere = "SITEID in (0,?) and SITETYPE=4";
		WCMFilter filter = new WCMFilter("XWCMKEYWORD", sWhere, "KFREQ  desc","");
		filter.addSearchValues(0, nSiteId);
		Keywords currKeywords = Keywords.openWCMObjs(null, filter);
		String sKeyWordItems = "";
		for (int i = 0,nLen = currKeywords.size(); i < nLen; i++){
			Keyword currKeyword = (Keyword)currKeywords.getAt(i);
			if(currKeyword==null)continue;
			String sKeyWord = currKeyword.getKNAME();
			if(CMyString.isEmpty(sKeyWordItems)){
				sKeyWordItems += sKeyWord;
			}else{
				sKeyWordItems += "~" + sKeyWord;
			}
		}
		return sKeyWordItems;
	}

	private String getValue(MetaViewData obj, String sFieldName, String sDefaultValue){ 
		if(obj.isAddMode()){
			return sDefaultValue;
		}

		Object value = obj.getProperty(sFieldName);
		if(value == null){
			return sDefaultValue;
		}
		// 如果是浮点或者双精度，需要过滤掉后面的0
		if (value instanceof Double || value instanceof Float) {
			java.text.DecimalFormat df = new java.text.DecimalFormat("####.###");
			return df.format(value);			
		}

		return value.toString();
	}
	
	private String getClassInfoSelectType(String sFieldName)throws WCMException{
		String sSelectType = "0";
		MetaViewField oMetaViewField = geMetaDataDefCacheMgr().getMetaViewField(m_nViewId, sFieldName);
		sSelectType = CMyString.showObjNull(oMetaViewField.getAttributeValue("PARENTOPTIONAL"),"0");
		return sSelectType;
	}

	private String getDesc(MetaViewData obj, String sFieldName, String sDefaultValue){
		String sFieldValue = "";
		if(obj.isAddMode()) sFieldValue = sDefaultValue;
		else {
			try{
				sFieldValue = obj.getRealProperty(sFieldName);
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
		return CMyString.showNull(sFieldValue);
	}
	
	//by CC 20120606 增加获取分类法字段的描述信息,如果是新增模式时，根据分类ID获取分类法对象后，再获取描述信息
	private String getClassInfoDesc(MetaViewData obj, String sFieldName, String sDefaultValue) throws WCMException {
		String sFieldValue = "";
		
		if(obj.isAddMode()){
			int[] nClassIds = CMyString.splitToInt(sDefaultValue, ",");
			String[] sClassDesc = new String[nClassIds.length];
			for(int i=0; i< nClassIds.length; i++){
				ClassInfo chassInfo = ClassInfo.findById(nClassIds[i]);
				if(chassInfo != null){
					sClassDesc[i] = CMyString.isEmpty(chassInfo.getDesc()) ? chassInfo.getName() : chassInfo.getDesc();
				} else {
					sClassDesc[i] = sDefaultValue;
				}
			}
			sFieldValue =  CMyString.join(sClassDesc, ",");
		} else {
			try{
				sFieldValue = obj.getRealProperty(sFieldName);
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
		return CMyString.showNull(sFieldValue);
	}
	
	//日期和自定义时间类型混合在一起，并且不能在编辑页面展示desc信息，故需要用getCalender方法，而不是getValue方法取值。
	private String getCalendar(MetaViewData obj, String sFieldName, String sDefaultValue, String sType){
		if (sDefaultValue.startsWith("$sysdate")) {
			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String s1 = sDefaultValue.trim().substring("$sysdate".length());
			sDefaultValue = df.format(d);
			if (s1.length() > 1) {
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(d);
				gc.add(Calendar.DATE, Integer.parseInt(s1.replaceAll("\\+", "")));
				gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
						.get(Calendar.DATE));
				sDefaultValue = df.format(gc.getTime());
			}
		}
		String sFieldValue = "";
		if(obj.isAddMode()) sFieldValue = sDefaultValue;
		else sFieldValue = obj.getPropertyAsString(sFieldName);
		if(!CMyString.isEmpty(sFieldValue)){
			 if(sType.equals("11")){
				 sFieldValue = sFieldValue.substring(0, 10);
			 }
		}
		return CMyString.showNull(sFieldValue);
	}

	private String makeHtmlCon(String _sHtml) throws Exception{
		if(CMyString.isEmpty(_sHtml)) return _sHtml;
		com.trs.cms.content.HTMLContent htmlCon = new com.trs.cms.content.HTMLContent(_sHtml);
		return htmlCon.parseHTMLContent(null);
	}

	private String makeIntegerFiledValueRange(){
		com.trs.infra.persistent.db.DBManager dbman = com.trs.infra.persistent.db.DBManager.getDBManager();
		if(dbman.getDBTypeAsInt() ==  com.trs.infra.util.database.DBTypes.ORACLE){
			return "min:'-9999999999',max:'9999999999'";
		}else{
			return "min:'-2147483648',max:'2147483647'";
		}
	}
	private String getRootClassInfoFieldName(int _nViewId, int _nRootClassInfoId) throws WCMException {
		com.trs.infra.persistent.db.DBManager dbman = com.trs.infra.persistent.db.DBManager.getDBManager();
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		String sql = "select DBFIELDNAME from XWCMVIEWFIELDINFO where VIEWID=? and CLASSID=?";
		try{
			conn = dbman.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,_nViewId);
			pstmt.setInt(2,_nRootClassInfoId);
			rs = pstmt.executeQuery();
			if(rs.next()) return rs.getString(1);
		}catch(Exception ex){
			//ignore.
		}finally{
			if(rs != null) {
				try{
					rs.close();
				}catch(Exception ex){}
			}
			if(pstmt != null) {
				try{
					pstmt.close();
				}catch(Exception ex){}
			}
			if(conn != null) {
				try{
					dbman.freeConnection(conn);
				}catch(Exception ex){}
			}
		}
		return null;
	}

	private boolean showSaveAndFlow(int nDocumentId, int nChannelId) throws WCMException {
		// 配置项DocAdd_INTO_FLOW为true时文档直接进行流转，不用显示保存并流转按钮
		String sDocIntoFlow = ConfigServer.getServer().getSysConfigValue("DOCADD_INTO_FLOW", "true");
		if("true".equalsIgnoreCase(sDocIntoFlow)){
			return false;
		}
		Channel currChannel = Channel.findById(nChannelId);
		if(currChannel == null){
			return false;
		}
		// 判断栏目是否配置工作流，没有配置工作流时不显示保存并流转按钮
		FlowEmployMgr flowEmployMgr = (FlowEmployMgr) DreamFactory.createObjectById("FlowEmployMgr");
		if(flowEmployMgr.getFlow(currChannel) == null ){
			return false;
		}
		if(nDocumentId == 0){ // 新建文档
			return true;
		}
		// 判断文档是否可以进入流转，可以流转则返回true显示保存并流转按钮，否则返回false不显示保存并流转按钮
		return WCMProcessServiceHelper.canDocumentIntoFlow(nDocumentId);
	}
%>