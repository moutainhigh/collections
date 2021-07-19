package com.gwssi.ebaic.common.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.gwssi.ebaic.approve.image.service.ImageApproveService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.upload.UploadListUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 图片审核页面。
 * 
 * @author lxb
 */
@Controller
@RequestMapping("/approve/pic")
public class BeApprovePicController {
	
	@Autowired
	ImageApproveService imageApproveService ;
	/**
	 * 获取图片大类
	 * 审核内容页
	 * @param requeest
	 * @param response
	 * @throws OptimusException 
	 */
	@RequestMapping("/getCategory")
	public void getList(OptimusRequest request,OptimusResponse response) throws OptimusException{
		List<Map<String, Object>> configList = imageApproveService.getCatgoryList(request);
		response.addResponseBody(JSON.toJSONString(configList));
	}
	
	/**
	 * 获取指定图片大类上传的所有图片
	 * 图像审查页
	 * @param requeest
	 * @param response
	 * @throws OptimusException 
	 */
	@RequestMapping("/getListByCategory")
	public void getCategoryPic(OptimusRequest request,OptimusResponse response) throws OptimusException{
		Map<String, Object> paramMap = ParamUtil.getParams(request);
		String categoryId = StringUtil.safe2String(paramMap.get("categoryId"));
		String gid = StringUtil.safe2String(paramMap.get("gid"));
		List<Map<String, Object>> list = UploadListUtil.getListByCategory(categoryId,gid);
		if("1".equals(categoryId)){
			//查询住所文件时把签字盖章页的住所证明文件也加上
			String sql = "select f_id,file_id,thumb_file_id,data_type,f.state,f.sn,f.category_id from be_wk_upload_file f where gid=? and category_id='5' and ref_id='dom' and state<>'2' order by sn";
			List<Map<String, Object>> domList = DaoUtil.getInstance().queryForList(sql, gid);
			list.addAll(domList);
		}else if("5".equals(categoryId)){
			//签字盖章页
			Iterator<Map<String,Object>> it =list.iterator();
			while(it.hasNext()){
				Map<String, Object> row = it.next();
				String refId = StringUtil.safe2String(row.get("refId"));
				if(refId !=null && refId.indexOf("dom")!=-1){
				    it.remove();//把住所证明页删掉，这里只是相当于把图片隐藏，并没有真正的删除
					break;
				}
			}
			int l = list.size();
			//把股东确认书也加上
			String sql = "select f_id,'inv' as ref_id,'股东确认书' as ref_text,file_id,thumb_file_id,data_type,f.state,f.sn+"+l+" as sn,f.category_id from be_wk_upload_file f where gid=? and category_id='8'  and state<>'2' order by sn";
			List<Map<String, Object>> invSign = DaoUtil.getInstance().queryForList(sql, gid);
			list.addAll(invSign);
		}
		response.addResponseBody(JSON.toJSONString(list));
	}
	@RequestMapping("/getPicInfo")
	public void getPicInfo(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String fId = ParamUtil.get("fId");
		if(StringUtils.isBlank(fId) ){
			throw new OptimusException("无效的文件标识："+fId);
		}
		Map<String,Object> o = UploadListUtil.getPicInfo(fId);
		response.addResponseBody(JSON.toJSONString(o));
	}
	/**
	 * 获取人员证件图片对应的详细信息
	 * 包括：图片关联的人员信息、审核意见、状态
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/getPersonDetail")
	public void getPersonDetail(OptimusRequest request,OptimusResponse response) throws OptimusException{
		Map<String, Object> paramMap = ParamUtil.getParams(request);
		String fileId = StringUtil.safe2String(paramMap.get("fileId"));
		if(StringUtils.isBlank(fileId) ){
			throw new OptimusException("无效的文件标识："+fileId);
		}
		
		//审核意见
		//数据关联
		
	}
	
}
