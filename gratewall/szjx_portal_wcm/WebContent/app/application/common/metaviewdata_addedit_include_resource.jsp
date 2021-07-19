<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%
	String sContextPath = CMyString.showNull(request.getContextPath(), "/wcm");
	sContextPath = CMyString.setStrEndWith(sContextPath, '/');
	String sCorePath = sContextPath + "app/application/core/";
	int nDrafrStatus = Status.STATUS_ID_DRAFT;
%>
<script language="javascript">
<!--
	var m_nDraftStatus = <%=nDrafrStatus%>;
//-->
</script>
<script src="<%=sCorePath%>core.js"></script>
<script src="<%=sCorePath%>com.trs.util/Observable.js"></script>
<script src="<%=sCorePath%>com.trs.util/CAjaxCaller.js"></script>
<script src="<%=sCorePath%>com.trs.ui/BaseComponent.js"></script>

<script src="<%=sCorePath%>wcm-adapter.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/core/CMSObj.js"></script>

<script src="<%=sCorePath%>ajax.js"></script>
<script src="<%=sCorePath%>wcm-ajax.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/core/LockUtil.js"></script>
<!--wcm-dialog start-->
<link href="<%=sContextPath%>app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<link href="<%=sContextPath%>app/css/wcm-common.css" rel="stylesheet" type="text/css" >
<script src="<%=sContextPath%>app/js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/Observable.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/Component.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>

<script src="<%=sContextPath%>app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="<%=sContextPath%>app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />

<script src="<%=sContextPath%>app/js/source/wcmlib/components/ProcessBar.js"></script>

<script src="<%=sCorePath%>com.trs.ui/text/XText.js"></script>
<link href="<%=sCorePath%>com.trs.ui/text/resource/XText.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/otherfield/XOtherField.js"></script>
<link href="<%=sCorePath%>com.trs.ui/otherfield/resource/XOtherField.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/docappendixes/XDocAppendixes.js"></script>
<link href="<%=sCorePath%>com.trs.ui/docappendixes/resource/XDocAppendixes.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/imgappendixes/XImgAppendixes.js"></script>
<link href="<%=sCorePath%>com.trs.ui/imgappendixes/resource/XImgAppendix.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/docmultiappendixes/XDocMultiAppendixes.js"></script>
<link href="<%=sCorePath%>com.trs.ui/docmultiappendixes/resource/XDocMultiAppendixes.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/doclinkappendixes/XDocLinkAppendixes.js"></script>
<link href="<%=sCorePath%>com.trs.ui/doclinkappendixes/resource/XDocLinkAppendixes.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/process/XProcess.js"></script>
<link href="<%=sCorePath%>com.trs.ui/process/resource/XProcess.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/preview/XPreview.js"></script>
<link href="<%=sCorePath%>com.trs.ui/preview/resource/XPreview.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/radio/XRadio.js"></script>
<link href="<%=sCorePath%>com.trs.ui/radio/resource/XRadio.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/select/XSelect.js"></script>
<link href="<%=sCorePath%>com.trs.ui/select/resource/XSelect.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/appendix/YUIConnection.js"></script>
<script src="<%=sCorePath%>com.trs.ui/appendix/XAppendix.js"></script>
<link href="<%=sCorePath%>com.trs.ui/appendix/resource/XAppendix.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/checkbox/XCheckbox.js"></script>
<link href="<%=sCorePath%>com.trs.ui/checkbox/resource/XCheckbox.css" rel="stylesheet" type="text/css" />
<script src="<%=sContextPath%>app/classinfo/ClassInfoSelector.js"></script>
<script src="<%=sCorePath%>com.trs.ui/classinfo/XClassInfo.js"></script>
<link href="<%=sCorePath%>com.trs.ui/classinfo/resource/XClassInfo.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/ckeditor/XEditor.js"></script>
<link href="<%=sCorePath%>com.trs.ui/ckeditor/resource/XEditor.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/relateddoc/XRelatedDoc.js"></script>
<link href="<%=sCorePath%>com.trs.ui/relateddoc/resource/XRelatedDoc.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/combox/XCombox.js"></script>
<link href="<%=sCorePath%>com.trs.ui/combox/resource/XCombox.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/suggestion/XSuggestion.js"></script>
<link href="<%=sCorePath%>com.trs.ui/suggestion/resource/XSuggestion.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/calendar/XCalendar.js"></script>
<link href="<%=sCorePath%>com.trs.ui/calendar/resource/XCalendar.css" rel="stylesheet" type="text/css" />
<script src="<%=sContextPath%>app/js/easyversion/calendar3.js"></script>
<link href="<%=sContextPath%>app/js/easyversion/resource/calendar.css" rel="stylesheet" type="text/css" />

<script src="<%=sCorePath%>com.trs.ui/appendix/XAppendixExt.js"></script>
<link href="<%=sCorePath%>com.trs.ui/appendix/resource/XAppendixExt.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/link/XLink.js"></script>
<link href="<%=sCorePath%>com.trs.ui/link/resource/XLink.css" rel="stylesheet" type="text/css" />

<script src="<%=sContextPath%>app/template/TemplateSelector.js"></script>
<script src="<%=sContextPath%>app/application/common/TemplateSelectHandler.js" type="text/javascript"></script>
<script src="<%=sContextPath%>app/application/common/TopSetHandler.js" type="text/javascript"></script>

<script language="javascript" src="<%=sContextPath%>app/channel/ChannelTreeSelector.js" type="text/javascript"></script>
<script language="javascript" src="<%=sCorePath%>com.trs.ui/quote/XQuote.js" type="text/javascript"></script>
<link href="<%=sCorePath%>com.trs.ui/quote/resource/XQuote.css" rel="stylesheet" type="text/css" />	

<script src="<%=sContextPath%>app/application/common/metaviewdata_addedit.js"></script>
<link href="<%=sContextPath%>app/application/common/metaviewdata_addedit.css" rel="stylesheet" type="text/css" />