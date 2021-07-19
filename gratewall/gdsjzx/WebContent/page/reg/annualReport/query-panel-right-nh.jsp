<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>个体年度报表展示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/detial.css" />
<script type="text/javascript">
$(function() {
	$("#formpanel").formpanel('option', 'readonly', true);
		//行间间距缩小
		//$('.jazz-textfield-comp').css("height","8px");
  		//获取传递过来的参数，进行初始化请求
  		if(parent.entityNo!=null){
  			var year = getUrlParam("year");
 			queryHistory(parent.entityNo,parent.priPid,parent.sourceflag,year);
  		} 
 	});
	function queryHistory(entityNo,priPid,sourceflag,year){
		$('#formpanel .jazz-panel-content').loading();
		$("#formpanel").formpanel('option', 'dataurl',rootPath+
		'/report/detail.do?flag='+entityNo+'&priPid='+priPid+'&sourceflag='+sourceflag+'&year='+year);
		
		
		
		$("#formpanel").formpanel('reload', "null", function(){
	        $('#formpanel .jazz-panel-content').loading('hide');
	        //选取面板上的字号名称：$("").hiddenfield("getValue"));
	        var ancheyear = $('#ancheyear').textfield("getValue");
	        $('#formpanel div:first > div span:first').html("<a style='font-size:19px;'>年度报告:"+$('#ancheYear').textfield("getValue")+"&nbsp&nbsp&nbsp</a><a style='float:right;font-size:19px;'>主体名称："+$('#entname').textfield("getValue")+"&nbsp&nbsp&nbsp</a>");
	        var ancheyear = $('#ancheyear').textfield("getValue");
	         $('div[name="regcap"]').textfield('setValue', regcap+"(万元)人民币元 ");
		});
	}

	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }
     
     
</script>
</head>

<body>
<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" 
	titledisplay="true" width="98%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*'], border: true}" height="auto">
<!-- <div style="border:0px solid red;width:100%;font-weight:bold;color:#327BB9;margin:1%;margin-left:2%;">基本信息</div>
<div></div>
<hr style="color:#FCFCFC;"><hr  style="color:#FCFCFC;margin-left:-2px;"> -->
	<!-- 企业报送基本信息表 -->
	<div name='ancheid' vtype="hiddenfield" label="年报ID" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='pripid' vtype="hiddenfield" label="主体身份代码" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div id="entname" name='entname' vtype="textfield" label="企业(机构)名称" labelAlign="right" labelwidth='150px'  colspan="2" width="850" height='40px'></div>
	<div id="regno" name='regno' vtype="textfield" label="注册号" labelAlign="right" labelwidth='150px'  width="410" height='40px'></div>
	<div name='enttype' vtype="textfield" label="企业类型" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='uniscid' vtype="textfield" label="统一社会信用代码" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div id="ancheyear" name='ancheyear' vtype="textfield" label="年报提交年份" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='anchedate' vtype="textfield" label="年报时间" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='busst' vtype="textfield" label="经营状态" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='busst_cn' vtype="textfield" label="经营状态(中文)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='empnumdis' vtype="hiddenfield" label="是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='empnum' vtype="textfield" label="从业人数" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='antype' vtype="textfield" label="企业年报类别" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='colgranum' vtype="textfield" label="高校毕业生人数(经营者)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='colemplnum' vtype="textfield" label="高校毕业生人数(雇工)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='retsolnum' vtype="textfield" label="退役士兵人数(经营者)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='retemplnum' vtype="textfield" label="退役士兵人数(雇工)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='dispernum' vtype="textfield" label="残疾人人数(经营者)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='disemplnum' vtype="textfield" label="残疾人人数(雇工)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='unenum' vtype="textfield" label="再就业人数(经营者)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='uneemplnum' vtype="textfield" label="再就业人数(雇工)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='dependententname' vtype="hiddenfield" label="隶属企业名称" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='assgro' vtype="hiddenfield" label="资产总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='assgrodis' vtype="hiddenfield" label="资产总额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='liagro' vtype="hiddenfield" label="负债总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='liagrodis' vtype="hiddenfield" label="负债总额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='vendinc' vtype="hiddenfield" label="营业总收入" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='vendincdis' vtype="hiddenfield" label="营业总收入是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='maibusinc' vtype="hiddenfield" label="主营业务收入" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='maibusincdis' vtype="hiddenfield" label="主营业务收入是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div><div name='relationsisshow' vtype="textfield" label="隶属关系是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='progro' vtype="hiddenfield" label="利润总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='progrodis' vtype="hiddenfield" label="利润总额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div><div name='relationsisshow' vtype="textfield" label="隶属关系是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='netinc' vtype="hiddenfield" label="净利润" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='netincdis' vtype="hiddenfield" label="净利润是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div><div name='relationsisshow' vtype="textfield" label="隶属关系是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='ratgro' vtype="hiddenfield" label="纳税总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='ratgrodis' vtype="hiddenfield" label="纳税总额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div><div name='relationsisshow' vtype="textfield" label="隶属关系是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='totequ' vtype="hiddenfield" label="所有者权益合计" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='totequdis' vtype="hiddenfield" label="所有者权益合计是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div><div name='relationsisshow' vtype="textfield" label="隶属关系是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='numparm' vtype="textfield" label="党员（预备党员）人数" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='parins' vtype="textfield" label="党组织建制" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='parins_cn' vtype="textfield" label="党组织建制（中文名称）" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='resparmsign' vtype="textfield" label="法定代表人是否党员" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='resparsecsign' vtype="textfield" label="法定代表人是否党组织书记" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='lastupdatetime' vtype="textfield" label="最后一次修改时间" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	

	
	<!-- 农民专业合作社年度报备表 -->
	<div name='coopid' vtype="hiddenfield" label="coopid" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='ancheid' vtype="hiddenfield" label="ancheid" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='farspeartname' vtype="textfield" label="合作社名称" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='totalamount' vtype="textfield" label="成员出资总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='memnum' vtype="textfield" label="成员总人数" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='annnewmemnum' vtype="textfield" label="本年度新增人数" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='annredmemnum' vtype="textfield" label="本年度退出人数" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='levelf' vtype="textfield" label="获得示范合作社等级情况" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='certification' vtype="textfield" label="农产品认证情况" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='priyeasales' vtype="textfield" label="上年度销售额或营业收入" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='priyeasalesdis' vtype="hiddenfield" label="销售额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='priyeasub' vtype="textfield" label="上年度获得政府补助资金" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='priyeasubdis' vtype="hiddenfield" label="补助资金是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='priyeaprofit' vtype="textfield" label="上年度盈余总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='priyeaprofitdis' vtype="textfield" label="上年度盈余总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	
	<div name='priyealoan' vtype="textfield" label="上年度金融贷款" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='priyealoandis' vtype="hiddenfield" label="金融贷款是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='regcapitalissame' vtype="textfield" label="不知道啥" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='farnum' vtype="textfield" label="农民人数" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	
	<!-- 分支机构 -->
	<div name='addr' vtype="textfield" label="通讯地址" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='postalcode' vtype="textfield" label="邮政编码" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='tel' vtype="textfield" label="联系电话" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='email' vtype="textfield" label="电子邮箱" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
																															
	<div name='fzjg' vtype="textfield" label="分支机构|注册号" labelAlign="right" labelwidth='150px'  width="1000" colspan="2" height='40px'></div>
	
</div>
</br>
</body>
</html>
