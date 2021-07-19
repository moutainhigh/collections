package com.gwssi.ebaic.mobile.api;

import com.gwssi.rodimus.rpc.ParameterName;

/**
 * 
 * <h2>电子营业执照接口</h2>
 * 
 * @author 祥乾 刘
 *
 */
public interface ElicCertService {
	/**
	 * <h3>获取电子营业执照</h3>
	 * 
	 * @param uniScid 统一社会信息代码
	 * @return fileId 文件唯一标识
	 */
	public String getCertFileId(@ParameterName(value="uniScid")String uniScid);
	
}
