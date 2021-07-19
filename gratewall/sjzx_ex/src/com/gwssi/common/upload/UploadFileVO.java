package com.gwssi.common.upload;
/**
 * @desc �����ϴ�VO����
 * @author dwn
 * @version 1.0
 */
public class UploadFileVO {
	
	public UploadFileVO(){
	}
	// ҳ�渽����ǩ�� �磺record:file
	private String recordName;    
    // �������ͣ�ҳ�浥�����ͣ�ҳ��ͬʱ���ڶ������͸�����״̬��GgConstants.UPLOAD_STATUS_ά��
	private String fileStatus;
    // ��������Ŀ¼
    private String ejml;
	
	// �洢����ĸ������� ������Ϊ[id].�฽��Ϊ[;id1;id2;id3];
	private String returnId;
	// �洢����ĸ������� ������Ϊ[name].�฽��Ϊ[;name1;name2;name3];
	private String returnName;
	
    // ҳ�汣��ı�ɾ������Idֵ
	private String deleteId;
    // ҳ�汣��ı�ɾ������nameֵ
	private String deleteName;
	
    // ���¸���ǰҵ�����ݿ����id�ֶδ洢��ֵ
	private String originId;
    // ���¸���ǰҵ�����ݿ����name�ֶδ洢��ֵ
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
