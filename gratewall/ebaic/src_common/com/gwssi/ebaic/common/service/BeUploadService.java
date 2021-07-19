package com.gwssi.ebaic.common.service;

import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;

@Service("beUploadService")
public class BeUploadService {
	
	private static final String TARGET_PAGE_FILE_DIR;
    static {
    	ResourceBundle resourceBundle=ResourceBundle.getBundle("torch");
    	TARGET_PAGE_FILE_DIR=resourceBundle.getString("project.filePath");
    }
    /**
     * 检查是否生成过文书
     * @return
     */
    public String docState(String gid){
    	if(StringUtils.isBlank(gid)){
			throw new EBaicException("无效的业务标识");
		}
		String  sql = "select to_char(timestamp,'yyyy-MM-dd') as timestamp  from be_wk_doc where gid=?";
    	String date = DaoUtil.getInstance().queryForOneString(sql, gid);
    	if(StringUtils.isBlank(date)){
    		StringBuffer sqlbf = new StringBuffer();
        	sqlbf.append("select count(*) from (select i.inv name, i.cer_no, i.cer_type, '1' as c")
    		.append(" from be_wk_investor i where i.inv_type in ('20', '35', '36', '91')")
    		.append("and gid = ? union all select i.inv name,i.b_lic_no cer_no,'' as cer_type,")
    		.append(" '2' as c from be_wk_investor i where i.inv_type not in ('20', '35', '36', '91')")
    		.append(" and gid = ? union all select l.le_rep_name,l.le_rep_cer_no cer_no,l.le_rep_cer_type as cer_type,")
    		.append(" '1' as c from be_wk_le_rep l where gid = ?) p")
    		.append(" left join sysmgr_identity s  on (s.cer_type=p.cer_type or p.c='2')")
    		.append(" and s.cer_no = p.cer_no and s.type = '0' where  s.flag not in ('0','1')");
    		
    		long cnt = DaoUtil.getInstance().queryForOneLong(sqlbf.toString(), gid,gid,gid);
    		if(cnt==0){
    			return "ok";
    		}else{
    			return "";
    		}
    	}else{
    		return date;
    	}
    	
    }
    public String getUploadStep(String gid){
    	return  DaoUtil.getInstance().queryForOneString("select nvl(upload_step,'1') as upload_step  from be_wk_requisition where gid=?", gid);
    }
    public void setUploadStep(String gid,String step){
    	DaoUtil.getInstance().execute("update be_wk_requisition set upload_step=? where gid=?", step,gid);
    	
    }
    public Map<String,Object> getCustomDoc(String gid){
    	return DaoUtil.getInstance().queryForRow("select f_id,file_id,ref_text from be_wk_upload_file where category_id='custom_doc' and gid =?", gid);
    }
    public String getDocTemplatePath(String gid){
    	
    	String path = TARGET_PAGE_FILE_DIR+"/file/doc/1100/自制章程模板.docx";
    	return path;
    }
}
