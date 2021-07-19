<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge;chrome=1" />
<title>北京工商网上登记申请平台（全流程电子化）</title>
<link rel="stylesheet" href="${(other.separator)!}static/css/jazz-all.css" />
<link rel="stylesheet" href="${(other.separator)!}static/css/common.css" />
<link rel="stylesheet" href="${(other.separator)!}static/css/addTitleButton.css" />
</head>
<body>
<!-- 构建页面==开始 -->
<#if widgetList?exists && (widgetList?size>0)>
<#list widgetList as widget>
${widget!}
</#list>
</#if>
<!-- 构建页面==结束 -->
<script type="text/javascript" src="${(other.separator)!}static/js/config.js"></script>
<script type="text/javascript" data-main="torch/${(fbo.pagefileName)!}Torch.js" src="${(other.separator)!}static/lib/require.js"></script>
</body>
</html>