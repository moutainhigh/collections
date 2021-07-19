package com.gwssi.common.upload;
/**
 * @desc 附件上传VO对象
 * @author dwn
 * @version 1.0
 */
public class UploadFileVO {
	
	public UploadFileVO(){
	}
	// 页面附件标签名 如：record:file
	private String recordName;    
    // 附件类型：页面单种类型，页面同时存在多种类型附件，状态由GgConstants.UPLOAD_STATUS_维护
	private String fileStatus;
    // 附件二级目录
    private String ejml;
	
	// 存储保存的附件名称 单附件为[id].多附件为[;id1;id2;id3];
	private String returnId;
	// 存储保存的附件名称 单附件为[name].多附件为[;name1;name2;name3];
	private String returnName;
	
    // 页面保存的被删除附件Id值
	private String deleteId;
    // 页面保存的被删除附件name值
	private String deleteName;
	
    // 更新附件前业务数据库表附件id字段存储的值
	private String originId;
    // 更新附件前业务数据库表附件name字段存储的值
	private String originName;
	
	public String getDeleteId() {
		return deleteId;
	}
	public void setDeleteId(String deleteId) {
		this.deleteId = deleteId;
	}
	public String getDeleteName() {
		return deleteName;
	}
	public void setDeleteName(String deleteName) {
		this.deleteName = deleteName;
	}
	public String getOriginId() {
		return originId;
	}
	public void setOriginId(String originId) {
		this.originId = originId;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public String getReturnId() {
		if(this.returnId==null)
		{
			this.returnId="";
		}
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
	public String getReturnName() {
		if(this.returnName==null)
		{
			this.returnName="";
		}
		return returnName;
	}
	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
    public String getEjml() {
        return ejml;
    }
    public void setEjml(String ejml) {
        this.ejml = ejml;
    }
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("returnId").append(returnId).append("returnName").append(returnName);
		return sb.toString();
		
	}
}
