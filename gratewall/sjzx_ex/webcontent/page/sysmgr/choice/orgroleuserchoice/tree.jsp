<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:html>
<freeze:include href="/script/gwssi-xtree.js"/>
<freeze:include href="/script/struts-coolmenus3.js"/>
  <head>
    <title>人员选择</title>
  </head>

<%
String type= request.getParameter("type");
String idname= request.getParameter("idname");
String textname= request.getParameter("textname");
String jsclrid= request.getParameter("jsclrid");

%>
  <body bgcolor="#ffffff">

    <freeze:errors/>


  	<script language="javascript">
  function setTreeSelectedNode(){    
    var type = "<%=type%>"; 
    var ids = "<%=jsclrid%>";
    if(type == "check" && ids!=null && ids.length>0 ){
      var ary = ids.split(",");
      for( i = 0; i < ary.length; i++) {
        cmdMenu.menu.fireEvent(ary[i], "onclick");
      }
    }
  }

     function getCheck( ){
            	
      	var str = cmdMenu.getCheckValue();
			  if(str!=null){
        str = str.replaceAll(";", ",");
				}     	
      	var ary = str.split(",");
      	var idlist, textlist;
      	idlist = "";
      	textlist = "";
      	var selnode;
      	if(str.length>1){
	      	for( i = 0; i < ary.length; i++) {
	      		selnode = cmdMenu.lookupNode(ary[i]);
	      		var type = selnode.memo;
	      		if(type=="USER"){
	      		idlist = idlist + selnode.id + ",";
	      		textlist = textlist + selnode.text + ",";
	      	  }
	      	}
	      	//去除最后一位","号
      		idlist = idlist.substring(0,idlist.length-1);
      		textlist = textlist.substring(0,textlist.length-1);
      		var transfer = "idlist=" + idlist + "&textlist=" + textlist;
      		   
      		window.opener.addvalue(idlist,textlist,"<%=idname%>","<%=textname%>");
        	window.close();
      	}
      }

      function funcClick( ){
      	
      	var selectId = cmdMenu.selectedNode.id;

      	if( selectId == null || selectId == '' ){
      		return false;
      	}
      	var node = cmdMenu.lookupNode(selectId);
      	var type = node.memo;
      	if(type == "USER"){
        var textname = node.text;
      	var transfer = "id=" + selectId + "&text=" + textname;
      	   
      	window.opener.addvalue(selectId,textname,"<%=idname%>","<%=textname%>");
        window.close();        
        }
      }

    </script>
        	<table border="0" cellpadding="5" cellspacing="0" width="100%" height="100%">

            <tr>
            	<td valign="top">

<%
if(type.equals("tree")){
%>
                <freeze:xtree name='cmdMenu' width="100%" height="100%" bgcolor="FFFFFF"
                  nodeicon="scrollend.gif" nodename="select-key" idname="jgid_pk" pidname="sjjgid_fk" textname="{jgmc}"
                  memoname="{type}" aspect="tree" root="机构角色用户选择" onclick="funcClick"/>
<%
}else{
%>
                <freeze:xtree name='cmdMenu' width="100%" height="100%" bgcolor="FFFFFF"
                  nodeicon="scrollend.gif" nodename="select-key" idname="jgid_pk" pidname="sjjgid_fk" textname="{jgmc}"
                  memoname="{type}" aspect="check" root="机构角色用户选择" />

<%
}
%>              </td>
            </tr>

            
            <tr align="center">
            	<td height=30>
<%
if(type.equals("check")){
%>            		
                <input name="ok" type="button" class="menu" onClick="getCheck();" value="确 定">
<%
}
%>  
&nbsp;&nbsp;&nbsp;<input name="ok" type="button" class="menu" onClick="window.close()" value="关 闭">

              </td>
            </tr>

          </table>

  </body>
  <script language="javascript">
    setTreeSelectedNode();
  </script>
</freeze:html>
