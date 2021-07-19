package com.gwssi.ebaic.approve.image.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.dao.ApproveDaoUtil;
import com.gwssi.rodimus.util.FileUtil;
import com.gwssi.rodimus.util.ParamUtil;

@Controller
@RequestMapping(value="/approve/signPic")
public class SignPicController {
	
	@RequestMapping("/show")
	public void showPic(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String userId = ParamUtil.get("userId");
		String filePath = getSignPicByUserId(userId);//
		FileUtil.download("", "","", filePath, response.getHttpResponse());
	}
	/**
	 * 
	 * @param userId
	 * @return
	 */
	private String getSignPicByUserId(String userId) {
		String sql = "select t.sign_pic_url from sysmgr_user t where t.user_id=?";
		String ret = ApproveDaoUtil.getInstance().queryForOneString(sql, userId);
		return ret;
	}
}
