define(['require', 'jquery', 'approvecommon','approve/approve' ], function(require, $, approvecommon,approve) {
	var getParameter = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null) { 
		   return unescape(r[2]); 
		} 
		return null; 
	};
	var torch = {
		shareholder_datarender : function(event, obj) {
		},
		noShareholder_datarender : function(event, obj) {
		},
		mainPerson_datarender : function(event, obj) {
		},
		gid : getParameter('gid'),
		query_fromNames : [ 'entLinkInfo_Form', 'processAIEntInfo_Form', 'processAIEntBasicInfo_Form', 'financeInfo_Form', 'entcontactInfo_Form', 'shareholder_Form', 'noShareholder_Form', 'mainPerson_Form' ],

		processAllInfoSure : function() {
			$("div[name='entLinkInfo_Form']").formpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=entLinkInfo&gid=" + torch.gid);
			$("div[name='entLinkInfo_Form']").formpanel("reload");
			$("div[name='processAIEntInfo_Form']").formpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=processAIEntInfo&gid=" + torch.gid);
			$("div[name='processAIEntInfo_Form']").formpanel("reload");
			$("div[name='processAIEntBasicInfo_Form']").formpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=processAIEntBasicInfo&gid=" + torch.gid);
			$("div[name='processAIEntBasicInfo_Form']").formpanel("reload");
			$("div[name='financeInfo_Form']").formpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=financeInfo&gid=" + torch.gid);
			$("div[name='financeInfo_Form']").formpanel("reload");
			$("div[name='entcontactInfo_Form']").formpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=entcontactInfo&gid=" + torch.gid);
			$("div[name='entcontactInfo_Form']").formpanel("reload");
			$("div[name='shareholder_Grid']").gridpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=shareholder&gid=" + torch.gid);
			$("div[name='shareholder_Grid']").gridpanel("query", [ "shareholder_Form" ]);
			$("div[name='noShareholder_Grid']").gridpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=noShareholder&gid=" + torch.gid);
			$("div[name='noShareholder_Grid']").gridpanel("query", [ "noShareholder_Form" ]);
			$("div[name='mainPerson_Grid']").gridpanel("option", "dataurl", "../../../torch/service.do?fid=processAIFuncId&wid=mainPerson&gid=" + torch.gid);
			$("div[name='mainPerson_Grid']").gridpanel("query", [ "mainPerson_Form" ]);
			$("div[name='shareholder_Grid']").gridpanel("option", "datarender",function(obj,resultData){
				var dataArrs = resultData.data;
				var dataSize = dataArrs.length;
				if(dataSize<10){
					$("div[id='shareholder_Grid_paginator']").hide();
				}
			});
			$("div[name='noShareholder_Grid']").gridpanel("option", "datarender",function(obj,resultData){
				var dataArrs = resultData.data;
				var dataSize = dataArrs.length;
				if(dataSize<10){
					$("div[id='noShareholder_Grid_paginator']").hide();
				}
			});
			$("div[name='mainPerson_Grid']").gridpanel("option", "datarender",function(obj,resultData){
				var dataArrs = resultData.data;
				var dataSize = dataArrs.length;
				if(dataSize<10){
					$("div[id='mainPerson_Grid_paginator']").hide();
				}
			});
		},
		processAllInfoReset : function() {
			for (x in torch.query_fromNames) {
				$("div[name='" + torch.query_fromNames[x] + "']").formpanel("reset");
			}
		},
		
		_init : function() {
			require(['domReady'], function (domReady) {
				  domReady(function () {
					    $("div[name='_Form']").gridpanel("option", "datarender", torch.shareholder_datarender);
						$("div[name='_Form']").gridpanel("reload");
						$("div[name='_Form']").gridpanel("option", "datarender", torch.noShareholder_datarender);
						$("div[name='_Form']").gridpanel("reload");
						$("div[name='_Form']").gridpanel("option", "datarender", torch.mainPerson_datarender);
						$("div[name='_Form']").gridpanel("reload");

						$("div[name='processAllInfo_query_button']").off('click').click(torch.processAllInfoSure);
						$("div[name='processAllInfo_reset_button']").off('click').click(torch.processAllInfoReset);
						torch.processAllInfoSure();
						torch.getApplySetupPriviewFiles();  //预览图片
						
				  });
				});			
			
		},
		/**
		 * 预览图片
		 */
		getApplySetupPriviewFiles:function(){
			var $_this = this;
			$.ajax({
				url:"../../../torch/service.do?fid=applySetupPreview&wid=applySetupPreviewFiles&gid="+jazz.util.getParameter('gid'),
				type:'post',
				dataType:'json',
				success:function(data){
					var rows = data.data[0].data.rows;
					var categoryId='';
					var obj=$('#uploadFiles');
					$.each(rows,function(index,val){
						if(val.categoryId != categoryId){
							//新的大类
							var tmpH='<div class="content" style="clear:both;"><div class="content-top">'+
							
							'<span>'+val.title+'</span></div>'+
						    '<div id="categoryId'+val.categoryId+'" class="previes-prove"></div></div>';
							obj.append(tmpH);
							categoryId=val.categoryId;
						}
						if(val.fileId){
							var tmpH='<div class="categoryPic" ><img id="'+val.fileId+'" width="100px" height="100px" src="../../../upload/showPic.do?fileId='
							+(val.thumbFileId?val.thumbFileId:val.fileId)
							+'" ><br /><span>'+(val.refText?val.refText:'')
							+'</span></div>';
							$('#categoryId'+categoryId).append(tmpH);
						}
						
					});
					$(".previes-prove").find('img').on('click',$_this.showPic);
				},
				error:function(data){
					
				}
			});
		},
		showPic:function(e){
			
			var content = '<div style="text-align:center;width:100%;overflow-x:auto;">'
				        +     '<img src="" style="height:480px;margin:8px 0" id="showPic" title="" />'
			            + '</div>';
			var win = window.parent.jazz.widget({
    			vtype : 'window',
    			name : 'showPicDiv',
    			title : '图片预览',
    			titlealign : 'left',
    			titledisplay : true,
    			modal : true,
    			height : '540',
    			width : '800',
    			closable : true,
    			content:content
    		});
    		// 打开窗口
    		win.window('open');
    		var obj = e.srcElement?e.srcElement:e.target;
			var id=$(obj).attr('id');
			$('#showPic',window.parent.document).attr('src','../../../upload/showPic.do?fileId='+id);
		}
	};
	
	torch._init();
	return torch;

});
