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
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
.jazz-field-comp-in2{
	background-color:transparent;
}
.jazz-button-disabled{
	background-color:#3498db;
}

a:hover .button-text{
    background:url(<%=contextpath%>/static/script/JAZZ-UI/lib/themes/gongshang/images/center-1-2.png) repeat;
}
</style>
<script>
	
	var type = null;
	var pkDcStandardSpec = null;
	function initData(res){
		type = res.getAttr("type");
		pkDcStandardSpec = res.getAttr("pkDcStandardSpec");
	}
	
	$(function(){
		$("#formpanel_edit .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
		$("#btn3").button("option","disabled",true);
		if(type == "add"){
			$('div[name="createrName"]').textfield("option","disabled",true);
			$('div[name="createrTime"]').textfield("option","disabled",true);
			$('div[name="modifierName"]').hide();
			$('div[name="modifierTime"]').hide();
			setMessage();
		}
		if(type == "update"){
			$("#btn3").button("option","disabled",false);
			/*  $("#fileName").attachment("option","click", function(){  
			 alert($("#fileName").attachment("getValue"));
			if($("#fileName").attachment("getValue")!= null){
				jazz.info("11111");
			}
		
	});  */
			
			$('div[name="createrName"]').textfield("option","disabled",true);
			$('div[name="createrTime"]').textfield("option","disabled",true);
			$('div[name="modifierName"]').textfield("option","disabled",true);
			$('div[name="modifierTime"]').textfield("option","disabled",true);
			$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+
					'/dataStandar/getDataStandarById.do?pkDcStandardSpec='+pkDcStandardSpec);
			$('div[name="formpanel_edit"]').formpanel('reload');
			
		}
	});
	
	
	function setMessage(){
		$('div[name="formpanel_edit"]').formpanel('option','dataurl',rootPath+
				'/dataStandar/setMessage.do');
		$("div[name='formpanel_edit']").formpanel('reload');
	}
	
	function _close(e, data){
		e.currentClass.deleteFile(data);
		$("#btn3").button("option","disabled",true);
		//$("#fileName").attachment("option","fileuploadlimit","1");
		//$("#fileName").attachment("deleteFile", data);
		
	}
	
	
	function _uploadsuccess(){
		jazz.info("上传成功！");
		$("#btn3").button("option","disabled",false);
		
	}
	
	function save(){
		var standardName = $('div[name="standardName"]').textfield('getValue');
		var standardCom = $('div[name="standardCom"]').textfield('getValue');
		var releaseTime = $('div[name="releaseTime"]').datefield('getValue');
		if(standardName == ''||standardCom == ''||releaseTime == ''){
			jazz.info("有必填选项未填写，保存失败");
		}
		else{
			var params = {
					url:rootPath+"/dataStandar/saveDataStandard.do?type="+type, 
					components: ['formpanel_edit'],
					callback : function(data,obj,res) {
						window.top.$("#frame_maincontent").get(0).contentWindow.query();
						back();
					}
				};
				$.DataAdapter.submit(params);
			
		}
		
		
	}
	
	function back(){
		window.top.$("#frame_maincontent").get(0).contentWindow.leave();
	}

</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel" height="100%"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		dataurl="">

		<div name='pkDcStandardSpec' vtype="hiddenfield" label="主键" labelAlign="right"></div>
		
		<div name='standardName' vtype="textfield" label="规范发布名称" labelAlign="right"
			 rule="must" width="95%" labelwidth="100" maxlength="50"></div>
		<div name='standardCom' vtype="textfield" label="规范发布单位" labelAlign="right"
			rule="must" width="95%" labelwidth="125" maxlength="50"></div> 
		
		<div name='releaseTime' id="releaseTime" vtype="datefield" label="规范发布时间" labelAlign="right"
			rule="must" width="95%" labelwidth="100"></div>
			
			<!-- style="height: 30px;"给上传文件如果自定义封装的时候 -->
		<div name="fileName" id="fileName" vtype="attachment"   label="上传文件"  title="上传附件" fileuploadlimit="1" single="true"  labelwidth="120" width="95%" close="_close()"  rule="must"
			description="请上传标准规范文档" ishidelist="0" theme="1" uploadsuccess="_uploadsuccess()"  allowfiletypes="*.doc;*.docx" allowfiletypesdesc="doc" ></div>
		<div name="fileUrl" id="fileUrl" vtype="hiddenfield"></div>
		
		<div name='createrName' vtype="textfield" label="创建人" labelAlign="right"
		    width="95%" labelwidth="100"></div>
		    
		<div name='createrTime' vtype="textfield" label="创建时间" labelAlign="right" 
		    width="95%" labelwidth="120"></div>
		    
		<div name='modifierName' vtype="textfield" label="最后修改人" labelAlign="right" 
			width="95%" labelwidth="100"></div>
		<div name='modifierTime' vtype="textfield" label="最后修改时间" labelAlign="right"
			width="95%" labelwidth="120"></div>
		<div name='remarks' vtype="textareafield"  colspan="2" rowspan="1" label="备注" labelAlign="right" 
			 width="97.5%" labelwidth="100" height="110" maxlength="1000"></div>
		
		
		<div id="toolbar" name="toolbar" vtype="toolbar" class="toolLine" >
	    	<div id="btn3" name="btn3" vtype="button" text="保 存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返 回" defaultview="1" align="center" click="back()"></div>
	    </div>
	</div>

</body>
</html>