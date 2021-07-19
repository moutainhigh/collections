//只初始化第一层级
$(function(){
	$.ajax({
		url : '/gdsjzx/admin/getFunTreeByUser.do',//
		async: true,
		data : {
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(datajson) {
			initMenu(datajson);
			loginWin();
		},
		error : function() {
			alert("页面异常！请重新加载");
		}
	});
	$('#contloads').hide();
	$('#inputBox').keydown(function(e){
		if (e.keyCode === 13){
			allaction();
		}
		/* if('${search}' !=""|| '${search}' !=undefined ||'${search}'!=null){
			$('#inputBox').val('${search}');
		}; */
	});
	$('#showTip').click(function(){
		if($('#tipShow').css("display")=='none'){
			$('#tipShow').css("display","block");
		}else{
			$('#tipShow').css("display","none");
		}
	});
});
//组织菜单方法
function initMenu(datajson){
	var n;
	//2016/09/12 修改菜单栏加载逻辑；修改菜单栏中'登录'默认id为0；修改是否登录用loginName判断（单纯用li数量判断会因为有些菜单栏不需要登录也可以可见导致错误）
	for(var i=0;i<datajson.length;i++){
		n=datajson[i];
		if(n.pid==0 && n.id != '0'){
			if(n.title=="首页"){
				$('<li><a code="xxx" href="javascript:openMenu(encodeURI(\''+contextPath+n.url+"?name="+n.title+"&id="+n.id+'\'),\''+n.title+'\');"><span style="background:url(\'/gdsjzx/page/sjbd/img/3.png\');top:17px;display:inline-flex;height:16px;position:relative;">&#12288;</span>'+n.title+'</a></li>' ).appendTo($('#nav'));
			}else if(n.title=="数据分析"){
				$('<li><a code="xxx" href="javascript:openMenu(encodeURI(\''+contextPath+n.url+"?name="+n.title+"&id="+n.id+'\'),\''+n.title+'\');"><span style="background:url(\'/gdsjzx/page/sjbd/img/4.png\');top:17px;display:inline-flex;height:16px;position:relative;">&#12288;</span>'+n.title+'</a></li>' ).appendTo($('#nav'));
			}else if(n.title=="报表管理"){
				$('<li><a code="xxx" href="javascript:openMenu(encodeURI(\''+contextPath+n.url+"?name="+n.title+"&id="+n.id+'\'),\''+n.title+'\');"><span style="background:url(\'/gdsjzx/page/sjbd/img/5.png\');top:17px;display:inline-flex;height:16px;position:relative;">&#12288;</span>'+n.title+'</a></li>' ).appendTo($('#nav'));
			}else if(n.title=="数据服务"){
				$('<li><a code="xxx" href="javascript:openMenu(encodeURI(\''+contextPath+n.url+"?name="+n.title+"&id="+n.id+'\'),\''+n.title+'\');"><span style="background:url(\'/gdsjzx/page/sjbd/img/6.png\');top:17px;display:inline-flex;height:16px;position:relative;">&#12288;</span>'+n.title+'</a></li>' ).appendTo($('#nav'));
			}else{
				$('<li><a code="xxx" href="javascript:openMenu(encodeURI(\''+contextPath+n.url+"?name="+n.title+"&id="+n.id+'\'),\''+n.title+'\');"><span style="background:url(\'/gdsjzx/page/sjbd/img/7.png\');top:17px;display:inline-flex;height:16px;position:relative;">&#12288;</span>'+n.title+'</a></li>' ).appendTo($('#nav'));
			}
		}
		//菜单
		/*if(n.pid==0){
			if(i!=datajson.length-1){
				if(n.id!='0'){
					$('<li><a code="xxx" href="javascript:openMenu(encodeURI(\''+contextPath+n.url+"?name="+n.title+"&id="+n.id+'\'),\''+n.title+'\');">'+n.title+'</a></li>' ).appendTo($('#nav'));
				}else{
					$('<li><a class="theme-login" >'+n.title+'</a></li>' ).appendTo($('#nav'));
				}
			}else{	
				$('.headertop').append($('<b style="float:right;font-size:14px;margin-left:25px;">欢迎你！登陆人：'+loginName+'</b>'));
				$('<li><a onClick="quit()">退出</a></li>' ).appendTo($('#nav'));	
			}
		}*/
	} 
	/*if($('#nav li').length==0){
		$('<li><a class="theme-login" >登录</a></li>' ).appendTo($('#nav'));<span style="width:32px;height:32px;background:url(\'\');">&#12288;</span>
	}*/
	if(loginName == null || loginName == '' || loginName == undefined){
		$('<li><a class="theme-login" >登录</a></li>' ).appendTo($('#nav'));
	}else{
		//$('.headertop').append($('<b style="float:right;font-size:14px;margin-left:25px;">欢迎你！登陆人：'+loginName+'</b>'));
		$('<li ><a style="width:60px;"><font color="white" size="1">'+loginName+'</font></a><a style="width:30px;color:white;" onClick="quit()"><font color="white" size="1">退出</font></a></li>' ).appendTo($('#nav'));	
		//$('<li></li>' ).appendTo($('#nav'));	
	}
	//更多
	//$('<li ><a  class="bri"  href="javascript:openMenu();">'+"更多"+'</a></li>').appendTo($('#nav'));
}
function openMenu(url,title){
	var h = $(window).height();
	var head = $('.headertop').height();
	var foot = $('#gwssiimg').height();
	var datah = h-head-foot;
	if(datah<615){
		datah = 615;
	}
	$('#Content').css("height",datah);
	$('#contloads').css("height",datah);
	$('#centerConten').css("height",datah);
	if(title=='首页'){
		$('#Content').css("height",620);
		$('#centerConten').css("height",620);
		$('#imgContainer').css("height",620);
		$('#trsback').show();
		$('#imgContainer').show();
		$('#container').hide();
		$('#container2').show();
		$('#centerConten').show();
		//$('#Content-Left').show();
		//$('#Content-Main').show();
		$('#contloads').hide();
		$('.results').hide();
		$('.p').hide();
	}else if(title=='检索帮助'){
		window.open(url,'_blank');
	}else{
		$('#Content').css("height",datah);
		$('#centerConten').css("height",datah);
		$('#contloads').css("height",datah);
		$('#imgContainer').css("height",datah);
		$('#trsback').hide();
		$('.results').hide();
		$('.p').hide();
		//$('#Content-Left').hide();
		//$('#Content-Main').hide();
		$('#container').hide();
		$('#container2').hide();
		$('#centerConten').show();
		$('#contloads').show();
		window.open(url,'contloads','');
		//$('#trsback').hide();
	}
	$('#imgContainer').attr("src","/gdsjzx/page/sjbd/img/8.png");
}
function loginWin(){
	$('.theme-login').click(function(){
		$('.theme-popover-mask').fadeIn(100);
		$('.theme-popover').slideDown(200);
	});
	$('.theme-poptit .close').click(function(){
		$('.theme-popover-mask').fadeOut(100);
		$('.theme-popover').slideUp(200);
	});
	$('#dlm').mousedown(function(){
        if($('#dlm').val()=="请输入账号"){
        	$('#dlm').val("");
        }
    });
	$('#dlm').click(function(){
		$("#login_msg").html('');
	});
	$('#mm').click(function(){
		$("#login_msg").html('');
	});
}
var url='/gdsjzx/datatrs/querydata.do';
var title="";
//点击搜索按钮
function allaction(){
	var pn = $("#inputBox").val();//文本框的id属性
	if($.trim(pn)!="" && '请输入内容'!=$.trim(pn)){
		 title=$.trim($('#whole').html());
		url='/gdsjzx/datatrs/querydata.do?type='+ encodeURI(encodeURI(title));
		returnOrgNo(url,"1",pn);
	} else {
		alert("输入栏不能为空");
	}
}
function stripscript(s) { 
	var pattern = new RegExp("[+-/()<>\\[\\]@!&^=*]"); ///+-=*/()<>[]@!&^
	//`~!@#\$\^&*()=+-|{}':;',\\[\\].<>/?~！@#￥……&\\*（）——|{}【】‘；：”“'。，、？  
	var rs = ""; 
	/*for (var i = 0; i < s.length; i++) { 
		rs = rs+s.substr(i, 1).replace(pattern, ''); 
	} */
	if(pattern.test(s)){
		rs="全文检索不支持特殊字符(包含:+、-、/、(、)、<、>、[、]、@、!、&、^、=、*)";
		alert(rs);
	}
	return rs;
}
function returnOrgNo(url,orgno,queryKeyWord){
	if(stripscript(queryKeyWord)!=''){
		return;
	}
	var pages;
	var listbegin;
	var listend;
	var pagescount;
	var pagedata;
	$('.results').empty();
	$('#centerConten').hide();
	//$('#trsback').hide();
	$('#container').hide();
	$('#container2').hide();
	$('#contloads').hide();
	//$('#imgContainer').hide();
	$('#imgContainer').css("height",$('#trsback').height());
	$('#imgContainer').attr("src","/gdsjzx/page/sjbd/img/q.png");
	$('.results').show();
	$.ajax({
		url : url,
		async: false,
		data : {
			pages : orgno,
			queryKeyWord : queryKeyWord
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code=='0'){
				pages = data.data.pageNo;
				temp=pages;
				listbegin = data.listbegin;
				listend = data.listend;
				pagescount = data.data.totalPageCount;
				pagedata=data.data.items;
				if(pagedata==undefined || pagedata ==null || pagedata.length==0){
					$(".results").css("height","200");
					$('<div style="text-align:center;"><a style="color:red;">你搜索的内容为空!</a><div>').appendTo($('.results'));
					$('.p').hide();
				}else{
					$('.p').show();
					$(".results").css("height","auto");
					trsShow(pages,temp,listbegin,listend,pagescount,pagedata);
				}
			}else{
				$(".results").css("height","200");
				$('<div style="text-align:center;"><a style="color:red;">'+data.msg+'</a><div>').appendTo($('.results'));
				$('.p').hide();
			}
		},
		error : function() {
			$(".results").css("height","200");
			$('<div style="text-align:center;"><a style="color:red;">检索trs库出错!</a><div>').appendTo($('.results'));
			$('.p').hide();
		}
	});

}
function trsShow(pages,temp,listbegin,listend,pagescount,pagedata){
	if($(".page")!=undefined){
		$(".page").remove();
		$("<div class='page'></div>").appendTo($(".p"));
	}

	if($('.sous_list')){
		$('.sous_list').remove();
		$('html,body').animate({scrollTop:0},1000);//回到顶端
		$("<div class='sous_list'></div>").appendTo($(".results"));
	}

	var postalCode="";
	var tel="";
	//12315主题的trs内容
	if(title=='案件信息'){
		for(var i=0;i<pagedata.length;i++){
			$('<div class="sous_list" ><div><h3><a onclick="openWindow(\''+contextPath+'/page/trs/caseInfo.jsp?'+pagedata[i].url+'\')" href="javascript:void(0);" >'+pagedata[i].casename+'&nbsp;&nbsp;'+pagedata[i].caseno+'</a></h3>'+
					'<p id="'+pagedata[i].caseid+'"><b>案件主体:</b></p>'+
					'<p><b>案件状态:</b>'+pagedata[i].casestate+'&nbsp;&nbsp;&nbsp;&nbsp;<b>案件类型:</b>'+pagedata[i].casetype+'&nbsp;&nbsp;&nbsp;&nbsp;<b>案件结果:</b>'+pagedata[i].caseresult+'&nbsp;&nbsp;&nbsp;&nbsp;<b>办案机构:</b>'+pagedata[i].casedep+'&nbsp;&nbsp;&nbsp;&nbsp;<b>销案日期:</b>'+pagedata[i].clocasedate+'</p>'+
					'<p><b>案发地:</b>'+pagedata[i].casespot+'&nbsp;&nbsp;&nbsp;&nbsp;<b>案发时间:</b>'+pagedata[i].casetime+'&nbsp;&nbsp;&nbsp;&nbsp;<b>立案机关:</b>'+pagedata[i].casefiauth+'&nbsp;&nbsp;&nbsp;&nbsp;<b>案值:</b>'+pagedata[i].caseval+'</p>'+
					'<p><b>案由:</b>'+pagedata[i].casereason+'</p>'+
					'<p><b>销案理由:</b>'+pagedata[i].clocaserea+
					'</p></div></div>').appendTo($('.results'));
			$('#'+pagedata[i].caseid).html('<b>案件主体:</b>'+pagedata[i].entname);
		}
	}else if(title=='12315'){//12315主题的trs内容
		for(var i=0;i<pagedata.length;i++){
			$('<div class="sous_list" ><div><h3><b>涉案主体:</b>'+pagedata[i].invopt+'&nbsp;&nbsp;&nbsp;&nbsp;'+pagedata[i].enttel+'&nbsp;&nbsp;&nbsp;&nbsp;<b>地址:</b>'+pagedata[i].entaddr+'</h3>'+
					'<p><b>投诉人:</b>'+pagedata[i].pname+'&nbsp;&nbsp;&nbsp;&nbsp;<b>事发地:</b>'+pagedata[i].accsce+'&nbsp;&nbsp;&nbsp;&nbsp;<b>事发时间:</b>'+pagedata[i].acctime+'&nbsp;&nbsp;&nbsp;&nbsp;<b>投诉人</b>'+pagedata[i].ptel+
					'<p><b>受理登记人:</b>'+pagedata[i].accregper+'&nbsp;&nbsp;&nbsp;&nbsp;<b>登记部门:</b>'+pagedata[i].regdep+'&nbsp;&nbsp;&nbsp;&nbsp;<b>登记时间:</b>'+pagedata[i].regtime+'&nbsp;&nbsp;&nbsp;&nbsp;'+
					'<p><b>关键字:</b>'+pagedata[i].keyword+'&nbsp;&nbsp;&nbsp;&nbsp;<b>商品名:</b>'+pagedata[i].mdsename+'&nbsp;&nbsp;&nbsp;&nbsp;'+
					'<p style="white-space:normal;"><b>具体原由:</b><span>'+pagedata[i].applidique+
					'</span></div></div>').appendTo($('.results'));
		}
		//市场主体的trs内容
	}else if(title=='市场主体'){
		var aArray = ["撤销变更登记中", "注销登记中", "个体户停业中", "三来一补注销中","撤销登记中","吊销","吊销并注销","已注销","停业","已撤销登记"]; 
		for(var i=0;i<pagedata.length;i++){
			var entState = "";
			//alert(aArray.indexOf(pagedata[i].entState)==-1);
			if($.inArray(pagedata[i].entState, aArray)!=-1){
				entState = entState = '<b>企业状态:'+pagedata[i].entState+'</b>';
			}else{
				entState = '<b>企业状态:</b><span style="color:#21419E;font-weight:bold">'+pagedata[i].entState+'</span>';
			}
			$('<div class="sous_list" ><div><h3><a onclick="openWindow(\''+contextPath+'/page/trs/panoramicAnalysis.jsp?'+pagedata[i].url+'&regno='+pagedata[i].regNo+'\',\''+pagedata[i].entName+'\')" href="javascript:void(0);" >'+pagedata[i].entNameColor+'&nbsp;&nbsp;'+pagedata[i].regNo+'</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="color:#15123A;" javascript:void(0); onclick="openWindow(\''+contextPath+'/page/trs/qyzp.jsp?'+pagedata[i].url+'&regno='+pagedata[i].regNo+'\',\''+pagedata[i].entName+'\')">族谱分析</a></h3>'+
					'<p><b>统一社会信用代码:</b>'+pagedata[i].uniscid+'&nbsp;&nbsp;&nbsp;&nbsp;'+
					entState+
					'&nbsp;&nbsp;&nbsp;&nbsp;<b>企业类型:</b>'+pagedata[i].entType+'&nbsp;&nbsp;&nbsp;&nbsp;<b>法定代表人:</b>'+pagedata[i].leRep+'&nbsp;&nbsp;&nbsp;&nbsp;<b>成立日期:</b>'+pagedata[i].estDate+'</p>'+
					'<p><b>行业类别:</b>'+pagedata[i].industryPhy+'&nbsp;&nbsp;&nbsp;&nbsp;<b>行业:</b>'+pagedata[i].industryCo+'&nbsp;&nbsp;&nbsp;&nbsp;<b>登记机关:</b>'+pagedata[i].regOrg+'</p>'+
					'<p><b>投资人:</b>'+pagedata[i].inv+'</p>'+
					'<p><b>经营状态:</b>'+pagedata[i].opState+'&nbsp;&nbsp;&nbsp;&nbsp;<b>经营方式:</b>'+pagedata[i].opScoAndForm+'&nbsp;&nbsp;&nbsp;&nbsp;<b>经营范围:</b>'+pagedata[i].opScope+'</p>'+
					'<p><b>住所:</b>'+pagedata[i].dom+'</p>'+
					'<p><b>变更历史:</b>'+pagedata[i].bgsx+
					'</div></div>').appendTo($('.results'));
		}
		//年度报告的trs内容  
	}else if(title=='年度报告'){
		for(var i=0;i<pagedata.length;i++){
			var annual=pagedata[i].anCheYears;
			var arrayAnn=new Array();
			var annLength=null;
			if(annual!=null||annual.length>4){
				arrayAnn=annual.split(",");
				annLength=arrayAnn.length-1;
			};
			var strann='';//arrayAnn[annLength]
			var url=$.trim(pagedata[i].url)+pagedata[i].anCheYear.replace(/(^\s*)|(\s*$)/g,"") +"&entname="+pagedata[i].entName+"&regno="+pagedata[i].regNo+"enttypeName="+pagedata[i].entType +"&open=ndbg";
			for(var j=0;j<arrayAnn.length;j++){
				strann ='<a onclick="openWindow(\''+contextPath+'/page/trs/panoramicAnalysis.jsp?'+$.trim(pagedata[i].url)+arrayAnn[j].replace(/(^\s*)|(\s*$)/g,"") +"&entname="+pagedata[i].entName+"&regno="+pagedata[i].regNo+"enttypeName="+pagedata[i].entType + "&open=ndbg"+'\')" href="javascript:void(0);" >'+arrayAnn[j]+'</a>&nbsp;&nbsp;&nbsp;'+strann;
			}
			$('<div class="sous_list"><div><h3><a onclick="openWindow(\''+contextPath+'/page/trs/panoramicAnalysis.jsp?'+url+'\')" href="javascript:void(0);" >'+pagedata[i].entName+'&nbsp;&nbsp;'+pagedata[i].regNo+'</a></h3>'+
					'<p><b> 企业类型:</b>'+pagedata[i].entType+'&nbsp;&nbsp;&nbsp;&nbsp;<b>企业状态:</b>'+pagedata[i].busSt+'&nbsp;&nbsp;&nbsp;&nbsp;<b>当前年份:</b>'+pagedata[i].anCheYear+'</p>'+
					'<p><b>电子邮箱:</b>'+pagedata[i].email+'&nbsp;&nbsp;&nbsp;&nbsp;<b>通讯地址:</b>'+pagedata[i].addr+'</p>'+
					'<p><b>投资人:</b>'+pagedata[i].invName+'&nbsp;&nbsp;&nbsp;&nbsp;</p>'+
					'<p><b>该企业历史年报:</b>'+strann+
					'</div></div>').appendTo($('.results'));
		}
	}


	if (pages > 1) {
		$("<span><a onclick='lazyload("+ (pages - 1) +")'>上一页</a></span>").appendTo($('.page'));
	}
	for (var i = listbegin; i <= listend; i++) {
		if (i != pages) {
			$("<span><a onclick='lazyload("+ i +")'>" + i + "</a><i></i></span>").appendTo($('.page'));
		} else {
			$("<span><a onclick='lazyload("+ i +")'>" + i + "</a><i class='active_i'></i></span>").appendTo($('.page'));
		}
	}
	if (pages != pagescount && pagescount!=0) {
		$("<span><a onclick='lazyload("+ (pages + 1) +")'>下一页</a></span>").appendTo($('.page'));
	};
}

function openWindow(url){
	//url='http://localhost:8080/gdsjzx/page/trs/panoramicAnalysis.jsp?flag=0&priPid=17077eb2-011c-1000-e000-a5cc0a070114&jbxxid=17077eb2-011c-1000-e000-a5cc0a070114&sourceflag=440200&enttype=9999&economicproperty=2&enttypeName=%E4%B8%AA%E4%BD%93&type=0&open=sczt';
	//window.open(encodeURI(url));//修改
	openWindow(url,"&@");
}
function openWindow(url,entname){
	//gdsjzx/page/trs/panoramicAnalysis.jsp?flag=0&priPid=451477f9-010d-1000-e000-03c00a010119&jbxxid=451477f9-010d-1000-e000-03c00a010119&sourceflag=440000&
	//enttype=1222&year=?
	/*economicproperty=2&
	enttypeName=股份有限公司(非上市、自然人投资或控股)&
	//type=12&
	//entname=广东众望通科技股&
	//gdsjzx/page/reg/regDetail.jsp?flag=0&priPid=451477f9-010d-1000-e000-03c00a010119&
	enttype=1222&year=2015&entname=广东<font color=red>众望通</font>科技股份有限公司&
	regno=440000400007004enttypeName=股份有限公司(非上市、自然人投资或控股)&open=ndbg*/
	//regno=440000400007004
	if(typeof(entname) != "undefined"){
		var arrUrl=url.split('&');
		var lenghts=arrUrl.length;
		var deletevar="&"+arrUrl[lenghts-2];
		url=url.replace(deletevar,'')+"&entname="+entname+"&open=sczt";
		/*url = url.substring(0,url.lastIndexOf('&'));
		url = url.substring(0,url.lastIndexOf('&'))+"&regno="+"&entname="+entname+"&open=sczt";*/
	}else{
		//url=url.replace("regDetail.jsp",'panoramicAnalysis.jsp');
		url=url.replace("<font color=red>",'');
		url=url.replace("</font>",'');
	}
	//alert(url);
	console.info(url);
	window.open(encodeURI(url));//修改
}
//延迟加载
function lazyload(i){
	var keyword=$('#inputBox').val();
	if($.trim(keyword)!="" && '请输入内容'!=$.trim(keyword)){
		returnOrgNo(url,i,keyword);
	} else {
		alert("输入栏不能为空");
	}
}
//退出
function quit() {
	$.ajax({
		url: contextPath+'/admin/quit.do',
	    //	async : false,
		dataType : 'json',
		type : "POST", //请求方式
		success : function(data) {
			window.location.href =contextPath+"/page/sjbd/index.jsp";
		}
	});
	/*var params = {
		url : contextPath+'/admin/quit.do',
		callback : function(data, r, res) {
			window.location.href =contextPath+"/page/sjbd/index.jsp";
		}
	};
	$.DataAdapter.submit(params);*/
}
//登录
function login() {
	var userVerify=$.trim($("#dlm").val());
	var pwdVerify=$.trim($("#mm").val());
	if(userVerify!="" && pwdVerify!=""){
		$.ajax({
			url:"/gdsjzx/admin/login.do",
			data:{
				dlm : userVerify,
				 mm : pwdVerify
			},
			type:"post",
			success:function(res){
				if(res.back=='success'){
					if(res.url!=null || res.url!=""){
						window.location.href =  contextPath+res.url;
					}else{
						window.location.href =  contextPath+"/page";
					}
				}else{
					$("#login_msg").html("<font color='red'> * " + res.msg + "</font>");
				}
			}
		});
	}
}
/*function clearSel(){
	$('#hh').hide();
};*/
function gets_value(str){
	$('#whole').html(str);
	$('#hh').hide();
}
function sh(x){
	if($('#hh').css("display")=='none'){
		//$('#tipShow').css("display","block");
		$('#hh').show();
	}else{
		//$('#hh').css("display","none");
		$('#hh').hide();
	}
	//$('#hh').show();
	//document.getElementById(x).style.display = document.getElementById(x).style.display? "" : "none";
}
function showlable(){
	$('#more ~ li').show();
}
//获取参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r != null) return unescape(r[2]); return null; //返回参数值
}
//模拟post提交打开新窗口，并传递参数
/*function post(URL, PARAMS) { var temp_form = document.createElement("form");
	temp_form .action = URL;
	temp_form .target = "_blank";
	temp_form .method = "post";
	temp_form .style.display = "none"; for (var x in PARAMS) {
		var opt = document.createElement("textarea");
		opt.name = x;
		opt.value = PARAMS[x];
		temp_form .appendChild(opt);
	}
	document.body.appendChild(temp);
	temp_form .submit();
}*/
//getInputValue();"><!-- javascript:if(this.value=='请输入内容')this.value=''; -->
function getInputValue(){
	var t = $('#inputBox').val();
	if(t=='请输入内容'){
		$('#inputBox').val("");
	}
	//$('.xmkc1').show();
}