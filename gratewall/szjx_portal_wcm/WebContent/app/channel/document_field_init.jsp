<%@ page import="java.util.HashMap" %>

<%
	
	//String[] allFields = {"WCMDocument.DOCTITLE","WCMCHNLDOC.CRUSER","WCMCHNLDOC.CRTIME","WCMCHNLDOC.DOCPUBTIME","WCMCHNLDOC.RECID","WCMCHNLDOC.CHNLID","WCMCHNLDOC.DOCID","WCMCHNLDOC.DOCORDER","WCMCHNLDOC.DOCSTATUS","WCMCHNLDOC.DOCORDERPRI","WCMCHNLDOC.INVALIDTIME","WCMCHNLDOC.DOCCHANNEL","WCMCHNLDOC.DOCFLAG","WCMCHNLDOC.DOCKIND","WCMDocument.DOCTYPE","WCMDocument.ATTACHPIC","WCMDocument.DOCPRO","WCMDocument.DOCKEYWORDS","WCMDocument.DOCRELWORDS","WCMDocument.DOCPEOPLE","WCMDocument.DOCPLACE","WCMDocument.DOCAUTHOR","WCMDocument.DOCEDITOR","WCMDocument.DOCAUDITOR","WCMDocument.DOCOUTUPID","WCMDocument.DOCVALID","WCMDocument.DOCPUBURL","WCMDocument.DOCPUBTIME","WCMDocument.DOCRELTIME","WCMDocument.DOCWORDSCOUNT","WCMDocument.HITSCOUNT","WCMDocument.SUBDOCTITLE","WCMDocument.DOCLINK","WCMDocument.DOCFILENAME","WCMDocument.DOCFROMVERSION","WCMDocument.DOCSOURCENAME","WCMDocument.DOCLINKTO","WCMDocument.DOCMIRRORTO","WCMDocument.DOCSOURCE","WCMDocument.DOCVERSION","WCMDocument.DOCSECURITY"};
	//有些是不需要显示的，过滤掉，如需要添加，可以参照上面的添加，上面的较全
	String[] allFieldsBak = {"WCMCHNLDOC.CRUSER","WCMCHNLDOC.CRTIME","WCMCHNLDOC.DOCPUBTIME","WCMCHNLDOC.DOCSTATUS","WCMCHNLDOC.DOCCHANNEL","WCMDocument.DOCKEYWORDS","WCMDocument.DOCRELWORDS","WCMDocument.DOCPEOPLE","WCMDocument.DOCPLACE","WCMDocument.DOCAUTHOR","WCMDocument.DOCEDITOR","WCMDocument.DOCAUDITOR","WCMDocument.DOCPUBURL","WCMDocument.DOCPUBTIME","WCMDocument.DOCRELTIME","WCMDocument.DOCWORDSCOUNT","WCMDocument.HITSCOUNT","WCMDocument.SUBDOCTITLE","WCMDocument.DOCLINK","WCMDocument.DOCFILENAME","WCMDocument.DOCFROMVERSION","WCMDocument.DOCSOURCENAME","WCMDocument.DOCSOURCE","WCMDocument.DOCVERSION"};
	/**
	*
	*/
	String[] mustNeed = {"WCMDocument.DOCTYPE","WCMDocument.DOCID","WCMDocument.DOCCHANNEL","WCMDocument.ATTACHPIC","WCMCHNLDOC.CHNLID","WCMCHNLDOC.DOCORDERPRI","WCMCHNLDOC.MODAL","WCMCHNLDOC.INVALIDTIME"};

	String[] notNeed = {"DOCCONTENT","DOCHTMLCON","DOCABSTRACT","TITLECOLOR","DOCPUBHTMLCON","ATTRIBUTE","EDITOR","RIGHTDEFINED","TEMPLATEID","SCHEDULE","DOCNO","FLOWOPERATIONMARK","FLOWPREOPERATIONMARK","FLOWOPERATIONMASKENUM","RANDOMSERIAL","POSTUSER","NODEID"};
	
%>