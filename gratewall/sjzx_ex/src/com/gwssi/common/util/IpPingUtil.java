package com.gwssi.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

public class IpPingUtil {
	
	/**
	 * ����IP�����Ƿ������ͨ
	 * @param ip
	 * @return
	 */
	protected static Logger log = TxnLogger.getLogger(IpPingUtil.class.getName());
	
	public static boolean ping(String ip) {
		boolean flag;
		Properties props = System.getProperties();
		String osName = props.getProperty("os.name");
		BufferedReader br = null;
		Process p = null;
		//������֤һ���ǲ�����Ч��ip��ַ
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		if(ip.matches(regex)){
			try {
				String cmd = "";
				log.info("-----osName:---"+osName);
				if (osName.indexOf("Windows") != -1) {
					cmd = " -n 4";
				} else {
					cmd = " -c 4";
				}
				log.info("-----ping-cmd:---"+cmd);
				p = Runtime.getRuntime().exec("ping " + ip + cmd);
				log.info("-----ping-p:---"+p.getInputStream());
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					log.info("line = "+line);
					sb.append(line);
				}
				log.info("---------nping result:-------> " + sb.toString());
				int result = getLossRate(sb.toString());
				if (result >= 75) {
					flag = false;
				} else {
					flag = true;
				}
				log.info("ip: "+ip + " �����ʣ�"+result+"%");
				p.destroy();
				return flag;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}else{
			log.info("�Ƿ�Ip:"+ip);
			return false;
		}
	}

	/**
	 * ���ݷ��ؽ�� ���ض�����
	 * 
	 * @param temp
	 * @return
	 */
	public static int getLossRate(String temp) {
		Pattern p = Pattern.compile("\\d{0,3}%");
		Matcher mp = p.matcher(temp);
		int s = 100;
		if(mp.find()){
			String mOne = mp.group();
			log.info("find = "+mOne);
			mOne = mOne.replace("%", "");
			System.out.println(mOne);
			try {
				s = Integer.parseInt(mOne);
			} catch (Exception e) {
				log.info("������s="+s);
			}
		}else{
			System.out.println("2");
			log.info("δƥ��ɹ�");
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println("���:"+IpPingUtil.ping("160.99.1.4"));
		IpPingUtil.getLossRate("4 packets transmitted, 4 packets received, 0% packet loss");
	}
}
