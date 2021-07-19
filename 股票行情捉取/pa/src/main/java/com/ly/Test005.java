package com.ly;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test005 {
	//public static String sendCPICPostRequest(String requestUrl, String cookStr, String licenceNo, String referer, String host){
	public static String sendCPICPostRequest(String requestUrl){
	    //String payload = "{\"meta\":{},\"redata\":{\"ecarvo\":{\"plateNo\":\"" + licenceNo + "\",\"carVIN\":\"\",\"plateColor\":\"1\"}}}";
	    String payload = "{\"body\":\"zg4XMXrLyE55vLav0lnPpI1jQE0MczPCjNKf4lWc2MmydAF%2FrOURhKnmu7lEkLfsmNYZejXdcS5Nin5LwecABpVKvHCWeNcRaHb%2BOb1F0LMHoMqNVPKQFs2zozjPSW%2Bp6N5Z5YIEhrsF7Jb6ereIBY1MFWA1hCtBHQpk67TxTMkelU%2FD9%2FYJO8DQLldwUBg69%2FhQeAbH8eKybWomWppMEJ29DlhEZwgD3DsVq3Ij5VeVvLRtJpK7NpBuHP%2BiAHXVB0FpN46rT7e0sNrnoITgede1g%2BM39hBTdhSNWjPb7iR6ddPOvidCHiXiRamSAUula2iGAj9%2B61%2FOBtqaXTKjdQ%3D%3D\",\"spartamsg\":\"eyJzZGt2ZXJzaW9uIjoiMS4zLjAiLCJkaWQiOiJjMGY4MTFkMGRiOGE0YzgyNzcxMjNiNDllYTBlNjE3YSIsInVzZXJfYWdlbnQiOiJNb3ppbGxhLzUuMCAoV2luZG93cyBOVCA2LjE7IFdPVzY0KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvNzEuMC4zNTc4Ljk4IFNhZmFyaS81MzcuMzYiLCJyZWZlcmVyIjoiaHR0cHM6Ly9zdG9jay5waW5nYW4uY29tLyIsImd1aWQiOiI0YzhkOGMxOC1iMWFmLTRjNzgtNmEyNS03YTc3YThmOGE3N2MiLCJvcyI6IlcifQ==\",\"encrypt\":true}";
	    
	    //http://www.runoob.com/java/net-url-header.html
	    //https://login.stock.pingan.com/loginservice/setCookie?encryptedToken=Fros7H5ZHSEs9tjMqUUWxwPj1b6efSKvjdigMSjIpoedfqF3klEwD7rPnhcDYrDSIY5ha9fan%2BRmldNe5PEIfzLm%2F%2FQ2Vho95D9Q%2Fm%2Fhrgs10GsIdGvtK24Zij9HepC%2BNrtrV%2BHg1iMwq%2B3r2j7adQ7VR8xz3kB4vb1Vlf2WnSYkfnoO%2FMcOHv42Bv6ohd0TsnHQXSad7bRHwb%2Fci9qkNIwy8qdiaq7yWok7tQ7qpvayuMf4Ie3elrgwk7sxp0oGbyQZZjAu3bOrlrraijzarlCeUWd5F4Ah%2FPa7kvli%2Bc5WjrW0q%2BBnr3%2F6vfKG9Ed3Ni9Az%2Bno77XrpQtswHDrJw%3D%3D&appChannel=LOGIN&unionId=aae15dba1c2b4d5faceaf303e92428a9&callBack=asyncCallback0&_=1547716559975
	    //https://login.stock.pingan.com/loginservice/setCookie?encryptedToken=Fros7H5ZHSEs9tjMqUUWxwPj1b6efSKvjdigMSjIpoedfqF3klEwD7rPnhcDYrDSIY5ha9fan%2BRmldNe5PEIfzLm%2F%2FQ2Vho95D9Q%2Fm%2Fhrgs10GsIdGvtK24Zij9HepC%2BNrtrV%2BHg1iMwq%2B3r2j7adQ7VR8xz3kB4vb1Vlf2WnSYkfnoO%2FMcOHv42Bv6ohd0TsnHQXSad7bRHwb%2Fci9qkNIwy8qdiaq7yWok7tQ7qpvayuMf4Ie3elrgwk7sxp0oGbyQZZjAu3bOrlrraijzarlCeUWd5F4Ah%2FPa7kvli%2Bc5WjrW0q%2BBnr3%2F6vfKG9Ed3Ni9Az%2Bno77XrpQtswHDrJw%3D%3D&appChannel=LOGIN&unionId=aae15dba1c2b4d5faceaf303e92428a9&callBack=asyncCallback0&_=1547716559975
	    StringBuffer jsonString;
	    try {
	        //URL url = new URL(requestUrl);
	    	URL url  = new URL("https://login.stock.pingan.com/loginservice/login");
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
	        connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
	        connection.setRequestProperty("Cookie", "BIGipServersis-tradesso-login-com_30075_PrdPool=!RbqMozikoEQdBfC3WPIL4ff4Z7fFeZJ+SxuYjkA5tp9mNiCuwDiXiSMAJTnxdtK/LovA1FQniAgje+0=; WEBTRENDS_ID=4.0.4.21-2489478384.30715421; paid_test=4c8d8c18-b1af-4c78-6a25-7a77a8f8a77c; pa_stock_client_id=cnsz045163|24175457853257219|2ea1c510-2b6e-410c-a4a4-d33a5ece8c53; ps_login_captcha_client_id=487768d5468646a0a2066607913e40ab; WT-FPC=id=4.0.4.21-2489478384.30715421:lv=1547701162887:ss=1547699531678:fs=1547699531678:pn=8:vn=1");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
	        connection.setRequestProperty("Connection", "keep-alive");
	        connection.setRequestProperty("Host", "login.stock.pingan.com");
	        connection.setRequestProperty("Origin", "login.stock.pingan.com");
	        connection.setRequestProperty("Pragma", "no-cache");
	        connection.setRequestProperty("Referer", "https://login.stock.pingan.com/login/index_pc.html?referUrl=https%3A%2F%2Fstock.pingan.com%2F&kbChannel=J&accountType=1|2|3|4");
	        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
	        connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");

	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	        writer.write(payload);
	        writer.close();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	            jsonString.append(line);
	        }
	        br.close();
	        connection.disconnect();
	    } catch (Exception e) {
	        throw new RuntimeException(e.getMessage());
	 }
	    System.out.println(jsonString.toString());
	 return jsonString.toString();
	}
	
	public static void main(String[] args) {
		sendCPICPostRequest(null);
	}
}
