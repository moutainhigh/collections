package com.gwssi.rodimus.doc.v1.core.arch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v1.DocUtil;
import com.gwssi.rodimus.doc.v2.core.util.PdfUtil;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.util.FileUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.XmlUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class ArchBuilder {
	
	/**
	 * <h3>生成元信息xml文件</h3>
	 * 
	 * 
	 * @param gid
	 * @param xmlDirPath （生成xml文件路径）
	 */
	public static void buildXml(String gid,String xmlDirPath){
		if (StringUtil.isBlank(gid)) {
			throw new RodimusException("gid不能为空。");
		}
		if(StringUtils.isBlank(xmlDirPath)){
			throw new RodimusException("xmlDirPath不能为空。");
		}
		List<Map<String,Object>> xmlContextList = createXmlContextList(gid);
		String xmlContext = XmlUtil.list2Xml("ArchiveInfo", xmlContextList, 1);
		FileUtil.createFile(xmlDirPath, xmlContext);
	}
	
	public static List<Map<String,Object>> createXmlContextList(String gid){
		List<Map<String,Object>> xmlContextList = new ArrayList<Map<String,Object>>();
		if(StringUtils.isBlank(gid)){
			return xmlContextList;
		}
		Map<String,Object> baseInfoMap = new HashMap<String, Object>();
		Map<String,Object> changeItemMap = new HashMap<String, Object>();
		Map<String,Object> fileItemMap = new HashMap<String, Object>();
		//创建基本信息
		createBaseInfo(gid,baseInfoMap);
		//创建变更项信息
		createChangeItemInfo(gid,changeItemMap);
		//创建文件目录信息
		//createFileItemInfo(gid,fileItemMap);
		createFileItemInfoFromDb(gid,fileItemMap);
		xmlContextList.add(baseInfoMap);
		xmlContextList.add(changeItemMap);
		xmlContextList.add(fileItemMap);
		return xmlContextList;
	}
	
	public static void createBaseInfo(String gid,Map<String,Object> result){
		Map<String, Object> baseMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(gid)){
			return;
		}
		StringBuffer sql = new StringBuffer(" select e.gid as Serice_Accept_Number,e.reg_org as Adm_Code,e.ent_id as Entityno, ");
		sql.append(" e.uni_scid as Uni_S_C_I_D,e.reg_no as Regist_I_D,e.ent_name as Company_Name, ");
		sql.append(" e.ent_type as Company_Type_Code,e.est_date as Establish_Date,e.op_loc as Address, ");
		sql.append(" (select listagg(r.le_rep_name,'、')within GROUP(order by r.le_rep_id) from ");
		sql.append(" be_wk_le_rep r where r.gid=? )as Legal_Person, e.ent_state as Company_Status_Code,");
		sql.append(" q.operation_type as Business_Type_Code,to_char(e.approve_date,'yyyy-mm-dd') as Valid_Date, ");
		sql.append(" s.reg_org as Check_Adm_Code,s.user_cert as User_Cert,s.user_sign as Sign_Info ");
		sql.append(" from be_wk_ent e left join be_wk_requisition q on q.gid = e.gid ");
		sql.append(" left join be_wk_file_sign s  on s.gid = e.gid ");
		sql.append(" where e.gid=? ");
		baseMap = DaoUtil.getInstance().queryForRow(sql.toString(), gid,gid);
		result.put("BaseInfo", baseMap);
	}
	
	public static void createChangeItemInfo(String gid,Map<String,Object> result){
		Map<String, Object> changeItemMap = null;
		if(StringUtils.isBlank(gid)){
			return;
		}
		result.put("AlterationItemList", changeItemMap);
	}
	

	/**
	 * 解析PDF文件，获取章节信息。
	 * 
	 * @param gid
	 * @param result
	 */
	public static List<Map<String, Object>> parseChapterInfo(String gid){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("“gid”不能为空。 ");
		}

		String sql = "select * from be_wk_doc d where d.gid = ?";
		Map<String, Object> beWkDocBo = DaoUtil.getInstance().queryForRow(sql, gid);
		if(beWkDocBo==null || beWkDocBo.isEmpty()){
			throw new RuntimeException("未找到文件记录。 ");
		}
		
		List<Map<String, Object>> fileItemListForXml = new ArrayList<Map<String,Object>>();
		
		String dir = StringUtil.safe2String(beWkDocBo.get("url"));
		String fileRootPath = ConfigUtil.get("file.rootPath");//统一文件根目录
		String rootPath = ConfigUtil.get("doc.rootpath.pdf");//PDF根目录
		String fullPdfFilePath = fileRootPath+rootPath + dir + DocUtil.PDF_NAME;//PDF文件
		
		// 解析章节页码
		List<Map<String, Object>> fileItemList = PdfUtil.parseChapterPageNo(fullPdfFilePath,0);
		
		String InnerfileTitle;
		int BeginPageNum,EndPageNum;
		for (Map<String, Object> map : fileItemList) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			InnerfileTitle = StringUtil.safe2String(map.get("chapName")) ;
			BeginPageNum = (Integer) map.get("logicPageNo");
			EndPageNum = BeginPageNum+(Integer) map.get("chapPageCnt")-1;
			itemMap.put("InnerfileTitle", InnerfileTitle);
			itemMap.put("BeginPageNum",new BigDecimal(BeginPageNum));
			itemMap.put("EndPageNum", new BigDecimal(EndPageNum));
			Map<String, Object> xmlMap = new HashMap<String, Object>();
			xmlMap.put("InnerfileItem", itemMap);
			fileItemListForXml.add(xmlMap);
		}
		return fileItemListForXml;
	}
	
	public static void createFileItemInfoFromDb(String gid,Map<String,Object> result){
		if(StringUtils.isBlank(gid)){
			throw new RuntimeException("“gid”不能为空。 ");
		}
		String sql = "select directory from be_wk_doc d where d.gid=?";
		String dirString = DaoUtil.getInstance().queryForOneString(sql, gid);
		if(StringUtils.isBlank(dirString)){
			throw new RuntimeException("未获取到文件目录信息");
		}
		JSONArray jrArray = JSON.parseArray(dirString);
		result.put("InnerfileItemList", jrArray);
	}
}
