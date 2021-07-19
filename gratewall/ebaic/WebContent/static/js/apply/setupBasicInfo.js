define(['require','jquery','jazz', 'common','scope','apply/setupApproveMsg', 'validator','util'], function(require, $, jazz, common,Scope,approveMsg,validator,util){
    var basicInfo = {
    	_init: function(){  
    		require(['domReady'], function (domReady) {
			  domReady(function () {
				  
				  basicInfo.applySetupBasicQuery();
				  //加载页签模板
				  $(".main").renderTemplate({templateName:"content",insertType:"wrap",wrapSelector:".content"},
		    				{srcpath:"../../../static/image/img/icon/info-white.png",title:"文件上传信息",gid:jazz.util.getParameter('gid')});    		
				  //为页签增加事件
				  $('.banner').find('a').live('click',function(){		    			
						  return basicInfo.applySetupBasicSave();
					  });
				  
				  $("#domDistrict").comboxfield('setValue','110108');
		    		$(".checkdetail").on("click",basicInfo.tooltipInfo);
		    		
		    		/**
		    		 * chaiyoubing  2016-07-11  企业基本信息新增方返回法
		    		 */
		    		$("#applySetupBasic_back_button").on('click',basicInfo.backPersonAccountHome);		    		
		    		$('#customInput').on('click',basicInfo.showCustom);
		    		$("#customScope").textareafield('option','change',basicInfo.changeCustomScope);
		    		$('#approve-custom').textareafield('option','change',basicInfo.changeAproveCustom);
		    		basicInfo.applySetupBasicNameQuery();
		    		basicInfo.init_tooltips();//提示信息
		    		basicInfo.reLocate();//导航高亮提示
		    		$("#tradeTerm").autocompletecomboxfield('option','change',basicInfo.onTradeTermFocus);
		    		$("#domDetail").textfield('option','change',basicInfo.copyDomDetail);
		    		$("#domOwnType").comboxfield("option", "change", function() {
		    			var option = $("#domOwnType").comboxfield('getValue');
		    			basicInfo.domOwnType_Text(option);
		    		});
		    		$("#proLocCity").comboxfield('option','dataurl','../../../dmj/getDomDistrict.do?domDistrict=');
		    		$("#domDistrict").comboxfield('option','dataurl','../../../dmj/getDomDistrict.do?domDistrict='+basicInfo.domDistrict);
		    		$("#domOwnType").comboxfield('option','dataurl','../../../dmj/getDomOwnType.do');
		    		$('#mainSc').find('input').on('change',function(){basicInfo.addMainScope(false);});
		    		$('#mainSc').autocompletecomboxfield('option','itemselect',function(){basicInfo.addMainScope(true);})
		    		$('#addLicense').on('click',basicInfo.addLicense);
		    		$('#moreScope').on('click',basicInfo.moreScope);
		    		$('#addOtherScope').on('click',basicInfo.showOtherScope);
		    		basicInfo.initScope();
		    		basicInfo.backScopeEve();
		    		$("img[name='back']").on('click',basicInfo.hideAdvice);//隐藏退回修改意见
					$(".modifyBack").on('click',basicInfo.showAdvice);//出现退回修改意见
		    		$("#clickBt").on('click',basicInfo.openWin)		 
		    		common.computeHeight();		    		
				   $("#applySetupBasic_query_button").on('click',basicInfo.applySetupBasicSave);
				   $("#applySetupBasic_next_button").on('click',function(){basicInfo.applySetupBasicSave('next')});
				   $(".content").on("click",basicInfo.closetipInfo);
			  });
			
			});
    		
    	},

    	openWin:function(){
    		util.openWindow('aa', '业务提交确认授权', '../../../file/intro/001.pdf',1100, 550);
    	},

    	/**退回修改经营范围事件处理**/
    	backScopeEve : function(){
    		var isBackStatus = basicInfo.isStatusOfBack(); 
    		if(isBackStatus == '1'){//退回修改
    			$("#setupScope").hide();
    			$("#backScope").show();
    		}else{
    			$("#setupScope").show();
    			$("#backScope").hide();
    		}
    		
    		//点击编辑显示单选按钮
    		$('a[name="backScopeUpdate"]').live('click',function(){
    			$('#scopeWaySel').show();
    		});
    		
    		//单选按钮事件
    		$('input[name="scoSelect"]').on('click',function(){
    			var rScoValue = $('input[name="scoSelect"]:checked').val();
    			if(rScoValue == '1'){
    				var zcontent = $('#backCustomDiv').text();
    				$('#backCustomDiv').html('<br><div vtype="textareafield" id="backCustomScope" name="backCustomScope" width="600" ></div><br>');
    				/*$('#backCustomDiv').css({"display":"inline-block"});*/
    				$('#backCustomScope').parseComponent();
					$('#backCustomScope').textareafield('setValue',zcontent);
					$('#backCustomScope').textareafield('option','change',basicInfo.changeCustomScope);
    				$('#backCustScope').show();
    				$('#setupScope').hide();
    				$("div[name='backScope']").css('height','200px;');
    			}
    			if(rScoValue == '2'){
    				$('#backCustScope').hide();
    				$('#setupScope').show();
    				$("div[name='backScope']").css('height','60px;');
    			}
    		});
    		
    		
    	},
    	
    	isStatusOfBack : function(){
    		var res;
    		$.ajax({
				url : '../../../apply/setup/basicinfo/hasStateOfBacked.do?gid=' + jazz.util.getParameter('gid'),
				type : "post",
				async : false,
				dataType : "json",
				success : function(data) {
					res = data;
				}
			});
    		return res;
    	},
    	
    	 nextStep:function(){
 	    	window.location.href="../../../page/apply/setup/inv.html?gid="+jazz.util.getParameter('gid');
 	    },
 	   /**
 	    * 返回个人账户首页
 	    */
 	   backPersonAccountHome:function(){
 		  window.location.href="../../../page/apply/person_account/home.html";
 	   },
 		edit_primaryValues : "&gid=" + jazz.util.getParameter('gid'),

		edit_fromNames : [ 'applySetupBasic_Form' ],
		
	
		
		// 住所产权属性
		domOwnType_Text : function(option) {
			
			if(option=="1"){//有房产证
				var content = "您使用的房屋应取得房屋所有权证，而且房屋所有权证记载的房屋用途应与企业从事的经营活动一致。    房产证记载用途如果为“工业、教育、医疗卫生、其他（涉外、宗教、监狱）”，可由产权人出具确有配套服务需求的说明。    如果房屋的规划批准材料于2009年10月1日前取得，且房产证记载用途为“交通、仓储、商业、金融、信息、科研、文化、娱乐、体育、办公、综合”的，可直接办理登记注册。";
				
				$('#suggest_dd2').html(content).removeClass('hide');
				//有房产证，去住所提供方式
				$("#domProRight").hide();
				$("#domProRight").comboxfield("setValue","");
				$("#domProRight").comboxfield("option","rule","");
			}else{
				var content = "暂未取得房屋所有权证的，须提交以下材料：农村地区可提交乡村规划建设许可证或临时乡村规划建设许可证，也可提交由乡镇政府出具的经营场所权属、用途、属合法建设的证明。用于经营的房屋属于中央单位的，须提交由中央各直属机构房屋管理部门出具的证明；属于国务院各部委的，须提交由国务院机关事务管理局出具的证明；属于国务院国资委监管的中央企业的，须提交由中央企业出具的证明；属于市级以上各类园区内的，须提交由园区管理部门出具的证明；属于市国资委监管的国有企事业单位，利用工业、仓储等用途的房屋从事商业等其他用途经营的，须提交由市国资委出具的证明；属于其他情况的，区县政府可结合实际情况明确房屋权属出证的规定，工商部门依据规定办理登记注册。其中《投资办照通用指南及风险提示》为下载链接，下载这个告知单详情参见<a id='clickBt' href='javascript:void(0)' target='_blank' >《投资办照通用指南及风险提示》</a>告知单第六部分“选择企业住所（经营场所）时应注意哪些问题” ";
				
				$('#suggest_dd2').html(content).removeClass('hide');
				//$('#suggest_dd2').html("").addClass('hide');
				//无房产证，出住所提供方式并且必填
				$("#domProRight").show();
				$("#domProRight").comboxfield("option","rule","must");
			}
		},
		applySetupBasicQuery : function() {
			var updateKey = "&wid=applySetupBasic";
			$.ajax({
				url : '../../../torch/service.do?fid=applySetupBasic' + updateKey
						+ this.edit_primaryValues,
				type : "post",
				async : false,
				dataType : "json",
				success : function(data) {
					var jsonData = data.data;
					if ($.isArray(jsonData)) {
						for (var i = 0, len = jsonData.length; i < len; i++) {
							$("div[name='" + jsonData[i].name + "']").formpanel(
									"setValue", jsonData[i] || {});
						}
						if(jsonData.length > 0 ){
							if(jsonData[0].data){
								if(jsonData[0].data.regCap == undefined || jsonData[0].data.regCap == ""){
									$("div[name='tradeTerm']").autocompletecomboxfield('setText','');
									//页面初次加载执照副本数给出默认值1
									$("#copyNo").textfield('setValue','1');
								}else{
									if(jsonData[0].data.tradeTerm == undefined || jsonData[0].data.tradeTerm == ""){
										$("div[name='tradeTerm']").autocompletecomboxfield('setValue', '999999');
										$('.jazz-field-comp-suffix', tradeTerm).css('color', '#FFF');
									}else{
										$("div[name='tradeTerm']").autocompletecomboxfield('setText', jsonData[0].data.tradeTerm);
										$('.jazz-field-comp-suffix', tradeTerm).css('color', '#000');
									}
								}
								basicInfo.domOwnType_Text(jsonData[0].data.domOwnType);
							}
						}
						
					}
				}
			});
		},
    	domDistrict : "110108",//TODO 设置为空表示全部都查，可封装成公共组件
    	//点击出现/消失查看详细
    	tooltipInfo : function(){    		
    		if($("#check").hasClass("moreDetailHover")){    			
    			$(".hidden").css("display","none");
        		$(".moreDetail").removeClass("moreDetailHover");
        		$(".checkdetail").removeClass("checkdetailHover");
    		}else{
    			$(".hidden").css("display","block");
        		$(".moreDetail").addClass("moreDetailHover");
        		$(".checkdetail").addClass("checkdetailHover");
    		}
    	},
   	closetipInfo:function(){    
    		if($("#check").hasClass("moreDetailHover")){
    			$(".hidden").css("display","none");
        		$(".moreDetail").removeClass("moreDetailHover");
        		$(".checkdetail").removeClass("checkdetailHover");
    		}
    	},
    	applySetupBasicNameQuery : function(){
    		$.ajax({
				url : '../../../torch/service.do?fid=applySetupBasic&wid=applySetupNameInfo&gid='
				+jazz.util.getParameter('gid'),
				type : "post",
				async : false,
				dataType : "json",
				success : function(data) {
					if(data.exception == undefined){
						if(data.data && data.data.length >0){
							var namedata = data.data[0];
							if(namedata){
								basicInfo.checkEntTypeIndustryCoState(namedata.data);
								$('#namepanel').formpanel('setValue', namedata);
							}
						}
					}
				}
			});
		},
    	
    	init_tooltips : function(){
    		
    		$('#zs_department').tooltip(
				{
					showevent : 'mousedown',
					hideevent : 'blur',
					width : 190,
					content : '产权人可以是自然人或单位，您最终提交的申请材料中需要包括《住所使用证明》，届时需要提供产权人签字或盖章的产权证复印件。'
				});
    		
    		$('#domDetail').tooltip(
				{
					showevent : 'mousedown',
					hideevent : 'blur',
					width : 190,
					content : '请您使用真实的、合法的纸质材料办理登记注册手续。提供虚假注册地址或登记的住所无法取得联系的公司将被记入经营异常名录，影响您的正常经营。'
				});
    		
    		$('#domAcreage').tooltip(
    			{
	    			showevent : 'mousedown',
	    			hideevent : 'blur',
	    			width : 190,
	    			content : '按实际营业面积填写，可以有两位小数。'
    			});
    	},
    	
    	onTradeTermFocus : function(){
    		var tradeTerm = $("#tradeTerm");	
    		if (tradeTerm) {
    			var t = tradeTerm.autocompletecomboxfield('getText');
    			t = t && $.trim( t );
    			t = t || '';

    			if (t.indexOf('长期') != -1) {
    				// 隐藏“年”
    				$('.jazz-field-comp-suffix', tradeTerm).css('color', '#FFF');
    			} else {
    				// 显示“年”
    				$('.jazz-field-comp-suffix', tradeTerm).css('color', '#000');
    			}
    			
    			if(t=='请选择'){
    				t = '';
    			}

    			tradeTerm.autocompletecomboxfield('setText', t);
    		}	
    	},
    	
    	copyDomDetail : function(){
    	
    		var domDetail = $("#domDetail");
    		if(domDetail) {
    			var domValue = domDetail.textfield('getValue');
    			var proLocValue = $("#proLocOther").textfield('getValue');
    			domValue = $.trim(domValue);
    			proLocValue = $.trim(proLocValue);
    			if(domValue != null && domValue != "" && (proLocValue == null || proLocValue=="")){
    				$("#proLocOther").textfield('setValue',domValue);
    				
    				//生产经营地下拉框同样跟随住所
					var domDistrict = $("#domDistrict");
					if(domDistrict){
						var domDistrictValue = domDistrict.comboxfield('getValue');
						if(domDistrictValue){
							$("#proLocCity").comboxfield('setValue',domDistrictValue);
						}
					}
    			}
    		}
    	},
    	showdiv:function () {
    		document.getElementById("bg").style.display ="block";
    		document.getElementById("hint-box").style.display="block";
    	},
    	
    	reLocate:function(){
    		var urlstr=location.href;
    		var urlstatus=false;
    		$(".banner a").each(function(){
    			if((urlstr+"/").indexOf($(this).attr("href"))>-1 && $(this).attr("href")!=''){
    				$(this).addClass("blueactive");
    	    		$(this).find("span:eq(0)").addClass("icon-info-blue");
    				urlstatus=true;
    			}else{
    				$(this).removeClass("blueactive");
    				$(this).find("span:eq(0)").addClass("info");
    			}
    		});
    		if(!urlstatus){
    			$(".banner a").eq(0).addClass("blueactive");
    		}
    	},
    	
    	checkEntTypeIndustryCoState : function(data){
    		var entType = data['entType'];
			var industryCo = data['industryCo'];
			entType = entType.substr(0,4);
			industryCo = industryCo.substr(0,4);
			if(entType == '1100'){//确认需求
//				$('#applySetupBasic_Form').formpanel('reset');
//				alert('企业类型不符合');
//				window.location.href = '../../../page/apply/index.html';
			}
			if(industryCo == ''){//确认需求
				$('#applySetupBasic_Form').formpanel('reset');
				alert('行业代码不符合');
				window.location.href = '../../../page/apply/index.html';
			}
    	},
    	
    	initTags: function(){
    		$.ajax({
    			url:'../../../apply/setup/scope/tags.do',
    			type:'post',
    			data:{gid:jazz.util.getParameter('gid')},
    			dataType:'json',
    			success: basicInfo.renderTags,
    			error: function(){
    				jazz.error('经营范围初始化错误！');
    			}
    		});
    	},
    	
    	renderTags: function(data){
    		if(data.exceptionMes){
				jazz.util.error(data.exceptionMes);
			}else{
				$("#scope_tags").renderTemplate({templateName:'scope_tags',insertType:'append'},data);
				$('.tags_top').find('span').on('click',function(event){basicInfo.changScopTitle(event)});
				$('.tag_content').on('click',basicInfo.addScope);
				$('.tags_top').find('span').first().click();
			}
    	},
    	
    	changScopTitle: function(e){
    		var item = $(e.target);
    		item.addClass('selected');
    		item.siblings().removeClass('selected');
    		var id = $('.tags_top').find('.selected').attr("for");
			var item = $('#'+id)
			item.removeClass('hide');
			item.siblings('.tags_content').addClass('hide');
    	},
    	
    	initScope: function(){
    		$.ajax({
    			url:'../../../apply/setup/scope/load.do',
    			type:'post',
    			data:{gid:jazz.util.getParameter('gid')},
    			dataType:'json',
    			success: basicInfo.loadScope,
    			error: function(){
    				jazz.error('经营范围初始化错误！');
    			}
    		});
    	},
    	
    	loadScope: function(data){
    		if(data.exceptionMes){
				jazz.util.error(data.exceptionMes);
			}else{
				var scopeJson = JSON.parse(data.scopeJson || '{}');
				if(data.opScopeType == '3'){
					scopeJson.type = '3';
	    			$('#apply').addClass('hide');
	    			$('#approve-main').textareafield('setValue',data.mainScope || '');
	    			$('#approve-custom').textareafield('setValue',data.opCustomScope || '');
	    			if(data.ptBusScope){
	    				$('#approve-scope').textareafield('setValue',data.ptBusScope);
	    			}else{
	    				$('#approve-scope').addClass('hide');
	    			}
	    			if(data.opScope){
	    				$("#approve-license").textareafield('setValue',data.opScope);
	    			}else{
	    				$("#approve-license").addClass('hide');
	    			}
	    			if(data.opSuffix){
	    				$("#approve-suffix").textareafield('setValue',data.opSuffix);
	    			}else{
	    				$("#approve-suffix").addClass('hide');
	    			}
	    			var businessScope = basicInfo.makeUpBusinessScope(data.mainScope, data.opScope , data.ptBusScope , data.opCustomScope , data.opSuffix);
	    			$('#scope_text').text(businessScope);
	    		}else{
	    			basicInfo.initTags();
	    			basicInfo.Scope = new Scope([basicInfo.renderScope,basicInfo.renderScopeText]);
	    			basicInfo.Scope._init(scopeJson);
					window.Scope = basicInfo.Scope;
					
					var isBackStatus = basicInfo.isStatusOfBack(); 
		    		if(isBackStatus == '1'){//退回修改
						var context;
						if(data.opCustomScope){
							context = $('#scope_text').text().replace(data.opCustomScope,'<span id="backCustomDiv">'+data.opCustomScope+'</span>')
							+ '<a href="javascript:void(0)" name="backScopeUpdate">点击编辑</a>';
						}else{
							var zcontext = $('#scope_text').text();
							context = zcontext.substring(0,zcontext.indexOf('（企业依法自主选择经营'))
							+ '<span id="backCustomDiv"></span>'
							+ zcontext.substring(zcontext.indexOf('（企业依法自主选择经营'))
							+ '<a href="javascript:void(0)" name="backScopeUpdate">点击编辑</a>';
						}
						
						$('#backCustScope').html(context);
					}
	    		}
			}
    	},
    	
    	renderScope: function(scopeData){
    		var data = scopeData;
			$('#approve').addClass('hide');
    		if(data.type && data.type == '6'){
    			$('#mainSc').autocompletecomboxfield('setValue',(data.mainScope && data.mainScope.text) || '');
    			$('#mainSc').autocompletecomboxfield('setText',(data.mainScope && data.mainScope.text) || '');
    			$('#mainSc').autocompletecomboxfield('option','rule','must');
    			if(data.customScope || basicInfo.hasScope(data.scope)){
    				$('#scope_tags').removeClass('hide');
    				$('#customContent').removeClass('hide');
    				if(data.customScope){
    					$("#customtext").removeClass('hide');
        				$('#customScope').textareafield('setValue',data.customScope);
    				}else{
        				$("#customtext").addClass('hide');
        				$('#customScope').textareafield('setValue','');
        			}
    			}else{
    				$('#scope_tags').addClass('hide');
    				$('#customContent').addClass('hide');
    				$("#customtext").addClass('hide');
    			}
    		}
			var html = '';
			if(data.scope){
				for(var i = 0, len = data.scope.length; i < len; i++){
					var scopCon = data.scope[i];
					if(scopCon){
						for(var j = 0, lenj = scopCon.length; j < lenj; j++){
    						html += '<div class="scope_con"><span class="scope_text" index="'+i+'" code="'+scopCon[j].code+'" group="'+scopCon[j].group+'" type="scope">' + scopCon[j].name + '</span><span class="remove">x</span></div>';
    					}
					}
				}
			}
			if(data.license){
				var license = data.license;
				for(var i = 0, len = license.length; i < len; i++){
					html += '<div class="license_con"><span class="license_text" code="'+ license[i].code +'" type="license">' + license[i].name + '</span><span class="remove">x</span></div>';
				}
			}
			
			$('#scope').empty().html(html);
			$('.remove').off().on('click',basicInfo.removeScope);
			
    	},
    	
    	hasScope: function(scope){
    		if(scope && scope.length > 0){
    			for(var i = 0, len = scope.length; i < len; i++){
    				if(scope[i]){
	    				if(scope[i].length > 0){
	    					return true;
	    				}
	    			}
    			}
    			return false;
    		}else{
    			return false;
    		}
    	},
    	
    	renderScopeText: function(scopeData){
    		var text = basicInfo.Scope.getScopeText();
			$('#scope_text').empty().html(text);
            
    	},
    	
    	makeUpBusinessScope : function(mainScope, opScope , ptBusScope ,opCustomScope , opSuffix){

    		 var businessScope = '';
    		 
    		 mainScope = basicInfo.wipeDot(mainScope);
    		 if(mainScope){
    			 mainScope += "；";
    		 }
    		 opScope = basicInfo.wipeDot(opScope);
    		 if(opScope){
    			 opScope += "；";
    		 }
    		 ptBusScope = basicInfo.wipeDot(ptBusScope);
    		 if(ptBusScope){
    			 ptBusScope += "；";
    		 }
    		 opCustomScope = basicInfo.wipeDot(opCustomScope);
    		 if(opCustomScope){
    			 opCustomScope += "；";
    		 }
    		 opSuffix = basicInfo.wipeDot(opSuffix);
    		 businessScope = mainScope + ptBusScope + opCustomScope + opScope;
    		 businessScope = basicInfo.wipeDot(businessScope);
    		 if(businessScope){
    			 businessScope += "。";
    		 }
    		 if(opSuffix){ 
    			 businessScope += "（"+opSuffix+"。）";
    		 }
    		 return businessScope;
    	 },
    	 
    	 wipeDot : function(s){
			 if(!s){
				 return '';
			 }
			 s = $.trim(s);
			 s = s.replace(/^[。|\.|;|；|,|，|、]*/,'');
			 s = s.replace(/[。|\.|;|；|,|，|、]*$/,'');
			 return s;
		 },
    	
    	removeScope: function(e){
    		var item = $(e.target).prev();
    		var type = item.attr('type');
    		if(type == 'scope'){
    			basicInfo.Scope.removeScope({name:item.html(),group:item.attr('group'),code:item.attr('code'),index:item.attr('index')});
    		}else if(type == 'license'){
    			basicInfo.Scope.removeLicense({name:item.html(),code:item.attr('code')});
    		}
    	},
    	
    	addScope: function(e){
    		var name = $(e.target).html();
    		var code = $(e.target).attr('code');
    		var group = $(e.target).attr('group');
    		var index = $('.tags_top').find('span[for="'+$(e.target).parent().attr('id')+'"]').attr('index');
    		var scope = {name:name,code:code,group:group,index:index};
    		basicInfo.Scope.addScope(scope);
    	},
    	
    	addMainScope: function(isStandard){
    		var val = $('#mainSc').autocompletecomboxfield('getText');
    		basicInfo.Scope.addMainScope({text:val,isStandard:isStandard});
    	},
    	/**
    	 * 保存
    	 * @param type
    	 * @returns {Boolean}
    	 */
    	applySetupBasicSave : function(type) {
    		// 校验必须选择主营经营范围
    		var scopeJson = '';
    		if(basicInfo.Scope){
    			basicInfo.changeCustomScope();
    			var busiscope = basicInfo.Scope.getScope();
        		if(busiscope){
					if(!(busiscope.mainScope && busiscope.mainScope.text)){
	    				jazz.info("请输入主要经营项目。");
	    				return false;
	    			}
        		}
        		var isBackStatus = basicInfo.isStatusOfBack(); 
        		if(isBackStatus == '1'){//退回修改
        			var rScoValue = $('input[name="scoSelect"]:checked').val();
        			if(rScoValue == '1'){
        				var zCustomScopeVal = $("#backCustomScope").textareafield('getValue');
            			busiscope.customScope = zCustomScopeVal;
        			}
    			}
    			scopeJson = JSON.stringify(basicInfo.Scope.getScope());
    		}else{
    			basicInfo.changeAproveCustom();
    			scopeJson = JSON.stringify({customScope:$("#approve-custom").textareafield('getValue'),type:'3'});
    		}
    		var customScope = $("#approve-custom").textareafield('getValue');
			//保存企业基本信息
			var params = {
				url : "../../../torch/service.do?fid=applySetupBasic"
						+ basicInfo.edit_primaryValues,
				components : basicInfo.edit_fromNames,
				params:{
					gid: jazz.util.getParameter('gid'),
				    scopeJson: scopeJson,
				    businessScope: $('#scope_text').html(),
				},
				callback : function(jsonData, param, res) {
					if(type == "next"){
						basicInfo.nextStep();
					}else{
						jazz.info("保存成功");
					}
					
				}
			};
			$.DataAdapter.submit(params);
		},
		
    	showCustom: function(){
    		var custom = $('#customtext');
    		if(custom.hasClass('hide')){
    			custom.removeClass('hide');
    		}else{
    			custom.addClass('hide');
    		}
    	},
    	
    	showOtherScope: function(){
    		var custom = $('#scope_tags');
    		if(custom.hasClass('hide')){
    			custom.removeClass('hide');
    		}else{
    			custom.addClass('hide');
    		}
    		var custom = $('#customContent');
    		if(custom.hasClass('hide')){
    			custom.removeClass('hide');
    		}else{
    			custom.addClass('hide');
    		}
    	},
    	
    	/**
  	    * 如果选出的经营范围不能满足您的要求，您可以手动输入；
  	    * 1、当失去焦点时，提示申请人确认？
  	    * 2、是，将输入的内容和select license.lic_metter_memo from sys_license_config license
  	    * 查到的内容比较，如果包含表sys_license_config的内容，则系统自动生成许可记录
  	    * 3、否，删除手动输入文字中的相关文字；
  	    * 4、根据您输入的信息生成的经营范围如下：
  	    */
    	changeCustomScope: function(){
    		var customScopeVal = $("#customScope").textareafield('getValue');
    		var isBackStatus = basicInfo.isStatusOfBack(); 
    		if(isBackStatus == '1'){//退回修改
    			var rScoValue = $('input[name="scoSelect"]:checked').val();
    			if(rScoValue == '1'){
    				customScopeVal = $("#backCustomScope").textareafield('getValue');
    			}
    		}
    		var params = {
		        url : '../../../apply/setup/scope/compareScope.do',
		        async:false,
		        type:'post',
		        dataType:'json',
		        params:{
		        	customScopeVal:customScopeVal
		        },
		        callback : function(data, param, res) {
		        	var ret = res.getAttr("result");
		        	if(ret.length>0){
		        		var msg  = "您手动输入的经营范围中包含后置许可经营用语：";
		        		var names = "";
		        		var anths = "";
		        		for(var i=0;i<ret.length;i++){
		        			customScopeVal = customScopeVal.replace(ret[i].name,"");
		        			if(i==ret.length-1){
		        				names+=ret[i].name+"，";
		        				anths+=ret[i].anth+"("+ret[i].name+")，";
		        			}else{
			        			names+=ret[i].name+"、";
			        			anths+=ret[i].anth+"("+ret[i].name+")、";
		        			}
		        		}
		        		msg+=names+"请您确认是否将后置许可添加到经营范围当中？";
		        		jazz.confirm(msg,function(){
		        			//确定将后置许可经营范围添加到经营范围当中
		        			window.parent.Scope.addLicense(ret);
		        		},function(){
		        			$("#customScope").textareafield('setValue',customScopeVal);
		        			if(isBackStatus == '1'){//如果是退回修改
		        				if(rScoValue == '1'){
		        					$("#backCustomScope").textareafield('setValue',customScopeVal);
		        				}
		        			}
		        		});
		        	}
		        	//不管是或否，都需要删除将匹配到的许可经营范围删掉，并且生成的经营范围如下
		        	basicInfo.Scope.addCustomScope(customScopeVal);
		        }
    		};  	
        	$.DataAdapter.submit(params);
    	},
    	
    	changeAproveCustom:function(){
    		var ptBusScope = $('#approve-scope').textareafield('getValue');
    		var opScope = $("#approve-license").textareafield('getValue');
    		var opCustomScope = $("#approve-custom").textareafield('getValue');
    		var opSuffix = $("#approve-suffix").textareafield('getValue');
    		var mainScope = $('#approve-main').textareafield('getValue');
			var businessScope = basicInfo.makeUpBusinessScope(mainScope, opScope , ptBusScope , opCustomScope , opSuffix);
			$('#scope_text').text(businessScope);
    	},
    	
    	addLicense: function(){
    		var win = jazz.widget({
    			vtype : 'window',
    			name : 'license',
    			title : '后置许可经营用语',
    			titlealign : 'left',
    			titledisplay : true,
    			modal : true,
    			frameurl : 'license.html',
    			height : '440',
    			width : '700',
    			visible:true
    		});
    		// 打开窗口
    		win.window('open');
    	},
    	//打开经营范围更多页面
    	moreScope: function(){
    		var win = jazz.widget({
    			vtype : 'window',
    			name : 'moreScope',
    			title : '经营范围',
    			titlealign : 'left',
    			titledisplay : true,
    			modal : true,
    			frameurl : 'scope.html',
    			height : '445',
    			width : '730',
    			visible:true
    		});
    		// 打开窗口
    		win.window('open');
    	},
    	//点击出现退回修改意见
    	showAdvice:function(){
    		$(".advice").css("display","block");
    		$(".modifyBack").css("display","none");
    	},
    	//点击隐藏退回修改意见
    	hideAdvice:function(){
    		$(".advice").css("display",'none');
    		$(".modifyBack").css("display","block");
    	}
    	
    };
    basicInfo._init();   
    return basicInfo;
});
