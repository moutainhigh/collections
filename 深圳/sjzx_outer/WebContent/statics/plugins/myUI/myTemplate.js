function temp0(data) {
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		var id = " ";
		if (isNotEmpty(data[0].id)) {
			id = data[0].id;
		}
		var unifsocicrediden = " ";
		if (isNotEmpty(data[0].unifsocicrediden)) {
			unifsocicrediden = data[0].unifsocicrediden;
		}
		
		var regno = " ";
		if (isNotEmpty(data[0].regno)) {
			regno = data[0].regno;
		}
		var entname = " ";
		if (isNotEmpty(data[0].entname)) {
			entname = data[0].entname;
		}
		var dom = " ";
		if (isNotEmpty(data[0].dom)) {
			dom = data[0].dom;
		}
		var lerep = " ";
		if (isNotEmpty(data[0].lerep)) {
			lerep = data[0].lerep;
		}
		var regcap = " ";
		if (isNotEmpty(data[0].regcap)) {
			regcap = data[0].regcap;
		}

		var enttype = " ";
		if (isNotEmpty(data[0].enttype)) {
			enttype = data[0].enttype;
		}
		var estdate = " ";
		if (isNotEmpty(data[0].estdate)) {
			estdate = data[0].estdate;
		}
		var opfrom = " ";
		if (isNotEmpty(data[0].opfrom)) {
			opfrom = data[0].opfrom;
		}
		var opto = " ";
		if (isNotEmpty(data[0].opto)) {
			opto = data[0].opto;
		}
		var apprdate = " ";
		if (isNotEmpty(data[0].apprdate)) {
			apprdate = data[0].apprdate;
		}
		var entname = " ";
		if (isNotEmpty(data[0].entname)) {
			entname = data[0].entname;
		}
		var entstatus = " ";
		if (isNotEmpty(data[0].entstatus)) {
			entstatus = data[0].entstatus;
		}
		var opscope = " ";
		if (isNotEmpty(data[0].opscope)) {
			opscope = data[0].opscope;
		}
		
		var compform = " ";
		if (isNotEmpty(data[0].compform)) {
			compform = data[0].compform;
		}
		
		/*var branch = " ";
		if (isNotEmpty(data[0].branch)) {
			branch = data[0].branch;
		}*/
		var biZhong = " ";
		if (isNotEmpty(data[0].currency)) {
			biZhong = data[0].currency;
		}
		
		var entName = $("#entName").attr("entname");
		$("#panel-1").find("h2").html(entName+"的基本信息");
		
		var opetype = $("#opetype").attr("opetype");
		
		var remark = " ";
		if (isNotEmpty(data[0].remark)) {
			remark = data[0].remark;
		}
		
		var _html = "";
		if(opetype!="JT"){
			_html += "<tr class='active'><td class='title'>统一社会信用代码：</td><td class='titleDesc'>" + unifsocicrediden + "</td></tr>";
			_html += "<tr class='success'><td class='title'>注册号：</td><td class='titleDesc'>" + regno + "</td></tr>";
			if(opetype=="FGS"||opetype=="NZYY"||opetype=="HHFZ"||opetype=="WZHHFZ"||opetype=="GRDZFZ"||opetype=="WZFZ"||opetype=="WGDB"||opetype=="WGJY"||opetype=="HZSFZ"){
				$.ajax({
					url:"../entEnt/entMother.do",
					data:{id:id},
					dataType:"json",
					async : false,//取消异步请求
					success:function(data){
						var data = data.data[0].data;
						if(!$.isEmptyObject(data)){
							var subname = " " ;
							if(isNotEmpty(data[0].subname)){
								subname  =data[0].subname;
							}
							var subregno = " ";
							if(isNotEmpty(data[0].regno)){
								subregno  =data[0].regno;
							}
							_html += "<tr class='active'><td class='title'>隶属企业名称：</td><td class='titleDesc'>"+subname+"</td></tr>";
						}else{
							_html += "<tr class='active'><td class='title'>隶属企业名称：</td><td class='titleDesc'></td></tr>";
						}
					}
				});
			}			
			_html += "<tr class='active'><td class='title'>商事主体名称：</td><td class='titleDesc'>" + entname + "</td></tr>";
		}else{
			$.ajax({
				url:"../entEnt/entMother.do",
				data:{id:id},
				dataType:"json",
				async : false,//取消异步请求
				success:function(data){
					var data = data.data[0].data;
					if(!$.isEmptyObject(data)){
						var subname = " " ;
						if(isNotEmpty(data[0].subname)){
							subname  =data[0].subname;
						}
						var subregno = " ";
						if(isNotEmpty(data[0].regno)){
							subregno  =data[0].regno;
						}
						_html += "<tr class='success'><td class='title'>注册号：</td><td class='titleDesc'>" + regno + "</td></tr>";
						_html += "<tr class='active'><td class='title'>集团名称 ：</td><td class='titleDesc'>" + entname + "</td></tr>";
						_html += "<tr class='active'><td class='title'>母公司名称 ：</td><td class='titleDesc'>" + subname + "</td></tr>";
						_html += "<tr class='active'><td class='title'>母公司注册号 ：</td><td class='titleDesc'>" + subregno + "</td></tr>";
						_html += "<tr class='active'><td class='title'>母公司住所 ：</td><td class='titleDesc'>" + dom + "</td></tr>";
						_html += "<tr class='active'><td class='title'>注册资本（金）总和（万元） ：</td><td class='titleDesc'>"+regcap+"</td></tr>";
					}
				}
				
			});
			
		}
		
		if(opetype=="GS"||opetype=="NZFR"||opetype=="GRDZ"||opetype=="WZGS"||opetype=="WGJY"||opetype=="HZS"){
			_html += "<tr class='success'><td class='title'>住所：</td><td class='titleDesc'>" + dom + "</td></tr>";
		}else if(opetype=="FGS"||opetype=="NZYY"||opetype=="HHFZ"||opetype=="WZHHFZ"||opetype=="GRDZFZ"||opetype=="WZFZ"){
			_html += "<tr class='success'><td class='title'>营业场所：</td><td class='titleDesc'>" + dom + "</td></tr>";
		}else if(opetype=="HZSFZ"||opetype=="GT"){
			_html += "<tr class='success'><td class='title'>经营场所：</td><td class='titleDesc'>" + dom + "</td></tr>";
		}else if(opetype=="HHQY"||opetype=="WZHH"){
			_html += "<tr class='success'><td class='title'>主要经营场所：</td><td class='titleDesc'>" + dom + "</td></tr>";
		}else if(opetype=="WGDB"){
			_html += "<tr class='success'><td class='title'>驻在场所：</td><td class='titleDesc'>" + dom + "</td></tr>";
		}else if(opetype=="SLYB"){
			_html += "<tr class='success'><td class='title'>地址：</td><td class='titleDesc'>" + dom + "</td></tr>";
		}	
		if(opetype=="GS"||opetype=="NZFR"||opetype=="WZGS"||opetype=="HZS"){
			var entId  = $("#entId").attr("ent");
			$.ajax({
				url:"../entEnt/fddbr.do",
				type:"post",
				data:{id:entId},
				dataType:"json",
				async : false,//取消异步请求
				success:function(data){
					var persname =  " ";
					try {
						if(isNotEmpty(data.data[0].data[0].persname)){
							persname = data.data[0].data[0].persname;
							_html += "<tr class='success'><td class='title'>法定代表人：</td><td class='titleDesc'>" + persname + "</td></tr>";
						}else{
							_html += "<tr class='success'><td class='title'>法定代表人：</td><td class='titleDesc'> </td></tr>";
						}
					} catch (e) {
					}finally {
						//_html += "<tr class='success'><td class='title'>法定代表人：</td><td class='titleDesc'> </td></tr>";
					}
				}
			});
		}else if(opetype=="FGS"||opetype=="NZYY"||opetype=="HHFZ"||opetype=="WZHHFZ"||opetype=="GRDZFZ"||opetype=="WZFZ"||opetype=="WGJY"||opetype=="HZSFZ"||opetype=="SLYB"){
			var entId  = $("#entId").attr("ent");
			$.ajax({
				url:"../entEnt/fddbr.do",
				type:"post",
				data:{id:entId},
				dataType:"json",
				async : false,//取消异步请求
				success:function(data){
					var persname =  " ";
					try{
						if(isNotEmpty(data.data[0].data[0].persname)){
							persname = data.data[0].data[0].persname;
							_html += "<tr class='success'><td class='title'>负责人：</td><td class='titleDesc'>" + persname + "</td></tr>";
						}else{
							_html += "<tr class='success'><td class='title'>负责人：</td><td class='titleDesc'> </td></tr>";
						}
					}catch (e) {
						
					}finally {
						
					}
				}
			});
		}else if(opetype=="HHQY"||opetype=="WZHH"){
			var entId  = $("#entId").attr("ent");
			var name = " ";
			$.ajax({
				url:"../entEnt/zxhhr.do",
				type:"post",
				data:{id:entId},
				dataType:"json",
				async : false,//取消异步请求
				success:function(data){
					var persname =  " ";
					try {
						if(!$.isEmptyObject(data.data[0].data)){
							if(isNotEmpty(data.data[0].data[0].persname)){
								var arr = data.data[0].data ;
								name =  data.data[0].data[0].persname;
								for (var j = 1; j < arr.length; j++) {
									name +=  "," + arr[j].persname;
								}
								//persname = data.data[0].data[0].persname;
							}
							_html += "<tr class='success'><td class='title'>执行事务合伙人：</td><td class='titleDesc'>" + name + "</td></tr>";
						}else{
							_html += "<tr class='success'><td class='title'>执行事务合伙人：</td><td class='titleDesc'></td></tr>";
						}
					} catch (e) {
					}finally {
						
					}
				}
			});
			
		}else if(opetype=='GRDZ'){
			var entId  = $("#entId").attr("ent");
			$.ajax({
				url:"../entEnt/fddbr.do",
				type:"post",
				data:{id:entId},
				dataType:"json",
				async : false,//取消异步请求
				success:function(data){
					var persname =  " ";
					try {
						if(isNotEmpty(data.data[0].data[0].persname)){
							persname = data.data[0].data[0].persname;
							_html += "<tr class='success'><td class='title'>投资人：</td><td class='titleDesc'>" + persname + "</td></tr>";
						}else{
							_html += "<tr class='success'><td class='title'>投资人：</td><td class='titleDesc'> </td></tr>";
						}
					} catch (e) {
					}finally {
						
					}
				}
			});
		}else if(opetype=='WGDB'){
			var entId  = $("#entId").attr("ent");
			$.ajax({
				url:"../entEnt/fddbr.do",
				type:"post",
				data:{id:entId},
				dataType:"json",
				async : false,//取消异步请求
				success:function(data){
					var persname =  " ";
					try {
						if(isNotEmpty(data.data[0].data[0].persname)){
							persname = data.data[0].data[0].persname;
						}
						_html += "<tr class='success'><td class='title'>首席代表：</td><td class='titleDesc'>" + persname + "</td></tr>";
					} catch (e) {
					}finally {
						
					}
				}
			});
		}else if(opetype=='GT'){
			var entId  = $("#entId").attr("ent");
			$.ajax({
				url:"../entEnt/jyz.do",
				type:"post",
				data:{id:entId},
				dataType:"json",
				async : false,//取消异步请求
				success:function(data){
					var persname =  " ";
					try {
						if(isNotEmpty(data.data[0].data[0].persname)){
							persname = data.data[0].data[0].persname;
						}
						_html += "<tr class='success'><td class='title'>经营者：</td><td class='titleDesc'>" + persname + "</td></tr>";
					} catch (e) {
					}finally {
						
					}
				}
			});
		}
		if(opetype=="GS"){
			_html += "<tr class='success'><td class='title'>认缴注册资本（万元）：</td><td class='titleDesc'>" + regcap + "</td></tr>";
		}else if(opetype=="WZGS"){
			_html += "<tr class='success'><td class='title'>认缴注册资本：</td><td class='titleDesc'>"+biZhong + " " + regcap + "万元</td></tr>";
		}else if(opetype=="NZFR"){
			_html += "<tr class='success'><td class='title'>注册资金（万元）：</td><td class='titleDesc'>  " + regcap + "  </td></tr>";
		}else if(opetype=="HHQY"){
			_html += "<tr class='success'><td class='title'>认缴出资额（万元）：</td><td class='titleDesc'>  " + regcap + "  </td></tr>";
		}else if(opetype=="GRDZ"){
			_html += "<tr class='success'><td class='title'>出资额（万元） ：</td><td class='titleDesc'>  " + regcap + " </td></tr>";
		}else if(opetype=="HZS"){
			_html += "<tr class='success'><td class='title'>成员出资总额 （万元）：</td><td class='titleDesc'>  " + regcap + "  </td></tr>";
		}else if(opetype=="GT"){
			_html += "<tr class='success'><td class='title'>资金数额（万元）：</td><td class='titleDesc'>  " + regcap + "  </td></tr>";
		}else if(opetype=="SLYB"){
			_html += "<tr class='success'><td class='title'>加工装配金额</td><td class='titleDesc'>" +biZhong+" "+ regcap + " 万元</td></tr>";
		}
		if(opetype=="SLYB"){
			_html += "<tr class='success'><td class='title'>经营方式：</td><td class='titleDesc'>" + enttype + "</td></tr>";
		}else{
			_html += "<tr class='success'><td class='title'>经济性质：</td><td class='titleDesc'>" + enttype + "</td></tr>";
		}
		if(opetype=="GT"){
			_html += "<tr class='success'><td class='title'>组成形式：</td><td class='titleDesc'>" + compform + "</td></tr>";
		}else if(opetype=="WGDB"||opetype=="HZS"||opetype=="HZSFZ"){
			_html += "<tr class='success'><td class='title'>业务范围：</td><td class='titleDesc'>" + opscope + "</td></tr>";
		}else if(opetype=="WGJY"||opetype=="SLYB"){
			_html += "<tr class='success'><td class='title'>经营范围：</td><td class='titleDesc'>" + opscope + "</td></tr>";
		}
		_html += "<tr class='success'><td class='title'>成立日期：</td><td class='titleDesc'>"+estdate + "</td></tr>";
		if(opetype=="GS"||opetype=="FGS"||opetype=="WZGS"||opetype=="NZYY"||opetype=="HHFZ"||opetype=="WZHHFZ"||opetype=="GRDZFZ"||opetype=="WZFZ"){
			if(isNotEmpty(opto) && opto!="5000-01-01" && opto!="1900-01-01") {
				_html += "<tr class='success'><td class='title'>营业期限：</td><td class='titleDesc'>"+"自"+opfrom + "起至" + opto + "止"+ "</td></tr>";
			}else{
				_html += "<tr class='success'><td class='title'>营业期限：</td><td class='titleDesc'>"+"永续经营"+ "</td></tr>";
			}
		}else if(opetype=="HHQY"||opetype=="WZHH"){
			if(isNotEmpty(opto) && opto!="5000-01-01" && opto!="1900-01-01") {
				_html += "<tr class='success'><td class='title'>合伙期限：</td><td class='titleDesc'>"+"自"+opfrom + "起至" + opto + "止"+ "</td></tr>";
			}else{
				_html += "<tr class='success'><td class='title'>合伙期限：</td><td class='titleDesc'>"+"永续经营"+ "</td></tr>";
			}
		}else if(opetype=="NZFR"){
			if(isNotEmpty(opto) && opto!="5000-01-01" && opto!="1900-01-01") {
				_html += "<tr class='success'><td class='title'>经营期限：</td><td class='titleDesc'>"+"自"+opfrom + "起至" + opto + "止"+ "</td></tr>";
			}else{
				_html += "<tr class='success'><td class='title'>经营期限：</td><td class='titleDesc'>"+"永续经营"+ "</td></tr>";
			}
		}else if(opetype=="WGDB"){
			if(isNotEmpty(opto) && opto!="5000-01-01" && opto!="1900-01-01") {
				_html += "<tr class='success'><td class='title'>驻在期限：</td><td class='titleDesc'>"+"自"+opfrom + "起至" + opto + "止"+ "</td></tr>";
			}else{
				_html += "<tr class='success'><td class='title'>驻在期限：</td><td class='titleDesc'>"+"永续经营"+ "</td></tr>";
			}
		}else if(opetype=="WGJY"){
			if(isNotEmpty(opto) && opto!="5000-01-01" && opto!="1900-01-01") {
				_html += "<tr class='success'><td class='title'>证照有效期限：</td><td class='titleDesc'>"+"自"+opfrom + "起至" + opto + "止"+ "</td></tr>";
			}else{
				_html += "<tr class='success'><td class='title'>证照有效期限：</td><td class='titleDesc'>"+"永续经营"+ "</td></tr>";
			}
		}
		_html += "<tr class='success'><td class='title'>核准日期：</td><td class='titleDesc'>" + apprdate + "</td></tr>";
		
		
		var tid = $("#entJinYinYiChang").attr("entJinYinYiChang");
		/*if(opetype=="GT"){
			var yearnj = " ";
			 $.ajax({
					url : '../entEnt/nj.do',
					type : 'POST',
					dataType : 'json',
					async : false,//取消异步请求
					data : {
						"entId" : tid,
					},
					success:function(data){
						hideMask();
						var data = data.data[0].data;
						if (data.length!=0) {
							for (var i = 0; i < data.length; i++) {
								yearnj += " " + data[i].njyear + "年度已年检、";
							}
							yearnj = yearnj.substring(0, yearnj.length-1);
						}else{
						 yearnj = "无验照信息 ";	
						}
						
						_html += "<tr class='success'><td class='title'>个体验照情况 ：</td><td class='titleDesc'>"+yearnj+ "</td></tr>";
					}
			});
			
		}else if(opetype!="GT"&&opetype!="JT"){
			var yearnj = " ";
			 $.ajax({
					url : '../entEnt/nj.do',
					type : 'POST',
					dataType : 'json',
					async : false,//取消异步请求
					data : {
						"entId" : tid,
					},
					success:function(data){
						hideMask();
						var data = data.data[0].data;
						if (data.length!=0) {
							for (var i = 0; i < data.length; i++) {
								yearnj += " " + data[i].njyear + "年度已年检、";
							}
							yearnj = yearnj.substring(0, yearnj.length-1);
						}else{
							yearnb = "无年检信息";
						}
						
						_html += "<tr class='success'><td class='title'>年检情况：</td><td class='titleDesc'>"+yearnj+"</td></tr>";
					}
			});
		}*/
		
		
		
		if(opetype!="WGDB"&&opetype!="JT"){
			var tid = $("#entJinYinYiChang").attr("entJinYinYiChang");
			var opetype = $("#opetype").attr("opetype");
			var yearnb = " ";
			 $.ajax({
					url : '../entEnt/nb.do',
					type : 'POST',
					dataType : 'json',
					async : false,//取消异步请求
					data : {
						"pripid" : tid,
						"opetype":opetype
					},
					success:function(data){
						//hideMask();
						var data = data.data[0].data;
						if (data.length!=0) {
							for (var i = 0; i < data.length; i++) {
								yearnb += " " + data[i].ancheyear + "年报已公示、";
							}
							yearnb =  yearnb.substring(0, yearnb.length-1);
						}else{
							yearnb = "无年报信息";
						}
						
						_html += "<tr class='success'><td class='title'>年报情况：</td><td class='titleDesc'>"+yearnb+"</td></tr>";
					}
			});
		}
		
		
		if(entstatus=="撤销登记"){
			var temp = unifsocicrediden;
			var temp2 = regno;
			
			if(isEmpty(temp)){
				temp = "";
			}
			if(isEmpty(temp2)){
				temp2 = "";
			}
			var urls = temp2+"&unifsocicrediden="+temp;
			
			_html += "<tr class='success'><td class='title'>主体状态：</td><td class='titleDesc'>"+entstatus + "<a href='https://amr.sz.gov.cn/foodsupervise/cxInfoServlet?regno="+urls+"' target='_blank' style='margin-left:10px; color:red;'>点击查看撤销详请</a></td></tr>";
		}else{
			_html += "<tr class='success'><td class='title'>主体状态：</td><td class='titleDesc'>"+entstatus + "</td></tr>";
		}
		
		
		
		
		
		
		if(opetype=="GS"||opetype=="HHQY"||opetype=="WZHH"||opetype=="WZGS"){
			var id  = $("#entId").attr("ent");
			var branch = " ";
			$.ajax({
				url : '../entEnt/branch.do',
				type : 'POST',
				dataType : 'json',
				async : false,//取消异步请求
				data : {
					"id" : id
				},
				success : function(data) {
					if (isNotEmpty(data.data[0].data[0].content)) {
						branch = data.data[0].data[0].content;
					}
				}
			});
			_html += "<tr class='success'><td class='title'>分支机构：</td><td class='titleDesc'>"+branch + "</td></tr>";
		}
		_html += "<tr class='success'><td class='title'>备注：</td><td class='titleDesc'>"+remark + "</td></tr>";
		$("#panel-1 table").empty().append(_html);

	}
}






/*许可经营信息*/
function temp1(data) {
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").remove();
		$("#tagsSwitch").removeClass("hide").addClass("show");
		
		/*CBUITEM,PABUITEM*/
		var cbuitem = " ";
		if (isNotEmpty(data[0].cbuitem)) {
			cbuitem = data[0].cbuitem;
		}
		var pabuitem = " ";
		if (isNotEmpty(data[0].pabuitem)) {
			pabuitem = data[0].pabuitem;
		}
		
		var entName = $("#entName").attr("entname");
		$("#panel-2").find("h2").html(entName+"的许可经营信息");
		
		var _html = "";
		_html += "<tr class='active'><td class='title centerDesc'>一般经营项目：</td><td class='titleDesc'>" + cbuitem + "</td></tr>";
		
		if(isNotEmpty(pabuitem)){
			_html += "<tr class='active'><td class='title centerDesc'>许可经营项目：</td><td class='titleDesc'><span style='color:red'>以下项目涉及应取得许可审批的，须凭相关审批文件方可经营:</span><br/>" + pabuitem + "</td></tr>";
		}else{
			_html += "<tr class='active'><td class='title centerDesc'>许可经营项目：</td><td class='titleDesc'>" + pabuitem + "</td></tr>";
		}

		$("#panel-2 table").empty().append(_html);
	}
}



/*股东信息*/
function temp2(data) {
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").remove();
		$("#tagsSwitch").removeClass("hide").addClass("show");
		
		var entName = $("#entName").attr("entname");
		$("#panel-3").find("h2").html(entName+"股东信息");
		var _html = "";
		_html+="<tr><td class='ctitle'>股东名称</td><td class='ctitle'>出资额(万元)</td><td class='ctitle'>出资比例(%)</td><td class='ctitle'>股东属性</td><td class='ctitle'>股东类别</td></tr>";
		for(var i=0;i<data.length;i++){
		var inv = " ";
		if(isNotEmpty(data[i].inv)){
			inv = data[i].inv;
		}
		var conprop = " ";
		if(isNotEmpty(data[i].conprop)){
			conprop = data[i].conprop;
		}
		var subconam = " ";
		if(isNotEmpty(data[i].subconam)){
			subconam = data[i].subconam;
		}
		
		if(isNotEmpty(data[i].invatt)){
			invatt = data[i].invatt;
		}
		var invtype = " ";
		if(isNotEmpty(data[i].invtype)){
			invtype = data[i].invtype;
		}
		_html += "<tr class='active'><td>"+inv+"</td><td>"+subconam +"</td><td>"+conprop+"</td><td>"+invatt+"</td><td>"+invtype+"</td></tr>";
		}
		$("#panel-3 table").empty().append(_html);
	}
}

//合伙人信息--HHR
function temp21(data) {
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		
		var entName = $("#entName").attr("entname");
		$("#panel-3").find("h2").html(entName+"合伙人信息");
		
		var _html = "";
		var count = 0;
		//_html +="<tr><td class='ctitle'>合伙人名称</td><td class='ctitle'>认缴出资额（万元</td><td class='ctitle'>出资比例（%）</td><td class='ctitle'>合伙人属性</td></tr>";
		_html +="<tr><td class='ctitle'>合伙人名称</td><td class='ctitle'>认缴出资额（万元）</td><td class='ctitle'>出资比例（%）</td><td class='ctitle'>合伙人属性</td><td class='ctitle'>合伙人类别</td><td class='ctitle'>委派代表</td></tr>";
		for(var i=0;i<data.length;i++){
			var id = " ";
			if(isNotEmpty(data[i].id)){
				id = data[i].id;
			}
	
			var inv = " ";
			if(isNotEmpty(data[i].inv)){
				inv = data[i].inv;
			}
			var conprop = " ";
			if(isNotEmpty(data[i].conprop)){
				conprop = data[i].conprop;
			}
			var subconam = " ";
			if(isNotEmpty(data[i].subconam)){
				subconam = data[i].subconam;
			}
			var invatt = " ";
			if(isNotEmpty(data[i].invatt)){
				invatt = data[i].invatt;
			}
			var invtype = " ";
			if(isNotEmpty(data[i].invtype)){
				invtype = data[i].invtype;
			}
			
			var responway = " ";
			if(isNotEmpty(data[i].responway)){
				responway = data[i].responway;
			}
			
			var iswpdbFlag = " ";
			if(isNotEmpty(data[i].exeaffsign)){
				iswpdbFlag = data[i].exeaffsign;
			}
			var wpdb = " ";
			var entId = $("#entId").attr("ent");
			
			if((iswpdbFlag=="是"&&invatt=="本地企业")||(iswpdbFlag=="是"&&invatt=="其他投资者")){//委派代表
				$.ajax({
					url : '../entEnt/wpdb.do',
					type : 'POST',
					dataType : 'json',
					async : false,//取消异步请求
					data : {
						"entId":entId,
						"id":id,
						invatt:invatt
					},
					async : false,//取消异步请求
					success:function(data){
						if(!$.isEmptyObject(data.data[0].data)){
							for (var i = 0; i < data.data[0].data.length; i++) {
								wpdb = data.data[0].data[i].persname;
								_html += "<tr class='active "+i+"'><td>"+inv+"</td><td>"+subconam +"</td><td>"+conprop+"</td><td>"+invatt+"</td><td>"+responway+"</td><td>"+wpdb+"</td></tr>";
							}
						}else{
							_html += "<tr class='active "+i+"'><td>"+inv+"</td><td>"+subconam +"</td><td>"+conprop+"</td><td>"+invatt+"</td><td>"+responway+"</td><td></td></tr>";
						}
					}
				});
			}else{
				_html += "<tr class='active'><td>"+inv+"</td><td>"+subconam +"</td><td>"+conprop+"</td><td>"+invatt+"</td><td>"+responway+"</td><td>"+wpdb+"</td></tr>";
			}
			//_html += "<tr class='active'><td>"+inv+"</td><td>"+subconam +"</td><td>"+conprop+"</td><td>"+invatt+"</td><td>"+responway+"</td><td>"+wpdb+"</td></tr>";
		}
			
		$("#panel-3 table").empty().append(_html);
	}
}



//成员信息--合作社的HZS
function temp22(data) {
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").remove();
		$("#tagsSwitch").removeClass("hide").addClass("show");
		
		var entName = $("#entName").attr("entname");
		$("#panel-3").find("h2").html(entName+"的成员信息");
		var _html = "";
		_html +="<tr><td class='ctitle'>成员名称</td><td class='ctitle'>认缴出资额（万元</td><td class='ctitle'>出资比例（%）</td><td class='ctitle'>成员类型</td></tr>";
		for(var i=0;i<data.length;i++){
		var inv = " ";
		if(isNotEmpty(data[i].inv)){
			inv = data[i].inv;
		}
		var conprop = " ";
		if(isNotEmpty(data[i].conprop)){
			conprop = data[i].conprop;
		}
		var subconam = " ";
		if(isNotEmpty(data[i].subconam)){
			subconam = data[i].subconam;
		}
		
		if(isNotEmpty(data[i].invatt)){
			invatt = data[i].invatt;
		}
		var invtype = " ";
		if(isNotEmpty(data[i].invtype)){
			invtype = data[i].invtype;
		}
		_html += "<tr class='active'><td>"+inv+"</td><td>"+subconam +"</td><td>"+conprop+"</td><td>"+invatt+"</td></tr>";
		}
		$("#panel-3 table").empty().append(_html);
	}
}




//成员信息 -- 集团--JT
function temp23(data) {
	
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		
		var entName = $("#entName").attr("entname");
		$("#panel-3").find("h2").html(entName+"的成员信息");
		var _html = "";
		_html +="<tr><td class='ctitle'>成员名称</td><td class='ctitle'>成员属性</td><td class='ctitle'>注册号</td><td class='ctitle'>注册资本(万元)</td><td class='ctitle'>母公司持股比例(%)</td></tr>";
		for(var i=0;i<data.length;i++){
		var inv = " ";
		if(isNotEmpty(data[i].inv)){
			inv = data[i].inv;
		}
		var conprop = " ";
		if(isNotEmpty(data[i].conprop)){
			conprop = data[i].conprop;
		}
		var subconam = " ";
		if(isNotEmpty(data[i].subconam)){
			subconam = data[i].subconam;
		}
		
		if(isNotEmpty(data[i].invatt)){
			invatt = data[i].invatt;
		}
		var invtype = " ";
		if(isNotEmpty(data[i].invtype)){
			invtype = data[i].invtype;
		}
		var cerno = " ";
		if(isNotEmpty(data[i].cerno)){
			cerno = data[i].cerno;
		}
		
		_html += "<tr class='active'><td>"+inv+"</td><td>"+invtype +"</td><td>"+cerno+"</td><td>"+subconam+"</td><td>"+conprop+"</td></tr>";
		}
		$("#panel-3 table").empty().append(_html);
	}
}




//三来一补SLYB
function temp24(data) {
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").remove();
		$("#tagsSwitch").removeClass("hide").addClass("show");
		
	    var entName = $("#entName").attr("entname");
		$("#panel-3").find("h2").html(entName+"的协议方信息");
		var _html = "";
		//显示标题
		_html +="<tr><td class='ctitle'>协议方名称</td><td class='ctitle'>协议方属性</td><td class='ctitle'>协议方类别</td></tr>";
		for(var i=0;i<data.length;i++){
		var inv = " ";
		if(isNotEmpty(data[i].inv)){
			inv = data[i].inv;
		}
		
		if(isNotEmpty(data[i].invatt)){
			invatt = data[i].invatt;
		}
		var invtype = " ";
		if(isNotEmpty(data[i].invtype)){
			invtype = data[i].invtype;
		}
		_html += "<tr class='active'><td>"+inv+"</td><td>"+invatt +"</td><td>"+invtype+"</td></tr>";
		}
		$("#panel-3 table").empty().append(_html);
	}
}





/*主要人员信息*/
function temp3(data){
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		
		var entName = $("#entName").attr("entname");
		$("#panel-4").find("h2").html(entName + "的成员信息");
		
		var _html = "";
		_html +="<tr><td class='ctitle'>姓名</td><td class='ctitle'>职务</td><td class='ctitle'>产生方式</td></tr>";
		
		for(var i=0;i<data.length;i++){
			var posbrform = " ";
			var name = " ";
			var post = " ";
			
			if(isNotEmpty(data[i].name)){
				name = data[i].name
			}
			if(isNotEmpty(data[i].posbrform)){
				posbrform = data[i].posbrform
			}
			if(isNotEmpty(data[i].post)){
				post = data[i].post
			}
			_html += "<tr class='active'><td>"+name+"</td><td>"+post+"</td><td>"+posbrform+"</td></tr>";
		}
		
		

		$("#panel-4 table").empty().append(_html);
	}
}


/*变更信息*/
function temp4(data, id){
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		
		var entName = $("#entName").attr("entname");
		$("#panel-5").find("h2").html(entName + "的变更信息");
		
		var _html = "";
		_html = "<tr><td class='bgTitle'>变更日期</td><td class='bgTitle'>变更事项</td><td class='bgTitle'>&nbsp;</td></tr>";
		for(var i=0;i<data.length;i++){
			var altdate = " ";
			var altitems = " ";
			var alttime = " ";
			var regino = " ";
			if(isNotEmpty(data[i].altdate)){
				altdate = data[i].altdate
			}
			if(isNotEmpty(data[i].altitems)){
				altitems = data[i].altitems
			}
			if(isNotEmpty(data[i].alttime)){
				alttime = data[i].alttime
			}
			if(isNotEmpty(data[i].regino)){
				regino = data[i].regino
			}
			
			_html += '<tr class="active"><td class="alingCenter" style="width:20%">'+altdate+'</td><td class="alingLeft" style="width:60%">'+altitems+'</td><td class="alingLeft" style="width:100px"><a style="color:blue;text-decoration:underline" onclick="detail(\''+id+'\',\''+alttime+'\',\''+entName+'\',\''+altdate+'\',\''+regino+'\')">'+'查看打印信息'+'</a></td></tr>';
		}
		$("#panel-5 table").empty().append(_html);
	}
}

function detail(id,alttime,entName,altdate,regino){
	//window.open('biangeng.html?id='+id+"&alttime="+alttime+"&entName="+encode(entName)+"&altdate="+altdate);
	window.open('biangeng.html?id='+id+"&alttime="+alttime+"&entName="+encodeURIComponent(entName)+"&altdate="+altdate+"&regino="+regino);
}

/*股权质押信息*/
function temp5(data){
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	}else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		
		var entName = $("#entName").attr("entname");
		$("#panel-6").find("h2").html(entName + "的股权质押信息");
		var _html = "";
		for(var i=0;i<data.length;i++){
			if(i!=0){
				_html += "<tr><td class='seprateLine'></td><td class='seprateLine'></td></tr>";
			}
			var limityyvalue = " ";
			var limitstatus = " ";
			//var Limitsm = " ";
			var limitsm = " ";
			var memo = " ";
			var hztime = " ";
			
			if (isNotEmpty(data[i].limityyvalue)) {
				limityyvalue = data[i].limityyvalue;
			}
			if (isNotEmpty(data[i].limitstatus)) {
				limitstatus = data[i].limitstatus;
			}
			if (isNotEmpty(data[i].limitsm)) {
				limitsm = data[i].limitsm;
			}
			if (isNotEmpty(data[i].memo)) {
				memo = data[i].memo;
			}
			/*if (isNotEmpty(data[i].limittimebegin)) {
				hztime = data[i].hztime.substring(0,10);
			}*/
			if (isNotEmpty(data[i].hztime)) {   //核准日期显示判断条件
				hztime = data[i].hztime.substring(0,10);
			}
			_html += "<tr class='active'><td class='title'>限制原因：</td><td class='titleDesc'>" + limityyvalue + "</td></tr>";
			_html += "<tr class='active'><td class='title'>限制状态：</td><td class='titleDesc'>" + limitstatus + "</td></tr>";
			_html += "<tr class='active'><td class='title'>限制说明：</td><td class='titleDesc'>" + limitsm + "</td></tr>";
			_html += "<tr class='active'><td class='title'>备注信息：</td><td class='titleDesc'>" + memo + "</td></tr>";
			_html += "<tr class='active'><td class='title'>核准时间：</td><td class='titleDesc'>" + hztime + "</td></tr>";
			
		}
		$("#panel-6 table").empty().append(_html);
	}

}

/*动产抵押信息*/
function temp6(data){
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		var entName = $("#entName").attr("entname");
		$("#panel-7").find("h2").html(entName + "的动产抵押信息");
		
		var _html = "";
		for(var i=0;i<data.length;i++){
			if(i!=0){
				_html += "<tr><td class='seprateLine'></td><td class='seprateLine'></td></tr>";
			}
			var RecordID = " "
			var MorRegCNO = " ";
			var Mortgagor = " ";
			var Mortgagee = " ";
			var GuaNameQuan = " ";
			var OriMorContNO = " ";
			var AuthDate = " ";
			var RegStatus = " ";
			
			if (isNotEmpty(data[i].recordid)) {
				RecordID = data[i].recordid;
				$.ajax({
					url : '../entEnt/dcdy.do',
					type : 'POST',
					dataType : 'json',
					async : false,//取消异步请求
					data : {
						"id" : RecordID,
						"flag" : "1"
					},
					success : function(data) {
						var data = data.data[0].data;
						if (isNotEmpty(data[0].mortgagor)) {
							Mortgagor = data[0].mortgagor;
						}
					}
				});
				$.ajax({
					url : '../entEnt/dcdy.do',
					type : 'POST',
					dataType : 'json',
					async : false,//取消异步请求
					data : {
						"id" : RecordID,
						"flag" : "2"
					},
					success : function(data) {
						var data = data.data[0].data;
						if (isNotEmpty(data[0].mortgagee)) {
							Mortgagee = data[0].mortgagee;
						}

					}
				});
				$.ajax({
					url : '../entEnt/dcdy.do',
					type : 'POST',
					dataType : 'json',
					async : false,//取消异步请求
					data : {
						"id" : RecordID,
						"flag" : "3"
					},
					success : function(data) {
						var data = data.data[0].data;
						if (isNotEmpty(data[0].guanamequan)) {
							GuaNameQuan = data[0].guanamequan;
						}
						for(var i=1;i<data.length;i++){
							if (isNotEmpty(data[i].guanamequan)) {
								GuaNameQuan = GuaNameQuan + "," + data[i].guanamequan;
							}
						}
					}
				});
			}
			if (isNotEmpty(data[i].morregcno)) {
				MorRegCNO = data[i].morregcno;
			}
			if (isNotEmpty(data[i].orimorcontno)) {
				OriMorContNO = data[i].orimorcontno;
			}

			if (isNotEmpty(data[i].authdate)) {
				AuthDate = data[i].authdate.substring(0,10);
			}
			if (isNotEmpty(data[i].regstatus)) {
				RegStatus = data[i].regstatus;
			}
			_html += "<tr class='active'><td class='title'>登记证号：</td><td class='titleDesc'>" + MorRegCNO + "</td></tr>";
			_html += "<tr class='active'><td class='title'>抵押人名称：</td><td class='titleDesc'>" + Mortgagor + "</td></tr>";
			_html += "<tr class='active'><td class='title'>抵押权人名称：</td><td class='titleDesc'>" + Mortgagee + "</td></tr>";
			_html += "<tr class='active'><td class='title'>抵押物名称与数量：</td><td class='titleDesc'>" + GuaNameQuan + "</td></tr>";
			_html += "<tr class='active'><td class='title'>抵押合同编号：</td><td class='titleDesc'>" + OriMorContNO + "</td></tr>";
			_html += "<tr class='active'><td class='title'>核准时间：</td><td class='titleDesc'>" + AuthDate + "</td></tr>";
			_html += "<tr class='active'><td class='title'>登记证状态：</td><td class='titleDesc'>" + RegStatus + "</td></tr>";
			
		}
		$("#panel-7 table").empty().append(_html);
	}
}

/*法院冻结信息*/
function temp7(data){
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		var entName = $("#entName").attr("entname");
		$("#panel-8").find("h2").html(entName + "的法院冻结信息");
		var _html = "";
		for(var i=0;i<data.length;i++){
			if(i!=0){
				_html += "<tr><td class='seprateLine'></td><td class='seprateLine'></td></tr>";
			}
			var limityyvalue = " ";
			var limittimebegin = " ";
			var limittimeend = " ";
			var limitstatus = " ";
			var Limitsm = " ";
			var memo = " ";
			
			if (isNotEmpty(data[i].limityyvalue)) {
				limityyvalue = data[i].limityyvalue;
			}
			if (isNotEmpty(data[i].limittimebegin)) {
				limittimebegin = data[i].limittimebegin.substring(0,10);
			}
			if (isNotEmpty(data[i].limittimeend)) {
				limittimeend = data[i].limittimeend.substring(0,10);
			}
			if (isNotEmpty(data[i].limitstatus)) {
				limitstatus = data[i].limitstatus;
			}
			if (isNotEmpty(data[i].Limitsm)) {
				Limitsm = data[i].Limitsm;
			}
			if (isNotEmpty(data[i].memo)) {
				memo = data[i].memo;
			}
			_html += "<tr class='active'><td class='title'>限制原因：</td><td class='titleDesc'>" + limityyvalue + "</td></tr>";
			_html += "<tr class='active'><td class='title'>限制时间：</td><td class='titleDesc'>" +"自"+ limittimebegin +"至"+ limittimeend + "</td></tr>";
			_html += "<tr class='active'><td class='title'>限制状态：</td><td class='titleDesc'>" + limitstatus + "</td></tr>";
			_html += "<tr class='active'><td class='title'>限制说明：</td><td class='titleDesc'>" + Limitsm + "</td></tr>";
			_html += "<tr class='active'><td class='title'>备注信息：</td><td class='titleDesc'>" + memo + "</td></tr>";
			
		}
		$("#panel-8 table").empty().append(_html);
	}
}

/*经营异常信息*/
function temp8(data) {
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		var entName = $("#entName").attr("entname");
		$("#panel-9").find("h2").html(entName + "的经营异常信息");
		
		var _html ="";
		//_html +="<tr><td width='20%'>列入经营异常名录原因</td><td width='10%'>列入日期</td><td>作出决定机关(列入)</td><td width='20%'>移出经营异常名录原因</td><td width='10%'>移出日期</td><td>作出决定机关(移出)</td></tr>";
		_html +="<tr><td class='yiChangTitle' width='7%'>列入日期</td><td class='yiChangTitle' width='20%'>列入经营异常名录原因</td><td class='yiChangTitle' width='7%'>移出日期</td><td class='yiChangTitle' width='20%'>移出经营异常名录原因</td></tr>";
		for(var i=0;i<data.length;i++){
			/*if(i!=0){
				_html += "<tr><td class='seprateLine'></td><td class='seprateLine'></td></tr>";
			}*/
			var createtime = " "; 
			if(isNotEmpty(data[i].createtime)){
				createtime = data[i].createtime;
			}
			var addreason = " "; 
			if(isNotEmpty(data[i].addreason)){
				addreason = data[i].addreason;
			}
			
			var addJiGuan = " "; 
			if(isNotEmpty(data[i].addJiGuan)){
				addJiGuan = data[i].addJiGuan;
			}
			
			
			var removetime = " "; 
			if(isNotEmpty(data[i].removetime)){
				removetime = data[i].removetime;
			}
			
			var removedate = " "; 
			if(isNotEmpty(data[i].removedate)){
				removedate = data[i].removedate;
			}
			
			
			var removereason = " "; 
			if(isNotEmpty(data[i].removereason)){
				removereason = data[i].removereason;
			}
			
			var removeJiGuan = " "; 
			if(isNotEmpty(data[i].removeJiGuan)){
				removeJiGuan = data[i].removeJiGuan;
			}
			//_html += "<tr class='active'><td class='title' >标记为经营异常状态时间：</td><td>"+createtime+"</td></tr>";
			//_html += "<tr class='active'><td class='title'>标记为经营异常状态事由：</td><td>"+remark+"</td></tr>";
			//_html +="<tr><td>"+addreason+"</td><td>"+createtime+"</td><td>"+addJiGuan+"</td><td>"+removereason+"</td><td>"+removedate+"</td><td>"+removeJiGuan+"</td></tr>";
			//_html +="<tr><td>"+createtime +"</td><td>"+addreason+"</td><td>"+addJiGuan+"</td><td>"+removereason+"</td><td>"+removedate+"</td><td>"+removeJiGuan+"</td></tr>";
			_html +="<tr><td>"+createtime +"</td><td>"+addreason+"</td><td>"+removedate+"</td><td>"+removereason+"</td></tr>";
		}
		$("#panel-9 table").empty().append(_html);
	}
}

/*严重违法失信信息*/
function temp10(data) {
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		var entName = $("#entName").attr("entname");
		$("#panel-11").find("h2").html(entName + "的严重违法失信信息");
		
		var _html ="";
		_html +="<tr><td class='yanZhongTitle' width='7%'>列入日期</td><td class='yanZhongTitle' width='20%'>列入严重违法失信原因</td><td class='yanZhongTitle' width='7%'>移出日期</td><td class='yanZhongTitle' width='20%'>移出严重违法失信原因</td></tr>";
		for(var i=0;i<data.length;i++){
			var createtime = " "; 
			if(isNotEmpty(data[i].createtime)){
				createtime = data[i].createtime;
			}
			var addreason = " "; 
			if(isNotEmpty(data[i].addreason)){
				addreason = data[i].addreason;
			}
			
			var addDept = " "; 
			if(isNotEmpty(data[i].addDept)){
				addDept = data[i].addDept;
			}
			
			var removetime = " "; 
			if(isNotEmpty(data[i].removetime)){
				removetime = data[i].removetime;
			}
			
			/*var removedate = " "; 
			if(isNotEmpty(data[i].removedate)){
				removedate = data[i].removedate;
			}*/
			
			var removereason = " "; 
			if(isNotEmpty(data[i].removereason)){
				removereason = data[i].removereason;
			}
			
			var removeDept = " "; 
			if(isNotEmpty(data[i].removeDept)){
				removeDept = data[i].removeDept;
			}
			_html +="<tr><td>"+createtime +"</td><td>"+addreason+"</td><td>"+removetime+"</td><td>"+removereason+"</td></tr>";
		}
		$("#panel-11 table").empty().append(_html);
	}
}

/*网站信息*/
function temp9(data) {
	// var data = data.data[0].data;
	if ($.isEmptyObject(data)) {
		alert("没有对应的企业信息");
	} else {
		$("#myForm1").removeClass("formHeight");
		$("#tips").removeClass("show").addClass("hide");
		$("#tagsSwitch").removeClass("hide").addClass("show");
		//var entName = $("#entName").attr("entName");
		//$("#panel-10").find("h2").html(entName + "的网站信息");
		
		var _html = "";
		$("#panel-10 table").empty().append(_html);
	}
 }


function isEmpty(val) {
	val = $.trim(val);
	if (val == null)
		return true;
	if (val == undefined || val == 'undefined')
		return true;
	if (val == "")
		return true;
	if (val.length == 0)
		return true;
	if (!/[^(^\s*)|(\s*$)]/.test(val))
		return true;
	return false;
}

function isNotEmpty(val) {
	return !isEmpty(val);
}

////
/*
powere 
by rog.pub
github at https://github.com/9442478/
lay.css
*/
////