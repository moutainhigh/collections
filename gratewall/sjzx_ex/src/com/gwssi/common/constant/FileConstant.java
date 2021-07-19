package com.gwssi.common.constant;

/**
 * 
 *     
 * 项目名称：bjgs_exchange    
 * 类名称：FileConstant    
 * 类描述： 文件上传下载常量   
 * 创建人：dwn    
 * 创建时间：Mar 27, 2013 2:29:53 PM    
 * 修改人：SKS    
 * 修改时间：Mar 27, 2013 2:29:53 PM    
 * 修改备注：    
 * @version     
 *
 */
public class FileConstant
{
	// 上传附件类型：同页面单一种类附件
	public static final String UPLOAD_FILESTATUS_SINGLE = "single";
	
	// 上传附件类型：同页面多种类型附件
	public static final String UPLOAD_FILESTATUS_MULTIPLE = "multiple";
	
	
	// 文件
    public static String file = "FILE";

    // 文件夹
    public static String folder = "FOLDER";

    // 大小
    public static String size = "SIZE";

    // file对象类型
    public static String file_type = "TYPE";
	// 文件名称
    public static String file_name = "NAME";

    // 文件映射编号pk
    public static String file_id = "ID";
    
 // 文件最后修改时间
    public static String file_lastModified = "LASTMODIFIED";
    
    
    /**
     * 路径分隔符
     */
    public static final String PATH_SEPERATOR = "/";
    public static final String PATH_SEPERATOR1 = "\\";

    /**
     * 多个ID值分隔符
     */
    public static final String ID_SEPERATOR = ",";
    
    
 // 组织机构有效状态
    public static String status_inuse = "0"; // 有效

    public static String status_offuse = "1"; // 无效
}
