package com.yunpan.bean;

import java.util.Date;

/**
 * 
 * 文件资源类类 Resource<BR>
 * 创建人:潭州学院-keke <BR>
 * 时间：2014年11月23日-上午12:37:25 <BR>
 * 
 * @version 1.0.0
 * 
 */
public class Resource implements java.io.Serializable {
	//八种基础数据类型:byte short int long float double boolean char
	//八种分装数据类型Byte Short Integer Long Float Double Boolean Character==因为他们的类默认值都是null
	//我们泛型只允许是封装数据类型
	private static final long serialVersionUID = 1L;
	// 主键
	private Integer id;
	// 文件名
	private String name;
	// 重名以后的文件名称
	private String newName;
	// 文件的后缀
	private String ext;
	// 文件描述
	private String description;
	//文件的路径
	private String url;
	// 文件的宽度
	private Integer width;
	// 文件的高度
	private Integer height;
	// 文件字节大小
	private Integer size;
	// 文件转换以后的大小
	private String sizeString;
	// 删除状态 0未删除 1删除
	private Integer isDelete;// is_delete_name isDeleteName
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;
	// 发布状态 0未发布 1发布
	private Integer status;
	//文件的类型 1文件2图片3音频4其他
	private Integer type;
	// 实体映射
	// 用户，上传者
	private Integer userId;
	// 对应的文件夹
	private Integer folderId;

	// 生成getter/setter快捷键是:alt+shift+s
	// ctrl+a 全选 快捷键是:ctrl+shift+f
	//清除一个类里面无用的引入包的时候：Ctrl+shift+o
	//ctrl+shift+r 全局搜索文件定位
	//ctrl+l 定位行
	
	public Resource(){
		
	}
	
	public Resource(Integer id){
		this.id = id;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getSizeString() {
		return sizeString;
	}

	public void setSizeString(String sizeString) {
		this.sizeString = sizeString;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}