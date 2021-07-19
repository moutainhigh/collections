	<input type="hidden" name="FlowDocId" id="FlowDocId" value="<%=nFlowDocId%>">
    <input type="hidden" name="objectId" id="objectId" value="<%=nObjectId%>">
    <input type="hidden" name="channelId" id="channelId" value="<%=nChannelId%>">
    <input type="hidden" name="viewId" id="viewId" value="<%=nViewId%>">	
	<%
		boolean zAdd = (nObjectId == 0);
		boolean bIsMutiTable = metaView.isMultiTable();
		Documents reldocs = null;
		String[] arValues = null;
		StringBuffer buffRelDocs = new StringBuffer(256);	
		StringBuffer buffValidation = new StringBuffer(512);		
		Set multiTableSet = new HashSet(1,1.0f);

		for(int i=0,size=metaViewFields.size();i<size;i++){
			metaViewField = (MetaViewField)metaViewFields.getAt(i);
			if(metaViewField == null) continue;
			//null , edit , hidden三层属性过滤.
			String sEditable = metaViewField.isEditable() ? "": "disabled";
			int nRequired = metaViewField.getPropertyAsInt("NOTNULL",0);
			boolean bIsHidden = metaViewField.isHidden();
			if(bIsHidden) continue;
			int rowId = metaViewField.getId();
			//modify by huxuanlai@ 2009/7/13 单表数据存储在wcmmetatableXXX表，多表视图存在wcmmetaviewXXX表中，此为其检索字段的区别
			String sFieldName = "";
			if(bIsMutiTable){
				sFieldName = metaViewField.getPropertyAsString("FIELDNAME");				
			}else{
				sFieldName = metaViewField.getPropertyAsString("DBFIELDNAME");				
			}
			String sAnotherName = CMyString.transDisplay(metaViewField.getAnotherName());
			String sDefaultValue = "";
			if(zAdd && !CMyString.isEmpty(metaViewField.getDefaultValue())){
				sDefaultValue = CMyString.transDisplay(metaViewField.getDefaultValue());
			}
			//此处没用CMyString.transDisplay转义sFieldValue，是因为编辑器字段可能需要显示原始的html代码。			
			String sFieldValue = CMyString.showNull(metaViewData.getRealProperty(sFieldName));
			if(CMyString.isEmpty(sFieldValue)){
				sFieldValue = sDefaultValue;
			}
			String sEnmValue = CMyString.transDisplay(metaViewField.getEnmValue());
			//类型判断信息
			int nFieldType = metaViewField.getType();

	%>
    <div class="row" name="<%=sFieldName%>" desc="<%=sAnotherName%>" _type="<%=nFieldType%>" required="<%=nRequired%>" >
        <span class="label"><%=CMyString.filterForHTMLValue(metaViewField.getAnotherName())%>：</span>
        <span class="value">
<%
		
		if(metaViewField.isFromMainTable()){
			switch(nFieldType){
				case 1:	
				int nDbType = metaViewField.getDBType();
				switch(nDbType){
					case 12://自定义类型：字符串
					makeValidation(buffValidation,sFieldName,nRequired);
					buffValidation.append("'string',max_len:");
					buffValidation.append(metaViewField.getLength());
					buffValidation.append("}");
%>
					<input type="text" class="txtcls" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=CMyString.transDisplay(sFieldValue)%>" <%=sEditable%>/>
				<%
					break;
					case 4://自定义类型：整数
					makeValidation(buffValidation,sFieldName,nRequired);
					buffValidation.append("'int',value_range:'-2147483648,2147483647'");
					buffValidation.append("}");
				%>
					<input type="text" class="txtcls" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" <%=sEditable%>/>
				<%
					break;
					case 8://自定义类型：双精度
					makeValidation(buffValidation,sFieldName,nRequired);
					buffValidation.append("'double',value_range:'-2e125,2e125'");
					buffValidation.append("}");
				%>
					<input type="text" class="txtcls" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" <%=sEditable%>/>
				<%
					break;
					case 6://自定义类型：小数
					makeValidation(buffValidation,sFieldName,nRequired);
					buffValidation.append("'float',value_range:'-2e125,2e125'");
					buffValidation.append("}");
				%>
					<input type="text" class="txtcls" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sFieldValue%>" <%=sEditable%>/>
				<%
					break;
					case 93://时间
				%>
				<%if(metaViewField.isEditable()){%>
					<script>					
					TRSCalendar.render({
						id : '<%=sFieldName%>',
						value : "<%=sFieldValue%>",
						timeable : true,
						sumbit : true,						
						required : <%=metaViewField.isNotNull()%>						
					});
					</script>
				<%}else{%>
					<INPUT class=inputtext id='<%=sFieldName%>' style="WIDTH: 80px" name='<%=sFieldName%>'	value="<%=sFieldValue.split(" ")[0]%>" disabled>
				<%}%>
				<%
					break;
					case 2005: //自定义类型：可视化编辑器
				%>	
				<%if(metaViewField.isEditable()){%>
					<span class="openeditor" id="<%=sFieldName%>_openeditor" _relFram="<%=sFieldName%>_frame" clickFn="openEditor"></span>
					<input type="hidden" name="<%=sFieldName%>" id="<%=sFieldName%>" />			
					<iframe src="../neweditor/editor.html" class="editorContainer" id="<%=sFieldName%>_frame" frameborder="0" style="height:280px;width:498px;border:0px;overflow:hidden;"></iframe>
					<script>						
						SetValueByEditor($("<%=sFieldName%>_frame"),"<%=CMyString.filterForJs(sFieldValue)%>");
						m_EditorIds.push("<%=sFieldName%>");
					</script>
				 <%}else{%>
					<iframe src="page/blank.html" class="editorContainer" id="<%=sFieldName%>_frame" name="<%=sFieldName%>" frameborder="0"></iframe>
					<script>
						SetValueByEditor("<%=sFieldName%>_frame","<%=CMyString.filterForJs(makeHtmlCon(sFieldValue))%>");
					</script>
				 <%}%>
				<%
					break;
					default: 
				%>
			<%}%>
		<%
			break;
			case 2://密码文本
			makeValidation(buffValidation,sFieldName,nRequired);
			buffValidation.append("'string',max_len:");
			buffValidation.append(metaViewField.getLength());
			buffValidation.append("}");
		%>
		<input type="password" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=CMyString.transDisplay(sFieldValue)%>" <%=sEditable%>/>
		<%
			break;
			case 3://普通文本
			makeValidation(buffValidation,sFieldName,nRequired);
			buffValidation.append("'string',max_len:");
			buffValidation.append(metaViewField.getLength());
			buffValidation.append("}");
		%>		
        <input type="text" class="txtcls" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=CMyString.transDisplay(sFieldValue)%>" <%=sEditable%>/>
		<%
			break;
			case 4://多行文本
			makeValidation(buffValidation,sFieldName,nRequired);
			buffValidation.append("'string',max_len:");
			buffValidation.append(metaViewField.getLength()).append(",");
			buffValidation.append("showid:'").append(sFieldName).append("validTip'");
			buffValidation.append("}");
		%>
		<textarea name="<%=sFieldName%>" id="<%=sFieldName%>" rows="6" cols="50" <%=metaViewField.isEditable() ? "":"readonly"%>><%=CMyString.filterForHTMLValue(sFieldValue)%></textarea><div style="margin-left:85px;height:18px;display:block;">&nbsp;<span id="<%=sFieldName%>validTip"></span></div>
		<%
			break;
			case 5://是否
		%>		
		<span class="input_radio_container">
			<input id="<%=sFieldName%>_0" type="radio" name="<%=sFieldName%>" value="1" <%=sFieldValue.equals("1")?"checked":""%> <%=sEditable%>/>
			<label for="<%=sFieldName%>_0" WCMAnt:param="metaviewdata_addedit_include.jsp.yes">是</label>
		</span>
		<span class="input_radio_container">
			<input id="<%=sFieldName%>_1" type="radio" name="<%=sFieldName%>" value="0" <%=sFieldValue.equals("0")?"checked":""%> <%=sEditable%>/>
			<label for="<%=sFieldName%>_1" WCMAnt:param="metaviewdata_addedit_include.jsp.no">否</label>
		</span>
		<%
			break;
			case 6://单选
		%>		
		<%	if(CMyString.isEmpty(sEnmValue)){
			   break;
			}
			arValues = sEnmValue.split("~");
			for(int ix=0,len=arValues.length;ix<len;ix++){
				String[] arValue = pairSplit(arValues[ix]);
				boolean bSelectFlag = false;
				sFieldValue = CMyString.transDisplay(sFieldValue);
				if(!CMyString.isEmpty(sFieldValue) && sFieldValue.equals(arValue[1])){
					bSelectFlag = true;					
				}
				
			%>
			<span class="input_radio_container">
				<input id="<%=sFieldName%>_<%=ix%>" type="radio" name="<%=sFieldName%>" value="<%=arValue[1]%>" <%=bSelectFlag ? "checked":""%> <%=sEditable%>/>
				<label for="<%=sFieldName%>_<%=ix%>"><%=arValue[0]%></label>
			</span>
		<%}%>
		<%
			break;
			case 7://下拉
		%>		
		<select class="select_container" id="<%=sFieldName%>" name="<%=sFieldName%>" <%=sEditable%>>
			<option value="" WCMAnt:param="metaviewdata_addedit_include.jsp.select">--请选择--</option>
			<%
			 if(!CMyString.isEmpty(sEnmValue)){
				arValues = sEnmValue.split("~");
				for(int ix=0,len=arValues.length;ix<len;ix++){
					String[] arValue = pairSplit(arValues[ix]);
					boolean bSelectFlag = false;
					sFieldValue = CMyString.transDisplay(sFieldValue);
					if(!CMyString.isEmpty(sFieldValue) && sFieldValue.equals(arValue[1])){
						bSelectFlag = true;					
					}

			%>
			<option value="<%=arValue[1]%>" <%=bSelectFlag ? "selected":""%>><%=arValue[0]%></option>
			<%}}%>
		</select>
		<%
			break;
			case 8://附件
		%>		
		<input id="<%=sFieldName%>" type="hidden" name="<%=sFieldName%>" value="<%=CMyString.transDisplay(sFieldValue)%>" />
		
		<form name="<%=sFieldName%>_frm" id="<%=sFieldName%>_frm" method="post" enctype="multipart/form-data">
			<%if(metaViewField.isEditable()){%>
			<div class="appendixBrowser">
				<input type="file" ignore="true" name="<%=sFieldName%>_browser_btn" id="<%=sFieldName%>_browser_btn" />
			</div>
			<%}%>
			<span id="<%=sFieldName%>_text" style="MARGIN-LEFT:80px"><%=sFieldValue%></span>
			<span class="appendix_delete" id="<%=sFieldName%>_delete_btn" 
				<%=CMyString.isEmpty(sFieldValue)?"style='display:none'":""%>></span>
		</form>
		<%if(metaViewField.isEditable()){%>
		<script>			
			wcm.AppendixFieldMgr.add("<%=sFieldName%>");
		</script>
		<%}%>
		<%
			break;
			case 9://多选
		%>						
		<%
			if(CMyString.isEmpty(sEnmValue)){
			   break;
			}

			arValues = sEnmValue.split("~");
			for(int ix=0,len=arValues.length;ix<len;ix++){
				String[] arValue = pairSplit(arValues[ix]);
				boolean bSelectFlag = false;
				sFieldValue = CMyString.transDisplay(sFieldValue);
				if(!CMyString.isEmpty(sFieldValue) && !CMyString.isEmpty(arValue[1])){
					if((","+sFieldValue).indexOf(","+arValue[1])!=-1){
						bSelectFlag = true;					
					}
				}
				
		%>
		<span class="input_checkbox_container">
			<input id="<%=sFieldName%>_<%=ix%>" type="checkbox" name="<%=sFieldName%>" value="<%=arValue[1]%>" <%=bSelectFlag ? "checked":""%> <%=sEditable%>/>
			<label for="<%=sFieldName%>_<%=ix%>"><%=arValue[0]%></label>
		</span>
		<%}%>			
		<%
			break;
			case 10://分类法
			StringBuffer sClassText = new StringBuffer(128);
			int nClassInfoRootId = -1;
			String classNotExist = LocaleServer.getString("metaviewdata.label.classNotExist","指定的分类法不存在!");
			//getPropertyAsString为id信息
			sFieldValue = CMyString.showEmpty(CMyString.transDisplay(metaViewData.getPropertyAsString(sFieldName)),sDefaultValue);
			if(sFieldValue.length() > 0){		
				String[] arId = sFieldValue.split(",");
				String[] arName = new String[arId.length];
				String classinfoNotExist = "<font color=red>" + 
					LocaleServer.getString("metaviewdata.label.classinfoNotExist","指定的分类法信息不存在!") 
					+ "</font>";
				boolean flag = true;

				for(int classLoop = 0; classLoop < arId.length; classLoop++){
					if(CMyString.isEmpty(arId[classLoop])){ 
	     				 flag = false;
						 break;
					}
					int nClassInfoId = 0;
					try{
						nClassInfoId = Integer.parseInt(arId[classLoop]);
					}catch(Exception ex){
					}
					ClassInfo oClassInfo = ClassInfo.findById(nClassInfoId);
					if(oClassInfo == null){
						 flag = false;
						 break;
					}else{
						 arName[classLoop] = CMyString.transDisplay(oClassInfo.getName());
					}
				}
				if(flag){
					sClassText.append(arName[0]).append('[').append(arId[0]).append(']');
					if(!metaViewField.isRadioOrCheck()){
						for(int ix=1,len=arId.length;ix<len;ix++){
							sClassText.append(",").append(arName[ix]).append('[').append(arId[ix]).append(']');
						}
					}
				}else{
					sClassText = new StringBuffer(classinfoNotExist);
				}

				//求rootid
				int nClassInfoId = 0;
				try{
					nClassInfoId = Integer.parseInt(arId[0]);
				}catch(Exception ex){
				}
				ClassInfo oClassInfo = ClassInfo.findById(nClassInfoId);
				if(oClassInfo == null){
					nClassInfoRootId = -1;
				}else{
					nClassInfoRootId = oClassInfo.getRootId();
				}

			}else if(nObjectId == 0 && !mDefaultClassInfo.isEmpty()){
				String[] defaultClassInfo =(String[]) mDefaultClassInfo.get(sFieldName.toUpperCase());
				if(defaultClassInfo != null){
					sFieldValue = defaultClassInfo[0];
					sClassText.append(defaultClassInfo[1]).append('[').append(sFieldValue).append(']');
				}
			}
			if(metaViewField.getClassId()>0 && CMyString.isEmpty(metaViewField.getClassName())){
				sClassText = new StringBuffer();
				sClassText.append("<font color=red>").append(classNotExist).append("[ID=")
					.append(metaViewField.getClassId()).append("]").append("</font>");			
			}
		%>				
		<span class="classinfo_container">
			<input type="hidden" id="<%=sFieldName%>"  name="<%=sFieldName%>" _type="classInfo" value="<%=CMyString.transDisplay(sFieldValue)%>" />
			<%if(metaViewField.isEditable()){%>
			<span class="selectclassinfo" id="<%=sFieldName%>_SelectClassInfo" 
				_classId="<%=metaViewField.getProperty("CLASSID")%>" 
				_treetype="<%=metaViewField.getPropertyAsInt("RADORCHK",0)%>"  
				_classDesc="<%=CMyString.transDisplay(metaViewField.getClassName())%>" _rootId="<%=nClassInfoRootId%>"  clickFn="selClassInfo"></span>
			<%}%>
			<span id="<%=sFieldName%>_Text"><%=sClassText.toString()%></span>
		</span>
		<%
			break;
			case 11://时间
		%>	
		<%if(metaViewField.isEditable()){%>
			<script>					
				TRSCalendar.render({
					id : '<%=sFieldName%>',
					value : "<%=sFieldValue.split(" ")[0]%>",
					timeable : false,
					sumbit : true,						
					required : <%=metaViewField.isNotNull()%>					
				});
			</script>
		<%}else{%>
			<INPUT class=inputtext id='<%=sFieldName%>' style="WIDTH: 80px" name='<%=sFieldName%>' value="<%=sFieldValue.split(" ")[0]%>" disabled>
		<%}%>
		
		<%
			break;
			case 12:
			case 16://可视化编辑器
		%>
		<%if(metaViewField.isEditable()){%>
		<span class="openeditor" id="<%=sFieldName%>_openeditor" _relFram="<%=sFieldName%>_frame" clickFn="openEditor"></span>
		<input type="hidden" name="<%=sFieldName%>" id="<%=sFieldName%>" /><iframe src="../neweditor/editor.html" class="editorContainer" id="<%=sFieldName%>_frame" frameborder="0" style="height:280px;width:498px;border:0px;overflow:hidden;"></iframe>
		<script>						
			SetValueByEditor("<%=sFieldName%>_frame","<%=CMyString.filterForJs(makeHtmlCon(sFieldValue))%>");
			m_EditorIds.push("<%=sFieldName%>");
		</script>
		<%}else{%>
				<iframe src="page/blank.html" class="editorContainer" id="<%=sFieldName%>_frame" name="<%=sFieldName%>" frameborder="0"></iframe>
				<script>
					SetValueByEditor("<%=sFieldName%>_frame","<%=CMyString.filterForJs(makeHtmlCon(sFieldValue))%>");
				</script>
		<%}%>
		<%
			break;
			case 14://相关文档
		%>
		<input type="hidden" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=CMyString.transDisplay(sFieldValue)%>" locateChannel="<%=metaViewField.getPropertyAsString("LOCATECHANNEL","")%>" />
		<%if(metaViewField.isEditable()){%>
		<span class="relationDocument" clickFn="selRelations" _jsonId="<%=sFieldName%>"></span>
		<%}%>
		<script>
			m_arRelationIds.push("<%=sFieldName%>");
		</script>
		<%
			reldocs = Documents.findByIds(loginUser, sFieldValue);	
			buffRelDocs.append("<RELATIONS>");
			if(!reldocs.isEmpty()){				
				for (int ix = 0, nSize = reldocs.size(); ix < nSize; ix++) {
					Document doc = (Document) reldocs.getAt(ix);
					if (doc == null) continue;
					buffRelDocs.append("<RELATION>");
					buffRelDocs.append("<RELDOC Id='");
					buffRelDocs.append(doc.getId());
					buffRelDocs.append("' ChannelId='");
					buffRelDocs.append(doc.getChannelId());
					buffRelDocs.append("' TITLE='" + doc.getTitle() + "'/>");
					buffRelDocs.append("</RELATION>");					
				}
					
			}
			buffRelDocs.append("</RELATIONS>");	
		%>		
		<textarea style="display:none" id="<%=sFieldName%>_relations_text">
			<%=PageViewUtil.toHtmlValue(buffRelDocs.toString())%>			
		</textarea>
		<div id="<%=sFieldName%>_tip">
			<span class="relationDocumentTip" id="<%=sFieldName%>_tip_container">			
			</span>
		</div>
		<%
			buffRelDocs.setLength(0);			
		%>						
		<%
			break;
			case 15://可输入下拉列表
			case 17:
			makeValidation(buffValidation,sFieldName,nRequired);
			buffValidation.append("'string',max_len:");
			buffValidation.append(metaViewField.getLength());
			buffValidation.append(",showid:'").append(sFieldName).append("_tip'");
			buffValidation.append("}");

			String sInputSelectDefaultValue = convertoDesc(sDefaultValue , sEnmValue);
			String sInputSelectValue = metaViewData.getRealProperty(sFieldName);
			if(CMyString.isEmpty(sInputSelectValue)){
				   sInputSelectValue = sInputSelectDefaultValue;
			}
		%>	
		<div class="<%=(nFieldType==15)?"inputSelect":"inputSelect_suggestion_div"%>">
			<input type="text" class="<%=(nFieldType==15)?"":"txtcls"%>" name="<%=sFieldName%>" id="<%=sFieldName%>" value="<%=sInputSelectValue %>"  isInputable="true" _type="<%=(nFieldType==15)?"inputselect":"suggestion"%>" <%=sEditable%>>
			<span <%=nFieldType == 17 ? "style='display:none'" : ""%>>
				<select id="<%=sFieldName%>_sel"  
				onclick="clickInputSel('<%=sFieldName%>','<%=sFieldName%>_sel')" onchange="changeInputSel('<%=sFieldName%>','<%=sFieldName%>_sel')" <%=sEditable%>>
					<%	
					if(!CMyString.isEmpty(sEnmValue)){
						arValues = sEnmValue.split("~");
						for(int ix=0,len=arValues.length;ix<len;ix++){
							String[] arValue = pairSplit(arValues[ix]);	
					%>
					<option value="<%=arValue[0]%>" _value="<%=arValue.length>1?arValue[1]:arValue[0]%>"><%=arValue[0]%></option>
					<%}}%>				
				</select>
			</span>
		</div>
		<span id="<%=sFieldName%>_tip" class="inputSelect_tip"></span>
		<%if(nFieldType == 17){%>
			<script>
				m_SuggestionIds.push("<%=sFieldName%>");
			</script>
		<%}%>
		<%
			break;
			default:
		%>
<%			
			}
%>		
<%
		
	}else{	
			String sReferTable = "WCMMetaTable" + metaViewField.getTableName();
			String sReferTableDataId = sReferTable + "ID";
			String sReferDBFieldName = metaViewField.getPropertyAsString("DBFIELDNAME");
			if(!multiTableSet.contains(sReferTable)){
				multiTableSet.add(sReferTable);
%>
		
<%}%>

		<span id="<%=sReferTable%>_<%=sFieldName.toUpperCase()%>" class="otherField_value">
			<%=CMyString.transDisplay(sFieldValue)%>
		</span>
		<span _referTo="<%=sReferTable%>" class="selectFields_browser" title="选择字段值" WCMAnt:paramattr="title:metaviewdata_addedit_include.jsp.selectFields" clickFn="browserOtherData" _fieldName="<%=sFieldName%>"></span>
<%
	}
	multiTableSet.clear();
%>
	</span>
  </div>    	
<%
	}

%>
<script>
var validations = [<%=(buffValidation.length()>0)?buffValidation.substring(1):""%>];
</script>
<%!
	private String getTemplateAsString(Template _template) {
		if(_template == null) {
			return LocaleServer.getString("metaviewdata.label.none", "无");
		}
		return _template.getName() + "&nbsp;";
	}	

	private String getAppendixsXml(Document _currDocument,int nAppendixType) throws WCMException{
		if(_currDocument == null || _currDocument.isAddMode()) return "&lt;Appendixes Num=&quot;0&quot;/&gt;";
		
		try{
			AppendixMgr m_oAppendixMgr = (AppendixMgr) DreamFactory
					.createObjectById("AppendixMgr");
			// 3.执行操作（获取指定文档的附件）
			Appendixes appendixes = m_oAppendixMgr.getAppendixes(_currDocument,nAppendixType ,null);
            // 将附件转换成为XML
            AppendixToXML appendixToXML = new AppendixToXML();
			return PageViewUtil.toHtmlValue(appendixToXML.toXmlString(null, appendixes));
		}catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,  LocaleServer.getString("addedit_include.exchange.error","转换Appendixs集合对象为XML字符串失败！"), ex);
		}
	}
	
	private String makeHtmlCon(String _sHtml) throws Exception{
		if(CMyString.isEmpty(_sHtml)) return _sHtml;
		com.trs.cms.content.HTMLContent htmlCon = new com.trs.cms.content.HTMLContent(_sHtml);
		return htmlCon.parseHTMLContent(null);
	}
	private void makeValidation(StringBuffer buff,String id,int required){
		buff.append(",{renderTo:'").append(id).append("',");
		buff.append("required:'").append(required).append("',");
		buff.append("no_desc:'',type:");
	}
	private String convertoDesc(String _sValue, String _sEnmValue) {
        if (CMyString.isEmpty(_sValue) || CMyString.isEmpty(_sEnmValue)) {
            return _sValue;
        }
        if (_sValue.length() <= 0 || _sEnmValue.indexOf('~') < 0)
            return _sValue;

        StringTokenizer st = new StringTokenizer(_sEnmValue, "~");
        while (st.hasMoreTokens()) {
            String item = st.nextToken();
            String[] aItem = item.split("`");

            if (aItem.length <= 1)
                continue;

            if (aItem[1].equalsIgnoreCase(_sValue)) {
                return aItem[0];
            }
        }

        return _sValue;
    }
	private String[] pairSplit(String sPair){
		String[] arValue = {"",""};
		if(sPair.indexOf("`") != -1){
			String[] tempArr = sPair.split("`");
			arValue[0] = tempArr[0];
			arValue[1] = tempArr[1];
		}else{
			arValue[0] = sPair;
			arValue[1] = "";
		}
		return arValue;
	}
%>