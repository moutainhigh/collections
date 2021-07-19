<%@ page contentType="text/html; charset=GBK"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<link href='/module/layout/layout-sj/css/gwssi.css' rel='stylesheet' type='text/css'>
<freeze:html>
<freeze:include href="/script/gwssi-xtree.js" />
<head>
	<title>部门选择</title>
</head>

<%
	//type：tree(单选) /check(多选)
	String type = request.getParameter("type");
	String idname = request.getParameter("idname");
	String textname = request.getParameter("textname");
	String jsclrid = request.getParameter("jsclrid");
	DataBus contextDB = (DataBus)request.getAttribute("freeze-databus");
%>
<body bgcolor="#ffffff">
 <freeze:errors />
	<script language="javascript">
	//处理多选时，第二次选择时第一次选择的记录是被选中的
	  function setTreeSelectedNode(){    
	    var type = "<%=type%>"; 
	    var ids = "<%=jsclrid%>";
	    if(type == "check" && ids!=null && ids.length>0 && ids!="undefined")
	    {
	      var ary = ids.split(",");
	      for( i = 0; i < ary.length; i++) {
	        cmdMenu.menu.fireEvent(ary[i], "onclick");
	      }
	    }
	  }
  	//多选时，单击树中的节点处理事件
     function getCheck(){
        var idlist, textlist;
      	idlist = "";
      	textlist = ""; 	
      	var str = cmdMenu.getCheckValue();
		if(str!=null && str !=""){
			  	
	        str = str.replaceAll(";", ",");
					     	
	      	var ary = str.split(",");
	      	
	      	var selnode;
      		if(str.length>0){
	      		for( i = 0; i < ary.length; i++) {
	      			selnode = cmdMenu.lookupNode(ary[i]);
	      			idlist = idlist + selnode.id + ",";
	      			textlist = textlist + selnode.text + ",";
	      		}
	      		//去除最后一位","号
	      		idlist = idlist.substring(0,idlist.length-1);
	      		textlist = textlist.substring(0,textlist.length-1);
	      		var transfer = "idlist=" + idlist + "&textlist=" + textlist;
      		 
      		}
      	}
     	window.opener.addvalue(idlist,textlist,"<%=idname%>","<%=textname%>");
       	window.close();

      }
	//单选时，单击树中的节点处理事件
      function funcClick(){
      	
      	var selectId = cmdMenu.selectedNode.id;

      	if( selectId == null || selectId == '' ){
      		return false;
      	}
      	var node = cmdMenu.lookupNode(selectId);
        var textname = node.text;
        
        if(node.text=='机关处室' || node.text=='分局科室' || node.text=='分管工商所' ||node.text=='分局工商所' || node.text=='直属事业单位'){
          return false;
        }
      	var transfer = "id=" + selectId + "&text=" + textname;
      	var parentNode=cmdMenu.lookupNode(node.parentid);
      	if(parentNode){
      	  if(parentNode.text=='机关处室' ||parentNode.text=='分局科室' || parentNode.text=='分局工商所' || parentNode.text=='分管工商所' || parentNode.text=='直属事业单位'){
      	    var ppNode=cmdMenu.lookupNode(parentNode.parentid);
      	    if(ppNode){
      	      textname=ppNode.text+"-"+textname;
      	    }
      	  }else{
      	      textname=parentNode.text+"-"+textname;
      	  }
      	}
        
      	var transfer = "id=" + selectId + "&text=" + textname;
      	window.opener.addvalue(selectId,textname,"<%=idname%>","<%=textname%>");
        window.close();        
                     
      }
    //取消按钮
      function cancelButton(){
      	window.opener.removevalue("<%=idname%>","<%=textname%>");
      	window.close();
      }  
    </script>
	<table width="95%"  border="0" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<td bgcolor="#FFFFFF">
				<%
				if (type.equals("tree")) {
				%>
			
         <script language='javascript'>
				var cmdMenu = new TreeDefine( 'cmdMenu', '机构选择', '', 'tree', true, '', 'select-key', 
				'sjjgid_fk', 'jgid_pk', '{jgmc}', '{type}', '', 'icon', 'openicon', 'FFFFFF', '', '', '', 
				'scrollend.gif', 'funcClick', '', '', '', '', '', '', '', '', [
				<%
		          if(!contextDB.getRecordset("select-key").isEmpty()){
		          	for(int i=0;i<contextDB.getRecordset("select-key").size();i++){
		          		DataBus db=contextDB.getRecordset("select-key").get(i);
		          		String jgjc=db.getValue("jgjc");
		          		String jgid_pk=db.getValue("jgid_pk");
		          		String sjjgname=db.getValue("sjjgname");
		          		if(StringUtils.isBlank(sjjgname)){
		          		  if(jgjc.equals("区县分局")){
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"0','分局科室','','','分局科室',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"1','分局工商所','','','分局工商所',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"2','直属事业单位','','','直属事业单位',false),");
		          		  }
		          		  if(db.getValue("jgmc").equals("市局机关")){
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"1','机关处室','','','机关处室',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"2','直属事业单位','','','直属事业单位',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"0','专业分局','','','专业分局',false),");
		          		  }
		          		}
		          	}
		              for(int i=0;i<contextDB.getRecordset("select-key").size();i++){
		                DataBus db=contextDB.getRecordset("select-key").get(i);
		                String sjjgid_fk=db.getValue("sjjgid_fk");
		                String jgid_pk=db.getValue("jgid_pk");
		                String jgmc=db.getValue("jgmc");
		                if(db.getValue("jgjc").equals("专业分局")){
		              	  sjjgid_fk=sjjgid_fk+"0";
		                }
		                if(db.getValue("jgjc").equals("分局科室")){
		              	  sjjgid_fk=sjjgid_fk+"0";
		                }
		                if(db.getValue("jgjc").equals("分局工商所")){
		              	  sjjgid_fk=sjjgid_fk+"1";
		                }
		                if(db.getValue("jgjc").equals("直属事业单位")){
		              	  sjjgid_fk=sjjgid_fk+"2";
		                }
		                if(db.getValue("jgjc").equals("机关处室")){
		              	  sjjgid_fk=sjjgid_fk+"1";
		                }
		                String content="new XmenuNode('"+sjjgid_fk+ "','"+jgid_pk+"','"+jgmc+"','','','"+jgmc+"',false)";
		                out.println(i==contextDB.getRecordset("select-key").size()-1 ? content : content+",");
		              }
		            }
		          %> ]);
		          cmdMenu.create('100%', '100%');
		          </script>
				<% 
				} else {
				%>
				<script language='javascript'>
				var cmdMenu = new TreeDefine( 'cmdMenu', '机构选择', '', 'check', true, '', 'select-key', 'sjjgid_fk', 'jgid_pk', 
				'{jgmc}', '{type}', '', 'icon', 'openicon', 'FFFFFF', '', '', '', 'scrollend.gif', '', '', '',
				 '', '', '', '', '', '', [
				<%
		          if(!contextDB.getRecordset("select-key").isEmpty()){
		          	System.out.println(contextDB.getRecordset("select-key").size());
		          	for(int i=0;i<contextDB.getRecordset("select-key").size();i++){
		          		DataBus db=contextDB.getRecordset("select-key").get(i);
		          		String jgjc=db.getValue("jgjc");
		          		String jgid_pk=db.getValue("jgid_pk");
		          		String sjjgname=db.getValue("sjjgname");
		          		if(StringUtils.isBlank(sjjgname)){
		          		  if(jgjc.equals("区县分局")){
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"0','分局科室','','','分局科室',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"1','分局工商所','','','分局工商所',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"2','直属事业单位','','','直属事业单位',false),");
		          		  }
		          		  if(db.getValue("jgmc").equals("市局机关")){
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"1','机关处室','','','机关处室',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"2','直属事业单位','','','直属事业单位',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"0','专业分局','','','专业分局',false),");
		          		  }
		          		}
		          	}
		              for(int i=0;i<contextDB.getRecordset("select-key").size();i++){
		                DataBus db=contextDB.getRecordset("select-key").get(i);
		                String sjjgid_fk=db.getValue("sjjgid_fk");
		                String jgid_pk=db.getValue("jgid_pk");
		                String jgmc=db.getValue("jgmc");
		                if(db.getValue("jgjc").equals("专业分局")){
		              	  sjjgid_fk=sjjgid_fk+"0";
		                }
		                if(db.getValue("jgjc").equals("分局科室")){
		              	  sjjgid_fk=sjjgid_fk+"0";
		                }
		                if(db.getValue("jgjc").equals("分局工商所")){
		              	  sjjgid_fk=sjjgid_fk+"1";
		                }
		                if(db.getValue("jgjc").equals("直属事业单位")){
		              	  sjjgid_fk=sjjgid_fk+"2";
		                }
		                if(db.getValue("jgjc").equals("机关处室")){
		              	  sjjgid_fk=sjjgid_fk+"1";
		                }
		                String content="new XmenuNode('"+sjjgid_fk+ "','"+jgid_pk+"','"+jgmc+"','','','"+jgmc+"',false)";
		                out.println(i==contextDB.getRecordset("select-key").size()-1 ? content : content+",");
		              }
		            }
		          %> ]);
		          cmdMenu.create('100%', '100%');
		          </script>
			
				<%
				}
				%>
			</td>
		</tr>

		<tr align="center">
			<td height=30 bgcolor="#FFFFFF">

				<%
					if (type.equals("check")) {
				%>
				<table cellspacing="0" cellpadding="0" class="button_table">
				<tr>
					<td class="btn_left"></td>
					<td>
				<input name="ok" type="button" class="menu" onClick="getCheck();"
					value="确定">&nbsp;&nbsp;
					</td>
					<td class="btn_right"></td>
				</tr>
				</table>
				<%
					}
				%>
				<table cellspacing="0" cellpadding="0" class="button_table">
				<tr>
					<td class="btn_left"></td>
					<td>
				<input name="ok" type="button" class="menu" onClick="cancelButton();" value="取消">
				</td>
					<td class="btn_right"></td>
				</tr>
				</table>
			</td>
		</tr>
	</table>
	
</body>
<script language="javascript">
    setTreeSelectedNode();
</script>
</freeze:html>
