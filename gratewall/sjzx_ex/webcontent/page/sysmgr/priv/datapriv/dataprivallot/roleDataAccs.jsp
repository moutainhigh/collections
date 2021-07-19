<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:include href="/script/lib/jquery.js"/>
<freeze:include href="/script/page/roleDataAccs.js"/>
<freeze:include href="/script/component/JqTree.js"/>
<freeze:include href="/script/plugins/jquery.form.js"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/JqTree.css"/>

<freeze:html  title="选择角色权限">
<style>
	.div_frame{
		width:49%;
		float:left;
	} 
	
	.div_frame_title{
		position:relative;
		width:120px;
		height:20px;
		border:1pt solid #7BA9E9;
		left:50px;
		top:13px;
		z-index:10;
		background-color:#E6E6E6;
		cursor:pointer;
	}
	
	.div_frame_title_text{
		position:relative;
		margin-top:3px;
		text-align:center;
		width:120px;
	}
	
	.div_frame_context{
		position:relative;
		left:1px;
		top:1px;
		width:100%;
		
		margin-left:21px;
		height:370px;
		border:1pt solid #7BA9E9;
	}
	
	input.buttonface{
		COLOR: #000000;
		FONT-SIZE: 9pt;
		FONT-STYLE: normal;
		FONT-VARIANT: normal;
		FONT-WEIGHT: normal;
		LINE-HEIGHT: normal;
		background-image: url(../images/smallInput.gif);
		border: 1px solid #207FCF;
		height: 22px;
	}
</style>
<script>	
	$(document).ready(function() {
		//首先根据读取权限信息
		var roleid = "<%=request.getParameter("roleid")%>";
		$("input[@name=record:roleids]").attr("value",roleid);
		var contextPath = "<%=request.getContextPath()%>";
		var loadSrc = contextPath+"/txn1030049.ajax?record:roleids="+roleid;
		$.get(loadSrc,function(xml){
			if($("error-code",xml).text()=="000000"){
				//selectGroups
				/**
				var returnRecord = $("record:first",xml);
				*/
				$("record",xml).each(function(){
					var selectGroups = $("selectGroups",this).text();
					//document.getElementsByName("record:selectGroups").value = selectGroups;
					$("input[@name=record:selectGroups]").attr("value",selectGroups);
					var dataaccgrpidArray = selectGroups.split(",")||new Array();
					new pageRoleDataAccs.create(contextPath,roleid,dataaccgrpidArray);
				});
				
			}else{
				alert($("error-desc",xml).text());
			}
		});
		
	});
</script>

<freeze:body>
<form name="groupForm" action="<%=request.getContextPath()%>/txn1030048.ajax" method="post" accept-charset="GBK">
	<input type="hidden" name="record:saveXml"/>
	<input type="hidden" name="record:selectGroups"/>
	<input type="hidden" name="record:roleids"/>
	<div style="height:410px;">
		<div class="div_frame">
			<div class="div_frame_title"> <div class="div_frame_title_text">自定义数据权限</div></div>
			<div align="center"  class="div_frame_context">
				<div style="float:left;margin:12px;margin-bottom:12px;margin-left:1px;">
					<div  style="margin-left:0;">
					访问规则：
					<select name="record:dataaccrule">
						<option value="1">1-允许访问选择项</option>
						<option value="2">2-允许访问非选择项</option>
					</select>
					</div>
					<div align="left" style="width:270px;height:320px;overflow-y:auto;overflow-x:hidden;" id="dataObjectTree"></div>
				</div>
			</div>
		</div>
		<div class="div_frame">
			<div class="div_frame_title"> <div class="div_frame_title_text">选择数据权限组</div></div>
			<div align="center" class="div_frame_context">
				<div style="margin:12px;margin-bottom:12px;">
					<select id="dataGroups" multiple="multiple" style="width:208px;" size="22"></select>
				</div>
			</div>
		</div>
	</div>
	
	<div align="center">
		<div style="padding-top:10px;">
			<input id="save_dataGroups" type="submit" class="buttonface" value=" 保 存 "/>
			<input onclick="window.close();" type="button" class="buttonface" value=" 返 回 "/>
		</div>
	</div>
</form>
</freeze:body>
</freeze:html>