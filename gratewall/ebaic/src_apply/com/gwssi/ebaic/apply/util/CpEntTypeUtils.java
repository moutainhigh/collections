package com.gwssi.ebaic.apply.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.gwssi.optimus.core.exception.OptimusException;


public class CpEntTypeUtils {
	
	// 中外合資
	// 有限责任公司(中外合资)	5110 股份有限公司(中外合资、未上市)	5210股份有限公司(中外合资、上市)	5220
	static Set<String> internationalJointCapitalSet = new HashSet<String>(Arrays.asList("5110","5210","5220"));
	// 中外合作
	// 有限责任公司(中外合作)	5120
	static Set<String> internationalCollaborateSet = new HashSet<String>(Arrays.asList("5120"));
	// 外商獨資
	// 有限责任公司(外国自然人独资)	5140有限责任公司(外国法人独资)	5150有限责任公司(外国非法人经济组织独资)	5160
	static Set<String> foreignSoleSet = new HashSet<String>(Arrays.asList("5140","5150","5160"));
	// 外商合資
	// 有限责任公司(外商合资)	5130股份有限公司(外商合资、未上市)	5230股份有限公司(外商合资、上市)	5240
	static Set<String> foreignJointCapitalSet = new HashSet<String>(Arrays.asList("5130","5230","5240"));
	
	/**
	 * 中外合資
	 * @param entType
	 * @return
	 * @throws OptimusException
	 */
	public static boolean isInternationalJointCapital(String entType) throws OptimusException{
		return internationalJointCapitalSet.contains(entType);
	}
	
	/**
	 * 中外合作。
	 * @param entType
	 * @return
	 * @throws OptimusException
	 */
	public static boolean isInternationalCollaborate(String entType) throws OptimusException{
		return internationalCollaborateSet.contains(entType);
	}
	
	/**
	 * 外商獨資。
	 * @param entType
	 * @return
	 * @throws OptimusException
	 */
	public static boolean isForeignSole(String entType) throws OptimusException{
		return foreignSoleSet.contains(entType);
	}
	
	/**
	 * 外商合資。
	 * @param entType
	 * @return
	 * @throws OptimusException
	 */
	public static boolean isForeignJointCapital(String entType) throws OptimusException{
		return foreignJointCapitalSet.contains(entType);
	}
	
	
	/**
	 * 是否股份公司。
	 * @param catId
	 * @return
	 * @throws OptimusException 
	 */
	public static boolean isGf(int catId) throws OptimusException {
		boolean ret = isGf(catId + "");
		return ret;
	}
	
	/**
	 * 是否股份公司。
	 * @param catId
	 * @return
	 * @throws OptimusException 
	 */
	public static boolean isGf(String catId) throws OptimusException {
		if("1200".equals(catId) || "5200".equals(catId) ){
			return true ;
		}else if ("1100".equals(catId) || "1110".equals(catId) || "2000".equals(catId)|| "5100".equals(catId)|| "5810".equals(catId)){
			return false ;
		}
		throw new OptimusException("非法的企业分类编号 ："+catId);
	}
	
	/**
	 * 是否外资公司。
	 * @param catId
	 * @return
	 * @throws OptimusException 
	 */
	public static boolean isForeignEnt(int catId) throws OptimusException {
		boolean ret = isForeignEnt(catId + "");
		return ret;
	}
	/**
	 * 判断是否是外资企业。
	 * @param catId
	 * @return
	 * @throws OptimusException
	 */
	public static boolean isForeignEnt(String catId) throws OptimusException{
		if("5100".equals(catId) || "5200".equals(catId) || "5810".equals(catId)){
			return true ;
		}else if ("1100".equals(catId) || "1200".equals(catId) || "1110".equals(catId) || "2000".equals(catId)){
			return false ;
		}else{
			return false;
		}
		//throw new OptimusException("非法的企业分类编号 ："+catId);
	}
	
	/**
	 * 是否一人有限责任公司
	 * @param entType 小类
	 * @return
	 */
	public static boolean isOnePerson(String entType) {
		if(StringUtils.isEmpty(entType)){
			return false;
		}
		String entTypes = "1150_1151_1152";
		return entTypes.contains(entType);
	}
	/**
	 * 判断是否分公司。
	 * @param catId
	 * @return
	 * @throws OptimusException
	 */
	public static boolean isBranchEnt(String catId) throws OptimusException{
		if("5810".equals(catId)|| "2000".equals(catId)){
			return true ;
		}else if ("5100".equals(catId) || "5200".equals(catId) || "1100".equals(catId) || "1200".equals(catId) || "1110".equals(catId) ){
			return false ;
		}
		throw new OptimusException("非法的企业分类编号 ："+catId);
	}
	/**
	 * 是否特殊区域。
	 * @param specArea
	 * @return
	 */
	public static boolean isSpecArea(String specArea) {
		//return !"0".equals(specArea);
		if(specArea==null || "".equals(specArea)){
			return false;
		}else{
			return !"0".equals(specArea);
		}
	}

	/**
	 * 是否市国资委投资，或者是市国资委投资的公司再投资占50%以上。
	 * @param sgzwFlag
	 * @return
	 */
	public static boolean isSgzwFlag(String sgzwFlag) {
		return "1".equals(sgzwFlag);
	}
	
	/**
	 * 是否特殊行业。
	 * @param entBaseInfo
	 * @return
	 */
	public static boolean isSpecIndustry(String specIndustry) {
		//return !"0".equals(specIndustry);
		if(specIndustry==null||"".equals(specIndustry)){
			return false;
		}else{
			return !"0".equals(specIndustry);
		}
	}

	
	
}