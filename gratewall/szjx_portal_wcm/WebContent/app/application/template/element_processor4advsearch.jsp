<%!
	/**-----------------------style config start------------------------------**/
	String checkboxContainerDefaultStyle	= "input_checkbox_container";
	String radioContainerDefaultStyle		= "input_radio_container";
	String selectContainerDefaultStyle		= "select_container";
	String appendixContainerDefaultStyle	= "appendix_container";
	String classinfoContainerDefaultStyle	= "classinfo_container";
	String classinfoSelectDefaultStyle		= "selectclassinfo";
	String editorContainerDefaultStyle		= "classinfo_container";
	String openEditorDefaultStyle			= "openeditor";
	
	String appendixBrowserDefaultStyle		= "appendix_browser";
	String appendixDeleteDefaultStyle		= "appendix_delete";
	/**-----------------------style config end--------------------------------**/

	/**-----------------------classInfo id config start------------------------------**/
	String classInfoTextIdSuffix		= "_Text";
	String classInfoSelectIdSuffix		= "_SelectClassInfo";
	/**-----------------------classInfo id config end--------------------------------**/

	/**-----------------------editor id config start------------------------------**/
	String openEditorIdSuffix		= "_openEditor";
	/**-----------------------editor id config end--------------------------------**/

	/**-----------------------appendix id config start------------------------------**/
	String appendixIframeIdSuffix		= "_iframe";
	String appendixBrowserBtnIdSuffix	= "_browser_btn";
	String appendixDeleteBtnIdSuffix	= "_delete_btn";
	String appendixTextIdSuffix		= "_text";	
	/**-----------------------appendix id config end--------------------------------**/

	/**-----------------------other fields config start------------------------------**/
	String otherFieldsContainerId		= "otherFieldsContainer";
	/**-----------------------other fields config end--------------------------------**/

	String itemSplit = "~";	//每一项分割符
	String textValueSplit = "`";
	int index = 0;

		
//common mothod
	private String[][] getItems(String sEnmValue){
		String [] aItems = sEnmValue.split(itemSplit);
		String [][] aResultItems = new String[aItems.length][2];

		for (int i = 0; i < aItems.length; i++){
			String[] aItem = aItems[i].split(textValueSplit);
			if(aItem.length > 1){
				if(aItem[1] == null) 
					aItem[1] = "";
				aResultItems[i][1] = aItem[0];
				aResultItems[i][0] = aItem[1];
			}else{
				aResultItems[i][1] = aItem[0];
				aResultItems[i][0] = aItem[0];
			}
		}
		return aResultItems;
	}

	private String getInputSelectHTML(MetaViewField oMetaViewField, boolean isInputable) throws WCMException{
		String enumValue = oMetaViewField.getEnmValue();

		if(enumValue == null) return "";
		enumValue = CMyString.filterForHTMLValue(enumValue);
		StringBuffer sHTML = new StringBuffer();
		StringTokenizer st = new StringTokenizer(enumValue, "~");
		if(isInputable){
			sHTML.append("<option value=''>--please select--</option>");
		}

		while(st.hasMoreTokens()){
			String item = st.nextToken();
			String[] aItem = item.split("`");

			if(aItem.length < 1) continue;
			String sLabel = aItem[0];
			String sValue = aItem.length > 1 ? aItem[1] : sLabel;
			sHTML.append("<option value='" + sLabel + "' _value='" + sValue + "'>");
			sHTML.append(sLabel);
			sHTML.append("</option>");
		}
		return sHTML.toString();
	}

	
//高级检索页面
	 private String dealWith_checkbox(MetaViewField oMetaViewField, MetaViewData obj){
		//前提
		int sFieldType = oMetaViewField.getType();
		String sViewFieldName = oMetaViewField.getPropertyAsString("FIELDNAME","");
		String sFieldName = getFieldName(oMetaViewField);
		String sAnotherName = oMetaViewField.getPropertyAsString("ANOTHERNAME","");
		int nTitleField = oMetaViewField.isTitleField() == true ? 1 : 0;
		String sFieldValue = "";
		try{
			if(obj !=null){
				sFieldValue = obj.getRealProperty(sViewFieldName);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String sEnmValue = oMetaViewField.getPropertyAsString("ENMVALUE","");
		String[][] aItems = getItems(sEnmValue);


		StringBuffer  sHTML = new StringBuffer();
//函数公用模板 
		//原来未控制输出部分
//		sHTML.append("<span class='label' style='float:left;'> ").append(sAnotherName).append("：</span>");
//		sHTML.append("<span class='value' style='margin-left:80px;display:block;'>");
		//原来控制输出部分
		for (int i = 0; i < aItems.length; i++){
			String sChecked = ("," + sFieldValue + ",").indexOf("," + aItems[i][0] + ",") >= 0 ? " checked" : "";
			sHTML.append("<span")
				 .append(" class='").append(checkboxContainerDefaultStyle).append("'>")
				 .append("<input type=checkbox name='").append(sFieldName).append("'")
				 .append(" id='").append(sFieldName).append("_").append(i).append("'")
				 .append(sChecked)
				 .append(" value='" + aItems[i][0] + "'")
				 .append(" />")
				 .append("<label for='").append(sFieldName).append("_").append(i).append("'>")
				 .append(aItems[i][1]).append("</label></span>");
		}
		//未控制输出部分
//		sHTML.append("<div style='color:red;display:block;'>");
//		sHTML.append("<input type='checkbox' ignore='true' name='mode_query' id='mode_").append(sFieldName)
//			 .append("' tyle='width:auto;border:0px;'>");
//		sHTML.append("<label for='mode_").append(sFieldName).append("'>组合成And条件</label>");
//		sHTML.append("</div>");
//		sHTML.append("</span>");
		//
		return sHTML.toString();
	}

	private String dealWith_radio(MetaViewField oMetaViewField, MetaViewData obj){
		//前提
		int sFieldType = oMetaViewField.getType();
		String sViewFieldName = oMetaViewField.getPropertyAsString("FIELDNAME","");
		String sFieldName = getFieldName(oMetaViewField);
		String sAnotherName = oMetaViewField.getPropertyAsString("ANOTHERNAME","");
		int nTitleField = oMetaViewField.isTitleField() == true ? 1 : 0;
		String sFieldValue = "";
		try{
			if(obj != null){
				sFieldValue = obj.getRealProperty(sViewFieldName);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String sEnmValue = oMetaViewField.getPropertyAsString("ENMVALUE","");
		String[][] aItems = getItems(sEnmValue);
		StringBuffer  sHTML = new StringBuffer();
		
		//原来未控制输出部分
//		sHTML.append("<span class='label' style='float:left;'> ").append(sAnotherName).append("：</span>");
//		sHTML.append("<span class='value' style='margin-left:80px;display:block;'>");
		//原来控制输出部分
		for (int i = 0; i < aItems.length; i++){
			String sChecked = ("," + sFieldValue + ",").indexOf("," + aItems[i][0] + ",") >= 0 ? " checked" : "";
			sHTML.append("<span")
				 .append(" class='").append(radioContainerDefaultStyle).append("'>")
				 .append("<input type=radio name='").append(sFieldName).append("'")
				 .append(" id='").append(sFieldName).append("_").append(i).append("'")
				 .append(sChecked)
				 .append(" value='" + aItems[i][0] + "'")
				 .append(" />")
				 .append("<label for='").append(sFieldName).append("_").append(i).append("'>")
				 .append(aItems[i][1]).append("</label></span>");
		}
		sHTML.append("</span>");

		return sHTML.toString();
	}

	private String dealWith_select(MetaViewField oMetaViewField, MetaViewData obj){
		//前提
		int sFieldType = oMetaViewField.getType();
		String sViewFieldName = oMetaViewField.getPropertyAsString("FIELDNAME","");
		String sFieldName = getFieldName(oMetaViewField);
		String sAnotherName = oMetaViewField.getPropertyAsString("ANOTHERNAME","");
		int nTitleField = oMetaViewField.isTitleField() == true ? 1 : 0;
		String sFieldValue = "";
		try{
			//根据传过来的参数obj是否为null来分开新建（高级检索）和编辑两种情况
			if(obj !=null){
				sFieldValue = obj.getRealProperty(sViewFieldName);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String sEnmValue = oMetaViewField.getPropertyAsString("ENMVALUE","");
		String[][] aItems = getItems(sEnmValue);
		StringBuffer  sHTML = new StringBuffer();
//原来未控制输出部分
//		sHTML.append("<span class='label' style='float:left;'> ").append(sAnotherName).append("：</span>");
//		sHTML.append("<span class='value'>");
//原来控制输出部分
		sHTML.append("<select class='").append(selectContainerDefaultStyle).append(" name='").append(sFieldName)
			 .append("' id='").append(sFieldName).append("'>");
		//sHTML.append("<option value=''>--请选择--</option>");
		for (int i = 0; i < aItems.length; i++){
			String sSelected = (aItems[i][0] == sFieldValue ? " selected" : "");
			sHTML.append("<option value='").append(aItems[i][0]).append("' ")
				 .append(sSelected).append(">").append(aItems[i][1]).append("</option>");
		}
		sHTML.append("</select>");
//未控制输出部分
//		sHTML.append("<input type='text' name='").append(sFieldName)
//			 .append("' id='").append(sFieldName).append("' value='")
//			 .append(sFieldName).append("' class='inputSelect_input'")
//			 .append("<span class='inputSelect_span'><select id='").append(sFieldName).append("_sel'")
//			 .append(" class='inputSelect_select'>").append(getInputSelectHTML(sFieldName)).append("</select></span>")
//			 .append("</span>")
//			 .append("<a name='selectMultiValue' _attachElement='").append(sFieldName)
//			 .append(" style='cursor:default;' class='selectMultiValueA'></a>");

		return sHTML.toString();
	}

	private String delaWith_class(MetaViewField oMetaViewField, MetaViewData obj){
		//前提
		int sFieldType = oMetaViewField.getType();
		String sViewFieldName = oMetaViewField.getPropertyAsString("FIELDNAME","");
		String sFieldName = getFieldName(oMetaViewField);
		String sAnotherName = oMetaViewField.getPropertyAsString("ANOTHERNAME","");
		int nTitleField = oMetaViewField.isTitleField() == true ? 1 : 0;
		String aClassNames = "";
		String aClassIds = "";
		try{
			if(obj !=null){
				//","分割的classname数组
				aClassNames = obj.getRealProperty(sViewFieldName);
				//","分割的classid数组
				aClassIds = obj.getPropertyAsString(sViewFieldName);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String sEnmValue = oMetaViewField.getPropertyAsString("ENMVALUE","");
		String sClassName = oMetaViewField.getClassName();
		int nClassId = oMetaViewField.getClassId();
		String[][] aItems = getItems(sEnmValue);
		StringBuffer  sHTML = new StringBuffer();

		//split是否是为适应多分类法的情况？
		String sCurrentValue = "";
		if(!aClassNames.equals("")){
			sCurrentValue = sClassName + "[" + nClassId + "]";
		}
		
		sHTML.append("<span").append(" class='").append(classinfoContainerDefaultStyle).append("'>")
			 .append("<input _type='classInfo' type='hidden' name='").append(sFieldName).append("' id='").append(sFieldName)
		     .append("' value='").append(aClassIds).append("'/>")
			 .append("<div id='")
			 .append(sFieldName).append(classInfoSelectIdSuffix).append("'")
			 .append(" class='").append(classinfoSelectDefaultStyle).append("'")
			//添加事件属性
			 .append(" _classId='").append(nClassId).append("'")
			 .append(" _name='").append(sFieldName).append("'")
			 .append(" _type='classInfo'")
			 .append(">")
			//有事件则再次处添加控制，并表明类型
			 .append("<a name='controller'></a>")
			 .append("</div>")
		     .append("<span id='").append(sFieldName).append(classInfoTextIdSuffix).append("'>")
			 .append(sCurrentValue)
			 .append("</span>")
		     .append("</span>");
		return sHTML.toString();
	}



	//只处理需要处理的内容，不追求统一。
	private String dealWith_appendix_onlyNode(MetaViewField oMetaViewField, MetaViewData obj){
		int index = 0;
		int sFieldType = oMetaViewField.getType();
		String sViewFieldName = oMetaViewField.getPropertyAsString("FIELDNAME","");
		String sFieldName = getFieldName(oMetaViewField);
		int nTitleField = oMetaViewField.isTitleField() == true ? 1 : 0;
		int nModal = obj.getChnlDocProperty("Modal",CMSConstants.CONTENT_MODAL_ENTITY);
		String sFieldValue = "";
		try{
			sFieldValue = CMyString.showNull(obj.getRealProperty(sViewFieldName));
		}catch(Exception e){
			e.printStackTrace();
		}
		//控制样式和属性
		StringBuffer  sHTML = new StringBuffer();
		sHTML.append( "<span class='" );
		if(!sFieldValue.equals("")){
			sHTML.append("appendix_type_none");
		}
		sHTML.append("'");
		//sHTML.append( " width=80px align='left'");
		sHTML.append( " name='" + sFieldName + "'" );
		sHTML.append(" id='" + sFieldName + "'" );
		sHTML.append( " value='" + sFieldValue + "'" );
		sHTML.append( "_type='appendix' ");
		sHTML.append( ">");
		//
		String sId = sFieldName + "_" + index;
		sHTML.append( " <a title='" + sFieldValue + "' unselectable='on' id='" + sFieldName + "_" + index 
		+ "' style='BACKGROUND-IMAGE:url(../images/metadata/file_type/type_"
		+ sFieldValue.substring(sFieldValue.lastIndexOf(".")+1) + ".gif)"
		+"' href='' onclick='return false;' class='appendix_type'>&nbsp;</a>");
		sHTML.append( "</span>");
		return sHTML.toString();
	}

	private String dealWith_onlyNode(MetaViewField oMetaViewField, MetaViewData obj){
		//前提
		int nFieldType = oMetaViewField.getType();
		String sViewFieldName = oMetaViewField.getPropertyAsString("FIELDNAME","");
		String sFieldName = getFieldName(oMetaViewField);
		String sAnotherName = oMetaViewField.getPropertyAsString("ANOTHERNAME","");
		int nTitleField = oMetaViewField.isTitleField() == true ? 1 : 0;
		String sEnmValue = oMetaViewField.getPropertyAsString("ENMVALUE","");
		String sFieldValue = "";
		boolean bTitleField = oMetaViewField.isTitleField();
		StringBuffer sHTML = new StringBuffer();

		try{
			if(obj !=null){
				sFieldValue = obj.getPropertyAsString(sViewFieldName,"");
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		String sClassStyle = "";
		switch(nFieldType){
			case 5:
			case 6:
				sClassStyle = " class='radioContainerDefaultStyle'";
			case 7:
			case 15:
			case 17:
				sClassStyle = " class='selectContainerDefaultStyle'";
			case 9:
				sClassStyle = " class='checkboxContainerDefaultStyle'";
			default:
		}

		String[][] aItems = getItems(sEnmValue);
		
		sHTML.append("<span").append(sClassStyle).append(" id=").append(sFieldName).append(">");
		boolean flag = false;
		for (int i = 0; i < aItems.length; i++){
			boolean isChecked = ("," + sFieldValue + ",").indexOf("," + aItems[i][0] + ",") >= 0;
			if(!isChecked) continue;
			sHTML.append(aItems[i][1]).append(",");
			flag = true;
		}
		if(flag){
			sHTML.deleteCharAt(sHTML.length() -1);
		}
		sHTML.append("</span>");
		return sHTML.toString();
	}

	private String tranShow(int nModal ,int nTitleField, String columnContent, boolean bCanView, boolean bIsTop, boolean bIsPic, int nDocId){
		if(CMyString.isEmpty(columnContent)){
			return "&nbsp;";
		}
		if(nTitleField ==1 ){
			String topSpan = bIsTop ? "<span class='document_topped'></span>" : "";
			String titleSpan = "<span class='record_modal_" + nModal + "'></span>";
			String imageSpan = "<span class='" + (bIsPic ? "document_attachpic" : "") + "'></span>";
			String recid = LocaleServer.getString("metaviewdata.label.recId","记录ID");

			return topSpan + titleSpan + "<a contextmenu='1' unselectable='on' href='#' title='" + recid + ":[" + nDocId + "]" + "'  onclick='return false' class='titleField" + (bCanView ? "" :" no_right") +"' grid_function='" + (bCanView ? "view" :"") + "'>" + columnContent + "</a>" + imageSpan;
		}
		return columnContent;
	}

	private String dealWith_class_onlyNode(String aClassName,String aClassId){
		if(aClassName == null ||aClassName.equals("")){
			return "";
		}
		StringBuffer sClassValue = new StringBuffer();
		String[] sClassNames = aClassName.split(",");
		String[] sClassIds = aClassId.split(",");
		for(int i = 0; i < sClassNames.length ; i++){
			sClassValue.append(sClassNames[i]).append("[").append(sClassIds[i]).append("]");
			if(i != sClassNames.length -1){
				sClassValue.append(",");
			}
		}
		return sClassValue.toString();
		
	}
%>