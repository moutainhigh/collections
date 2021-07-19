package com.gwssi.ssoagent.httpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



public class HttpClientTest {
	   private static class TrustAnyTrustManager implements X509TrustManager {
	        
	        public void checkClientTrusted(X509Certificate[] chain, String authType)
	                throws CertificateException {
	        }
	 
	        public void checkServerTrusted(X509Certificate[] chain, String authType)
	                throws CertificateException {
	        }
	 
	        public X509Certificate[] getAcceptedIssuers() {
	            return new X509Certificate[] {};
	        }
	    }
	 
	    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
	        public boolean verify(String hostname, SSLSession session) {
	            return true;
	        }
	    }
	    static String _url="";
	    
	    Map<String,String> _params;
	    public HttpClientTest(String url)
	    {
	        this._url=url;
	        this._params=null;
	    }
	    public HttpClientTest(String url,Map<String,String> keyValueParams)
	    {
	        this._url=url;
	        this._params=keyValueParams;
	    }
	    
	    public static String getClient(String url) throws Exception{
	    	
	    	return Do(url);
	    }
	   
	    
	    
	    public static  String Do(String url) throws Exception
	    {
	         String result = "";
	         BufferedReader in = null;
	            try {
	                
	               // String urlStr = this._url + "&" + getParamStr();
	                String urlStr =url;
	               // System.out.println("GET�����URLΪ��"+urlStr);
	                SSLContext sc = SSLContext.getInstance("SSL");
	                sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
	                         new java.security.SecureRandom());
	               // URL realUrl = new URL(urlStr);
	                // �򿪺�URL֮�������
	                URL realUrl = new URL(null,urlStr,new sun.net.www.protocol.https.Handler());
	            
	            
	                HttpsURLConnection connection = (HttpsURLConnection) realUrl.openConnection();
	                //����https�������
	                connection.setSSLSocketFactory(sc.getSocketFactory());
	                connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
	                connection.setDoOutput(true);
	                
	                // ����ͨ�õ���������
	                connection.setRequestProperty("accept", "*/*");
	                connection.setRequestProperty("connection", "Keep-Alive");
	                /*connection.setRequestProperty("user-agent",
	                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");*/
	                connection.setRequestProperty("user-agent",
	                        "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.27");
	               
	                // ����ʵ�ʵ�����
	                connection.connect();
	                
	                // ���� BufferedReader����������ȡURL����Ӧ
	                in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
	                String line;
	                while ((line = in.readLine()) != null) {
	                    result += line;
	                }
	              //  System.out.println("��ȡ�Ľ��Ϊ��"+result);
	            } catch (Exception e) {
	               // System.out.println("����GET��������쳣��" + e);
	                //e.printStackTrace();
	                throw e;
	            }
	            // ʹ��finally�����ر�������
	            finally {
	                try {
	                    if (in != null) {
	                        in.close();
	                    }
	                } catch (Exception e2) {
	                    //e2.printStackTrace();
	                    throw e2;
	                }
	            }
	            return result;
	    
	    }

	    private String getParamStr()
	    {
	        String paramStr="";
	        // ��ȡ������Ӧͷ�ֶ�
	        Map<String, String> params = this._params;
	        // ��ȡ�����б���ɲ����ַ���
	        for (String key : params.keySet()) {
	            paramStr+=key+"="+params.get(key)+"&";
	        }
	        //ȥ�����һ��"&"
	        paramStr=paramStr.substring(0, paramStr.length()-1);
	        
	        return paramStr;
	    }
/*	public static String getClient(String url) {

		try {

			HttpClient httpclient = new DefaultHttpClient();
			SSLContext ctx = SSLContext.getInstance("SSL");
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {

				}

				public void checkServerTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);

			ClientConnectionManager ccm = httpclient.getConnectionManager();
			// register https protocol in httpclient's scheme registry
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));

			HttpGet httpget = new HttpGet(url);
			HttpParams params = httpclient.getParams();

			params.setParameter("param1", "paramValue1");

			httpget.setParams(params);
			System.out.println("REQUEST:" + url);
			ResponseHandler responseHandler = new BasicResponseHandler();
			String responseBody;

			responseBody = httpclient.execute(httpget, responseHandler);
			return responseBody;
			//System.out.println(responseBody);

			// Create a response handler

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return null;
	}*/
}
