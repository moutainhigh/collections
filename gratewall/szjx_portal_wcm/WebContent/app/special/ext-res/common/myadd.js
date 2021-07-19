Ext.ns("wcm.tpqh");

(function(){
	
/* 带缩略图的切换 开始*/
	
		
	
	var currslid = 1;
	var slidint;
	
     var ncount;
	wcm.tpqh.setfoc =function(id,currWidgetInstId,nCount){

		var picarry =  {};
		var lnkarry =  {};
		var ttlarry =  {};
		
		ncount = nCount;
       for(var m=0;m<nCount;m++){
				var temp  = "ttd"+(m+1)+"_"+currWidgetInstId;
				if(!document.getElementById(temp)){return false;}
				var valuem = document.getElementById(temp).value;
				//debugger;
				var sValue = eval("("+valuem+")");
				picarry[m] = sValue.picarry;
				lnkarry[m] = sValue.linkarry;
				ttlarry[m] = sValue.ttlarry;			
		 }
		// ie7 下
		//document.getElementById("focpic_"+currWidgetInstId).sstyle.left = "auto";
		//document.getElementById("focpic_"+currWidgetInstId).sstyle.top = "auto";
		document.getElementById("focpic_"+currWidgetInstId).src = picarry[id-1];
		document.getElementById("foclnk_"+currWidgetInstId).href = lnkarry[id-1];
		document.getElementById("foctitle_"+currWidgetInstId).innerHTML = '<a href="'+lnkarry[id-1]+'" target="_blank">'+ttlarry[id-1]+'</a>';
		currslid = id;
		for(i=1;i<nCount+1;i++){
			document.getElementById("tmb"+i+"_"+currWidgetInstId).className = "thumb_off";
		};
		//debugger;
		document.getElementById("tmb"+id+"_"+currWidgetInstId).className ="thumb_on";
		var focpic = eval("focpic"+"_"+currWidgetInstId);
		focpic.style.visibility = "hidden";
		if(focpic.filters!=null)
			focpic.filters[0].Apply();
		if (focpic.style.visibility == "visible") {
			focpic.style.visibility = "hidden";
			if(focpic.filters!=null)
				focpic.filters.revealTrans.transition=23;
		}
		else {
			focpic.style.visibility = "visible";
			if(focpic.filters!=null)
				focpic.filters[0].transition=23;
		}
		if(focpic.filters!=null)
			focpic.filters[0].Play();
		stopit();
	}

	function playnext(currWidgetInstId){
		if(currslid==ncount){
			currslid = 1;
		}
		else{
			currslid++;
		};
		wcm.tpqh.setfoc(currslid,currWidgetInstId,ncount);
		wcm.tpqh.playit(currWidgetInstId,ncount);
	}
	wcm.tpqh.playit=function(currWidgetInstId,ncount){		
		slidint = setTimeout(function(){
			playnext(currWidgetInstId);
		}, 4500);
	}
	function stopit(){
		clearTimeout(slidint);
		}
	/* 带缩略图的切换 结束*/
DOM.ready(function(){
	  

		// 图片切换--带缩略图
		var tpqh = document.getElementsByClassName("trs-mytpqh2");
		var currWIID = document.getElementsByName("currWidgetInstId");
		for (var j = 0; j < tpqh.length; j++) {

			var currWidgetInstId = currWIID[j].value;			
			var textarea = document.getElementsByName("tpyzarea_"+currWidgetInstId);
			var nCount = textarea.length;			
			//debugger;	
			wcm.tpqh.setfoc(1,currWidgetInstId,nCount);
			wcm.tpqh.playit(currWidgetInstId,nCount);
		}

	})
})();


(function(){

	DOM.ready(function(){
	  

			// 显示统计
			var doms = document.getElementsByClassName("trs-javascript");
			var ta = document.getElementsByName("trs-javascript");
			
			for (var i = 0; i < doms.length; i++) {
				var value = ta[i].value;
				if(existURL(value) == "")				 
				 return;
				var datas = eval("("+value+")");
				var src = datas.Src;
				var areaType = datas.areaType;
				var currid = datas.currwidgetid;

				areaType = areaType==1?"地区":"部门";

				var docHead = document.getElementsByTagName("head")[0];
				var newScriptNode = document.createElement('script');
				newScriptNode.language="javascript";
				newScriptNode.id = "src_"+currid;
				newScriptNode.type="text/javascript";
				newScriptNode.setAttribute('commonresource', '1');

				newScriptNode.charset="utf-8";

				newScriptNode.src = src;
				
				//newScriptNode.onload=function(){
					setTimeout(function(){					
					  var link = window["mlink_"+currid] ;
					  var ret = window["json_"+currid];

					 var aHtml =new Array();
						aHtml.push('<TABLE border=0 cellSpacing=1 cellPadding=0  width=160 align=middle>'+
							'<TBODY>'+
							'<TR class=lq_ftlist align=middle bgColor=#006699>'+
								'<TD height=30 width=48><FONT   color=#ffffff>名次</FONT>&nbsp;</TD>'+
								'<TD height=30 width=64><FONT   color=#ffffff>'+areaType+'</FONT>&nbsp;</TD>'+
								'<TD height=30 width=48><FONT color=#ffffff>信息</FONT>&nbsp;</TD>'+
							'</TR></TBODY>');

					var dataType = false;
					if(datas.DataType=="year"){
						dataType = true;
					}
					var temp ;
					if(ret != null){
						for(var i= 0;i<ret.length;i++){
							if(dataType){
								temp = ret[i].year;
							}else{
								temp = ret[i].month;
							}

							aHtml.push( '<TR align=middle bgColor=#33ccff><TD height=20><P align=center><FONT color=#ffffff>'+ ret[i].rank+ '</FONT>&nbsp;</P></TD><TD height=20><FONT color=#ffffff> <P align=center>'+ret[i].name+'</P></FONT></TD> <TD height=20><P align=center><FONT  color=#ffffff>'+temp+ '</FONT>&nbsp;</P></TD></TR>');
						}
					}else{

						aHtml.push(' <TR align=middle bgColor=#33ccff><TD colspan="3">指定的栏目下没有相关的统计数据</TD></TR>');
					}
								
					  document.getElementById("contentid_"+currid).innerHTML = aHtml.join("");
					  document.getElementById("detaillink_"+currid).innerHTML = '<a href="'+link+'" target="_blank">[详细]</a>';

					},3000);

				docHead.appendChild(newScriptNode);
					 
			
		 }
		})	 
})() ;