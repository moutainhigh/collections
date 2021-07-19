package com.gwssi.rodimus.indentity;

import org.apache.commons.lang.StringUtils;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.dao.IdentityDaoUtil;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.indentity.domain.IdentityCardBO;
import com.gwssi.rodimus.indentity.support.IdentityService;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 
 * @author liuhailong
 */
public class IdentityCardUtil {
	/**
	 * 身份证核查。
	 * 
	 * identity表（缓存经过身份认证的）。
	 * 
	 * sfz库中，id_card_info表（缓存调用过公安部接口的）。
	 * 
	 * 公安部接口调用。
	 * 
	 * @param name
	 * @param cerNo
	 * @return
	 * @throws OptimusException
	 */
	public static boolean check(String name, String cerNo) {
		if(StringUtil.isBlank(name)){
			throw new RodimusException("姓名不能为空。");
		}
		if(StringUtil.isBlank(cerNo)){
			throw new RodimusException("身份证号码不能为空。");
		}
		// 查询 sysmgr_identity 库
		String sql = "select count(1) as cnt from sysmgr_identity d where d.type='2' and d.cer_type='1' and d.flag='1' and d.name=? and d.cer_no = ?";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, name,cerNo);
		if(cnt>0){
			return true;
		}
		// 查询身份证库
		sql = "select count(1) as cnt from id_card_info i where i.id_number = ? and i.name = ?";
		cnt = IdentityDaoUtil.getInstance().queryForOneLong(sql, cerNo,name);
		if(cnt>0){
			return true;
		}
		// 掉公安部接口
		IdentityCardBO bo = getIdentityCardInfo(name,cerNo);
		if(bo!=null && StringUtil.isNotBlank(bo.getSex())){
			return true;
		}
		return false;
	}

	/**
	 * 通过公安部接口校验姓名和身份证号码是否匹配。
	 * 
	 * @author lixibo
	 * @param name
	 * @param cerNo
	 * @return
	 * @throws OptimusException
	 */
	public static IdentityCardBO getIdentityCardInfo(String name, String cerNo)  {
		// 改为先查询 id_card_info 库，库中没有数据，才调用公安部接口
		IdentityCardBO ret = getIdentityCardInfoByDb(name,cerNo);
		if(ret!=null){
			return ret;
		}
		ret = getIdentityCardInfoByWebService(name,cerNo);
		return ret;
	}
	
	static IdentityService identityService;
	
	public void setIdentityService(IdentityService identityService){
		IdentityCardUtil.identityService = identityService;
	}
	
	/**
	 * 调用公安接口。
	 * 
	 * @param name
	 * @param cerNo
	 * @return
	 */
	private static IdentityCardBO getIdentityCardInfoByWebService(String name, String cerNo) {
		IdentityCardBO ret = identityService.validate(name, cerNo);
		return ret;
	}

	public static IdentityCardBO getIdentityCardInfoByDb(String name, String cerNo) {
		String sql = "select i.id_card_info_id,i.id_number as cer_no,i.name,i.photo as pic_data,i.houseadd,i.native,i.reg_org,i.op_from,i.op_to,i.save_to,i.gender as sex,i.nation as folk,i.birthday from id_card_info i where i.id_number = ? and i.name = ?";
		IdentityCardBO ret = IdentityDaoUtil.getInstance().queryForRowBo(sql, IdentityCardBO.class, cerNo,name);
		return ret;
	}

	
	public static String getIdCardPictureFileId(String cerNo){
		String fileid = MD5Util.MD5Encode("身份证号："+cerNo);
		return fileid;
	}
	

	public static boolean getAppIdentityState(String name,String cerNo){
		if(StringUtils.isBlank(name) || StringUtils.isBlank(cerNo)){
			throw new RodimusException("验证身份失败，姓名或证件号码为空");
		}
		String sql = "select count(p.type) from sysmgr_identity i,sysmgr_identity_picture p where i.identity_id=p.identity_id and i.type='1' and cer_no=? and name=? and cer_type='1'";
		Long n = DaoUtil.getInstance().queryForOneLong(sql, cerNo,name);
		return (n==3);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 15位身份证号码转为18位。
	 * 
	 * @param century
	 *            19xx 年用 19，20xx 年用 20
	 * @param idCardNo15
	 *            待转换的 15 位身份证号码
	 * @return
	 */
	public static String from15to18(int century, String idCardNo15) {
		String centuryStr = "" + century;
		if (century < 0 || centuryStr.length() != 2)
			throw new IllegalArgumentException("世纪数无效！应该是两位的正整数。");
		if (!isIdCardNo(idCardNo15)) {
			throw new IllegalArgumentException("身份证号格式不正确！");
		}

		int[] weight = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };

		// 通过加入世纪码, 变成 17 为的新号码本体.
		String newNoBody = idCardNo15.substring(0, 6) + centuryStr + idCardNo15.substring(6);

		// 算最后一位校验码
		int checkSum = 0;
		for (int i = 0; i < 17; i++) {
			int ai = Integer.parseInt("" + newNoBody.charAt(i));
			checkSum = checkSum + ai * weight[i];
		}
		int checkNum = checkSum % 11;
		String checkChar = null;
		switch (checkNum) {
		case 0:
			checkChar = "1";
			break;
		case 1:
			checkChar = "0";
			break;
		case 2:
			checkChar = "X";
			break;
		default:
			checkChar = "" + (12 - checkNum);
		}
		return newNoBody + checkChar;
	}

	/**
	 * 18位身份证号码转为15位。
	 * 
	 * @param idCardNo18
	 * @return
	 */
	public static String from18to15(String idCardNo18) {
		if (StringUtils.isEmpty(idCardNo18)) {
			return "";
		}
		idCardNo18 = idCardNo18.trim();
		if (idCardNo18.length() != 18) {
			return idCardNo18;
		}
		if (!isIdCardNo(idCardNo18)) {
			throw new IllegalArgumentException("身份证号格式不正确！");
		}
		return idCardNo18.substring(0, 6) + idCardNo18.substring(8, 17);
	}

	/**
	 * 判断给定的字符串是不是符合身份证号的要求 15位都是数字 18位最后一位可能是大写字母X
	 * 
	 * @param cardNo
	 * @return
	 */
	private static boolean isIdCardNo(String cardNo) {

		if (StringUtils.isBlank(cardNo)) {
			return false;
		}

		int len = cardNo.length();
		if (len != 15 && len != 18) {
			return false;
		}

		for (int i = 0; i < len; i++) {
			String number = cardNo.charAt(i) + "";
			try {
				Integer.parseInt(number);
			} catch (NumberFormatException e) {
				if (i != 17) {
					return false;
				}
				if (i == 17 && !"X".equals(number)) {
					return false;
				}
			}
		}
		return true;
	}
}
