package com.gwssi.datacenter.dataStandar.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.common.FileUtil;
import com.gwssi.datacenter.dataStandar.service.DataStandarService;
import com.gwssi.datacenter.model.DcStandardSpecBO;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.core.web.fileupload.OptimusFileItem;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.util.UuidGenerator;

@Controller
@RequestMapping("/dataStandar")
public class DataStandarController extends BaseController{
	@Autowired
	private DataStandarService dataStandarService;

	
	/**进入标准规范管理页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("dataStandarPage")
	public void getDataStandarPage(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//进入标准规范管理页面
		resp.addPage("/page/dataStandard/dataStandard_list.jsp");
	}
	
	@RequestMapping("query")
	public void queryDataStandar(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取formpanel中的数据
		Map map = req.getForm("formpanel");
		
		//通过条件查询
		List list = dataStandarService.getDataStandar(map);
		
		//封装数据并返回前台
		resp.addGrid("gridpanel", list);
		
	}
	
	/**进入标准规范管理新增页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("addDataStandar")
	public void addDataStandar(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取参数
		String type = req.getParameter("type");
		
		//封装数据
		resp.addAttr("type",type);
		
		//进入新增页面
		resp.addPage("/page/dataStandard/dataStandard_edit.jsp");
	}
	
	/**设置创建信息
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("setMessage")
	public void setMessage(OptimusRequest req,OptimusResponse resp)throws OptimusException{
		
		//获取当前用户
		HttpSession session = WebContext.getHttpSession();
		User user=(User) session.getAttribute(OptimusAuthManager.USER);	
		
		//创建对象
		DcStandardSpecBO dcStandardSpecBO = new DcStandardSpecBO();
		
		//设置创建、最后修改人、时间等信息
		dcStandardSpecBO.setCreaterName(user.getUserName());
		dcStandardSpecBO.setCreaterId(user.getUserId());
		dcStandardSpecBO.setCreaterTime(Calendar.getInstance());
		dcStandardSpecBO.setModifierId(user.getUserId());
		dcStandardSpecBO.setModifierName(user.getUserName());
		dcStandardSpecBO.setModifierTime(Calendar.getInstance());
		
		//封装数据并返回前台
		resp.addForm("formpanel_edit",dcStandardSpecBO );
		
	}
	
	
	/**新增标准规范的保存
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("saveDataStandard")
	public void saveDataStandard(OptimusRequest req,OptimusResponse resp)throws Exception{
		
		//获取数据
		DcStandardSpecBO dcStandardSpecBO = req.getForm("formpanel_edit", DcStandardSpecBO.class);
		Map<String,String> map = req.getForm("formpanel_edit");
		String type = req.getParameter("type");
		
		//文件上传
		Map fileInfo = new HashMap();
		List<OptimusFileItem> list = req.getUploadList("formpanel_edit",
				"fileName");
		
		if (!list.isEmpty()) {
			String rootDir = ConfigManager.getProperty("rootDir");
			String uploadTempDir = ConfigManager.getProperty("upload.tempDir");
			//String uploadPath = req.getHttpRequest().getRealPath("/") ;
			
			//文件路径
			String uploadPath = rootDir + File.separator + uploadTempDir;
			String fileUrl = dcStandardSpecBO.getFileUrl();
			if(!"".equals(fileUrl)){
				FileUtil.deleteFile(uploadPath + fileUrl);
				
				//保存文件
				fileInfo = FileUtil.saveDocFile(list.get(0), uploadPath);
			}else{
				fileInfo = FileUtil.saveDocFile(list.get(0), uploadPath);
			}
			
			dcStandardSpecBO.setFileUrl(fileInfo.get("path").toString().replace("\\", "/"));
			
		}
		
		//新增数据的保存
		if(type.equals("add")){
			 String pkDcStandardSpec = UuidGenerator.getUUID();
			 dcStandardSpecBO.setPkDcStandardSpec( pkDcStandardSpec);
			 
			 //获取当前用户
			 HttpSession session = WebContext.getHttpSession();
			 User user = (User) session.getAttribute(OptimusAuthManager.USER);
			 
			 dcStandardSpecBO.setCreaterId(user.getUserId());
			 dcStandardSpecBO.setEffectiveMarker(AppConstants.EFFECTIVE_Y);
			 dcStandardSpecBO.setCreaterTime(Calendar.getInstance());
			 dcStandardSpecBO.setModifierId(user.getUserId());
			 dcStandardSpecBO.setModifierTime(Calendar.getInstance());
			 int countData = dataStandarService.getNumData() + 1;
			 dcStandardSpecBO.setOrderNo(new BigDecimal(countData));
			 dataStandarService.saveAdd(dcStandardSpecBO);
			
		}
		
		//修改数据的保存
		if(type.equals("update")){
			Calendar calendar = Calendar.getInstance();
			
			//获取当前用户
			HttpSession session = WebContext.getHttpSession();
			User user = (User) session.getAttribute(OptimusAuthManager.USER);
			
			//String 转换为Calendar
			String str= map.get("createrTime");
			if(StringUtils.isNotEmpty(str)){
				dcStandardSpecBO.setCreaterTime(dataStandarService.changeStringToCalendar(str));	
			}
			
			dcStandardSpecBO.setModifierId(user.getUserId());
			dcStandardSpecBO.setModifierTime(calendar);
			dcStandardSpecBO.setModifierName(user.getUserName());
			dataStandarService.saveUpdate(dcStandardSpecBO);
		}
		
	}
	
	/**删除选中的标准规范
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("delete")
	public void deleteDataStandar(OptimusRequest req,OptimusResponse resp)throws Exception{
		
		//获取参数
		String pkDcStandardSpec = req.getParameter("pkid");
		
		//通过主键删除标准规范
		dataStandarService.deleteDataStandar(pkDcStandardSpec);
		
		//封装数据并返回
		resp.addAttr("back","success");
		
	}
	
	/**进入标准规范编辑页面
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("updateDataStandar")
	public void updateDataStandar(OptimusRequest req,OptimusResponse resp)throws Exception{
		
		//获取参数
		String pkDcStandardSpec = req.getParameter("pkDcStandardSpec");
		String type = req.getParameter("type");
		
		//封装数据
		resp.addAttr("pkDcStandardSpec", pkDcStandardSpec);
		resp.addAttr("type", type);
		
		//进入标准规范编辑页面
		resp.addPage("/page/dataStandard/dataStandard_edit.jsp");
	}
	
	/**将选中的标准规范记录回显
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("getDataStandarById")
	public DcStandardSpecBO getDataStandarById(OptimusRequest req,OptimusResponse resp)throws Exception{
		
		//获取参数
		String pkDcStandardSpec = req.getParameter("pkDcStandardSpec");
		
		List param = new ArrayList();
		param.add(pkDcStandardSpec);
		
		//通过主键查询
		List<DcStandardSpecBO> list = dataStandarService.getDataStandarById(param);
		
		//当list不为空时
		if (!list.isEmpty()) {
			DcStandardSpecBO dcStandardSpecBO = (DcStandardSpecBO)list.get(0);
			
			//封装数据并返回前台
			resp.addForm("formpanel_edit", dcStandardSpecBO);
		}
		return null;
	}
	
	
	/**文件下载
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@RequestMapping("downloadDoc")
	public void downloadDoc(OptimusRequest req,OptimusResponse resp)throws Exception{
		
		//获取参数
		String pkDcStandardSpec = req.getParameter("pkid");
		
		//通过主键查询文件url
		List<Map> list = dataStandarService.getUrlAndName(pkDcStandardSpec);
		String fileUrl = list.get(0).get("fileUrl").toString();
		
		String rootDir = ConfigManager.getProperty("rootDir");
		String uploadTempDir = ConfigManager.getProperty("upload.tempDir");
		
		
		//文件路径
		String uploadPath = rootDir + File.separator + uploadTempDir;
		String path = uploadPath +fileUrl;
		fileUrl=path;
		//文件名
		/*String fileName = fileUrl.substring(fileUrl.lastIndexOf(File.separator)+1);*/
		String fName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
		//String fileName = fName.substring(0, fName.lastIndexOf("+"))+fName.substring(fName.lastIndexOf("."));
		
		//得到要下载的文件
		File f = new File("c://temp/w.rar");
		
		resp.download(f, URLEncoder.encode("cc.rar", "UTF-8"), false);
		/*
		//读取要下载的文件，保存到文件输入流
		FileInputStream fin = new FileInputStream(f);
		resp.getHttpResponse().reset();
		resp.getHttpResponse().setContentType("application/x-download;charset=GBK");
		
		//设置响应头，控制浏览器下载该文件
		resp.getHttpResponse().setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
	
		//创建输出流	
		java.io.OutputStream os = resp.getHttpResponse().getOutputStream();
		
		//创建缓冲区
		byte[] b = new byte[1024];
		int len = 0;
		
		//循环将输入流中的内容读取到缓冲区当中
		while((len = fin.read(b))>0){
			
			//输出缓冲区的内容到浏览器，实现文件下载
			os.write( b, 0, len);
				
		}
		
		os.flush();
		
		//关闭文件输入流
		os.close();
		
		//关闭输出流
		fin.close();
			*/
	}
		
		
}
