/**
 * 长城计算机软件与系统有限公司-工商行业-报表系统
 * 
 * 报表展示页面。
 * 
 * @author liuhailong<liuhailong2008@foxmail.com>
 * 
 */
var report = {};



/**
 * 定义 report 的方法。
 */
$(function(){
	/**
	 * 报表初始化。
	 */
	report.refresh = function(name){
		//window.parent.open("show_list.html?name=" + name);
		var oReport = $("div[name='"+name+"Report']");
		var config = report.config(name);
		// 0、显示为正在加载数据

		
		// 1、获取配置信息
		if(!config.headerDataConfig || !config.headerDataConfig.length){
			alert("报表 headerDataConfig 解析错误，请联系系统管理员。");
			return ;
		}
		
		
		// 2、从服务器重新加载数据
		report.data(name,report.gridRender);
		report.doExport(name);
	} ;
	
	report.init = function(name){
		// 1、加载报表配置信息
		var config = report.config(name);
		if(!config){
			//alert('加载报表配置信息失败，请重试。');
			return ;
		}
		// 2、生成HTML框架
		var html = report._getInitHtml(name);
		$("body").append(html);
		var oReport = $("div[name='"+name+"Report']");
		// 3、生成Title
		if(config.title){
			oReport.find(".report-title").text(config.title);
		}
		// 4、生成Header
		var headerHtml = report._getHeaderHtml(config);
		oReport.find('.report-content-table').append(headerHtml);
		// 5、生成查询表单
		var treeBoxes = {} ;
		var treeBoxHelpers = {} ;
		var rules = {};
		var paramsHtml = report._getParamsFormHtml(config,treeBoxes,treeBoxHelpers);
		report._getTreeBoxHelper(treeBoxHelpers);
		oReport.find('.report-query').append(paramsHtml);
		var treeOptions ;
		for(var treeBoxId in treeBoxes){
			treeOptions = treeBoxes[treeBoxId] ;
			treeHelper = treeBoxHelpers[treeBoxId] ;
			treeBox_init(treeBoxId,treeOptions,treeBoxHelpers);
		}
		
		// 6、生成数据
		var reportData ;
		if(config.queryOnLoading==true){
			reportData = report.refresh(name);
		}else{
			reportData = null;
			var emptyDataHtml = "<tr class='report-data-tr'><td colspan=\""+
				(config.headerDataConfig.length+1)+"\" class='report-data-empty' style='border-width:0px;'><img src='../../gwaic/images/newicon/fenye.gif'><span style='line-height:36px;'>没有你需要的查询的记录</span></td></tr>";
			$("div[name='"+name+"Report']").find(".report-paging-tr").remove();
			$("div[name='"+name+"Report']").find('.report-content-table').append(emptyDataHtml);
		}
		
		// 7、生成辅助信息
		// 如：辅助选择日期的“上半年”、“上个月”、“上季度”等；
		// 如：辅助选择区县的自定义查询条件。
		// 日期配置需如右侧格式，helper:['appDateHelper','date','app_date_begin','app_date_end']
		// 默认显示上一月
		$('.report-query-helper-btn').each(function(idx,ele){
			ele = $(ele);
			if(ele.text()=='默认'){
				ele.click();
			}
		});
		// 8、 收藏功能
//		report.initFav(name);
		
		// 9、备注功能
		report.showRemark(name,config);
		
		//10、界面美化
		report.ui(name);
		
	};
	report._getTreeBoxHelper = function(treeBoxHelpers){
		var g = '';
		for(var groupCode in treeBoxHelpers){
			if(groupCode){
				if(g){
					g += ','+groupCode;
				}else{
					g = groupCode;
				}
			}
		}
		
		
		var data = {};
		// 通过ajax请求数据
		/*$.ajax({
			url : '../../report/reportHelperAction.do?method=getList&g='+g,
			dataType:'json',
			async:false,
			type:'POST',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			error:function(obj){
				alert('获取自定义查询条件出错:登录超时，请重新登录。');//obj.responseText
				//alert(obj.responseText);
				return false;
			},
			complete: function(){
				return true;
			},
			success: function(responeText){
				data = responeText;
			}
		});// end of ajax
*/		for(var groupCode in treeBoxHelpers){
			if(groupCode){
				var helpers = data[groupCode] || [];
				treeBoxHelpers[groupCode] = helpers;
			}
		}
		
	};
	
	/**
	 * 加载报表配置信息。
	 */
	report._loadConfig = function(name){
		// 从服务器获取配置数据
		var config = null;
		$.ajax({
			url : '../../rodimus/report?method=config&name='+name ,
			async:false,
			error:function(obj){
				//$('body').load(obj.responseText);
				//alert(obj.responseText);
				alert('报表配置错误，请联系系统管理员。');
				return false;
			},
			complete: function(responeText){
			//	alert(responeText);
				return true;
			},
			success: function(responseText){
				config =  report.parseJson(responseText) ;
			}
		});
		// 将结果缓存起来
		if(!report._configData){
			report._configData = {};
		}
		report._configData[name] = config ;
		// 返回数据
		return config;
	} ;
	
	report.parseJson = function(text){
		if(JSON && JSON.parse){
			return JSON.parse(text);
		}
		alert("请确认使用IE 8以上浏览器，并对当前站点未启用兼容性视图。");
		return false;
	};
	/**
	 * 界面美化。
	 */
	report.ui = function(name){
		var oReport = $("div[name='"+name+"Report']");
		// 将统计变量搞得更显著
		var oStatTypeInput = oReport.find("*[name='stat_type']");
		var oStatTypeTd = oStatTypeInput.parent();
		var oStatTypeTh = oStatTypeTd.prev();
		
		oStatTypeTh.css({fontWeight:'bold',height:'36px'});
		oStatTypeInput.css({fontWeight:'bold'});
	};

	/**
	 * 全部展开/折叠。
	 */
	report.expandAll = function(name){
		var oReport = $("div[name='"+name+"Report']");
		
		var tds = oReport.find('.report-header-tr').find('th');
		$.each(tds,function(idx,item){
			item = $(item);
			if(item.text()=='统计变量'){
				var html = "<span name='btnExpandAll' title='全部展开/折叠' class='btn-expand'>[+]</span>";
				item.find("span[name='btnExpandAll']").remove();
				item.append(html);
				item.find("span[name='btnExpandAll']").click(function(){
					var btnExpandAll = oReport.find("span[name='btnExpandAll']");
					if(btnExpandAll.text()=='[+]'){
						$(".dig-tr").show();
						$(".btn-expand").text("[-]");
						btnExpandAll.text("[-]");
					}else{
						$(".dig-tr[l!='1']").hide();
						$(".btn-expand").text("[+]");
						btnExpandAll.text("[+]");
					}
				});
			}
		});
	};
	
	/**
	 * 获得报表配置。
	 */
	report.config = function(name){
		if(!report._configData){
			report._configData = {};
		}
		var ret = report._configData[name];
		if(!ret){
			ret = report._loadConfig(name);
		}
		return ret;
	};
	
	/**
	 * 页面结构。
	 */
	report._getInitHtml = function(name){
		var html =  "<div name=\""+name+"Report\" class=\"report\"> "+
					//"	<h1  class=\"report-title\"></h1> "+
					"	<div class=\"report-query\"><div class='report-query-banner'><img align='absmiddle' src='../../gwaic/images/icon/application_view_list.png' /><span class='report-title'></span></div></div> "+
					"	<div class=\"report-content\"> "+
					"		<table class=\"report-content-table\"></table> "+
					//"		<div class=\"report-content-paging\"></div> "+
					"	</div> "+
					"	<div class=\"report-remark\"></div> "+
					"</div>";
		return html;
	} ;
	
	/**
	 * 表头。
	 */
	report._getHeaderHtml = function(config){
		if(!config.head || !config.head.length){
			alert('未配置报表表头，请联系系统管理员。');
			return ;
		}
		var headerRow,headerColumn , headerHtml = '';
		for(var i=0;i<config.head.length;++i){
			headerRow = config.head[i];
			if(!headerRow.length){
				// 该行为空
				continue ;
			}
			headerHtml += "<tr class='report-header-tr'>";
			if(i==0){
				headerHtml += "<th rowspan='"+(config.head.length)+"'>序号</th>";
			}
			for(var r=0;r<headerRow.length;++r){
				headerColumn = headerRow[r];
				if(!headerColumn.label){
					alert('报表表头未配置“label”属性，请联系系统管理员。');
					return ;
				}else{
					headerColumn.label = headerColumn.label.replace(/\"/g,'');
				}
				headerHtml += "<th";
				if(headerColumn.rowspan){
					headerHtml += " rowspan=\""+ headerColumn.rowspan + "\"";
				}
				if(headerColumn.colspan){
					headerHtml += " colspan=\""+ headerColumn.colspan + "\"";
				}

				if(headerRow.length-1==r){// the last one
					headerHtml += " style='border-right-width: 0px;' ";
				}
				headerHtml += ">"+ headerColumn.label + "</th>";
			}
			headerHtml += "</tr>";
		}
		return headerHtml;
	} ;
	/**
	 * 重置表单。
	 */
	report.reset = function(name){
		var oReport = $("div[name='"+name+"Report']");
		
		oReport.find("input").each(function(idx,element){
			var oFormFiled = $(element);
			oFormFiled.val('');
		});
		
		$('.report-query-helper-btn').each(function(idx,ele){
			ele = $(ele);
			if(ele.text()=='默认'){
				ele.click();
			}
		});
		
		$(".report-query-treebox").each(function(idx,ele){
			ele = $(ele);
			var inputId = ele.attr('id');
			onHelperAll(inputId);
		});
	};
	/**
	 * 查询表单。
	 */
	report._getParamsFormHtml = function(config,treeBoxes,treeBoxHelpers){
		if(!config.params || !config.params.length){
			return '';
		}
		// 根据配置生成查询表单
		var retHtml = "<table class='report-query-fieldset'  align='center' cellspacing='0' cellpadding='0' "+
								 "	width='100%' border='0'>";
		
		var hiddenFieldHtml='' , paramConfig , paramId ;
		
	
		//retHtml += "<button name='"+config.name+"_query_submit' onclick=\"report.refresh('"+config.name+"')\" class='report-query-btn' >查询</button>";
		//retHtml += "<button name='"+config.name+"_query_reset' onclick=\"report.reset('"+config.name+"')\" class='report-query-btn' >重置</button>";
		
		
		retHtml += "<div style='text-align:left;'>";
		//retHtml += "<button name='"+config.name+"_query_back' onclick='report.goBack();' class='defaultButton button-back' >返回</button>";
		/*retHtml += "<button name='"+config.name+"_query_export' onclick=\"report.doExport('"+config.name
							+"',1);\" class='defaultButton button-excel' >导出大类</button>";
		retHtml += "<button name='"+config.name+"_query_export' onclick=\"report.doExport('"+config.name
		+"');\" class='defaultButton button-excelall' >导出明细</button>";
		*/
		
		retHtml +="<a href='javascript:void(0)'  id ='doExcelDownload1' ><span style='display:inline-block;line-height:23px;' class='defaultButton button-excel' >导出大类</span></a>";
		retHtml +="<a href='javascript:void(0)'  id ='doExcelDownload' ><span style='display:inline-block;line-height:23px;' class='defaultButton button-excel'>导出明细</span></a>";
		
		// 收藏
		retHtml += "<button name='"+config.name+"_query_fav' onclick=\"report.doFav('"+config.name+"');\" style='display:none;' class='defaultButton button-fav' >收藏</button>";
		retHtml += "<button name='"+config.name+"_query_unfav' onclick=\"report.doUnFav('"+config.name+"');\" style='display:none;' class='defaultButton button-unfav' >取消收藏</button>";
		
		retHtml += "<div>";
		
		return retHtml ;
	};
	report.goBack = function(){
		var oriUrl = document.referrer;
		oriUrl = oriUrl.replace('&amp;','&');
		
		if(oriUrl){
			window.location.href = oriUrl ;
		}else{
			history.go(-1);
		}
	};
	
	/**
	 * 导出。将生成文件的url赋值给对应的超链接
	 */
	report.doExport = function(name,level){
		if(!level){
			level = 0;//表示导出全部
		}
		// 1、获取查询表单数据
		var oReport = $("div[name='"+name+"Report']");
		var urlParam = '';
		
		
		var queryParamsFields = oReport.find(".report-query-field");
		for(var idx = 0; idx< queryParamsFields.length; ++ idx){
			var element = queryParamsFields[idx];
			var oFormFiled = $(element);
			
			var rule = oFormFiled.attr('rule');
			var label = oFormFiled.attr('label')||'';
			var paramName = oFormFiled.attr('name') || '';
			var paramValue = oFormFiled.val() || '';
			
			if(rule){
				// 将 rule 通过 逗号分割开
				var ruleArray = rule.split(';');
				if(ruleArray && ruleArray.length>1){
					for(var i=0;i<ruleArray.length;++i){
						var singleRule = ruleArray[i];

						if(!singleRule){
							continue;
						}
						
						if(singleRule=='must'){
							if(!paramValue){
								alert(label+'不能为空。');
								return ;
							}
						}
						//gt,2015-12-12
						var arrSingleRule = singleRule.split(',');
						if(!arrSingleRule){
							continue;
						}
						if(arrSingleRule.length<2){
							continue;
						}
						var ruleOp = arrSingleRule[0];
						var ruleVal = arrSingleRule[1];
						if('gt'==ruleOp){
							if(paramValue>ruleVal){
								
							}else{
								alert(label+'必须大于'+ruleVal);
								return ;
							}
						}
					}
					
				}
				
			}
			urlParam += '&'+paramName+'='+paramValue;
		}
		
		
		// 3、从服务器获取数据
		//report.showLoading();
		var url = "../../rodimus/report?method=excel&level=0&name=" + (name||'') + urlParam;
		//alert(url);
		//window.location.href = url ;
		//report.hideLoading();
		$('#doExcelDownload').attr('href',url);
		var url1 = "../../rodimus/report?method=excel&level=1&name=" + (name||'') + urlParam;
		$('#doExcelDownload1').attr('href',url1);
		//window.open(url);
	};
	
	/**
	 * 隐藏loading。
	 */
	report.hideLoading = function(){
		$('#loadingDiv').hide();
		$('#opacityDiv').hide();
	};
	
	/**
	 * 从服务器获得数据。
	 */
	report.data = function(name,render){
		var data = {};
		var oReport = $("div[name='"+name+"Report']");
		// 1、获取查询表单数据
		var queryData = {};
		
		
		var queryParamsFields = oReport.find(".report-query-field");
		for(var idx = 0; idx< queryParamsFields.length; ++ idx){
			var element = queryParamsFields[idx];
			var oFormFiled = $(element);
			var rule = oFormFiled.attr('rule');
			var label = oFormFiled.attr('label')||'';
			var paramName = oFormFiled.attr('name') || '';
			var paramValue = oFormFiled.val() || '';
			if(rule){
				// 将 rule 通过 逗号分割开
				var ruleArray = rule.split(';');
				if(ruleArray && ruleArray.length>1){
					for(var i=0;i<ruleArray.length;++i){
						var singleRule = ruleArray[i];

						if(!singleRule){
							continue;
						}
						
						if(singleRule=='must'){
							if(!paramValue){
								alert(label+'不能为空。');
								return ;
							}
						}
						//gt,2015-12-12
						var arrSingleRule = singleRule.split(',');
						if(!arrSingleRule){
							continue;
						}
						if(arrSingleRule.length<2){
							continue;
						}
						var ruleOp = arrSingleRule[0];
						var ruleVal = arrSingleRule[1];
						if('gt'==ruleOp){
							if(paramValue>ruleVal){
								
							}else{
								alert(label+'必须大于'+ruleVal);
								return ;
							}
						}
					}
					
				}
				
			}
			queryData[paramName] = paramValue;
		}
		
		
		queryData['r'] = Math.random();
		
		// 2、获取分页数据
		queryData.pageSize = oReport.find("*[name='pageSize']").val() || 10;
		queryData.pageNo   = oReport.find("*[name='pageNo']").val() || 1;
		// 3、从服务器获取数据
		// 报表名称
		queryData.name = name ;
		
		report.showLoading();
		
		$.ajax({
			url : '../../rodimus/report?method=data&name='+name ,
			dataType:'json',
			data:queryData,
			async:true,
			type:'POST',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			error:function(obj){
				alert('数据查询出错，请重新登录后重试。');
				//alert(obj.responseText);
				return false;
			},
			complete: function(){
				report.hideLoading();
				return true;
			},
			success: function(responeText){
				data = responeText;
				if(render){
					render(name,data);
				}
			}
		});
		
		// 4、返回数据
		return data;
	} ;
	
	/**
	 * 显示Loading。
	 */
	report.showLoading = function(){
		$('#loadingDiv').show();
		//保持遮罩层能挡住整个页面
		$('#opacityDiv').css('height',$('body').height());
		$('#opacityDiv').show();
	};
	
	/**
	 * 刷新。
	 */
	report.refresh = function(name){
		
		var oReport = $("div[name='"+name+"Report']");
		var config = report.config(name);
		// 0、显示为正在加载数据

		
		// 1、获取配置信息
		if(!config.headerDataConfig || !config.headerDataConfig.length){
			alert("报表 headerDataConfig 解析错误，请联系系统管理员。");
			return ;
		}
		
		
		// 2、从服务器重新加载数据
		report.data(name,report.gridRender);
		report.doExport(name);
	} ;
	
	/**
	 * 展开节点。
	 */
	report.expand = function(v,isExpand){
		v = v || '';
		var children = $("tr[p='"+v+"']");
		if(isExpand==undefined || isExpand==null ){//如果未传入
			if(children.length>0){
				var child = children[0];
				child = $(child);
				var displayStyle = child.css('display');
				if(displayStyle=='none'){
					isExpand = true;
				}else{
					isExpand = false;
				}
			}else{//没有子节点
				return ;
			}
		}
		
		if(isExpand){
			children.show();
			$("#btn"+v).text("[-]");
		}else{
			// 迭代折起子节点
			$.each(children,function(idx,item){
				var item = $(item);
				var v = item.attr('v');
				report.expand(v,false);
			});
			children.hide();
			$("#btn"+v).text("[+]");
		}
	};
	
	/**
	 * 渲染查询结果。
	 */
	report.gridRender = function(name,reportData){
		
		var config = report.config(name);
		var oReport = $("div[name='"+name+"Report']");
		
		var headerHtml = report._getHeaderHtml(config);
		
		oReport.find(".report-header-tr").remove();
		oReport.find('.report-content-table').prepend(headerHtml);
		
		// 3、渲染到Html Table
		var trs = oReport.find(".report-data-tr");
		trs.remove();
		trs = oReport.find(".dig-tr");
		trs.remove();
		// 3.1、如果数据为空
		if(!reportData || !reportData.rows || !reportData.rows.length){ 
			var emptyDataHtml = "<tr class='report-data-tr'><td colspan=\""+
			(config.headerDataConfig.length+1)+"\" class='report-data-empty' style='border-width:0px;'><img src='../../gwaic/images/newicon/fenye.gif'><span style='line-height:36px;'>没有你需要的查询的记录</span></td></tr>";
			$("div[name='"+name+"Report']").find(".report-paging-tr").remove();
			$("div[name='"+name+"Report']").find('.report-content-table').append(emptyDataHtml);
			return ;
		}
		// 3.2、渲染数据
		var isDig = false ;
//		if(config.dig){
//			isDig = true;
//		}
		
		var digValue ,digText,digParent,digLevel;
		var dataHtml = '' , rowData ;
		
		if(reportData.rows.length>0){
			var t = reportData.rows[0]['digLevel'] ;
			if(t){
				isDig = true;
			}
		}
		
		for(var i = 0; i<reportData.rows.length;++i){
			rowData = reportData.rows[i];
			
			dataHtml += "<tr";
			if(isDig){
				
				digValue = rowData['digValue'] || '';
				digParent = rowData['digParent'] || '';
				digText = rowData['digText'] || '';
				digLevel = rowData['digLevel'] || '1';
				
				dataHtml += " class='dig-tr' ";
				dataHtml += " id='tr"+digValue+"' ";
				dataHtml += "v='"+digValue+"' ";
				dataHtml += "p='"+digParent+"' ";
				dataHtml += "l='"+digLevel+"' ";
				
				if(digLevel>1){
					dataHtml += " style='display:none;' ";
				}
			}
			dataHtml += " class='report-data-tr'>";
			if(digText=='总计'){
				dataHtml += "<td style='text-align:center;'>&nbsp;</td>";
			}else{
				dataHtml += "<td style='text-align:center;'>"+rowData['rowIdx']+"</td>";
			}
			
			if(isDig){
				
				
				dataHtml += "<td style='text-align:left;";
				if(digLevel>1){
					dataHtml += "padding-left:"+(30*digLevel)+"px;";
				}
				dataHtml += "'>";
				if(digText=='总计'){
					dataHtml += "<span style='font-weight:bold;'>总&nbsp;&nbsp;计<span>";
				}else{
					dataHtml += digText;
					dataHtml += "<span id=\"btn"+digValue+"\" class='btn-expand' onclick=\"report.expand('"+digValue+"');\">[+]</span>";
				}
				
				dataHtml += "</td>";
			}
			var columnConfig ,cellData;
			for(var c = 0;c<config.headerDataConfig.length; ++c){
				if(isDig){
					if(c==0){
						continue;
					}
				}
				columnConfig = config.headerDataConfig[c];
				if(!columnConfig.name){
					alert("headerDataConfig必须设置name属性，请联系系统管理员。");
					return ;
				}
				// begin of style
				dataHtml += "<td style=\"";
				if(columnConfig.align){
					dataHtml += "text-align:"+columnConfig.align+";";
				}
				// the last td
				if(c==config.headerDataConfig.length-1){
					dataHtml += "border-right-width: 0px;";
				}
				// end of style
				dataHtml += "\" ";
				
				cellData = rowData[columnConfig.name] || '&nbsp;';
				
				dataHtml += ">" + cellData;
				dataHtml += "</td>";
			}
			dataHtml += "</tr>";
			
		}
		oReport.find('.report-content-table').append(dataHtml);
		
		// 如果没有子节点，则隐藏折叠按钮
		$.each($(".dig-tr"),function(idx,item){
			item = $(item);
			var v = item.attr('v');
			var children = $("tr[p='"+v+"']");
			if(children.length>0){//有子节点
				
			}else{
				$('#btn'+v).after("<span style='display:none;'>[x]</span>");//占位，以便对齐
				$('#btn'+v).remove();
			}
		});
		//oReport.find(".report-data-tr").find(".btn-expand").length>0
		if(isDig){
			report.expandAll(name);
		}
		// 4、渲染分页
		oReport.find(".report-paging-tr").remove();
		
		if(reportData && reportData.paging){ 
			var pagingHtml = "<tr class='report-paging-tr'><td colspan=\""+(config.headerDataConfig.length+1)+"\" style='border-right-width: 0px;'>";
			pagingHtml += "共"+reportData.paging.totalCnt+"条结果&nbsp;&nbsp;";
			pagingHtml += "每页<select name='pageSize'><option value='"+reportData.paging.pageSize+"'>"+reportData.paging.pageSize+"</option></select>条&nbsp;&nbsp;";
			pagingHtml += "<span class='report-paging-nav-first'>首页</span> ";
			pagingHtml += "<span class='report-paging-nav-pre'>上一页</span>&nbsp;";
			pagingHtml += "<span class='report-paging-nav-next'>下一页 </span>&nbsp;";
			pagingHtml += "<span class='report-paging-nav-last'>末页</span>&nbsp;&nbsp;";
			pagingHtml += "第<select class='report-paging-pageno' name='pageNo'>";
			for(var p=1;p<=reportData.paging.pageCnt;++p){
				pagingHtml += "<option value='"+p+"' ";
				if(reportData.paging.pageNo==p){
					pagingHtml += " selected='true' ";
				}
				pagingHtml += ">"+p+"</option>";
			}
			pagingHtml += "</select>页&nbsp;";
			pagingHtml += "共<span class='report-paging-pageCnt'>"+reportData.paging.pageCnt+"</span>页&nbsp;";
			pagingHtml += "</td></tr>";
			var oReport = $("div[name='"+name+"Report']");
			oReport.find('.report-content-table').append(pagingHtml);
			
			oReport.find('.report-paging-nav-first').click(function(){
				report.goToPage(name,1);
			});
			oReport.find('.report-paging-nav-pre').click(function(){
				var toPageNo = reportData.paging.pageNo-1;
				if(toPageNo<1){
					toPageNo = 1;
				}
				report.goToPage(name,toPageNo);
			});
			oReport.find('.report-paging-nav-next').click(function(){
				var toPageNo = reportData.paging.pageNo+1;
				if(toPageNo>reportData.paging.pageCnt){
					toPageNo = reportData.paging.pageCnt;
				}
				report.goToPage(name,toPageNo);
			});
			oReport.find('.report-paging-nav-last').click(function(){
				report.goToPage(name,reportData.paging.pageCnt);
			});
			
		}
	};
	
	/**
	 * 跳转到指定页码。 
	 */
	report.goToPage = function(name,toPageNo){
		toPageNo = toPageNo || '1';
		var oPageNo = $("div[name='"+name+"Report']").find("*[name='pageNo']");
		oPageNo.val(toPageNo);
		report.refresh(name);
	} ;
	
	/**
	 * 返回Helper Html字符串。
	 */
	report.initHelper = function(helperType,initParam1,initParam2,initParam3,initParam4,initParam5){
		// 日期类型的需要传入两个参数，形如：'app_date_begin','app_date_end'，为开始日期和结束日期的name
		if(helperType=='date'){
			// 应该传递报表的name，以便支持同一个页面多个报表。时间紧迫，先不管了，有时间重构时候再说吧。
			var html = "<span onclick=\"report.dateHelperOnClick('"+initParam1+"','"+initParam2+"','preWeek')\" class='report-query-helper-btn'>上一周</span>" +
					"<span onclick=\"report.dateHelperOnClick('"+initParam1+"','"+initParam2+"','preMonth')\" class='report-query-helper-btn'>上一月</span>" +
					"<span onclick=\"report.dateHelperOnClick('"+initParam1+"','"+initParam2+"','preQuarter')\" class='report-query-helper-btn'>上一季度</span>" +
					"<span onclick=\"report.dateHelperOnClick('"+initParam1+"','"+initParam2+"','preYear')\" class='report-query-helper-btn'>上一年</span>";
			
			
			html += "<span onclick=\"report.dateHelperOnClick('"+initParam1+"','"+initParam2+"','default')\" class='report-query-helper-btn' style='display:none;'>默认</span>"; 
			
			return html;
		}
	};
	
	/**
	 * 日期Helper。
	 */
	report.dateHelperOnClick = function(beginDateName,endDateName,type){
		// 检查参数合法性
		if(!type){ return ; }
		if(!beginDateName){ return ; }
		if(!endDateName){ return ; }
		// 获得开始日期和结束日期的值，字符串，格式形如：yyyy-MM-dd
		var beginValue = '', endValue = '';
		var today = new Date();
		var year = today.getFullYear();
		var month , dateOfMonth ;
		
		if(type=='default'){
			// 本月第一天
			today.setDate(1);

			month = today.getMonth() + 1;
			month = (month<10)?'0'+month:month;
			dateOfMonth = today.getDate();
			dateOfMonth = (dateOfMonth<10)?'0'+dateOfMonth:dateOfMonth;
			beginValue = today.getFullYear() + '-' + month + '-' +dateOfMonth;
			// 今天
			today = new Date();
			month = today.getMonth() + 1;
			month = (month<10)?'0'+month:month;
			dateOfMonth = today.getDate();
			dateOfMonth = (dateOfMonth<10)?'0'+dateOfMonth:dateOfMonth;
			endValue = today.getFullYear() + '-' + month + '-' +dateOfMonth;
			
		}
		if(type=='preWeek'){
			var dayOfWeek = today.getDay();//1-周一，0-周日，6-周六
			// 得到本周一
			var date = today.getDate();
			if(dayOfWeek==0){
				date = date - 6;
			}else{
				date = date - dayOfWeek + 1;
			}
			// 得到前一周的周一
			date = date - 7;
			today.setDate(date);
			month = today.getMonth() + 1;
			month = (month<10)?'0'+month:month;
			dateOfMonth = today.getDate();
			dateOfMonth = (dateOfMonth<10)?'0'+dateOfMonth:dateOfMonth;
			beginValue = today.getFullYear() + '-' + month + '-' + dateOfMonth;
			// 得到前一周的周日
			today.setDate(today.getDate()+6);
			month = today.getMonth() + 1;
			month = (month<10)?'0'+month:month;
			dateOfMonth = today.getDate();
			dateOfMonth = (dateOfMonth<10)?'0'+dateOfMonth:dateOfMonth;
			endValue = today.getFullYear() + '-' + month + '-' + dateOfMonth;
		}
		
		if(type=='preMonth'){
			// 上月第一天
			today.setMonth(today.getMonth()-1);
			today.setDate(1);

			month = today.getMonth() + 1;
			month = (month<10)?'0'+month:month;
			dateOfMonth = today.getDate();
			dateOfMonth = (dateOfMonth<10)?'0'+dateOfMonth:dateOfMonth;
			beginValue = today.getFullYear() + '-' + month + '-' +dateOfMonth;
			// 上月最后一天
			today.setMonth(today.getMonth()+1);
			today.setDate(0);
			month = today.getMonth() + 1;
			month = (month<10)?'0'+month:month;
			dateOfMonth = today.getDate();
			dateOfMonth = (dateOfMonth<10)?'0'+dateOfMonth:dateOfMonth;
			endValue = today.getFullYear() + '-' + month + '-' +dateOfMonth;
			
		}
		if(type=='preQuarter'){
			today.setDate(1);
			month = today.getMonth() ;
			month = month % 3;
			today.setMonth(today.getMonth() - month - 3);
			
			month = today.getMonth() + 1;
			month = (month<10)?'0'+month:month;
			dateOfMonth = today.getDate();
			dateOfMonth = (dateOfMonth<10)?'0'+dateOfMonth:dateOfMonth;
			beginValue = today.getFullYear() + '-' + month + '-' +dateOfMonth;
			// 上季度最后一天
			today.setMonth(today.getMonth()+3);
			today.setDate(0);
			month = today.getMonth() + 1;
			month = (month<10)?'0'+month:month;
			dateOfMonth = today.getDate();
			dateOfMonth = (dateOfMonth<10)?'0'+dateOfMonth:dateOfMonth;
			endValue = today.getFullYear() + '-' + month + '-' +dateOfMonth;
			
		}
		if(type=='preYear'){
			var yearBefore = year - 1;
			beginValue = yearBefore + '-01-01';
			endValue = yearBefore + '-12-31';
		}
		// 设置值到输入框
		var oBegin = $("input[name='"+beginDateName+"']");
		oBegin.val(beginValue);
		var oEnd = $("input[name='"+endDateName+"']");
		oEnd.val(endValue);
	};

	report.initFav = function(reportCode){
		var oReport = $("div[name='"+reportCode+"Report']");
		if(oReport.length<1){
			return ;
		}
		$.ajax({
			url :'../../report/reportListAction.do?method=isFav&reportCode='+reportCode+'&n='+Math.random(),
			dataType:'json',
			type:"post",
			error:function(obj){
				alert('获取收藏状态出错。');
				return false;
			},
			success: function(data){
				if(data=="1"){
					var oUnFavBtn = oReport.find("button[name='"+reportCode+"_query_unfav']");
					oUnFavBtn.show();
				}else{
					var oFavBtn = oReport.find("button[name='"+reportCode+"_query_fav']");
					oFavBtn.show();
				}
			}
		});
	};
	
	report.doUnFav = function(reportCode){
		$.ajax({
			url : '../../report/reportListAction.do?method=fav&reportCode='+reportCode+'&favSign=-1',
			dataType:'json',
			type:"post",
			error:function(obj){
				alert('取消收藏操作失败，请稍后重试或联系管理员。');
				return false;
			},
			success: function(data){
				var oReport = $("div[name='"+reportCode+"Report']");
				if(oReport.length<1){
					return ;
				}
				var oUnFavBtn = oReport.find("button[name='"+reportCode+"_query_unfav']");
				oUnFavBtn.hide();
				var oFavBtn = oReport.find("button[name='"+reportCode+"_query_fav']");
				oFavBtn.show();
			}
		});
	};
	

	report.doFav = function(reportCode){
		
		$.ajax({
			url : '../../report/reportListAction.do?method=fav&reportCode='+reportCode+'&favSign=1',
			dataType:'json',
			type:"post",
			error:function(obj){
				alert('收藏操作失败，请稍后重试或联系管理员。');
				return false;
			},
			success: function(data){
				var oReport = $("div[name='"+reportCode+"Report']");
				if(oReport.length<1){
					return ;
				}
				var oUnFavBtn = oReport.find("button[name='"+reportCode+"_query_unfav']");
				oUnFavBtn.show();
				var oFavBtn = oReport.find("button[name='"+reportCode+"_query_fav']");
				oFavBtn.hide();
			}
		});
	};
	
	report.showRemark = function(reportCode,config){
		// 取得要显示的文本
		var remark = config.remark || '';
		
		// 获得页面控件
		var oReport = $("div[name='"+reportCode+"Report']");
		if(oReport.length<1){
			return ;
		}
		
		var html = "<span style='font-weight:bold;'>说明：</span>" + remark ;
		
		oReport.find('.report-remark').html(html);
	};
	
	/**
	 * 从url中获得参数。
	 */
	report.getParameter = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null) { 
		   return unescape(r[2]); 
		} 
		return null; 
	};
	
});

/**
 * 初始化报表显示。
 */
$(function(){
	var name = report.getParameter('name') || report.getParameter('code') || '';
	if(!name){
		//alert('请传入报表代码。');
		document.write('请传入报表代码。');
		return ;
	}
	report.init(name);
});
