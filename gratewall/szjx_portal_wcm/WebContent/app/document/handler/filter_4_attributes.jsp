<%
	String sDocPeople = "";
	String sSubDocTitle = "";
	String sDOCKEYWORDS = "";
	String sDocSourceName = "";
	String sDocAuthor = "";
	if(nDocumentId>0){
		sDocPeople = CMyString.filterForHTMLValue(CMyString.showNull(currDocument.getPeople()));
		sSubDocTitle = CMyString.filterForHTMLValue(CMyString.showNull(currDocument.getSubTitle()));
		sDOCKEYWORDS = CMyString.filterForHTMLValue(CMyString.showNull(currDocument.getKeywords()));
		if(currDocument.getSource() != null){
			sDocSourceName = CMyString.filterForHTMLValue(CMyString.showNull(currDocument.getSource().getName()));
		}else{
			sDocSourceName = CMyString.filterForHTMLValue(CMyString.showNull(currDocument.getPropertyAsString("DocSourceName")));
		}
		sDocAuthor = CMyString.filterForHTMLValue(CMyString.showNull(currDocument.getAuthorName()));
	}
%>
<%
Map docPeopleMap = new HashMap();
docPeopleMap.put("DOCPEOPLE", sDocPeople);
docPeopleMap.put("SUBDOCTITLE", sSubDocTitle);
docPeopleMap.put("DOCKEYWORDS", sDOCKEYWORDS);
docPeopleMap.put("DOCSOURCENAME", sDocSourceName);
docPeopleMap.put("DOCAUTHOR", sDocAuthor);
builder.append(docPeopleMap);
%>