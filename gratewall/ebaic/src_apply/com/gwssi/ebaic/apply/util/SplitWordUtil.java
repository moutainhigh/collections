package com.gwssi.ebaic.apply.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SplitWordUtil {

	private final static Logger logger = Logger.getLogger(SplitWordUtil.class);

	public static Map<String, String> splitAddress(String address) {
		String[] streets = new String[] { "街", "道","镇", "乡", "路" };
		String[] villages = new String[] { "村" };
		String[] nos = new String[] { "号" };
		String[] builds = new String[] { "楼" };
		// int qx_index = address.indexOf("区");
		Map<String, String> address_map = new HashMap<String, String>();
		address_map.put("domStreet", "");
		address_map.put("domVillage", "");
		address_map.put("domNo", "");
		address_map.put("domBuliding", "");
		address_map.put("domOther", "");
		logger.debug("地址去掉市和区后:" + address);
		int maxindex = getMaxIndex(address, streets);
		if (maxindex > 0 && StringUtils.isNotBlank(address)) {
			logger.debug("街道乡路:" + address.substring(0, maxindex + 1));
			address_map.put("domStreet", address.substring(0, maxindex + 1));
			address = address.substring(maxindex + 1);// 去除街道乡路
		}
		maxindex = getMaxIndex(address, villages);

		if (maxindex > 0 && StringUtils.isNotBlank(address)) {
			logger.debug("村:" + address.substring(0, maxindex + 1));
			address_map.put("domVillage", address.substring(0, maxindex + 1));
			address = address.substring(maxindex + 1);// 去除村
		}

		maxindex = getFirstIndex(address, nos);
		if (maxindex > 0 && StringUtils.isNotBlank(address)) {
			logger.debug("门牌号:" + address.substring(0, maxindex + 1));
			address_map.put("domNo", address.substring(0, maxindex + 1));
			address = address.substring(maxindex + 1);// 去除门牌号
		}

		maxindex = getFirstIndex(address, builds);
		if (maxindex >= 0 && StringUtils.isNotBlank(address)) {
			logger.debug("楼宇:" + address.substring(0, maxindex + 1));
			address_map.put("domBuliding", address.substring(0, maxindex + 1));
			address = address.substring(maxindex + 1);// 去除楼宇
		}
		if (StringUtils.isNotBlank(address)) {
			logger.debug("其他信息:" + address);
			address_map.put("domOther", address);
		}
		return address_map;
	}

	private static int getMaxIndex(String str, String[] psrs) {
		if(str==null){
			return -1;
		}
		int maxindex = -1;
		for (int i = 0; i < psrs.length; i++) {
			int index = str.lastIndexOf(psrs[i]);
			if (index > maxindex) {
				maxindex = index;
			}
		}
		return maxindex;
	}

	private static int getFirstIndex(String str, String[] psrs) {
		int maxindex = -1;
		for (int i = 0; i < psrs.length; i++) {
			int index = str.indexOf(psrs[i]);
			if (index > maxindex) {
				maxindex = index;
			}
		}
		return maxindex;
	}

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		String address = "北知春路6号(锦秋国际大厦)6号楼A06室";
		System.out.println(SplitWordUtil.splitAddress(address));
		System.out.println("耗时:" + (System.currentTimeMillis() - begin));

	}
}
