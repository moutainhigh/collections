<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelSyn" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ChannelSyn)){
		throw new WCMException(CMyString.format(LocaleServer.getString("channelsyn_findbyid.jsp.servicenoobject","服务(com.trs.ajaxservice.DocumentSynServiceProvider.findbyid)返回的对象类型不为ChannelSyn,而为{0},请确认."),new Object[]{result.getClass()}));
	}
	ChannelSyn obj = (ChannelSyn)result;
	boolean bCanEdit = hasRight(loginUser,obj,13);
	String sTochannelName = obj.getToChannel().getName();
	String sQuerySql = CMyString.filterForHTMLValue(obj.getWhereSql());
	String sEditable = bCanEdit?"editable":"readonly";
%>
<%!
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}

	private String typeConvert(int _nType) throws WCMException{
		switch(_nType){
			case 1 :
				return LocaleServer.getString("channelsyn_findbyid.label.copy", "复制");
			case 2 :
				return LocaleServer.getString("channelsyn_findbyid.label.quote", "引用");
			case 3 :
				return LocaleServer.getString("channelsyn_findbyid.label.mirror", "镜像");
			default:
				return LocaleServer.getString("channelsyn_findbyid.label.unknown", "未知");
		}
	}
%>
<div id="template_docSynDis" select="ChannelSyn" >
	<div class="attribute_row readonly">
		<span class="wcm_attr_name" WCMAnt:param="channelsyn_findbyid.jsp.toChannel">目标栏目:</span>
		<span class="wcm_attr_value"><%=sTochannelName%></span>
	</div>
	<div class="attribute_row_sep"></div>
	<div class="attribute_row"><span class="wcm_attr_name" WCMAnt:param="channelsyn_findbyid.jsp.execTime">执行时间</span></div>
	<div class="attribute_row SDATE <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="channelsyn_findbyid.jsp.beginTime">开始时间:</span>
		<span class="wcm_attr_value" id="SDATE_SPAN" _fieldName="SDATE" _fieldValue="<%=obj.getStartTime().toString("yy-MM-dd")%>" style="color:#09549F" validation="type:date,date_format:yy-mm-dd,required:'',desc:'执行开始时间'" validation_desc="执行开始时间" WCMAnt:paramattr="validation_desc:channelsyn_findbyid.jsp.execBeginTime" rightindex="13"><%=obj.getStartTime().toString("yy-MM-dd")%></span><button id="embed1" class="calendarShow"><IMG class="calImg" alt="" src="../images/icon/TRSCalendar.gif" border=0></button>
	</div>
	<div class="attribute_row EDATE <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="channelsyn_findbyid.jsp.endTime">结束时间:</span>
		<span class="wcm_attr_value" id="EDATE_SPAN" _fieldName="EDATE" _fieldValue="<%=obj.getEndTime().toString("yy-MM-dd")%>" style="color:#09549F" validation="type:date,date_format:yy-mm-dd,required:'',desc:'执行结束时间'" validation_desc="执行结束时间" WCMAnt:paramattr="validation_desc:channelsyn_findbyid.jsp.execEndTime" rightindex="13"><%=obj.getEndTime().toString("yy-MM-dd")%></span><button id="embed2" class="calendarShow"><IMG class="calImg" alt="" src="../images/icon/TRSCalendar.gif" border=0></button>
	</div>
	<div class="attribute_row_sep"></div>
	<div class="attribute_row"><span class="timerange" WCMAnt:param="channelsyn_findbyid.jsp.docTime">文档时间</span></div>
	<div class="attribute_row DOCSDATE <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="channelsyn_findbyid.jsp.docBeginTime">开始时间:</span>
		<span class="wcm_attr_value" id="DOCSDATE_SPAN" _fieldName="DOCSDATE" _fieldValue="<%=obj.getDocStartTime().toString("yy-MM-dd")%>" style="color:#09549F" validation="type:date,date_format:yy-mm-dd,required:'',desc:'文档开始时间'" validation_desc="文档开始时间" WCMAnt:paramattr="validation_desc:channelsyn_findbyid.jsp.textBeginTime" rightindex="13"><%=obj.getDocStartTime().toString("yy-MM-dd")%></span><button id="embed3" class="calendarShow"><IMG class="calImg" alt="" src="../images/icon/TRSCalendar.gif" border=0></button>
	</div>
	<div class="attribute_row DOCEDATE <%=sEditable%>" style="display:none">
		<span class="wcm_attr_name" WCMAnt:param="channelsyn_findbyid.jsp.docEndTime">结束时间:</span>
		<span class="wcm_attr_value" id="DOCEDATE_SPAN" _fieldName="DOCEDATE" _fieldValue="<%=obj.getDocEndTime().toString("yy-MM-dd")%>" style="color:#09549F" validation="type:date,date_format:yy-mm-dd,required:'',desc:'文档结束时间'"  validation_desc="文档结束时间" WCMAnt:paramattr="validation_desc:channelsyn_findbyid.jsp.textEndTime" rightindex="13"><%=obj.getDocEndTime().toString("yy-MM-dd")%></span><button id="embed4" class="calendarShow"><IMG class="calImg" alt="" src="../images/icon/TRSCalendar.gif" border=0></button>
	</div>
	<div class="attribute_row_sep"></div>
	<div class="attribute_row TransmitType <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="channelsyn_findbyid.jsp.transmiteType">分发模式:</span>
		<span class="wcm_attr_value select" _selectEl="selType" id="TransmitType" _fieldName="TransmitType" _fieldValue="<%=obj.getTransmitType()%>" style="color:#09549F"><%=typeConvert(obj.getTransmitType())%>&nbsp;</span>
		<span style="display:none">
			<select name="selType">
					<option value="1" WCMAnt:param="channelsyn_findbyid.jsp.copy">复制</option>
					<option value="2" WCMAnt:param="channelsyn_findbyid.jsp.quote">引用</option>
					<option value="3" WCMAnt:param="channelsyn_findbyid.jsp.mirror">镜像</option>
			</select>
		</span>
	</div>
	<div class="attribute_row_sep"></div>
	<div class="attribute_row WHERESQL <%=sEditable%>">
		<span class="timerange" WCMAnt:param="channelsyn_findbyid.jsp.queryCondition">检索条件</span>
		<span class="wcm_attr_value" _fieldName="WHERESQL" rightindex="13" _fieldValue="<%=sQuerySql%>" validation="type:'string',desc:'检索条件'" validation_desc="检索条件" WCMAnt:paramattr="validation_desc:channelsyn_findbyid.jsp.queryCondition" id='WHERESQL'><label class="querycondition"><%=sQuerySql%></label></span>
	</div>
	<div class="attribute_row_sep"></div>
</div>
<script language="javascript">
	var calSpIds = ['SDATE_SPAN','EDATE_SPAN','DOCSDATE_SPAN','DOCEDATE_SPAN'];
	var calBuTds = ['embed1','embed2','embed3','embed4'];
	for (var i = 0; i < calSpIds.length; i++){
		wcm.TRSCalendar.get({
			input : calSpIds[i],
			dtFmt : 'yy-mm-dd',
			getValue : calGetValue(calSpIds[i]),
			setValue : calSetValue(calSpIds[i]),
			handler : calBuTds[i]
		});
	}
</script>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("channelsyn_findbyid.runtime.error", "channelsyn_findbyid.jsp运行期异常!"), tx);
}
%>