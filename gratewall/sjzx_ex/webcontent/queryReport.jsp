<%@ page language="java" import="" pageEncoding="UTF-8"%>
<%
String modelStr = request.getParameter("modelStr");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
		<script type="text/javascript" src="scripts-dq/lib/jquery-1.2.6-packed.js"></script>
		<script type="text/javascript" src="scripts-dq/lib/jquery.ui-1.6-packed.js"></script>
		<script type="text/javascript" src="scripts-dq/lib/json2.js"></script> 
		
		<script type="text/javascript" src="scripts-dq/youi/youi.all-1.1.0-packed.js"></script>
		<script type="text/javascript" src="scripts-dq/youi/youi.field-modified.js"></script>
		<script type="text/javascript" src="scripts-dq/youi/youi.tree-1.1.1-packed.js"></script>
		<script type="text/javascript" src="scripts-dq/page/youi.extraNew.js"></script>
		<script type="text/javascript" src="scripts-dq/youi/youi.queryReport.js"></script>
		<script type="text/javascript" src="scripts-dq/youi/ui.datepicker-zh-CN.js"></script>
		
		<link rel="stylesheet" type="text/css" href="styles-dq/page/youi.extra.css"/>
		<link rel="stylesheet" type="text/css" href="styles-dq/youi.panel-1.1.0.css"/>
		<link rel="stylesheet" type="text/css" href="styles-dq/youi.tree-1.1.0.css"/>
		<link rel="stylesheet" type="text/css" href="styles-dq/youi.field-1.1.0.css"/>
		<link rel="stylesheet" type="text/css" href="styles-dq/youi.queryReport.css"/>
		<link rel="stylesheet" type="text/css" href="styles-dq/ui/themes/flora/flora.dialog.css"/>
		<link rel="stylesheet" type="text/css" href="styles-dq/ui/themes/flora/flora.resizable.css"/>
		<link rel="stylesheet" type="text/css" href="styles-dq/ui/themes/flora/flora.tabs.css"/>
		<link rel="stylesheet" type="text/css" href="styles-dq/ui/themes/flora/flora.datepicker.css" type="text/css"></link>
		<script type="text/javascript">
			var modelStr = '';
			modelStr = '<%= modelStr%>';
			if (modelStr == 'null') modelStr = '';
			$(document).ready(function(){
				$.youi._is_debug = true;//??????????????????
				$.youi.serverConfig.path = '';
				$('#queryReport').queryReport({
					metaTrees:{
						'target':{
							text:'????????????',
							src:'treeList.action?method=getTargetTree',//'data/targetTree.data',
							treeOptions:{
								
							}
						},
						'group':{
							text:'????????????',
							src:'treeList.action?method=getGroupTree',//'data/groupTree.data',
							treeOptions:{
								dragClass:'group'//
							}
						}
					},
					templateId:modelStr
				});
			});
		</script>
	</head>
	<body class="flora">
		<div id="queryReport">
			<div class="metaPanel">
				<ul style="width:600px;margin-left:-2px;"></ul>
				<div class="treePanel"></div>
			</div>
			<div class="queryPanel">
				<table>
					<tr>
						<td valign="top">
						<div class="pub">
							<div title="???????????????" id="addZlBlock"></div>
							<div title="???????????????" id="addBlBlock"></div>
						</div>
						</td>
						<td valign="top"><div class="bl"><div class="blocks"></div></div></td>
					</tr>
					<tr>
						<td valign="top"><div class="zl"><div class="blocks"></div></div></td>
						<td valign="top">
							<div class="work">
								<div class="targets">
									<div class="header"><span>??????????????????</span><input id="targetsName"/><input id="editConds" value="???????????????" type="button"/><input id="editTargets" value="???????????????" class="hide" type="button"/></div>
									<div class="content">
										<div class="targets-content" id="targets"></div>
										<div class="targets-content" id="otherConds"></div>
									</div>
								</div>
								<div class="group">
									<div class="header">
										<span>???????????????</span><input id="groupName"/>
										<span class="buttonSpan tree">
											<input type="button" class="youi-button" value="??????" id="expandGroupItemTree"/>
											<input type="button" class="youi-button" value="????????????" id="fastSelectItems"/>
											&nbsp;&nbsp;<input id="groupCheckbox" type="checkbox" value="checkbox"/><label>????????????</label>
										</span>
										<span class="buttonSpan cond">
											<input type="button" class="youi-button" value="?????????" id="addCondition"/>
										</span>
									</div>
									<div class="content">
										<div id="tree-groupItem" class="itemPanel tree">
											
										</div>
										<div id="cond-groupItem" class="itemPanel cond"> 
											<table width="420" border="0" cellspacing="0" cellpadding="0">
											  <tr>
											    <td width="25%"><div class="innerItem" style="width:100px;">????????????</div></td>
											    <td width="15%"><div class="innerItem" style="width:65px;">???????????????</div></td>
											    <td width="30%"><div class="innerItem" style="width:128px;">??????</div></td>
											    <td width="25%"><div class="innerItem" style="width:108px;">????????????</div></td>
											    <td width="5%"><div class="innerItem" style="width:19px;">???</div></td>
											  </tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<div id="dialog-saveTemplate" style="display:none;">
			<table>
				<tr>
					<td style="vertical-align:middle;width:60px;">????????????</td>
					<td>
						<input class="modelInput" style="width:255px;" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align:top">????????????</td>
					<td>
						<textarea class="modelMemo" style="width:255px;" cols="30" rows="4"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div id="dialog-fastSelectGroupItems"></div>
		<div id="dialog-params" style="display:none;">
			<div id="paramTabs">
				<ul style="height: 30px;">
				<!--
					<li><a href="#fragment-1"><span>One</span></a></li> 
				-->
				</ul>
			</div>
		</div>
		<div id="dialog-title">
			<div id="titleInput" >
			</div>
		</div>
		<div id="dialog-fastSelect">
			<div id="fastSelectList" >
			</div>
		</div>
		<div id="dialog-loading" style="left:60px; position:absolute; top:40px; height:10px; width:10px; display:none;"><div><label>?????????...</label></div><div><br /><img src="images/ajax-loader.gif" /></div></div>
		
		<div id="youi-extra-dialog-edit" class="youi-extra-dialog-edit" style="display:none">
          <div class="youi-extra-dialog-edit-leftCol-title newText" >????????????</div>
		  <div class="youi-extra-dialog-edit-leftCol col">
		  	<div class="listText">???</div>
		  </div>
		  <div class="youi-extra-dialog-edit-opr listText">
		  	<div class="listText">=</div>
		  </div>
          <div class="youi-extra-dialog-edit-rightCol-title newText" >????????????</div>
		  <div class="youi-extra-dialog-edit-rightCol col">
			<div class="listText">???</div>
		  </div>
          <div class="youi-extra-dialog-edit-input-title newText" >??????</div>
          <div class="youi-extra-dialog-edit-input-type"><div></div></div>
		  <div class="youi-extra-dialog-edit-input"><input type="text" style="width:144px; height:22px; line-height:22px; border:1pt solid #92E1F5;" /></div>
		</div>
		<div id="youi-extra-dialog-oprList" class="youi-extra-dialog-oprList" style="display:none" >
			<div class="list" ></div>
		</div>
		<div id="youi-extra-dialog-rList" class="youi-extra-dialog-rList" style="display:none" >
			<div class="list" ></div>
		</div>
		<div id="youi-extra-dialog-colList" class="youi-extra-dialog-colList" style="display:none" >
			<div class="select" ></div>
			<div class="list" ></div>
		</div>
		<div id="inputItemSelect" style="display:none;"></div>
	</body>
</html>
