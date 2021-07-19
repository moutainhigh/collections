$package('com.trs.validation.ValidatorConfig');

// verify for determination of User Language
var UserLanguage = 'auto';
// determine if need to hide the desc of field before the alertion String
ValidationHelper.HideFieldDesc4AlertionStr = false;
ValidationHelper.EmailLenthLimitation = 200;

// default configuration for defined-alertion
var DefinedAlertion = new Object();
////Simplified Chinese(zh-ch)
DefinedAlertion['zh-cn'] = new Object();
DefinedAlertion['zh-cn']['default']			 = '{0}校验失败。';
DefinedAlertion['zh-cn']['required']		 = '{0}不能为空。';
DefinedAlertion['zh-cn']['common-char']		 = '{0}必须为字母、下划线或者数字所组成。';
DefinedAlertion['zh-cn']['chinese']			 = '{0}要求全部为汉字。';
DefinedAlertion['zh-cn']['string2']			 = '{0}不能含有"<"，"$&" 或者 "#INNERTHML"。';
DefinedAlertion['zh-cn']['below_min_len']	 = '{0}长度为{1}，小于允许输入的最小长度{2}(每汉字相当于2个字符)。';
DefinedAlertion['zh-cn']['beyond_max_len']	 = '{0}长度为{1}，大于允许输入的最大长度{2}(每汉字相当于2个字符)。' ;
DefinedAlertion['zh-cn']['int']		= '{0}数值类型要求为整型。';
DefinedAlertion['zh-cn']['float']	= '{0}数值类型要求为浮点型。';
DefinedAlertion['zh-cn']['below_min_val']	 = '{0}小于允许输入的最小值({1})。';
DefinedAlertion['zh-cn']['beyond_max_val']	 = '{0}大于允许输入的最大值({1})。';
DefinedAlertion['zh-cn']['equals-with']		 = '{0}必须和{1}一致。';
DefinedAlertion['zh-cn']['unknow_date_format']	   = '{0}不支持的日期格式：{1}。';
DefinedAlertion['zh-cn']['unexpected_date_format'] = '{0}不是一个有效日期，期待格式：{1}。';
DefinedAlertion['zh-cn']['invalid_year']	 = '[年] 必须大于 0。';
DefinedAlertion['zh-cn']['invalid_month']	 = '[月] 必须在 1-12 之间。';
DefinedAlertion['zh-cn']['invalid_day']		 = '[日] 必须在 1-31 之间。';
DefinedAlertion['zh-cn']['invalid_day_of_month']		= '当前月份最多只有30天。';
DefinedAlertion['zh-cn']['invalid_leapyear_feb']		= '闰年的二月最多只有29天。';
DefinedAlertion['zh-cn']['invalid_non_leapyear_feb']	= '非闰年的2月最多只有28天。';

//en
DefinedAlertion['en-us'] = new Object();
DefinedAlertion['en-us']['default']			 = 'failed to validate.';
DefinedAlertion['en-us']['required']		 = 'required.';
DefinedAlertion['en-us']['common-char']		 = 'should be common ascii characters, A-Z,a-z and undercore.';
DefinedAlertion['en-us']['chinese']			 = 'should be chinese characters.';
DefinedAlertion['en-us']['string2']			 = 'should NOT be along with \'<\', \'$&\' or \'#INNERTHML\'.';
DefinedAlertion['en-us']['below_min_len']	 = 'for length {1}, it should NOT be shorter than {2}.';
DefinedAlertion['en-us']['beyond_max_len']	 = 'for length {1}, it should NOT be longger than {2}.' ;
DefinedAlertion['en-us']['int']	  = 'expected an integer.';
DefinedAlertion['en-us']['float'] = 'expected a float.';
DefinedAlertion['en-us']['below_min_val']	 = 'the value should NOT be below {1}.';
DefinedAlertion['en-us']['beyond_max_val']	 = 'the value should NOT be beyond {1}.';
DefinedAlertion['en-us']['equals_with']		 = 'should has the same value of "{1}".';
DefinedAlertion['en-us']['unknow_date_format']	   = 'invalid date format：{1}.';
DefinedAlertion['en-us']['unexpected_date_format'] = 'expected a determinated date format：{1}.';
DefinedAlertion['en-us']['invalid_year']	 = '[year] should be above 0.';
DefinedAlertion['en-us']['invalid_month']	 = '[month] should be between 1-12.';
DefinedAlertion['en-us']['invalid_day']		 = '[day] should be between 1-31.';
DefinedAlertion['en-us']['invalid_day_of_month']		= 'the days of current month should not beyond 30th.';
DefinedAlertion['en-us']['invalid_leapyear_feb']		= 'the feberery of a leap year has no more than 29 days';
DefinedAlertion['en-us']['invalid_non_leapyear_feb']	= 'the feberery of a non-leap year has no more than 28 days。';
//
DefinedAlertion['zh-cn'][ATTR_NAME_FORMATE]		= '期待日期格式：{1}。';
DefinedAlertion['zh-cn'][ATTR_NAME_LENGTH_MIN]	= '{0}最小长度允许为{1}。';
DefinedAlertion['zh-cn']['length_range']		= '{0}长度允许范围为[{1}~{2}]。';
DefinedAlertion['zh-cn'][ATTR_NAME_LENGTH_MAX]  = '{0}最大长度允许为{1}。';
DefinedAlertion['zh-cn'][ATTR_NAME_MIN]			= '{0}最小值允许为{1}。';
DefinedAlertion['zh-cn'][ATTR_NAME_MAX]			= '{0}最大值允许为{1}。';
DefinedAlertion['zh-cn']['value_range']			= '{0}值允许范围为[{1}~{2}]。';
DefinedAlertion['zh-cn']['email']				= '要求为Email。e.g. jack@trs.com';
DefinedAlertion['zh-cn']['ip']					= '要求为IPV4。e.g. 192.9.200.114';
DefinedAlertion['en-us'][ATTR_NAME_FORMATE]		= 'expected format: {1}。';
DefinedAlertion['en-us'][ATTR_NAME_LENGTH_MIN]	= 'the length should be NOT shotter than{1}。';
DefinedAlertion['en-us']['length_range']		= 'the length should be within [{1}, {2}]。';
DefinedAlertion['en-us'][ATTR_NAME_LENGTH_MAX]  = 'the length should be NOT logger than{1}。';
DefinedAlertion['en-us'][ATTR_NAME_MIN]			= 'the value should be NOT below {1}。';
DefinedAlertion['en-us'][ATTR_NAME_MAX]			= 'the value should be NOT beyond {1}。';
DefinedAlertion['en-us']['value_range']			= 'the value should be with [{1}~{2}]。';
DefinedAlertion['en-us']['email']				= 'need Email format. e.g. jack@trs.com';
DefinedAlertion['en-us']['ip']					= 'need IPV4 format. e.g. 192.9.200.114';

// self defined
DefinedAlertion['zh-cn']['invalid_email']			= '{0}不是一个合法的Email，正确格式为: jack@trs.com';
DefinedAlertion['zh-cn']['invalid_email_overflow']	= 'Email地址长度必须在' + ValidationHelper.EmailLenthLimitation + '个字符以内。';
DefinedAlertion['zh-cn']['invalid_ip_format']	 = '{0}不符合IPV4格式。正确格式为: 192.9.200.114';
DefinedAlertion['zh-cn']['love_highest_op_vilo'] = '{0}IPV4最高位不能为0。正确格式为: 192.9.200.114';
DefinedAlertion['zh-cn']['vilo_val_beyond_255']  = '{0}IPV4任一位数值不能超过255。正确格式为: 192.9.200.114';
DefinedAlertion['en-us']['invalid_email']			= 'invalid email format.';
DefinedAlertion['en-us']['invalid_email_overflow']	= 'for length of a certain email, it should NOT be shorter than ' + ValidationHelper.EmailLenthLimitation;
DefinedAlertion['en-us']['invalid_ip_format']	 = 'invalid IPV4 format';
DefinedAlertion['en-us']['love_highest_op_vilo'] = 'the hightest vilo of IPV4 could not be 0.';
DefinedAlertion['en-us']['vilo_val_beyond_255']  = 'no vilo of IPV4 could be beyond 255.';

/**
	Samples to demonstrat the method of extending pattern validation
*/
ValidationHelper.extend(function(sRule, sVal, field){
	switch(sRule.toLowerCase()){
		case 'email':
			if (sVal.trim().length > ValidationHelper.EmailLenthLimitation) {
				return ValidationHelper.getAlertionString(field, 'invalid_email_overflow');
			}
			if (!validateEmail(sVal))
				return ValidationHelper.getAlertionString(field, 'invalid_email');
			break;
		case 'ip':
			var sCheckResult = checkIPV4(sVal, field);
			if (sCheckResult) return sCheckResult;
			break;
	}
	
	return null;
});

function validateEmail(_sEmail){
	if (_sEmail.length == 0) {
		return true;
	}

	var emailPat=/^(.+)@(.+)$/;
	var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]";
	var validChars="\[^\\s" + specialChars + "\]";
	var quotedUser="(\"[^\"]*\")";
	var ipDomainPat=/^(\d{1,3})[.](\d{1,3})[.](\d{1,3})[.](\d{1,3})$/;
	var atom=validChars + '+';
	var word="(" + atom + "|" + quotedUser + ")";
	var userPat=new RegExp("^" + word + "(\\." + word + ")*$");
	var domainPat=new RegExp("^" + atom + "(\\." + atom + ")*$");
	var matchArray=_sEmail.match(emailPat);
	if (matchArray == null) {
		return false;
	}
	var user=matchArray[1];
	var domain=matchArray[2];
	if (user.match(userPat) == null) {
		return false;
	}
	var IPArray = domain.match(ipDomainPat);
	if (IPArray != null) {
		for (var i = 1; i <= 4; i++) {
			if (IPArray[i] > 255) {
				return false;
			}
		}
		return true;
	}
	var domainArray=domain.match(domainPat);
	if (domainArray == null) {
		return false;
	}
	var atomPat=new RegExp(atom,"g");
	var domArr=domain.match(atomPat);
	var len=domArr.length;
	if ((domArr[domArr.length-1].length < 2) ||
		(domArr[domArr.length-1].length > 3)) {
		return false;
	}
	if (len < 2) {
		return false;
	}
	return true;
}

function checkIPV4(_sIP, _field){
	if (_sIP.trim().length == 0) 
		return null;

	var ptn = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;

	if(_sIP.match(ptn) == null){
		return ValidationHelper.getAlertionString(_field, 'invalid_ip_format');
	}

	if (RegExp.$1 == 0)
		return ValidationHelper.getAlertionString(_field, 'love_highest_op_vilo');
	if (Math.max(RegExp.$1, RegExp.$2, RegExp.$3, RegExp.$4) > 255)
		return ValidationHelper.getAlertionString(_field, 'vilo_val_beyond_255');

	return null;
}
function checkHttpUrl(_sUrl){
	//站点HTTP格式检验
	var ptnRootDomain = /^(http:\/\/|https:\/\/)./;
	var strRootDomain = _sUrl || '';
	strRootDomain = strRootDomain.toLowerCase();
	if(strRootDomain!='' && !strRootDomain.match(ptnRootDomain)) {
		return '不合法的url！正确格式为：http(s)://[站点](:[端口])/(子目录)！';
	}
	
	return null;
}
//------------self defined form validator -------------//
