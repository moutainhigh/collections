<%!
private final static int OUTLINE_COLS_COUNT = 5;
private final static String makeTitleFieldColClassName(MetaViewFields metaViewFields) throws WCMException {
	int nOutlinFieldCount = 0;
	for(int i = 0,size = metaViewFields.size(); i < size; i++){
		MetaViewField mvf = (MetaViewField) metaViewFields.getAt(i);
		if(mvf != null && mvf.isInOutline()) nOutlinFieldCount++;
	}
	if(nOutlinFieldCount > OUTLINE_COLS_COUNT){
		return "";
	}
	
	return "class='titleFieldCol'";
}
%>