<%!
	/**
	*获取当前记录对象的权限值
	*/
	private String getRightValue(User loginUser, MetaViewData metaViewData) throws WCMException{
		String rightValue = "";
        if (loginUser.isAdministrator()) {
			rightValue = RightValue.getAdministratorValues();
		}

		Channel oChannel = metaViewData.getChannel();
		ViewDocument viewDocument = ViewDocument.findById(oChannel,
				metaViewData.getChnlDocId(), null, "cruser,FLOWOPERATIONMARK");
		rightValue = viewDocument.getRightValue(loginUser).toString();
		return rightValue;
	}
	
	private RightValue getRightValueObj(User loginUser, MetaViewData metaViewData) throws WCMException{
		Channel oChannel = metaViewData.getChannel();
		ViewDocument viewDocument = ViewDocument.findById(oChannel,
				metaViewData.getChnlDocId(), null, "cruser,FLOWOPERATIONMARK");
		RightValue rightValue = viewDocument.getRightValue(loginUser);
		return rightValue;
	}	

	/**
	*获取文档的title信息
	*/
	private String getDocumentsTitle(String sDocIds, String sSeprator){
		int[] aDocIds = CMyString.splitToInt(sDocIds, ",");
		StringBuffer sb = new StringBuffer();
		Document doc = null;
		for(int index = 0, length = aDocIds.length; index< length; index++){
			try{
				doc = Document.findById(aDocIds[index], "docTitle");
				sb.append(CMyString.filterForHTMLValue(doc.getTitle())).append("[").append(aDocIds[index]).append("]");
			}catch(Throwable tx){
				sb.append(CMyString.filterForHTMLValue(LocaleServer.getString("metaviewdata.label.reldocNotExist","指定的相关文档不存在"))).append("[").append(aDocIds[index]).append("]").append(sSeprator);
			}
			if(index != length -1) sb.append(sSeprator);
		}
		return sb.toString();
	}

	/**
	*开始处理标题字段的HTML
	*/
	private String beginHandleTitleField(int nObjId, boolean bIsTop, boolean bTopForever, int nModal, boolean bCanView){
		StringBuffer sb = new StringBuffer();
		if(bIsTop) sb.append("<span class='")
			.append("document_topped" + (bTopForever ? "_forEver" : "")).append("'></span>");
		sb.append("<span class='record_modal_").append(nModal).append("'></span>");
		sb.append("<a contextmenu='1' unselectable='on' href='#' onclick='return false'");
		String recid = LocaleServer.getString("metaviewdata.label.recId","记录ID");
		sb.append(" title='").append(recid).append(":[").append(nObjId).append("]'");
		if(bCanView)sb.append(" grid_function='view'");
		sb.append(" class='titleField").append(bCanView ? "" : " no_right").append("'");
		sb.append(">");
		return sb.toString();
	}

	/**
	*结束处理标题字段的HTML
	*/
	private String endHandleTitleField(boolean bIsPic){
		StringBuffer sb = new StringBuffer();
		sb.append("</a>");
		if(bIsPic) sb.append("<span class='document_attachpic'></span>");
		return sb.toString();
	}

	/**
	*判断某个用户在某个对象上是否有相应的权限
	*/
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}

	private boolean hasRight(RightValue oRightValue, int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(oRightValue,_nRightIndex);
	}
%>