<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPageContext" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishTagContext" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishTaskContext" %>
<%@ page import="com.trs.components.common.publish.domain.tagparser.TagDocument" %>
<%@ page import="com.trs.components.common.publish.domain.tagparser.TagDocumentReader" %>
<%@ page import="com.trs.components.common.publish.domain.tagparser.TagParseHelper" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Templates" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>

<%@include file="../include/public_server.jsp"%>

<%
	initPreviewUrl(request);

	//获取参数
	String sObjectIds = currRequestHelper.getString("ObjectIds");
	String sTemplateIds = currRequestHelper.getString("TemplateIds");
	int[] aTemplateIds = CMyString.splitToInt(sTemplateIds, ",");
	boolean bIncludeResource = currRequestHelper.getBoolean("IncludeResource", true);
	String sSplitIndexes = currRequestHelper.getString("splitIndexes");
	int[] nSplitIndexes = CMyString.splitToInt(sSplitIndexes, ",");
	

	//优先获取链接地址上传入的channelid，如果没有，则从系统配置中读取栏目的配置id（PRODUCT_FEATURES_CHANNELID）
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	if(nChannelId == 0){
		nChannelId = Integer.parseInt(ConfigServer.getServer().getSysConfigValue("PRODUCT_FEATURES_CHANNELID", "1031"));
	}

	// 2 构造站点的Url地址
	PublishTaskContext taskContext = new PublishTaskContext(null, null);
	PublishPathCompass compass = taskContext.getPathCompass();
	IPublishFolder folder = (IPublishFolder)PublishElementFactory.lookupElement(WCMTypes.OBJ_CHANNEL, nChannelId);
	String sSiteHttpUrl =  compass.getAbsoluteHttpPath(folder.getRoot());

	MetaViewDatas oViewDatas = MetaViewDatas.findByIds(sObjectIds);

	//校验权限

	//
	StringBuffer sbResult = new StringBuffer(300);
	sbResult.append("<div style='clear:both;'>");

	for (int i = 0, nSize = oViewDatas.size(); i < nSize; i++) {
		MetaViewData oViewData = (MetaViewData) oViewDatas.getAt(i);
		if(oViewData == null){
			continue;
		}
		if(!isVisible(loginUser, oViewData, WCMRightTypes.DOC_BROWSE)){
			continue;
		}
		Template oTemplate = Template.findById(aTemplateIds[i]);
		if(oTemplate == null){
			continue;
		}
		String sPreviewContent = getPublishContent(oViewData, oTemplate);

		/*对preview的图片地址进行替换，使其带上http前缀，
		*本来不应该在此次进行处理，而应该修改wcm的preview的设置，
		*从而使com.trs.components.common.publish.domain.publisher.PublishPathCompass.getPreviewHttpPath返回http前缀的地址
		*/
		sbResult.append(sPreviewContent.replaceAll("/preview/", m_sPreviewUrl + "preview/"));
		if((i+1) != nSize && isInArray(i, nSplitIndexes)){
			sbResult.append("</div><div style='clear:both;'>");
		}

	}
	sbResult.append("</div>");
	out.clear();
%>
<%if(bIncludeResource){%>
<link href="<%=sSiteHttpUrl%>images/features.css" rel="stylesheet" type="text/css" />
<%}%>
<%
	out.print(sbResult.toString());
%>


<%!
	private String m_sPreviewUrl = null;

	private void initPreviewUrl(HttpServletRequest request){
		if(m_sPreviewUrl != null){
			return;
		}
		String sURL = request.getRequestURL().toString();
		String sURI = request.getRequestURI().toString();
		m_sPreviewUrl = sURL.substring(0, sURL.length() - sURI.length());
		m_sPreviewUrl = CMyString.setStrEndWith(m_sPreviewUrl, '/');
	}

  private boolean isVisible(User currUser, MetaViewData _oMetaViewData, int _nRightIndex) throws WCMException {
        // 如果不需要校验单独授权，直接放行 }
        if (_nRightIndex == WCMRightTypes.ALL_BYPASS)
            return true;

        // 对于当前记录如果没有单独授权放行
        int nDocId = _oMetaViewData.getMetaDataId();
        if (!DocumentAuthServer.isDefineRightOnDocument(nDocId))
            return true;

        // 如果具有权限，放行
        RightValue rightValue = new RightValue();
        rightValue.load(currUser, Document.OBJ_TYPE, nDocId, true);
        // 由于没有权限需要忽略这条记录需要进行的一些操作
        if (hasRight(rightValue, _nRightIndex))
            return true;

        // 如果是创建者，放行
        // 判断当前用户是不是Owner
        final String GET_CRUSER_SQL = "select CrUser from WCMDocument where DocId=?";
        String sCrUser = DBManager.getDBManager().sqlExecuteStringQuery(
                GET_CRUSER_SQL, new int[] { nDocId });
        if (currUser.getName().equals(sCrUser))
            return true;

        return false;
    }

    private boolean hasRight(RightValue _rightValue, int nRightIndex) {
        if (nRightIndex == WCMRightTypes.OBJ_ACCESS)
            return _rightValue.getValue() > 0;

        return _rightValue.isHasRight(nRightIndex);
    }

	/**
	*使用指定的模板，发布指定的记录，返回发布后的内容
	*/
	private String getPublishContent(MetaViewData oViewData, Template oTemplate)throws Exception{
        TagDocument tagDoc = TagDocumentReader.read(oTemplate.getText());

        // 2 构造解析的上下文参数
        PublishTaskContext taskContext = new PublishTaskContext(null, null);

        IPublishElement pagePublishElement = PublishElementFactory.makeElementFrom(oViewData);

        PublishPageContext oPageContext = new PublishPageContext(pagePublishElement, taskContext);

        PublishTagContext tagContext = new PublishTagContext(oPageContext);
		

		IPublishFolder folder = ((IPublishContent)pagePublishElement).getOriginalHome();
		PublishPathCompass compass = taskContext.getPathCompass();

        // 3 解析
        String[] results = TagParseHelper.parseItems(tagDoc.getItems(), tagContext);

        return CMyString.showNull(results[0]);
	}

	private boolean isInArray(int item, int[] items){
		for(int i = 0; i < items.length; i++){
			if(items[i] == item){
				return true;
			}
		}
		return false;
	}
%>