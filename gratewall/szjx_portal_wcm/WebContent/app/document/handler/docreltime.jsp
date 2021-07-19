<%
	String sDocRelTime = CMyDateTime.now().toString("yyyy-MM-dd HH:mm");
	if(nDocumentId>0){
		sDocRelTime = CMyString.showNull(currDocument.getReleaseTime().toString("yyyy-MM-dd HH:mm"));
	}
%>
<%
Map docRelTimeMap = new HashMap();
docRelTimeMap.put("DOCRELTIME-FORMAT", sDocRelTime);
builder.append(docRelTimeMap);
%>