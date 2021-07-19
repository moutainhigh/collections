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
	var update;
	var serviceobjectname;
	var backstageData;
	var tree;
	var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			},
			key: {
				checked: "checked"
			}
		},
		check: { 
            enable: true, 
            chkboxType:{ "Y":'s', "N":'s'}, 
        }
	};
	$(function(){
		var fwdxjbid = jazz.util.getParameter("fwdxjbid");
		update = jazz.util.getParameter("update");
		//$("#formpanel_edit").formpanel('option', 'disabled', true);
		if(update=="true"){
			changeArea();
			$('#formpanel_edit .jazz-panel-content').loading();
			$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/dataservice/serviceObjectDetail.do?fwdxjbid='+fwdxjbid);
			$("#formpanel_edit").formpanel('reload', "null", function(){
			$('#formpanel_edit .jazz-panel-content').loading('hide');
			var sname=$('div[name="serviceobjectname"]').hiddenfield('getValue');
			var serviceorgname=$('div[name="serviceorgname"]').hiddenfield('getValue');
			var serviceobjectregion=$('div[name="serviceobjectregion"]').hiddenfield('getValue');
			checkArea(serviceobjectregion,"2");
			//回显
			$('#BelongArea').find('option[value="'+serviceobjectregion+'"]').attr("selected","selected");
			$('#BelongOrg').find('option[value="'+serviceorgname+'"]').attr("selected","selected");
			});
			}else{
				changeArea();
				checkArea("440000","2");
			}
			
			var funcUrl = rootPath+'/datatheme/themeContentTree.do?fwdxjbid='+fwdxjbid+'&update='+update;
			var params = {
			url : funcUrl,
			callback : function(data, r, res) { 
				var data = data["data"];
				tree = $.fn.zTree.init($("#funcTree"), setting, data);
				tree.expandAll(true);
			}
		};
		$.DataAdapter.submit(params);
		});
		
	function back() {
		parent.winEdit.window("close");
	}
	
	
	
	function save() {
		var serviceobjectuser = $("div[name='serviceobjectuser']").textfield('getValue');
		/* var serviceobjectname = $("div[name='serviceobjectname']").textfield('getValue'); */
		serviceobjectname = serviceObjectName();
		backstageData=backstageName();
		var serviceobjectip = $("div[name='serviceobjectip']").textfield('getValue');
		var objectsectororsystem = $("div[name='objectsectororsystem']").comboxfield('getValue');
		var state = $("div[name='state']").radiofield('getValue');
		if(state==''||serviceobjectuser == ''||serviceobjectname == ''||serviceobjectip==''||objectsectororsystem==''){
			jazz.info("有必填选项未填写，添加失败");
			}else{
		
			var selectedData = tree.getCheckedNodes(true);
			var funcIds = new Array();
			var funcNames = new Array();
			$.each(selectedData, function(i, n){
				funcIds.push(n.id);
				funcNames.push(n.name);
			});
			var funcIdsStr = funcIds.join(","); 
			var funcNamesStr = funcNames.join(","); 
				
			var params = {
				url : rootPath+'/dataservice/saveServiceObject.do?update='+update,
				components : [ 'formpanel_edit' ],
				params: {
						serviceName:serviceobjectname,
						backstageData:backstageData,
						funcIdsStr : funcIdsStr,
						funcNamesStr : funcNamesStr,
						},
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						parent.queryUrl();
						parent.winEdit.window("close");
						jazz.info("保存成功！");
					}else{
						if (res.getAttr("back") == 'name_unique')
							jazz.error("服务对象名称已存在！");
						else
							jazz.error("添加失败！");
					}
				}
			};
			$.DataAdapter.submit(params);
		
			
				}
			}

function changeArea(){
		 $.ajax({
		url:'/gdsjzx/dataservice/iniArea.do',
		async:false,
		type:"post",
		dataType : 'json',
		success:function(data){
			for(var t=0;t<data.length;t++){
					var con="<option  value="+data[t].qhcode+">"+data[t].qhvalue+"</option>";
					$(con).appendTo($('#BelongArea'));
				}
			}
		});	 	
}

	//根据地区选择下一级
	function checkArea(even,type){
		var area;
		if("1"==type){
			area=$(even).val();
		}else{
			if("2"==type){
			area=even;	
			}
		}
		  $.ajax({
			url:'/gdsjzx/dataservice/iniArea.do?belongOrg='+area,
			async:false,
			type:"post",
			dataType : 'json',
			success:function(data){
			$('#BelongOrg option').remove();
				for(var t=0;t<data.length;t++){
					var con="<option  value="+data[t].jgcode+">"+data[t].jgvalue+"</option>";
					$(con).appendTo($('#BelongOrg'));
				}
				}
			});	 	
	};
		//显示的中文名
	function serviceObjectName(){
		var area=$('#BelongArea').find("option:selected").text();
		var org=$('#BelongOrg').find("option:selected").text();
		var busName=$("div[name='businessname']").textfield('getValue');
					
		//var name=area+"/"+org+"/"+busName;
		//不需要地市名称
		var name = org + "/" + busName;
		return name;
	}

	//传后台的val;
	function backstageName(){
		var area=$('#BelongArea').val();
		var org=$('#BelongOrg').val();
		var busName=$("div[name='businessname']").textfield('getValue');
					
		var name=area+"/"+org+"/"+busName;
		return name;
	}
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="450" dataurl="">

		<div name='fwdxjbid' vtype="hiddenfield" label="ID" labelAlign="right" width="400" labelwidth="130"></div>
		
		<div style="margin-left:35px" class="jazz-field-comp jazz-datefield-comp">
			<label class="jazz-field-comp-label jazz-form-text-label" for="comp_j_09638185_1005_input" style="display: block; text-align: right; width: 100px;">所属地市:</label>
			<select id="BelongArea" onChange="checkArea(this,'1')" style="margin-left:10px;width:245px;height:30px;border:1px solid #C5D6E0;">
			</select>
		</div>
		<div style="margin-left:35px" class="jazz-field-comp jazz-datefield-comp">
			<label class="jazz-field-comp-label jazz-form-text-label" for="comp_j_09638185_1005_input" style="display: block; text-align: right; width: 100px;">机构:</label>
			<select id="BelongOrg" style="margin-left:10px;width:245px;height:30px;border:1px solid #C5D6E0;">
			</select>
		</div>
		<div name='serviceobjectname' vtype="hiddenfield" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='serviceorgname' vtype="hiddenfield" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='serviceobjectregion' vtype="hiddenfield" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='businessname' vtype="textfield" label="业务系统名称" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='serviceobjectip' vtype="textfield" label="服务对象IP" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='serviceobjectport' vtype="textfield" label="服务对象端口" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='state' vtype="radiofield" dataurl="[{value: '0',text: '启用',checked:'true'},{value: '1',text: '停用'}]" label="状态" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='controlobjectstate' vtype="radiofield" dataurl="[{value: '0',text: '可控',checked:'true'},{value: '1',text: '不可控'}]" label="可控状态" labelAlign="right" rule="must" width="400" labelwidth="130"></div>
		<div name='reason' vtype="textareafield" label="描述" labelAlign="right"
			width="850" labelwidth="130" height="60" colspan="2"></div>
		<div vtype="textfield" label="所属主题" readonl labelAlign="right" colspan="2" width="850" labelwidth="130" readonly="true"></div>
		<div style="padding-left: 100px;height: 10px;" id="funcTree" name="funcTree" class="ztree" title="sfsdfs" titledisplay="true"></div>
		</div>
		<div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn1" name="btn1" vtype="button" text="保存" icon="../../../style/default/images/save.png" click="save()"></div>
			<div id="btn2" name="btn2" vtype="button" text="返回" icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
</body>
</html>