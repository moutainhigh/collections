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
//var t1 = window.setInterval(showNext,500); 

function showNextData(){
	
}
</script>
</head>
<body>


<div id="column_id" width="100%" height="auto" vtype="panel" name="panel"    
         layout="column" layoutconfig="{width: ['20%','*']}">   
        <div>   
            <iframe id="includeframe" name="includeframe" src="<%=request.getContextPath()%>/page/reg/query-tree-left.jsp" width="100%" class="initheight" frameborder="0" scrolling="auto"></iframe> 
        </div>   
        <div>   
            
<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
	titledisplay="true" width="98%" layout="table"  showborder="true"
	layoutconfig="{cols:2, columnwidth: ['50%','*'],border: false}" height="auto" title="市场主体基本信息">
	<div name='pripid' vtype="textfield" label="主体身份代码" labelalign="right" readonly="true" labelwidth='100px' width="410"></div>
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

 
        </div>   
    </div>   


</body>
</html>