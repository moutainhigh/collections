<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>自主查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<style type="text/css">
td{
	text-align: center;
}
.jazz-pagearea{
	height: 0px;
}
</style>

<script>
var i = 0;
function showNext(){ 
	i++;
	$('#formpanel'+i).show();
	
	if(i>=26){//去掉定时器的方法 
		window.clearInterval(t1);
	}
} 
//重复执行某个方法 
var t1 = window.setInterval(showNext,500); 

function showNextData(){
	
}
</script>
</head>
<body>
<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto" title="市场主体基本信息">
	<div name='pripid' vtype="textfield" label="主体身份代码" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='regno' vtype="textfield" label="注册号" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='entname' vtype="textfield" label="企业(机构)名称" labelAlign="right" labelwidth='100px' width="410"></div>
	
	<div name='enttype' vtype="textfield" label="市场主体类型" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='regcap' vtype="textfield" label="注册资本(金)" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='IndustryPhy' vtype="textfield" label="行业门类" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='IndustryCo' vtype="textfield" label="行业代码" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='EstDate' vtype="textfield" label="成立日期" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='RegOrg' vtype="textfield" label="登记机关" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='PostalCode' vtype="textfield" label="邮政编码" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='Tel' vtype="textfield" label="联系电话" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='Email' vtype="textfield" label="电子邮箱" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='ABuItemCo' vtype="textfield" label="许可经营项目" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='CBuItem' vtype="textfield" label="一般经营项目" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='OpScope' vtype="textfield" label="经营(业务)范围" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='OpFrom' vtype="textfield" label="经营(驻在)期限自" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='OpTo' vtype="textfield" label="经营(驻在)期限至" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='LocalAdm' vtype="textfield" label="属地监管工商所" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='OpState' vtype="textfield" label="经营状态" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='ApprDate' vtype="textfield" label="核准日期" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='InsAuth' vtype="textfield" label="年检机关" labelAlign="right" labelwidth='100px' width="410"></div>
	
	<div name='OpScoAndForm' vtype="textfield" label="经营范围及方式" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='RecDate' vtype="textfield" label="备案日期" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='OldRegNO' vtype="textfield" label="旧注册号" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='DepInCha' vtype="textfield" label="主管部门" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='memo' vtype="textfield" label="备注" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='BLicCopyNum' vtype="textfield" label="执照副本数" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='OrgCode' vtype="textfield" label="法人企业机构代码" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='economicProperty' vtype="textfield" label="企业经济性质" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='EcoTecDevZone' vtype="textfield" label="住所所在经济开发区" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='ExeNum' vtype="textfield" label="执行人数" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='Dom' vtype="textfield" label="住所" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='DomProRight' vtype="textfield" label="住所产权" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='exchangeRate' vtype="textfield" label="美元汇率" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='fileID' vtype="textfield" label="档案编号" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='ForCapIndCode' vtype="textfield" label="外资产业代码" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='MidPreIndCode' vtype="textfield" label="中西部优势产业代码" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='foreignCertNo' vtype="textfield" label="证照号码" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='industryRangeOption' vtype="textfield" label="农民专业合作社业务服务" labelAlign="right" labelwidth='100px' width="410"></div>
	
	<div name='Addr' vtype="textfield" label="地址" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='mainFoodstuffLabel' vtype="textfield" label="主经营类别" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='LeRep' vtype="textfield" label="法定代表人" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='Ent' vtype="textfield" label="企业(机构)类型" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='Receptionunit' vtype="textfield" label="接待单位" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='ReceptionTel' vtype="textfield" label="接待电话" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='technicPersonNum' vtype="textfield" label="技术人员" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='unemployment' vtype="textfield" label="下岗职工人数" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='ParNum' vtype="textfield" label="合伙人数" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='EmpNum' vtype="textfield" label="从业人数	" labelAlign="right" labelwidth='100px' width="410"></div>
</div>

<div vtype="formpanel" id="formpanel1" displayrows="1" name="formpanel1"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="市场主体隶属信息" style="display: none;">
	<div name='RegNO' vtype="textfield" label="注册号" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='EntName' vtype="textfield" label="企业(机构)名称" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='Addr' vtype="textfield" label="住所" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='DomDistrict' vtype="textfield" label="隶属企业住所行政区划" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='RegOrg' vtype="textfield" label="登记机关" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='OpScoAndForm' vtype="textfield" label="经营范围及方式" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='Country' vtype="textfield" label="隶属企业国别" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='entityCharacter' vtype="textfield" label="隶属企业性质" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='enterpriseType' vtype="textfield" label="企业类型" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='foreignName' vtype="textfield" label="外文名称" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='EstDate' vtype="textfield" label="成立日期" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='subordinateRelation' vtype="textfield" label="隶属关系" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='isBranch' vtype="textfield" label="是否分支机构" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='isForeign' vtype="textfield" label="是否外来的企业" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='operBeginDate' vtype="textfield" label="隶属企业营业起始时间" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='operEndDate' vtype="textfield" label="隶属企业营业终止时间" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='PrilName' vtype="textfield" label="负责人" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='Tel' vtype="textfield" label="联系电话" labelAlign="right"  labelwidth='100px' width="410"></div>
</div>
<div vtype="formpanel" id="formpanel2" displayrows="1" name="formpanel2"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="市场主体补充信息" style="display: none;">
	<div name='EcoTecDevZone' vtype="textfield" label="住所所在经济开发区" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='ProType' vtype="textfield" label="项目类型" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='ABuItemCo' vtype="textfield" label="许可经营项目" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='CBuItem' vtype="textfield" label="一般经营项目" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='ConGro' vtype="textfield" label="投资总额" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='ConGroCur' vtype="textfield" label="投资总额币种" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='ConGroUSD' vtype="textfield" label="投资总额折万美元" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='RegCap' vtype="textfield" label="注册资本(金)" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='RegCapCur' vtype="textfield" label="注册资本(金)币种" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='RegCapUSD' vtype="textfield" label="注册资本(金)折万美元" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='RegCapRMB' vtype="textfield" label="注册资本(金)折人民币" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='RecCap' vtype="textfield" label="实收资本" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='RecCapCur' vtype="textfield" label="实收资本币种" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='RecCapUSD' vtype="textfield" label="实收资本折万美元" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='RecCapRMB' vtype="textfield" label="实收资本折人民币" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='DomeRegCap' vtype="textfield" label="中方注册资本(金)" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='DomeRegCapCur' vtype="textfield" label="中方注册资本(金)币种" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='DomeRegCapUSD' vtype="textfield" label="中方注册资本(金)折万美元" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='DomeRegCapInvProp' vtype="textfield" label="中方注册资本(金)出资比例" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='DomeRecCap' vtype="textfield" label="中方实收资本" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='DomeRecCapCur' vtype="textfield" label="中方实收资本币种" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='DomeRecCapUSD' vtype="textfield" label="中方实收资本折万美元" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='DomeRecCapConProp' vtype="textfield" label="中方实收资本出资比例" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='ForRegCap' vtype="textfield" label="外方注册资本(金)" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='ForRegCapCur' vtype="textfield" label="外方注册资本(金)币种" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='ForRegCapUSD' vtype="textfield" label="外方注册资本(金)折万美元" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='ForRegCapInvProp' vtype="textfield" label="外方注册资本(金)出资比例" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='ForRecCap' vtype="textfield" label="外方实收资本" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='ForRecCapCur' vtype="textfield" label="外方实收资本币种" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='ForRecCapUSD' vtype="textfield" label="外方实收资本折万美元" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='ForRecCapConProp' vtype="textfield" label="外方实收资本出资比例" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='InsForm' vtype="textfield" label="设立方式" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='ChaMecDate' vtype="textfield" label="转型日期" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='activeCapCoinCap' vtype="textfield" label="活动资金币种" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='activeCapExRate' vtype="textfield" label="活动资金的汇率" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='activeCapital' vtype="textfield" label="流动资金（元）" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='fixedCapCoinType' vtype="textfield" label="固定资金币种" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='fixedCapExRate' vtype="textfield" label="固定资金币种汇率" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='fixedCapital' vtype="textfield" label="固定资金（元）" labelAlign="right"  labelwidth='100px' width="410"></div>
	
	<div name='ConGroRate' vtype="textfield" label="投资币种折美元汇率" labelAlign="right"  labelwidth='100px' width="410"></div>
	
</div>

<div id="column_id" width="100%" height="300" vtype="panel" name="panel"    
         layout="column" layoutconfig="{width: ['20%','*']}">   
        <div>   
            <div name="w1" vtype="panel" title="电影栏目" titledisplay="true" height="200"></div>   
        </div>   
        <div>   
            <div vtype="formpanel" id="formpanel4" displayrows="1" name="formpanel4"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="市场主体隶属信息补充信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
        </div>      
    </div>   

<div vtype="formpanel" id="formpanel3" displayrows="1" name="formpanel3"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="市场主体标记信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel4" displayrows="1" name="formpanel4"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="市场主体隶属信息补充信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel5" displayrows="1" name="formpanel5"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="名称基本信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel6" displayrows="1" name="formpanel6"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="证照信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel7" displayrows="1" name="formpanel7"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="冻结股东信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel8" displayrows="1" name="formpanel8"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="股权出质信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel9" displayrows="1" name="formpanel9"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="高级成员信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel10" displayrows="1" name="formpanel10"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="法定代表人" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>

<div vtype="formpanel" id="formpanel11" displayrows="1" name="formpanel11"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="注吊销信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel12" displayrows="1" name="formpanel12"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="变更信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel13" displayrows="1" name="formpanel13"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="清算信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel14" displayrows="1" name="formpanel14"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="股权冻结信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel15" displayrows="1" name="formpanel15"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="迁入信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel16" displayrows="1" name="formpanel16"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="迁出信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel17" displayrows="1" name="formpanel17"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="归档信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel18" displayrows="1" name="formpanel18"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="投资人及出资信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel19" displayrows="1" name="formpanel19"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="投资人及出资其他信息" style="display: none;">
	<div name='isOriginatePerson' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px' width="410"></div>
	<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px' width="410"></div>
	<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px' width="410"></div>
</div>
<div vtype="formpanel" id="formpanel20" displayrows="1" name="formpanel20"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="出资业务信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>

<div vtype="formpanel" id="formpanel21" displayrows="1" name="formpanel21"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="外国(地区)企业在中国从事生产经营活动基本信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel22" displayrows="1" name="formpanel22"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="业务环节信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel23" displayrows="1" name="formpanel23"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="人员基本信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel24" displayrows="1" name="formpanel24"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="人员其他证件信息" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel25" displayrows="1" name="formpanel25"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="待办事宜" style="display: none;">
	<div name='zzjgdm' vtype="textfield" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
		<div name='qymc' vtype="textfield" label="企业名称" labelAlign="right"  labelwidth='100px'
		width="410"></div>
		<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px'
		width="410"></div>
</div>
<div vtype="formpanel" id="formpanel26" displayrows="1" name="formpanel26"
	titledisplay="true" width="100%" layout="table"  showborder="false"
	layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="auto"
	title="待办附加信息" style="display: none;">
	<div name='zzjgdm' vtype="field" label="组织机构代码" labelAlign="right" labelwidth='100px'
		width="410"></div><div name='zzjgd1m' vtype="field" label="组织机构3代码" labelAlign="right" labelwidth='100px'
		width="410"></div>
</div>
</body>
</html>