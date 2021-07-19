//获取cookie、
export default {
	getCookie: function(name) {
		var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
		if(arr = document.cookie.match(reg))
			return(arr[2]);
		else
			return null;
	},

	//设置cookie,增加到vue实例方便全局调用
	/*setCookie: function(c_name, value, expiredays) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + expiredays);
		document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
	},*/

	setCookieExpireByMin: function(c_name, value, minute) {
		/*var exdate = new Date();
		exdate.setTime(exdate.getTime() + minute * 60 * 1000);
		console.log(exdate.toLocaleTimeString())
		console.log(exdate.toUTCString())
		document.cookie = c_name + "=" + escape(value) + ((minute == null) ? "" : ";expires=" + exdate.toUTCString());*/



		//https://www.jianshu.com/p/4336081f3023
		var exdate = new Date();
		exdate.setTime(exdate.getTime() + minute * 60 * 1000); //设置date为当前时间+30分
		document.cookie = c_name + "=" + escape(value) + ((minute == null) ? "" : ";expires=" + exdate.toUTCString());

		/*var d = new Date();
		//1小时候过期
		d.setHours(d.getHours() + 1);
		//存储cookie值
		document.cookie = 'testvalue='+exdate.toLocaleTimeString()+';expires=' + d.toGMTString();
		//存储cookie过期时间，要获取testvalue这个cookie的过期时间，通过获取testexp这个cookie来实现
		document.cookie = 'testexp=' + escape(d.toLocaleString()) + ';expires=' + d.toGMTString();*/

	},
	setCookieExpireByDay: function(c_name, value, expiredays) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + expiredays);
		document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
	},

	//删除cookie
	delCookie: function(name) {
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval = getCookie(name);
		if(cval != null)
			document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
	}

}

//https://www.cnblogs.com/endv/p/8089506.html
//https://www.jianshu.com/p/a3721fe0605a
//https://www.cnblogs.com/hai-cheng/p/7813562.html  see
//https://blog.csdn.net/fiona_lms/article/details/80075227 see

//https://segmentfault.com/a/1190000011275595

//cokie https://www.cnblogs.com/endv/p/8089506.html