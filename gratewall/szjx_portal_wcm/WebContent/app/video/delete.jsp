<!--- congli @ 2009-7-23 15:40 删除转码失败的视频的操作页面 --->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="com.trs.infra.persistent.db.DBManager"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.components.video.domain.XVideoMgr"%>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.components.video.persistent.XVideo"%>
<%@ page import="com.trs.components.wcm.content.persistent.Documents"%>
<%@include file="../include/public_server.jsp"%>

<%!
XVideoMgr xVideoMgr = new XVideoMgr();
public class FailVideoMgr {
	final String DB_TABLE_NAME = "WCMDOCUMENT";
	final String DB_TABLE_NAME_CHNL = "WCMCHNLDOC";
	final String DB_TABLE_NAME_XVIDEO = XVideo.DB_TABLE_NAME;
	public List getDocIdsSP1() throws Exception {
		List docIds = new ArrayList();

		// 构造SQL语句
        WCMFilter filter = new WCMFilter(DB_TABLE_NAME_XVIDEO,"CONVERT_STATUS = -1","");
		Documents docs = Documents.openWCMObjs(null,filter);
        if(docs==null)
           return docIds;
		for(int i=0;i<docs.size();i++){
			if(docs.getAt(i)!=null){
              docIds.add(new Integer(docs.getAt(i).getId()));
			}
        }

	return docIds;
	}
	//删除MAS中转码失败的视频
	public void deleteFailVideosInMAS(){
		List failVideos =new ArrayList();
		String sql;
		try{
			failVideos = getDocIdsSP1();
     for(int i=0;i<failVideos.size();i++){
    	 int ids = new Integer("" + failVideos.get(i)).intValue();
    	 
    	 List videos = XVideo.findXVideosByDocId(ids);
    	 for(int j=0;j<videos.size();j++){
    	 XVideo xvideos = (XVideo)videos.get(j);
    		 
    	 xVideoMgr.sendDeleteReuqest(xvideos.getSrcFileName());
    	 
    	 }
    	 
		}
		}
		catch(Exception e){
		}
    	

	
	      
	}
	
	
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title WCMAnt:param="delete.jsp.clearVideo">清除转码失败的视频</title>
<script>
function Exit(){
	window.close();
}
</script>
</head>
<body>

<%
FailVideoMgr failVideoMgr = new  FailVideoMgr();
failVideoMgr.deleteFailVideosInMAS();

%>
<br><%=CMyString.format(LocaleServer.getString("delete.jsp.video","共有[{0}]个转码失败的视频"),new int[]{failVideoMgr.getDocIdsSP1().size()})%><br>
<br><%=CMyString.format(LocaleServer.getString("delete.jsp.clear","成功清除[{0}]个转码失败的视频"),new int[]{xVideoMgr.deleteFailVideo(failVideoMgr.getDocIdsSP1())})%><br>

<br><input type="button" name="save" value="退出" WCMAnt:paramattr="value:delete.jsp.quit" onclick="javascript:Exit();"><br>
</body>
</html>