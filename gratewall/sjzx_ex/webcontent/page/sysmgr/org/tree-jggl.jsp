<%@ page contentType="text/html; charset=GBK" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<freeze:html width="650" height="300">
<freeze:include href="/script/gwssi-xtree.js"/>
  <head>
    <title>机构树状展示列表</title>
  </head>
<freeze:body>
  <%
  DataBus contextDB = (DataBus)request.getAttribute("freeze-databus");
  String selectedID = contextDB.getRecord("selectedNode").getValue("selectedId");
   %>
<freeze:title caption="机构的目录结构"/>
<freeze:errors/>
<script language="javascript">
      
// 点击目录时触发的动作
	function treeClick( )
	{
		var win = getIframeByName('tree_view');

		//if( tree.selectedNode.id == '@temp:add-root-node' ){
			//var page = new pageDefine( "insert-jggl.jsp", "增加机构信息");
			//page.goPage(null, win);
		//}
		//else{
      	    var selectId = tree.selectedNode.id;
      	    var selectName = tree.selectedNode.text;
      		//节点
      		var node = tree.lookupNode(selectId);
      		var param = "select-key:jgid_pk="+selectId;
      		param += "&select-key:jgname="+selectName;		
		
			var page = new pageDefine( "info-jggl.jsp?"+param, "修改机构信息" );
	  		page.addValue(tree.selectedNode.id, "primary-key:id");
			page.addValue( 'catalog', 'original:freeze-next-page' );
			var win = getIframeByName('tree_view');
			if( win != null ){
				page.goPage( null, win );
			}
			
			return true;
		//}
	}      
     //返回 
      function func_record_refresh(){
        goBack( "/txn806001.do");
      }
      //刷新时记录以前的选择节点
      function setTreeSelectedNode(){
        var id = "<%=selectedID%>";
        if(id == null || id.length < 1){
        	return ; 
        }
       
      	tree.menu.fireEvent(id, "onclick");
      }      
      
// 目录初始化前的动作
	function treePrepare()
	{
		// 增加最后一个节点
		//tree.addNode( '', '@temp:add-root-node', '增加机构' );
		
		
	}
	function func_record_addRecord(){
	
		var win = getIframeByName('tree_view');
		var page = new pageDefine("/txn806007.do", "增加机构");
		page.goPage(null, win);
	}
	
</script>
  <table border="0" cellpadding="0" cellspacing="0" width="95%" height="95%" align="center" style='table-layout:fixed;'>
	<tr height="1"><td width="30%"></td><td width="70%"></td></tr>
	<tr height="26" valign="top"><td width="100%"></td><td style="padding-right:1.5%;" width="100%" align="right">
	<div title="增加" class="btn_add" onclick="func_record_addRecord()"></div>
	</td></tr>
	<tr><td height="1" bgcolor="3366FF" width="100%" colspan="2"></td></tr>
	<tr height="99%">
	<td>
         <script language='javascript'>
           var tree = new TreeDefine( 'tree', '深圳市工商行政管理局', '', 'tree', true, 
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
