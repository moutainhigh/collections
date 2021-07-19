package cn.gwssi.datachange.msg_push.bus;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.gwssi.quartz.inter.JobServer;
import cn.gwssi.resource.KeysUtil;

public class Test {

	public static void main(String[] args) {
		try {
			JobServer jobServer =(JobServer) Class.forName("cn.gwssi.datachange.msg_push.bus.PushMsgWithFtpJob").newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//"*/10 * HH dd MM ? yyyy",new Date(),
		SimpleDateFormat sdf = new SimpleDateFormat("*/10 * 32 12 12 3 2222");
		String cron=sdf.format(new Date());
		System.out.println(cron);
		
		try {
			System.out.println(KeysUtil.md5Encrypt("hello"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
