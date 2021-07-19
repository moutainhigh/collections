<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="300">
<freeze:include href="/script/gwssi-xtree.js"/>
  <head>
    <title>功能统计树状展示列表</title>
  </head>
<freeze:body>
<freeze:title caption="功能统计树状展示列表"/>
<freeze:errors/>
<script language="javascript">
	function funclick(){
		var treeNode = areacode.lookupNode(areacode.menu.selectedNode.id);  
		var nodeId = treeNode.id;
		if(nodeId == 100001){//简单查询>主体登记信息
			var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100002){//简单查询>高管人员信息
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=2", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100003){//简单查询>集团登记查询
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=3", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100004){//简单查询>企业监管信息
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "基本信息" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 1000041){//简单查询>企业监管信息>网格信息
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=4", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000042){//简单查询>企业监管信息>无照经营
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=5", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100005){//简单查询>商标监管信息
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=6", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100006){//简单查询>案件管理信息
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=7", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100007){//简单查询>合同监管信息
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=8", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100008){//简单查询>食品安全信息
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "基本信息" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 1000081){//简单查询>食品安全信息>市场动态
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=11", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000082){//简单查询>食品安全信息>食品备案
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=9", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000083){//简单查询>食品安全信息>食品企业
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=10", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000084){//简单查询>食品安全信息>专项整治
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=12", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000085){//简单查询>食品安全信息>突发事件
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=13", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100009){//简单查询>商品质量监管
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "基本信息" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 1000091){//简单查询>商品质量监管>企业信息
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=14", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000092){//简单查询>商品质量监管>商品信息
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=15", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000093){//简单查询>商品质量监管>检测信息
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=16", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000094){//简单查询>商品质量监管>停止销售
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=17", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000095){//简单查询>商品质量监管>有形市场
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=18", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000096){//简单查询>商品质量监管>汽车市场
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=19", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100010){//简单查询>申诉举报信息
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "基本信息" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 1000101){//简单查询>申诉举报信息>申诉信息
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=20", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000102){//简单查询>申诉举报信息>举报信息
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=21", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 100011){//简单查询>广告监管信息
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "基本信息" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 1000111){//简单查询>广告监管信息>专项管理
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=24", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000112){//简单查询>广告监管信息>违法广告
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=23", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000113){//简单查询>广告监管信息>审批查询
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=25", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000114){//简单查询>广告监管信息>发布媒体
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=22", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000115){//简单查询>广告监管信息>投诉举报
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=26", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000116){//简单查询>广告监管信息>案件管理
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=27", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000117){//简单查询>广告监管信息>社团法人
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=28", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 1000118){//简单查询>广告监管信息>事业法人
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=29", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 200000){//全文检索
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "基本信息" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 300000){//高级查询
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "基本信息" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 300001){//高级查询 > 复合查询
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=30", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 300002){//高级查询 > 自定义查询
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=31", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400000){//统计报表
		    //var page = new pageDefine( "/txn60900001.do?select-key:func_index=1", "基本信息" );
			//var win = window.frames('tree_view');
			//page.goPage( null, win );
		}else if(nodeId == 400001){//统计报表>主体登记
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=32", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400002){//统计报表>企业监管
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=33", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400003){//统计报表>案件管理
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=34", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400004){//统计报表>申诉举报
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=35", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400005){//统计报表>商品监管
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=36", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400006){//统计报表>广告管理
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=37", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}else if(nodeId == 400007){//统计报表>灵活报表
		    var page = new pageDefine( "/txn60900001.do?select-key:func_index=39", "基本信息" );
			var win = window.frames('tree_view');
			page.goPage( null, win );
		}
	}
	
</script>
  <table border="0" cellpadding="0" cellspacing="0" width="95%" height="95%" align="center" style='table-layout:fixed;'>
	<tr height="99%">
	<td width="25%">
      <freeze:xtree name="areacode" property="$HOME/sysmgr/stat/frame-detail_tree.xml" aspect="tree" root="功能使用统计"  nodename="node" idname="id" textname="{name}" memoname="condition" width="100%" height="100%" border="1" bordercolor="#aadadb" onclick="funclick()"/>
	</td>
	<td valign="top" valign="top" align="center" >
	<iframe name='tree_view' scrolling=no frameborder=0 width=95% height=100% style="display:block"></iframe>
	</td>
	</tr>
  </table>
</freeze:body>
</freeze:html>
