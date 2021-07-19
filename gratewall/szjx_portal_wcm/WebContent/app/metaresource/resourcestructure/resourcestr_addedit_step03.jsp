<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../include/error_for_dialog.jsp"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>新建资源结构：步骤三</title>
	<script src="../js/jquery.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/jquery-wcm-ui-extend.js"></script>
	<script src="../js/jquery-wcm-extend.js"></script>
	<link href="resourcestr_addedit_step03.css" rel="stylesheet" type="text/css" />
	<script language="javascript">
	<!--
		var PageContext = window.PageContext || {};
		$.extend(PageContext,{
			"pre_step" : function(){
				window.location.href="resourcestr_addedit_step02.jsp?ViewId="+$.getParameter("ViewId");
			},
			"ok" : function(){
				
			}
		});
	//-->
	</script>
</head>

<body>

	<div class="container">
		<div class="header"><div class="desc">步骤三  资源录入界面设计</div></div>
		
		<div class="content">
		<center>
			<div class="main">
				<!--顶部内容-->
				<div class="topBox">
					<div class="leftTopBox">
						选择几列布局：
					</div>
					<div class="rightTopBox">
						<div class="defineButton">
							创建自定义布局
						</div>
					</div>
				</div>
				<!--中间内容-->
				<div class="layoutRow">
					<div class="leftLayoutCell">
						<div >
							<img src="../images/onerowlayout.gif" border=0 alt="一列布局" />
						</div>
						<div>
							<input type="radio" name="layout" value="" id="oneLayout" checked="checked"/>
							<label for="oneLayout">[ 一列布局 ]</label>
						</div>
					</div>
					<div class="rightLayoutCell">
						<div >
							<img src="../images/tworowlayout.gif" border=0 alt="二列布局" />
						</div>
						<div>
							<input type="radio" name="layout" value="" id="twoLayout"/>
							<label for="twoLayout">[ 二列布局 ]</label>
						</div>
					</div>
				</div>
				<!--底部内容-->
				<div class="layoutRow">
					<div class="leftLayoutCell">
						<div >
							<img src="../images/threerowlayout.gif" border=0 alt="三列布局" />
						</div>
						<div>
							<input type="radio" name="layout" value="" id="threeLayout"/>
							<label for="threeLayout">[ 三列布局 ]</label>
						</div>
					</div>
					<div class="rightLayoutCell">
						<div >
							<img src="../images/fourrowlayout.gif" border=0 alt="四列布局" />
						</div>
						<div>
							<input type="radio" name="layout" value="" id="fourLayout"/>
							<label for="fourLayout">[ 四列布局 ]</label>
						</div>
					</div>
				</div>
			</div>
			</center>
		</div>
		
		<div class="footer">
				<div class="btn-box">
					<button name="" id="" class="btn" onclick="PageContext.pre_step();">上一步</button>
					<button name="" id="" class="btn" onclick="PageContext.ok();">完  成</button>
					<button name="" id="" class="btn" onclick="">取  消</button>
				</div>
		</div>
	</div>

</body>
</html>