package com.gwssi.ebaic.approve.reqhistory.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.upload.UploadConfigUtil;
import com.gwssi.rodimus.upload.UploadListUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 审批历史查询。
 * 
 * @author xupeng
 */
@Service("reqHistoryService")
public class ReqHistoryService {
	
	/**
	 * 查询上传材料。
	 * 
	 * @param gid
	 * @return
	 * @throws OptimusException
	 */
	public List<Map<String,Object>> getReqPicInfo(String listCode,String gid,Map<String, Object> paramMap) throws OptimusException{
		if(StringUtil.isBlank(gid)){
            throw new OptimusException("业务流水号不能为空!");
        }
		List<Map<String, Object>> ret = UploadConfigUtil.getConfigList(listCode, paramMap);
		// 状态2表示已经删除，FIXME 和lixibo确认一下
		String sql = "select f.* from be_wk_upload_file f where f.state <> 2 and f.gid = ? order by f.category_id,f.sn asc";
		List<Map<String,Object>> list = DaoUtil.getInstance().queryForList(sql,gid);
		ret = UploadListUtil.mergeList(ret,list);
		return ret;
	}
	
	
	/**
	 * 根据gid得到当前数据的处理状态
	 * @param gid
	 * @return
	 * @throws OptimusException 
	 */
	public String getReqState (String gid) throws OptimusException{
		if(StringUtil.isBlank(gid)){
			throw new OptimusException("业务流水号不能为空!");
		}
		String sql = "select req.state from be_wk_Requisition req where req.gid = ?";
		String state = (String) DaoUtil.getInstance().queryForOne(sql, gid);
		return state;
	}

}
