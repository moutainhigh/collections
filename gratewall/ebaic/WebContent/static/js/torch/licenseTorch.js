define([ 'require', 'jquery', 'common','util' ],function(require, $, common,util) {
	var torch = {
		licenseForm_datarender : function(item,rowsdata) {
			var data = rowsdata.data;
			if(!data){
				return data;
			}
    		for (var i = 0; i < data.length; i++) {
    			var row = data[i];
    			
    			var code = row["code"] || '';
    			data[i]["code"] = "<span name='code'>"+code+"</span>";
    			
    			var text = row["text"] || '';
    			data[i]["text"] = "<span name='text'>"+text+"</span>";
    		}
    		return data;
		},
		query_fromNames : [ 'licenseForm_Form' ],
		
		query : function() {
			$("div[name='license_Grid']").gridpanel("option", "dataurl","../../../torch/service.do?fid=applySetupLicense&wid=applySetupLicense");
			$("div[name='license_Grid']").gridpanel("query",[ "licenseForm_Form" ],torch.selectCheckbox);
		},
		cancel : function(){
			util.closeWindow("license",false);
		},
		submit : function(){
			if(!window.parent){
				alert('当前页面只能从经营范围编辑页面打开。');
				return ;
			}
			if(!window.parent.Scope){
				alert('父页面没有Scope变量。');
				return ;
			}
			if(!window.parent.Scope.changeLicense){
				alert('父页面没有changeLicense方法。');
				return ;
			}
			
			var codes = $("input[name='selectedLicenseCodeInput']").val() || '';
			var names = $("#selectedLicenseNameValue").text() || '';
			
			var codeArray = codes.split(',');
			var nameArray = names.split('、');
			if(!codeArray){
				codeArray = []; 
			}
			if(!$.isArray(codeArray)){
				codeArray = [];
			}
			
			var ret = [];
			
			for(var i=0,len=codeArray.length;i<len;++i){
				var code = codeArray[i];
				var name = nameArray[i];
				if(code && name){
					var license = {name:name, code:code };
					ret.push(license);
				}
			}
			
			window.parent.Scope.changeLicense(ret);
			$('.jazz-titlebar-icon-close',window.parent.document).click();
		},
		/**
		 * 点击行选中复选框，或直接选中复选框
		 */
		clickRowOrChecked:function(){
			var codes = $("input[name='selectedLicenseCodeInput']").val() || '';
			var names = $("#selectedLicenseNameValue").text() || '';
			var flag = false;//直接点击复选框时，也会触发点击行事件，用flag区分。
			//点击行选中复选框
			$("div[name='grid_table']").find("tr").live('click',function(){
				
				var checked = $($(this).find("input[type='checkbox']")).attr('checked')=='checked';
				
				var code = $($(this).find("input[type='checkbox']")).parent().parent().find("span[name='code']").text() || '';
				var name = $($(this).find("input[type='checkbox']")).parent().parent().find("span[name='text']").text() || '';
				if(!code){
					return ;
				}

				var codesJudge = ',' + codes + ',';
				if(flag){
					checked = !checked;
				}
				if(!checked){
					// 设置为选中
					var pos = codesJudge.indexOf(code);
					if(pos>-1){ // 已经存在
						return ;
					}
					if(codes){//原来不为空，则追加
						codes = codes + ',' + code ;
						names = names + '、' + name ;
					}else{//原来为空，则设值
						codes = code ;
						names = name ;
					}
				}else{
					// 设置为取消选中
					var pos = codesJudge.indexOf(code);
					if(pos<0){ // 不存在
						return ;
					}
					if(!codes){//原来为空
						return
					}
					codes = codes.replace(',' + code + ','      ,     ',');
					codes = codes.replace( code + ','           ,     '');
					codes = codes.replace(',' + code            ,     '');
					codes = codes.replace( code                 ,     '');
					
					names = names.replace('、' + name + '、'    ,     '、');
					names = names.replace( name + '、'          ,     '');
					names = names.replace('、' + name           ,     '');
					names = names.replace(name                  ,     '');
				}
				
				$("input[name='selectedLicenseCodeInput']").val(codes);
				$("#selectedLicenseNameValue").text(names);
				
			});
			//直接选中复选框
			$("div[name='license_Grid']").find("input[type='checkbox']").live('click',function(){
				flag = true;
				var checked = $(this).attr('checked')=='checked';
				
				var code = $(this).parent().parent().find("span[name='code']").text() || '';
				var name = $(this).parent().parent().find("span[name='text']").text() || '';
				if(!code){
					return ;
				}

				var codesJudge = ',' + codes + ',';
				
				if(checked){
					// 设置为选中
					var pos = codesJudge.indexOf(code);
					if(pos>-1){ // 已经存在
						return ;
					}
					if(codes){//原来不为空，则追加
						codes = codes + ',' + code ;
						names = names + '、' + name ;
					}else{//原来为空，则设值
						codes = code ;
						names = name ;
					}
				}else{
					// 设置为取消选中
					var pos = codesJudge.indexOf(code);
					if(pos<0){ // 不存在
						return ;
					}
					if(!codes){//原来为空
						return
					}
					codes = codes.replace(',' + code + ','      ,     ',');
					codes = codes.replace( code + ','           ,     '');
					codes = codes.replace(',' + code            ,     '');
					codes = codes.replace( code                 ,     '');
					
					names = names.replace('、' + name + '、'    ,     '、');
					names = names.replace( name + '、'          ,     '');
					names = names.replace('、' + name           ,     '');
					names = names.replace(name                  ,     '');
				}
				
				$("input[name='selectedLicenseCodeInput']").val(codes);
				$("#selectedLicenseNameValue").text(names);
				
			});
		},
		_init : function() {
			require([ 'domReady' ],function(domReady) {
				domReady(function() {

					$("div[name='license_Grid']").gridpanel("option","dataurl","../../../torch/service.do?fid=applySetupLicense&wid=applySetupLicense");
					$("div[name='license_Grid']").gridpanel("option","datarender",torch.licenseForm_datarender);
					$('div[name="grid_paginator"]').paginator("option","gopage",torch.selectCheckbox);
					$("div[name='license_Grid']").gridpanel("reload",null,torch.selectCheckbox);

					$('#applySetupLicense_query_button').off('click').on('click',torch.query);
					$('#applySetupLicense_submit_button').off('click').on('click',torch.submit);
					$('#applySetupLicense_cancel_button').off('click').on('click',torch.cancel);
					
					var inputLicenses = [];
					if(window.parent){
						if(window.parent.Scope){
							if(window.parent.Scope.getLicense){
								inputLicenses = window.parent.Scope.getLicense();
							}
						}
					}
					torch.initLicenseScope(inputLicenses);
					//点击行选中复选框，或直接选中复选框
					torch.clickRowOrChecked();
					
				});
			});
		},
		/**
		 * 初始化。
		 */
		initLicenseScope : function(inputLicenses){
			var licenses = inputLicenses || [];
			if(!$.isArray(licenses)){
				licenses = [];
			}
			var codes='',names='';
			var first = true;
			for(var i=0,len=licenses.length;i<len;++i){
				var o = licenses[i];
				if(!o){continue;}
				if(!o.name){continue;}
				if(!o.code){continue;}
				if(first){
					first = false;
					codes += o.code ;
					names += o.name ;
				}else{
					codes += ',' + o.code ;
					names += '、'+ o.name ;
				}
			}

			$("input[name='selectedLicenseCodeInput']").val(codes);
			$("#selectedLicenseNameValue").text(names);
			
			/*if(!names){
				$("#selectedLicenseNameLabel").hide();
			}else{
				$("#selectedLicenseNameLabel").show();
			}*/
			
			$(".jazz-grid-headerCell-box").hide();
		},
		/**
		 * 设置checkbox的选中
		 * shiyameng
		 */
		selectCheckbox: function(){
			var codes = $("input[name='selectedLicenseCodeInput']").val() || '';
			$('span[name = "code"]').each(function(index,item){
				var code = $(item).text();
				if(codes.indexOf(code) != -1){
					var checkbox = $(item).parent().prevAll('.jazz-grid-cell-box').eq(0);
					checkbox.click();
				}
			})
		}
	};
	torch._init();
	return torch;

});
