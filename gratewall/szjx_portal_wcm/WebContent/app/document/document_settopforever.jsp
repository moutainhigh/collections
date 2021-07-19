<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDocs" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>

<%@include file="../include/public_processor.jsp"%>

<%!
	//获取到所有已经置顶的文档
	private Documents getToppedDocs(User user,Channel channel) throws WCMException{
			WCMFilter filter = new WCMFilter("","DOCORDERPRI>=1 and CHNLID=? and DOCSTATUS>0 and DOCCHANNEL>0","DOCORDERPRI desc","RECID,DOCID");
			ChnlDocs chnldocs = new ChnlDocs(user);
			filter.addSearchValues(channel.getId());
			chnldocs.open(filter);
			Documents documents = new Documents(user);
			if(chnldocs.isEmpty()){
				return documents;
			}			
			StringBuffer buff = new StringBuffer(128);
			for(int i=0,size=chnldocs.size();i<size;i++){
				ChnlDoc chnldoc = (ChnlDoc)chnldocs.getAt(i);
				if(chnldoc == null) continue;
				buff.append(',').append(chnldoc.getDocId());
			}
			chnldocs.clear();
			if(buff.length() > 1){
				documents.setSelect("DOCID,DOCTITLE,DOCCHANNEL");
				documents.addElement(buff.substring(1));				
			}
			return documents;
	}
%>

<%
	//初始化(获取数据)  获取栏目ID和文档ID。
	int nChannelId = processor.getParam("ChannelId", 0);
	int nDocumentId = processor.getParam("DocumentId", 0);
	processor.setEscapeParameters(new String[]{
		"DocumentId", "ObjectId"
	});
	
	Document currDocument = null;
	ChnlDoc currChnlDoc = null;
	Channel currChannel = null;
	boolean bSetExistDoc = false;
	
	if(nDocumentId > 0){
		//获取document中文档对象
		currDocument = (Document) processor.excute("document", "findbyid");
		if(currDocument==null){
			if(nDocumentId!=0){
				throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nDocumentId),WCMTypes.getLowerObjName(Document.OBJ_TYPE)}));
			}
		}
		//获取chnldoc中文档对象
		currChnlDoc = ChnlDoc.findByDocAndChnl(nDocumentId,nChannelId);
		if(currChnlDoc == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nDocumentId),WCMTypes.getLowerObjName(Document.OBJ_TYPE)}));
		}
	} else {
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nDocumentId),WCMTypes.getLowerObjName(Document.OBJ_TYPE)}));
	}
	
	if(nChannelId>0){
		//获得currChannel对象
		currChannel = Channel.findById(nChannelId);
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nChannelId),WCMTypes.getLowerObjName(Channel.OBJ_TYPE)}));
		}
		if(currChannel.isDeleted()){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,currChannel+LocaleServer.getString("document_addedit_label_5","已被删除!请刷新您的栏目树."));
		}
	}
%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
<head>
	<title>  </title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">

	<script src="../js/easyversion/lightbase.js"></script>
	<script src="../js/easyversion/extrender.js"></script>
	<script src="../js/easyversion/elementmore.js"></script>
	<script src="../js/source/wcmlib/core/MsgCenter.js"></script>
	<script src="../js/source/wcmlib/WCMConstants.js"></script>
	<script src="../js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../js/easyversion/calendar_lang/$locale$.js"></script>
	<script src="../js/easyversion/calendar3.js"></script>
	<link href="document_addedit.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		html,body { margin: 0; padding: 0;background : #EDEDED; }
		.advancedprops{width: 100%; height: 100%;}

		.advancedprops .topbutton{height: 28px; overflow:visible; padding-left: 2px; font-size: 12px;}
		.advancedprops .topbutton .pri_set_deadline{ position:absolute;top: 1px; right:25px;}
		.DTImg{ width: 25px; height: 20px; border: 0;cursor:pointer;background:url("../images/icon/TRSCalendar.gif") center center no-repeat;}
		.calendarText, .DTImg {vertical-align : middle;}
		.checkRadio, label {vertical-align : middle;}

		/*置顶排序样式*/
		.advancedprops .mainorder{position: absolute; top: 28px; bottom: 0px; width: 100%;}
		.topset_up,.topset_down{cursor:pointer;width:16px;height:16px;line-height:16px;margin-left:3px;float:left;padding:0;zoom:1;background-image: url(../images/icon/icons2.gif);background-repeat: no-repeat; }
		.topset_up{background-position: 0 -400px;}
		.topset_down{background-position: 0 -384px;}
	</style>

</head>
<body>
	<div class="advancedprops" id="advancedprops">
		<%
			//置顶信息
			boolean bIsCanTop = false;//是否在当前栏目有置顶权限
			//有修改文档的权限时才可做置顶设置
			bIsCanTop = DocumentAuthServer.hasRight(loginUser, currChannel, currDocument, WCMRightTypes.DOC_POSITIONSET);
			bIsCanTop = bIsCanTop || (nDocumentId == 0);
			boolean bTopped = false;//是否置顶
			boolean bTopForever = false;//是否永久置顶
			CMyDateTime dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
			if(nDocumentId>0){
				ChnlDoc topChnlDoc = ChnlDoc.findByDocAndChnl(currDocument,currChannel);
				dtTopInvalidTime = topChnlDoc.getInvalidTime();
				if(dtTopInvalidTime != null && dtTopInvalidTime.isNull()){
					dtTopInvalidTime = null;
				}
				bTopped = (topChnlDoc.getPropertyAsInt("DOCORDERPRI", 0) > 0);
				if(bTopped && dtTopInvalidTime == null)	bTopForever = true;
				if(dtTopInvalidTime == null){
					dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
				}
			}
			String sTopInvalidTime = dtTopInvalidTime.toString("yyyy-MM-dd HH:mm");
			int nDay = dtTopInvalidTime.getDay();
			Documents toppedDocuments = null;
			if(bIsCanTop && currChannel != null) {
				toppedDocuments = getToppedDocs(loginUser,currChannel);
			}
		%>
		<div style="display:<%=bIsCanTop?"":"none"%>;">
			<!--顶部单选按钮和截止时间选择-->
			<div class="topbutton" id="topbutton">
				<span id="choiced" class="" >
					<input type="radio" id="pri_set_0" name="TopFlag" class="checkRadio" value="0">
					<label for="pri_set_0" WCMAnt:param="document_props.jsp.noSet">不置顶</label>
					<input type="radio" id="pri_set_2" name="TopFlag" class="checkRadio" value="2">
					<label for="pri_set_2" WCMAnt:param="document_props.jsp.topForEver">永久置顶</label>
					<input type="radio" id="pri_set_1" name="TopFlag" class="checkRadio" value="1">
					<label for="pri_set_1" WCMAnt:param="document_props.jsp.topTimeVal">限时置顶</label>
				</span>
				<div id="pri_set_deadline" class="pri_set_deadline" style="display:<%=(!bTopped || bTopForever)?"none":""%>">
					<span>
						<input type="text" name="TopInvalidTime" id="TopInvalidTime" elname="限时置顶" value="<%=sTopInvalidTime%>" class="calendarText" WCMAnt:paramattr="elname:document_addedit_props.jsp.topintime" onblur = "checkDateRange()"/>
						<button type="button" class="DTImg" id="btnTopInvalidTime" ></button>
					</span>
				</div>
			</div> 
			<!--文档置顶排序-->
			<div class="mainorder" id="mainorder">
				<div class="attr_row" id="topset_order" style="display:<%=(!bTopped)?"none":""%>">
					<div style="font-size: 12px; height:24px;line-height:24px;">
						<span class="attr_name" id="attr_name" style="display:<%=(bTopped)?"none":""%>" WCMAnt:param="document_props.jsp.topOrder">文档置顶排序:</span>
						<span id="setOrder" class="setOrder" style="display:<%=(!bTopped)?"none":""%>" WCMAnt:param="document_settopforever.jsp.setcurrdocsort">
							设置当前文档的排序:  第 <input type="text" style="width: 20px; height: 13px;" name="indexValue" id="indexValue" value="1" /><input type="text" name="defaultValue" id="defaultValue" value="0" style="display: none;"/> 位
						</span>
					</div>
					<div style="font-size: 14px; overflow:hidden;height:100%;">
						<div id="docContextOrder" style="width:100%; height: 250px; overflow-y: scroll;">
							<table border=0 cellspacing=1 cellpadding=0 style="background:gray;width:100%;">
								<thead>
									<tr bgcolor="#CCCCCC" align=center valign=middle>
										<td width="40" WCMAnt:param="document_props.jsp.order">序号</td>
										<td WCMAnt:param="document_props.jsp.docTitle">文档标题</td>
									</tr>
								</thead>
								<tbody id="topset_order_tbody">
									<%
										if(toppedDocuments==null||toppedDocuments.size()==0){
									%>
									<tr bgcolor="#FFFFFF" align=center valign=middle>
										<td>&nbsp;</td>
										<td align=left>&nbsp;</td>
									</tr>
									<%
										}else{
											for(int i=0, n=toppedDocuments.size(); i<n; i++){
												Document topDoc = (Document)toppedDocuments.getAt(i);
												if(topDoc==null)continue;
												int nTopDocId = topDoc.getId();
												String sDocTitle = CMyString.truncateStr(topDoc.getTitle(), 55, "...");
												String sDocTitle2 = PageViewUtil.toHtmlValue(topDoc.getTitle());
												String sDocTitle3 = PageViewUtil.toHtmlValue(sDocTitle);
												if(nTopDocId!=nDocumentId){
									%>
									<tr bgcolor="#FFFFFF" align=center valign=middle _docid="<%=nTopDocId%>" _doctitle="<%=sDocTitle3%>">
										<td><%=i+1%></td>
										<td align=left title="<%=nTopDocId%>-<%=sDocTitle2%>"><div style="overflow:hidden"><%=CMyString.filterForHTMLValue(sDocTitle)%></div></td>
									</tr>
									<%
													continue;
												}//end if
									%>
									<tr bgcolor="#FFFFCF" align=center valign=middle _currdoc="1" _docid="<%=nTopDocId%>">
										<td><%=i+1%></td>
										<td align=left style="color:red;" WCMAnt:param="document_props.jsp.currDocument">--当前文档--</td>
									</tr>
									<%		bSetExistDoc = true;
											}//end for
										}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script language="javascript">
<!--
	//初始化页面数据，当前选中radio和当前文档排行值
	var checkValue = !<%= bTopped %> ? 0 : ( <%=bTopForever %> ? 2 : 1);
	document.getElementById('pri_set_' + checkValue).checked = true;
	var rows = $('topset_order_tbody').rows;
	for (var i=0; i<rows.length; i++) {
		if(rows[i].getAttribute("_docid") == <%=nDocumentId%>) {
			$("indexValue").setAttribute("value", i+1);
			$("defaultValue").setAttribute("value", i+1);
			break;
		}
	}
	//显示日期组件
	Event.observe(window, 'load', function(){
		wcm.TRSCalendar.get({
			input:"TopInvalidTime",  // 日历输入框的id  （input元素）
			handler:"btnTopInvalidTime", // 日期选择按钮的id
			withtime:true,
			dtFmt:'yyyy-mm-dd HH:MM'
		});
	});

//-->
</script>
<script language="javascript">
<!--
	//根据选择的radio进行显示和隐藏
	Event.observe("choiced", "click", function(event){
		var currEvent = window.event || event;
		var currElement = currEvent.target || currEvent.srcElement;
		if(currElement.tagName != "INPUT") return;
		var value = currElement.value;
		if(value=='1'){
			Element.show('topset_order');
			Element.show('pri_set_deadline');
			Element.hide('attr_name');
			Element.show('setOrder');
		}else if(value == '2'){
			Element.show('topset_order');
			Element.hide('pri_set_deadline');
			Element.hide('attr_name');
			Element.show('setOrder');
		}else {
			Element.hide('topset_order');
			Element.hide('pri_set_deadline');
			Element.hide('attr_name');
			Element.hide('setOrder');
			
		}
	});
//-->
</script>
<script language="javascript">
<!--
	window.m_cbCfg = {
		btns : [
			{
				text : '确定',
				cmd : function(){
					if(!checkDateRange()) {
						return false;
					}
					this.hide();
					//如果没有置顶权限，这些内容是不能显示的，因此直接关闭。
					if(!<%=bIsCanTop%>) {
						this.close();
					}
					//否则，构造数据，拼接成json对象
					//1、获取到选中的radio值。
					var TopFlag = ($$F("TopFlag") !=0) ? 1 : 0;
					var Position = TopFlag;
					if($$F("TopFlag") == 2){
						TopFlag = 2;
					}
					//2、根据输入的排序值获取相应的TargetDocumentId。
					var TargetDocumentId = 0;
					var rows = $('topset_order_tbody').rows;
					if(TopFlag){
						//获取到输入的文档排序值
						var index = $F("indexValue");
						var defaultIndex = $F("defaultValue");
						//debugger;
						if(0 == <%=toppedDocuments.size()%>) {//置顶文档还没有时
							TargetDocumentId = 0;
							Position = 0;
						} else if(index >= <%=toppedDocuments.size()+1%> ){//当选择未置顶的文档排序到最后
							TargetDocumentId = rows[<%=toppedDocuments.size()%>-1].getAttribute("_docid");
							Position = 0;
						} else if(index == <%=toppedDocuments.size()%> && <%=bSetExistDoc%>){//当调整某一篇已经置顶文档排序到最后
							TargetDocumentId = rows[index-1].getAttribute("_docid");
							Position = 0;
						} else if(index == 1){//index排在第一位
							TargetDocumentId = rows[index-1].getAttribute("_docid");
						} else if(0 < index && index <= <%=toppedDocuments.size()%>){//index在置顶文档总数之间
							//往上调整时。如：从第4位调整到第2位
							if(defaultIndex > index || defaultIndex == 0) {
								TargetDocumentId = rows[index-1].getAttribute("_docid");
							} else {
								//往下调整时。如：从第2位调整到第4位
								TargetDocumentId = rows[index].getAttribute("_docid");
							}
						} else {
							this.show();
							alert("您输入的排序数字有问题，请重新输入:");
							$("indexValue").focus();
							return false;
						}
					}
					var postdata = {
						"TopFlag" : TopFlag,
						"Position" : Position,
						"ChannelId" : <%=nChannelId%>,
						"DocumentId" : <%=nDocumentId%>,
						"TargetDocumentId" : TargetDocumentId,
						"InvalidTime" : ($$F("TopFlag")==1)?$('TopInvalidTime').value:'',
						"FlowDocId" : getParameter('FlowDocId') || 0
					};
					// 通知外部页面，传递参数返回参数
					this.notify(postdata);
					return false;
				}
			},
			{text : '关闭'}
		]
	};
//-->
</script>
<!-- 添加置顶时间的客户端校验 -->
<script src="document_settopforever.js"></script>
<!-- ...... -->
</html>