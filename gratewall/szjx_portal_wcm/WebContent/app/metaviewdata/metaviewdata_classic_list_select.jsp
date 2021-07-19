<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="java.sql.Types" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@include file="../include/public_server.jsp"%>
<%@include file="element_processor.jsp"%>
<%
response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires",0); 
response.setHeader("Cache-Control","private"); 
%>
<%
//为构造检索框搜索字段所添加的变量。
StringBuffer sSearchFieldInfo = new StringBuffer("[");

try{
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	MetaViewDatas viewDatas = (MetaViewDatas)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);

	IMetaDataDefCacheMgr oMetaDataDefCacheMgr = (IMetaDataDefCacheMgr) DreamFactory.createObjectById("IMetaDataDefCacheMgr");
	
%>

<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">		
<!-- ----------------------------------------------------------------------------------------- -->
<%
	//定义变量
	String sFieldName = "";
	String sAnotherName = "";
	String sDBFieldName = "";
	String sFieldValue ="";
	int columnNum = 0;//编辑/删除两列
	int nFieldType = 0;
	int nDBType = 0;
	MetaViewField oMetaViewField = null;
	
	//获取参数
	int nViewId = oMethodContext.getValue("ViewID", 0);
	String sMainTableName = oMethodContext.getValue("TABLENAME");
	String sSelectFields = 	oMethodContext.getValue("SELECTFIELDS");
	String sCurrFieldName = oMethodContext.getValue("CURRFIELD");
	String sCurrOrderBy = CMyString.showEmpty(oMethodContext.getValue("ORDERBY"), "METADATAID DESC");	
	String[] aSelectFields = sSelectFields.toUpperCase().split(",");
	int selLength = aSelectFields.length;

	//构造控制条件
	boolean[] clobFieldFlags = new boolean[selLength];
	boolean[] hiddenColFlags = new boolean[selLength];
	boolean[] passWordFlags = new boolean[selLength];
	String[] aSelectDBFields = new String[selLength];


	MetaViewField currMetaViewField = getViewField(nViewId,sCurrFieldName);
	if( currMetaViewField != null ) {
	//检索字段生成
	sSearchFieldInfo.append("{name:'MetaDataId',desc:'"+
		LocaleServer.getString("classic_list_select.record","记录ID")
		+"',type:'int'},");
	sSearchFieldInfo.append("{name:'");
	sSearchFieldInfo.append(sCurrFieldName);
	sSearchFieldInfo.append("',desc:'");
	sSearchFieldInfo.append(currMetaViewField.getAnotherName());
	sSearchFieldInfo.append("',type:'");
	sSearchFieldInfo.append(currMetaViewField.getDBType());
	sSearchFieldInfo.append("'},");
	}
	for(int i = 0,len=aSelectFields.length;i < len; i++ ){
		sFieldName = aSelectFields[i];
		oMetaViewField = oMetaDataDefCacheMgr.getMetaViewField(nViewId, sFieldName);
		if (oMetaViewField == null)	continue;		
		sAnotherName = oMetaViewField.getAnotherName();
		sDBFieldName = oMetaViewField.getDBName();

		aSelectDBFields[i] = sDBFieldName;  

		nDBType = oMetaViewField.getDBType();
		nFieldType = oMetaViewField.getType();			
		if(oMetaViewField.isSearchField()){
			//时间类型放在高级检索中。此处排除大字段,附件字段、相关文档字段，不提供检索。
			if(nDBType != 93 && nDBType != 2005 && nFieldType != 8 && nFieldType != 14){
				sSearchFieldInfo.append("{");
				sSearchFieldInfo.append("name:'").append(CMyString.filterForJs(sDBFieldName)).append("',")
				.append("desc:'").append(CMyString.filterForJs(sAnotherName)).append("',");
				switch(nDBType){
					case 4:
						sSearchFieldInfo.append("type:").append("'int'");
						break;
					case 6:
					case 8:
						sSearchFieldInfo.append("type:").append("'float'");
						break;
					case 12:
						sSearchFieldInfo.append("type:").append("'String'");
						break;
				}
				sSearchFieldInfo.append("},");
			}
		}

		if(!oMetaViewField.isInMultiTable()){
			hiddenColFlags[i] = true;
		}
		if(sFieldName.equalsIgnoreCase(sCurrFieldName)){
			hiddenColFlags[i] = false;
		}
		if(nFieldType == 2){
			passWordFlags[i] = true;
		}

		if(hiddenColFlags[i] == false){
			columnNum++;
		}
		//To add  more field and status 
		if(nFieldType == MetaDataConstants.FIELD_TYPE_HTML || nFieldType == MetaDataConstants.FIELD_TYPE_HTML_CHAR || nDBType == 2005){
			clobFieldFlags[i] = true;
	%>
	<td <%=hiddenColFlags[i]?"style='display:none'":""%>><%=sAnotherName%><%=getOrderFlag(sDBFieldName, sCurrOrderBy)%>
	</td>

	<%	}else{
	
	%>
		<td <%=hiddenColFlags[i]?"style='display:none'":""%> grid_sortby="<%=sDBFieldName%>"><%=sAnotherName%><%=getOrderFlag(sDBFieldName, sCurrOrderBy)%>
		</td>

	<%
		}
	}

	if(sSearchFieldInfo.length() > 1){
		sSearchFieldInfo.setCharAt(sSearchFieldInfo.length() -1 , ']');
	}else{
		sSearchFieldInfo.append("]");
	}		
				
%>
<!-- ----------------------------------------------------------------------------------------- -->
<%
	int nPageSize = 20;
	int nPageIndex = 1;
	if (oMethodContext != null) {
		nPageSize = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
		nPageIndex = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);
	}
	CPager currPager = new CPager();
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(viewDatas.size());
	currPager.setPageSize(nPageSize);
%>
	<tbody class="grid_body" id="grid_body">		
			<% if(viewDatas.isEmpty()){%>
				<tr>
					<td colspan="<%=columnNum%>" class="no_object_found" WCMAnt:param="metaviewdata_classic_list_select.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
				</tr>
			<%
				}else{
				MetaViewData viewData = null;
				for (int i = currPager.getFirstItemIndex(), len = currPager.getLastItemIndex(); i <= len; i++) {
					viewData = (MetaViewData)viewDatas.getAt(i-1);					
					if(viewData == null) continue;
					int rowId = viewData.getId();
					int viewDataId = viewData.getMetaDataId();
			%>
				<tr id="tr_<%=viewDataId%>" class='grid_row  grid_row_<%=((i & 1) == 0)?"even":"odd"%>' title="ID:<%=viewDataId%>" chnlId="<%=viewData.getChannelId()%>" rowid="<%=rowId%>" recId="<%=viewDataId%>">
					<%
						for(int j=0,fieldSize=aSelectDBFields.length;j<fieldSize;j++){
							sFieldValue =CMyString.showNull(viewData.getRealProperty(aSelectDBFields[j]));
					%>
					<td <%=hiddenColFlags[j]?"style='display:none'":""%>>
						<span class="grid_column columnwidth" name="fieldtext" _fieldName="<%=aSelectFields[j]%>" _needTrans="<%=(clobFieldFlags[j] || passWordFlags[j])%>">
							<%if(clobFieldFlags[j]){%>
								<textarea style="display:none">
									<%=sFieldValue%>
								</textarea>
								<span WCMAnt:param="metaviewdata_classic_list_select.jsp.textarea">大文本</span>
							<%}else if(passWordFlags[j]){%>
     							<textarea style="display:none"> 
								    <%="***"%>
								</textarea>
								<span>***</span>
							<%}else{%>
								<%=CMyString.transDisplay(sFieldValue, false)%>
							<%}%>							
						</span>
					</td>
					<%}%>					
				</tr>
			<%}}%>
		
	</tbody>	
</table>

<script>
	setSearchFieldInfo(<%=sSearchFieldInfo.toString()%>);	
	try{		
		var context = PageContext.getContext();
		Ext.apply(context.params, {	
			VIEWID : <%=nViewId%>
		});
		<%if(!viewDatas.isEmpty()){%>
			wcm.Grid.init({
				OrderBy : '<%=sCurrOrderBy%>',
				RecordNum : <%=currPager.getItemCount()%>
			});
		<%}%>
		PageContext.drawNavigator({			
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
		$MsgCenter.on({
			objType : WCMConstants.OBJ_TYPE_METAVIEWDATA,
			afterselect : function(event){
				if(event.getObjs().length() > 0){
					var sId = event.getObj().getId();
					var srcEl = $("tr_"+sId);
					var fieldCols = srcEl.getElementsByTagName("span");
					var fieldCol = null;
					var fields = [];
					var selectedValue = {id:sId,values:fields};	
					var sHtml = null;
					for(var i=0,len=fieldCols.length;i<len;i++){
						fieldCol = fieldCols[i];
						if(fieldCol.getAttribute("name") != "fieldtext") continue;
						if(fieldCol.getAttribute("_needTrans") == "true"){							
							sHtml = Element.first(fieldCol).innerText;
						}else{
							sHtml = fieldCol.innerHTML;
						}						
						fields.push({name:fieldCol.getAttribute("_fieldName"),value:sHtml});
					}
					
					var cbSelf = wcm.CrashBoarder.get(window).getCrashBoard();
					cbSelf.hide();
					cbSelf.notify(selectedValue);
					cbSelf.close();
				}
			}
		});
	}catch(err){
		alert(err.message);
	}
	
</script>

<%
}catch(Throwable tx){	
	throw new WCMException(LocaleServer.getString("list_select.runExce","metaviewdata_classic_list_select.jsp运行期异常!"), tx);
}
%>
<%!
	public String getOrderFlag(String field, String currOrderBy){
		if(CMyString.isEmpty(currOrderBy))return "";
		String[] orderBy = currOrderBy.toLowerCase().split(" ");
		field = field.toLowerCase();
		if(!orderBy[0].equals(field))return "";
		return "&nbsp;" + ("asc".equals(CMyString.showEmpty(orderBy[1], "asc"))?"↑":"↓");
	}

	private MetaViewField getViewField(int _nViewId,String _sFieldName) throws WCMException{
		WCMFilter filter = new WCMFilter("", "viewId = ?", "");
        filter.addSearchValues(_nViewId);		
		MetaViewFields oMetaViewFields = new MetaViewFields(null);
		oMetaViewFields.open(filter);
		if(!oMetaViewFields.isEmpty()){
			String sFieldName = _sFieldName.toUpperCase();
			for(int i=0,size=oMetaViewFields.size();i<size;i++){
				MetaViewField curr = (MetaViewField)oMetaViewFields.getAt(i);
				if(curr != null && sFieldName.equals(curr.getName().toUpperCase()) ) {
					return curr;
				}
			}
		}
        return null;
	}

	private String getPageAttributes(CPager _pager) {
		String sRetVal = "";
		sRetVal += "Num:"+String.valueOf(_pager.getItemCount());
		if (_pager.getPageSize() > 0){
			sRetVal += ",PageSize:"+String.valueOf(_pager.getPageSize());
			sRetVal += ",PageCount:"+String.valueOf(_pager.getPageCount());
			sRetVal += ",CurrPageIndex:"+String.valueOf(_pager.getCurrentPageIndex());
		}
		return sRetVal;
	}
    private RightValue getRightValue(CMSObj obj, User loginUser) throws WCMException {
		try {
			if (loginUser.isAdministrator()) {
				return RightValue.getAdministratorRightValue();
			}
			return getRightValueByMember(obj, loginUser);
		} catch (Exception e) {
			throw new WCMException(CMyString.format(LocaleServer.getString("classic_list_select.construct.failed","构造[{0}]权限信息失败!"),new Object[]{obj}), e);
		}
	}
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}
	private int getViewByTable(String tableName){
		if(CMyString.isEmpty(tableName)) return 0;
		String sName = tableName.toUpperCase();
		int ix = sName.indexOf("WCMMETATABLE");
		if(ix != 0) return 0;
		tableName = tableName.substring("WCMMETATABLE".length());		
		String sql = "select viewname,viewinfoid from xwcmviewinfo viewinfo where exists (select employer.viewid from xwcmmetaviewemployer employer where employer.viewid=viewinfo.viewinfoid) and viewinfo.maintablename=?";
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		com.trs.infra.persistent.db.DBManager dbmanager = com.trs.infra.persistent.db.DBManager.getDBManager();
		try{
			conn = dbmanager.getConnection();
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1,tableName);
			rs = pstmt.executeQuery();
			int id = 0;
			String viewName = null;
			while(rs.next()){
				viewName = rs.getString(1);				
				if(!viewName.equalsIgnoreCase(tableName)) continue;//多表视图
				if(id == 0){					
					id = rs.getInt(2);
				}else{
					return 0;
				}
			}
			return id;
		}catch(Exception e){			
			return 0;
		}finally{			
			if(rs != null){
				try{rs.close();}catch(Exception e){}
			}
			if(pstmt != null){
				try{pstmt.close();}catch(Exception e){}
			}
			if(conn != null){
				try{dbmanager.freeConnection(conn);}catch(Exception e){}
			}
		}
	}
	
%>