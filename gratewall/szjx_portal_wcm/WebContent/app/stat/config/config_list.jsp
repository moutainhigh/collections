<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>

<%@include file="../../include/public_server.jsp"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict/dtd">
<html>
 <head>
  <title WCMAnt:param="config_list.title"> 用户奖励统计表 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="Author" content="CH">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script language="javascript" src="../../js/easyversion/lightbase.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/easyversion/extrender.js" type="text/javascript"></script>
  <script language="javascript" src="../../js/easyversion/elementmore.js" type="text/javascript"></script>
  <link href="config.css" rel="stylesheet" type="text/css" />
  <script language="javascript">
  <!--
	function selected(dom){
		// 获取已经选中的元素，并删除选中状态
		var els = document.getElementsByClassName("selected");
		for(var i=0;i<els.length;i++){
			Element.removeClassName(els[i],"selected");
		}

		// 添加选中样式
		Element.addClassName(dom,"selected");
	}
  //-->
  </script>
 </head>

 <body>
  <div class="container">
	<div class="content">
		<div class="thumb_item" title="双击进入奖励规则设置页面" ondblclick="window.open('../../../bonusrule/bonusrule_list.jsp','_blank')" onclick="selected(this);" WCMAnt:paramattr="title:config_list.doubleClick.rule">
			<div class="thumb" style="background-image:url(../images/bonusrule.gif);">
				
			</div>
			<div class="attrs" WCMAnt:param="config_list.award.rule">
				奖励规则设置
			</div>
		</div>
		<div class="thumb_item" title="双击进入栏目组织关联设置页面" ondblclick="window.open('../../../stat/chnldept_list.jsp','_blank')"  onclick="selected(this);" WCMAnt:paramattr="title:config_list.doubleClick.channelgroupbindsetpage">
			<div class="thumb" style="background-image:url(../images/chnldept.gif);" >
				
			</div>
			<div class="attrs" WCMAnt:param="config_list.set">
				栏目组织关联设置
			</div>
		</div>
		<div class="thumb_item" title="双击进入二级域名设置页面" ondblclick="window.open('../../../stat/hostobjdomain_list.jsp','_blank')" onclick="selected(this);" WCMAnt:paramattr="title:config_list.doubleClickseconddomainsetpage">
			<div class="thumb"  style="background-image:url(../images/hostobjdomain.gif);">
				
			</div>
			<div class="attrs" title="二级域名设置" WCMAnt:paramattr="title:config_list.field.setattr" WCMAnt:param="config_list.field.set">
				二级域名设置
			</div>
		</div>
	</div>
  </div>
 </body>
</html>