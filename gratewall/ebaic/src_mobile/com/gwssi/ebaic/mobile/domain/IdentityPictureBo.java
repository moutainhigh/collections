package com.gwssi.ebaic.mobile.domain;

import com.gwssi.rodimus.rpc.RpcBaseBo;

/**
 * 身份认证信息图片Bo。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class IdentityPictureBo extends RpcBaseBo {
	
	private static final long serialVersionUID = -6971887974935391662L;
	
	private String identityId = "";
	private String pictureId = "";
	private String type = "";
	private String fileId = "";	
	private String approveMsg = "";
	private String flag = "";
	
	
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	/**
	 * <pre>三位编码，第一位表示个人还是企业，0-个人，1-企业；
	 * 如果是个人，第二位表示证件类型，如果是个人参照码表CB16，第三位为顺序号；
	 * 如果是企业，第二三位参照码表CA50。
	 * 例如：010-个人身份证正面；
	 *  011-个人身份证反面；
	 *  012-个人手持身份证；
	 *  1AA-企业营业执照。	
	 * </pre>
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getApproveMsg() {
		return approveMsg;
	}
	public void setApproveMsg(String approveMsg) {
		this.approveMsg = approveMsg;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
