<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String contextpath = request.getContextPath();
%>
<style type="text/css">
.title_posi {
    color: #666666;
    font-family: simsun;
    font-size: 12px;
    font-weight: normal;
    line-height: 30px;
}
.jazz-column-element {
    border: 0 none;
    float: left;
    height: 100%;
    margin: 0;
    padding: 0;
    overflow-y: auto !important;
    overflow-x: hidden !important;
}
#toolbar{
    position:fixed;

}
#xx{width:50px;height:100%;border-right:1px dashed blue}
.ztree li span.button.ico_open {/* 树打开状态  有子节点的时候*/
    background: rgba(0, 0, 0, 0) url("../static/images/treeicon/tree_folderopen.gif") no-repeat scroll center center !important;
    height: 30px;
    width: 20px;
}
.ztree li span.button.ico_close {/* 树关闭状态 父类节点状况 */
    background: rgba(0, 0, 0, 0) url("../static/images/treeicon/tree_folder.gif") no-repeat scroll center center !important;
    height: 30px;
    width: 20px;
}
.ztree li span.button.ico_docu {/* 没有节点的状况 */
    background: rgba(0, 0, 0, 0) url("../static/images/treeicon/tree_page.gif") no-repeat scroll center center !important;
    height: 30px;
    width: 20px;
}
</style>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
var pkposition=null;
	function initData(res){
		pkposition=res.getAttr("pkposition");
	}
	var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			}
		},
		callback: {
			
		}
	};
	



	var tree;
	$(document).ready(function(){
		//addButton();
		$(".jazz-column-element").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
			var params = {
				url : rootPath+'/auth/getPositionName.do?pkposition=' + pkposition,	
				callback : function(data, r, res) { 
					$("#showPosition").text("岗位名称：" + res.getAttr("showPosition"));
				}
			}
			$.DataAdapter.submit(params);
		refreshFuncTree();
	
	});

	
	function refreshFuncTree(){
		var params = {
			url : rootPath+'/auth/getPosiRole.do?pkposition='+pkposition,
			callback : function(data, r, res) { 
				var data = data["data"];
				tree = $.fn.zTree.init($("#funcTree"), setting, data);
				tree.expandAll(true);
			}
		}
		$.DataAdapter.submit(params);
	}
	

</script>
</head>
<body>
        <div id="demo" >
          	<div id="div1" style="height:5px;"></div>
        	<div id="toolbar" name="toolbar" vtype="toolbar" align="center">
				<div name="showPosition" class="title_posi" id="showPosition"> </div>
		
			</div>
			 <div id="div1" style="height:20px;"></div>		
			 <div id="funcTree" name="funcTree" class="ztree"  ></div>
		</div>
    	    	

 
</body>

</html>