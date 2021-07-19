<%!
	private String getDBTypeContainer(){
		StringBuffer sb = new StringBuffer();
		sb.append("<select formElement='true' name='dbType' style='display:none;'>");            
		MetaDataTypes dbDataTypes = MetaDataConstants.DB_DATA_TYPES;
		for(int i = 0, length = dbDataTypes.getDataTypesCount(); i < length; i++){
			MetaDataType dbDataType = dbDataTypes.getDataTypeAt(i);
			sb.append("<option _maxLength='" + dbDataType.getMaxLength() + "' _value='" + dbDataType.getKey() + "' value='" + dbDataType.getDataType() + "'>" + dbDataType.getDataTypeDesc() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	private String getFieldTypeContainer(){
		StringBuffer sb = new StringBuffer();
		sb.append("<select formElement='true' changeEvent='dataTypeChange' name='fieldType' style='display:none;'>");
		MetaDataTypes dataTypes = MetaDataConstants.DATA_TYPES;
		for(int i = 0, length = dataTypes.getDataTypesCount(); i < length; i++){
			MetaDataType dataType = dataTypes.getDataTypeAt(i);
			sb.append("<option _value='" + dataType.getKey() + "' value='" + dataType.getDataType() + "'>" + dataType.getDataTypeDesc() + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	private String getClassInfoContainer()throws WCMException{
		IClassInfoMgr m_oClassInfoMgr = (IClassInfoMgr) DreamFactory.createObjectById("IClassInfoMgr");
		StringBuffer sb = new StringBuffer();
		sb.append("<select formElement='true' name='classId' style='display:none;'>");
		sb.append("<option value='0'>--请选择一个分类--</option>");
		ClassInfos oClassInfos = m_oClassInfoMgr.queryChildren(null, null, null);
		for(int i = 0, length = oClassInfos.size(); i < length; i++){
			ClassInfo oClassInfo = (ClassInfo)oClassInfos.getAt(i);
			sb.append("<option value='" + oClassInfo.getId() + "'>" + CMyString.transDisplay(oClassInfo.getName()) + "</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
%>
<%
	int totalBlank = 10 - ((currPager!=null)?currPager.getItemCount():0);
	if(totalBlank < 5){//至少输出５个空行到客户端
		totalBlank = 5;
	}
	for(int i = nBlankStartIndex,n=0; n < totalBlank; i++,n++){
		String sObjectId = "n" + i;
		String sObjectName = "";
		String sObjectAnotherName = "";
		String sFieldTypeID = "3"; //TODO默认选中第一个
		String sFieldType = "";
		String sDBTypeID = "12";
		String sDBType = "";
		int classId = 0;
		String sClassInfoName = "";
		String sEnmValue = "";
		String sDisplayEnmValue = "none";
		String sDisplayClassId = "none";
%>
	<DIV style="display:none;" class="grid_row wcm_pointer" grid_rowid="<%=sObjectId%>" id="row_<%=sObjectId%>" objectName="<%=sObjectName%>">
		<SPAN class="grid_column" style="width:30px">
			<input type="checkbox" class="wcm_pointer grid_checkbox" id="ckb_<%=sObjectId%>"  name="ObjectId" value="<%=sObjectId%>"/>
		</SPAN>
		<SPAN class="grid_column object_edit" id="editOrSave_<%=sObjectId%>" style="width:30px" grid_function="editInRow"></SPAN>
		<SPAN class="grid_column ObjectDesc" style="width:130px;" title="<%=sObjectAnotherName%>">
			<span><a href="#" class="anotherName" grid_function="edit" onclick="return false;" id="anotherName_<%=sObjectId%>"><%=sObjectAnotherName%></a></span>
			<input validation="type:'string',required:'',max_len:'100',desc:'中文名称'" formElement="true" type="text" name="anotherName" value="" style="display:none;" class="input_text">
		</SPAN>
		<SPAN class="grid_column" style="width:120px;font-size:12px;text-overflow:ellipsis;" align="left">			
			<span id="fieldName_<%=sObjectId%>"><%=sObjectName%></span>
			<input validation="type:'common_char2',required:'',max_len:'30',desc:'英文名称'" formElement="true" type="text" name="fieldName" value="" style="display:none;" class="input_text">
		</SPAN>
		<SPAN class="grid_column ObjectType" grid_dywidth="ObjectType,1.0">
			<span _id="<%=sFieldTypeID%>" id="fieldType_<%=sObjectId%>"><%=sFieldType%></span>
			<%=sFieldTypeContainer%>
			<span id="enmValueContainer_<%=sObjectId%>" style="display:none;">
				<span id="enmValue_<%=sObjectId%>"><%=sEnmValue%></span>
				<input formElement="true" type="text" name="enmValue" value="" style="display:none;width:130px;" class="input_text" _title="格式为:item~item<br><span style='white-space:nowrap;overflow:visible;'>其中item为：label`value或label</span><br>如:<li>中国`1~美国`2~英国`3<br><li>中国~美国~英国<br><font color='red'>双击打开构造页面</font>">
			</span>
			<span id="classIdContainer_<%=sObjectId%>" style="display:none;">
				<span _id="<%=classId%>" id="classId_<%=sObjectId%>"><%=sClassInfoName%></span>
				<%=sClassInfoContainer%>
			</span>
		</SPAN>
		<SPAN class="grid_column" style="width:100px">
			<span _id="<%=sDBTypeID%>" id="dbType_<%=sObjectId%>"><%=sDBType%></span>
			<%=sDBTypeContainer%>
		</SPAN>
		<SPAN id="delete_<%=sObjectId%>" class="grid_column object_delete" style="width:30px;border-right:0" grid_function="delete" title="删除"></SPAN>
		<SPAN id="cancel_<%=sObjectId%>" class="grid_column object_cancel" style="width:30px;border-right:0;display:none;" grid_function="cancel" title="撤销"></SPAN>
	</DIV>
<%}%>