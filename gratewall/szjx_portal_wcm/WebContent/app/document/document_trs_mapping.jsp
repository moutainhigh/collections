<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
//4.初始化(获取数据)
	//response.setHeader("ReturnJson", "true");
//6.业务代码
	IDocumentService currDocumentService = ServiceHelper.createDocumentService();
	File[] arFile = currDocumentService.getExitedMappingFiles();
	if(arFile == null) {
		arFile = new File[0];
	}
	/*//对获取到的数据进行排序
	Arrays.sort(arFile, new Comparator(){
		public int compare(Object oArFile1,Object oArFile2){
			DateFormat df = DateFormat.getDateTimeInstance();
			Date dt1 = df.parse(oArFile1.lastModified());
			Date dt2 = df.parse(oArFile2.lastModified());
			if(dt1.getTime()>dt2.getTime()){
				return 1;
			}
			return -1;
		}
	});*/
//7.结束
	out.clear();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="document_trs_mapping.jsp.title">Document_trs_mapping</title>
	<!--css-->
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-list-common.css">
	<link rel="stylesheet" type="text/css" href="../../app/css/wcm-opers.css">
    <link rel="stylesheet" type="text/css" href="../../app/js/resource/widget.css"/>
</head>
<style type="text/css">
	/*strict*/
	.ext-strict .layout_center_container{top:0;bottom:0;}
	.ext-strict .layout_center{left:0;right:0px;}
	.ext-strict .layout_center_innercontainer{top:40px;bottom:0px;}
	.ext-strict .layout_innercenter{left:0;right:0;}
	/*ie6*/
	.ext-ie6 .layout_container{padding-top:0;padding-bottom:0;}
	.ext-ie6 .layout_center_container{padding-left:0;padding-right:2px;}
	.ext-ie6 .layout_innercontainer{padding-top:40px;padding-bottom:0px;}
	.ext-ie6 .layout_center_innercontainer{padding-left:0;padding-right:0;}
	/*all*/
	.layout_north{height:40px;}
	.layout_south{display:none;}
	.layout_east{display:none;}
	.trsmapping_edit{
		background-image:url(../images/document/trsmapping_edit.gif);
		background-repeat:no-repeat;
		background-position:center center;
		display:inline-block;
		width:16px!important;
		height:16px;
	}
	.trsmapping_delete{
		background-image:url(../images/document/docbak_delete.gif);
		background-repeat:no-repeat;
		background-position:center center;
		display:inline-block;
		width:16px!important;
		height:16px;
	}
</style>
<body>
<script src="../../app/js/easyversion/cssrender.js"></script>
<div class="layout_container">
	<div class="layout_center_container">
		<div class="layout_center">
			<div class="layout_container layout_innercontainer">
				<div class="layout_north">
					<div style="font-size:12px;float:left;">
						<span WCMAnt:param="document_trs_mapping.jsp.newMapFile">新建映射文件:</span> <input type="text" class="input_text" id="NewFileName" name="NewFileName" value="" validation="type:'string',required:true,showid:'errMsg',format:'/^[0-9a-zA-Z]{1,30}$/'">&nbsp;
						<input type="button" id="trueBtn" class="input_btn" name="" value="确定" WCMAnt:paramattr="value:document_trs_mapping.jsp.sure" >
					</div><div id="errMsg" style="float:left;padding-left:5px"></div>
				</div>
				<div class="layout_center_container layout_center_innercontainer " style="width:100%">
					<div class="layout_center layout_innercenter">

						<div class="layout_container table_grid">
							<div id='wcm_table_grid' class="layout_center_container table_grid_data" style="width:100% !important;height:300px">
								<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray" style="width:100%!important">
									<tr class="grid_head" id="grid_head">
										<td WCMAnt:param="document_trs_mapping.jsp.order" style="width:50px">序号</td>
										<td WCMAnt:param="document_trs_mapping.jsp.modify" style="width:40px">修改</td>
										<td WCMAnt:param="document_trs_mapping.jsp.filename" style="text-align:center">文件名称</td>
										<td WCMAnt:param="document_trs_mapping.jsp.modifyTime" style="width:120px">修改时间</td>
										<td WCMAnt:param="document_trs_mapping.jsp.delete" style="width:40px;border-right:0;">删除</td>
									</tr>
									<tbody class="grid_body" id="grid_body">
									<%
										File currFile = null;
										CMyDateTime myDateTime = CMyDateTime.now();
										boolean bFirst = true;
										for(int i=0; i<arFile.length; i++) {
											currFile = arFile[i];
											if(currFile == null)
												continue;
											myDateTime.setDateTime(new Date(currFile.lastModified()));
											String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
									%>
									<tr id="tr_<%=(i+1)%>" class="grid_row <%=sRowClassName%>" rowid="<%=(i+1)%>" filename="<%=currFile.getName()%>">
										<td><%=(i+1)%></td>
										<td><span class="trsmapping_edit grid_function" grid_function="trsmapping_edit">&nbsp;</span></td>
										<td style="text-align:left"><a href="#" onclick="return false;" grid_function="trsmapping_edit"><%=currFile.getName()%></a></td>
										<td><%=myDateTime.toString("MM-dd HH:mm")%></td>
										<td><span class="trsmapping_delete grid_function" grid_function="trsmapping_delete">&nbsp;</span></td>
									</tr>
									<%
										}
									%>
									</tbody>
									<tbody id="divNoObjectFound" style="display:none;">
										<tr><td colspan="5" class="no_object_found" WCMAnt:param="template_list.head.no_object_found">不好意思, 没有找到符合条件的对象!</td></tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div><script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/easyversion/list.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/document.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../js/source/wcmlib/pagecontext/PageContext.js"></script>
<script src="../js/source/wcmlib/pagecontext/PageNav.js"></script>
<script src="../js/source/wcmlib/pagecontext/PageGrid.js"></script>
<script src="../js/source/wcmlib/pagecontext/PageLiterator.js"></script>
<script src="../../app/js/source/wcmlib/pagecontext/AbsListInner.js"></script>
<script src="../js/source/wcmlib/pagecontext/BubblePannel.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<!-- CarshBoard Outer End -->
<!--validator-->
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<!--page js-->
<script src="document_trs_mapping_list.js"></script>
<script language="javascript">
<!--
	try{
		wcm.Grid.init({
			RecordNum : <%=arFile.length%>
		});
	}catch(err){
		//Just skip it.
	}	//PageContext.LoadPage();
	//Validation的附加部分,主要用于处理多语种问题
	var m_arrValidations = [{
			renderTo : "NewFileName",
			desc : wcm.LANG.DOCUMENT_PROCESS_156 || '映射文件名',
			message :  wcm.LANG.DOCUMENT_PROCESS_157 || '映射文件名只支持字母和数字,长度为1-30.',
			warning: wcm.LANG.DOCUMENT_PROCESS_157 || '映射文件名只支持字母和数字,长度为1-30.'
		}
	];
	Event.observe(window,'load',function(){
		ValidationHelper.registerValidations(m_arrValidations);
		ValidationHelper.initValidation();
	});
//-->
</script>
</body>
</html>