<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>违法广告信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>


<script type="text/javascript">
		var entityNo=null;
		var priPid=null;
	 $(function(){
  		entityNo=getUrlParam("flag");
  		priPid=getUrlParam("priPid");
  		/* if(priPid==null){
  			alert("从主页中重新检索");
  		} */
 	}); 
 	
 	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }
     
   //  $('#main')
</script>
</head>
<FRAMESET rows="*" cols="20%,*" bordercolor='#006699' border=1 >
          <FRAME name="left"  src="<%=request.getContextPath()%>/page/general/wf_left_tree.jsp">
          <FRAME name="frame_panel" src="<%=request.getContextPath()%>/page/general/wf_detail_right.jsp">
 <NOFRAMES>
 <body>
 </body>
 </NOFRAMES>
</FRAMESET>

</html>