<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.cms.content.ExtendedField" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.database.DataType" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="java.sql.Types" %>
<script language="javascript">
<!--
	var calenderControls = [];
//-->
</script>
<%!
		private String showExtendFields(ContentExtFields currExtendedFields,Document currDocument,String strDBName,boolean bReadOnly,Channel oChannel) throws Throwable{
		String sRetVal = "";
		if(currExtendedFields != null && !currExtendedFields.isEmpty()){
			ContentExtField currExtendedField = null;
			String sExtFieldName = "";
			String sExtFieldValue = "";
			ExtendedField newContentExtField = null;
			for(int i=0; i<currExtendedFields.size(); i++){
				try{
					currExtendedField = (ContentExtField)currExtendedFields.getAt(i);
					if(currExtendedField == null) continue;
					newContentExtField = ExtendedField.findById(currExtendedField.getExtFieldId());
					sExtFieldName = PageViewUtil.toHtml(currExtendedField.getName());
					//做一个过滤，如果前面已经输出了，这里就不再输出
					if(isHasShowField(oChannel,sExtFieldName))continue;

					if(currExtendedField.getType().getType() == java.sql.Types.TIMESTAMP) {
						CMyDateTime tmpDateTime = currDocument.getPropertyAsDateTime(currExtendedField.getName());
						if(tmpDateTime == null) {
							sExtFieldValue = "";
						} else {
							sExtFieldValue= PageViewUtil.toHtmlValue(tmpDateTime.toString("yyyy-MM-dd HH:mm"));
							if(sExtFieldValue == ""){
								sExtFieldValue = PageViewUtil.toHtmlValue(newContentExtField.getPropertyAsString("FIELDDEFAULT"));
							}
						}
					} else {
						sExtFieldValue= PageViewUtil.toHtmlValue(currDocument.getPropertyAsString(currExtendedField.getName()));
						if(sExtFieldValue == ""){
							String sContentExtFieldTypeName = CMyString.filterForHTMLValue(newContentExtField.getAttributeValue("FIELDTYPE"));
								sExtFieldValue = PageViewUtil.toHtmlValue(newContentExtField.getPropertyAsString("FIELDDEFAULT"));
						}
					}
					sRetVal += "<div class=\"attr_row_extended\"><SPAN class=\"attr_name_extended\" title='"+sExtFieldName+"["+PageViewUtil.toHtml(toDescription(currExtendedField.getType(), strDBName))+"]'>";
					sRetVal += PageViewUtil.toHtml(currExtendedField.getDesc());
					sRetVal += ":";
					sRetVal += getEditorIcon(currExtendedField);
					sRetVal += "</span>";
					sRetVal += getPropertyHtml(currExtendedField,sExtFieldValue,bReadOnly);
					sRetVal += "</div>";
				} catch(Exception ex){
					throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, CMyString.format(LocaleServer.getString("document_addedit_extendedfield.jsp.getfieldattrfailue", "获取第[{0}]个扩展字段的属性失败!"),
						new int[]{(i+1)}), ex);
				}//end try-catch
			}//end for
		}
		else{
			sRetVal += "<div><SPAN colSpan='2'>";
			sRetVal += LocaleServer.getString("contentextfield.label.noExtField", "无扩展字段");
			sRetVal += "</div>";
		}
		return sRetVal;
	}
	
	private boolean isHasShowField(Channel currChannel,String sExtFieldName){
		String sBaseProps = currChannel.getPropertyAsString("BASEPROPS");
		String sOtherProps = currChannel.getPropertyAsString("OTHERPROPS");
		String sAdvanceProps = currChannel.getPropertyAsString("ADVANCEPROPS");
		if(!CMyString.isEmpty(sBaseProps) && sBaseProps.indexOf(sExtFieldName)>-1)return true;
		if(!CMyString.isEmpty(sOtherProps) && sOtherProps.indexOf(sExtFieldName)>-1)return true;
		if(!CMyString.isEmpty(sAdvanceProps) && sAdvanceProps.indexOf(sExtFieldName)>-1)return true;
		return false;
	}

	private String showSingleExtendField(String sFieldName,Document currDocument,int nChannelId,String strDBName,boolean bReadOnly) throws Throwable{
		String sRetVal = "";
		ContentExtField currExtendedField = null;
		String sExtFieldName = "";
		String sExtFieldValue = "";
		ExtendedField newContentExtField = null;
		try{
			String sWhere = "OBJID=? and EXTFIELDID in (select EXTFIELDID from WCMEXTFIELD where FIELDNAME=?)";
			WCMFilter filter = new WCMFilter("WCMCONTENTEXTFIELD",sWhere,"","");
			filter.addSearchValues(nChannelId);
			filter.addSearchValues(sFieldName);
			ContentExtFields m_oContentExtFields = ContentExtFields.openWCMObjs(null,filter);
			if(m_oContentExtFields.size()>=1){
				currExtendedField = (ContentExtField)m_oContentExtFields.getAt(0);
			}
			if(currExtendedField == null) return "";
			newContentExtField = ExtendedField.findById(currExtendedField.getExtFieldId());
			sExtFieldName = PageViewUtil.toHtml(currExtendedField.getName());

			if(currExtendedField.getType().getType() == java.sql.Types.TIMESTAMP) {
				CMyDateTime tmpDateTime = currDocument.getPropertyAsDateTime(currExtendedField.getName());
				if(tmpDateTime == null) {
					sExtFieldValue = "";
				} else {
					sExtFieldValue= PageViewUtil.toHtmlValue(tmpDateTime.toString("yyyy-MM-dd HH:mm"));
					if(sExtFieldValue == ""){
						sExtFieldValue = PageViewUtil.toHtmlValue(newContentExtField.getPropertyAsString("FIELDDEFAULT"));
					}
				}
			} else {
				sExtFieldValue= PageViewUtil.toHtmlValue(currDocument.getPropertyAsString(currExtendedField.getName()));
				if(sExtFieldValue == ""){
					String sContentExtFieldTypeName = CMyString.filterForHTMLValue(newContentExtField.getAttributeValue("FIELDTYPE"));
						sExtFieldValue = PageViewUtil.toHtmlValue(newContentExtField.getPropertyAsString("FIELDDEFAULT"));
				}
			}
			sRetVal += "<div class=\"attr_row_extended\"><SPAN class=\"attr_name_extended\" title='"+sExtFieldName+"["+PageViewUtil.toHtml(toDescription(currExtendedField.getType(), strDBName))+"]'>";
			sRetVal += PageViewUtil.toHtml(currExtendedField.getDesc());
			sRetVal += ":";
			sRetVal += getEditorIcon(currExtendedField);
			sRetVal += "</span>";
			sRetVal += getPropertyHtml(currExtendedField,sExtFieldValue,bReadOnly);
			sRetVal += "</div>";
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, LocaleServer.getString("document_addedit_extendedfield.jsp.getthisextendfieldfailure","获取这个扩展字段的属性失败!"), ex);
		}//end try-catch
		return sRetVal;
	}
		// 将字段类型转换成中文
	private String toDescription(DataType _currDataType, String _strDBName){
		if(_currDataType == null || _strDBName == null) {
			return "";
		}
		int nDataType = _currDataType.getType();
		switch(nDataType) {
			case java.sql.Types.FLOAT :
//				if(_strDBName.equalsIgnoreCase("Oracle")) {
//					return LocaleServer.getString("contentextfield.label.int", "整数型");
//				}
				return LocaleServer.getString("contentextfield.label.float", "小数型");
			case java.sql.Types.SMALLINT:
			case java.sql.Types.INTEGER :
			case java.sql.Types.NUMERIC :
				return LocaleServer.getString("contentextfield.label.int", "整数型");
			case java.sql.Types.VARCHAR :
				return LocaleServer.getString("contentextfield.label.text", "文本型");
			case java.sql.Types.TIMESTAMP :
				return LocaleServer.getString("contentextfield.label.time", "时间型");
			default :
				return _currDataType.getName();
		}
	}
	private String getEditorIcon(ContentExtField _currField) throws Exception{
		if(_currField == null || _currField.getType() == null) return "";
		int nFieldType = 0;
		try{
			nFieldType = Integer.parseInt(_currField.getAttributeValue("FIELDTYPE"));
		}catch(Throwable t){
			//just skip it.
		}
		if(nFieldType == 10){
			return "<span style=\"float:none\" id=\"DocAbstractHint\" onclick=\"openSimpleEditor3('" + _currField.getName() + "')\" title=\"打开简易编辑器\"  WCMAnt:paramattr=\"title:document_props.jsp.openSimpleEditor\"></span>";
		}
		return "";
	}

	private String getPropertyHtml(ContentExtField _currField) throws Exception{
		return getPropertyHtml(_currField,"",false);
	}	
	//考虑扩展字段的各种类型在面板上的显示，就再写一个区分吧！
	private String getPropertyHtml(ContentExtField _currField,String _sValue,boolean bReadOnly) throws Exception{
		if(bReadOnly){
			return _sValue;
		}
		if(_currField == null || _currField.getType() == null) return "";
		int nDataType = _currField.getType().getType();
		String sHtml = "";
		String sName = CMyString.filterForJs(_currField.getName());
		String sValue = CMyString.filterForJs(_sValue);
		//时间型使用控件显示
		if(nDataType == java.sql.Types.DATE || nDataType == java.sql.Types.TIMESTAMP) {
			sHtml = "<input class=\"calendarText\" type=\"text\" name=\"" +  sName + "\" id=\"" + sName + "\" value=\"" + sValue + "\"/><button type=\"button\" class=\"DTImg\" id=\"btn" + sName + "\"><img src=\"../images/icon/TRSCalendar.gif\" border=0 alt=\"\"></button>";
			sHtml += "<script>window.calenderControls.push(\'" + sName + "\');</script>";
			return sHtml;
		}
		String sPattern = getPattern(nDataType);
		String sEnmValue = CMyString.transDisplay(_currField.getAttributeValue("ENMVALUE"));
		String[] arValues = null;
		StringBuffer buffValidation = new StringBuffer(512);
		String sDbFieldName = _currField.getName().toUpperCase();
		if("string".equals(sPattern)){
			if(_currField.getAttributeValue("FIELDTYPE") == null){
				sHtml = "<TEXTAREA rows=3 ID=\"" + sName + "\" NAME=\"" + sName +  "\" pattern=\"string\" max_len=\"" + _currField.getMaxLength() + "\" elname=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\" >"+ _sValue+"</TEXTAREA>";
				return sHtml;
			}
			int nFieldType = Integer.parseInt(_currField.getAttributeValue("FIELDTYPE"));
			switch(nFieldType){
				case 2 :  //密码文本
					sHtml += "<SPAN class=attr_input_text >";
					sHtml += "<input type=\"password\" ID=\"" + sName + "\"  NAME=\"" + sName +  "\" value=\"" + sValue + "\" pattern=\"string\" max_len=\"" + _currField.getMaxLength() + "\" elname=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\"/>";
					sHtml += "</span>";
					break;
				case 3 :  //普通文本
				    sHtml += "<SPAN class=attr_input_text >";
					sHtml += "<input type=\"text\" ID=\"" + sName + "\" NAME=\"" + sName +  "\" value=\"" + _sValue + "\" pattern=\"string\" max_len=\"" + _currField.getMaxLength() + "\" elname=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\"/>";
					sHtml += "</span>";
					break;
				case 6 : //单选
					if(CMyString.isEmpty(sEnmValue)){
					   break;
					}
					arValues = sEnmValue.split("~");
					sHtml += "<div style=\"display:inline-block;width:143px;border: #b7b7a6 1px solid;\">";
					for(int ix=0,len=arValues.length;ix<len;ix++){
						String[] arValue = pairSplit(arValues[ix]);
						boolean bSelectFlag = false;
						if(!CMyString.isEmpty(sValue) && !CMyString.isEmpty(arValue[1])){
							if((sValue).indexOf(arValue[1])!=-1){
								bSelectFlag = true;					
							}	
						}
						sHtml += "<input type=\"radio\" style=\"border:0px;\" ID=\"" + sName + "_" + ix + "\" NAME=\"" + sName +  "\" value=\"" + arValue[1] + "\" " + (bSelectFlag ?"checked":"")  + "/>&nbsp;<label for=\"" + sName + "_" + ix + "\">" + arValue[0] + "</label>&nbsp;</br>";
					}
					sHtml += "</div>";
					break;
				case 7 : //下拉列表
					sHtml = "<span class=\"arrt_value_select\"><select class=\"select_container\" style=\"width:143px;\" id=\"" +  sName + "\" NAME=\"" + sName + "\">";
					sHtml += "<option value=\"\">" 
						+ LocaleServer.getString("contentextfield.label.pleaseSel", "--请选择--")
						+ "</option>";
					if(!CMyString.isEmpty(sEnmValue)){
						arValues = sEnmValue.split("~");
						for(int ix=0,len=arValues.length;ix<len;ix++){
							String[] arValue = pairSplit(arValues[ix]);
							boolean bSelectFlag = false;
							if(!CMyString.isEmpty(sValue) && sValue.equals(arValue[1])){
								bSelectFlag = true;					
							}
							sHtml += "<option value=\"" + arValue[1] + "\" " + (bSelectFlag ? "selected":"") + ">" + arValue[0] + "</option>";
						}
						sHtml += "</select></span>";
					}
					break;
				case 8 : //多选
					if(CMyString.isEmpty(sEnmValue)){
					   break;
					}

					arValues = sEnmValue.split("~");
					sHtml += "<div style=\"display:inline-block;width:143px;border: #b7b7a6 1px solid;\">";
					for(int ix=0,len=arValues.length;ix<len;ix++){
						String[] arValue = pairSplit(arValues[ix]);
						boolean bSelectFlag = false;
						if(!CMyString.isEmpty(sValue) && !CMyString.isEmpty(arValue[1])){
							if((","+sValue).indexOf(","+arValue[1])!=-1){
								bSelectFlag = true;					
							}
						}
						sHtml += "<input type=\"checkbox\" style=\"border:0px;\" ID=\"" + sName + "_" + ix + "\" NAME=\"" + sName +  "\" value=\"" + arValue[1] + "\" "  + (bSelectFlag ?"checked":"")  + "/>&nbsp;<label for=\"" + sName + "_" + ix + "\">" + arValue[0] + "</label>&nbsp;</br>";
					}
					sHtml += "</div>";
					break;
				case 10: // 简易编辑器
					makeValidation(buffValidation,sDbFieldName,0);
					buffValidation.append("'string',max_len:");
					buffValidation.append(_currField.getMaxLength());
					buffValidation.append("}");
					//sHtml = "<div id=\"DocAbstractHint\" _action=\"openSimpleEditor2\"></div>";
					sHtml = "<TEXTAREA rows=5 ID=\"" +sName + "\" NAME=\"" + sName +  "\"  pattern=\"string\" max_len=\"" + _currField.getMaxLength() + "\" elname=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\"  >"+ _sValue+"</TEXTAREA>";
					break;
				default : //多行文本
					makeValidation(buffValidation,sDbFieldName,0);
					buffValidation.append("'string',max_len:");
					buffValidation.append(_currField.getMaxLength());
					buffValidation.append("}");
					sHtml = "<TEXTAREA rows=3 ID=\"" +sName + "\" NAME=\"" + sName +  "\"  pattern=\"string\" max_len=\"" + _currField.getMaxLength() + "\" elname=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\"  >"+ _sValue+"</TEXTAREA>";
			}
		}else if("int".equals(sPattern)){
			sHtml += "<SPAN class=attr_input_text>";
			sHtml += "<INPUT ID=\"" + sName + "\" NAME=\"" + sName +  "\" value=\""+_sValue+"\" pattern=\"number\" number=\"true\" min=-2147483648 max=2147483647 elname=\"" + CMyString.filterForHTMLValue(_currField.getDesc()) + "\"></span>";
		}else if("float".equals(sPattern)){
			sHtml += "<SPAN class=attr_input_text >";
			sHtml += "<INPUT ID=\"" + sName + "\" NAME=\"" + sName +  "\" value=\""+_sValue+"\" pattern=\"double\" double=\"true\" min=\"-2e125\" max=\"2e125\" scale=\"" + _currField.getAttributeValue("DBSCALE") + "\" elname=\"" + CMyString.filterForHTMLValue( _currField.getDesc()) + "\"></span>";
		}
		return sHtml;
	}

	private String getPropertyHtml(ContentExtField _currField,String _sValue) throws Exception{
		return getPropertyHtml(_currField,_sValue,false);
	}
	private String getPattern(int _nType){
		switch(_nType){
			case Types.BIGINT:
			case Types.INTEGER:
			case Types.SMALLINT:
			case Types.NUMERIC:
				return "int";
			case Types.FLOAT:
			case Types.DOUBLE:			
				return "float";
			case Types.DATE:
			case Types.TIMESTAMP:
				return "time";
				//return " ";
			case Types.CLOB:
				return "";
			default:
				return "string";
		
		}
	}
	private String[] pairSplit(String sPair){
		String[] arValue = {"",""};
		if(sPair.indexOf("`") != -1){
			String[] tempArr = sPair.split("`");
			if(tempArr.length == 2){
				arValue[0] = tempArr[0];
				arValue[1] = tempArr[1];
			}else if(tempArr.length == 1){
				arValue[0] = arValue[1] = tempArr[0];
			}else{
				return arValue;
			}
		}else{
			arValue[0] = sPair;
			arValue[1] = sPair;
		}
		return arValue;
	}
	private void makeValidation(StringBuffer buff,String id,int required){
		buff.append(",{renderTo:'").append(id).append("',");
		buff.append("required:'").append(required).append("',");
		buff.append("no_desc:'',type:");
	}
%>
<script language="javascript">
<!--
	Event.observe(window, 'load', function(){
		if(calenderControls.length > 0){
			for (var i = 0; i < calenderControls.length; i++){
				wcm.TRSCalendar.get({input:calenderControls[i],handler:'btn' + calenderControls[i], dtFmt:'yyyy-mm-dd HH:MM', withtime:true});
			}
		}
	});
//-->
</script>