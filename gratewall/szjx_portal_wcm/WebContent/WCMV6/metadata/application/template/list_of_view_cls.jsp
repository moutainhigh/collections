<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<%
response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires",0); 
response.setHeader("Cache-Control","private"); 
%>
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>视图[<TRS_VIEW FIELD="ViewDesc"/>]数据列表</TITLE>

<!-- ~Common CSS Begin~ -->
<link href="../../../css/common.css" rel="stylesheet" type="text/css" />
<link href="../../../css/wcm_list_abstract.css" rel="stylesheet" type="text/css" />
<!-- ~Common CSS End~ -->

<!-- ~Template CSS End~ -->
<link href="../css/list_of_view.css" rel="stylesheet" type="text/css" />
<!-- ~Template CSS End~ -->

<!-- ~Imports Begin~    -->
<SCRIPT language=JavaScript src="../../../js/com.trs.util/Common.js"></SCRIPT>
<script label="TagParser">
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
</script>
<script label="Imports">
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.util.CommonHelper');
    $import('com.trs.wcm.SheetChanger');
	$import('com.trs.wcm.QuickKey');
	$import('com.trs.wcm.SimpleDragger');
	$import('com.trs.wcm.Grid');
	$import('com.trs.wcm.PageFilter');
	$import('com.trs.wcm.BubblePanel');		
	$import('com.trs.wcm.SimpleQuery');
	$import('com.trs.wcm.domain.PublishMgr');
	$import('com.trs.wcm.domain.DocumentMgr');
	$import('com.trs.wcm.domain.ChnlDocMgr');
</script>
<!-- ~Imports END~    -->

<script src="../../../common/wcm_list_abstract.js" label="PageScope"></script>

<!-- ~Template JavaScript END~    -->
<script language="javascript" src="../js/ViewTemplateMgr.js" type="text/javascript"></script>
<script language="javascript" src="../js/ViewClsTemplateMgr.js" type="text/javascript"></script>
<script language="javascript" src="../js/FileUploader.js" type="text/javascript"></script>
<script language="javascript" src="../js/list_of_view.js" type="text/javascript"></script>
<script language="javascript" src="../js/list_of_view_cls.js" type="text/javascript"></script>
<script language="javascript" src="../js/htmlElementParser.js" type="text/javascript"></script>
<!-- ~Template JavaScript END~    -->

<!-- ~Page JavaScript Begin~    -->
<script language="javascript" src="list_of_view_cls.js" type="text/javascript"></script>
<!-- ~Page JavaScript END~    -->
<script language="javascript">
<!--
	function getPageParams(){
		var oResult = {
			viewId			: <TRS_VIEW FIELD="ViewInfoId"/>,
			searchParams	: []
		};
		<TRS_ViewFields>
			<TRS_Condition Condition="@SearchField" Type="INT" REFERENCE="1">
				<TRS_Condition Condition="@DBTYPE" Type="INT" REFERENCE="4">
				oResult.searchParams.push({
					name: '<TRS_ViewField Field="FieldName" filterForHTML="true"/>', 
					desc: '<TRS_ViewField Field="AnotherName" filterForHTML="true"/>',
					type: 'int'
				});
				</TRS_Condition>						
				<TRS_Condition Condition="@DBTYPE" Type="INT" REFERENCE="6~8">
				oResult.searchParams.push({
					name: '<TRS_ViewField Field="FieldName" filterForHTML="true"/>', 
					desc: '<TRS_ViewField Field="AnotherName" filterForHTML="true"/>',
					type: 'float'
				});
				</TRS_Condition>						
				<TRS_Condition Condition="@DBTYPE" Type="INT" REFERENCE="4~6~8~93" not="true">
				oResult.searchParams.push({
					name: '<TRS_ViewField Field="FieldName" filterForHTML="true"/>', 
					desc: '<TRS_ViewField Field="AnotherName" filterForHTML="true"/>',
					type: 'string'
				});
				</TRS_Condition>
			</TRS_Condition>
		</TRS_ViewFields>				
		return oResult;
	}
//-->
</script>

<style type="text/css">
	.object_edit{
        background:url(../../../images/metadata/edit.gif) center center no-repeat;
	}
	.object_delete{
        background:url(../../../images/metadata/delete.gif) center center no-repeat;
	}
	.object_edit_disabled{
        background:url(../../../images/metadata/edit_disabled.gif) center center no-repeat;
	}
	.object_delete_disabled{
        background:url(../../../images/metadata/delete_disabled.gif) center center no-repeat;
	}
	.advance_search{
		width:100%;
		height:100%;
		cursor:pointer;
        background:url(../../../images/metadata/advance_search.png) right 8px no-repeat;
	}
</style>
</HEAD>

<BODY>
	<div id="dy_list_adjust"></div>
	<table id="table_body" class="wcm_table_layout" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td valign="top" class="wcm_list_view">
				<table class="wcm_table_layout" border=0 cellspacing=0 cellpadding=0>
				<tbody>
					<tr class="list_pageinfo">
						<td colSpan="3">
							<div class="pageinfo">
								<div class="pageinfo_left"></div>
								<div class="pageinfo_right"></div>
								<div style="float:right;" id="query_box"></div>                            
								<!--PageInfo模板-->
								<textarea id="pageinfo_template" select="PageInfo" style='display:none'>
									<table height="28" border="0" cellpadding="0" cellspacing="0">
										<tr height="28">
											<td align="left" valign="center">
												<div height="28" class="pagefilter_container" id="pagefilter_container">
													<for select="Filters.Filter">
													<span class="pagefilter {#IsDefault;#Type,,PageFilter.filterClass}" pagefilter_type="{#Type}">
														<table border="0" cellpadding="0" cellspacing="0">
															<tr height="23">
																<td class="left" width="7"></td>
																<td class="middle" nowrap="nowrap" valign="middle">
																	<a href="#" onclick="return false">{#Name}</a>
																</td>
																<td class="right" width="7"></td>
															</tr>
														</table>
													</span>
													</for>
													<span class="wcm_pointer pagefilter_more_btn" id="pagefilter_more_btn"></span>
												</div>
											</td>
										</tr>
									</table>
								</textarea>
							</div> 
						</td>
					</tr>
					<tr class="list_view">
						<td class="list_view_border"></td>
						<td valign="top">
							<DIV id='objects_grid' class="grid">
								<table class="wcm_table_layout" border=0 cellspacing=0 cellpadding=0>
									<tr style="height:29px">
										<td>
											<DIV class="grid_head" id="grid_head">
												<!--列表头模板-->
												<SPAN class="grid_head_column" onclick="Grid.toggleAllRows();" style="width:40px;">
													全选
												</SPAN>
												<SPAN class="grid_head_column" style="width:30px">
													编辑
												</SPAN>
									<!-- 视图字段表头 BEGIN -->
									<TRS_ViewFields InOutline="true">
										<TRS_Condition Condition="@TitleField" Type="INT" REFERENCE="1">
											<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="12">
												<SPAN class="grid_head_column normalColumn1 appendix_<TRS_ViewField Field='FieldType'/>" grid_dywidth="normalColumn1,1.0" style="cursor:default;">
													<TRS_ViewField Field='AnotherName' filterForHTML="true">标题字段中文名称</TRS_ViewField>
												</SPAN>
											</TRS_Condition>
											<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="12" not="true">
												<SPAN class="grid_head_column normalColumn1 appendix_<TRS_ViewField Field='FieldType'/>" grid_dywidth="normalColumn1,1.0" grid_sortby="<TRS_ViewField Field='FullFieldName'/>">
													<TRS_ViewField Field='AnotherName' filterForHTML="true">标题字段中文名称</TRS_ViewField>
												</SPAN>
											</TRS_Condition>
										</TRS_Condition>
										<TRS_Condition Condition="@TitleField" Type="INT" REFERENCE="0">
											<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="12">
												<SPAN class="grid_head_column normalColumn0 appendix_<TRS_ViewField Field='FieldType'/>" style="cursor:default;">
													<TRS_ViewField Field='AnotherName' filterForHTML="true">非标题字段中文名称</TRS_ViewField>
												</SPAN>
											</TRS_Condition>	
											<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="12" not="true">
												<SPAN class="grid_head_column normalColumn0 appendix_<TRS_ViewField Field='FieldType'/>" grid_sortby="<TRS_ViewField Field='FullFieldName'/>">
													<TRS_ViewField Field='AnotherName' filterForHTML="true">非标题字段中文名称</TRS_ViewField>
												</SPAN>
											</TRS_Condition>	
										</TRS_Condition>
									</TRS_ViewFields>	
									<!-- 视图字段表头 END -->
												<SPAN class="grid_head_column" style="width:30px;" grid_sortby="WCMCHNLDOC.docstatus">
													状态
												</SPAN>
												<SPAN class="grid_head_column" style="width:30px;border-right:0">
													删除
												</SPAN>
											</DIV>
										</td>
									</tr>
									<tr>
										<td valign="top"><div id="grid_data" class="grid_data"></div></td>
									</tr>
								</table>
							</DIV>
						</td>
						<td class="list_view_border"></td>
					</tr>
					<tr class="list_navigator">
						<td class="list_view_border"></td>
						<td><DIV id="list_navigator" class="list_navigator_div"></DIV></td>
						<td class="list_view_border"></td>
					</tr>
				</tbody>
				</table>
			</td>
		</tr>
	</table>

    <!---------------------------------------------内部模板开始---------------------------------------------//-->
	<textarea id="objects_template" select="MetaViewDatas" style='display:none'>
		<for select="MetaViewData" blankRef="divNoObjectFound">
			<DIV class="grid_row wcm_pointer" grid_rowid="{#RECID}" id="row_{#RECID}" currchnlid="{#CHNLID}" docid='{#MetaDataId}' right="{#Right}">
				<SPAN class="grid_column" style="width:40px" title="id:{#MetaDataId}">
					<input type="checkbox" class="wcm_pointer grid_checkbox" name="RecId" value="{#RECID}"/>
					<span class="grid_index">{$$INDEX}</span>
				</SPAN>
				<SPAN class="grid_column object_edit{@isDisabled(#Right, edit)}" style="width:30px" grid_function="edit"></SPAN>

	<!-- 视图数据 BEGIN -->
	<TRS_ViewFields InOutline="true">
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="1">	
			<TRS_Condition Condition="@DBType" Type="INT" REFERENCE="2005">
				<!--自定义数据类型：编辑器-->
				<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left">
				<a href="" onclick="return false;" class="editorColumn">大文本</a></SPAN>
			</TRS_Condition>
			<TRS_Condition Condition="@DBType" Type="INT" not="true" REFERENCE="2005">
				<!--自定义数据类型：非编辑器-->
				<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left">
				<a href="" onclick="return false;" title="{#<TRS_ViewField Field='FieldName'/>}">{#<TRS_ViewField Field='FieldName'/>}</a></SPAN>
			</TRS_Condition>
		</TRS_Condition>
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="2~3~4~11">			
			<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left">
			<a href="" onclick="return false;" title="{#<TRS_ViewField Field='FieldName'/>}">{#<TRS_ViewField Field='FieldName'/>}</a></SPAN>
		</TRS_Condition>
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="5">			
			<!--是否-->
			<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left" _name="<TRS_ViewField Field='FieldName'/>" _value="{#<TRS_ViewField Field='FieldName'/>}"
				 _type="radio" _items="是`1~否`0" _onlyNode="true"><a name="controler"></a></SPAN>
		</TRS_Condition>	
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="6">			
			<!--单选-->
			<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left" _name="<TRS_ViewField Field='FieldName'/>" _value="{#<TRS_ViewField Field='FieldName'/>}"
				 _type="radio" _items="<TRS_ViewField Field='EnmValue'/>" _onlyNode="true"><a name="controler"></a></SPAN>
		</TRS_Condition>	
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="7">			
			<!--下拉-->
			<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left" _name="<TRS_ViewField Field='FieldName'/>" _value="{#<TRS_ViewField Field='FieldName'/>}"
				 _type="select" _items="<TRS_ViewField Field='EnmValue'/>" _onlyNode="true"><a name="controler"></a></SPAN>
		</TRS_Condition>	
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="8">			
			<!--附件-->
			<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left" _name="<TRS_ViewField Field='FieldName'/>" _value="{#<TRS_ViewField Field='FieldName'/>}"
				 _type="appendix" _onlyNode="true" _isTitleField="<TRS_ViewField Field='TitleField'/>" style="width:80px;"><a name="controler"></a></SPAN>
		</TRS_Condition>	
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="9">			
			<!--多选-->
			<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left" _name="<TRS_ViewField Field='FieldName'/>" _value="{#<TRS_ViewField Field='FieldName'/>}"
				 _type="checkbox" _items="<TRS_ViewField Field='EnmValue'/>" _onlyNode="true"><a name="controler"></a></SPAN>
		</TRS_Condition>	
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="10">			
			<!--分类法-->
			<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left" _name="<TRS_ViewField Field='FieldName'/>" _value="{#<TRS_ViewField Field='FieldName'/>.ID}"
				 _type="classinfo" _classId="<TRS_ViewField Field='ClassId'/>" _classDesc="{#<TRS_ViewField Field='FieldName'/>.NAME}" _onlyNode="true"><a name="controler"></a></SPAN>
		</TRS_Condition>	
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="12~16">			
			<!--编辑器-->
			<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left">
			<a href="" onclick="return false;" class="editorColumn">大文本</a></SPAN>
		</TRS_Condition>	
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="15~17">			
			<!--可输入下拉列表-->
			<!--
			<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/>" align="left">
				{#<TRS_ViewField Field='FieldName'/>}
			</SPAN>
			//-->
			<script language="javascript">window.gContainDynamicSelect=true;</script>
			<SPAN class="grid_column normalColumn<TRS_ViewField Field='TitleField'/> record_modal_{#MODAL}" align="left" _name="<TRS_ViewField Field='FieldName'/>" _value="{#<TRS_ViewField Field='FieldName'/>}"
				 _type="select" _items="<%=getEnumValue("<TRS_ViewField Field='FieldName'/>")%>" _onlyNode="true"><a name="controler"></a></SPAN>
		</TRS_Condition>	
	</TRS_ViewFields>	
	<!-- 视图数据 END -->
				<SPAN class="grid_column" style="width:30px;">{#DOCSTATUS.NAME}</SPAN>
				<SPAN class="grid_column object_delete{@isDisabled(#Right, delete)}" style="width:30px;border-right:0" grid_function="delete"></SPAN>
			</DIV>
		</for>
	</textarea>

    <div id="divNoObjectFound" style="display:none;">
        <div class="no_object_found">不好意思, 没有找到符合条件的数据..-____-|||</div>
    </div>
    <!---------------------------------------------内部模板结束---------------------------------------------//-->
</BODY>
</HTML>

<%!
	int viewId = <TRS_VIEW FIELD="ViewInfoId"/>;
	IMetaDataDefCacheMgr dataDefCache = (IMetaDataDefCacheMgr)DreamFactory.createObjectById("IMetaDataDefCacheMgr");

	private String getEnumValue(String fieldName) throws WCMException{
		MetaViewField field = dataDefCache.getMetaViewField(viewId, fieldName);
		String enumValue = field.getEnmValue();

		if(enumValue == null) return "";
		enumValue = CMyString.filterForHTMLValue(CMyString.filterForHTMLValue(enumValue));
		return enumValue;
	}
%>