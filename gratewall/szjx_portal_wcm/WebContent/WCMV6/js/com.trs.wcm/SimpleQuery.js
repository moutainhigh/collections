$package('com.trs.wcm');

$importCSS('com.trs.wcm.SimpleQuery');

var SimpleQuery = {
	baseURL : '../',
	QUERY_MAX_LEN: 200,
	register : function(_sContainerId, _arQueryFields, _fQueryAction, _bCanQueryAll, _nMaxLen){
		//校验参数
		if(_sContainerId == null || _sContainerId == '') {
			alert('[SimpleQuery.register] 没有指定容器用来存放检索模块！');
			return;
		}
		this.Container = $(_sContainerId);
		if(this.Container == null) {
			alert('[SimpleQuery.register] 没有找到[Id=' + _sContainerId + ']的容器用来存放检索模块！');
			return;
		}
		if(_arQueryFields == null || !(_arQueryFields.length > 0)) {
			alert('[SimpleQuery.register] 没有指定要注册的检索字段！');
			return;
		}

		//获取参数
		this.QAction = _fQueryAction || ProtoType.emptyFunction;
		this.CanQueryAll = _bCanQueryAll || false;
		var arAllFields = this.CanQueryAll ? [{name: -1, desc: '全部', type: 'string'}] : [];
		this.QueryFields = arAllFields.concat(_arQueryFields);
		if(_nMaxLen && parseInt(_nMaxLen) > 0) {
			this.QUERY_MAX_LEN = _nMaxLen;
		}

		//初始化界面
		this.__initQueryBox();
	},
	changeQueryType : function(){
		var eQVal = $('txtQueryVal');
		if(eQVal.value.indexOf('..输入') >= 0) {
			var currQueryField = this.__getCurrentField();
			var sDefaltQVal = '..输入' + (currQueryField['name'] == -1 ? '检索词' : currQueryField['desc']);

			eQVal.value = sDefaltQVal;
			eQVal.style.color = 'gray';		
		}

		eQVal.select();
		eQVal.focus();
	},
	changeQueryVal : function(_txt){
		_txt.style.color = '#414141';
		_txt.select();

		delete _txt;
	},
	renderQuery : function(_form){
		var currQueryField = this.__getCurrentField();
		var sDefaltQVal = '..输入' + (currQueryField['name'] == -1 ? '检索词' : currQueryField['desc']);

		var sQVal = $('txtQueryVal').value.trim();
		var sInvalidMsg = null;
		var params = {};

		if(this.CanQueryAll && currQueryField['name'] == -1) {
			sInvalidMsg = this.__checkStrLen(sQVal, this.QUERY_MAX_LEN);
			if(sInvalidMsg == null && sQVal != sDefaltQVal) {
				for (var i = 1; i < this.QueryFields.length; i++){
					var field = this.QueryFields[i];
					
					if(field == null){
						continue;
					}
					if(field['type'] == 'int' && sQVal.match(/^[1-9]+[0-9]*$/) == null) {
						continue;
					}
					if(field['type'] == 'float' && sQVal.match(/^-?[0-9]+(.[0-9]*)?$/) == null) {
						continue;
					}
					params[field['name']] = sQVal;
				}
				params['isor'] = true;
			}
		}else{
			params['isor'] = false;
			//校验参数
			var validate = currQueryField['validate'];
			if(validate != null && typeof(validate) == 'function') {
				sInvalidMsg = validate(sQVal);
			}else{
				if(currQueryField['type'] == 'int') {//校验 int 类型
					if(sQVal != '' && sQVal != sDefaltQVal) {
						var nIntVal = parseInt(sQVal, 10);
						if(!(/^\d+$/).test(sQVal)) {// || nIntVal == 0) {
							//sInvalidMsg = '要求为正整数！';
							sInvalidMsg = '要求为大于零的整数！';
						}else if(nIntVal > parseInt('1111111111111111111111111111111',2)){
							sInvalidMsg = '要求为不大于' + parseInt('1111111111111111111111111111111',2) + '(2^31)的数字！';
						}
						sQVal = nIntVal;
					}
				}else if(currQueryField['type'] == 'float'){
					if(sQVal != '' && sQVal != sDefaltQVal) {
						if(sQVal.match(/^-?[0-9]+(.[0-9]*)?$/) == null){
							sInvalidMsg = '要求为浮点数！';
						}
					}					
				}else{//默认为 string 类型
					var nMaxLen = this.QUERY_MAX_LEN;
					var nLen = currQueryField['length'];
					if(nLen > 0) {
						nMaxLen = nLen;
					}
					sInvalidMsg = this.__checkStrLen(sQVal, nMaxLen);
				}
			}		
			if(sQVal == sDefaltQVal) {
				sQVal = '';
			}
			params[currQueryField['name']] = sQVal;
		}
		//处理不合理的检索值
		if(sInvalidMsg != null) {
			$fail(sInvalidMsg, function(){
				$dialog().hide();
				$('txtQueryVal').focus();
				$('txtQueryVal').select();
			});
			return;
		}

		//开始检索
		for (var i = 1; i < this.QueryFields.length; i++){
			var field = this.QueryFields[i];
			if(field == null || params[field['name']] != null) {
				continue;
			}
			params[field['name']] = '';
		}
		this.QAction(params);
	},
	__checkStrLen : function(_str, _nMaxLen){
		var nLength = _str.getLength();
		if(nLength > _nMaxLen) {
			return '<span style="width:180px;overflow-y:auto;">当前检索字段限制为[<b>' + _nMaxLen 
				+ '</b>]个字符长度，当前为[<b>' + nLength 
				+ '</b>]！\<br><br><b>提示：</b>每个汉字长度为2。</span>';
		}	
		return null;
	},
	__getCurrentField : function(){
		return this.QueryFields[$('selQueryType').selectedIndex];
	},
	__initQueryBox : function(){
		var sQueryFields = '';
		for (var i = 0; i < this.QueryFields.length; i++){
			var field = this.QueryFields[i];
			if(field == null) {
				continue;
			}
			sQueryFields += '<option value="' + field['name'] + '">' + field['desc'] + '</option>';
		}
		var sDesc = '..输入' + (this.CanQueryAll ? '检索词' : this.QueryFields[0]['desc']);
		var sQueryValues = '<input name="QueryVal" id="txtQueryVal" type="text" class="simple_query_text" onfocus="SimpleQuery.changeQueryVal(this)" value="'
				+ sDesc + '">';
		
		if(this.Html == null) {
			this.Html = '\
				<form id="fmQuery" name="fmQuery" method="post" action="" style="margin:0px;" onsubmit="SimpleQuery.renderQuery(this); return false;">\
					<table border="0" cellpadding="0" cellspacing="0"  class="simple_query_box" background="' + this.baseURL + 'images/include/searchbg.gif">\
						<tr>\
							<td width="100%" align="right" valign="bottom">\
								<table height="20" border="0" cellpadding="0" cellspacing="0">\
								  <tr>\
									<td style="white-space: nowrap; padding-top: 4px; padding-right: 5px;">\
									  ' + sQueryValues + '\
									  <select name="QueryType" id="selQueryType" onchange="SimpleQuery.changeQueryType(this)" class="simple_query_select">\
										' + sQueryFields + '\
									  </select>\
									</td>\
									<td width="45" valign="bottom" align="right">\
									  <input name="imageField" type="image" src="' + this.baseURL + 'images/include/do_search.gif" width="46" height="18" border="0" style="margin-bottom: 3px; cursor: pointer;"></td>\
									<td id="rightEdge" align="right"><img class="simple_query_right_edge" src="' + this.baseURL + 'images/include/searchr.gif" height="29"></td>\
								  </tr>\
								</table>\
							</td>\
						</tr>\
					</table>\
				</form>';
			
		}
		this.Container.innerHTML = this.Html;
	}
}