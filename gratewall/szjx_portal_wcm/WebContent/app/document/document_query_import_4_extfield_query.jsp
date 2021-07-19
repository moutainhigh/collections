<%
/**
*遍历当前栏目下扩展字段,提取为检索字段，仅当系统配置“EXTFIELD_QUERY”设置为true时生效
*涉及到输入变量sSearchFieldInfo，oMethodContext
*涉及到输出变量sSearchFieldInfo
*/
if(ConfigServer.getServer().getSysConfigValue("EXTFIELD_QUERY", "false").trim().equalsIgnoreCase("true")){
	ExtendedFields currContentExtFields = null;
	ExtendedField newContentExtField = null;
	ContentExtFields contentExtFields = null;
	ContentExtField contentExtField = null;
	String sFieldDescGroup = "";
	try{
		String sChannelId = oMethodContext.getValue("ChannelId");
		String sSiteId = oMethodContext.getValue("SiteId");
		String sWhere = "exists (select ExtFieldId from WCMContentExtField where WCMExtField.ExtFieldId=WCMContentExtField.ExtFieldId and ObjType=? and ObjId=?) and TableName='WCMDOCUMENT'";
		WCMFilter filter = null;
		String sFieldTypeName = "";
		filter = new WCMFilter("WCMEXTFIELD", sWhere, "CRTIME DESC","");
		filter.addSearchValues(0, sChannelId == null ? 103 : 101);
		filter.addSearchValues(1, sChannelId == null ? sSiteId : sChannelId);
		try{
			currContentExtFields = ExtendedFields.openWCMObjs(ContextHelper.getLoginUser(),filter);
		}catch(Exception e){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("document_query_import_4_extfield_query.jsp.getextendfieldsfailure", "获取扩展字段集合失败!"), e);
		}
		if(currContentExtFields.size()> 0){
			for(int j=0;j < currContentExtFields.size();j++){
				newContentExtField = (ExtendedField) currContentExtFields.getAt(j);
				//排除日期型字段
				sFieldTypeName = newContentExtField.getTypeName();
				if(sFieldTypeName.trim().equals("DATETIME")){
					continue;
				}
				sWhere = "ObjType=? and ObjId=? and extfieldId = ?";
				filter = new WCMFilter("", sWhere, "","");
				filter.addSearchValues(0,sChannelId == null ? 103 : 101);
				filter.addSearchValues(1, sChannelId == null ? sSiteId : sChannelId);
				filter.addSearchValues(2, newContentExtField.getId());
				contentExtFields = ContentExtFields.openWCMObjs(ContextHelper.getLoginUser(),filter);
				if(contentExtFields.size() <= 0){
					throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, CMyString.format(LocaleServer.getString("document_query_import_4_extfield_query.jsp.notfindextendfieldid", "未找到Id为{0}的扩展字段"), new int[]{newContentExtField.getId()}));
				}
				contentExtField = (ContentExtField)contentExtFields.getAt(0);
				int nlength = sFieldDescGroup.split(",").length;
				boolean bFound = false;
				if(nlength > 0){
					for(int k = 0;k < nlength;k++){
						if(sFieldDescGroup.split(",")[k].trim().equals(contentExtField.getDesc())){
							bFound = true;
							break;
						}
					}
					if(!bFound){
						sFieldDescGroup += contentExtField.getDesc() + ",";
					}else{
						continue;
					}
				}
				sSearchFieldInfo.append(",{");					sSearchFieldInfo.append("name:'").append(CMyString.filterForJs(newContentExtField.getName())).append("',").append("desc:'").append(CMyString.filterForJs(contentExtField.getDesc())).append("',");
				if(sFieldTypeName.trim().equals("INT")){
					sSearchFieldInfo.append("type:").append("'int'");
				}else if(sFieldTypeName.trim().equals("FLOAT")){
					sSearchFieldInfo.append("type:").append("'float'");
				}else{
					sSearchFieldInfo.append("type:").append("'String'");
				}
				sSearchFieldInfo.append("}");
			}
		}
	}catch(Exception ex){
		ex.printStackTrace();
	}
}
%>