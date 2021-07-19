<%@ page import="com.trs.infra.util.CMyString" %>
<%
	String sContextPath = CMyString.showNull(request.getContextPath(), "/wcm");
	sContextPath = CMyString.setStrEndWith(sContextPath, '/');
	String sCorePath = sContextPath + "app/application/core/";
%>
<script src="<%=sCorePath%>core.js"></script>
<script src="<%=sCorePath%>com.trs.util/Observable.js"></script>
<script src="<%=sCorePath%>com.trs.util/CAjaxCaller.js"></script>
<script src="<%=sCorePath%>com.trs.ui/BaseComponent.js"></script>

<script src="<%=sCorePath%>wcm-adapter.js"></script>

<script src="<%=sCorePath%>ajax.js"></script>
<script src="<%=sCorePath%>wcm-ajax.js"></script>

<script src="<%=sContextPath%>app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="<%=sContextPath%>app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="<%=sContextPath%>app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />


<script src="<%=sCorePath%>com.trs.ui/text/XText.js"></script>
<link href="<%=sCorePath%>com.trs.ui/text/resource/XText.css" rel="stylesheet" type="text/css" />
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
<script src="<%=sCorePath%>com.trs.ui/editor/XEditor.js"></script>
<link href="<%=sCorePath%>com.trs.ui/editor/resource/XEditor.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/combox/XCombox.js"></script>
<link href="<%=sCorePath%>com.trs.ui/combox/resource/XCombox.css" rel="stylesheet" type="text/css" />
<script src="<%=sCorePath%>com.trs.ui/suggestion/XSuggestion.js"></script>
<link href="<%=sCorePath%>com.trs.ui/suggestion/resource/XSuggestion.css" rel="stylesheet" type="text/css" />

<script src="<%=sContextPath%>app/special/channel/ChannelTreeSelector.js"></script>
<script src="<%=sCorePath%>com.trs.ui/channeltree/XChannelTree.js"></script>
<link href="<%=sCorePath%>com.trs.ui/channeltree/resource/XChannelTree.css" rel="stylesheet" type="text/css" />

<script src="<%=sCorePath%>com.trs.ui/docselect/XDocSelect.js"></script>
<link href="<%=sCorePath%>com.trs.ui/docselect/resource/XDocSelect.css" rel="stylesheet" type="text/css" />