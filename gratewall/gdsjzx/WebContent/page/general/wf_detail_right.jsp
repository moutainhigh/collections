<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>市场主体信息展示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("#formpanel").formpanel('option', 'readonly', true);
	$("#formpanel2").formpanel('option', 'readonly', true);	
		//行间间距缩小
	$('.jazz-textfield-comp').css("height","8px");

  		//获取传递过来的参数，进行初始化请求
  		if(parent.entityNo!=null){
 			queryHistory(parent.entityNo,parent.priPid);
  		} 
 	});
	function queryHistory(entityNo,priPid){
		$('#formpanel .jazz-panel-content').loading();
		$("#formpanel").formpanel('option', 'dataurl',rootPath+
		'/reg/detail.do?flag='+entityNo+'&priPid='+priPid);
		
		$("#formpanel").formpanel('reload', "null", function(){
	        $('#formpanel .jazz-panel-content').loading('hide');
	        //选取面板上的字号名称：$("").hiddenfield("getValue"));
			$('#formpanel div:first > div span:first').html("<a style='font-size:19px;'>主体名称："+$('#entname').hiddenfield("getValue")+"&nbsp&nbsp&nbsp</a><a style='float:right;font-size:19px;'>注册号:"+$('#regno').hiddenfield("getValue")+"&nbsp&nbsp&nbsp</a>");
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
<div height="2px" vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*'], border: false}" height="auto"
	>
<div height="2px" style="border:0px solid red;width:100%;font-weight:bold;color:#327BB9;margin:1%;margin-left:2%;">基本信息</div>
<div height="2px"></div>
<hr style="color:#FCFCFC;"><hr  style="color:#FCFCFC;margin-left:-2px;">
	<div id="regno" name='regno' vtype="hiddenfield" label="注册号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div id="entname" name='entname' vtype="hiddenfield" label="企业(机构)名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	
	<div name='oldregno' vtype="textfield" label="广告名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='enttype' vtype="textfield" label="广告编号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='servicestate' vtype="textfield" label="发布媒体" labelAlign="right" labelwidth='100px'  width="410"></div>
	<div name='regcap' vtype="textfield" label="广告主名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryphy' vtype="textfield" label="广告经营者名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryco' vtype="textfield" label="广告主类型" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='estdate' vtype="textfield" label="广告经营者类型" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='email' vtype="textfield" label="商品(服务)名称" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='abuitemco' vtype="textfield" label="商品(服务)类别" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='regorg' vtype="textfield" label="商品(服务)大类" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='postalcode' vtype="textfield" label="商品(服务)中类" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='tel' vtype="textfield" label="商品(服务)小类" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='cbuitem' vtype="textfield" label="广告相关批号" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opscope' vtype="textfield" label="发布时间" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='localadm' vtype="textfield" label="登记时间" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opfrom' vtype="textfield" label="管辖单位" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='opto' vtype="textfield" label="登记人姓名" labelAlign="right" labelwidth='102px'  width="410"></div>
</div>




<div vtype="formpanel" id="formpanel2" displayrows="1" name="formpanel2"
	titledisplay="true" width="100%" height="40%" layout="table"  showborder="false" 
	layoutconfig="{cols:2, columnwidth: ['50%','*'], border: false}" >

<div height="2px" style="width:100%;font-weight:bold;color:#327BB9;margin-left:2%;">广告违法信息</div>
<div height="2px"></div>
<hr height="2px" style="color:#FCFCFC;"><hr height="2px" style="color:#FCFCFC;margin-left:-2px;">
	<div name='oldregno' vtype="textfield" label="广告发布版面" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='enttype' vtype="textfield" label="违规表现" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='servicestate' vtype="textfield" label="认定依据" labelAlign="right" labelwidth='100px'  width="410"></div>
	<div name='regcap' vtype="textfield" label="处罚依据及监管措施" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryphy' vtype="textfield" label="处理人姓名" labelAlign="right" labelwidth='102px'  width="410"></div>
	<div name='industryco' vtype="textfield" label="发布次数" labelAlign="right" labelwidth='102px'  width="410"></div>
</div>
</body>
</html>
