// winΪ��ǰ���ڶ���(self)��pDomΪ��ǰFrame��DOM����(this)
//function resizeFrame(win, pDom){
//    var bodyDiv = pDom.contentWindow.document.getElementById('body-div');
//    if (!bodyDiv){
//        return;
//    }
//    // alert(pDom.contentWindow.document.body.outerHTML);
//    // �ж�������û�а��հٷֱ����ø߶ȵ�IFRAME
//    var belowIframe = pDom.contentWindow.document.getElementsByTagName("IFRAME");
//    if (belowIframe.length > 0){
//    	for (var tempIndex = 0; tempIndex < belowIframe.length; tempIndex++){
//    		// ���û������autoHeight���Ҹ߶��к��аٷֺ�
//    		if ( belowIframe[tempIndex].autoHeight != "1" ){
//    			if ( (belowIframe[tempIndex].height && belowIframe[tempIndex].height.toString().indexOf("%") > -1) 
//    				|| (belowIframe[tempIndex].style.height && belowIframe[tempIndex].style.height.toString().indexOf("%") > -1) ){
//    				// alert("IFRAME�ĸ߶����ò��ܺ��аٷֺţ����޸ģ�");
//    				pDom.style.height = "500px";
//    				return;
//    			}
//    		}
//    	}
//    }
//    
//    var currHeight = bodyDiv.scrollHeight;
//    // bodyDiv.style.overflowY = 'hidden';
//    pDom.style.height = currHeight;
//    if (win == top){
//        return;
//    }else{
//        var parentDom = win.frameElement;
//        // ����Ψһ��ʾ������
//        if (parentDom && parentDom.autoHeight == '1'){
//            resizeFrame(win.parent, parentDom);
//        }
//    }
//}

// ��ȡԪ�صľ�������(������position��λʹ��)
// ������position=absolute����relative��ʱ��ֹͣ
function getElementPos(obj){
	var parent = obj;
	var left = parent.offsetLeft;
	var top = parent.offsetTop;
	while (parent = parent.offsetParent){
		// alert(parent.tagName);
		if (parent.tagName.toUpperCase() == "BODY" || parent.style.position.toLowerCase() == "absolute" || parent.style.position.toLowerCase() == "relative"){
			break;
		}else{
			left += parent.offsetLeft;
			top  += parent.offsetTop;
		}
	}
	return {x: left, y: top};
}

/**
 * ����ĳ��Ϊѡ��
 */
function setCurrentRowChecked(gridName, index){
	index = index ? index : 0;
	var t = event.srcElement;
	while(t = t.parentNode){
		var tn = t.tagName.toUpperCase();
		if (tn == "TR" || tn == "BODY"){
			var	grid = getGridDefine( gridName, index );
			if( grid == null ) return;
			// û��chekcbox
			if( grid.checkflag == "" ) return;
			
			// checkbox
			var flagBoxs = grid.getFlagBoxs();
			
			for (var i = 0; i < flagBoxs.length; i++){
				flagBoxs[i].checked = false;
			}
			var rid = t.id.substring(4);
			
			var func = "selectRow('" + gridName + "', " + index + ", ";
			var selectFunction = new Function(func + rid + ", true)");
			selectFunction();
			
			break;
		}
	}
	t = null;
}

function getGridValueOnClickRow(gridName, index, columnName){
	if (!event){
		return;
	}	
	var grid = getGridDefine(gridName, index);
	
	if (!grid){
		return;
	}
	
	var t = event.srcElement;
	while(t = t.parentNode){
		var tn = t.tagName.toUpperCase();
		if (tn == "TR" || tn == "BODY"){
			var rid = parseInt(t.id.substring(4));
			// alert((rid + 1) + ":" + grid.getCellValue(rid + 1, columnName));
			return grid.getCellValue(rid + 1, columnName);
			// break;
		}
	}
	
	t = null;
}

function testResizeFrame(win){
//	alert("����");
	win =  win || window;
	
	if (win == top){
    	return;
    }
    
	var bodyDiv = win.document.getElementById('body-div');
    if (!bodyDiv){
        return;
    }
    
    var parentDom = win.frameElement;
//    alert(parentDom.outerHTML);
    if (!parentDom || !parentDom.tagName || parentDom.tagName.toUpperCase() != "IFRAME"){
    	return;
    }
    
    // ����ֶ�����Ϊ�����Զ��ı��С���򲻸ı��С�ˡ�
    if (parentDom.autoHeight == "0"){
    	return;
    }
    
    if (arguments[1] != null && arguments[1] != ""){
    	parentDom.style.height = arguments[1];
    	return;
    }
    
	parentDom.style.height = 0;
	// bodyDiv.style.overflowY = 'hidden';
    var currHeight = bodyDiv.scrollHeight;
    // �����ǰҳ����ں�������������20�ĸ߶�
    if ( bodyDiv.scrollWidth > bodyDiv.offsetWidth ){
    	currHeight += 20;
    }
    parentDom.style.height = currHeight;
    // bodyDiv.style.overflowY = 'auto';
    testResizeFrame( win.parent );
}