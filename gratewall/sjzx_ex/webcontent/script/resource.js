<!--

// ȡ��Ŀ¼
function getRootPath()
{
	// ȡjs��·��
	var scripts = document.getElementsByTagName("script");
	var src = "/script/resource.js";
	for( var i = 0; i < scripts.length; i++ ){
		if (scripts[i].src.match(src)) {
			base = scripts[i].src.replace( src, "" );
			return base;
		}
	}
	
	var href = location.href;
	var	ptr = href.indexOf('?');
	if( ptr > 0 ){
		href = href.substring(0, ptr);
	}
	
	href = href.substring(0, href.lastIndexOf("/") +1)
	
	// delete http://host:port/
	ptr = href.indexOf('//');
	if( ptr > 0 ){
		ptr = href.indexOf( '/', ptr+2 );
		if( ptr > 0 ){
			href = href.substring( ptr );
		}
	}
	
	// ����Ŀ¼
	ptr = href.indexOf('/', 1 );
	if( ptr > 0 ){
		href = href.substring( 0, ptr );
	}
	
	return href;
}


// ��Ŀ¼��ԭ����HTMLҳ����
var rootPath = getRootPath();


// Ԥ����ͼƬ
var _browseImage = new Image();
_browseImage.src = rootPath + "/script/browsebox/search_results_view.gif";

// �����ļ�
function includeScript( xurl )
{
	/* if( xurl.indexOf('/') == 0 ){
		xurl = rootPath + xurl;
	}
	else{
		// ȡjs��·��
		var base = rootPath + '/script/';
		
		// ȡjs��·��
		var scripts = document.getElementsByTagName("script");
		if( scripts != null ){
			var src = "resource.js";
			for( var i = 0; i < scripts.length; i++ ){
				if (scripts[i].src.match(src)) {
					base = scripts[i].src.replace( src, "" );
					break;
				}
			}
		}
		
		xurl = base + xurl;
	} */
	
	// ����ҳ��
	/* var newScript = document.createElement('script');
	newScript.src = xurl;
	newScript.type = "text/javascript";
	var head = document.all.tags('head')[0];
	head.appendChild( newScript ); */
	
	document.write('<script language="JavaScript" src="' + rootPath + '/script/' + xurl + '"></script>\n' );
}

var d1 = new Date();
var tmpMi = d1.getTime();
includeScript( "gwssi-validate.js?v="+tmpMi );
includeScript( "public-func.js?v="+tmpMi );
includeScript( "gwssi-page.js?v="+tmpMi );
includeScript( "gwssi-select.js?v="+tmpMi );
includeScript( "gwssi-grid.js?v="+tmpMi );
includeScript( "gwssi-form.js?v="+tmpMi );
includeScript( "gwssi-xfield.js?v="+tmpMi );
includeScript( "gwssi-browse.js?v="+tmpMi );
includeScript( "calendar.js?v="+tmpMi );
//includeScript( "lib/unitpngfix.js" );
//csdb ��Ŀ��� liqiang 20070706
includeScript( "checkForm.js?v="+tmpMi );
includeScript( "selectTree.js?v="+tmpMi );
//bjaic 20081020 ���
includeScript( "queryFieldChecked.js?v="+tmpMi );
//bjaic 20081023 WeiQiang ��� �Զ����ع�����
includeScript( "component/pageScroll.js?v="+tmpMi );
//bjaic 20081121 WeiQiang ����������Զ��������
includeScript( "component/selectTitlePlugin.js?v="+tmpMi );
-->
