<%!
	private String formatShow(String _sParam) {
		return _sParam.length() > 19 ? _sParam.substring(0,16) + ".." :_sParam;
	}
%>
<%!
	private String displayShow(String _sName) {
		return _sName.length() > 15 ? _sName.substring(0,13) + ".." : _sName;
	}
%>
<%!
	private String getXMLFieldsName(){
		List list = XMLConfigServer.getInstance().getConfigObjects(DocumentShowFieldConfig.class);
		StringBuffer sbFieldName = new StringBuffer();
		DocumentShowFieldConfig currDocumentShowFieldConfig = null;
		for(java.util.Iterator it=list.iterator(); it.hasNext(); ) {
			currDocumentShowFieldConfig = (DocumentShowFieldConfig)it.next();
			if(!currDocumentShowFieldConfig.isWrite())continue;
			if(sbFieldName.length() == 0){
				sbFieldName.append(currDocumentShowFieldConfig.getFieldname());
			}else{
				sbFieldName.append(",").append(currDocumentShowFieldConfig.getFieldname());
			}
		}
		String sAllFieldsName = sbFieldName.toString();
		return sAllFieldsName;
	}
%>