<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>

<freeze:include href="/script/lib/jquery.js"/>
<freeze:include href="/script/page/setRolesMap_main.js"/>
<freeze:include href="/script/lib/interface.js"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/script/plugins/jquery.select.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/component/JqTree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/JqTree.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/JqForm.css"/>
<freeze:html title="角色权限分配">
<freeze:body>
<style>
	body{
		background-color:#C1DCF7;
	}
	.div_frame{
		width:32%;
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
		background-color:#F4FAFC;
		/*cursor:pointer;*/
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
		height:410px;
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
	
	/**
	 * 设置数据权限
	 */
	function func_record__showDataObjectList(){
		
	}

	var contextPath = "<%=request.getContextPath()%>";
	$(document).ready(function() {
		window.functionsSelect = window.document.getElementById("functions");
		new pageRoleSetOpenMain.create(contextPath);
	});
</script>

<freeze:errors/>
<body bgcolor="#C1DCF7;">
<freeze:title caption="角色数据权限分配" />
<div style="width:100%;height:460px;border:1pt solid #7BA9E9;margin:10px;margin-top:3px;">
	<div style="width:100%;heigth:35px;background:#6699FF;">
		<div style="margin-left:10px;margin-top:3px;">数据权限分配</div>
	</div>
	<div class="div_frame" >
		<div class="div_frame_title"> <div class="div_frame_title_text">选择角色</div></div>
		<div align="center"  class="div_frame_context">
			<div style="margin:12px;margin-bottom:12px;">
				<select id="roles" style="width:208px;" size="23"></select>
			</div>
			<div style="float:right;margin-right:5px;margin-top:-5px;">
				<input class="buttonface" id="roleDataAcc" type="button" value="角色权限"/>
			</div>
		</div>
	</div>

	<div class="div_frame">
		<div class="div_frame_title"><div class="div_frame_title_text">功能点列表</div></div>
		<div class="div_frame_context">
		    <div style="width:100%;margin-top:15px;">
			<div style="width:260px;height:355px; overflow:hidden; overflow-y:auto;">
				<div id="functions" style="width:80px;"></div>
			</div>
			</div>
			<div style="width:100%;">
			<div style="width:210px;float:right;">
				<input id="selectAll" class="buttonface" type="button" value=" 全选 "/>
				<input id="selectDispose" class="buttonface" type="button" value=" 取消 "/>
				<input id="selectDataAccGroup" class="buttonface" type="button" value="查看权限组"/>
			</div>
			</div> 
		</div>
	</div>

   <!-- 	 
	<div class="div_frame">
		<div class="div_frame_title"> <div class="div_frame_title_text">选择功能权限</div></div>
		<div align="center" class="div_frame_context">
			<div style="margin:12px;margin-bottom:12px;">
				<select id="functions" multiple="multiple" style="width:208px;" size="23"></select>
			</div>
			<div style="float:right;margin-right:5px;margin-top:-5px;">
				<input id="selectAll" class="buttonface" type="button" value=" 全部选择 "/>
				<input id="selectDispose" class="buttonface" type="button" value=" 取消选择 "/>
			</div>
		</div>
	</div> 
 -->
	<div class="div_frame">
		<div class="div_frame_title"><div class="div_frame_title_text">选择数据权限</div></div>
		<div align="center"  class="div_frame_context">
			<div style="margin:12px;">
				<select id="dataObjects" multiple="multiple" style="width:208px;" size="23"></select>
			</div>
			<div style="float:right;margin-right:5px;margin-top:-5px;">
				<input class="buttonface" id="selectDataAcc" type="button" value="选择数据权限"/>
				<input class="buttonface" id="removeDataGroups" type="button" value="移除数据权限"/>
			</div>
		</div> 
	</div>
</div>


</body>
</freeze:body>
</freeze:html>
