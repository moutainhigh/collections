	function back() {
		parent.parent.winEdit.window("close");
	}
	function save() {
		serviceobjectname = serviceObjectName();
		//var servicetype = $("div[name='servicetype']").comboxfield('getValue');
		var servicetype = '0';
		var servicestate = $("div[name='servicestate']").radiofield('getValue');
		//var businessname = $("div[name='businessname']").textfield('getValue');
		//业务系统名称废除，但为区别提供方&数据中心的区别。此处写死
		var businessname = ($("#ServiceObject").attr('isSjzx') == '1' ? '数据中心' : '外部提供方');
		var interfacename = $("div[name='interfacename']").textfield('getValue');
		var serviceurl = $("div[name='serviceurl']").textareafield('getValue');
		var defaulttime=$("div[name='defaulttime']").textfield('getValue');
		var description = $("div[name='description']").textareafield('getValue');
		//var serviceObjectId=$('#ServiceObject option:selected').val();
		var soid = $('#ServiceObject option:selected').val();
		var url=serviceclassifychange();
		/*if(!isValidurl(url)){
			jazz.info("url格式不规范");
			return ;
		}*/
		var reg = new RegExp("^[0-9]*$");
		if(parseInt(defaulttime)<0 || !reg.test(defaulttime)){
			jazz.info("默认时间须大于0和数字");
			return ;
		}else{
		 if(businessname==''||interfacename==''||servicetype==''||servicestate==''|| serviceurl==''){
			jazz.info("有必填选项未填写，添加失败");
			return ;
		}else{
			if(servicetype=='2'){
				var subscriptionobject = $("div[name='subscriptionobject']").textfield('getValue');
				var startsubscribetime = $("div[name='startsubscribetime']").datefield('getValue');
				var frequency = $("div[name='frequency']").textfield('getValue');
				var path = $("div[name='path']").textfield('getValue');
				var acceptway = $("div[name='acceptway']").comboxfield('getValue');
				var reason = $("div[name='reason']").textareafield('getValue');
				if(subscriptionobject==''||startsubscribetime == ''||frequency == ''||path==''||acceptway==''||reason==''){
					jazz.info("有必填选项未填写，添加失败");
				}else{
					//var selectedData = tree.getCheckedNodes(true);
					//if(selectedData.length<=0){
					var selectedData = '';
					if(false){
						jazz.info("请选择配置服务内容！");
					}else{
						var funcIds = new Array();
						var funcNames = new Array();
						$.each(selectedData, function(i, n){
							funcIds.push(n.id);
							funcNames.push(n.name);
						});
						var funcIdsStr = funcIds.join(","); 
						var funcNamesStr = funcNames.join(","); 
						var backstageData=backstageName();
						
						var params = {
							url : rootPath+'/dataservice/saveService.do?update='+update,
							components : [ 'formpanel_edit','formpanel_edit1' ],
							params: {
								funcIdsStr : funcIdsStr,
								funcNamesStr : funcNamesStr,
								serviceName:serviceobjectname,
								backstageData:backstageData,
								description:description,
								soid:soid,
								url:url
							},
							callback : function(data, r, res) { 
								if (res.getAttr("back") == 'success'){
									parent.parent.queryUrl();
									parent.parent.winEdit.window("close");
									jazz.info("保存成功！服务名:"+serviceobjectname);
								}else{
									alert(res.getAttr("back"));
									if(res.getAttr("back") == 'errorname'){
										jazz.error("该名字已存在数据库！");
									}else{
										jazz.error("添加服务对象失败！");
									}
								}
							}
						};
						$.DataAdapter.submit(params);
					}
				}
			}else{
				//var selectedData = tree.getCheckedNodes(true);
				//if(selectedData.length<=0){
				var selectedData = '';
				if(false){
					jazz.info("请选择配置服务内容！");
				}else{
					var funcIds = new Array();
					var funcNames = new Array();
					$.each(selectedData, function(i, n){
						funcIds.push(n.id);
						funcNames.push(n.name);
					});
					var funcIdsStr = funcIds.join(","); 
					var funcNamesStr = funcNames.join(","); 
					var backstageData=backstageName();
					var params = {
						url : rootPath+'/dataservice/saveService.do?update='+update,
						components : [ 'formpanel_edit' ],
						params: {
							funcIdsStr : funcIdsStr,
							funcNamesStr : funcNamesStr,
							serviceName:serviceobjectname,
							backstageData:backstageData,
							url:url,
							soid:soid,
							description:description
						},
						callback : function(data, r, res) { 
							if (res.getAttr("back") == 'success'){
								parent.parent.queryUrl();
								parent.parent.winEdit.window("close");
								jazz.info("保存成功！服务名:"+serviceobjectname);
							}else{
								if(res.getAttr("back") == 'errorname'){
									jazz.error("该名字已存在数据库！");
								}else{
									jazz.error("添加服务对象失败！");
								}
							}
						}
					};
					$.DataAdapter.submit(params);
				}
			}
		}
      }
		
	}
	
	/*$(function(){
		alert(isValidurl("198.0.1.123:asdd/"));
		alert(isValidurl("10.0.1.89:100000/"));
		alert(isValidurl("10.0.1.32:1010/"));
	});*/
	//验证IP地址：端口号/SOAService
	function isValidurl(url)     
	{     
		var urlList=url.split("/");
		if(urlList!=null && urlList.length>1){
			var ip  = urlList[0].split(":")[0];
			var port= urlList[0].split(":")[1]; 
			var regip =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;     
			var regport=  /^([0-9]{1,4})?$/;
			var flag=false;
			if(urlList[1]=="SOAService"){
				flag=true;
			}
			return (regip.test(ip) &&  regport.test(port) && flag);     
		}
	}   
	function submintSave(){
	}
	
	$(function(){
		changeArea();
		checkArea("440000","2");
		initServiceObject();
	});
	
	function initServiceObject() {
		  var serviceobjectid = $("#serviceobjectid",parent.document).val();
		 // alert(serviceobjectid);
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
					for(var t=0;t<data.length;t++){
						if($("#ServiceObject").attr('isSjzx') == '1'){
							if(data[t].businessname == '数据中心'){
								 con="<option  value='"+data[t].fwdxjbid+"'"+(data[t].fwdxjbid == serviceobjectid ? " selected" : "")+">"+data[t].serviceobjectname+"</option>";
								$(con).appendTo($('#ServiceObject'));
							}
						} else {
							if(data[t].businessname != '数据中心'){
								 con="<option  value='"+data[t].fwdxjbid +"'"+(data[t].fwdxjbid == serviceobjectid ? " selected" : "")+">"+data[t].serviceobjectname+"</option>";
								$(con).appendTo($('#ServiceObject'));
							}
						}
						
					}
				 
					}
				});
	}
	//T_DM_XZQHDM行政区划代码
	function changeArea(){
	var backstageData=backstageName();
	    $.ajax({
			url:rootPath+'/dataservice/iniArea.do',
			async:false,
			type:"post",
			post:backstageData,
			dataType : 'json',
			success:function(data){
			//$('#listId2 > ul li').remove();
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
			url:rootPath+'/dataservice/iniArea.do?belongOrg='+area,
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
		//var area=$('#BelongArea').attr("selected","selected").innerHTML;
		var area=$('#BelongArea').find("option:selected").text();
		var org=$('#BelongOrg').find("option:selected").text();
		//var busName=$("div[name='businessname']").textfield('getValue');
		var interfaceName=$("div[name='interfacename']").textfield('getValue');
					
		//var name=area+"/"+org+"/"+interfaceName;
		var name = org+"/"+interfaceName;
		return name;
	}
	
	//传后台的val;
	function backstageName(){
		var area=$('#BelongArea').val();
		var org=$('#BelongOrg').val();
		//var busName=$("div[name='businessname']").textfield('getValue');
		var interfaceName=$("div[name='interfacename']").textfield('getValue');
					
		var name=area+"/"+org+"/"+interfaceName;
		return name;
	}
	
	
	function serviceclassifychange(){
		var url=$('div[name="serviceurl"]').textareafield('getValue');
		var alias=$('div[name="alias"]').textfield('getValue');
		//if(url!=null && url.length>0){
			//var index=url.indexOf("SOAService/");
			//var urllen=url.length;
		var serviceclassify=$('#serviceclassify').val();
		//var serviceurl="http://"+url.substring(0,index+11)+serviceclassify+alias;//url.substring(index+10);
		var serviceurl="http://"+url+"/"+serviceclassify+"/"+alias;//url.substring(index+10);
		return serviceurl;
		//}
	};