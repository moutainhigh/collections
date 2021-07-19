package com.gwssi.ebaic.mobile.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.mobile.api.ElicCertService;
import com.gwssi.rodimus.dao.BaseDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.RpcException;
import com.gwssi.rodimus.pic.CertPicUtil;
import com.gwssi.rodimus.util.FileUtil;
import com.gwssi.torch.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;

@Service(value="elicCertServiceImpl")
public class ElicCertServiceImpl implements ElicCertService {

	public String getCertFileId(String uniScid) {
		if(StringUtils.isBlank(uniScid)){
			throw new RpcException("社会统一信用代码不能为空。");
		}
		//查询cp_wk_file是否有记录
		String fileId = getFileId(uniScid);
		if(!StringUtils.isBlank(fileId)){
			return fileId;
		}
		//文件物理路径
		String phyFilePath = CertPicUtil.genCertPicture(uniScid);
		String srcPath = phyFilePath;
		if(StringUtils.isBlank(phyFilePath)){
			throw new RpcException("电子营业执照获取失败。");
		}
		File file = new File(phyFilePath);
		if(!file.exists()){
			throw new RpcException("电子营业执照获取失败。");
		}
		long size = file.length(); 
		phyFilePath = phyFilePath.replaceAll("\\\\","/");
		//文件统一根路径
		String rootPath = FileUtil.getRootPath();
		rootPath = rootPath.replaceAll("\\\\","/");
		String suffix = phyFilePath.substring(phyFilePath.lastIndexOf(".")+1,phyFilePath.length());
		String path = phyFilePath.substring(0,phyFilePath.lastIndexOf("/")+1);
		fileId = UUIDUtil.getUUID();
		String destPath = path + fileId +"."+ suffix;
		path = path.replace(rootPath, "");
		if(!path.startsWith("/")){
			path = "/"+path;
		}
		//生成文件
		FileUtil.copyFile(srcPath, destPath, false);
		//插入file表记录
		FileUtil.saveFile(fileId, suffix, path, "image/jpeg", size, "");
		//保存业务关联表记录
		saveRef(uniScid,fileId);
		return fileId;
	}
	
	private String getFileId(String uniScid){
		StringBuffer sql = new StringBuffer(" select f.file_id from be_wk_upload_file f ");
		sql.append(" where f.category_id='E' and f.ref_id=? order by f.timestamp desc ");
		List<String> params = new ArrayList<String>();
		params.add(uniScid);
		BaseDaoUtil dao = DaoUtil.getInstance();
		Map<String, Object> ret = dao.queryForRow(sql.toString(), params);
		if(ret==null || ret.isEmpty()){
			return "";
		}
		String fileId = StringUtil.safe2String(ret.get("fileId"));
		return fileId;
	}
	
	private void saveRef(String uniScid,String fileId){
		//删除之前的电子营业执照记录
		StringBuffer sql = new StringBuffer(" delete from be_wk_upload_file f where f.ref_id=? and f.category_id='E' ");
		BaseDaoUtil dao = DaoUtil.getInstance();
		List<String> params = new ArrayList<String>();
		params.add(uniScid);
		dao.execute(sql.toString(), params);
		//保存记录表
		sql.setLength(0);
		params.clear();
		sql.append(" insert into be_wk_upload_file(f_id,ref_id,file_id,category_id,timestamp) values(?,?,?,'E',sysdate) ");
		String fId = UUIDUtil.getUUID();
		params.add(fId);
		params.add(uniScid);
		params.add(fileId);
		dao.execute(sql.toString(), params);
	}
	
	public static void main(String[] args) {
		String string = "/ddd/abc/jpg";
		System.out.println(string.substring(0,string.lastIndexOf("/")+1));
	}

}
