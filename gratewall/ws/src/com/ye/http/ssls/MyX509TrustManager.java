package com.ye.http.ssls;

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

//ContextLoader.
//spring �����ĳ�ʼ����һЩ����: getCurrentWebApplicationContext().getBean(MyX509TrustManager.class);
public class MyX509TrustManager {
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

	static String _url = "";

	Map<String, String> _params;

	@SuppressWarnings("static-access")
	public MyX509TrustManager(String url) {
		this._url = url;
		this._params = null;
	}

	@SuppressWarnings("static-access")
	public MyX509TrustManager(String url, Map<String, String> keyValueParams) {
		this._url = url;
		this._params = keyValueParams;
	}

	public static String getClient(String url) throws Exception {

		return Do(url);
	}

	public static String Do(String url) throws Exception {
		String result = "";
		BufferedReader in = null;
		try {
			String urlStr = url;
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			// �򿪺�URL֮�������
			URL realUrl = new URL(null, urlStr,
					new sun.net.www.protocol.https.Handler());
			HttpsURLConnection connection = (HttpsURLConnection) realUrl
					.openConnection();
			// ����https�������
			connection.setSSLSocketFactory(sc.getSocketFactory());
			connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
			connection.setDoOutput(true);
			// ����ͨ�õ���������
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection
					.setRequestProperty(
							"user-agent",
							"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.27");
			// ����ʵ�ʵ�����
			connection.connect();
			// ���� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw e;
		}
		// ʹ��finally�����ر�������
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				throw e2;
			}
		}
		return result;

	}

	@SuppressWarnings("unused")
	private String getParamStr() {
		String paramStr = "";
		// ��ȡ������Ӧͷ�ֶ�
		Map<String, String> params = this._params;
		// ��ȡ�����б���ɲ����ַ���
		for (String key : params.keySet()) {
			paramStr += key + "=" + params.get(key) + "&";
		}
		// ȥ�����һ��"&"
		paramStr = paramStr.substring(0, paramStr.length() - 1);

		return paramStr;
	}

}
