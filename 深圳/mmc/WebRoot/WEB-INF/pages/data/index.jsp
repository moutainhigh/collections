<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%@include file="/pub/pub.jsp"%>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
			<img src="statics/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	<div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(statics/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
		<span style="float:right; padding-right:20px;" class="head">
			欢迎叶乃榕登录
			<a href="#" id="editpass">修改密码</a>
			<a href="#" id="loginOut">安全退出</a>
		</span>
		<span style="padding-left:10px; font-size: 16px; ">
			<img src="statics/images/blocks.gif" width="20" height="20" align="absmiddle" />
			数据报表检查平台
		</span>
	</div>
	<div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
		<div class="footer">By 叶乃榕 9442478</div>
	</div>
	<div region="west" hide="true" split="true" title="导航菜单" style="width:350px;" id="west">
		<div id="nav" class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->

		</div>

	</div>
	<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; ">
				<p>登记报表生成之前需要检查数据，sql语句存放于张蓉\交接\登记报表数据检查.sql 文件中，每月25日执行，与上月数据对比，如有不知情的差异，应为错误数据，及时更正</p>
			</div>
		</div>
	</div>


	<!--修改密码窗口-->
	<div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" maximizable="false" icon="icon-save" style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<table cellpadding=3>
					<tr>
						<td>新密码：</td>
						<td>
							<input id="txtNewPass" type="Password" class="txt01" />
						</td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td>
							<input id="txtRePass" type="Password" class="txt01" />
						</td>
					</tr>
				</table>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"> 确定</a>
				<a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
	<script type="text/javascript" src='statics/js/outlook2.js'></script>

	<script type="text/javascript">
		var _menus = {
			"menus" : [ {
				"menuid" : "1",
				"icon" : "icon-sys",
				"menuname" : "数据检查",
				"menus" : [ {
					"menuid" : "01",
					"menuname" : "企业管辖区域为空数量01",
					"icon" : "icon-add",
					"url" : "data01"
				}, {
					"menuid" : "02",
					"menuname" : "个体管辖区域为空数量02",
					"icon" : "icon-users",
					"url" : "data02"
				}, {
					"menuid" : "03",
					"menuname" : "不存在的企业类型数据03【列表】",
					"icon" : "icon-role",
					"url" : "data03"
				}, {
					"menuid" : "04",
					"menuname" : "企业行业类型为空数量04",
					"icon" : "icon-set",
					"url" : "data04"
				}, {
					"menuid" : "05",
					"menuname" : "个体行业类型为空数量05",
					"icon" : "icon-database",
					"url" : "data05"
				}, {
					"menuid" : "06",
					"menuname" : "企业注册资本排序06【列表】",
					"icon" : "icon-log",
					"url" : "data06"
				}, {
					"menuid" : "07",
					"menuname" : "企业中状态为吊销的户数07",
					"icon" : "icon-set",
					"url" : "data07"
				}, {
					"menuid" : "08",
					"menuname" : "企业中状态为吊销并且吊销日期不为空的户数08",
					"database" : "icon-log",
					"url" : "data08"
				}, {
					"menuid" : "09",
					"menuname" : "个体中状态为吊销的户数09",
					"icon" : "icon-database",
					"url" : "data09"
				}, {
					"menuid" : "10",
					"menuname" : "个体中状态为吊销并且吊销日期不为空的户数10",
					"icon" : "icon-log",
					"url" : "data10"
				}, {
					"menuid" : "11",
					"menuname" : "企业期末实有户数11",
					"icon" : "icon-log",
					"url" : "data11"
				}, {
					"menuid" : "12",
					"menuname" : "企业本期登记户数12",
					"icon" : "icon-set",
					"url" : "data12"
				}, {
					"menuid" : "13",
					"menuname" : "企业本期吊销户数13",
					"icon" : "icon-database",
					"url" : "data13"
				}, {
					"menuid" : "14",
					"menuname" : "企业本期注销户数14",
					"icon" : "icon-add",
					"url" : "data14"
				}, {
					"menuid" : "15",
					"menuname" : "个体期末实有户数15",
					"icon" : "icon-edit",
					"url" : "data15"
				}, {
					"menuid" : "16",
					"menuname" : "个体本期登记户数16",
					"icon" : "icon-users",
					"url" : "data16"
				}, {
					"menuid" : "17",
					"menuname" : "个体本期吊销户数17",
					"icon" : "icon-edit",
					"url" : "data17"
				}, {
					"menuid" : "18",
					"menuname" : "个体本期注销户数18",
					"icon" : "icon-database",
					"url" : "data18"
				}, {
					"menuid" : "19",
					"menuname" : "本期登记纯内资企业户数19",
					"icon" : "icon-role",
					"url" : "data19"
				},
				{
					"menuid" : "20",
					"menuname" : "6100企业 20",
					"icon" : "icon-role",
					"url" : "data20"
				},
				{
					"menuid" : "21",
					"menuname" : "6299企业 21",
					"icon" : "icon-role",
					"url" : "data21"
				} ]
			} ]
		};
		//设置登录窗口
		function openPwd() {
			$('#w').window({
				title : '修改密码',
				width : 300,
				modal : true,
				shadow : true,
				closed : true,
				height : 160,
				resizable : false
			});
		}
		//关闭登录窗口
		function closePwd() {
			$('#w').window('close');
		}

		//修改密码
		function serverLogin() {
			var $newpass = $('#txtNewPass');
			var $rePass = $('#txtRePass');

			if ($newpass.val() == '') {
				msgShow('系统提示', '请输入密码！', 'warning');
				return false;
			}
			if ($rePass.val() == '') {
				msgShow('系统提示', '请在一次输入密码！', 'warning');
				return false;
			}

			if ($newpass.val() != $rePass.val()) {
				msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
				return false;
			}

			$.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
				msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
				$newpass.val('');
				$rePass.val('');
				close();
			})

		}

		$(function() {

			openPwd();

			$('#editpass').click(function() {
				$('#w').window('open');
			});

			$('#btnEp').click(function() {
				serverLogin();
			})

			$('#btnCancel').click(function() {
				closePwd();
			})

			$('#loginOut').click(function() {
				$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

					if (r) {
						location.href = '/ajax/loginout.ashx';
					}
				});
			})
		});
	</script>
</body>
</html>