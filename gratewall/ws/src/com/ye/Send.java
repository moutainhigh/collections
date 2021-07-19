package com.ye;

import java.net.URLEncoder;
 

import com.alibaba.fastjson.JSONObject;

public class Send {
 
	// public static final String APPKEY = "your_appkey_here";// 你的appkey
    public static final String APPKEY = "ee5cf78a299518bc";// 你的appkey
    public static final String URL = "http://api.jisuapi.com/sms/send";
    public static final String mobile = "18269215167";// 手机号
    public static final String content = "您的关注002558，名字中国深化，当前12.3，已达到关注。【胖头鱼】";// utf-8
 
    public static void Get() throws Exception {
        String result = null;
        String url = URL + "?mobile=" + mobile + "&content=" + URLEncoder.encode(content, "utf-8") + "&appkey="
                + APPKEY;
 
        try {
            result = HttpUtil.sendGet(url, "utf-8");
            JSONObject json = JSONObject.parseObject(result);
            System.out.println(json);
            /*if (json.getInteger("status") != 0) {
                System.out.println(json.getString("msg"));
            } else {
                JSONObject resultarr = json.parseObject("result");
                String count = resultarr.getString("count");
                String accountid = resultarr.getString("accountid");
                System.out.println(count + " " + accountid);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    	try {
			Send.Get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}