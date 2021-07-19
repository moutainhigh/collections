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
</style>
<script>

	var pkDcProcedure = null;
	var type = null;
	
	function initData(res){
		pkDcProcedure = res.getAttr("pkDcProcedure");
		type = res.getAttr("type");
		
		 
	}
	
	$(function(){
		
		$("#formpanel_edit .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
		if(type == "update"){
			$('div[name="createrName"]').textfield("option","disabled",true);
			$('div[name="createrTime"]').textfield("option","disabled",true);
			$('div[name="modifierName"]').textfield("option","disabled",true);
			$('div[name="modifierTime"]').textfield("option","disabled",true);
			$('div[name="procSql"]').hide();
			$('div[name="procName"]').textfield("option","disabled",true);
			$('div[name="busiObjectName"]').textfield("option","disabled",true);
			
			var params = {
					url:rootPath+'/proceList/showProce.do?pkDcProcedure='+pkDcProcedure,
					callback : function(data, r, res) {
						$('div[name="pkDcProcedure"]').hiddenfield('setValue',res.getAttr("pkDcProcedure"));
						$('div[name="procName"]').textfield('setValue',res.getAttr("procName"));
						$('div[name="busiObjectName"]').textfield('setValue',res.getAttr("busiObjectName"));
						$('div[name="procUseDesc"]').textfield('setValue',res.getAttr("procUseDesc"));
						$('div[name="procSql"]').textfield('setValue',res.getAttr("procSql"));
						$('div[name="createrName"]').textfield('setValue',res.getAttr("createrName"));
						$('div[name="createrTime"]').textfield('setValue',res.getAttr("createrTime"));
						$('div[name="modifierName"]').textfield('setValue',res.getAttr("modifierName"));
						$('div[name="modifierTime"]').textfield('setValue',res.getAttr("modifierTime"));
						$('div[name="remarks"]').textareafield('setValue', res.getAttr("remarks"));
					}
			};
			$.DataAdapter.submit(params);
			
		}
		
		if(type == "check"){
			
			$('div[name="procUseDesc"]').textfield("option","height",70);
			$('div[name="procSql"]').textfield("option","height",500);
			$("#btn3").button("hide");
			$("#btn4").button("hide");
			$(".jazz-form-text-label").children("font").remove();//取出必须项 *号
			$("#formpanel_edit").formpanel("option","readonly",true);
			var params = {
					url:rootPath+'/proceList/showProce.do?pkDcProcedure='+pkDcProcedure,
					callback : function(data, r, res) {
						$('div[name="pkDcProcedure"]').hiddenfield('setValue',res.getAttr("pkDcProcedure"));
						$('div[name="procName"]').textfield('setValue',res.getAttr("procName"));
						$('div[name="busiObjectName"]').textfield('setValue',res.getAttr("busiObjectName"));
						$('div[name="procUseDesc"]').textfield('setValue',res.getAttr("procUseDesc"));
						$('div[name="procSql"]').textfield('setValue',res.getAttr("procSql"));
						$('div[name="createrName"]').textfield('setValue',res.getAttr("createrName"));
						$('div[name="createrTime"]').textfield('setValue',res.getAttr("createrTime"));
						$('div[name="modifierName"]').textfield('setValue',res.getAttr("modifierName"));
						$('div[name="modifierTime"]').textfield('setValue',res.getAttr("modifierTime"));
						$('div[name="remarks"]').textareafield('setValue', res.getAttr("remarks"));
					}
			};
			$.DataAdapter.submit(params);
		}
	});
	
	function back() {
		window.top.$("#frame_maincontent").get(0).contentWindow.leave();
	} 
	
	
	function save(){
			
			var procUseDesc = $('div[name="procUseDesc"]').textfield('getValue');
			if(procUseDesc == ''){
				jazz.info("有必填选项未填写，保存失败");
			}
			else{
				if(type == "update"){
					 
					var params = {
							url : rootPath+'/proceList/saveUpdateProce.do',
							components : [ 'formpanel_edit' ],
							callback : function(data,r,res){
								if(res.getAttr("back") == 'success'){
									window.top.$("#frame_maincontent").get(0).contentWindow.query();
									back();
								}
								else{
									jazz.info("编辑失败");
								}
							}
					};
					$.DataAdapter.submit(params);
				}
					
			}
		}
	
	
	
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel" height="100%"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		dataurl="">

		<div name='pkDcProcedure' vtype="hiddenfield" label="主键" labelAlign="right"></div>
		<div name='procName' vtype="textfield" label="存储过程名称" labelAlign="right"
			 width="80%" labelwidth="100" maxlength="50" ></div>
		<div name='busiObjectName' vtype="textfield" label="业务系统" labelAlign="right"
			width="80%" labelwidth="100" maxlength="50" ></div>
		<div name='procUseDesc' vtype="textfield" label="存储过程用途" labelAlign="right" colspan="2" rowspan="1"
			rule="must" width="90%"  labelwidth="100" maxlength="1000"></div>
		<div name='createrName' vtype="textfield" label="创建人" labelAlign="right"
		    width="80%" labelwidth="100"></div>
		<div name='createrTime' vtype="textfield" label="创建时间" labelAlign="right" 
		    width="80%" labelwidth="100"></div>
		<div name='modifierName' vtype="textfield" label="最后修改人" labelAlign="right" 
			width="80%" labelwidth="100"></div>
		<div name='modifierTime' vtype="textfield" label="最后修改时间" labelAlign="right"
			width="80%" labelwidth="100"></div>
		<div name='remarks' vtype="textareafield"  colspan="2" rowspan="1" label="备注" labelAlign="right" 
			 width="90%" labelwidth="100" height="110" maxlength="1000"></div>
		<div name='procSql' vtype="textfield" label="存储过程脚本" labelAlign="right" colspan="2" rowspan="1"
			width="90%"  labelwidth="100" maxlength="1500"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" text="保 存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返 回" defaultview="1" align="center" click="back()"></div>
	    </div>
	</div>
</body>
</html>