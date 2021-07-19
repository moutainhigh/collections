<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>

<%@include file="../../include/public_server.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.1//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title WCMAnt:param="adintrs-demo.jsp.title"> 广告选择demo </title>
	<script src="../../js/easyversion/lightbase.js"></script>
	<script src="../../js/easyversion/extrender.js"></script>
	<script src="../js/adapter4Top.js"></script>

	<script language="javascript" src="../../template/trsad_config.jsp" type="text/javascript"></script>
	<script language="javascript">
	<!--
		var bEnableAdInTrs = window.trsad_config && window.trsad_config['enable']==true;

		function loadTRSAdOption(){
			if(window.bEnableAdInTrs) {
				try{
					var strsAdCon = trsad_config['root_path'];
					if(strsAdCon==null)return;
					var nStrLen = strsAdCon.length;
					if(strsAdCon.charAt(nStrLen-1)!='/'){
						strsAdCon = strsAdCon + '/';
					}

					var sWcmurl = "http://"+window.location.host+"/wcm/app/template/adintrs_intoTemp.jsp";

					// 打开广告位选择页面
					wcm.CrashBoarder.get('adintrs_slt').show({
						width:'600px',
						height:'400px',
						title : wcm.LANG.TEMPLATE_47 ||'选择广告',
						src : 'document/dialog_window.html',
						params : {
							URL:7,//要跳转到广告位选择页面的URL序号值
							WCMURL:sWcmurl, //广告位的处理页面
							AdLocationIds:'63,66'//页面中已经添加的广告位的Id
						},
						callback : function(params){
							// 广告位脚本的js(使用逗号分隔开)
							var sResult = params.result;
							// 广告位id(多个Id使用逗号分隔开)
							var sAdLocationIds = params.adLocationIds;

							//广告对应的js信息
							alert("广告脚本的js序列："+sResult);

							//将返回的广告位地址中的变量做替换操作
							sResult = sResult.replace(/\$\{admanage_root_path\}/g, strsAdCon + "adintrs/");
						}
					});
				}catch(err){
					//TODO
					alert('插入广告位出错:' + err.message);
				}
			}
		}
	//-->
	</script>
</head>

<body>
<button onclick="loadTRSAdOption();"  WCMAnt:param="adintrs-demo.jsp.select_ad">选择广告</button>
</body>
</html>