package com.gwssi.rodimus.pic;

import java.awt.Font;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.ApproveDaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.pic.domain.ImageWidget;
import com.gwssi.rodimus.pic.domain.TextWidget;
import com.gwssi.rodimus.util.StringUtil;

public class CertPicUtil {
	
	/**
	 * 返回图片模板存放目录。
	 * 
	 * @return
	 */
	protected static String getTemplatePath() {
		String rootPath = ConfigUtil.get("file.rootPath")+"cert"+File.separator;
		//String rootPath = "H:\\CertPrint\\";
		
		String ret = rootPath +"template"+File.separator;
		return ret;
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	/**
	 * 获得路径目标路径，不包含文件名。
	 * 
	 * @param entInfo
	 * @return
	 */
	protected static String getTargetPath(Map<String, Object> entInfo) {
		
		String rootPath = ConfigUtil.get("file.rootPath")+"cert"+File.separator;
		//String rootPath = "H:\\CertPrint\\";
		
		if(entInfo==null || entInfo.isEmpty()){
			String ret = rootPath + "000000000" + File.separator + sdf.format(new Date());
			return ret;
		}
		
		String regOrg = StringUtil.safe2String(entInfo.get("regOrg"));
		if(StringUtil.isBlank(regOrg)){
			regOrg = "000000000";
		}
		String regEntDate = StringUtil.safe2String(entInfo.get("regEndDate"));
		if(StringUtil.isBlank(regEntDate)){
			regEntDate = sdf.format(new Date());
		}
		String ret = rootPath + regOrg+
				File.separator + regEntDate + File.separator ;
		return ret;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 生成电子营业执照照片。
	 * 
	 * @param entInfo
	 * @return 文件路径
	 */
	public static String genCertPicture(String regNo) {
		if(StringUtil.isBlank(regNo)){
			throw new EBaicException("参数regNo不能为空。");
		}
		Map<String,Object> entInfo = CertPicUtil.getEntInfo(regNo);
		if(entInfo==null || entInfo.isEmpty()){
			throw new EBaicException(String.format("根据企业注册号(%s)没有找到注册企业。", regNo));
		}
		// 获取目标路径
		String phyFileDir = CertPicUtil.getTargetPath(entInfo);
        // 获取目标文件
		String phyFilePath = phyFileDir + regNo + ".jpg";
		File phyFile = new File(phyFilePath);
		// 如果已经存在，则直接返回
    	if(phyFile.exists()){
        	return phyFilePath;
        }
    	File phyFileDirObj = new File(phyFileDir);
        if(!phyFileDirObj.exists()){
        	phyFileDirObj.mkdirs();
        }

		// 图片路径
       String picTemplatePath = getTemplatePath();
       entInfo.put("picTemplatePath", picTemplatePath);
       entInfo.put("phyFileDir", phyFileDir);
        
        // 获取背景图片
        String bgImgPath = picTemplatePath +"AA_11.jpg";   
        File bg = new File(bgImgPath);
        // 获取文字内容
        List<TextWidget> strings = getTextInfo(entInfo);
        // 获取图像内容（二维码和签章）
        List<ImageWidget> images = getImageInfo(entInfo);
        // 生成执照图片
        PicUtil.genCertPicture(bg, strings, images, phyFilePath);
        // 返回执照图片物理路径
        return phyFilePath;
	}
	

	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询企业信息。
	 * 
	 * @param regNo
	 * @return
	 */
	protected static Map<String, Object> getEntInfo(String regNo) {
		String sql = " select t.ent_id as rs_ent_id,t.reg_no,t.reg_org,to_char(t.reg_end_date,'yyyyMMdd') as reg_end_date,t.uni_scid,t.ent_name,dm.code_name as ent_type,t.op_loc,e.name,t.reg_cap,to_char(t.est_date, 'yyyy\"年\"mm\"月\"dd\"日\"') as est_date, "+
				 	 " to_char(t.op_from, 'yyyy\"年\"mm\"月\"dd\"日\"') as op_from,to_char(t.op_to, 'yyyy\"年\"mm\"月\"dd\"日\"') as op_to,t.business_scope, "+
				 	 " to_char(t.approve_date,'yyyy') as app_date_year,to_char(t.approve_date,'mm') as app_date_month,to_char(t.approve_date,'dd') as app_date_day "+
				 	 " from cp_rs_ent t,cp_rs_entmember e,sysmgr_cvalue dm "+
				 	 " where t.ent_id = e.ent_id and e.le_rep_sign = '1' "+ 
				 	 " and dm.type_id_fk = 'CA16' and dm.code_value = t.ent_type "+
				 	 " and (t.reg_no = ? or t.uni_scid = ?) ";
		Map<String, Object> ret = ApproveDaoUtil.getInstance().queryForRow(sql, regNo, regNo);
		if(ret==null){
			ret = new HashMap<String,Object>();
		}
		return ret;
	}
	
	/**
	 *  产生生成营业执照的图片信息
	 * @param entInfo
	 * @return
	 */
	protected static List<ImageWidget> getImageInfo(Map<String, Object> entInfo) {
		// 定义返回值
		List<ImageWidget> images = new ArrayList<ImageWidget>();
		// 如果传入参数为空，则返回空集合
		if (entInfo == null || entInfo.isEmpty()) {
			return images;
		}
		String picTemplatePath = StringUtil.safe2String(entInfo.get("picTemplatePath"));// 模板路径
		String phyFileDir = StringUtil.safe2String(entInfo.get("phyFileDir")); // 目标路径
		String regNo = StringUtil.safe2String(entInfo.get("regNo"));
		
		// 生成二维码
		String rsEntId = StringUtil.safe2String(entInfo.get("rsEntId"));
		String barContext = "http://qyxy.baic.gov.cn/wap/wap/creditWapAction!qr.dhtml?id=" + rsEntId;
		String barCodePicPath = phyFileDir + regNo + "_Bar_Code.jpg"; 
		BarCodeUtil.genbarCode(barContext, barCodePicPath, 320, 320);
		
		// 二维码 
		ImageWidget barCodePic = new ImageWidget(450,2250,1.0f,barCodePicPath); 
        images.add(barCodePic);
        
		// 印章
		String stampPicPath = picTemplatePath + "Lic_Stamp_Hd.png";
		ImageWidget stampPic = new ImageWidget(1600, 2600, 1.0f, stampPicPath);
		images.add(stampPic);
		// 返回
		return images;
	}
	
	/**
	 *  生成营业执照的文字信息
	 * @param entInfo
	 * @return
	 */
	public static List<TextWidget> getTextInfo(Map<String, Object> entInfo){
		List<TextWidget> strings = new ArrayList<TextWidget>();
		strings = getTextInfo_staticText(strings);
		strings = getTextInfo_entInfo(strings,entInfo);
		return strings;
		
	}
	
	
	/**
	 * 配置文字模板的基本内容（不变）
	 * @param strings
	 */
	protected static List<TextWidget> getTextInfo_staticText(List<TextWidget> strings){
		
		Font heiFont = new Font("宋体",Font.BOLD,50);
		
		strings.add(new TextWidget(1050,1050,"统一社会信用代码",heiFont));
        
        int titleX = 400;
        int titleStartY = 1050 ;
        int titleLineHeight = 110 ;
        int lineCnt = 0;
        
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt),"名          称",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt),"类          型",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt),"住          所",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt),"法 定 代 表 人",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt),"注  册  资  本",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt),"成  立  日  期",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt),"营  业  期  限",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt),"经  营  范  围",heiFont));
        
        Font tipsFont = new Font("宋体",Font.PLAIN,40);
        strings.add(new TextWidget(450, 2600, "在线扫码获取详细信息", new Font("宋体",Font.PLAIN,32)));
        strings.add(new TextWidget(447, 2950, "提示：每年1月1日至6月30日通过企业信用公示系统", tipsFont));
        strings.add(new TextWidget(370, 3010, "报送上一年度年度报告并公示", tipsFont));
        strings.add(new TextWidget(600, 3175, "qyxy.baic.gov.cn", tipsFont));
        
		return strings;
	}
	
	/**
	 * 配置文字模板的动态内容
	 * @param strings
	 */
	protected static List<TextWidget> getTextInfo_entInfo(List<TextWidget> strings,Map<String, Object> entInfo){
		Font font = new Font("宋体",Font.BOLD,46);

		String uniScid = StringUtil.safe2String(entInfo.get("uniScid"));
		String entName = StringUtil.safe2String(entInfo.get("entName"));
		String entType = StringUtil.safe2String(entInfo.get("entType"));
		String opLoc = StringUtil.safe2String(entInfo.get("opLoc"));
		String name = StringUtil.safe2String(entInfo.get("name"));
		String regCap = StringUtil.safe2String(entInfo.get("regCap"));
		String estDate = StringUtil.safe2String(entInfo.get("estDate"));
		String opFrom = StringUtil.safe2String(entInfo.get("opFrom"));
		String opTo = StringUtil.safe2String(entInfo.get("opTo"));
		String businessScope = StringUtil.safe2String(entInfo.get("businessScope"));
		String appDateYear = StringUtil.safe2String(entInfo.get("appDateYear"));
		String appDateMonth = StringUtil.safe2String(entInfo.get("appDateMonth"));
		String appDateDay = StringUtil.safe2String(entInfo.get("appDateDay"));
		
        strings.add(new TextWidget(1050+450,1050,uniScid,font));
        
        int titleStartY = 1050 ;
        int titleLineHeight = 110 ;
        int lineCnt = 0;
        int contentStartX = 860 ;
        
        //对名称的处理
        if(StringUtil.isNotBlank(entName) && entName.length() > 24){
        	strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(++lineCnt),entName.substring(0,24),font));
        	strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(lineCnt)+50,entName.substring(24,entName.length()),font));
        }else{
        	strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(++lineCnt),entName,font));
        }
        
        strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(++lineCnt),entType,font));
        
        //对住所的处理
        if(StringUtil.isNotBlank(opLoc) && opLoc.length() > 24){
        	strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(++lineCnt),opLoc.substring(0,24),font));
        	strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(lineCnt)+50,opLoc.substring(24,opLoc.length()),font));
        }else{
        	strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(++lineCnt),opLoc,font));
        }
        
        strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(++lineCnt),name,font));
        strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(++lineCnt),regCap+"万元",font));
        strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(++lineCnt),estDate,font));
        
        //对营业期限的处理
        if(StringUtil.isBlank(opTo)){
        	opTo = "长期";
        }
        strings.add(new TextWidget(contentStartX,titleStartY + titleLineHeight*(++lineCnt),opFrom + " 至 " + opTo ,font));
        
        //对经营范围的处理
        Font scopeFont = font;
        int scopeLineLimit = 24;//一行允许的字数
        int scopeLineHeight = 75;
        int scopeStartY = titleStartY + titleLineHeight*(++lineCnt);
        String context = null;
        if(StringUtil.isNotBlank(businessScope) && (businessScope.length() > scopeLineLimit)){
        	if(businessScope.length() > 260){
        		scopeFont = new Font("宋体",Font.BOLD,30);
        		scopeLineLimit = 37;
        		scopeLineHeight = 40;
        	}
        	int all = businessScope.length();
        	int num = all/scopeLineLimit;
        	int j = 0 ;
        	for(j = 0; j < num; j++){
        		context = businessScope.substring(j * scopeLineLimit, ((j + 1) * scopeLineLimit));
        		strings.add(new TextWidget(contentStartX,scopeStartY + scopeLineHeight*j,context,scopeFont));
        	}
        	if((all-num*scopeLineLimit) > 0){
        		context = businessScope.substring(num * scopeLineLimit, businessScope.length());
        		strings.add(new TextWidget(contentStartX,scopeStartY + scopeLineHeight*j,context,scopeFont));
        	}
        }else{
        	strings.add(new TextWidget(contentStartX,scopeStartY,businessScope,scopeFont));
        }
        
        //对营业执照下方的申请日期的处理
        Font erWeiMa = new Font("黑体",Font.PLAIN,55);
        strings.add(new TextWidget(1515, 2970, appDateYear, erWeiMa));
        strings.add(new TextWidget(1720, 2970, appDateMonth, erWeiMa));
        strings.add(new TextWidget(1870, 2970, appDateDay, erWeiMa));
        
        
        
		return strings;
	}
}
