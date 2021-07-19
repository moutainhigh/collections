package com.gwssi.dao;

import java.util.List;
import java.util.Map;

import com.gwssi.pojo.EBaseinfo;

public interface EBaseinfoDao {

	/**
	 * 企业管辖区域为空数量
	 * 
	 * @return
	 */
	public Integer data01(Map map);

	/**
	 * 不存在的企业类型数据
	 * 
	 * @return
	 */
	public List<EBaseinfo> data03(Map map);
	
	
	 public Integer data03Total(Map paramMap);
	 
	// public	 void deleteDateById(List<EBaseinfo> deleteList);
	 
	 
	

	/**
	 * 企业行业类型为空数量
	 * 
	 * @return
	 */
	public Integer data04(Map map);

	/**
	 * 企业注册资本排序
	 * 
	 * @return
	 */
	public List<EBaseinfo> data06(Map map);

	public  Integer data06Total(Map paramMap);
	
	/**
	 * 企业中状态为吊销的户数
	 * 
	 * @return
	 */
	public Integer data07(Map map);

	/**
	 * 企业中状态为吊销并且吊销日期不为空的户数
	 * 
	 * @return
	 */
	public Integer data08(Map map);

	/**
	 * 企业期末实有户数
	 * 
	 * @param map
	 * @return
	 */
	public Integer data11(Map map);

	/**
	 * 企业本期吊销户数
	 * 
	 * @param map
	 * @return
	 */
	public Integer data13(Map map);

	/**
	 * 企业本期注销户数
	 * 
	 * @param map
	 * @return
	 */
	public Integer data14(Map map);

	/**
	 * 本期登记纯内资企业户数
	 * 
	 * @param map
	 * @return
	 */
	public Integer data19(Map map);
	
	
	
	
	// 非法6100企业列表 
	public Integer data20Total(Map paramMap);

	public List<EBaseinfo> data20(Map map);

	
	// 非法6299企业列表 
	public Integer data21Total(Map paramMap);

	public List<EBaseinfo> data21(Map map);

	
	
	
	//删除
	
	 public	 void delete03();
	 public	 void delete6100();
	 public	 void delete6299();
}
