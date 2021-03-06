<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%
	String contextpath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=contextpath %>/static/script/jazz.js" type="text/javascript"></script>
<script>
	
	var pkNotice;
	var username;
	var currentDate;
	
	$(function(){
		
		
		$('div[name="createrName"]').textfield("option","disabled",true);
		$('div[name="createrTime"]').textfield("option","disabled",true);
		$('div[name="modifierName"]').textfield("option","disabled",true);
		$('div[name="modifierTime"]').textfield("option","disabled",true);
		
		if(pkNotice == null){
			$('div[name="modifierName"]').hide();
			$('div[name="modifierTime"]').hide();
			$('div[name="createrName"]').textfield("setValue",username);
			$('div[name="createrTime"]').textfield("setValue",currentDate);
			$('div[name="modifierName"]').textfield("setValue",username);
			$('div[name="modifierTime"]').textfield("setValue",currentDate);
			
		}
		else{
			$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+
					'/message/show.do?pkNotice='+pkNotice);
			$('div[name="formpanel_edit"]').formpanel('reload');
			$('div[name="effectiveTime"]').datefield("option", "disabled", true);
			
			
		}
		
		
		
		
	});
	
	function checkTree(){
		var	tree = $.fn.zTree.getZTreeObj("zTree_sendTo");
		tree.setting.check.chkboxType = { "Y" : "s", "N" : "s" };

	}
	
	function initData(res){
		username = res.getAttr("username");
		currentDate = res.getAttr("currentDate");
		pkNotice = res.getAttr("pkNotice");
		 
	}
	
	
	
	function save(){
		var pkNotice = $('div[name="pkNotice"]').hiddenfield('getValue');
		var title = $('div[name="title"]').textfield('getValue');
		var sendTo = $('div[name="sendTo"]').comboxtreefield('getValue');
		var effectiveTime = $('div[name="effectiveTime"]').datefield('getValue');
		var createrName = $('div[name="createrName"]').textfield('getValue');
		var createrTime = $('div[name="createrTime"]').textfield('getValue');
		var modifierName = $('div[name="modifierName"]').textfield('getValue');
		var modifierTime = $('div[name="modifierxtTime"]').textfield('getValue');
		var content = $('div[name="content"]').textareafield('getValue');
		
		var treeObj = $.fn.zTree.getZTreeObj("zTree_sendTo");
		var nodes = treeObj.getCheckedNodes(true);
		var nodelist = [];
		$.each(nodes,function(i,node){
			if(node.type == 8){
				nodelist.push(node.id);
			}
			
		});
		var sendTos = nodelist.join(",");
		
		//alert(sendTo);
		if(title == ''||sendTo == ''||content ==''||sendTos == ''||effectiveTime == ''){
			if(sendTos == ''&&sendTo != ''){
				jazz.info("??????????????????????????????");
			}
			else{
				jazz.info("???????????????????????????????????????");
			}
			
		}
		
		else{
			var params = {
					url : rootPath+'/message/save.do',
					components : [ 'formpanel_edit' ],
					params: {"sendTos":sendTos},
					callback : function(data, r, res) { 
						if (res.getAttr("back") == 'success'){
							//jazz.info("????????????");
							window.top.$("#frame_maincontent").get(0).contentWindow.query();
							back();
							/* $('div[name="pkNotice"]').hiddenfield("setValue",res.getAttr("pkNotice")); */
						} 
						
						
					}
				};
				$.DataAdapter.submit(params);
		}
			
		
	}
	
	function back() {
		window.top.$("#frame_maincontent").get(0).contentWindow.leave();
	}
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%">

		<div name='pkNotice' vtype="hiddenfield" label="??????" labelAlign="right"></div>
		<div name='title' vtype="textfield" label="??????" labelAlign="right" colspan="2" rowspan="1"
			rule="must" width="90%" labelwidth="100" maxlength="50"></div>
		<div name='sendTo' vtype="comboxtreefield" label="????????????" labelAlign="right" dataurl="<%=contextpath%>/message/findByListPost.do" onclick="checkTree()"
			
			rule="must" width="80%" labelwidth="100"  multiple="true" maxlength="2000"></div>
		<div name='effectiveTime' vtype="datefield" label="????????????" labelAlign="right" rule="must"
			width="80%" labelwidth="100"></div>
		<div name='createrName' vtype="textfield" label="?????????" labelAlign="right"
		    width="80%" labelwidth="100"></div>
		<div name='createrTime' vtype="textfield" label="????????????" labelAlign="right" 
		    width="80%" labelwidth="100"></div>
		<div name='modifierName' vtype="textfield" label="???????????????" labelAlign="right" 
			width="80%" labelwidth="100"></div>
		<div name='modifierTime' vtype="textfield" label="??????????????????" labelAlign="right"
			width="80%" labelwidth="100"></div>
		<div name='content' vtype="textareafield"  colspan="2" rowspan="1" label="??????" height="150"
			labelAlign="right" rule="must" width="90%" labelwidth="100" maxlength="1000"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" text="??? ???" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="??? ???" defaultview="1" align="center" click="back()"></div>
	    </div>
	</div>
	

</body>
</html>