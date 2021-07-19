<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
var serviceId = '${param.serviceId}';
$(function(){
	initFtpPathTitle();
	initServiceObject();
	$("#ServiceObject").change(function(){
		//alert($(this).val() + ", " + serviceId);
		initData($(this).val());
	});
	//
	//alert('${param.serviceId}');	
 });
 
//存储|更新 需要订阅的服务
function save() {
	//初始化时间
	$('div[name="startsubscribetime"]').datefield('setValue', '1990-07-01');
	//$('div[name="serviceobjectid"]').hiddenfield('getValue') + "," + $('div[name="serviceid"]').hiddenfield('getValue')
	var serviceObjectId = $('div[name="serviceobjectid"]').hiddenfield('getValue');
	var fwdypzjbxxid = $('div[name="fwdypzjbxxid"]').hiddenfield('getValue');
	var startsubscribetime = $('div[name="startsubscribetime"]').datefield('getValue');
	var path = $('div[name="path"]').textfield('getValue');   //路径
	var frequency = $('div[name="frequency"]').textfield('getValue');  //周期
	var acceptway = $('div[name="acceptway"]').comboxfield('getValue'); // 方式？
	var reason = $('div[name="reason"]').textareafield('getValue');  //yuanyin
	var params = {
		url : rootPath+'/dataservice/saveListenerService.do',
		components : [  'formpanel_edit1' ],
		params: {
			//fwdypzjbxxid: fwdypzjbxxid,
			//serviceid : serviceId,
			//serviceObjectId : serviceObjectId,
			startsubscribetime:startsubscribetime,
			//backstageData:backstageData,
			path:path,
			frequency:frequency,
			acceptway:acceptway,
			reason:reason
		},
		callback : function(data, r, res) { 
			if (res.getAttr("back") == 'success'){
				//parent.parent.queryUrl();
				//parent.parent.winEdit.window("close");
				jazz.info("保存成功！");
				parent.winEdit.window("close");
			}else{
				alert(res.getAttr("back"));
				if(res.getAttr("back") == 'errorname'){
					jazz.error("该名字已存在数据库！");
				}else{
					jazz.error("添加失败！");
				}
			}
		}
	};
	$.DataAdapter.submit(params);
}

//初始化对象列表
function initServiceObject() {
	 //服务对象列表加载
	  $.ajax({
		//url:rootPath+'/dataservice/serviceObjectList.do?belongOrg='+area,
	    url:rootPath+'/dataservice/allServiceObjectList.do',
		async:false,
		type:"post",
		dataType : 'json',
		success:function(data){
			data = data.data[0].data.rows;
		$('#ServiceObject option').remove();
		var con;
		var flag = true;
			for(var t=0;t<data.length;t++){
				if(data[t].businessname != '数据中心'){
					 con="<option  value="+data[t].fwdxjbid+">"+data[t].serviceobjectname+"</option>";
					$(con).appendTo($('#ServiceObject'));
					if(flag){
						//初始化第一个对象关是否订阅当前服务的信息
						initData(data[t].fwdxjbid);
						flag = false;
					}
				}
			}
		 
			}
		});
}

function initData(serviceObjectId) {
	//alert(serviceObjectId + ", " + serviceId);
	if(serviceObjectId != null && serviceObjectId != ''){
		$('div[name="fwdypzjbxxid"]').hiddenfield('setValue','');
		$('div[name="serviceid"]').hiddenfield('setValue',serviceId);
		$('div[name="serviceobjectid"]').hiddenfield('setValue',serviceObjectId);
		$('div[name="startsubscribetime"]').datefield('setValue','');
		$('div[name="frequency"]').textfield('setValue','');
		$('div[name="path"]').textfield('setValue','');
		$('div[name="acceptway"]').comboxfield('setValue',0);
		$('div[name="reason"]').textareafield('setValue','');
		//alert($('div[name="serviceobjectid"]').hiddenfield('getValue') + "," + $('div[name="serviceid"]').hiddenfield('getValue'));
		 $.ajax({
				//url:rootPath+'/dataservice/serviceObjectList.do?belongOrg='+area,
			    url:rootPath+'/dataservice/selectListerInfo.do',
				async:false,
				type:"post",
				data:{serviceObjectId:serviceObjectId, serviceId:serviceId},
				dataType : 'json',
				success:function(data){
					if(data != null && data.length > 0){
						data = data[0];
						$('div[name="fwdypzjbxxid"]').hiddenfield('setValue',data.fwdypzjbxxid);
						//$('div[name="serviceid"]').hiddenfield('setValue',serviceId);
						//$('div[name="serviceobjectid"]').hiddenfield('setValue',serviceObjectId);
						$('div[name="startsubscribetime"]').datefield('setValue',data.startsubscribetime);
						$('div[name="frequency"]').textfield('setValue',data.frequency);
						$('div[name="path"]').textfield('setValue',data.path);
						$('div[name="acceptway"]').comboxfield('setValue',data.acceptway);
						$('div[name="reason"]').textareafield('setValue',data.reason);
					}
				}
		 });
		//$('div[name="serviceurl"]').textareafield('setValue',serviceurl);
		//$("#formpanel_edit1").formpanel('option', 'dataurl',rootPath+'/dataservice/selectListerInfo.do?serviceId='+serviceId + '&serviceObjectId=' + serviceObjectId);
		//$("#formpanel_edit1").formpanel('reload', "null", function(){$('#formpanel_edit1 .jazz-panel-content').loading('hide');});
	}
}
 
//FTP路径提示
function initFtpPathTitle() {
	var title = '数据格式支持：xml(默认)、txt、csv。\r\n' + 
		        '{rand:6} 会替换成随机数,后面的数字是随机数的位数\r\n' + 
		        '{time} 会替换成时间戳\r\n' + 
		        '{yyyy} 会替换成四位年份\r\n' + 
		        '{yy} 会替换成两位年份\r\n' + 
		        '{mm} 会替换成两位月份\r\n' + 
		        '{dd} 会替换成两位日期\r\n' + 
		        '{hh} 会替换成两位小时\r\n' + 
		        '{ii} 会替换成两位分钟\r\n' + 
		        '{ss} 会替换成两位秒\r\n' + 
		        '非法字符 \ : * ? " < > | */';
	$("#ftpPathTitle").attr('title', title);
}

function back() {
	parent.winEdit.window("close");
}
</script>
</head>
<body>
<div id="formpanel_edit1" name="formpanel_edit1" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false"  title="订阅配置" showborder="false" width="100%" height="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}" dataurl="">
			<div id='fwdypzjbxxid' name='fwdypzjbxxid' vtype="hiddenfield" label="fwdypzjbxxid" labelAlign="right" width="400" labelwidth="130"></div>
			<div id='serviceid' name='serviceid' vtype="hiddenfield" label="serviceid" labelAlign="right" width="400" labelwidth="130"></div>
			<div id='serviceobjectid' name='serviceobjectid' vtype="hiddenfield" label="serviceobjectid" labelAlign="right" width="400" labelwidth="130"></div>
			<!-- <div name='subscriptionobject' vtype="textfield" label="选择订阅对象" labelAlign="right" rule="must" width="400" labelwidth="130"></div> -->
			<div style="margin-left:35px" class="jazz-field-comp jazz-datefield-comp">
				<label class="jazz-field-comp-label jazz-form-text-label" for="comp_j_09638185_1005_input" style="display: block; text-align: right; width: 100px;">服务提供对象:</label>
				<select id="ServiceObject" style="margin-left:10px;width:245px;height:30px;border:1px solid #C5D6E0;">
				</select>
			</div>
			<div style="display:none"><div name='startsubscribetime' vtype="datefield" label="开始订阅时间" labelAlign="right" rule="must" width="400" labelwidth="130" ></div></div>
			<div name='frequency' vtype="textfield" label="频率/周期性设置" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
			<label class="jazz-field-comp-label jazz-form-text-label" style="color:red;display: block; text-align: left; width: 620px;">
				支持两种方式：1. 整数  -  订阅频率，时间粒度为一天。例如：填写 100 代表一天内推送 100 次</br>
				               2. Crontab表达式
			</label>
			<div name='path' vtype="textfield" label="FTP地址" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
			<label class="jazz-field-comp-label jazz-form-text-label" style="color:red;display: block; text-align: left; width: 620px;"><!-- &#10;&#13; -->
			    格式：ftp://ftp用户名:密码@ftp服务器地址:端口/授权路径及文件名&nbsp;&nbsp;<a href='#' id='ftpPathTitle'  title=''>提示</a></br>
				示例：ftp://uftp:123456@192.168.0.224:21/{yyyy}{mm}{dd}/Data_{time}{rand:8}.xml
			</label>
			<div name='acceptway' vtype="comboxfield" dataurl="[{value: '0',text: '启用'},{value: '1',text: '停用'}]" label="订阅状态" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
			<div name='reason' vtype="textareafield" label="原因" labelAlign="right" width="850" rule="must" labelwidth="130" height="60" colspan="2"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn1" name="btn1" vtype="button" text="保存" icon="../../../style/default/images/save.png" click="save()"></div>
			<div id="btn2" name="btn2" vtype="button" text="返回" icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
</body>

</html>