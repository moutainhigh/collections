<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>年度报表展示</title>
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
	    /*      var regcap =$('div[name="regcap"]').textfield("getValue");
	         $('div[name="regcap"]').textfield('setValue', regcap+"(万元)人民币元 "); */
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
	<div name='colgranum' vtype="textfield" label="高校毕业人数(经营者)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='colemplnum' vtype="textfield" label="高校毕业人数(雇工)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='retsolnum' vtype="textfield" label="退役士兵人数(经营者)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='retemplnum' vtype="textfield" label="退役士兵人数(雇工)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='dispernum' vtype="textfield" label="残疾人人数(经营者)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='disemplnum' vtype="textfield" label="残疾人人数(雇工)" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='unenum' vtype="textfield" label="再就业人数(经营者)" labelAlign="right" labelwidth='190px'  width="410"  height='40px'></div>
	<div name='uneemplnum' vtype="textfield" label="再就业人数(雇工)" labelAlign="right" labelwidth='170px'  width="410"  height='40px'></div>
	<div name='dependententname' vtype="hiddenfield" label="隶属企业名称" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='assgro' vtype="hiddenfield" label="资产总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='assgrodis' vtype="hiddenfield" label="资产总额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='liagro' vtype="hiddenfield" label="负债总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='liagrodis' vtype="hiddenfield" label="负债总额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='vendinc' vtype="hiddenfield" label="营业总收入" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='vendincdis' vtype="hiddenfield" label="营业总收入是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='maibusinc' vtype="hiddenfield" label="主营业务收入" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='maibusincdis' vtype="hiddenfield" label="主营业务收入是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='relationsisshow' vtype="hiddenfield" label="隶属关系是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='progro' vtype="hiddenfield" label="利润总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='progrodis' vtype="hiddenfield" label="利润总额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='netinc' vtype="hiddenfield" label="净利润" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='netincdis' vtype="hiddenfield" label="净利润是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='ratgro' vtype="hiddenfield" label="纳税总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='ratgrodis' vtype="hiddenfield" label="纳税总额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='totequ' vtype="textfield" label="所有者权益合计" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='totequdis' vtype="textfield" label="所有者权益合计是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='numparm' vtype="textfield" label="党员（预备党员）人数" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='parins' vtype="textfield" label="党组织建制" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='parins_cn' vtype="textfield" label="党组织建制（中文名称）" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='resparmsign' vtype="textfield" label="法定代表人是否党员" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='resparsecsign' vtype="textfield" label="法定代表人是否党组织书记" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='lastupdatetime' vtype="textfield" label="最后一次修改时间" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	


	<!-- 个体工商年度报备表/农民专业合作社年度报备表 -->
	<div name='individualid' vtype="hiddenfield" label="主键ID" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='name' vtype="textfield" label="经营者姓名" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='ancheid' vtype="textfield" label="基本信息主键" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='graduatesisshow' vtype="hiddenfield" label="高校毕业生人数是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='soldiersisshow' vtype="hiddenfield" label="退役士兵人数是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>	
	<div name='disabilityisshow' vtype="hiddenfield" label="残疾人人数是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>	
	<div name='unemploymentisshow' vtype="hiddenfield" label="下岗失业人数是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>	
	<div name='fundam' vtype="textfield" label="资金总额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='outputamount' vtype="textfield" label="产值" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='outputisshow' vtype="hiddenfield" label="产值是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='salesIsshow' vtype="hiddenfield" label="销售额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='retailamount' vtype="textfield" label="社会消费品零售额" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='retailisshow' vtype="hiddenfield" label="零售额是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='markupmode' vtype="textfield" label="组成形式" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='reportisshow' vtype="hiddenfield" label="年报是否公示" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='entnameissame' vtype="hiddenfield" label="企业名称与实际情况是否一致" labelAlign="right" labelwidth='190px'  width="410"  height='40px'></div>
	<div name='userissame' vtype="hiddenfield" label="经营者姓名与实际情况是否一致" labelAlign="right" labelwidth='190px'  width="410"  height='40px'></div>
	<div name='markupmodeissame' vtype="hiddenfield" label="组成形式与实际情况是否一致" labelAlign="right" labelwidth='190px'  width="410"  height='40px'></div>
	<div name='scopeissame' vtype="hiddenfield" label="经营范围与实际情况是否一致" labelAlign="right" labelwidth='190px'  width="410"  height='40px'></div>
	<div name='addressissame' vtype="hiddenfield" label="经营场所与实际情况是否一致" labelAlign="right" labelwidth='190px'  width="410"  height='40px'></div>
	
		<div name='addr' vtype="textfield" label="通讯地址" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='postalcode' vtype="textfield" label="邮政编码" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='tel' vtype="textfield" label="联系电话" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	<div name='email' vtype="textfield" label="电子邮箱" labelAlign="right" labelwidth='150px'  width="410"  height='40px'></div>
	
</div>
</br>
</body>
</html>
