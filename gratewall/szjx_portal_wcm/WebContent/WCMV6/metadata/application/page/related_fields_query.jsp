<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%
	String sViewId = request.getParameter("viewId");
	int nViewId = 0;
	MetaView oView = null;
	if(sViewId != null){
		nViewId = Integer.parseInt(sViewId);
		oView = MetaView.findById(nViewId);
	}

	//1.获得基本参数信息,并初始化相关集合数据
	String sTableName = CMyString.getStr(request.getParameter("tableName"));
	if(sTableName == null){
		throw new Exception("没有指定对应的参数tableName");
	}
	String sSelectFields = CMyString.getStr(request.getParameter("selectFields"));
	if(sSelectFields == null){
		throw new Exception("没有指定对应的参数selectFields");
	}
	String[] aSelectFields = sSelectFields.split(",");
	MetaViewDatas viewDatas = new MetaViewDatas(sTableName, MetaDataConstants.FIELDNAME_METADATA_ID);
	if(oView != null){
		viewDatas.setMetaView(oView);
	}

	String sOrderBy = CMyString.showNull(request.getParameter("OrderBy"), "");
	WCMFilter filter = new WCMFilter(sTableName, "", sOrderBy, sSelectFields);
	String sAndOr = " and ";
	if("true".equals(request.getParameter("isor"))){
		sAndOr = " or ";
	}
	String sWhere = null;
	for(int i = 0; i < aSelectFields.length; i++){
		String fieldValue = CMyString.getStr(request.getParameter(aSelectFields[i]));
		if(!CMyString.isEmpty(fieldValue)){
			if(sWhere != null){
				sWhere += sAndOr;
			}else{
				sWhere = "";
			}
			if(aSelectFields[i].equals(MetaDataConstants.FIELDNAME_METADATA_ID)){
				sWhere += aSelectFields[i] + " = ? ";
			}else{
				sWhere += aSelectFields[i] + " like ? ";
				fieldValue = "%" + fieldValue + "%";
			}
			filter.addSearchValues(fieldValue);
		}
	}
	//deal with MetaDataId 
	String sMetaDataId = CMyString.getStr(request.getParameter("MetaDataId"));
	if(!CMyString.isEmpty(sMetaDataId)){
		if(sWhere != null){
			sWhere += sAndOr;
		}else{
			sWhere = "";
		}
		sWhere += "MetaDataId = ? ";
		filter.addSearchValues(sMetaDataId);
	}

	String sRelationTableName = MetaDataConstants.PREFIX_DBRELATION_NAME + sViewId;
	String sType = request.getParameter("type");
	if(sType != null){
		int nType = Integer.parseInt(sType);
		if(nType == 0){
			String sTemp = "MetaDataId not in (select " + sTableName + "ID from " + sRelationTableName + ")";
			if(sWhere != null){
				sWhere = "(" + sWhere + ") and (" + sTemp + ")"; 
			}else{
				sWhere = sTemp;
			}
		}else if(nType == 1){
			String sObjectId = request.getParameter("objectId");
			if(sObjectId != null){
				String sMainTableName = MetaDataConstants.makeTrueTableName(oView.getMainTableName());
				String sTemp = "MetaDataId not in (select " + sTableName + "ID from " + sRelationTableName + " where " + sMainTableName + "ID != " + sObjectId + ")";
				if(sWhere != null){
					sWhere = "(" + sWhere + ") and (" + sTemp + ")"; 
				}else{
					sWhere = sTemp;
				}
			}else{
				String sTemp = "MetaDataId not in (select " + sTableName + "ID from " + sRelationTableName + ")";
				if(sWhere != null){
					sWhere = "(" + sWhere + ") and (" + sTemp + ")"; 
				}else{
					sWhere = sTemp;
				}
			}
		}
	}

	filter.setWhere(sWhere);
	viewDatas.open(filter);

	//2.构造分页参数
	int iPageSize = 20, iPageIndex = 1;
	if(request.getParameter("PageSize") != null){
		iPageSize = Integer.parseInt(request.getParameter("PageSize"));
	}
	if(request.getParameter("CurrPage") != null){
		iPageIndex = Integer.parseInt(request.getParameter("CurrPage"));
	}
	CPager currPager = new CPager(iPageSize);
	currPager.setCurrentPageIndex(iPageIndex);
	currPager.setItemCount(viewDatas.size());
	response.setHeader("Num",String.valueOf(currPager.getItemCount()));
	response.setHeader("PageSize",String.valueOf(currPager.getPageSize()));
	response.setHeader("PageCount",String.valueOf(currPager.getPageCount()));
%>

<%
	//记录对应字段是否为大字段
	int[] aIsClob = new int[aSelectFields.length];

	for (int i = currPager.getFirstItemIndex(), index = 0, length = currPager.getLastItemIndex(); i <= length; i++) {
		MetaViewData viewData = (MetaViewData) viewDatas.getAt(i - 1);
		if (viewData == null)
			continue;
		index++;
		int viewDataId = viewData.getId();
		viewData.setMetaView(oView);
		try{

%>
		<DIV title="ID:<%=viewDataId%>" class="grid_row wcm_pointer Row<%=index%2%>" grid_rowid="<%=viewDataId%>" id="row_<%=viewDataId%>">
			<%
				for(int j = 0; j < aSelectFields.length; j++){
					if(aIsClob[j] == 0){// 尚未初始化
						IMetaDataDefCacheMgr cacheMgr = getMetaDataDefCacheMgr();
						MetaViewField oViewField = cacheMgr.getMetaViewField(nViewId, aSelectFields[j]);
						int fieldType = oViewField.getType();
						if(fieldType == MetaDataConstants.FIELD_TYPE_HTML
								|| fieldType == MetaDataConstants.FIELD_TYPE_HTML_CHAR){
							aIsClob[j] = 1;
						}else{
							aIsClob[j] = -1;
						}
					}
					String sFieldValue = CMyString.showNull(viewData.getRealProperty(aSelectFields[j]), "");
					if(aIsClob[j] == 1){
			%>
						<SPAN class="grid_column columnWidth editorColumn" align="left">大文本</span>
						<textarea style="display:none;"><%=sFieldValue%></textarea>
			<%
						continue;
					}
			%>
					<SPAN class="grid_column columnWidth" align="left">
						<%=CMyString.transDisplay(sFieldValue, false)%>
					</SPAN>
<%
				}
%>
		</DIV>
<%
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
%>		

<%!
	private IMetaDataDefCacheMgr getMetaDataDefCacheMgr() throws WCMException{
		return (IMetaDataDefCacheMgr) DreamFactory.createObjectById("IMetaDataDefCacheMgr");
	}
%>