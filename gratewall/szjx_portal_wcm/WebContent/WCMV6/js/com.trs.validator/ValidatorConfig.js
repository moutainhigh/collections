$package('com.trs.validator');

/*
控件的属性validation支持的标记：
	desc		:用于替换默认提示信息中的{0},如果未设置此值，则用控件的name属性
	type		:可选值为email,ipv4,url,float,int,string,common_char,radio,select[exclude],
				 time[time_format],combin[subtype,relation,field]
	date_format	:日期格式的类型.yyyy/mm/dd, mm/dd/yyyy, dd/mm/yyyy, yyyy-mm-dd, mm-dd-yyyy, dd-mm-yyyy
	format		:执行自定义的js正则表达式校验.
	required	:为必填项
	equals_with	:和另一个控件值相等,此值为另一个控件的Id
	min_len		:最小长度
	max_len		:最大长度
	min			:最小值
	max			:最大值
	message		:未进行校验之前显示的提示信息,如果未设置此值,则显示默认提示信息.
	warning		:格式不合法时的提示信息,如果未设置此值,则显示默认警告信息.
	length_range:长度范围.length_range:'1,50'
	value_range	:值的范围.value_range:'-12,30'
	methods		:进行格式校验之后,最后执行的一系列自定义方法
	showid		:信息显示在showId上
	no_class	:当信息显示在showId上时,不保留系统默认的class
	rpc			:执行与服务器端交换的校验方法,回调时执行相应的方法ValidationHelper.failureRPCCallBack，ValidationHelper.successRPCCallBack
	close		:开关功能,一个表达式或函数调用.程序中用eval(close)进行判断决定此控件是否执行校验,true表示关闭,否则执行校验
e.g.
	<input type="text" name="userName" value="" validation="desc:'**';pattern:'**';format:''">
*/

var $ValidatorConfigs = com.trs.validator.ValidatorConfigs = {
	PREFIX_HINT_SPAN_ID		: 'com_trs_wcm_ajax_hint_node_',
	PREFIX_WARNING_SPAN_ID	: 'com_trs_wcm_ajax_warning_node_',
	PREFIX_MESSAGE_SPAN_ID	: 'com_trs_wcm_ajax_message_node_',
	WARNING_LOG_PATH		: com.trs.util.Common.BASE+'com.trs.validator/images/error.gif',
	MESSAGE_LOG_PATH		: com.trs.util.Common.BASE+'com.trs.validator/images/info.gif',
	WARNING_CLASSNAME_KEY	: "warningClassKey",
	MESSAGE_CLASSNAME_KEY	: "messageClassKey",
	WARNING_CLASSNAME_MOUSE	: "warningClassMouse",
	MESSAGE_CLASSNAME_MOUSE	: "messageClassMouse",
	REQUIREDCLASS			: "requiredClass",
	UserLanguage			: 'auto',
	DATE_FORMAT_DEFAULT		: "yyyy-mm-dd hh:MM:ss",
	TIME_FORMAT_DEFAULT		: "hh:mm:ss",
	MOUSE_MODE				: false,
	SHOW_ALL_MODE			: false,
	FOCUS_MODE				: true,
	REQUIRED_HINT			: true,
	WARNING_BORDER			: 'red 1px solid',
	

//-------------------------------Self Define Mark Begin---------------------------------
	DESC				: "desc"			.toLowerCase(),
	TYPE				: "type"			.toLowerCase(),
	REQUIRED			: "required"		.toLowerCase(),
	EQUALS_WITHS		: "equals_with"		.toLowerCase(),
	MIN_LEN				: "min_len"			.toLowerCase(),
	MAX_LEN				: "max_len"			.toLowerCase(),
	LENGTH_RANGE		: "length_range"	.toLowerCase(),
	MIN					: "min"				.toLowerCase(),
	MAX					: "max"				.toLowerCase(),	
	VALUE_RANGE			: "value_range"		.toLowerCase(),
	DATE_FORMAT			: "date_format"		.toLowerCase(),
	FORMAT				: "format"			.toLowerCase(),
	MESSAGE				: "message"			.toLowerCase(),
	WARNING				: "warning"			.toLowerCase(),	
	METHODS				: "methods"			.toLowerCase(),
	SHOWID				: "showid"			.toLowerCase(),
	COMMON_CHAR			: "common_char"		.toLowerCase(),
	COMMON_CHAR2		: "common_char2"	.toLowerCase(),
	NO_CLASS			: "no_class"		.toLowerCase(),
	BLUR_SHOW			: 'blur_show'		.toLowerCase(),
	RPC					: "rpc"				.toLowerCase(),
	CLOSE				: "close"			.toLowerCase(),
	TIME_FORMAT			: "time_format"		.toLowerCase(),
	EXCLUDE				: "exclude"			.toLowerCase(),
	SUBTYPE				: "sub_type"		.toLowerCase(),
	RELATION			: "relation"		.toLowerCase(),
	FIELD				: "field"			.toLowerCase(),
	NO_DESC				: "no_desc"			.toLowerCase(),
	REQUIRE_CONTAINER	: "require_container".toLowerCase(),
	NO_REQUIRE_HINT		: "no_require_hint"	.toLowerCase(),
//-------------------------------Self Define Mark end-----------------------------------


//-------------------------------Message Constants Define Begin-------------------------
	MESSAGE_INFO		:	{
		"zh-cn"			:		{
			REQUIRED				: '{0}不能为空;',
			EQUALS_WITHS			: '{0}必须和{1}一致;',
			NUMBER					: '{0}必须为数字;',
			INT						: '{0}必须为整数;',
			FLOAT					: '{0}必须为浮点数;',
			DOUBLE					: '{0}必须为双精度;',
			MIN_LEN					: '{0}最小长度为{1};',
			MAX_LEN					: '{0}最大长度为{1};',
			MIN						: '{0}最小值为{1};',
			MAX						: '{0}最大值为{1};',
			LENGTH_RANGE			: '{0}长度范围为{1}~{2};',
			VALUE_RANGE				: '{0}值范围为{1}~{2};',
			URL						: '{0}期望格式为:http(s)://[站点](:[端口])/(子目录);',
			LINK					: '{0}期望格式为:xxx.xxx.xxx;',
			IPV4					: '{0}符合IPV4格式，期望格式为: 192.9.200.114;',
			COMMON_CHAR				: '{0}由字母,数字,下划线组成;',
			COMMON_CHAR2			: '{0}由字母开始的字母,数字,下划线组成;',
			SELECT					: '必须选择一个{0}',
			TIME_ONE				: '{0}格式必须为"hh:mm"，如：09:03',
			TIME_TWO				: '{0}格式必须为"h:m"，如：9:3' 
		}
	},

//-------------------------------Message Constants Befine End---------------------------


//-------------------------------Warning Constants Define Begin-------------------------
	WARNING_INFO		:	{
		"zh-cn"			:		{
			DEFAULT					: '{0}格式不合法;',
			REQUIRED				: '{0}不能为空;',
			EQUALS_WITHS			: '{0}必须和{1}一致;',
			NUMBER					: '{0}必须为数字;',
			INT						: '{0}必须为整数;',
			FLOAT					: '{0}必须为浮点数;',
			DOUBLE					: '{0}必须为双精度;',				
			MIN_LEN					: '{0}小于最小长度{1};',
			MAX_LEN					: '{0}大于最大长度{1};',
			MIN						: '{0}小于最小值({1});',
			MAX						: '{0}大于最大值({1});',
			LENGTH_RANGE			: '{0}长度不在范围{1}~{2};',
			VALUE_RANGE				: '{0}值不在范围{1}~{2};',
			URL						: '{0}正确格式为：http(s)://[站点](:[端口])/(子目录);',
			LINK					: '{0}不合法,正确格式为:xxx.xxx.xxx;',
			IPV4					: '{0}不符合IPV4格式！正确格式为: 192.9.200.114;',
			IPV4_HIGH_EQUAL_0		: '{0}IPV4最高位不能为0！正确格式为: 192.9.200.114;',
			IPV4_BEYOND_255			: '{0}IPV4任一位数值不能超过255！正确格式为: 192.9.200.114;',
			COMMON_CHAR				: '{0}不是由字母、数字、下划线组成;',
			COMMON_CHAR2			: '{0}不是由字母开始的字母,数字,下划线组成;',
			SELECT					: '必须选择一个{0}',
			TIME					: '{0}格式必须为"hh:mm"，如：12:30',
			HOUR					: '小时必须在0~23之间',
			MINUTE					: '分钟必须在0~59之间',
			SECOND					: '秒必须在0~59之间'
		}
	},
//-------------------------------Warning Constants Define End---------------------------

	setMouseMode : function(bool){
		$ValidatorConfigs.MOUSE_MODE = bool;
	},
	getMouseMode : function(){
		return $ValidatorConfigs.MOUSE_MODE;
	},
	setShowAllMode : function(bool){
		$ValidatorConfigs.SHOW_ALL_MODE = bool;
	},
	getShowAllMode : function(){
		return $ValidatorConfigs.SHOW_ALL_MODE;
	},
	setFocusMode : function(bool){
		$ValidatorConfigs.FOCUS_MODE = bool;
	},
	getFocusMode : function(){
		return $ValidatorConfigs.FOCUS_MODE;
	},
	setRequiredHint : function(bool){
		$ValidatorConfigs.REQUIRED_HINT = bool;
	},
	getRequiredHint : function(){
		return $ValidatorConfigs.REQUIRED_HINT;
	}
};
