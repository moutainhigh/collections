package com.gwssi.dao;

import java.util.Map;

public interface EPb_baseinfoDao {

	/**
	 * 个体管辖区域为空数量
	 * 
	 * @return
	 */
	public Integer data02(Map map);

	/**
	 * 个体行业类型为空数量
	 * 
	 * @return
	 */
	public Integer data05(Map map);

	/**
	 * 个体中状态为吊销的户数
	 * 
	 * @return
	 */
	public Integer data09(Map map);

	/**
	 * 个体中状态为吊销并且吊销日期不为空的户数
	 */
	public Integer data10(Map map);

	/**
	 * 个体期末实有户数
	 * 
	 * @return
	 */
	public Integer data15(Map map);

	/**
	 * 个体本期登记户数
	 * 
	 * @return
	 */
	public Integer data16(Map map);

	/**
	 * 个体本期吊销户数
	 * 
	 * @return
	 */
	public Integer data17(Map map);

	/**
	 * 个体本期注销户数
	 * 
	 * @return
	 */
	public Integer data18(Map map);

}
