/**
 * A jQuery plugin boilerplate.
 * jquery.data2table.js
 * 
 * jufeng@gwssi.com.cn 2013.3.18
 */
var isIe = $.browser.msie;

;(function($) {
    // Change this to your plugin name.
    var pluginName = 'queryform';
    var isIe = $.browser.msie;

  /**
   * Plugin object constructor.
   * Implements the Revealing Module Pattern.
   */
    function Plugin(element, options) {
        // References to DOM and jQuery versions of element.
        var el = element;
        var $el = $(element);

        // Extend default options with those supplied by user.
        options = $.extend({}, $.fn[pluginName].defaults, options);
        var id = $el.attr("id");
        /**
     * Initialize plugin.
     */
        function init() {
            if(options.isGroup){
                options.pop = false;
            }
            if(options.isPinYinOrder){
            	options.pop = true;
            	options.data=options.data[0].data;
            }
            var keys = options.title.key; //对应数据库的字段名称
            $el.addClass("pack-up");
            $el.append("<ul id='"+$el.attr('id')+"_ul' class='pack-list'></ul>");
            if(options.isGroup){
              if(keys.indexOf(",")>-1){
                 keys = keys.split(',');
                 $el.find('#'+$el.attr('id')+'_ul').append("<li id="+keys[0]+" class='disabled'><a>"+options.title.name+"</a></li>");
              }
            }else{
                $el.find('#'+$el.attr('id')+'_ul').append("<li id="+options.title.key+" class='disabled'><a>"+options.title.name+"</a></li>");
            }
            $el.find('#'+$el.attr('id')+'_ul').append("<li class='active'><a href='#' >"+ "\u5168\u90E8"+"</a></li>");
            $el.find('#'+$el.attr('id')+'_ul').append( options.pop ? "<li class='pull-out'><a title='点击展开' href='#'><img  class='btn btn_more' src='css/images/frame_plus.png' /></a></li>" : "<li class='pull-right'><a title='点击展开' href='#'><img  class='btn btn_arrow_down' src='css/images/frame_down.png' /></a></li>");
            if(options.isGroup){    //数据分组
                for(var ii=0; ii<options.data.length; ii++){
                    $el.find('#'+$el.attr('id')+'_ul').append("<li><a id="
                            +options.data[ii].key+" id="+options.data[ii].name
                            +" href='javascript:;' >"+options.data[ii].name+" <span>"
                            +(options.data[ii].amount ? options.data[ii].amount:"")
                            +"</span></a><div class='clip' id='clip_"+ii+"' ></div></li>");
                    $el.find('#'+$el.attr('id')+'_ul').find("li:last").data("index", ii+3);
                }
                $el.find('#'+$el.attr('id')+'_ul').append("<li class='groupSel'></li>");
            }else{  //添加数据
                if(options.move2top){
                    $el.find('#'+$el.attr('id')+'_ul').append("<li class='move2topSel'></li>");
                }
                for(var ii=0; ii<options.data.length; ii++){
                    $el.find('#'+$el.attr('id')+'_ul').append("<li><a id="+options.data[ii].key+" href='javascript:;' >"+options.data[ii].title+" <span>"+(options.data[ii].amount ? options.data[ii].amount:"")+"</span></a></li>");
                    $el.find('#'+$el.attr('id')+'_ul').find("li:last").data("index", ii+3);
                }
                if(options.maxsize!=0 && parseInt(options.maxsize)>0){
                    $el.find('#'+$el.attr('id')+'_ul li:gt('+(2+options.maxsize)+')').hide();
                }
            }
            if(options.isGroup){
                $('.pull-out').hide();
            }
            if(!options.isGroup){
            $el.find("li:gt(0)").not('li:eq(1)').each(function(){
                $(this).find('a').bind('click', function(){
                   var oldItem = $(this).parent().parent().find("li.active");
                    var nowItem = $(this).parent();
                    oldItem.removeClass('active');
                    nowItem.addClass('active');
                    if(options.move2top){
                        if(nowItem.data('index') == oldItem.data('index'))
                            return;
                        if(oldItem.data('index')){
                            oldItem.insertAfter($el.find('#'+$el.attr('id')+'_ul li:eq('+oldItem.data('index')+')'));
                        }
                        if(nowItem.data('index')){
                            //nowItem.insertAfter($el.find('#'+$el.attr('id')+'_ul li:eq(1)'));
                        }
                        if($(this).parent().parent().find("li.active").css("display")=='none'){
                            $(this).parent().parent().find("li.active").css("display", "");
                        }
                    }
                    $(this).parent().parent().parent().queryform('getSelectedNode');
                })
            })}else{
                $el.find("li:gt(0)").not('li:eq(1)').each(function(index){
                     $(this).find('a').not('inner_a').bind('click', function(){
                        $(this).parent().siblings().removeClass('active');
                        $(this).parent().addClass('active');
                        $(this).parent().parent().parent().queryform('getSelectedNode');
                     });
                        $(this).find('.clip').not('inner_a').bind('click', function(){
                            $el.parent().find('div.tmp_layer').hide();
                            if($el.parent().find('#'+$el.attr('id')+'_'+(index-1)).length==0){
                                if(index<1)
                                    return;
                                $el.after('<div id="'+$el.attr('id')+'_'+(index-1)+'" class="tmp_layer"></div>');
                                var divcon = '';
                                var con = options.data[index-1].data;
                                for(var ii=0; ii<con.length; ii++){
                                    divcon += '<a title="'+ con[ii].title +'" id='+con[ii].key + ' ><span>'+ con[ii].title +'&nbsp;&nbsp;'+(con[ii].amount ? con[ii].amount : "")+'</span></a>';
                                }
                                $el.next('div#'+$el.attr('id')+'_'+(index-1)).css({"left": $(this).offset().left, "top": ($(this).offset().top + $(this).height())});
                                $el.next('div#'+$el.attr('id')+'_'+(index-1)).html(divcon);
                                
                                //关闭按钮
                                /*if($('#'+$el.attr('id')+'_'+(index-1)).find('div.closeMe').length == 0){
                                    
                                    $('#'+$el.attr('id')+'_'+(index-1)).append('<div class="closeMe">[关闭]</div>');
                                }
                                $('div.tmp_layer div.closeMe').bind('click', function(){
                                    $(this).parent().hide();
                                })*/
                                
                                $el.parent().find('div.tmp_layer a').bind('click', function(){
                                    $('div.tmp_layer a').removeClass('active');
                                    $(this).addClass('active');
                                    var index = $(this).parent().attr('id').split('_')[0];
                                    var parent_index =$(this).parent().attr('id').split('_')[1];
                                   
                                    $el.find('#'+$el.attr('id')+'_ul').find("li.inner_active").remove(); //清除上一次查询条件
                                    $el.find('#'+$el.attr('id')+'_ul').append("<li class='inner_active'><a class='inner_a' href='javascript:;'>"+$(this).text()+"</a></li>"); 
                                    $('#'+index).find('li.active').removeClass('active');
                                    $('#'+index).find('#clip_'+parent_index).parent().addClass('active');
                                    $(this).parent().hide();
                                    $('#'+index).queryform('getSelectedNode');
                                })
                                
								var mX,mY;//分别为鼠标x轴y轴
                                mX=event.clientX;//获得鼠标坐标
       							mY=event.clientY;//获得鼠标坐标
       							//设置DIV的坐标 使鼠标落在DIV内部
       							$('div.tmp_layer')[0].style.left=mX-5;
       							$('div.tmp_layer')[0].style.top=mY-1;

       							//设置鼠标事件 鼠标移出关闭
       							$el.parent().find('div.tmp_layer').bind('mouseleave',function(){
   									$(this).hide();
  								})
                            }else{
                                $el.parent().find('div#'+$el.attr('id')+'_'+(index-1)).show();
                            }
                        })
                })
            }
            if(options.isGroup){
                $el.find('#'+$el.attr('id')+'_ul li:eq(1) a').bind("click", function(){
                    $el.find("li.inner_active").remove();
                    $el.find("li.active").removeClass('active');
                    $(this).parent().addClass('active');
                    $('div.tmp_layer a').removeClass('active');
                    $(this).parent().parent().parent().queryform('getSelectedNode');
                })
            }else if(options.pop){
                $el.find('#'+$el.attr('id')+'_ul li:eq(1) a').bind("click", function(){
                    $el.find("li.active").removeClass('active');
                    $(this).parent().addClass('active');
                    $(this).parent().parent().parent().queryform('getSelectedNode');
                })
            }
            if($el.find('.pack-list').height() == $el.height()){
                $el.find('.pack-list li.pull-right').hide();
            }
            if(options.pop){    //弹出窗口
                var datahtml = $el.data('datahtml');
                 if($el.parents('body').find('#mask').length == 0){
                     $el.parents('body').append("<div id='mask' class='close_modal'></div>");
                 }
                 if($el.parents('body').find('#modal_window').length == 0){
                     $el.parents('body').append("<div id='modal_window' class='modal_window'></div>");
                 }
                 $el.find(".pull-out").click(function() {
                     // console.log("pull-out click");
                     var $e = $(this).parent();
                     $('#mask').css({
                         'display': 'inline',
                         opacity: 0
                     });
                     //fade in the mask to opacity 0.8 
                     $('#mask').fadeTo(500, 0.8);
                     $('#mask').height(document.body.availHeight);
                     $('#mask').width(document.body.availWidth);
                     //show the modal window
                     $('#modal_window').html('');
                     
                     if($('#searchDiv').length==0){
                         $('#modal_window').before('<div id="searchDiv" style="text-align:center;" >'
                                 +'<input value="" style="" type="text" id="searchBar" />'
                                 +'<span style="">(输入关键词进行检索)</span>'
                                 +'<span style="" id="windowClose">[关闭]</span></div>');
                     }else{
                         $('#searchDiv').show();
                     }
                     //alert($('#modal_window').css('top')+ " - - - - " +$('#modal_window').height());
                     //$('#searchDiv').css('top', ($('#modal_window').css('top')-$('#modal_window').height()));
                     $('#modal_window').append('<ul id="'+$e.attr("id")+'_modal'+'" class="pack-list"></ul>');
                     if(datahtml){
                         $('#modal_window ul#'+$e.attr("id")+'_modal').html(datahtml);
                    }else{
                        datahtml = $el.find('#'+$el.attr('id')+'_ul').html();
                        $el.data('datahtml', datahtml);
                        $('#modal_window ul#'+$e.attr("id")+'_modal').html(datahtml);
                    }
                         //输出内容
                         //$('#modal_window .pack-list').html($e.html());
                    $('#modal_window .pack-list li:lt(2)').hide();
                    $('#modal_window .pack-list li.pull-out').hide();
                    $('#modal_window li.move2topSel').remove();
                     //    $('#modal_window .pack-list li:gt(1)').each(function(index){
                       //      $(this).data('index', (index+2));
                        // })
                         //点击选中事件
                     $('#modal_window .pack-list li a').each(function(){
                         $(this).bind("click", function(){
                             var indes = $(this).text();
                             var id = $(this).parents('ul').attr("id").replace(/_modal/g, '');
                             $("#"+id).find("li:gt(1)").each(function(){
                                // console.log($(this).find('a').text() +" = = = "+ indes );
                                 if($(this).find('a').text() == indes ){
                                     // console.log($(this).find('a'));
                                     $(this).find('a')[0].click();
                                     $('.close_modal')[0].click();
                                     return false;
                                 }
                             })
                             if($(this).parent().hasClass('active')){
                                $('.close_modal')[0].click();
                             }
                         })
                     })
                     //if($('#searchDiv').length==0){
                         $('#searchBar').keyup(
                            function() {
                                $("#modal_window .pack-list li:gt(1)").not('.pull-out').hide().filter(
                                        ":contains('" + ($(this).val()) + "')").show();
                            }).keyup();
                         $('#windowClose').click(function(){
                             $(".close_modal")[0].click();
                         })
                     //}
                     if(options.maxsize !=0 && parseInt(options.maxsize)>0){
                         $('#modal_window ul.pack-list li:gt('+(3+options.maxsize)+')').not('.pull-out').show();
                     }
                     //$('#modal_window .pack-list li:lt('+(2+options.maxsize)+')').hide();
                     if($('#modal_window').outerHeight() >= $('body').height()*0.6 ){
                         $('#modal_window').css("overflow-y","scroll");
                         $('#modal_window').height($('body').height()*0.75);
                     }
                     var left = ($(window).width() - $('#modal_window').outerWidth()) / 2;
                     var top = ($(window).height() - $('#modal_window').outerHeight()) / 2;
                     //$('#searchDiv').css('top', ($('#modal_window').css('top').replace('px', '')-$('#modal_window').height()));
                     top = top+20;
                     if(top<=0){
                         top = 25;
                     }
                     $('#modal_window').css({
                         "top" : top,
                         "left" : left
                     });
                     
                     $('#searchDiv').css('top', (top - $('#searchDiv').height()-10));
                     $('#searchDiv').css('left', left);
                     $('#windowClose').css('left', $('#modal_window').width()-$('#windowClose').width()-5).bind('mouseover', function(){
                        $(this).css('color', "#fff").bind('mouseout', function(){$(this).css('color', '#ddd')})
                     });
                     $('#modal_window').fadeIn("fast");
                 });
                 $el.height(32);
                 $(".close_modal").click(function(){
                     $('#mask').fadeOut(500);
                     $('#modal_window').fadeOut(500);
                     $('#searchDiv').fadeOut(500);;
                 });
            }else{
                $el.find(".pull-right").toggle(function() {
                    var $e = $(this).parent();
                    var top1 = $e.offset().top;
                    var top2 = $e.find("li:last").offset().top;

                    var newHeight = top2 - top1 + 32;
                    $e.parent().animate({
                        height: newHeight
                    }, 500).addClass("pack-down");
                        var oldSrc = $e.find("img").attr("src");
                        var newSrc = oldSrc.replace(/frame_down/g, "frame_up");
                        $e.find("img").attr("src", newSrc);
                }, function() {
                    var $e = $(this).parent();
                    if ($e.parent().hasClass("pack-down")) {
                        $e.parent().removeClass("pack-down").animate({
                            height: 32
                        }, 500);
                       var oldSrc = $e.find("img").attr("src");
                       var newSrc = oldSrc.replace(/frame_up/g, "frame_down");
                       $e.find("img").attr("src", newSrc);
                    }
                });
            }
            //options.checkValue = '3d475c180b3e45129c57c8b330c3d364';
            //alert(options.date);

            if(options.checkValue && options.checkValue != '' && options.checkValue != '点击选择日期'){
                $el.find('li.active').removeClass('active');
                //alert(options.isGroup + ' = = ' + options.checkValue);
                if(options.isGroup){
                    var sa = options.checkValue.split(',');
                    var ctitle = '';
                    var dataN = options.data;
                    for(var ii=0; ii<dataN.length; ii++){
                        var tmpdata = dataN[ii].data;
                        for(var jj = 0; jj<tmpdata.length; jj++){
                            if(tmpdata[jj].key == sa[sa.length - 1] ){
                                ctitle = tmpdata[jj].title;
                                break;
                            }
                        }
                    }
                    if($el.find('#'+sa[0]).text() != ctitle ){
                        $el.find('#'+sa[0]).parent().addClass('active');
                    }
                    if(ctitle != ''){
                        $el.find('#'+$el.attr('id')+'_ul li.groupSel').addClass('inner_active').html("<a class='inner_a' href='javascript:;'>"+ctitle+"</a>"); 
                    }
                }else if(options.pop || options.move2top){
                    var ctitle = '';
                    var dataN = options.data;
                    for(var ii=0; ii<dataN.length; ii++){
                        if(dataN[ii].key == options.checkValue){
                            ctitle = dataN[ii].title;
                            break;
                        }
                    }
                    $el.find('li.move2topSel').addClass('inner_active').html("<a class='inner_a' href='javascript:;'>"+ctitle+"</a>");
                    $el.find('a#'+options.checkValue).parent().hide();
                }else{
                    $el.find('#'+options.checkValue).parent().addClass('active');
                }
            }
            if($el.find('li.move2topSel').text() == ''){
                $el.find('li.move2topSel').hide();
            }
            hook('onInit');
        }

        function dateInit(){
            //console.log( $el.attr('id') + " date init()");
            $el.addClass('pack-up');
            $el.append("<ul id='"+$el.attr('id')+"_ul' class='pack-list'></ul>");
            $el.find('#'+$el.attr('id')+'_ul').append("<li id="+options.title.key+" class='disabled'><a>"+options.title.name+"</a></li>");
            $el.find('#'+$el.attr('id')+'_ul').append("<li class='active'><a href='#'>"+ "\u5168\u90E8"+"</a></li>");
            $el.find('#'+$el.attr('id')+'_ul').append('<li><div id="'+$el.attr('id')+'_daterange"  style="background: #fff; border: 1px solid #ccc"><input class="choose_date" name="select-key:'+options.title.key+'" id="'+$el.attr('id')+'_queryDate" readonly="true" value="点击选择日期" style="width:280px;"  /></div></li>');
            var hourAndMin = options.dateTime?true:false;
            $("#"+$el.attr('id')+"_queryDate").daterangepicker({arrows:true,showTime:hourAndMin,onClose:function(){
            	var sval = $("#"+$el.attr('id')+"_queryDate").val(); 
            	
                if(sval!='点击选择日期'){
                    $('#'+$el.attr('id')+'_ul').find('li:eq(1)').removeClass("active");
                    if(sval.indexOf("至") > -1){
                        var tmpdata = sval.split(" 至 ");
                        if(tmpdata[0] > tmpdata[1] ){
                            alert("请选择合适的日期范围.");
                            return;
                        }
                    }
                    setFormFieldValue("select-key:created_time",sval);
                }
                
                changeDate(sval);
            }});
            $('#'+$el.attr('id')+'_ul').find('li:eq(1)').bind('click', function(){
                $("#"+$el.attr('id')+"_queryDate").val('点击选择日期');
                changeDate('date_all');
                $(this).addClass("active");
            })
            //alert(options.checkValue);
            if(options.checkValue && options.checkValue != '' && options.checkValue != '点击选择日期'){
                $el.find('li.active').removeClass('active');
                $('#'+$el.attr('id')+'_daterange input.choose_date').val(options.checkValue);
                /*
                var rp = $("#"+$el.attr('id')+"_queryDate");
                if(options.checkValue.indexOf('至')){
                    var nowSelDate = options.checkValue.split(" 至 ");
                    var da1 = new Date(nowSelDate[0]);
                    var da2 = new Date(nowSelDate[1]);
                    rp.find('.range-start').datepicker('setDate', da1);
                    rp.find('.range-end').datepicker('setDate', da2 );
                    alert(rp.daterangepicker('getDate'));
                }else{
                    alert(options.checkValue + '3332232234');
                    var da1 = new Date(options.checkValue);
                    rp.find('.range-end').datepicker('setDate', da1 );
                    rp.find('.range-start').datepicker('setDate', da1);
                }*/
            }
            /*$('#'+$el.attr('id')+'_daterange').parent().prev().click(function(){
                alert("x");
                $("#"+$el.attr('id')+"_queryDate").val('点击选择日期');
            })*/
        }
        
        /*function dateTimeInit(){
        	
            //console.log( $el.attr('id') + " date init()");
            $el.addClass('pack-up');
            $el.append("<ul id='"+$el.attr('id')+"_ul' class='pack-list'></ul>");
            $el.find('#'+$el.attr('id')+'_ul').append("<li id="+options.title.key+" class='disabled'><a>"+options.title.name+"</a></li>");
            $el.find('#'+$el.attr('id')+'_ul').append("<li class='active'><a href='#'>"+ "\u5168\u90E8"+"</a></li>");
            $el.find('#'+$el.attr('id')+'_ul').append('<li><div id="'+$el.attr('id')+'_daterange"  style="background: #fff; border: 1px solid #ccc"><input class="choose_date" name="select-key:'+options.title.key+'" id="'+$el.attr('id')+'_queryDate" readonly="true" value="点击选择日期" size="22" /></div></li>');
            
            $("#"+$el.attr('id')+"_queryDate").datetimepicker({onClose:function(){
                var sval = $("#"+$el.attr('id')+"_queryDate").val(); 
                if(sval!='点击选择日期'){
                    $('#'+$el.attr('id')+'_ul').find('li:eq(1)').removeClass("active");
                    if(sval.indexOf("至") > -1){
                        var tmpdata = sval.split(" 至 ");
                        if(tmpdata[0] > tmpdata[1] ){
                            alert("请选择合适的日期范围.");
                            return;
                        }
                    }
                }
                changeDate(sval);
            }});
            $('#'+$el.attr('id')+'_ul').find('li:eq(1)').bind('click', function(){
                $("#"+$el.attr('id')+"_queryDate").val('点击选择日期');
                changeDate('date_all');
                $(this).addClass("active");
            })
            //alert(options.checkValue);
            if(options.checkValue && options.checkValue != '' && options.checkValue != '点击选择日期'){
                $el.find('li.active').removeClass('active');
                $('#'+$el.attr('id')+'_daterange input.choose_date').val(options.checkValue);
                
                var rp = $("#"+$el.attr('id')+"_queryDate");
                if(options.checkValue.indexOf('至')){
                    var nowSelDate = options.checkValue.split(" 至 ");
                    var da1 = new Date(nowSelDate[0]);
                    var da2 = new Date(nowSelDate[1]);
                    rp.find('.range-start').datepicker('setDate', da1);
                    rp.find('.range-end').datepicker('setDate', da2 );
                    alert(rp.daterangepicker('getDate'));
                }else{
                    alert(options.checkValue + '3332232234');
                    var da1 = new Date(options.checkValue);
                    rp.find('.range-end').datepicker('setDate', da1 );
                    rp.find('.range-start').datepicker('setDate', da1);
                }
            }
            $('#'+$el.attr('id')+'_daterange').parent().prev().click(function(){
                alert("x");
                $("#"+$el.attr('id')+"_queryDate").val('点击选择日期');
            })
        }
*/
        function pinyinInit(){
            options.pop = true;
            //console.log( $el.attr('id') + " date init()");
            var keys = options.title.key; //对应数据库的字段名称
            //开始构造显示容器
            $el.addClass("pack-up");
            $el.append("<ul id='"+$el.attr('id')+"_ul' class='pack-list'></ul>");
            //列表的标题 id为对应字段的名字，方便构造表单查询项
            $el.find('#'+$el.attr('id')+'_ul').append("<li id="+options.title.key+" class='disabled'><a>"+options.title.name+"</a></li>");

            //全部选项，用来清空查询条件
            $el.find('#'+$el.attr('id')+'_ul').append("<li class='active'><a href='#' >"+ "\u5168\u90E8"+"</a></li>");
            //触发显示更多数据项的图标
            $el.find('#'+$el.attr('id')+'_ul').append("<li class='pull-out'><a title='点击展开' href='#'><img  class='btn btn_more' src='css/images/frame_plus.png' /></a></li>");
                //添加数据
                //将选中数据项提前//
                //如果是按拼音分组显示，则第一行显示热门数据项
                //实际上，对于页面显示来说，因为高度只有一行，所以其他情况是所有数据都被显示只是能看到第一行
                //所以，数据项有序是非常有必要的
                //这个插件现在看来在设计上有问题{Q}
            var tmp = options.data[0]['hot'];
            if(options.move2top){
                $el.find('#'+$el.attr('id')+'_ul').append("<li class='move2topSel'></li>");
                $el.find('.move2topSel').next().css("margin-left", "0");
            }
            for(var ii=0; ii<tmp.length; ii++){
                var st = tmp[ii];
                $el.find('#'+$el.attr('id')+'_ul').append("<li><a id="+st.key+" href='javascript:;' >"+st.title+" <span>"+(st.amount ? st.amount : "")+"</span></a></li>");
                $el.find('#'+$el.attr('id')+'_ul').find("li:last").data("index", ii+3);
            }
            if (options.maxsize != 0 && parseInt(options.maxsize) > 0) {
                $el.find('#' + $el.attr('id') + '_ul li:gt(' + (2 + options.maxsize) + ')').hide();
            }
            $el.find('.move2topSel').next().css("margin-left", "0");
            //alert(options.checkValue);
            if (options.checkValue && options.checkValue != '' && options.checkValue != '点击选择日期') {
                var lastSel = $el.find('#' + options.checkValue);
                var ls = options.checkValue;
                if (lastSel.length == 0) {
                    for (x in options.data[0]) {
                        if (x != 'firstLine') {
                            var tmp = options.data[0][x];
                            for (var ii = 0; ii < tmp.length; ii++) {
                                if (ls == tmp[ii].key) {
                                    $el.find('.move2topSel').html('<a id="' + ls + '" class="inner_a" href="javascript:;">' + tmp[ii].title + '</a>');
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            //绑定提交查询事件
            $el.find("li:gt(0)").not('li:eq(1)').each(function() {
                $(this).find('a').bind('click', function() {
                    var oldItem = $(this).parent().parent().find("li.active");
                    var nowItem = $(this).parent();
                    oldItem.removeClass('active');
                    nowItem.addClass('active');
                    if (options.move2top) {
                        if (nowItem.data('index') == oldItem.data('index'))
                            return;
                        if (oldItem.data('index')) {
                            oldItem.insertAfter($el.find('#' + $el.attr('id') + '_ul li:eq(' + oldItem.data('index') + ')'));
                        }
                        if (nowItem.data('index')) {
                            $(this).parent().parent().find(".move2topSel").html(nowItem.html());
                        }
                        if ($(this).parent().parent().find("li.active").css("display") == 'none') {
                            $(this).parent().parent().find("li.active").css("display", "");
                        }
                    }
                    $(this).parent().parent().parent().queryform('getSelectedNode');
                })
            })
            $el.find('#' + $el.attr('id') + '_ul li:eq(1) a').bind("click", function() {
                $el.find("li.active").removeClass('active');
                $(this).parent().addClass('active');
                $(this).parent().parent().parent().queryform('getSelectedNode');
            })
            if (options.checkValue) {
                var lastSel = $el.find('#' + options.checkValue);
                $el.find('.move2topSel').addClass('active').html("<a class='inner_a' href='javascript:;'>" + lastSel.text() + "</a>");
                $el.find('li:eq(1)').removeClass("active");
                lastSel.remove();
            }
            if ($el.parents('body').find('#mask').length == 0) {
                    $el.parents('body').append("<div id='mask' class='close_modal'></div>");
            }
            if ($el.parents('body').find('#modal_window').length == 0) {
                $el.parents('body').append("<div id='modal_window' class='modal_window'></div>");
            }
            $el.find('.pull-out').bind('click', function() {
                var datahtml = options.data[0]['data'];
                var $e = $(this).parent();
                $('#mask').css({
                    'display': 'inline',
                    opacity: 0
                });
                //fade in the mask to opacity 0.8 
                $('#mask').fadeTo(500, 0.4);
                $('#mask').height(document.body.availHeight);
                $('#mask').width(document.body.availWidth);
                //show the modal window
                $('#modal_window').html('');

                if ($('#searchDiv').length == 0) {
                    $('#modal_window').before('<div id="searchDiv" class="albar" style="text-align:center; width: 400px;" >'
                        //+ '<input value="" style="" type="text" id="searchBar" />' + '<span style="">(输入关键词进行检索)</span>' + '<span style="" id="windowClose">[关闭]</span>'+
                        + '<ul class="letter-list"><li class="on"><a href="#A">A-E</a></li><li><a href="#F">F-J</a></li><li><a href="#K">K-O</a></a></li><li><a href="#P">P-T</a></li><li><a href="#U">U-Z</a></li> <li><a href="#other">其他</a></li> </ul><div id="windowClose">[关闭]</div></div>');
                } else {
                    $('#searchDiv').show();
                }
                //alert($('#modal_window').css('top')+ " - - - - " +$('#modal_window').height());
                //$('#searchDiv').css('top', ($('#modal_window').css('top')-$('#modal_window').height()));
                $('#modal_window').html('');
                var datahtml = buildHtml(options.data[0]['data']);
                if (datahtml) {
                    $('#modal_window').html(datahtml);
                }
                $('#searchDiv a').bind('click', function() {
                    $(this).parent().addClass('on');
                    $(this).parent().siblings().removeClass('on');
                })
                $('#modal_window .item').bind('click', function() {
                    //$(this).parent().addClass('selected');
                    //$(this).parent().siblings().removeClass('selected');
                    var id = $(this).attr('id').replace('_', "");
                    var mc = $(this).text();
                    $el.find('.move2topSel').html('<a id="' + id + '" class="inner_a" href="javascript:;">' + mc + '</a>');
                    $('#modal_window').data('name', mc).data('value', id);
                    $el.queryform('getSelectedNode');
                }).bind('mouseover', function(){
                    $(this).addClass('item-hover');
                }).bind('mouseout', function(){
                    $(this).removeClass('item-hover');
                })
                //点击选中事件
                $('#modal_window .pack-list li a').each(function() {
                    $(this).bind("click", function() {
                        var indes = $(this).text();
                        var id = $(this).parents('ul').attr("id").replace(/_modal/g, '');
                        $("#" + id).find("li:gt(1)").each(function() {
                            // console.log($(this).find('a').text() +" = = = "+ indes );
                            if ($(this).find('a').text() == indes) {
                                // console.log($(this).find('a'));
                                $(this).find('a')[0].click();
                                $('.close_modal')[0].click();
                                return false;
                            }
                        })
                        if ($(this).parent().hasClass('active')) {
                            $('.close_modal')[0].click();
                        }
                    })
                })
                //if($('#searchDiv').length==0){
                $('#searchBar').keyup(
                    function() {
                        $("#modal_window .pack-list li:gt(1)").not('.pull-out').hide().filter(
                            ":contains('" + ($(this).val()) + "')").show();
                    }).keyup();
                $('#windowClose').click(function() {
                    $(".close_modal")[0].click();
                })
                //}

                //$('#modal_window .pack-list li:lt('+(2+options.maxsize)+')').hide();
                if ($('#modal_window').outerHeight() >= $('body').height() * 0.6) {
                    $('#modal_window').css("overflow-y", "hidden");
                    $('#modal_window').height($('body').height() * 0.75);
                }
                var left = ($(window).width() - $('#modal_window').outerWidth()) / 2;
                var top = ($(window).height() - $('#modal_window').outerHeight()) / 2;
                //$('#searchDiv').css('top', ($('#modal_window').css('top').replace('px', '')-$('#modal_window').height()));
                top = top + 20;
                if (top <= 0) {
                    top = 25;
                };
                $('#modal_window').css({
                    "top": top,
                    "left": left
                });

                $('#searchDiv').css('top', (top - $('#searchDiv').height() - 10));
                $('#searchDiv').css('left', left);
                $('#windowClose').css('left', $('#modal_window').width() - $('#windowClose').width() - 5);
                $('#modal_window').fadeIn("fast");
                $el.height(32);
                $(".close_modal").click(function() {
                    $('#mask').fadeOut(500);
                    $('#modal_window').fadeOut(500);
                    $('#searchDiv').fadeOut(500);;
                });
            })            
        }

    /**
     * Get/set a plugin option.
     * Get usage: $('#el').demoplugin('option', 'key');
     * Set usage: $('#el').demoplugin('option', 'key', value);
     */
        function option(key, val) {
            if (val) {
                options[key] = val;
            } else {
                return options[key];
            }
        }

        /*function getSelectedNodes(){
            // console.log(" getSelectedNodes() " );
            var returnVal = new Array;
            $(".pack-list li.active").each(function(){
                if($(this).parent().parent().attr('id') == 'modal_window'){
                    return true;
                }
                var name = $(this).parent().find("li.disabled").attr("id");
                if($(this).find("input").length > 0){
                    var input = $(this).find('input');
                    if(input && input.val() != '点击选择日期' && input.val() != ''){
                        var date = input.val();
                        date = date.split(' - ');
                        returnVal.push({"name" : name, "value" : date});
                    }
                }
                if($(this).find('a').attr("href").indexOf('#')==-1){
                    var value = $(this).find("a[href!='#']").attr("id") || '';
                    returnVal.push({"name": name, "value" : value});
                }
            })
            hook('onClick', returnVal);
        }*/
        function getSelectedNode(){
            var returnVal = new Array;
            var outer_item = $el.find("li.active");
            var name = outer_item.parent().find("li.disabled").attr("id");
            var value = outer_item.find("a[href!='#']").attr("id") || '';
            returnVal.push({"name": name, "value" : value});

            if(options.isGroup && !options.isPinYinOrder){
                //$oli = $el.find("li.inner_active a");
                $oli = $('div.tmp_layer a.active');
                value = $oli.attr("id");
                var keys = options.title.key;
                keys = keys.split(',');
                //alert(keys + '   ' + value);
                //keys = keys.split(",");
                if(typeof(value) != 'undefined'){
                    returnVal.push({"name": keys[1], "value": value});
                }else{
                    returnVal.push({"name": keys[1], "value": ""});
                }
                
            }
            if(options.isPinYinOrder){
                value = $('#modal_window').data('value');
                if(value){
                    for(var ii=0; ii<returnVal.length; ii++){
                        var tmp = returnVal[ii].name;
                        if(tmp == name){
                            returnVal.splice(ii, 1);
                        }
                    }
                    returnVal.push({"name": name, "value" : value});
                }
            }
            if(returnVal){
                if(!options.isGroup){
                  $('#'+$el.attr("id")+"_choose").val(returnVal[0].value);
                  document.getElementById('form_choose').submit();
                }else{
                  if(returnVal.length>1){
                      for(var i=0;i<returnVal.length;i++){
                          $('#'+$el.attr("id")+"_"+returnVal[i].name).val(returnVal[i].value);
                      }
                      document.getElementById('form_choose').submit();
                  }
                }
              }
        }

    /**
     * Destroy plugin.
     * Usage: $('#el').demoplugin('destroy');
     */
        function destroy() {
            // Iterate over each matching element.
            $el.each(function() {
                var el = this;
                var $el = $(this);
                // Add code to restore the element to its original state...
                hook('onDestroy');
                // Remove Plugin instance from the element.
                $el.removeData('plugin_' + pluginName);
                var id = $el.attr("id");
                $('#' + id + '_ul').remove();
                $el.removeClass();
/*                if($el.option("pop")){
                 if($el.parents('body').find('#mask').remov()){
                     $el.parents('body').append("<div id='mask' class='close_modal'></div>");
                 }
                 if($el.parents('body').find('#modal_window').length == 0){
                     $el.parents('body').append("<div id='modal_window' class='modal_window'></div>");
                 }
          }
 */           });
        }


    /**
     * Callback hooks.
     * Usage: In the defaults object specify a callback function:
     * hookName: function() {}
     * Then somewhere in the plugin trigger the callback:
     * hook('hookName');
     */
        function hook(hookName, arg) {
            if (options[hookName] !== undefined && arg !== undefined) {
                // Call the user defined function.
                // Scope is set to the jQuery element we are operating on.
                options[hookName].apply(el, arg);
            }else if(options[hookName] != undefined){
                options[hookName].call(el);
            }
        }

        // Initialize the plugin instance.
        if(options.date == true || options.dateTime == true){
            dateInit();
        }/*else if(options.dateTime == true){
            dateTimeInit();
        }*/else if(options.isPinYinOrder == true){
        	init();
        	//pinyinInit();
        }else{
            init();   
        }
        
        // Expose methods of Plugin we wish to be public.
        return {
            option: option,
            destroy: destroy,
           // getSelectedNodes: getSelectedNodes,
            getSelectedNode: getSelectedNode
        };
    }

    //1 构造指定字段的拼音数据

    function buildPinYin(source) {
        var td = source;
        var len = td.length;
        for(var i=0, value; value=td[i++]; ){
            var col = value.title;
            var py = pinyin.getCamelChars(col);
            var fy = py.substr(0, 1);
            var pyin = pinyin.getFullChars(col);
            value.py = py;
            value.fy = fy;
            value.pinyin = pyin;
        }

        var keys = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'OTHER'];
        //var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        var data = {}; //用来存放已经有序的数据，按照字母表顺序A-->Z
        //初始化过程
        for (var i = 0; i < keys.length; i++) {
            data[keys[i]] = [];
        }
        for (var i = 0, value; value = source[i++];) {
            //console.log(n+" -- "+value.py + ' = ' + value.fy + ' - - ' + value.pinyin);
            var index = value.py.toUpperCase().charCodeAt() - 65;
            if (index > -1 && index < 26) {
                data[keys[index]].push(value);
            } else {
                data['OTHER'].push(value);
            }
        }
        return data;
    }

    //2 构造html 按拼音划分
    function buildHtml(source) {
        var data = buildPinYin(source);
        var html = '<div class="cont">';
        for (var at = 65; at < 91; at++) {
            var tmp = data[String.fromCharCode(at)];
            var len = tmp.length;
            if (len > 0) {
                html += '<div class="cont-area" id="'+String.fromCharCode(at)+'">' + String.fromCharCode(at) + '</div><div>';
                for (var j = 0; j < len; j++) {
                    var td = tmp[j];
                    html += '<div id="_'+td.key+'" py="'+td.py+'" fy="'+td.fy+'" pinyin="'+td.pinyin
                    +'" class="item">' + td.title + '<span class="tnum">'+(td.amount ? '('+td.amount+')' : "")+'</span></div>';
                }
                html += "</div>";
            }else{
                html += '<div class="nodata" id="'+String.fromCharCode(at)+'">' + String.fromCharCode(at) + '</div>';
            }
            //delete data[String.fromCharCode(i)];
        }
        var tmp = data['OTHER'];
        var len = tmp.length;
        if (len > 0) {
            html += '<div class="cont-area" id="other">其他</div><div>';
            for (var j = 0; j < len; j++) {
                var td = tmp[j];
                html += '<div id="_'+td.key+'" py="' + td.py + '" fy="' + td.fy + '" pinyin="' + td.pinyin + '" class="item">' + td.title + '</div>';
            }
            html += "</div>";
        } else {
            html += '<div class="nodata" id="other">其他</div>';
        }
        html += '</div>';
        return html;
    }

  /**
   * Plugin definition.
   */
    $.fn[pluginName] = function(options) {
        // If the first parameter is a string, treat this as a call to
        // a public method.
        if (typeof arguments[0] === 'string') {
            var methodName = arguments[0];
            var args = Array.prototype.slice.call(arguments, 1);
            var returnVal;
            this.each(function() {
                // Check that the element has a plugin instance, and that
                // the requested public method exists.
                if ($.data(this, 'plugin_' + pluginName) && typeof $.data(this, 'plugin_' + pluginName)[methodName] === 'function') {
                    // Call the method of the Plugin instance, and Pass it
                    // the supplied arguments.
                    returnVal = $.data(this, 'plugin_' + pluginName)[methodName].apply(this, args);
                } else {
                    throw new Error('Method ' + methodName + ' does not exist on jQuery.' + pluginName);
                }
            });
            if (returnVal !== undefined) {
                // If the method returned a value, return the value.
                return returnVal;
            } else {
                // Otherwise, returning 'this' preserves chainability.
                return this;
            }
            // If the first parameter is an object (options), or was omitted,
            // instantiate a new instance of the plugin.

        } else if (typeof options === "object" || !options) {
            return this.each(function() {
                // Only allow the plugin to be instantiated once.
                if (!$.data(this, 'plugin_' + pluginName)) {
                    // Pass options to Plugin constructor, and store Plugin
                    // instance in the elements jQuery data object.
                    $.data(this, 'plugin_' + pluginName, new Plugin(this, options));
                }
            });
        }
    };

    // Default plugin options.
    // Options can be overwritten when initializing plugin, by
    // passing an object literal, or after initialization:
    // $('#el').demoplugin('option', 'key', value);
    $.fn[pluginName].defaults = {
        onInit: function() {},
        onDestroy: function() {},
        onClick:function(){},
        data: [],
        date: false,   //时候是日期类型条件
        dateTime: false,   //时候是日期时间类型条件
        pop: false,    //时候使用弹出框显示
        maxsize: 0,    //显示的最大条目数
        move2top: true,    //是否需要把当前选中放在第一位显示
        isGroup: false,  //数据量特别多时，是否需要分组显示数据（这样的[[],[],[]]结构）
        hot: [],  //如果是按拼音分组显示，则在第一行显示的内容； 也可以理解为热门数据
        isPinYinOrder: false   //数据量比较多时，是否需要按照拼音序分组展示； 目前需要组织好数据结构{"a":[],"b":[],...}
    };

})(jQuery);