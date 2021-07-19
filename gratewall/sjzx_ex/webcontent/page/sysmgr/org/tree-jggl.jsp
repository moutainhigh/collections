<%@ page contentType="text/html; charset=GBK" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<freeze:html width="650" height="300">
<freeze:include href="/script/gwssi-xtree.js"/>
  <head>
    <title>������״չʾ�б�</title>
  </head>
<freeze:body>
  <%
  DataBus contextDB = (DataBus)request.getAttribute("freeze-databus");
  String selectedID = contextDB.getRecord("selectedNode").getValue("selectedId");
   %>
<freeze:title caption="������Ŀ¼�ṹ"/>
<freeze:errors/>
<script language="javascript">
      
// ���Ŀ¼ʱ�����Ķ���
	function treeClick( )
	{
		var win = getIframeByName('tree_view');

		//if( tree.selectedNode.id == '@temp:add-root-node' ){
			//var page = new pageDefine( "insert-jggl.jsp", "���ӻ�����Ϣ");
			//page.goPage(null, win);
		//}
		//else{
      	    var selectId = tree.selectedNode.id;
      	    var selectName = tree.selectedNode.text;
      		//�ڵ�
      		var node = tree.lookupNode(selectId);
      		var param = "select-key:jgid_pk="+selectId;
      		param += "&select-key:jgname="+selectName;		
		
			var page = new pageDefine( "info-jggl.jsp?"+param, "�޸Ļ�����Ϣ" );
	  		page.addValue(tree.selectedNode.id, "primary-key:id");
			page.addValue( 'catalog', 'original:freeze-next-page' );
			var win = getIframeByName('tree_view');
			if( win != null ){
				page.goPage( null, win );
			}
			
			return true;
		//}
	}      
     //���� 
      function func_record_refresh(){
        goBack( "/txn806001.do");
      }
      //ˢ��ʱ��¼��ǰ��ѡ��ڵ�
      function setTreeSelectedNode(){
        var id = "<%=selectedID%>";
        if(id == null || id.length < 1){
        	return ; 
        }
       
      	tree.menu.fireEvent(id, "onclick");
      }      
      
// Ŀ¼��ʼ��ǰ�Ķ���
	function treePrepare()
	{
		// �������һ���ڵ�
		//tree.addNode( '', '@temp:add-root-node', '���ӻ���' );
		
		
	}
	function func_record_addRecord(){
	
		var win = getIframeByName('tree_view');
		var page = new pageDefine("/txn806007.do", "���ӻ���");
		page.goPage(null, win);
	}
	
</script>
  <table border="0" cellpadding="0" cellspacing="0" width="95%" height="95%" align="center" style='table-layout:fixed;'>
	<tr height="1"><td width="30%"></td><td width="70%"></td></tr>
	<tr height="26" valign="top"><td width="100%"></td><td style="padding-right:1.5%;" width="100%" align="right">
	<div title="����" class="btn_add" onclick="func_record_addRecord()"></div>
	</td></tr>
	<tr><td height="1" bgcolor="3366FF" width="100%" colspan="2"></td></tr>
	<tr height="99%">
	<td>
         <script language='javascript'>
           var tree = new TreeDefine( 'tree', '�����й������������', '', 'tree', true, 
              '806001', 'select-key', 'sjjgid_fk', 'jgid_pk', '{jgmc}', '{jgmc}', '', 
              'icon', 'openicon', 'F7F7F7', '', '', '', 'scrollend.gif', 'treeClick', '', '', '', '', '', '', '', '',[ 
        <%
          if(!contextDB.getRecordset("select-key").isEmpty()){
        	System.out.println(contextDB.getRecordset("select-key").size());
        	
        	//001001001016
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
treePrepare();
tree.create('100%', '100%');
         
         </script>
	</td>
	<td valign="top" valign="top" align="center" >
	<iframe name='tree_view' scrolling=no frameborder=0 width=95% height=100% style="display:block"></iframe>
	</td>
	</tr>
  </table>
</freeze:body>
  <script language="javascript">
  	setTreeSelectedNode();
  </script>
</freeze:html>
