package com.gwssi.common.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoCtrl;
import cn.gwssi.common.context.vo.VoFileInfo;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.FileConstant;
import com.gwssi.common.upload.*;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.file.manage.vo.VoXtCcglWjlb;
import com.gwssi.file.manage.vo.VoXtCcglWjys;

public class UploadFileManagerEx
{
	
	// 文件类别表名称
    private static final String WJLB_TABLE = "xt_ccgl_wjlb";

    // 文件映射表名称
    private static final String WJYS_TABLE = "xt_ccgl_wjys";

    // 主键单记录查询
    private static final String SELECT_FUNCTION_PK = "select one xt_ccgl_wjys by pk";

    // 修改文件名称记录
    private static final String UPDATE_FILE_FUNCTION = "update file name xt_ccgl_wjys";

    // 修改文件名称记录
    private static final String UPDATE_FILEWYBS_FUNCTION = "update file wybs xt_ccgl_wjys";

    // 增加记录
    private static final String INSERT_FUNCTION = "insert one xt_ccgl_wjys";

    // 删除记录
    private static final String DELETE_FUNCTION = "delete one xt_ccgl_wjys";

    private final static Logger log = TxnLogger
            .getLogger(UploadFileManagerEx.class.getName());

    private String root;

    private String cclbbh_pk;

    private String ejmlgz;

    private String DEFAULT_SECOND_DIR = "default";
	

    /**
     * 业务模块上传文件接口构造函数
     * 
     * @param fileType --
     *            文件类别
     * @throws TxnException
     */
    public UploadFileManagerEx(String fileType) throws TxnException {
        getRootPathByType(fileType);
    }

    /**
     * 保存单个附件
     * 
     * @param voFile
     *            ：烽火台上传文件VO对象
     * @return DataBus : 结点名如下 附件映射主键-Constants.file_id，附件名-Constants.file_name；
     *         字段名由sys.Constants维护
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, TxnContext context)
            throws TxnException {

        return saveFile(voFile, "", context);
    }

    /**
     * 保存单个附件
     * 
     * @param voFile
     *            ：烽火台上传文件VO对象
     * @param ywbz :
     *            业务备注，用于存储所属业务模块主键值
     * @return DataBus : 结点名如下 附件映射主键-Constants.file_id，附件名-Constants.file_name；
     *         字段名由sys.Constants维护
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz, TxnContext context)
            throws TxnException {
        return saveFile(voFile, ywbz, null, context);
    }

    /**
     * 保存单个附件
     * 
     * @param voFile
     *            ：烽火台上传文件VO对象
     * @param ywbz :
     *            业务备注，用于存储所属业务模块主键值
     * @param ejml
     *            ：二级目录名称
     * @return DataBus : 结点名如下 附件映射主键-Constants.file_id，附件名-Constants.file_name；
     *         字段名由sys.Constants维护
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz, String ejml,
            TxnContext context) throws TxnException {
        return saveFile(voFile, ywbz, ejml, "", context);
    }

    /**
     * 保存单个附件
     * 
     * @param voFile
     *            ：烽火台上传文件VO对象
     * @param ywbz :
     *            业务备注，用于存储所属业务模块主键值
     * @param ejml
     *            ：二级目录名称
     * @param xm_fk :
     *            项目主键 用于现场机关数据交换（修改时间：2007年01月25日）
     * @return DataBus : 结点名如下 附件映射主键-Constants.file_id，附件名-Constants.file_name；
     *         字段名由sys.Constants维护
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz, String ejml,
            String xm_fk, TxnContext context) throws TxnException {
        if (voFile == null || voFile.getOriFileName().trim().length() == 0) {
            return null;
        }

        // 获取上传附件信息
        String oriFileName = voFile.getOriFileName().trim();// 附件名称(原始文件名)
        String tempFileName = voFile.getValue(VoCtrl.ITEM_SVR_FILENAME);// 临时文件名称
        String uploadFileName="";
        
        
        if(tempFileName.indexOf("\\")!=-1){
        	//log.debug("文件路径标示符：\\");
        	uploadFileName = tempFileName.substring(tempFileName
                    .lastIndexOf(FileConstant.PATH_SEPERATOR1) + 1);
        }else{
        	//log.debug("文件路径标示符：/");
        	uploadFileName = tempFileName.substring(tempFileName
                    .lastIndexOf(FileConstant.PATH_SEPERATOR) + 1);
        }
       /* String uploadFileName = tempFileName.substring(tempFileName
                .lastIndexOf(FileConstant.PATH_SEPERATOR1) + 1);*/
        
        
        /**
         * bug修改：修改附件名称有英文逗号时文件名错乱问题
         * 修改人：方爱丹
         * 修改时间：2007-8-4
         */
        log.debug("替换英文逗号、英文单引号开始： "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("替换英文逗号、英文单引号开始结束："+oriFileName);
        
        // 获取二级目录
        String secondDir = getSecondDir(ejml);
        // 设置文件创建时间
        String cjsj = CalendarUtil.getCalendarByFormat(CalendarUtil.FORMAT14);
        // 组合上传附件的全路径：文件类型的根目录+二级目录+文件名称
        
        String prePath = root + FileConstant.PATH_SEPERATOR + secondDir;
        String filePath = prePath + FileConstant.PATH_SEPERATOR + uploadFileName;
        log.info("上传文件的完整文件名:"+filePath);
        DataBus result = new DataBus();
        // 创建文件
        createFile(tempFileName, filePath);
        // 删除临时文件
        delFile(tempFileName);
        try {
            DataBus db = new DataBus();
            db.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
            db.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
            db.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
            db.put(VoXtCcglWjys.ITEM_WJZT, FileConstant.status_inuse);
            db.put(VoXtCcglWjys.ITEM_CCLJ, secondDir);
            db.put(VoXtCcglWjys.ITEM_CJSJ, cjsj);
            db.put(VoXtCcglWjys.ITEM_SCXGSJ, cjsj);
            db.put(VoXtCcglWjys.ITEM_BZ, "");
            db.put(VoXtCcglWjys.ITEM_YWBZ, ywbz);
            db.put(VoXtCcglWjys.ITEM_YSBH_PK, "");
            // ****** 新增项目主键，用于现场与机关数据交换 @20070125 by ada
            db.put(VoXtCcglWjys.ITEM_XM_FK, xm_fk);

            context.addRecord("wjys", db);

            BaseTable filetable = TableFactory.getInstance().getTableObject(
                    this, WJYS_TABLE);
            filetable.executeFunction(INSERT_FUNCTION, context, db, "wjys");

            // 设置回传值
            result.put(FileConstant.file_id, context.getRecord("wjys").getValue(
                    VoXtCcglWjys.ITEM_YSBH_PK));
            result.put(FileConstant.file_name, context.getRecord("wjys").getValue(
                    VoXtCcglWjys.ITEM_WJMC));
        } catch (Exception e) {
            log.error(e);
            log.debug("文件保存错误!");
            //throw new TxnErrorException(ErrorConstant.SQL_EXECUTE_ERROR,
              //      BJAISConfig.get("ggkz.ccgl.wjys.add.saveerror"));
        }
        // 去除临时结点
        context.remove("wjys");
        return result;
    }
    
    /**
     * 删除单个附件 同时删除文件系统中存储的文件和文件映射表对应字段
     * 
     * @param ysbh_pks
     *            ：附件映射主键
     * @throws TxnException
     */
    public void deleteFile(String ysbh_pks, TxnContext context)
            throws TxnException {
        // 判断附件ID值是否为空
        if (ysbh_pks == null || ysbh_pks.length() <= 0){
        	log.debug("附件映射主键值为空!");
        	/*throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
                    BJAISConfig.get("ggkz.ccgl.wjys.select.ysidnullerror"));*/
        	
        }
            

        // 处理传递的附件映射主键
        String[] ysbhs = null;
        if (ysbh_pks.indexOf(FileConstant.ID_SEPERATOR) != -1) {
            ysbhs = ysbh_pks.split(FileConstant.ID_SEPERATOR);
        } else if (ysbh_pks.indexOf(";") != -1) {
            ysbhs = ysbh_pks.split(";");
        } else {
            ysbhs = new String[1];
            ysbhs[0] = ysbh_pks.trim();
        }

        log.debug("待删除附件主键值：" + ysbh_pks);
        // 循环删除所有附件
        for (int i = 0; i < ysbhs.length; i++) {
            // 若单个附件主键值为空转到下一个主键值
            if (ysbhs[i] == null || ysbhs[i].length() <= 0) {
                continue;
            }

            // 组装文件映射记录数据
            DataBus indb = new DataBus();
            indb.put(VoXtCcglWjys.ITEM_YSBH_PK, ysbhs[i]);
            indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
            indb.put(VoXtCcglWjys.ITEM_WYBS, "");
            indb.put(VoXtCcglWjys.ITEM_CCLJ, "");

            context.addRecord("wjys", indb);

            BaseTable filetable = TableFactory.getInstance().getTableObject(
                    this, WJYS_TABLE);
            try {
                // 查询单条记录用于组装附件存储路径，删除磁盘上的文件
                filetable.executeFunction(SELECT_FUNCTION_PK, context, indb,
                        "wjys");

            } catch (TxnDataException ex) {
                log.error(ex);
                log.debug("附件映射记录为空!");
               /* if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                    throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                            BJAISConfig
                                    .get("ggkz.ccgl.wjys.select.ysnullerror"));
                } else {
                    throw ex;
                }*/
            }
            // 组装文件存储的全路径
            String cclj = context.getRecord("wjys").getValue(
            		VoXtCcglWjys.ITEM_CCLJ);
            String wybs = context.getRecord("wjys").getValue(
            		VoXtCcglWjys.ITEM_WYBS);
            
            // 根据类别编号查询根目录
            String lbpk = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
            //String rootPath = getRootPathByLbPk(lbpk,context);
            String rootPath = getrootPathBycclb(lbpk);
            
            String prePath = rootPath + FileConstant.PATH_SEPERATOR + cclj;

            // 删除磁盘上存储的文件
            delFile(prePath + FileConstant.PATH_SEPERATOR + wybs);
            log.debug("删除附件路径:  " + prePath + FileConstant.PATH_SEPERATOR + wybs);

            // 删除附件的文件映射记录
            filetable.executeFunction(DELETE_FUNCTION, context, indb, "wjys");

            context.remove("wjys");
        }
    }
    
    /*
     * 将数据输入流 交换到 数据输出流 @param input 数据输入流 @param output 数据输出流 @throws
     * IOException
     */
    private void exchangeStream(InputStream input, OutputStream output)
            throws IOException {
        int readLen = 0;
        byte[] readBytes = new byte[8 * 1024];
        while ((readLen = input.read(readBytes)) > 0) {
            output.write(readBytes, 0, readLen);
        }
        output.flush();
    }
    
    /**
     * 根据文件类别名称获取存储根目录及文件类别编号pk
     * 
     * @param fileType
     * @throws TxnException
     */
    private void getRootPathByType(String fileType) throws TxnException {
        BaseTable filetable = TableFactory.getInstance().getTableObject(this,
                WJLB_TABLE);
//        log.info("fileType = " + fileType);
        // 判断文件类型是否为空
        if (fileType == null || fileType.length() == 0){
        	log.debug("文件类别名称为空!");
        	
        	//throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
              //      BJAISConfig.get("ggkz.ccgl.wjys.select.lbnamenullerror"));
        }
         /*root修改20130927-dwn*/   

       // 根据文件类型名称查询最新版本文件类型
        /*String sql = "select xt_ccgl_wjlb.* from xt_ccgl_wjlb where cclbmc ='"+fileType+"' and lbmcbb= (select max(lbmcbb) from xt_ccgl_wjlb where cclbmc ='"+fileType+"') ";
        //String sql = SQLConfig.get("604050200-0001", fileType);
        log.debug("配置数据库查询语句: " + sql);
        DataBus db = new DataBus();
        filetable.executeSelect(sql, db, "wjlb");
        DataBus wjlb = db.getRecord("wjlb");

        // 判断查询最新版本文件类型是否存在
        if (wjlb == null || wjlb.isEmpty()){
        	log.debug("文件类别不存在!");
        	//throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
            //      BJAISConfig.get("ggkz.ccgl.wjys.select.wjlbnullerror"));
        }
            

        // 设置文件类型根路径、类型id、二级目录规则信息
        root = wjlb.getValue(VoXtCcglWjlb.ITEM_CCGML);
        cclbbh_pk = wjlb.getValue(VoXtCcglWjlb.ITEM_CCLBBH_PK);
        ejmlgz = wjlb.getValue(VoXtCcglWjlb.ITEM_EJMLGZ);*/
        
        root = getRootPath(fileType);
        log.info("文件路径为:"+root);
        cclbbh_pk = getcclbString(fileType);
        log.info("文件类型编码为:"+cclbbh_pk);
        ejmlgz = "1";

    }
    
    /**
     * 
     * getRootPath(根据文件类型获取根目录信息)    
     * TODO(这里描述这个方法适用条件 – 可选)    
     * TODO(这里描述这个方法的执行流程 – 可选)    
     * TODO(这里描述这个方法的使用方法 – 可选)    
     * TODO(这里描述这个方法的注意事项 – 可选)    
     * @param fileType
     * @return        
     * String       
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    private String getRootPath(String fileType){
    	String rootPath = "";
    	
    	if("file_upload".equals(fileType)){
    		rootPath = ExConstant.FILE_UPLOAD;
    	}else if ("collect".equals(fileType)){
    		rootPath = ExConstant.COLLECT_RECORD;
    	}else if("share".equals(fileType)){
    		rootPath = ExConstant.SHARE_RECORD;
    	}else if("report".equals(fileType)){
    		rootPath = ExConstant.REPORT;
    	}else if("collect_xml".equals(fileType)){
    		rootPath = ExConstant.COLLECT_XML;
    	}else if("share_xml".equals(fileType)){
    		rootPath = ExConstant.SHARE_XML;
    	}else if("res_tbl".equals(fileType)){
    		rootPath = ExConstant.RES_TBL_RECORD;
    	}else if("file_ftp".equals(fileType)){
    		rootPath = ExConstant.FILE_FTP;
    	}else if("file_database".equals(fileType)){
    		rootPath = ExConstant.FILE_DATABASE;
    	}else if("share_config".equals(fileType)){
    		rootPath = ExConstant.SHARE_CONFIG;
    	}else if("service_target".equals(fileType)){
    		rootPath = ExConstant.SERVICE_TARGET;
    	}else {
    		rootPath = "";
    	}
    	
    	return rootPath;
    }
    
    /**
     * 
     * getcclbString(根据文件类型获取存储类别编码)    
     * TODO(这里描述这个方法适用条件 – 可选)    
     * TODO(这里描述这个方法的执行流程 – 可选)    
     * TODO(这里描述这个方法的使用方法 – 可选)    
     * TODO(这里描述这个方法的注意事项 – 可选)    
     * @param fileType
     * @return        
     * String       
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    private String getcclbString(String fileType){
    	String cclb = "";//存储类别编码
    	
    	if("file_upload".equals(fileType)){
    		cclb = "07";
    	}else if ("collect".equals(fileType)){
    		cclb = "02";
    	}else if("share".equals(fileType)){
    		cclb = "01";
    	}else if("report".equals(fileType)){
    		cclb = "03";
    	}else if("collect_xml".equals(fileType)){
    		cclb = "05";
    	}else if("share_xml".equals(fileType)){
    		cclb = "04";
    	}else if("res_tbl".equals(fileType)){
    		cclb = "06";
    	}else if("file_ftp".equals(fileType)){
    		cclb = "08";
    	}else if("file_database".equals(fileType)){
    		cclb = "10";
    	}else if("share_config".equals(fileType)){
    		cclb = "09";
    	}else if("service_target".equals(fileType)){
    		cclb = "12";
    	}else {
    		cclb = "";
    	}
    	
    	return cclb;
    }
    
    /**
     * 
     * getrootPathBycclb(根据存储类别获取根目录 add by dwn 20140218)    
     * TODO(这里描述这个方法适用条件 – 可选)    
     * TODO(这里描述这个方法的执行流程 – 可选)    
     * TODO(这里描述这个方法的使用方法 – 可选)    
     * TODO(这里描述这个方法的注意事项 – 可选)    
     * @param cclb
     * @return        
     * String       
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    private String getrootPathBycclb(String cclb){
    	
    	String rootPath = "";
    	
    	if("07".equals(cclb)){
    		rootPath = ExConstant.FILE_UPLOAD;
    	}else if ("02".equals(cclb)){
    		rootPath = ExConstant.COLLECT_RECORD;
    	}else if("01".equals(cclb)){
    		rootPath = ExConstant.SHARE_RECORD;
    	}else if("03".equals(cclb)){
    		rootPath = ExConstant.REPORT;
    	}else if("05".equals(cclb)){
    		rootPath = ExConstant.COLLECT_XML;
    	}else if("04".equals(cclb)){
    		rootPath = ExConstant.SHARE_XML;
    	}else if("06".equals(cclb)){
    		rootPath = ExConstant.RES_TBL_RECORD;
    	}else if("08".equals(cclb)){
    		rootPath = ExConstant.FILE_FTP;
    	}else if("10".equals(cclb)){
    		rootPath = ExConstant.FILE_DATABASE;
    	}else if("09".equals(cclb)){
    		rootPath = ExConstant.SHARE_CONFIG;
    	}else if("12".equals(cclb)){
    		rootPath = ExConstant.SERVICE_TARGET;
    	}else {
    		rootPath = "";
    	}
    	
    	return rootPath;
    }
    
    
    /*
     * 在文件系统中创建文件 @param srcFileName ：文件数据源全路径，包括文件名 @param filePath
     * ：文件系统中存储目的全路径，包括文件名
     */
    private void createFile(String srcFileName, String filePath)
            throws TxnErrorException {
        InputStream in = null; // 输入流
        OutputStream out = null; // 输出流

        try {
            log.debug("文件创建路径: " + filePath);
            if (filePath == null || filePath.length() <= 0){
            	log.debug("文件路径为空!");
            	/*throw new TxnErrorException(
                        ErrorConstant.FILE_CREATEPATH_ERROR, BJAISConfig
                                .get("ggkz.ccgl.wjys.io.patherror"));*/
            }
                

            // 创建路径
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 输入流为源文件
            in = new FileInputStream(srcFileName);
            // 输出流为服务器端存储位置
            out = new FileOutputStream(file);

            // 二进制流复制
            exchangeStream(in, out);

        } catch (Exception ex) {
            log.error(ex);
            log.debug("文件路径为空!");
            /*throw new TxnErrorException(ErrorConstant.FILE_CREATEPATH_ERROR,
                    BJAISConfig.get("ggkz.ccgl.wjys.io.patherror"));*/
        } finally {
            try {
                // 关闭数据流
                if(in!=null)
                    in.close();
                if(out!=null)
                    out.close();
            } catch (IOException ioe) {
                log.error(ioe);
                log.debug("文件流关闭错误!");
                /*throw new TxnErrorException(
                        ErrorConstant.FILE_CREATEPATH_ERROR, BJAISConfig
                                .get("ggkz.ccgl.wjys.io.closeerror"));*/
            }
        }
    }

    /*
     * 在文件系统中创建文件 @param InputStream ：文件数据源二进制数据流 @param filePath
     * ：文件系统中存储目的全路径，包括文件名
     */
    public void createFile(InputStream in, String filePath)
            throws TxnErrorException {
        OutputStream out = null; // 输出流
        try {
            log.debug("文件创建路径 : " + filePath);
            if (filePath == null || filePath.length() <= 0){
            	log.debug("文件路径为空!");
            	/*throw new TxnErrorException(
                        ErrorConstant.FILE_CREATEPATH_ERROR, BJAISConfig
                                .get("ggkz.ccgl.wjys.io.patherror"));*/
            }
                

            // 创建路径
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 输出流为服务器端存储位置
            out = new FileOutputStream(file);
            // 二进制流复制
            exchangeStream(in, out);

        } catch (Exception ex) {
            log.error(ex);
            log.debug("文件路径为空!");
            /*throw new TxnErrorException(ErrorConstant.FILE_CREATEPATH_ERROR,
                    BJAISConfig.get("ggkz.ccgl.wjys.io.patherror"));*/
        } finally {
            try {
                // 关闭数据流
                if(in!=null)
                    in.close();
                if(out!=null)
                    out.close();
            } catch (IOException ioe) {
                log.error(ioe);
                log.debug("文件流关闭错误!");
                /*throw new TxnErrorException(
                        ErrorConstant.FILE_CREATEPATH_ERROR, BJAISConfig
                                .get("ggkz.ccgl.wjys.io.closeerror"));*/
            }
        }
    }

    // 根据文件存储路径获取输出流
    public InputStream getOutputStream(String path) {
        InputStream result = null;
        try {
            result = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            log.error(e);
        }
        return result;
    }

    public String getRootDir() {
        return root;
    }

    public String getEjmlgz() {
        return ejmlgz;
    }

    public String getCclbbhPk() {
        return cclbbh_pk;
    }

    /*
     * 删除文件系统中的文件 @param fullPath ：文件在文件系统中的全路径，包括文件名 @return
     */
    public boolean delFile(String fullPath) {
        boolean result = false;
        File file = new File(fullPath);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }
    
    /*
     * 根据文件类型设置的二级目录规则生成二级目录 @param ejml ：业务模块传递的二级目录名 @return
     */
    public String getSecondDir(String ejml) {
        String secondDir = null;
        
        // 如果二级目录传递参数，设置二级目录为传递的路径
        if((ejml != null && ejml.length() > 0)){
            secondDir = ejml;
        }
        // 任意方式：由业务模块提供二级目录
        else if ("0".equals(ejmlgz) && (ejml != null && ejml.length() > 0)) {
            secondDir = ejml;
        }
        // 系统时间方式：根据系统时间创建二级目录
        else if ("1".equals(ejmlgz)) {
            secondDir = getDirByTime();
        }
        // 默认二级目录，用于程序未定义二级目录时使用
        else {
            secondDir = DEFAULT_SECOND_DIR;
        }

        // 在存储磁盘创建二级目录文件夹
        createFolder(root + FileConstant.PATH_SEPERATOR + secondDir
                + FileConstant.PATH_SEPERATOR);
        log.debug("二级目录：" + secondDir);
        return secondDir;
    }

    /** 根据文件存储模块的文件映射主键值，拷贝附件
     * @param fjid
     *            附件ID
     * @param filePath
     *            文件输出路径
     * @param type
     *            文件类型
     * @return boolean
     * @author yangxf 作用:copy对应附件的文件实体到指定的目录下
     */
    public boolean copyFileWithFjID(String fjid, String filePath, String type)
            throws TxnException {
        return copyFileWithFjID(fjid, null, filePath, type);
    }
    
    /** 根据文件全路径，拷贝附件
     * @param fullPath
     *            源文件全路径
     * @param filePath
     *            文件输出路径
     * @param type
     *            文件类型
     * @return boolean
     * @author yangxf 作用:copy对应附件的文件实体到指定的目录下
     */
    public boolean copyFileWithPath(String fullPath, String filePath, String type)
    throws TxnException {
        return copyFileWithFjID(null, fullPath, filePath, type);
    }
    
    private boolean copyFileWithFjID( String fjid, String orifullPath, String filePath, String type)
    throws TxnException {

        UploadFileManager uploadFileManager = null;
        FileInputStream stream = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        String str_fileFullPath = filePath.substring(0, filePath
                .lastIndexOf("/") + 1);
        log.info("通过附件ID执行文件copy操作，文件copy的全路径为:" + str_fileFullPath);

        // 创建目录
        FileManager.getInstance(filePath).checkFolder(str_fileFullPath);

        File file = new File(filePath);
        try {
            uploadFileManager = UploadFileManager.getInstance(type);           
            // 读文件流操作
            // 如果源文件全路径参数传递，则直接根据路径获得文件流
            if(orifullPath!=null && orifullPath.length()>0){
                stream = (FileInputStream) uploadFileManager.getRemoteStreamByFilePath(orifullPath);
            }else{
            // 如果源文件文件存储id参数传递，则根据id取路径获得文件流
                stream = (FileInputStream) uploadFileManager
                    .getRemoteStreamByFileID(fjid);
            }
            log.info("开始读文件流!" + stream.toString());

            // 写文件流
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);// 建立一个上传文件的输出流
            int i_bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((i_bytesRead = stream.read(buffer, 0, 1024)) > 0) {
                bos.write(buffer, 0, i_bytesRead);// 将文件写入服务器
            }
            bos.flush();
        } catch (Exception e) {           
            log.error("通过附件ID无法找到对应的附件实体，请核实后再试 "+e.fillInStackTrace());
            //throw new TxnErrorException(ErrorConstant.FILE_NOTEXIST,BJAISConfig.get("ggkz.ccgl.wjys.io.copyfileerror"));           
        
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (bos != null)
                    bos.close();
                if (stream != null)
                    stream.close();
            } catch (IOException ioe) {
                log.error("关闭文件流错误："+ioe.fillInStackTrace());
                //throw new TxnErrorException(ErrorConstant.FILE_CLOSE_ERROR,BJAISConfig.get("ggkz.ccgl.wjys.io.closeerror"));           
                
            }
        }
        return true;
    }

    /*
     * 根据二级目录规则=日期规则生成二级目录 格式为：年/月/日。如：2006/2006-11/2006-11-20 @return
     */
    private String getDirByTime() {
        String year = CalendarUtil.getCurrYear();
        String month = CalendarUtil.getCurrMonth();
        String yyyymmdd = CalendarUtil
                .getCalendarByFormat(CalendarUtil.FORMAT11);
        String dir = year + FileConstant.PATH_SEPERATOR + year + "-" + month
                + FileConstant.PATH_SEPERATOR + yyyymmdd;

        return dir;
    }

    /*
     * 创建目录 @param fullPath
     */
    private void createFolder(String fullPath) {
        FileManager fileManager = FileManager.getInstance(root);
        fileManager.checkFolder(fullPath);
    }
    
    protected String getRootPathByLbPk(String cclbbh_pk, TxnContext context) throws TxnException{
        String rootPath;
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJLB_TABLE);
        // 根据文件类型主键值查询文件存储根路径
        String sql = "select ccgml from xt_ccgl_wjlb where cclbbh_pk = '"+cclbbh_pk+"' ";
        //String sql = SQLConfig.get("604050200-0003", cclbbh_pk);
        table.executeSelect(sql, context, "rootwjlb");
        
        DataBus wjlb = context.getRecord("rootwjlb");        
        // 判断查询最新版本文件类型是否存在
        if (wjlb == null || wjlb.isEmpty())
        	log.debug("文件类别不存在!");
            //throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
            							//"文件类别不存在!");
                                        //BJAISConfig.get(WJLBNULLERROR));

        // 设置文件类型根路径、类型id、二级目录规则信息
        rootPath = wjlb.getValue(VoXtCcglWjlb.ITEM_CCGML);
        context.remove("rootwjlb");     
        return rootPath;
    }
    
    
}
