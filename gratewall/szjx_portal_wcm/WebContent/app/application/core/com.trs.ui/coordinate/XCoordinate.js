Ext.ns('com.trs.ui');
/**
*坐标类的字段
*/
(function(){
	//private 
	var coordinateSelectBtn = "-select-btn";
	var coordinateText = "-text";
	var template = [
		'<div class="XCoordinate">',
			'<input type="hidden" name="{0}" id="{0}" value="{1}" />',
			'<div class="coordinate-select" id="{0}-select-btn"></div>',
			'<div class="coordinate-text" id="{0}-text">{2}</div>',
		'</div>'
	].join("");

	com.trs.ui.XCoordinate = Ext.extend(com.trs.ui.BaseComponent, {
		/**
		*name,value,desc,rootId,treeType
		*/
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];
			//TODO
			var sClassInfoText = (config['desc']||"无") + "[Lat/Lng:(" + (config['value']||0) + ")]";
			if(!config['desc']){
				config['value'] = "";
				sClassInfoText = "";	
			}
			return String.format(template, config['name'], config['value'], sClassInfoText);
		},
		getCoordinate : function(){
			var config = this.initConfig;
			var sName = config['name'];
			//获取所有的参数 TODO...
			var CValue = $(sName).value;
			var params = {selectedValue:CValue};
			var sTitle = "获取经纬度";
			var sUrl = "../../application/common/coordinate_include.html";
			/*wcm.CrashBoarder.get('getCoordinate').show({
				title : sTitle,
				src : sUrl,
				width: '700px',
				height: '500px',
				params : params,
				reloadable : true,
				maskable : true,
				callback : function(_args){
					
				}
			});*/
			var result = ShowDialog4wcm52Style(sUrl, 700, 500);
			if(!result)return;
			$(sName).value = result;
			var arDescs = "Lat/Lng:("+result+")";
			Element.update(sName + coordinateText, arDescs);
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			Event.observe(this.initConfig['name'] + coordinateSelectBtn, 'click', this.getCoordinate.bind(this));
		}
	});
	function ShowDialog4wcm52Style(_url, _nWidth, _nHeight, dialogArguments){
		var nWidth = _nWidth , nHeight = _nHeight; 
		var nLeft	= (window.screen.availWidth - nWidth)/2;
		var nTop	= (window.screen.availHeight - nHeight)/2;

		var sFeatures	= "dialogHeight: "+nHeight+"px; dialogWidth: "+nWidth+"px; "
							+ "dialogTop: "+nTop+"; dialogLeft: "+nLeft+"; "
							+ "center: Yes; scroll:No;help: No; resizable: No; status: No;";
		try{
			var bResult = showModalDialog(_url, dialogArguments==null?window:dialogArguments, sFeatures);
			return bResult;
		}catch(e){
			alert(("您的IE插件已经将对话框拦截!\n")
					+ ("请将拦截去掉-->点击退出-->关闭IE,然后重新打开IE登录即可!\n")
					+ ("给您造成不便,TRS致以深深的歉意!"));		
		}
		return true;
	}
})();