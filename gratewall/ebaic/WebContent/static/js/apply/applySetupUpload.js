define(['require','jquery','common','uploadToo','apply/setupApproveMsg'], function(require, $,common,uploadToo,approveMsg){
	var upload = {
    	_init: function(){
    		require(['domReady'],function(domReady){
    			domReady(function(){
    				$('#save').on('click',upload.save);
    				upload.wrapInfo();
    				upload.reLocate();
    				upload.loadFiles();
    				
    				$("#next_step").on('click',upload.nextStep);
    				$("#pre_step").on('click',upload.backStep);
    				common.computeHeight();
    				$("#notice").on("click",function(){
    					upload.explanation();
    				});
    		 		$("#cancel").on('click',function(){
    		 			$("#download-way-box").css("display","none");
    		 		});
    		 		
    		 		$("img[name='back']").on('click',upload.hideAdvice);//隐藏退回修改意见
					$(".modifyBack").on('click',upload.showAdvice);//出现退回修改意见
					$('#id_confirm').on('click',upload.idConfirm);
					$('#continue_step').on('click',upload.continueStep);
					$('#upload_constitution').on('click',function(){
						$('#constitution').click();
					})
					$('#constitution').on('change',upload.uploadConstitution);
					$('.downloadPDF').on('click',upload.downloadPDF);
					
					upload.getDocTemplatePath();
    			});
    		});
    	},
    	
    	getDocTemplatePath:function(){
    		$("#docTemplateDownload").on('click',function(){
    			if($(this).hasClass('disabledBt')){
    				return false;
    			}
    			$("#docTemplateDownload").attr("href",'../../../upload/getDocTemplatePath.do?gid='+jazz.util.getParameter("gid"));
				
    		});
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
    	},
    	getFile : function(){		    		
    		$("#download_pdf").on("click",function(){
    			var win = jazz.widget({
        			vtype : 'window',
        			name : 'downloadPdf',
        			title : '下载生成文件',
        			titlealign : 'left',
        			titledisplay : true,
        			modal : true,
        			content : '<div style="text-align:center;padding:20px;">'+
        				          '<a id="download_pdf_direct" class="noticeBt" >下载全部文件</a></br>&nbsp;</br>'+
        				          '<a id="download_pdf_generate" class="noticeBt">下载更新文件</a>'+
        				      '</div>',
        			height : '160',
        			width : '260',
        			closable : true
        		});
        		// 打开窗口
        		win.window('open');   			
    		});
    		$("#download_pdf_direct").live('click',function(){
    			if($(this).hasClass('disabledBt')){
    				return;
    			}
    			var gid = jazz.util.getParameter("gid");    			
    			$.ajax({
    				url : '../../../pdf/check/Rule.do',
    				type : 'post',
    				dataType : 'json',
    				data : {gid:gid},
    				async : false,
    				success : function(data){
    					var check = data.check;
    					if(check == '2'){
    						$("#download_pdf_direct").attr("href","javascript:void(0)");
    						jazz.error('企业信息没有完善 请完善后再试！');
    					}else{
    						//下载上一版
    						$("#download_pdf_direct").addClass('disabledBt');
    		    			$("#download_pdf_direct").attr("href","../../../pdf/v2/getFile.do?gid="+gid+"&listCode=setup_1100");
    					}
    				},
    			});
    		});
    		   		
    		$("#download_pdf_generate").live('click',function(){
    			if($(this).hasClass('disabledBt')){
    				return;
    			}
    			var gid = jazz.util.getParameter("gid");
    			
    			$.ajax({
    				url : '../../../pdf/check/Rule.do',
    				type : 'post',
    				dataType : 'json',
    				data : {gid:gid},
    				async : false,
    				success : function(data){
    					var check = data.check;
    					if(check == '2'){
    						$("#download_pdf_generate").attr("href","javascript:void(0)");
    						jazz.error('企业信息没有完善 请完善后再试！');
    					}else{
    						//重新生成
    						$("#download_pdf_generate").addClass('disabledBt');
    						$("#download_pdf_generate").attr("href","../../../pdf/v2/getFile.do?gid="+gid+"&listCode=setup_1100&type=1");
    					}
    				},
    			});
    		});

    		
    	},
    	
    	downloadPDF: function(){
    		var  _this = $(this);
    		_this.addClass('disabledBt');
    		var gid = jazz.util.getParameter("gid");
			var downloadType = _this.attr('download');
			$.ajax({
				url : '../../../pdf/check/Rule.do',
				type : 'post',
				dataType : 'json',
				data : {gid:gid},
				async : false,
				success : function(data){
					var check = data.check;
					if(check == '2'){
						$("#download_content").attr("href","javascript:void(0)");
						jazz.error('企业信息没有完善 请完善后再试！');
					}else{
						//重新生成
						if(downloadType == 'pre'){
							$("#download_content").attr("href","../../../pdf/v2/getFile.do?gid="+gid+"&listCode=setup_1100");
						}else{
							$("#download_content").attr("href","../../../pdf/v2/getFile.do?gid="+gid+"&listCode=setup_1100&type=1");
						}
						_this.removeClass('disabledBt');
					}
				},
			});
    	},
    	
    	uploadConstitution:function(){
    		var name = $('#constitution').val();
    		var index = name.length - 4;
    		var fileType = name.substring(index);
    		if(fileType != '.pdf'){
    			jazz.error('请上传您下载的企业自主制定章程！要求文件格式为pdf。');
    			return;
    		}
    		$('#constitution_name').text(name);
    		
    		$('#constitution_loading').removeClass('hide');
    		var gid=jazz.util.getParameter("gid");
    		$.ajaxFileUpload
            (
                {
                    url: '../../../upload/upload.do?gid='+gid, //用于文件上传的服务器端请求地址up参数标记此次是上传操作还是裁剪操作
                    secureuri: false, //一般设置为false，是否安全上传
                    fileElementId: 'constitution', //文件上传控件的id属性 
                    dataType: 'json', //返回值类型 一般设置为json 期望服务器传回的数据类型
                    success: function(data){
                    	$('#constitution_loading').addClass('hide');
                    	if(data.exceptionMes){
                    		alert(data.exceptionMes);
                    	}else{
                    		var fileId =data[0].fileId;
                    		var fid=$('#constitution_name').attr('fid')?$('#constitution_name').attr('fid'):'';
                    		$.ajax({
                				url : '../../../upload/saveCustomDocInfo.do',
                				type : 'post',
                				dataType : 'json',
                				data : {gid:gid,fileId:fileId,fid:fid},
                				async : false,
                				success : function(data){
                					$('#constitution_name').attr('fid',data.data[0].data);
                					$('#constitution_name').attr('fileId',fileId);
                				},
                			});
                    		
                    		jazz.info('企业自主制定章程上传成功！');
                    	}    	
                    },
                    error: function (data, status, e)//服务器响应失败处理函数
                    {
                        alert(e);
                    }
                }
            )
    	},
    	
    	loadFiles: function(){
    		var gid=jazz.util.getParameter("gid");
    		
    		$.ajax({
    			url: '../../../upload/getList.do',
    			dataType:'json',
    			data:{gid:gid},
    			type:'post',
    			success:upload.initFiles,
    			error:function(XHR,status,error){
    				var result = $.parseJSON(XHR.responseText);
    				jazz.info(result.exceptionMes);
    			}
    		});
    	},
    	
    	initFiles: function(data){
    		var gid=jazz.util.getParameter("gid");
    		var uploadF = [];
    		var signF = [];
    		var filelist =data.list;
    		upload.step = data.step;
    		var customDoc = data.customDoc;
    		if(customDoc){
    			$('#constitution_name').attr('fid',customDoc.fId);
				$('#constitution_name').attr('fileId',customDoc.fileId);
				$('#constitution_name').text(customDoc.refText);
    		}
    		upload.showStep();
    		for(var i = 0, len = filelist.length; i < len; i++){
    			if(filelist[i]){
    				if(filelist[i].signPage && filelist[i].signPage == '1'){
    					signF.push(filelist[i]);
    				}else{
    					uploadF.push(filelist[i]);
    				}
    			}
    		}
    		var uloadfiles = {upload:uploadF};
    		var signfiles = {upload:signF};
    		$('#uploadList').renderTemplate({templateName:'upload_list'},uloadfiles);
    		$('#uploadList').find('img').each(function(index,item){
    			var fileId = $(item).attr('thumbfileid');
    			if(fileId && fileId != ''){
    				var img = new Image();
    				img.src = '../../../upload/showPic.do?fileId='+fileId+'&gid='+gid;
    				$(item).attr("src",img.src);
    				$(item).click(uploadToo.previewFileFun);
    			}
    		});
    		$('#signList').renderTemplate({templateName:'upload_list'},signfiles);
    		$('#signList').find('img').each(function(index,item){
    			var fileId = $(item).attr('thumbfileid');
    			if(fileId && fileId != ''){
    				var img = new Image();
    				img.src = '../../../upload/showPic.do?fileId='+fileId+'&gid='+gid;
    				$(item).attr("src",img.src);
    				$(item).click(uploadToo.previewFileFun);
    			}
    		});
    		$('.upload_close').on('click',function(event){
    			upload.hideImg(event);
    			});
			$('#uploadList').find('input').uploadToo({
				  uploadUrl: '../../../upload/upload.do',      //上传路径
				  uploadParam: {'gid': gid},
			      uploadCutUrl: '../../../upload/cutImage.do',      //剪切后上传路径
			      cutParam: {'gid': gid}      //剪切成功回掉方法
			});
			$('#signList').find('input').uploadToo({
				  uploadUrl: '../../../upload/upload.do',      //上传路径
				  uploadParam: {'gid': gid},
			      uploadCutUrl: '../../../upload/cutImage.do',      //剪切后上传路径
			      cutParam: {'gid': gid}      //剪切成功回掉方法
			});
    	},
    	
    	showStep: function(){
    		
    		if(upload.step && upload.step == '2'){
    			$('#stepTwo').removeClass('hide');
    			$('.upload-step').find('li').eq(2).addClass('selected');
    		}else if(upload.step && upload.step == '3'){
    			$('#stepThree').removeClass('hide');
    			$('#stepTwo').removeClass('hide');
    			$('.upload-step').find('li').eq(3).addClass('selected');
    			$('#continue_step').parent().addClass('hide');
    			$('#business_step').removeClass('novisible');
    		}
    	},
    	continueStep: function(){
    		if(!upload.step || upload.step == '' || upload.step == '1'){
    			upload.verifyStepOne();
    		}else if(upload.step == '2'){
    			upload.verifyStepTwo();
    		}
    		var gid=jazz.util.getParameter("gid");
    		$.ajax({
    			url: '../../../upload/setUploadStep.do',
    			dataType:'json',
    			data:{gid:gid,step:upload.step},
    			type:'post',
    			success:function(data){
    				
    	    	},
    			error:function(XHR,status,error){
    				var result = $.parseJSON(XHR.responseText);
    				jazz.info(result.exceptionMes);
    			}
    		});
    	},
    	
    	verifyStepOne: function(){
    		upload.step = '2';
    		upload.showStep();
    	},
    	
    	verifyStepTwo: function(){
    		upload.step = '3';
    		upload.showStep();
    	},
    	
    	hideImg: function(e){
    		var item = $(e.target);
    		item.prev().addClass('hide');
    		var img = item.prev().prev();
    		item.addClass('hide');
    		img.attr('fileid','');
    		img.attr('thumbfileid','');
    		img.addClass('hide');
    		
    		/*var fId = img.attr('fid');
            if(fId){
            	$.ajax({
            		url: '/upload/delPic.do',
            		type: 'post',
            		data: {fId:fId},
            		dataTye: 'json',
            		success: function(data){
            			item.addClass('hide');
                		img.attr('fileid','');
                		img.attr('thumbfileid','');
                		img.addClass('hide');
            		},error: function(e){
            			
            		}
            	});
            }*/
    		
    		
    	//	item.prevAll('.upload_container').find('.upload_close').click();
    	},
    	
    	addUploadCase: function(item){
    		var gid = jazz.util.getParameter("gid");
    		var fileId = item.attr('fileid') || '';
    		var info = {
    				fId: item.attr('fid') || '',
    				refId: item.attr('refid') || '',
    				refText: item.attr('reftext') || '',
    				thumbFileId: item.attr('thumbfileid') || '',
    				categoryId: item.attr('categoryid') || '',
    				dataType: item.attr('datatype') || '',
    				sn: item.attr('sn')||''
    		}
    		$.ajax({
    			url:'../../../upload/matchBusiRecord.do',
    			type:'post',
    			datatype:'json',
    			data:{gid:gid,fileId:fileId,info:JSON.stringify(info)},
    			success: function(data){
    				if(!data || data.exceptionMes){
    					jazz.error('上传文件出错！');
    				}else{
    					data = $.parseJSON(data);
    					item.attr('fid',data.fId);
    					item.attr('datatype',data.dataType);
    				}
    			},error: function(exception){
    				jazz.error('上传文件出错！');
    			}
    		});
    		var uploadfile = item.parent();
    		var container = item.parents('.container');
    		var max = container.attr('max');
    		var min = container.attr('min');
    		var isAdd = container.attr('isAdd');
    		var len = container.children().length - 1;
    		if(len < max && isAdd == '1'){
    			if(uploadfile.next().length == 0){
    				var categoryid = item.attr('categoryid');
        			var id = (parseInt(item.attr('id')) + 1) + categoryid +'file';
            		var addDom = '<div class="uploadfile"><input name="'+id+'" id="'+id+'" type="file" state="" categoryid="'+categoryid+'" thumbfileid="" fileid="" callback="addUploadCase" ></div>';
            		uploadfile.after(addDom);
            		var gid = jazz.util.getParameter("gid");
            		uploadfile.next().find('input').uploadToo({
            			uploadUrl: '../../../upload/upload.do',      //上传路径
      				    uploadParam: {'gid': gid},
      			        uploadCutUrl: '../../../upload/cutImage.do',      //剪切后上传路径
      			        cutParam: {'gid': gid}      //剪切成功回掉方法
            		});
        		}
        		
    		}
    		
    		
    	},
    	
    	
    	combiningUpload: function(item){
    		var imgitem = item.nextAll('img');
    		var fileId = item.attr('fileid') || '';
    		var info = {
    				'fId': imgitem.attr('fid') || '',
    				'refId': imgitem.attr('refid') || '',
    				'refText': imgitem.attr('reftext') || '',
    				'thumbFileId': item.attr('thumbfileid') || '',
    				'categoryId': imgitem.attr('categoryid') || '',
    				'dataType': imgitem.attr('datatype') || '',
    				'sn': item.attr('sn')||''
    				
    		};
    	
			var uploadContainer = item.parent();
    		var inputs = uploadContainer.find('input');
    		var fileid1 = inputs.eq(0).attr('fileid')||'';
    		var fileid2 = inputs.eq(1).attr('fileid')||'';
    		if(fileid1 != '' && fileid2 != ''){
    			info.srcId=(fileid1+','+fileid2);
    			var gid = jazz.util.getParameter("gid");
    			$.ajax({
    				url:'../../../upload/mergePic.do',
    				type:'post',
    				data:{
    					    gid:gid,
    					    list:JSON.stringify([fileid1,fileid2]),
    					    info:JSON.stringify(info)
    					},
    			    dataType:'json',
    			    success:function(data){
    			    	if(data){
    			    	//	data = $.parseJSON(data);
    			    		var img = item.nextAll('img');
	    			    	img.attr('fileid',data.fileId);
	    			    	img.attr('thumbfileid',data.thumbFileId);
	    			    	img.attr('srcid',fileid1+','+fileid2);
	    			    	img.attr('fid',data.fId);
	    			    	img.attr('state','1');
	    			    	var image = new Image();
	    			    	image.src = '../../../upload/showPic.do?fileId='+data.thumbFileId;
	    			    	img.attr('src',image.src);
	    			    	img.removeClass('hide');
	    			    	img.next().attr('class','upload_state upload_state_1');
	    			    	img.next().next().removeClass('hide');
	    			    	img.click(uploadToo.previewFileFun);
	    			    	uploadContainer.find('.upload_container').find('.upload_close').click();
    			    	}else{
    			    		//报错?
    			    		jazz.error('合成图片错误！');
    			    	}
    			    	
    			    	
    			    }
    			});
    		}
    		
    	},
    	
    	save:  function(){
    		var uploadlist = [];
    		$('.container').each(function(index,item){
    			var files ={};
    			files.title = $(item).attr('title');
    			files.categoryId = $(item).attr('categoryId');
    			files.fileArray = [];
    			$(item).find('input').each(function(i,elem){
    				var file = {};
    				if($(elem).attr('fileid')){
    					file.fileId = $(elem).attr('fileid');
    					if($(elem).attr('refid')){
        					file.refId = $(elem).attr('refid');
        				}
        				if($(elem).attr('thumbfileid')){
        					file.thumbFileId = $(elem).attr('thumbfileid');
        				}
        				if($(elem).attr('reftext')){
        					file.refText = $(elem).attr('reftext');
        				}
        				if($(elem).attr('state')){
        					file.state = $(elem).attr('state');
        				}
        				if($(elem).attr('fid')){
        					file.fId = $(elem).attr('fid');
        				}
        				file.categoryId = $(elem).attr('categoryId');
        				files.fileArray.push(file);
    				}
    				
    			});
    			$(item).find('.upload_img').each(function(i, elem){
    				var file = {};
    				if($(elem).attr('fileid')){
    					file.fileId = $(elem).attr('fileid');
    					if($(elem).attr('refid')){
        					file.refId = $(elem).attr('refid');
        				}
        				if($(elem).attr('thumbfileid')){
        					file.thumbFileId = $(elem).attr('thumbfileid');
        				}
        				if($(elem).attr('reftext')){
        					file.refText = $(elem).attr('reftext');
        				}
        				if($(elem).attr('state')){
        					file.state = $(elem).attr('state');
        				}
        				if($(elem).attr('fid')){
        					file.fId = $(elem).attr('fid');
        				}
        				file.categoryId = $(elem).attr('categoryId');
        				file.srcId = $(elem).attr('srcid');
        				files.fileArray.push(file);
    				}
    			});
    			uploadlist.push(files);
    		});
    		
    		$.ajax({
    			url: '../../../upload/saveFileInfo.do',
    			type:'post',
    			data: {gid:jazz.util.getParameter("gid"),list:JSON.stringify(uploadlist)},
    			dataType:'text',
    			success: function(data){
    				if(data.exceptionMes){
    					jazz.util.error(data.exceptionMes);
    				}
    				jazz.util.info('保存成功！');
    			}
    			
    				
    		});
    	},
    	
    	wrapInfo : function(){
    		var gid=jazz.util.getParameter("gid");
    		$(".main").renderTemplate({templateName:"content",insertType:"wrap",wrapSelector:".content"},
    				{gid:gid});
    	},
    	reLocate:function(){
    		var urlstr=location.href;
    		var urlstatus=false;
    		$(".banner a").each(function(){
    			if((urlstr+"/").indexOf($(this).attr("href"))>-1 && $(this).attr("href")!=''){
    				$(this).addClass("blueactive");
    	    		$(this).find("span:eq(0)").addClass("icon-upload-blue");
    				urlstatus=true;
    			}else{
    				$(this).removeClass("blueactive");
    				$(this).find("span:eq(0)").removeClass("icon-info-blue");
    				$(this).find("span:eq(0)").addClass("icon-info");
    			}
    			$(this).live('click',function(){
	    			return upload.save();
				});
    			
    		});
    		if(!urlstatus){
    			$(".banner a").eq(0).addClass("blueactive");
    		}

    	},
    	//上一页跳转
    	backStep:function(){
    		upload.save();
    		window.location.href="../../../page/apply/setup/contact.html?gid="+jazz.util.getParameter('gid');
    	},
    	//下一页跳转
    	nextStep:function(){
    		//找到所有待refId的元素
    		//要合并上传的
    		var c=0;
    		var imgsC= $('#uploadList').find('img[refid]');
    		var msg ="";
    		$.each(imgsC,function(i,o){
    			var fileId = $(o).attr('fileid');
    			var state = $(o).attr('state');
    			if(!fileId || state=='4'){
    				msg+=($(o).attr('reftext')+',');
    				c++;
    				if(c>=10){
    					return false;
    				}
    			}
    		});
    		//不需要合并的
    		var imgsS= $('#uploadList').find('input[refid]');
    		$.each(imgsS,function(i,o){
    			var refid = $(o).attr('refid');
    			if(!refid || refid=='leg_auth'){
    				return true;
    			}
    			var fileId = $(o).attr('fileid');
    			var state = $(o).attr('state');
    			if(!fileId || state=='4'){
    				msg+=($(o).attr('reftext')+',');
    				c++;
    				if(c>=10){
    					return false;
    				}
    			}
    		});
    		
    		
    		//签字盖章页上传检测 不用合并
    		var imgsP = $("#signList").find('input[refid]');
    		$.each(imgsP,function(i,o){
    			var refid = $(o).attr('refid');
    			if(!refid || refid=='leg_auth'){
    				return true;
    			}
    			var fileId = $(o).attr('fileid');
    			var state = $(o).attr('state');
    			if(!fileId || state=='4'){
    				msg+=($(o).attr('reftext')+',');
    				c++;
    				if(c>=10){
    					return false;
    				}
    			}
    		});
    		
    		//另外单独校验股东确认书
    		var imgsGdqrs = $("#signList").find("input[categoryid = '8']");
    		$.each(imgsGdqrs,function(i,o){
    			var fileId = $(o).attr('fileid');
    			if(fileId != ''){
    				return false;
    			}
    			c++;
				if(c>=10){
					return false;
				}
    			msg += '股东确认书';
    		});
    		
    		
    		if(msg){
    			msg = ('【'+msg.substring(0,msg.length-1));
    			if(c>=10){
    				msg+='…】等';
    			}else{
    				msg+='】';
    			}
    			jazz.error(msg+"未上传图片");
    		}else{
    			//有关联项的图片都上传了
    			upload.save();
    			window.location.href="../../../page/apply/setup/preview.html?gid="+jazz.util.getParameter('gid');
    		}
    		
    	},
    	//文件上传弹出框
    	explanation:function(){
    		var win=top.jazz.widget({
    			vtype:"window",
    			name:"topWindow",
    			title:"文件上传提示",
    			titlealign:"center",
    			titledisplay:true,
    			width:1018,
    			height:570,
    			modal:true,
    			visible:true,
    			frameurl:"../../../page/apply/setup/explanation.html",
    			buttons:[{
    				text:"我知道了",
    				align:"center",
    				click:function(e){
    					jazz.window.close(e);
    				}
    			}]
    		});
    	},
    	
    	//文件上传弹出框
    	idConfirm:function(){
    		var win=top.jazz.widget({
    			vtype:"window",
    			name:"idConfirmWindow",
    			title:"身份认证情况",
    			titledisplay:true,
    			width:'820',
    			height:'600',
    			modal:true,
    			visible:true,
    			frameurl:"../../../page/apply/setup/id_confirm_frame.html?gid="+jazz.util.getParameter('gid'),
    			buttons:[{
    				text:"我知道了",
    				align:"center",
    				click:function(e){
    					jazz.window.close(e);
    				}
    			}]
    		});
    	}
    	
     };
    upload._init();
    window.addUploadCase = upload.addUploadCase;
    window.combiningUpload = upload.combiningUpload;
    return upload;
});
