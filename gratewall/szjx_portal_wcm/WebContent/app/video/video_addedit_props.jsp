<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.components.wcm.publish.WCMContentPublishConfig" %>
<%@ page import="com.trs.components.common.job.Schedule" %>
<%@ page import="com.trs.components.common.job.Schedules" %>
<%@ page import="com.trs.components.common.job.JobWorkerType" %>
<%@ page import="com.trs.components.wcm.publish.domain.job.WithDrawJobWorker" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.service.IWCMProcessService" %>
<%@include file="video_addedit_extendedfield.jsp"%>
<style type="text/css">
	.ext-strict .extrict{width:93%;}
	.ext-ie8 .extrict{width:100%;}
</style>
<div class="props_column" id='props_setting' align="center">
	<table cellspacing=0 cellpadding=2 border=0 width="210">
	<tbody>
		<tr height="56" valign="bottom" align="center">
			<td _action="manageBasic">
				<div class="td_setting td_active2" id="td_basic_setting">
					<span class="setting_icon td_basic">&nbsp;</span>
					<span><%=LocaleServer.getString("document.label.basicset", "基本属性")%></span>
				</div>
			</td>
			<td _action="manageAdvanced">
				<div class="td_setting">
					<span class="setting_icon td_pubset">&nbsp;</span>
					<span><%=LocaleServer.getString("document.label.pubset", "高级设置")%></span>
				</div>
			</td>
		</tr>
		<tr height="56" valign="bottom" align="center">
			<td _action="manageRelation">
				<div class="td_setting">
					<span class="setting_icon td_relations">&nbsp;</span>
					<span><%=LocaleServer.getString("document.label.related", "相关文档")%></span>
				</div>
			</td>
			<td _action="manageExtends">
				<div class="td_setting">
					<span class="setting_icon td_extends">&nbsp;</span>
					<span><%=LocaleServer.getString("document.label.extend", "扩展字段")%></span>
				</div>
			</td>
		</tr>
	</tbody>
	</table>
</div>

<div class="props_column" id="basicprops" style="height:100%">
	<div class="props_column_title" id="basicprops_title" WCMAnt:param="document_props.jsp.basicAttribute">基本属性</div>
	<div class="props_column_body" id="basicprops_body" style="height:69%;overflow:auto;width:100%">
		<div class="attr_row">
			<span class="attr_name" WCMAnt:param="video_addedit_props.jsp.belongchnl">所属栏目:</span>
			<span id="sp_DocChannel" class="attr_value" style="padding-left:4px;" title="<%=docChannel.getId()%>-<%=CMyString.filterForHTMLValue(docChannel.getDesc())%>">
			<%=CMyString.filterForHTMLValue(displayShow(docChannel.getDesc()))%>
			</span>
			<span id="channel_select" _action="selectChannel" title="另存为..." style="width:16px;" WCMAnt:paramattr="title:document_addedit_props.jsp.saveAs"></span>
		</div>
		<%!
			private String displayShow(String _sName) {
				return _sName.length() > 15 ? _sName.substring(0,13) + ".." : _sName;
			}
		%>
		<div class="attr_row">
			<span class="attr_name" WCMAnt:param="document_props.jsp.headTitle">人物:</span>
			<span class="attr_input_text" style="width:140px;height:20px;">
				<input type="text" name="DOCPEOPLE" value="<%=v1(currDocument, "DOCPEOPLE")%>" pattern="string" max_len="200" elname="人物" WCMAnt:paramattr="elname:document_addedit_props.jsp.headLine"/>
			</span>
		</div>
		<div class="attr_row">
			<span class="attr_name" WCMAnt:param="document_props.jsp.docKeywords">关键词:</span>
			<span class="attr_input_text" style="width:140px;">
				<input type="text" name="DOCKEYWORDS" pattern="string" max_len="200" elname="关键词" id="dockeywords" WCMAnt:paramattr="elname:document_addedit_props.jsp.keyword" value="<%=v1(currDocument, "DockeyWords")%>"/>
			</span>
		</div>
		<div class="attr_row">
			<span class="attr_name" WCMAnt:param="video_addedit_props.jsp.author">作者:</span>
			<span class="attr_input_text" style="width:140px">
				<input type="text" name="DocAuthor" value="<%=v1(currDocument, "DocAuthor")%>" pattern="string" max_len="50" elname="作者" WCMAnt:paramattr="elname:document_addedit_props.jsp.docAuthor"/>
			</span>
		</div>
		<div class="attr_row">
			<span class="attr_name" WCMAnt:param="document_props.jsp.crtime">撰写时间:</span>
			<%
				String sDocRelTime = CMyDateTime.now().toString("yyyy-MM-dd HH:mm");
				if(nDocumentId>0){
					sDocRelTime = CMyString.showNull(currDocument.getReleaseTime().toString("yyyy-MM-dd HH:mm"));
				}
			%>
			<span>
				<input type="text" name="DocRelTime" id="DocRelTime" elname="撰写时间" value="<%=sDocRelTime%>" class="calendarText" WCMAnt:paramattr="elname:document_addedit_props.jsp.docreltime"/>
				<button type="button" class="DTImg" id="btnDocRelTime"><img src="../images/icon/TRSCalendar.gif" border=0 alt=""></button>
			</span>
		</div>
		<div class="attr_row" style="height:200px;">
			<span class="attr_name">
				<span style="float:left" WCMAnt:param="document_props.jsp.docAbstract">摘要:</span>
				<span id="DocAbstractHint" _action="openSimpleEditor2" title="打开简易编辑器" WCMAnt:paramattr="title:document_props.jsp.openSimpleEditor"></span>
			</span>
			<span onclick="extractAbstractAndKeywords();" id="ckmAuto" style="float:right;color:red;cursor:pointer;display:<%=bCKMExtract?"":"none"%>" WCMAnt:param="document_props.jsp.autoAbstract">自动摘要</span><div></div>
			<span class="attr_textarea">
				<textarea style="margin:0 5px;width:100%" id="DocAbstract" name="DocAbstract" cols="25" rows="10" pattern="string" max_len="1000" elname="摘要" WCMAnt:paramattr="elname:document_addedit_props.jsp.docAbstract"><%=v1(currDocument, "DocAbstract")%></textarea>
			</span>
		</div>
		<div class="attr_row" style="height:120px;">
			<span class="attr_name" style="overflow:visible;width:30px;" WCMAnt:param="document_props.jsp.docSource">来源:</span>
			<span class="attr_input_text" style="overflow:visible;width:142px;height:22px;">
				<input type="hidden" name="DocSource" value="<%=v1(currDocument, "DocSource")%>">
				<input type="text" id="DocSourceName"  name="docSourceName" value="<%=v1(currDocument, "DocSourceName")%>" pattern="string" max_len="100" elname="来源" WCMAnt:paramattr="elname:document_addedit_props.jsp.reSource"/>
			</span>
		</div>
		
	</div>
</div>
<div class="props_column" id="advancedprops" style="display:none;height:100%">
	<div class="props_column_title" id="advancedprops_title" WCMAnt:param="document_props.jsp.upperSet">高级设置</div>
	<div class="props_column_body" id="advancedprops_body" style="height:69%;overflow:auto;">
		<%
			//置顶信息
			boolean bIsCanTop = false;//是否在当前栏目有置顶权限
			//有修改文档的权限时才可做置顶设置
			bIsCanTop = DocumentAuthServer.hasRight(loginUser, currChannel, currDocument, WCMRightTypes.DOC_EDIT);
			boolean bTopped = false;//是否置顶
			boolean bTopForever = false;//是否永久置顶
			CMyDateTime dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
			if(nDocumentId>0){
				dtTopInvalidTime = currDocumentService.getTopTime(currDocument, currChannel);
				bTopped = currDocumentService.isDocumentTopped(currDocument, currChannel);
				if(bTopped && dtTopInvalidTime == null)
					bTopForever = true;
				if(dtTopInvalidTime == null){
					dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
				}
			}
			String sTopInvalidTime = dtTopInvalidTime.toString("yyyy-MM-dd HH:mm");
			Documents toppedDocuments = null;
			if(bIsCanTop && currChannel != null) {
				WCMFilter filter = new WCMFilter("", "DOCORDERPRI>0", "", "DocId, DocTitle, DocChannel");
				toppedDocuments = currChannelService.getDocuments(currChannel, filter);
			}
		%>
		<div style="display:<%=bIsCanTop?"":"none"%>;">
			<div class="attr_row" style="height:24px;line-height:24px;">
				<span class="attr_name" style="width:100px;font-weight:bold;font-size:12px" WCMAnt:param="document_props.jsp.topSet">置顶设置:</span>
			</div>
			<div class="attr_row" style="padding-left:2px;overflow:visible;height:100%;">
				<div class="topset_row" _action="topset">
					<input type="radio" id="pri_set_0" name="TopFlag" value="0">
					<label for="pri_set_0" WCMAnt:param="document_props.jsp.noSet">不置顶</label>
				</div>
				<div class="topset_row" _action="topset">
					<input type="radio" id="pri_set_2" name="TopFlag" value="2">
					<label for="pri_set_2" WCMAnt:param="document_props.jsp.topForEver">永久置顶</label>
				</div>
				<div class="topset_row">
					<div _action="topset">
						<input type="radio" id="pri_set_1" name="TopFlag" value="1">
						<label for="pri_set_1" WCMAnt:param="document_props.jsp.topTimeVal">限时置顶</label>
					</div>
					<div id="pri_set_deadline" style="display:<%=(!bTopped || bTopForever)?"none":""%>">
						<span style="padding-left:15px;">
							<input type="text" name="TopInvalidTime" id="TopInvalidTime" elname="限时置顶" value="<%=sTopInvalidTime%>" class="calendarText" WCMAnt:paramattr="elname:document_addedit_props.jsp.topintime"/>
							<button type="button" class="DTImg" id="btnTopInvalidTime"><img src="../images/icon/TRSCalendar.gif" border=0 alt=""></button>
						</span>
					</div>
				</div>
				<div class="attr_row" style="padding-left:2px;overflow:visible;height:100%;display:'';" id="display_padding">&nbsp;</div>
			</div>
			<div class="attr_row" id="topset_order" style="display:<%=(!bTopped)?"none":""%>">
				<div class="attr_row" style="height:24px;line-height:24px;">
					<span class="attr_name" style="width:100px;font-weight:bold;font-size:12px" WCMAnt:param="document_props.jsp.topOrder">置顶排序:</span>
				</div>
				<div class="attr_row" style="overflow:visible;height:100%;">
					<div class="attr_table" id="topset_order_table" style="width:100%;">
						<table border=0 cellspacing=1 cellpadding=0 style="table-layout:fixed;background:gray;" class="extrict">
						<thead>
							<tr bgcolor="#CCCCCC" align=center valign=middle>
								<td width="32" WCMAnt:param="document_props.jsp.order">序号</td>
								<td WCMAnt:param="document_props.jsp.docTitle">文档标题</td>
								<td width="40" WCMAnt:param="document_props.jsp.listOrder">排序</td>
							</tr>
						</thead>
						<tbody id="topset_order_tbody">
						<%
							if(toppedDocuments==null||toppedDocuments.size()==0){
						%>
							<tr bgcolor="#FFFFFF" align=center valign=middle>
								<td>&nbsp;</td>
								<td align=left>&nbsp;</td>
								<td>&nbsp;</td>
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
								<td>&nbsp;</td>
							</tr>
						<%
										continue;
									}//end if
						%>
							<tr bgcolor="#FFFFCF" align=center valign=middle _currdoc="1">
								<td><%=i+1%></td>
								<td align=left style="color:red;" WCMAnt:param="document_props.jsp.currDocument">--当前文档--</td>
								<td>
									<span class="topset_up" title="上移" _action="topsetUp" WCMAnt:paramattr="title:document_props.jsp.upper">&nbsp;</span>
									<span class="topset_down" title="下移" _action="topsetDown" WCMAnt:paramattr="title:document_props.jsp.lower">&nbsp;</span>
								</td>
							</tr>
						<%
								}//end for
							}
						%>
						</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<%!
			private String getTemplateAsString(Template _template) {
				if(_template == null) {
					return  LocaleServer.getString("document_props.label.none", "无");
				}
				return _template.getName() + "&nbsp;";
			}
		%>
		<%
			//发布信息
			boolean bMyIsCanPub = bIsCanPub;
			boolean bDefineSchedule = false;
			String sDocTemplateName = getTemplateAsString(null);
			String strExecTime = null;
			int nDocTemplateId = 0;
			CMyDateTime timeNow = CMyDateTime.now().dateAdd(CMyDateTime.MINUTE, 5);
			if(bMyIsCanPub && nDocumentId > 0) {
				com.trs.service.ITemplateService currTemplateService = ServiceHelper.createTemplateService();
				Template docTemplate = currTemplateService.getDetailTemplate(content);
				if(docTemplate != null) {
					nDocTemplateId = docTemplate.getId();
				}
				sDocTemplateName = getTemplateAsString(docTemplate);

				WCMContentPublishConfig currConfig = new WCMContentPublishConfig(loginUser, content);
				Schedule currSchedule = currConfig.getSchedule();
				bDefineSchedule = (currSchedule != null);
				if(bDefineSchedule) {
					strExecTime = currSchedule.getExeTime().toString("yyyy-MM-dd HH:mm");
				}
			}
			if(strExecTime==null){
				strExecTime = timeNow.toString("yyyy-MM-dd HH:mm");
			}
		%>
		<div class="attr_row" style="height:24px;line-height:24px;">
			<span class="attr_name" style="width:100px;font-weight:bold;font-size:12px" WCMAnt:param="document_props.jsp.pubSet">发布设置:</span>
		</div>
		<div class="attr_row">
			<span class="attr_name" WCMAnt:param="document_props.jsp.selectTemp">选择模板:</span>
			<span class="attr_name" style="width:16px;padding-top:0;float:left; margin-top: 5px;"><span id="template_setting" _action="selectTemplate" title="设置模板" WCMAnt:paramattr="title:document_props.jsp.setTemp"></span></span>
			<span id="spDetailTemp" _origTempId="<%=nDocTemplateId%>" _tempid="<%=nDocTemplateId%>" _tempname="<%=sDocTemplateName%>" style="color:green;font-weight:bold">
				<%=sDocTemplateName%>
			</span>
		</div>
		<div style="display:<%=bMyIsCanPub?"":"none"%>;">
			<div class="attr_row" style="height:28px;">
				<span class="attr_name" style="width:80px" _action="defineSchedule">
					<input type="checkbox" id="ip_DefineSchedule" name="DefineSchedule" IsBoolean="1" <%=bDefineSchedule?"checked":""%>>
					<label for="ip_DefineSchedule" WCMAnt:param="document_props.jsp.pubTimeVal">定时发布</label>:
				</span>
				<span id="sp_PublishOnTime" style="width:140px;height:28px;overflow:hidden;display:<%=bDefineSchedule?"":"none"%>">
					<span>
						<input class="calendarText" type="text" name="ScheduleTime" id="ScheduleTime" value="<%=strExecTime%>" elname="定时发布" WCMAnt:paramattr="elname:document_props.jsp.pubTimeVal">
						<button type="button" class="DTImg" id="btnScheduleTime"><img src="../images/icon/TRSCalendar.gif" border=0 alt=""></button>
					</span>
				</span>
				<span id="sp_NoPublish" style="width:120px;display:<%=bDefineSchedule?"none":""%>" WCMAnt:param="document_props.jsp.no">无</span>
			</div>
			<%
				Schedule unpubJob = null;
				if(nDocumentId > 0){
					unpubJob = getUnpubJob(loginUser,nDocumentId);	
				}
			%>
			<div class="attr_row">
				<span class="attr_name" style="width:80px">
					<input type="checkbox" name="unpubjob" id="unpubjob" value="" onclick="onPubjobset();" <%=(unpubJob==null?"":"checked")%>>
					<label for="unpubjob" WCMAnt:param="document_props.jsp.unpubschedule">定时撤稿</label>:													
				</span>
				<span id="unpubjobdatetime" style="width:140px;height:28px;overflow:hidden;display:<%=(unpubJob==null?"none":"inline")%>">
					<input type="hidden" name="UnpubSchId" id="UnpubSchId" value="<%=(unpubJob==null?0:unpubJob.getId())%>">
					<input type="text" name="UNPUBTIME" id="UNPUBTIME" value='<%=(unpubJob==null? "":unpubJob.getExeTime().toString("yyyy-MM-dd HH:mm"))%>' class="calendarText" elname="定时撤稿" WCMAnt:paramattr="elname:document_props.jsp.unpubschedule">
					<button id="btnCalunpubtime" type="button" class="DTImg"><IMG alt="" src="../images/icon/TRSCalendar.gif" border=0></button>
				</span>									
			</div>
		</div>
		<%
			if(nDocumentId == 0){
				int nFlowId = 0;
				String sFlowSetting = ConfigServer.getServer().getSysConfigValue(
							"DOC_ADD_FLOW_SETTING", "0");
				if(sFlowSetting != null && sFlowSetting.equals("1")) {
					IWCMProcessService processService = ServiceHelper.createWCMProcessService();
					Flow flow = processService.getFlowOfEmployer(currChannel);
					if(flow != null) {
						nFlowId = flow.getId();
					}
				}
				if(nFlowId>0){
		%>
		<div class="attr_row" style="height:24px;line-height:24px;">
			<span class="attr_name" style="width:100px;font-weight:bold;font-size:12px" WCMAnt:param="document_props.jsp.flowinfo">流转信息:</span>
		</div>
		<div>
			<iframe id="frmFlowDoc"  src="../flowdoc/workflow_process_init_for_doc.jsp?FlowId=<%=nFlowId%>" height="500px" scrolling="auto" frameborder="0"></iframe>
		</div>
		<%
			}
		}
		%>
	</div>
</div>

<div class="props_column" id="extendprops" style="display:none;height:100%">
	<div class="props_column_title" id="extendprops_title" WCMAnt:param="document_props.jsp.extendFieldManage">扩展字段管理</div>
	<div class="props_column_body" id="extendprops_body" style="width:98%;height:69%;overflow:auto">
    <%=showExtendFields(currExtendedFields,currDocument,strDBName,false,currChannel)%>
   </div>
</div>
<script language="javascript">
<!--
	Element["<%=bCKMExtract?"show":"hide"%>"]('ckmAuto');

	Event.observe(window, 'load', function(){
		if(!$('DOCKEYWORDS'))return;
		var sg1 = new wcm.Suggestion();
		var sInputValue = "";
		sg1.init({
			el : 'DOCKEYWORDS',
			autoComplete : false,
			requestOnFocus : false,
			execute : function(){
				//TODO override the method.
				if(sInputValue != ""&&sInputValue.charAt(sInputValue.length-1)!=";"){
					sInputValue += ";";
				}
				var sNewInputValue = this.getListValue();
				var inputValueArr = [];
				var oldInputValueArr = sInputValue.split(/[\s　,,;; . ,]/g );
				var newInputValueArr = sNewInputValue.split(/[\s　,,;; . ,]/g );
				for(var z=0;z<newInputValueArr.length;z++){
					if(!oldInputValueArr.include(newInputValueArr[z]))inputValueArr.push(newInputValueArr[z]);
				}
				sNewInputValue = inputValueArr.join(";")
				sInputValue += sNewInputValue;
				this.setInputValue(sInputValue);
				//alert(this.getInputValue());
			},
			request : function(sValue){			
				var items = [];
				var nLen = sValue.length;
				var arr = ["," , " " , "," , ";" , ";", ",", "."];
				if(sValue=="")sInputValue="";
				var arr1 = sValue.split(/[\s　,,;; . ,]/g );
				var arr2 = sInputValue.split(";");
				var arr3 = [];
				for(var m=0;m<arr2.length;m++){
					if(arr1.include(arr2[m]))arr3.push(arr2[m]);
				}
				if(arr.include(sValue.charAt(nLen-1))&&(!arr2.include(arr1[arr1.length-1])))arr3.push(arr1[arr1.length-1]);
				sInputValue = arr3.join(";");
				if(arr.include(sValue.charAt(nLen-1)))return;
				for (var i = 0; i < nLen; i++){
					var sChar = sValue.charAt(nLen-i-1);
					if(arr.include(sChar)){
						sValue = sValue.slice(nLen-i,nLen);
						break;
					}
					continue;
				}
				var oPostData = {
					siteType : <%=currDocument.getChannel().getSite().getType()%>,
					siteId : <%=currDocument.getChannel().getSiteId()%>,
					kname : sValue
				}
				BasicDataHelper.JspRequest('../keyword/keyword_create.jsp',oPostData,false,function(_trans,_json){
					//debugger
					var json = com.trs.util.JSON.eval(_trans.responseText.trim());
					for(var i=0;i<json.length;i++){
						items.push(json[i]);
					}
					sg1.setItems(items);
				}.bind(this));
			}
		});
	});
//-->
</script>