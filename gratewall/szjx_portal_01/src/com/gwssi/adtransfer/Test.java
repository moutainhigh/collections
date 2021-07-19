package com.gwssi.adtransfer;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.gwssi.ad.AdDao;

public class Test {

	public static void main(String[] args) throws Exception {

		// CN=樊丽华,OU=2010医疗器械生产安全监管处,OU=用户,DC=szaic,DC=gov,DC=cn
		// CN=樊丽华,OU=药品稽查处,OU=市市场稽查局,OU=委直属单位,OU=市场监管委,DC=szaic,DC=gov,DC=cn
		// String oldOu =
		// "CN=樊丽华,OU=2010医疗器械生产安全监管处,OU=用户,DC=szaic,DC=gov,DC=cn";
		// String newOu =
		// "CN=樊丽华,OU=药品稽查处,OU=市市场稽查局,OU=委直属单位,OU=市场监管委,DC=szaic,DC=gov,DC=cn";

		// AdDao ad = new AdDao();
		// ad.change(oldOu, newOu);
		BaseDao b = new BaseDao();
		List userList = b.conOracle();
		int count = 1;
		for (int i = 0; i < userList.size(); i++) {
			Map userMap = (Map) userList.get(i);
			String user_name = userMap.get("user_name").toString();
			String user_id = userMap.get("user_id").toString();
			String user_old_str = userMap.get("user_old_str").toString();
			String user_new_str = userMap.get("user_new_str").toString();

			System.out.println(count + "当前移动用户为：" + user_name + " id为："
					+ user_id);
			System.out.println("从： ");
			System.out.println(user_old_str);
			System.out.println("移动到： ");
			System.out.println(user_new_str);
			System.out.println();
			count++;
//			AdDao ad = new AdDao();
//			ad.change(user_old_str, user_new_str);
		}

	}
}
