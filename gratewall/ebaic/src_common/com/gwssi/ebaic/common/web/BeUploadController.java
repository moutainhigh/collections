package com.gwssi.ebaic.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.ebaic.common.service.BeUploadService;
import com.gwssi.ebaic.common.util.ImageUtil;
import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.ebaic.common.util.UploadFilePathUtil;
import com.gwssi.ebaic.common.util.UploadUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.doc.v2.DocUtil;
import com.gwssi.rodimus.upload.UploadConfigUtil;
import com.gwssi.rodimus.upload.UploadListUtil;
import com.gwssi.rodimus.util.FileUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 关键页上传。
 * 
 * @author lxb
 */
@Controller
@RequestMapping("/upload")
public class BeUploadController {
	
	@Autowired
	private BeUploadService beUploadService;
	/**
	 * 关键页上传页面初始化。
	 * @param requeest
	 * @param response
	 * @throws OptimusException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getList")
	public void getList(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		DocUtil.copyIdentityFile(gid);
		/**获取要上传的文件类别列表**/
		List<Map<String, Object>> configList = UploadConfigUtil.getList(UploadConfigUtil.CP_SETUP_1100,request);
		Map res = new HashMap();
		res.put("list", configList);		
		res.put("step", beUploadService.getUploadStep(gid));
		res.put("customDoc", beUploadService.getCustomDoc(gid));
		
		response.addResponseBody(JSON.toJSONString(res));
		
	}
	
	/**
	 * 将文件上传至服务器指定的路径
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/upload")
	public void upload(OptimusRequest request,OptimusResponse response) throws OptimusException{
		Map<String, Object> map = ParamUtil.getParams(request);
		//String gid = request.getParameter("gid");
		String gid = StringUtil.safe2String(map.get("gid"));
		if(StringUtils.isBlank(gid) ){
			throw new OptimusException("无效的业务标识："+gid);
		}
		//获取上传路径
		String path = UploadFilePathUtil.getUploadPath(gid);
		List ids=null;
		//接收上传文件
		HttpServletRequest hr = request.getHttpRequest();		
		try {
			ids =UploadUtil.upload(path, hr, true);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new OptimusException("文件上传失败："+e.getMessage());
		}
		if(ids==null){
			throw new OptimusException("文件上传失败,");
		}
		response.addResponseBody(JSONObject.toJSONString(ids));
	}
	@RequestMapping("/saveCustomDocInfo")
	public void saveCustomDocInfo(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid= ParamUtil.get("gid");
		String fileId= ParamUtil.get("fileId");
		String fid= ParamUtil.get("fid",false);
		Map<String,Object> info = new HashMap<String,Object>();
		Map<String,String> fileMap =FileUtil.getFileInfo(fileId);
		info.put("categoryId", "custom_doc");
		info.put("dataType", "customDoc");
		info.put("fid", fid);
		info.put("sn", "1");
		info.put("refText", fileMap.get("fileName"));
		fid = UploadUtil.singleFileMatchBusiRecord(gid, fileId, info);
		response.addAttr("fid",fid);
	}
	/**
	 * 根据传递的参数对图片进行裁剪
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("cutImage")
	public void cutImage(OptimusRequest request,OptimusResponse response) throws OptimusException{
		Map<String, Object> map = ParamUtil.getParams(request);
		String gid = StringUtil.safe2String(map.get("gid"));
		String fileId=StringUtil.safe2String(map.get("fileId"));//图片全路径（含文件名）
		if(StringUtils.isBlank(fileId)){
			throw new OptimusException("无效的文件标识："+fileId);
		}
		Map<String,String> fileInfo = FileUtil.getFileInfo(fileId);
		String srcName = fileInfo.get("srcName");//文件名
		String suffixName = fileInfo.get("suffixName");//拓展名
		String src= fileInfo.get("filePath");//获取原图片路径
		
		String dest=UploadFilePathUtil.getUploadPath(gid);//裁剪后图片路径。可能与原路径相同，也可能不同
		String destId = UploadUtil.getUUID();
		String pointX = (String)map.get("pointX"); 
		String pointY = (String) map.get("pointY"); 
		String width = (String) map.get("width"); 
		String height = (String) map.get("height"); 
		if(StringUtils.isBlank(pointX) || StringUtils.isBlank(pointY)
				|| StringUtils.isBlank(width)|| StringUtils.isBlank(height)){
			throw new OptimusException("剪切图片参数缺失：pointX="+pointX+",pointY="+pointY+",width="+width+",height="+height);
		}
		try {
			ImageUtil.cutImage(src+srcName, dest+destId+"."+suffixName, 
					Integer.parseInt(pointX), Integer.parseInt(pointY), 
					Integer.parseInt(width), Integer.parseInt(height));
		} catch (Exception e) {
			throw new OptimusException("图像裁剪失败"+e.getMessage(),e);
		}
		
		String thumbId=null;
		try {
			thumbId = UploadUtil.buildThumb(dest,destId,suffixName);//生成新的缩略图
		} catch (Exception e) {
			throw new OptimusException("生成缩略图失败",e);
		}
		//保存裁剪后的新文件记录
		// FIXME contentTYpe
		FileUtil.saveFile(destId, suffixName, dest, "cont/imamge", 0, destId+".jpg");
		Map ids = new HashMap();
		ids.put("fileId", destId);
		ids.put("thumbFileId", thumbId);
		response.addResponseBody(JSONObject.toJSONString(ids));
	}
	
	/**
	 * 获取图片文件用于展示
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/showPic")
	public void showPic(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String fileId = ParamUtil.get("fileId");
		String filePath = FileUtil.getFilePhyPath(fileId);
		FileUtil.download("", "","", filePath, response.getHttpResponse());
		
		
//		Map<String, Object> map = ParamUtil.getParams(request);
//		String fileId=map.get("fileId")==null?null:(String)map.get("fileId");
//		
//		String fileId = ParamUtil.get("fileId");
//		if(fileId==null || StringUtil.isBlank(fileId)||"undefined".equals(fileId)){
//			throw new FileException("无效的fileId");
//		}
//		Map<String,String> rsMap = FileUtil.getFileInfo(fileId);
//		String path = rsMap.get("filePath");
//		String fname = rsMap.get("srcName");
//		
//		String filePath = ConfigUtil.get("file.rootPath")+path+fname ;
//		try {
//			FileInputStream fis = null;
//			OutputStream os = null;
//			try {
//				os = response.getHttpResponse().getOutputStream();
//				fis = new FileInputStream(filePath);
//				byte[] data = new byte[10240];
//				int len = 0;
//				while ((len = fis.read(data)) > 0) {
//					os.write(data, 0, len);
//				}
//				os.flush();
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				IOUtils.closeQuietly(fis);
//				IOUtils.closeQuietly(os);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new FileException("获取指定文件失败:"+e.getMessage(),e);
//		}
		
	}
	
	/**
	 * 在数据库中记录已经上传的文件信息
	 * be_wk_upload_file
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/saveFileInfo")
	public void savefFileInfo(OptimusRequest request,OptimusResponse response) throws OptimusException{
		Map<String, Object> map = ParamUtil.getParams(request);
		
		String gid = (String) map.get("gid");
		String listString = (String)map.get("list");
		List list = JSONObject.parseObject(listString, List.class);
		if(list==null || list.size()==0){
			throw new OptimusException("获取页面文件列表失败");
		}
		List<Map<String, Object>> listInDB = UploadListUtil.getFileList(gid);
		
		int list_len = list.size();
		List<Map> uploadFileList = new ArrayList<Map>();
		//遍历提交的文件大类 将上传的文件列表整合到一起
		for (int i = 0; i < list_len; i++) {
			Map category = (Map)list.get(i);
			List<Map> fileArray=(List<Map>)category.get("fileArray");
			if(fileArray!=null && fileArray.size()>0){//有上传的文件
				uploadFileList.addAll(fileArray);
			}
		}
		
		//只有删除后没有重传的才需要标记为删除
		if(listInDB!=null && listInDB.size()>0){
			//新增的文件已经保存在数据库中了，这里只需将删除的文件设置为删除
			int up_len = uploadFileList.size();
			int db_len = listInDB.size();
			List<List<Object>> paramUpdate = new ArrayList<List<Object>>();
			for (int k = 0; k <db_len; k++){//遍历数据库中的记录 找到没有的
				Map record = listInDB.get(k);
				String rFid = StringUtil.safe2String(record.get("fId"));
				int j=0;
				for (j = 0; j < up_len; j++) {
					Map upMap = uploadFileList.get(j);
					String fId = StringUtil.safe2String(upMap.get("fId"));
					if(StringUtils.isNotBlank(rFid) 
							&&rFid.equals(fId)){
						break;
					}
				}
				if(j== up_len){
					//没有找到匹配项，是要删除的
					List<Object> param = new ArrayList<Object>();
					param.add(record.get("fileId"));					
					param.add(gid);
					param.add(record.get("categoryId"));
					param.add(record.get("fId"));
					paramUpdate.add(param);
				}
			}
			
			if(paramUpdate.size()>0){
				//更新be_wk_upload_file表中文件记录
				
				StringBuffer updateSql = new StringBuffer();
				updateSql.append("update be_wk_upload_file set state='2',timestamp=sysdate ")
						.append("where file_id=? and gid=? and category_id=? and f_id=?");
				IPersistenceDAO dao = DAOManager.getPersistenceDAO();
				dao.executeBatch(updateSql.toString(), paramUpdate);
			}
			
			
			//更新be_wk_requisition表时间戳
			SubmitUtil.updateTimeStamp("be_wk_requisition", "gid", gid);
		}
		
		
		response.addResponseBody("success");
	}
	
	/**
	 * 图片合并
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping("/mergePic")
	public void mergePic(OptimusRequest request,OptimusResponse response) throws OptimusException{
		Map<String, Object> map = ParamUtil.getParams(request);		
		String gid = StringUtil.safe2String(map.get("gid"));
		if(StringUtils.isBlank(gid) ){
			throw new OptimusException("无效的业务标识："+gid);
		}
		String infoString = StringUtil.safe2String(map.get("info"));
		Map<String,Object> info =null;
		try {
			info =JSONObject.parseObject(infoString, Map.class);
		} catch (Exception e) {
			throw new OptimusException("格式转换失败",e);
		}
		if(info ==null || info.isEmpty()){
			throw new OptimusException("文件业务信息为空");
		}
		String path =UploadFilePathUtil.getUploadPath(gid);
		String listString = StringUtil.safe2String(map.get("list"));
		System.out.println(listString);
		if(StringUtils.isBlank(listString)){
			throw new OptimusException("获取文件标识列表失败");
		}
		List<String > list=null;
		try {
			list =JSONObject.parseObject(listString, List.class);
		} catch (Exception e) {
			throw new OptimusException("格式转换失败",e);
		}
		if(list ==null || list.size()==0){
			throw new OptimusException("获取文件标识列表失败");
		} 
    	String yfileId = UploadUtil.mergeImages(list,path,"y");
    	
    	String thumbId=null;
		try {
			thumbId = UploadUtil.buildThumb(path,yfileId,"jpg");//生成新的缩略图
		} catch (Exception e) {
			throw new OptimusException("生成缩略图失败",e);
		}
		info.put("thumbFileId", thumbId);
		String fid =UploadUtil.singleFileMatchBusiRecord(gid,yfileId,info);//更新业务表
		Map<String,String> ids = new HashMap<String,String>();
		ids.put("fileId", yfileId);
		ids.put("thumbFileId", thumbId);
		ids.put("fId", fid);
		response.addResponseBody(JSONObject.toJSONString(ids));
		
	}
	
	/**
	 * 图片关联业务数据，如果没有生成过业务记录，生成，否则更新
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/matchBusiRecord")
	public void matchBusiRecord(OptimusRequest request,OptimusResponse response)throws OptimusException{
		Map<String, Object> map = ParamUtil.getParams(request);		
		String gid = StringUtil.safe2String(map.get("gid"));
		if(StringUtils.isBlank(gid) ){
			throw new OptimusException("无效的业务标识："+gid);
		}
		String fileId = StringUtil.safe2String(map.get("fileId"));
		if(StringUtils.isBlank(fileId) ){
			throw new OptimusException("无效的文件标识："+fileId);
		}
		String infoString = StringUtil.safe2String(map.get("info"));
		Map<String,Object> info =null;
		try {
			info =JSONObject.parseObject(infoString, Map.class);
		} catch (Exception e) {
			throw new OptimusException("格式转换失败",e);
		}
		if(info!=null && !info.isEmpty()){
			String fid = UploadUtil.singleFileMatchBusiRecord(gid,fileId,info);
			Map<String,String> resMap = new HashMap<String,String>();
			resMap.put("fId", fid);
			response.addResponseBody(JSONObject.toJSONString(resMap));
		}else{
			
		}
	}
	/**
	 * 删除指定图片
	 * 标记图片业务状态标识为4
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/delPic")
	public void delPic(OptimusRequest request,OptimusResponse response)throws OptimusException{
		Map<String, Object> map = ParamUtil.getParams(request);		
		String fid = StringUtil.safe2String(map.get("fId"));
		UploadUtil.delPic(fid);
	}
	
	@RequestMapping("/docState")
	public void docState(OptimusRequest request,OptimusResponse response)throws OptimusException{
		String gid = ParamUtil.get("gid");
		String state =beUploadService.docState(gid);
		
		response.addAttr("docState", state);
	}
	
	@RequestMapping("/setUploadStep")
	public void setUploadStep(OptimusRequest request,OptimusResponse response)throws OptimusException{
		String gid = ParamUtil.get("gid");
		String step =ParamUtil.get("step");
		beUploadService.setUploadStep(gid,step);
		
	}
	@RequestMapping("/getDocTemplatePath")
	public void getDocTemplatePath(OptimusRequest request,OptimusResponse response)throws OptimusException{
		String gid = ParamUtil.get("gid");
		String path =beUploadService.getDocTemplatePath(gid);
		String contentType = "application/octet-stream;charset=UTF-8";
		
		FileUtil.download(contentType, "zhangcheng.docx","", path , response.getHttpResponse());
		
	}
	
	/**
	 * 供远程调用，目前用于13服务器文书下载，从27服务器上执行合成，返回合成的fileId
	 * @param request
	 * @param optimusResponse
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/clientMergePic")
	@ResponseBody
	public void clientMergePic(OptimusRequest request,OptimusResponse optimusResponse){
		String jsonStr =request.getParameter("jsonStr");
		if(StringUtil.isBlank(jsonStr)){
			throw new RuntimeException("参数【jsonStr】为空");
		}
		List<Object> list = JSON.parseArray(jsonStr);
		Map<String,Object> map =null;
		List<String> fileList = null;
		String path = null;
		if(list!=null){
			map = (Map<String,Object>)list.get(0);
			fileList = (List<String>) map.get("fileList");
			path= (String)map.get("path");
		}
		
		String ret = UploadUtil.mergeImages(fileList,path,"y");
		System.out.println("合并后的文件ID："+ret);
		optimusResponse.addResponseBody(ret);
	}
}
