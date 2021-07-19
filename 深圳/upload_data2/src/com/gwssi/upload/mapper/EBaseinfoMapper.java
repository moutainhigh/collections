package com.gwssi.upload.mapper;

import com.gwssi.upload.pojo.EBaseinfo;

/**
 * 企业基本信息
 * @author Administrator
 *
 */
public interface EBaseinfoMapper {

	//插入数据
	public void insertEntDate(EBaseinfo eBaseInfo);
	
	//根据id获取信息
	public EBaseinfo findEBaseInfoById(String id);

}
