package com.ye;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

//http://blog.csdn.net/qq_34875064/article/details/51312127
//http://fenglingcorp.iteye.com/blog/1964545
//http://yuncode.net/code/c_54db711cbdcca3
//http://blog.csdn.net/qq_26926889/article/details/52891293

/*oracle:
	http://blog.csdn.net/weixin_39415084/article/details/77024822
	https://www.cnblogs.com/yx007/p/6519544.html
*/


//http://www.ihuyi.com/
//http://www.api51.cn/
//https://www.showapi.com
//https://www.jisuapi.com/api/sms/


//修改propertis文件http://pandoracp.blog.163.com/blog/static/199257023201110485918888/
//http://blog.csdn.net/ml5271169588/article/details/11709071
public class SMS {
	public void show() throws Exception {
		URL u = new URL("http://route.showapi.com/892-1?showapi_appid=51301&content=您的自选股[stockCode],名称[stockName],当前幅度[rate1]达到[rate2]提醒幅度，请关注&title=【[title]提醒】¬iPhone=18269215167&showapi_sign=79fe3ebf0f58418b9a8eee4b49887eef");
		InputStream in = u.openStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte buf[] = new byte[1024];
			int read = 0;
			while ((read = in.read(buf)) > 0) {
				out.write(buf, 0, read);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		byte b[] = out.toByteArray();
		System.out.println(new String(b, "utf-8"));

	}

	public void test() throws Exception {
		// http://route.showapi.com/892-5?showapi_appid=myappid&content=&title=&tNum=¬iPhone=&showapi_sign=mysecret
		URL u = new URL("http://route.showapi.com/892-2?showapi_appid=51301&showapi_sign=79fe3ebf0f58418b9a8eee4b49887eef");
		InputStream in = u.openStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte buf[] = new byte[1024];
			int read = 0;
			while ((read = in.read(buf)) > 0) {
				out.write(buf, 0, read);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		byte b[] = out.toByteArray();
		System.out.println(new String(b, "utf-8"));
	}

	public void delete() throws Exception {
		URL u = new URL("http://route.showapi.com/892-6?showapi_appid=51301&tNum=T170317001712&showapi_sign=79fe3ebf0f58418b9a8eee4b49887eef");
		InputStream in = u.openStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte buf[] = new byte[1024];
			int read = 0;
			while ((read = in.read(buf)) > 0) {
				out.write(buf, 0, read);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		byte b[] = out.toByteArray();
		System.out.println(new String(b, "utf-8"));
	}

	public static void main(String[] args) throws Exception {
		SMS s = new SMS();
		/*
		 * s.test(); s.delete();
		 */
		//s.show();
		 s.show();
	}

}
