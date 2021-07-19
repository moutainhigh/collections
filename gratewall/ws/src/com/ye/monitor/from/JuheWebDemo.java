package com.ye.monitor.from;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
 
/**
*��Ʊ���ݵ���ʾ������ �� �ۺ�����
*���߽ӿ��ĵ���http://www.juhe.cn/docs/21
**/
 //https://code.juhe.cn/docs/763
@Component
public class JuheWebDemo {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
 
    //�����������KEY
    public static final String APPKEY ="*************************";
 
    //1.�������
    public static void getRequest1(){
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/stock/hs";//����ӿڵ�ַ
        Map params = new HashMap();//�������
            params.put("gid","");//��Ʊ��ţ��Ϻ�������sh��ͷ�����ڹ�����sz��ͷ�磺sh601009
            params.put("key",APPKEY);//APP Key
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSON.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //2.��۹���
    public static void getRequest2(){
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/stock/hk";//����ӿڵ�ַ
        Map params = new HashMap();//�������
            params.put("num","");//��Ʊ���룬�磺00001 Ϊ������ʵҵ����Ʊ����
            params.put("key",APPKEY);//APP Key
 
        try {
            result =net(url, params, "GET");
           JSONObject object = JSON.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //3.��������
    public static void getRequest3(){
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/stock/usa";//����ӿڵ�ַ
        Map params = new HashMap();//�������
            params.put("gid","");//��Ʊ���룬�磺aapl Ϊ��ƻ����˾���Ĺ�Ʊ����
            params.put("key",APPKEY);//APP Key
 
        try {
            result =net(url, params, "GET");
           JSONObject object = JSON.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //4.��۹����б�
    public static void getRequest4(){
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/stock/hkall";//����ӿڵ�ַ
        Map params = new HashMap();//�������
            params.put("key",APPKEY);//�������APPKEY
            params.put("page","");//�ڼ�ҳ,ÿҳ20������,Ĭ�ϵ�1ҳ
 
        try {
            result =net(url, params, "GET");
           JSONObject object = JSON.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //5.���������б�
    public static void getRequest5(){
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/stock/usaall";//����ӿڵ�ַ
        Map params = new HashMap();//�������
            params.put("key",APPKEY);//�������APPKEY
            params.put("page","");//�ڼ�ҳ,ÿҳ20������,Ĭ�ϵ�1ҳ
 
        try {
            result =net(url, params, "GET");
           JSONObject object = JSON.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //6.���ڹ����б�
    public static void getRequest6(){
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/stock/szall";//����ӿڵ�ַ
        Map params = new HashMap();//�������
            params.put("key",APPKEY);//�������APPKEY
            params.put("page","");//�ڼ�ҳ(ÿҳ20������),Ĭ�ϵ�1ҳ
 
        try {
            result =net(url, params, "GET");
           JSONObject object = JSON.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //7.�����б�
    public static void getRequest7(){
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/stock/shall";//����ӿڵ�ַ
        Map params = new HashMap();//�������
            params.put("key",APPKEY);//�������APPKEY
            params.put("page","");//�ڼ�ҳ,ÿҳ20������,Ĭ�ϵ�1ҳ
 
        try {
            result =net(url, params, "GET");
           JSONObject object = JSON.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 
 
    public static void main(String[] args) {
 
    }
 
    /**
     *
     * @param strUrl �����ַ
     * @param params �������
     * @param method ���󷽷�
     * @return  ���������ַ���
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //��map��תΪ���������
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
