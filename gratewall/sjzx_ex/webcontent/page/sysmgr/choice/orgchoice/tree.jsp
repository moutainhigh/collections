<%@ page contentType="text/html; charset=GBK"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<link href='/module/layout/layout-sj/css/gwssi.css' rel='stylesheet' type='text/css'>
<freeze:html>
<freeze:include href="/script/gwssi-xtree.js" />
<head>
	<title>����ѡ��</title>
</head>

<%
	//type��tree(��ѡ) /check(��ѡ)
	String type = request.getParameter("type");
	String idname = request.getParameter("idname");
	String textname = request.getParameter("textname");
	String jsclrid = request.getParameter("jsclrid");
	DataBus contextDB = (DataBus)request.getAttribute("freeze-databus");
%>
<body bgcolor="#ffffff">
 <freeze:errors />
	<script language="javascript">
	//�����ѡʱ���ڶ���ѡ��ʱ��һ��ѡ��ļ�¼�Ǳ�ѡ�е�
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
  	//��ѡʱ���������еĽڵ㴦���¼�
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
	      		//ȥ�����һλ","��
	      		idlist = idlist.substring(0,idlist.length-1);
	      		textlist = textlist.substring(0,textlist.length-1);
	      		var transfer = "idlist=" + idlist + "&textlist=" + textlist;
      		 
      		}
      	}
     	window.opener.addvalue(idlist,textlist,"<%=idname%>","<%=textname%>");
       	window.close();

      }
	//��ѡʱ���������еĽڵ㴦���¼�
      function funcClick(){
      	
      	var selectId = cmdMenu.selectedNode.id;

      	if( selectId == null || selectId == '' ){
      		return false;
      	}
      	var node = cmdMenu.lookupNode(selectId);
        var textname = node.text;
        
        if(node.text=='���ش���' || node.text=='�־ֿ���' || node.text=='�ֹܹ�����' ||node.text=='�־ֹ�����' || node.text=='ֱ����ҵ��λ'){
          return false;
        }
      	var transfer = "id=" + selectId + "&text=" + textname;
      	var parentNode=cmdMenu.lookupNode(node.parentid);
      	if(parentNode){
      	  if(parentNode.text=='���ش���' ||parentNode.text=='�־ֿ���' || parentNode.text=='�־ֹ�����' || parentNode.text=='�ֹܹ�����' || parentNode.text=='ֱ����ҵ��λ'){
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
    //ȡ����ť
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
				var cmdMenu = new TreeDefine( 'cmdMenu', '����ѡ��', '', 'tree', true, '', 'select-key', 
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
		          		  if(jgjc.equals("���ط־�")){
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"0','�־ֿ���','','','�־ֿ���',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"1','�־ֹ�����','','','�־ֹ�����',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"2','ֱ����ҵ��λ','','','ֱ����ҵ��λ',false),");
		          		  }
		          		  if(db.getValue("jgmc").equals("�оֻ���")){
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"1','���ش���','','','���ش���',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"2','ֱ����ҵ��λ','','','ֱ����ҵ��λ',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"0','רҵ�־�','','','רҵ�־�',false),");
		          		  }
		          		}
		          	}
		              for(int i=0;i<contextDB.getRecordset("select-key").size();i++){
		                DataBus db=contextDB.getRecordset("select-key").get(i);
		                String sjjgid_fk=db.getValue("sjjgid_fk");
		                String jgid_pk=db.getValue("jgid_pk");
		                String jgmc=db.getValue("jgmc");
		                if(db.getValue("jgjc").equals("רҵ�־�")){
		              	  sjjgid_fk=sjjgid_fk+"0";
		                }
		                if(db.getValue("jgjc").equals("�־ֿ���")){
		              	  sjjgid_fk=sjjgid_fk+"0";
		                }
		                if(db.getValue("jgjc").equals("�־ֹ�����")){
		              	  sjjgid_fk=sjjgid_fk+"1";
		                }
		                if(db.getValue("jgjc").equals("ֱ����ҵ��λ")){
		              	  sjjgid_fk=sjjgid_fk+"2";
		                }
		                if(db.getValue("jgjc").equals("���ش���")){
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
				var cmdMenu = new TreeDefine( 'cmdMenu', '����ѡ��', '', 'check', true, '', 'select-key', 'sjjgid_fk', 'jgid_pk', 
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
		          		  if(jgjc.equals("���ط־�")){
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"0','�־ֿ���','','','�־ֿ���',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"1','�־ֹ�����','','','�־ֹ�����',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"2','ֱ����ҵ��λ','','','ֱ����ҵ��λ',false),");
		          		  }
		          		  if(db.getValue("jgmc").equals("�оֻ���")){
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"1','���ش���','','','���ش���',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"2','ֱ����ҵ��λ','','','ֱ����ҵ��λ',false),");
		          			  out.println("new XmenuNode('"+jgid_pk+"','"+jgid_pk+"0','רҵ�־�','','','רҵ�־�',false),");
		          		  }
		          		}
		          	}
		              for(int i=0;i<contextDB.getRecordset("select-key").size();i++){
		                DataBus db=contextDB.getRecordset("select-key").get(i);
		                String sjjgid_fk=db.getValue("sjjgid_fk");
		                String jgid_pk=db.getValue("jgid_pk");
		                String jgmc=db.getValue("jgmc");
		                if(db.getValue("jgjc").equals("רҵ�־�")){
		              	  sjjgid_fk=sjjgid_fk+"0";
		                }
		                if(db.getValue("jgjc").equals("�־ֿ���")){
		              	  sjjgid_fk=sjjgid_fk+"0";
		                }
		                if(db.getValue("jgjc").equals("�־ֹ�����")){
		              	  sjjgid_fk=sjjgid_fk+"1";
		                }
		                if(db.getValue("jgjc").equals("ֱ����ҵ��λ")){
		              	  sjjgid_fk=sjjgid_fk+"2";
		                }
		                if(db.getValue("jgjc").equals("���ش���")){
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
					value="ȷ��">&nbsp;&nbsp;
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
				<input name="ok" type="button" class="menu" onClick="cancelButton();" value="ȡ��">
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
