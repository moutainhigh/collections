(function (factory) {
    if (typeof define === "function" && define.amd) {
        // AMD模式
        define(["jquery",'jquery.Jcrop'], factory);
    } else {
        // 全局模式
        factory(jQuery);
    }
 }(function($) {
    var UploadToo;
    UploadToo = function(element, options) {
      this.id = element.attr('id');
      this.init(options);
    };
    /*UploadToo.defaults = {
      state: '',      //文件状态（默认新上传，未通过，通过，其他）
      thumbFileId :'',      //缩略图路径
      fileId: '',      //文件路径
      uploadFileClass: '',      //上传样式
      uploadFileTip: '',      //上传提示信息
      uploadCutTip: '请裁去多余的部分，如果得不到符合的图片，请重新上传',      //剪切提示信息
      uploadUrl: '',      //上传路径
      uploadParam: {},    //上传参数
      isCut: true,      //是否剪切
      uploadCutUrl: '',      //剪切后上传路径  
      cutParam: {},    //剪切后上传的参数
      uploadSuccess: null,      //上传成功的回掉方法
      cutSuccess: null,      //剪切成功回掉方法
      callback: null,      //确定裁剪后的回掉
      theme: {'':0,'1':1,'2':2,'3':1, '4':1}      //state:theme(
                                                 //0：没有文件上传的样式，1：有移除的样式，2：没有移除的样式)
    };*/
    // 文件上传的dom结构
    UploadToo.dom = [
        '<div class="upload_container {#uploadFileClass#}">',
            '<div class="upload_touch">',
                '<div class="upload_tip">{#uploadFileTip#}</div>',
                '<div class="upload_add"></div>',
            '</div>',
            '<div class="upload_file hide">',
                '<img src="">',
                '<div class="upload_state upload_state_{#state#}"></div>',
                '<div class="upload_close"></div>',
            '</div>',   
        '</div>'
    ].join('');
    //文件剪切的dom结构
    UploadToo.cropDom = [
        /*'<div class="loading_container"><div class="loading">',
            '<div class="loading_anim"></div><div class="loading_anim"></div><div class="loading_anim"></div><div class="loading_anim"></div><div class="loading_anim"></div>',
        '</div></div>', */               
        '<div id="upload_crop_container" class="upload_crop_container">',
            '<div id="cut_operation">',
                '<div class="tip">请裁去多余的部分，如果得不到符合的图片，请重新上传</div>',
                '<img id="cropImage" src="">',
                '<input type="hidden" id="x" name="x" value="0" />',
                '<input type="hidden" id="y" name="y" value="0" />',
                '<input type="hidden" id="w" name="w" value="0" />',
                '<input type="hidden" id="h" name="h" value="0" />',
                '<div class="button_container">',
                    '<input id="uploadcut" class="button" type="button" value="裁剪并上传" >',
                    '<input id="cancelcut" class="button" type="button" value="取消">',
                '</div>',
            '</div>',
            '<div id="cut_show" class="hide">',
                '<div class="tip">这是您上传的图片，如符合您的需求请确认，不符合请重新裁剪。</div>',
                '<img id="showImage" src="">',
                '<div class="button_container">',
                    '<input type="button" id="reuploadcut" class="button" value="重新裁剪" >',
                    '<input type="button" id="confirmcut" class="button" value="确定" >',
                '</div>',
            '</div>',
            '<div id="cut_preview" class="hide">',
                '<img id="previewImg">',
                '<div class="previewText"></div>',
                '<div class="close_preview"></div>',
            '</div>',
        '</div>'
    ].join('');

    UploadToo.loadingDom = [
        '<div id="loading" class="loading">',
            '<span></span>',
        '</div>'
    ].join('');
    
    UploadToo.locateCrop = function(){
        var scrollTop=0;
        if(document.documentElement&&document.documentElement.scrollTop)
        {
            scrollTop=document.documentElement.scrollTop;
        }
        else if(document.body)
        {
            scrollTop=document.body.scrollTop;
        }
        var marginLeft = - ($("#upload_crop_container").width() / 2);
        $("#upload_crop_container").css("top",scrollTop + 50).css("margin-left",marginLeft);
    };
    
    UploadToo.closePreview = function(){
        $("#upload_crop_container").addClass("hide");
    };
    
    UploadToo.previewFileFun = function(e){
    	if($("body").find("#upload_crop_container").length == 0){
            var html = UploadToo.formateString(UploadToo.cropDom, {});
            $("body").append(html);
            var cropBoxHeight = document.documentElement.clientHeight - 190;
            $("#cropImage").css("height",cropBoxHeight);
            $("#showImage").css("height",cropBoxHeight);
            $("#previewImg").css("height",cropBoxHeight);
            /*var divwidth = $("#previewImg").css('width');
            $("#cut_preview").css('width',divwidth);*/
            $("#cut_preview").find(".close_preview").off().on("click",UploadToo.closePreview);
        }
        
        $("#cut_preview").find("img").attr("src",'');
        $("#previewImg").load(function(){
        	$("#cut_preview").siblings().addClass("hide");
        	$("#cut_preview").removeClass("hide");
        	$("#cut_preview").find('.previewText').text($(e.target).attr('approvemsg'));
            $("#upload_crop_container").removeClass("hide");
            UploadToo.locateCrop();
        });
        var src = '../../../upload/showPic.do?fileId='+$(e.target).attr('fileid');
        $("#cut_preview").find("img").attr("src",src);
    };
    

    UploadToo.formateString = function (str, data){
        return str.replace(/\{#(\w+)#\}/g,function(match, key){
            return typeof data[key] === undefined ? '' : data[key];
        })
    };
    
    UploadToo.prototype = {
        init: function(options){    //初始化方法
        	
        	this.getOptions(options);
            var html = UploadToo.formateString(UploadToo.dom, this.settings);
            $('#'+this.id).after(html);
            $('#'+this.id).css("display","none").on("change",$.proxy(this.uploadFun,this));
            var nextElement = $('#'+ this.id).next();
            nextElement.find(".upload_add").on("click",$.proxy(this.addFileFun,this));
            nextElement.find(".upload_close").on("click",$.proxy(this.removeFileFun,this));
            nextElement.find("img").on("click",function(event){UploadToo.previewFileFun(event)});
            if(this.settings.fileId && this.settings.fileId != ''){
               // this.loadCrop(fileUrl);
                this.renderUpload(this.settings.theme[this.settings.state],'../../../upload/showPic.do?fileId=' + this.settings.thumbFileId);
                this.cutimg = new Image();
                this.cutimg.src = '../../../upload/showPic.do?fileId=' + this.settings.fileId;
            } 
        },
        
        getOptions: function(options){  //得到初始化参数化
        	var op = {
        			  state: $('#'+this.id).attr('state') || '',
        		      thumbFileId :$('#'+this.id).attr('thumbfileid') || '',
        		      fileId: $('#'+this.id).attr('fileid') || '',
        		      approveMsg: $('#'+this.id).attr('approvemsg') || '',
        		      uploadFileClass: $('#'+this.id).attr('uploadfileclass') || '', 
        		      uploadFileTip: $('#'+this.id).attr('uploadfiletip') || '',
        		      uploadCutTip: $('#'+this.id).attr('uploadcuttip') || '请裁去多余的部分，如果得不到符合的图片，请重新上传',
        		      uploadUrl: $('#'+this.id).attr('uploadurl') || '',
        		      uploadParam: $('#'+this.id).attr('uploadparam') || {},
        		      isCut: $('#'+this.id).attr('iscut') || true,
        		      uploadCutUrl: $('#'+this.id).attr('uploadcuturl') || '', 
        		      cutParam: $('#'+this.id).attr('cutparam') || {},
        		      uploadSuccess: $('#'+this.id).attr('uploadsuccess') || null,
        		      cutSuccess: $('#'+this.id).attr('cutsuccess') || null,
        		      callback: $('#'+this.id).attr('callback') || null,
        		      theme: $('#'+this.id).attr('theme') || {'':0,'1':1,'4':1,'3':2,'0':2}
        	};
            this.settings = $.extend({}, op, options);
        	
        },

        destory: function(){
        	var nextElement = $('#'+this.id).next();
            nextElement.remove();
            $("#upload_crop_container").remove();
            $('#'+this.id).removeData("uploadtool");
        },

        showloading: function(){
            
        },

        hideloading: function(){},

        addFileFun: function(){
            $('#'+this.id).click();
        },
        removeFileFun: function(){
        	var item = $('#'+this.id);
        	item.off('change').on("change",$.proxy(this.uploadFun,this));
        	this.renderUpload(0,'');
        	/*var $_this = this;
            
            var fId = item.attr('fid');
            if(fId){
            	$.ajax({
            		url: '../../../upload/delPic.do',
            		type: 'post',
            		data: {fId:fId},
            		dataTye: 'json',
            		success: function(data){
            			$_this.renderUpload(0,'');
            		},error: function(e){
            			
            		}
            	});
            }else{
            	$_this.renderUpload(0,'');
            }*/
        },
        
        
        uploadFun: function(){
            /*this.settings.uploadSuccess();*/
        	var $_this = this;
        	var value = $('#'+$_this.id).val();
        	var valLen = value.length - 4;
        	var format = value.substring(valLen);
        	format =format.toLowerCase();
        	if( format!= '.jpg' && format!= 'jpeg' && format!= '.png' && format!= '.gif' && format!= '.bmp' && format!= 'tiff'){
        		jazz.info('请上传jpg、jpeg、png、gif、bmp、tiff等格式的图片！');
        		return;
        	}
        	$('body').loading();
            $.ajaxFileUpload
            (
                {
                    url: $_this.settings.uploadUrl + '?gid=' + $_this.settings.uploadParam.gid, //用于文件上传的服务器端请求地址up参数标记此次是上传操作还是裁剪操作
                    secureuri: false, //一般设置为false，是否安全上传
                    fileElementId: $('#'+$_this.id).attr("name"), //文件上传控件的id属性 
                    dataType: 'json', //返回值类型 一般设置为json 期望服务器传回的数据类型
                    data: $_this.settings.uploadParam,
                    success: function(data){
                    	if(data.exceptionMes){
                    		alert(data.exceptionMes);
                    	}else{
                    		if($_this.settings.isCut){
                    			if(!data[0]){
                    				alert('返回数据有误！请联系管理员！');
                    				return;
                    			}
                    			var id = data[0].fileId;
                        		$_this.settings.fileId = id;
                        		$_this.settings.cutParam.fileId = id;
                        		$_this.loadCrop('../../../upload/showPic.do?fileId=' + id +'&gid='+$_this.settings.uploadParam.gid);
                    		}else{
                    			$_this.renderUpload(1,'../../../upload/showPic.do?fileId=' + id +'&gid='+$_this.settings.uploadParam.gid);
                    		}
                    		var uploadSuccess = $_this.settings.uploadSuccess;
                    		if(uploadSuccess){
                    			if(typeof uploadSuccess === 'function'){
                    				uploadSuccess($('#'+$_this.id),id);
                    			}
                    			else{
                    				uploadSuccess = window[uploadSuccess];
                    				uploadSuccess($('#'+$_this.id),id);
                    			}
                    		}
                    		
                    	}
                    	
                    },
                    error: function (data, status, e)//服务器响应失败处理函数
                    {
                        alert(e);
                    }
                }
            )
        },
        loadCrop: function(path){
        	this.img = new Image();
            this.img.src = path;
            var cropBoxHeight = document.documentElement.clientHeight - 190;
            if($("body").find("#upload_crop_container").length == 0){
                var html = UploadToo.formateString(UploadToo.cropDom, this.settings);
                $("body").append(html);
                $("#cropImage").css("height",cropBoxHeight);
                $("#showImage").css("height",cropBoxHeight);
                $("#previewImg").css("height",cropBoxHeight);
                $("#cut_preview").find(".close_preview").off().on("click",UploadToo.closePreview);
            }else{
                $("#upload_crop_container").removeClass("hide");
                $("#cut_operation").siblings().addClass("hide");
                $("#cut_operation").removeClass("hide"); 
            }
            $("#upload_crop_container").find("#uploadcut").off().on("click",$.proxy(this.uploadCut,this));
            $('#upload_crop_container').find('#cancelcut').off().on('click',$.proxy(this.cancelCut,this));
            $("#upload_crop_container").find("#reuploadcut").off().on("click",$.proxy(this.reuploadCut,this));
            $("#upload_crop_container").find("#confirmcut").off().on("click",$.proxy(this.confirmCut,this));
            $("#cropImage").attr("src",path);
            $("#cropImage").load(function(){
                 if(!(typeof(window.jcrop_api) === "undefined")){
                      changecrop(window.jcrop_api);
                  }else{
                     //同时启动裁剪操作，触发裁剪框显示，让用户选择图片区域
                      $("#cropImage").Jcrop({
                          bgColor: 'black',
                          bgOpacity: .6,
                          onChange: showCoords,   //当裁剪框变动时执行的函数
                          onSelect: showCoords,   //当选择完成时执行的函数
                      },function(){
                     	 if(!window.jcrop_api){
                     		 window.jcrop_api = this;
                              changecrop(window.jcrop_api);
                     	 }
                        });
                  }
            });
           
             function showCoords(c) {
                $("#upload_crop_container").find("#x").val(parseInt(c.x));
                $("#upload_crop_container").find("#y").val(parseInt(c.y));
                $("#upload_crop_container").find("#w").val(parseInt(c.w));
                $("#upload_crop_container").find("#h").val(parseInt(c.h)); 
            }
             function changecrop (jcrop_api){
            	var image = new Image();
            	image.src = path;
            	image.onload = function(){
            		jcrop_api.setImage("");
                    jcrop_api.setImage(image.src);
                    /*var bounds=jcrop_api.getBounds();
                    var imgwidth=bounds[0];
                    var imgheight=bounds[1];*/
                    var imgwidth = image.naturalWidth || width;
                    var imgheight = image.naturalHeight || height;
                    /*var width = 210;
                    var height = 297;
                    if(imgwidth>imgheight){
                        width = 297;
                        height = 210;
                    }
                    jcrop_api.setOptions({
                        boxHeight: cropBoxHeight,
                        aspectRatio: width / height
                     });*/
                    jcrop_api.setOptions({
                        boxHeight: cropBoxHeight
                     });
                    jcrop_api.animateTo(
                            [0,0,imgwidth,imgheight],
                            function(){
                            	UploadToo.locateCrop();
                            	$('body').loading('hide');
                           //   this.release();
                            })
                    //jcrop_api.setSelect([0, 0, width, height]);
            	}
            	
            }
        },
        uploadCut: function(){
        	
            /*this.settings.cutSuccess();*/
            var x = $("#upload_crop_container").find("#x").val();
            var y = $("#upload_crop_container").find("#y").val();
            var w = $("#upload_crop_container").find("#w").val();
            var h = $("#upload_crop_container").find("#h").val();
            if(!x || x == 'NaN'){
            	alert('请裁剪后上传！');
            	return;
            }
            var src = this.img.src;
            var data = this.settings.cutParam;
            data.pointX = x > 0 ? x : 0;
            data.pointY = y > 0 ? y : 0;
            data.width = w > 0 ? w : 0;
            data.height = h > 0 ? h :0;
            var $_this = this;
            $('body').loading('show');
            $.ajax({
                url:$_this.settings.uploadCutUrl,
                type:"post",
                dataType:"json",
                data:data,
                success:function(da){
                	$_this.settings.fileId = da.fileId;
                	$_this.settings.thumbFileId = da.thumbFileId;
            		$_this.showCut('1','../../../upload/showPic.do?fileId=' + $_this.settings.fileId +'&gid=' + data.gid);
            		if($_this.settings.cutSuccess){
            			$_this.settings.cutSuccess($('#'+$_this.id),$_this.settings.fileId);
            		}
                	
                },
                error:function(data,status,e){
                     alert(e);
                }
            })
        },
        
        cancelCut: function(){
        	$('#upload_crop_container').addClass('hide');
        	var item = $('#'+this.id);
        	item.off('change').on("change",$.proxy(this.uploadFun,this));
        },

        reuploadCut: function(){
            $("#cut_operation").siblings().addClass("hide");
            $("#cut_operation").removeClass("hide");           
            $("#cut_show").find("img").attr("src","");
        },

        confirmCut: function(){
            $("#upload_crop_container").addClass("hide");
            this.renderUpload(1,this.cutimg.src);
            var callback = this.settings.callback;
    		if(callback){
    			if(typeof callback === 'function'){
    				callback($('#'+this.id));
    			}
    			else{
    				callback = window[callback];
    				callback($('#'+this.id));
    			}
    		}
        },

        showCut: function(state,path){
            this.cutimg = new Image();
            this.cutimg.src = '../../../upload/showPic.do?fileId=' + this.settings.thumbFileId +'&gid=' + this.settings.cutParam.gid;
            this.settings.state = state;
            
            $("#cut_show").find("img").attr("src","");
            $('#showImage').load(function(){
            	$("#cut_show").siblings().addClass("hide");
                $("#cut_show").removeClass("hide");
                UploadToo.locateCrop();
                $('body').loading('hide');
            })
            $("#cut_show").find("img").attr("src",path);
        },

        renderUpload: function(theme,path){
            var nextElement = $('#'+this.id).next();
            nextElement.find(".upload_state").attr("class","upload_state upload_state_"+this.settings.state);
            var img = nextElement.find("img");
            img.attr("src",'');
            img.attr("src",path);
            
            switch (theme)
            {
            case 0:
              //未上传文件时样式
              nextElement.find(".upload_file").addClass("hide");
              nextElement.find(".upload_touch").removeClass("hide");
              nextElement.prev().attr('thumbfileid', ''); 
              nextElement.prev().attr('fileid', '');
              nextElement.prev().attr('state', '');
              break;
            case 1:
              //文件可移除时样式
              nextElement.find(".upload_file").removeClass("hide");
              nextElement.find(".upload_touch").addClass("hide");
              nextElement.find(".upload_close").removeClass("hide");
              nextElement.prev().attr('thumbfileid', this.settings.thumbFileId); 
              nextElement.prev().attr('fileid', this.settings.fileId);
              nextElement.prev().attr('state', this.settings.state);
              img.attr('fileid',this.settings.fileId);
              img.attr('approvemsg',this.settings.approveMsg);
              break;
            case 2:
              //文件不可移除时样式
              nextElement.find(".upload_file").removeClass("hide");
              nextElement.find(".upload_touch").addClass("hide");
              nextElement.find(".upload_close").addClass("hide");
              nextElement.prev().attr('thumbfileid', this.settings.thumbFileId); 
              nextElement.prev().attr('fileid', this.settings.fileId);
              nextElement.prev().attr('state', this.settings.state);
              img.attr('fileid',this.settings.fileId);
              img.attr('approvemsg',this.settings.approveMsg);
            default:
                //未上传文件时且不可上传时样式
                nextElement.find(".upload_file").addClass("hide");
                nextElement.find(".upload_touch").removeClass("hide");
                nextElement.find(".upload_add").addClass("hide");
                nextElement.prev().attr('thumbfileid', ''); 
                nextElement.prev().attr('fileid', '');
                nextElement.prev().attr('state', '');
                break;
            }
        }

        

    };
    
    $.fn.uploadToo = function() {
      var options = arguments[0];
      var param1 = arguments[1];
      var param2 = arguments[2];
      this.each(function() {
        var instance;
        instance = $.data(this, "uploadtool");
        if (!instance) {
          $.data(this, "uploadtool", new UploadToo($(this), options));
          instance = $.data(this, "uploadtool");
        }
        if(typeof options !== "undefined"){
            var action = typeof options === "string" ? options : options.action;
            if (typeof action !== "undefined") {
             instance[action](param1,param2);  
            }
        }
        
      });
    };
   return  UploadToo;
  })
  )
