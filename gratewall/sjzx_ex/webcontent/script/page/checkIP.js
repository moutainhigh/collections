//测试IP地址
/*
用途：校验ip地址的格式 
输入：strIP：ip地址 
返 回：如果通过验证返回true,否则返回false； 

*/ 
function isIP(strIP) { 
	if (isNull(strIP)) return false; 
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g //匹配IP地址的正则表达式 
	if(re.test(strIP)) 
	{ 
		if( RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) return true; 
	} 
	return false; 
} 

/* 
用 途：检查输入字符串是否为空或者全部都是空格 
输入：str 
返回： 
如果全是空返回true,否则返回false 
*/ 
function isNull( str ){ 
if ( str == "" ) return true; 
var regu = "^[ ]+$"; 
var re = new RegExp(regu); 
return re.test(str); 
} 

/*
用途：检查输入对象的值是否符合端口号格式
输入：str 输入的字符串
返回：如果通过验证返回true,否则返回false
*/
function check_port(port)
{
    if(!isNumber(port))
    	return false;
        
    if(port < 65536)
        return true;

    return false; 
}

/* 
用途：检查输入字符串是否符合正整数格式 
输入： 
s：字符串 
返回： 
如果通过验证返回true,否则返回 false 

*/ 
function isNumber( s ){ 
	var regu = "^[0-9]+$"; 
	var re = new RegExp(regu); 
	if (s.search(re) != -1) { 
		return true; 
	} else { 
		return false; 
	} 
} 