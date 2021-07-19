package com.ly.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
public class DownLoadPic {
	
	private static Logger logger = Logger.getLogger("pic");
	
    
    public  static void getPicsToDistincPath(String code){
    	run1(code);
    	run2(code);
    }
    
    

	public static void run1(String code){
    	String dayURL = "http://image.sinajs.cn/newchart/daily/n/CODE.gif";
    	String dayPicPath = "c://pic//day";
    	if(code.indexOf("6")==0){
    		dayURL = dayURL.replace("CODE", "sh" +code);
		}else{
    		dayURL = dayURL.replace("CODE", "sz" +code);
		}
    	downloadPicture(dayURL,dayPicPath);
	}
	
	
    public static void run2(String code){
		String weekUrl = "http://image.sinajs.cn/newchart/weekly/n/CODE.gif";
    	final String weekPicPath = "c://pic//week";
    	if(code.indexOf("6")==0){
    		weekUrl = weekUrl.replace("CODE", "sh" +code);
		}else{
			weekUrl = weekUrl.replace("CODE", "sz" +code);
		}
		downloadPicture(weekUrl,weekPicPath);
	}

    
    
    
    
    //https://blog.csdn.net/daluyang/article/details/79560576
    //https://www.cnblogs.com/kumu/p/10241986.html
    private static void downloadPicture(String picUrl,String path)  {
    	File file0=new File(path);
        if(!file0.isDirectory()&&!file0.exists()){
            file0.mkdirs();
        }
        String name = picUrl.substring(picUrl.lastIndexOf("/"),picUrl.length());
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)   //设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)//默认允许自动重定向
                .build();
	
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;//https://blog.csdn.net/victorxuqili/article/details/49180395
	    try{
	        HttpGet get = new HttpGet(picUrl);  
	        
	        get.setConfig(requestConfig);
	        
	        response = httpclient.execute(get);
	        HttpEntity entity = response.getEntity();  
	        InputStream in = entity.getContent();  
	        
	        File file = new File(file0+"\\" +name);  
	        try {
	            FileOutputStream fout = new FileOutputStream(file);
	            int l = -1;
	            byte[] tmp = new byte[1024];
	            while ((l = in.read(tmp)) != -1) {
	                fout.write(tmp, 0, l);
	            }
	            fout.flush();
	            fout.close();
	        } finally {
	            // 关闭低层流。
	            in.close();
	        }
	    }catch(Exception e){
	       // System.out.println("下载图片出错"+picUrl);
	        logger.debug("图片" +picUrl + " ,下载失败，原因为" + e.getMessage());
	    }finally {
	    	if(null!=response){
	    		try {
					response.close();
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    	
		}
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //picUrl 图片连接，name 图片名称，imgPath 图片要保存的地址,https://blog.csdn.net/victorxuqili/article/details/49180395
    //https://www.cnblogs.com/zhao1949/p/8175658.html
    //https://www.cnblogs.com/mengxinrenyu/p/7633802.html
    public void downloadImg(String picUrl ,String name ,String imgPath) throws ClientProtocolException, IOException{
    	CloseableHttpClient httpclient = HttpClients.createDefault();
	    try{
	        HttpGet get = new HttpGet(picUrl);  
	        HttpResponse response = httpclient.execute(get);
	        HttpEntity entity = response.getEntity();  
	        InputStream in = entity.getContent();  
	        String dir = imgPath;
	        File file = new File(dir,name+".jpg");  
	        try {
	            FileOutputStream fout = new FileOutputStream(file);
	            int l = -1;
	            byte[] tmp = new byte[1024];
	            while ((l = in.read(tmp)) != -1) {
	                fout.write(tmp, 0, l);
	            }
	            fout.flush();
	            fout.close();
	        } finally {
	            // 关闭低层流。
	            in.close();
	        }
	    }catch(Exception e1){
	        System.out.println("下载图片出错"+picUrl);
	    }
	    httpclient.close();

}

    
    
    
	
}
