package com.gwssi.common.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.gwssi.common.constant.FileConstant;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.file.manage.vo.VoXtCcglWjlb;
import com.gwssi.file.manage.vo.VoXtCcglWjys;

import cn.gwssi.common.component.exception.ErrorConstant;
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
import cn.gwssi.common.txn.TxnService;

/**
 * @desc 附件上传操作类
 * 操作包括:上传、修改、删除、获取文件路径等
 * 附件内容包括两部分：存储磁盘上的附件正文；数据库中文件映射记录。
 * 附件操作对上述两部分的附件内容同步操作
 * 支持两种附件来源：烽火台附件对象VoFileInfo;二进制流InputStream
 * @author adaFang
 * @version 1.0
 *
 */
public class UploadFileManager extends TxnService
{

	private static final String WJLBNULLERROR = "ggkz.ccgl.wjys.select.wjlbnullerror";

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
            .getLogger(UploadFileManager.class.getName());

    // 文件根目录
    private static String root;

    // 存储类别编号主键值
    private static String cclbbh_pk;

    // 二级目录规则
    private static String ejmlgz;

    // 默认文件目录，用于没有设置二级目录的情况
    private static final String DEFAULT_SECOND_DIR = "default";
	
	@Override
	protected void prepare(TxnContext arg0) throws TxnException
	{
		// TODO Auto-generated method stub
		
	}
	
	private UploadFileManager(String fileType) throws TxnException {
        getRootPathByType(fileType);
    }

    /**
     * 获取上传管理单例实例
     * 
     * @param fileType :
     *            文件所属类别，由ConstUploadFileType统一维护
     * @return UploadFileManager : 上传管理实例
     * @throws TxnException
     */
    public static UploadFileManager getInstance(String fileType)
                                                                throws TxnException {

        UploadFileManager manager = new UploadFileManager(fileType);
        return manager;
    }

    /**
     * 保存单个附件
     * 
     * @param voFile
     *            ：烽火台上传文件VO对象
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile) throws TxnException {
        return saveFile(voFile, "");
    }

    /**
     * 保存单个附件
     * 
     * @param voFile
     *            ：烽火台上传文件VO对象
     * @param ywbz :
     *            业务备注，用于存储所属业务模块主键值
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz) throws TxnException {
        return saveFile(voFile, ywbz, null);
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
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz, String ejml)
                                                                        throws TxnException {
        return saveFile(voFile, ywbz, ejml, "");
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
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus saveFile(VoFileInfo voFile, String ywbz, String ejml,
            String xm_fk) throws TxnException {
        if (voFile == null || voFile.getOriFileName().trim().length() == 0)
            return null;
        
        // 获取上传附件信息
        String oriFileName = voFile.getOriFileName().trim();// 附件名称(原始文件名)
        String tempFileName = voFile.getValue(VoCtrl.ITEM_SVR_FILENAME);// 临时文件名称
        //log.debug("Upload:::tempFileName==="+tempFileName);
        String uploadFileName = tempFileName.substring(tempFileName
                .lastIndexOf(FileConstant.PATH_SEPERATOR1) + 1);
        
        
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
        String cjsj = CalendarUtil.getCurrentDateTime();
        // 组合上传附件的全路径：文件类型的根目录+二级目录+文件名称
        String prePath = root + FileConstant.PATH_SEPERATOR + secondDir;
        String filePath = prePath + FileConstant.PATH_SEPERATOR + uploadFileName;
        // 新建回传对象DataBus
        DataBus result = new DataBus();
        
        // 创建文件目录文件
        createFile(tempFileName, filePath);
        // 删除临时文件
        delFile(tempFileName);
        
        try {
            TxnContext context = new TxnContext();
            DataBus db = new DataBus();
            // 设置输入数据节点
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
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, WJYS_TABLE);
            table.executeFunction(INSERT_FUNCTION, context, db, "wjys");
            
            // 返回附件id值和附件name值
            result.put(FileConstant.file_id, context.getRecord("wjys")
                    .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
            result.put(FileConstant.file_name, context.getRecord("wjys")
                    .getValue(VoXtCcglWjys.ITEM_WJMC));

            // 清除context中的文件映射表数据
            context.remove("wjys");
            
        } catch (Exception e) {
            log.error("文件映射关系存储数据库错误："+e.fillInStackTrace());
            log.equals("附件创建失败!");
            //throw new TxnErrorException(ErrorConstant.FILE_CREATE_ERROR,BJAISConfig.get("ggkz.ccgl.wjys.io.createerror"));
        }

        return result;
    }

    /**
     * 保存单个附件
     * 
     * @param InputStream
     *            ：上传文件二进制流
     * @param oriFileName
     *            ：文件名称
     * @param uploadFileName
     *            ：存储文件夹名称
     * @param ywbz :
     *            业务备注，用于存储所属业务模块主键值
     * @param ejml
     *            ：二级目录名称
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus saveFile(InputStream fileStream, String oriFileName,
            String uploadFileName, String ywbz, String ejml)
                                                            throws TxnException {
        return saveFile(fileStream, oriFileName, uploadFileName, ywbz, ejml, "");
    }

    /**
     * 保存单个附件
     * 
     * @param InputStream
     *            ：上传文件二进制流
     * @param oriFileName
     *            ：文件名称
     * @param uploadFileName
     *            ：存储文件夹名称
     * @param ywbz :
     *            业务备注，用于存储所属业务模块主键值
     * @param ejml
     *            ：二级目录名称
     * @param xm_fk :
     *            项目主键 用于现场机关数据交换（修改时间：2007年01月25日）
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus saveFile(InputStream fileStream, String oriFileName,
            String uploadFileName, String ywbz, String ejml, String xm_fk)
                                                                          throws TxnException {

        String secondDir = getSecondDir(ejml);
        String cjsj = CalendarUtil.getCurrentDateTime();
        // 组合上传附件的全路径：文件类型的根目录+二级目录+文件名称
        String prePath = root + FileConstant.PATH_SEPERATOR + secondDir;
        String filePath = prePath + FileConstant.PATH_SEPERATOR + uploadFileName;

        // 创建文件
        createFile(fileStream, filePath);

        
        /**
         * bug修改：修改附件名称有英文逗号时文件名错乱问题
         * 修改人：方爱丹
         * 修改时间：2007-8-4
         */
        log.debug("替换英文逗号、英文单引号开始： "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("替换英文逗号、英文单引号开始结束："+oriFileName);
        
        TxnContext context = new TxnContext();
        DataBus db = new DataBus();  
        // 设置输入数据节点
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
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);
        table.executeFunction(INSERT_FUNCTION, context, db, "wjys");

        log.debug("保存附件结点值：" + context.getRecord("wjys"));
        
        // 设置回传值
        DataBus result = new DataBus();
        // 返回附件id值和附件name值
        result.put(FileConstant.file_id, context.getRecord("wjys")
                .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
        result.put(FileConstant.file_name, context.getRecord("wjys")
                .getValue(VoXtCcglWjys.ITEM_WJMC));

        // 清除context中的文件映射表数据
        context.remove("wjys");
        return result;
    }

    /**
     * 保存单个附件，用于烽火台Action交易调用二进制流方式上传附件
     * 
     * @param InputStream
     *            ：上传文件二进制流
     * @param oriFileName
     *            ：文件名称
     * @param uploadFileName
     *            ：存储文件夹名称
     * @param ywbz :
     *            业务备注，用于存储所属业务模块主键值
     * @param ejml
     *            ：二级目录名称
     * @param context :
     *            烽火台数据总线
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus saveFile(InputStream fileStream, String oriFileName,
            String uploadFileName, String ywbz, String ejml, TxnContext context)
                                                                                throws TxnException {
        return saveFile(fileStream, oriFileName, uploadFileName, ywbz, ejml,
                        "", context);
    }

    /**
     * 保存单个附件，用于烽火台Action交易调用二进制流方式上传附件
     * 
     * @param InputStream
     *            ：上传文件二进制流
     * @param oriFileName
     *            ：文件名称
     * @param uploadFileName
     *            ：存储文件夹名称
     * @param ywbz :
     *            业务备注，用于存储所属业务模块主键值
     * @param ejml
     *            ：二级目录名称
     * @param xm_fk :
     *            项目主键 用于现场机关数据交换（修改时间：2007年01月25日）
     * @param context :
     *            烽火台数据总线
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus saveFile(InputStream fileStream, String oriFileName,
            String uploadFileName, String ywbz, String ejml, String xm_fk,
            TxnContext context) throws TxnException {
        String secondDir = getSecondDir(ejml);
        String cjsj = CalendarUtil.getCurrentDateTime();
        // 组合上传附件的全路径：文件类型的根目录+二级目录+文件名称
        String prePath = root + FileConstant.PATH_SEPERATOR + secondDir;
        String filePath = prePath + FileConstant.PATH_SEPERATOR + uploadFileName;
        // 创建文件
        createFile(fileStream, filePath);
        
        
        /**
         * bug修改：修改附件名称有英文逗号时文件名错乱问题
         * 修改人：方爱丹
         * 修改时间：2007-8-4
         */
        log.debug("替换英文逗号、英文单引号开始： "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("替换英文逗号、英文单引号开始结束："+oriFileName);
        
        // 组装附件数据存入文件映射表
        DataBus db = new DataBus();
        // 设置输入数据节点
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
        // 存入数据库表
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);
        table.executeFunction(INSERT_FUNCTION, context, db, "wjys");

        log.debug("保存附件结点值：" + context.getRecord("wjys"));
        // 返回附件主键id值和附件名称值
        DataBus result = new DataBus();
        result.put(FileConstant.file_id, context.getRecord("wjys")
                .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
        result.put(FileConstant.file_name, context.getRecord("wjys")
                .getValue(VoXtCcglWjys.ITEM_WJMC));

        log.debug("回传附件id值："
                  + context.getRecord("wjys").getValue("ysbh_pk"));
        // 清除context中的文件映射表数据
        context.remove("wjys");
        return result;
    }

    public DataBus updateFile(InputStream fileStream, String ysbh_pk,
            String oriFileName, String uploadFileName, String ywbz)
                                                                   throws TxnException {
        // 设置修改时间
        String scxgsj = CalendarUtil.getCurrentDateTime();
        
        
        /**
         * bug修改：修改附件名称有英文逗号时文件名错乱问题
         * 修改人：方爱丹
         * 修改时间：2007-8-4
         */
        log.debug("替换英文逗号、英文单引号开始： "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("替换英文逗号、英文单引号开始结束："+oriFileName);
        
        // 组装附件信息
        TxnContext context = new TxnContext();
        DataBus indb = new DataBus();
        indb.put(VoXtCcglWjys.ITEM_YSBH_PK, ysbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLJ, "");
        indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        indb.put(VoXtCcglWjys.ITEM_SCXGSJ, scxgsj);
        context.addRecord("wjys", indb);
        
        // 查询原附件信息,用于删除原附件
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);      
        try{
            // 获取文件存储原存储路径信息
            table.executeFunction(SELECT_FUNCTION_PK, context, indb, "wjys");
        } catch (TxnDataException ex) {
            log.error(ex);
            log.error("附件映射记录为空!");
            /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                BJAISConfig.get("ggkz.ccgl.wjys.select.ysnullerror"));
            }else{
                throw ex;
            }*/
            
        }
        // 根据文件映射记录组装附件在磁盘空间的二级存储路径
        String cclj = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLJ);
        String wybs = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_WYBS);
        
        // 根据类别编号查询根目录
        String lbpk = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
        String rootPath = getRootPathByLbPk(lbpk,context);
        
        // 原附件存放路径信息
        String oldprePath = rootPath + FileConstant.PATH_SEPERATOR + cclj;
        
        String newprePath = root + FileConstant.PATH_SEPERATOR + cclj;

        // 删除原附件
        delFile(oldprePath + FileConstant.PATH_SEPERATOR + wybs);

        String filePath = newprePath + FileConstant.PATH_SEPERATOR + uploadFileName;
        // 创建新附件
        createFile(fileStream, filePath);

        indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        
        // 更新附件映射信息
        table.executeFunction(UPDATE_FILEWYBS_FUNCTION, context, indb, "wjys");
        
        // 回传附件id/name值
        DataBus result = new DataBus();
        result.put(FileConstant.file_id, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
           result.put(FileConstant.file_name, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_WJMC));

        // 清除context中的文件映射表数据
        context.remove("wjys");
        return result;
    }

    /**
     * 更新附件，只修改附件名称信息，用于烽火台Action交易调用二进制流方式上传附
     * 
     * @param ysbh_pk :
     *            附件主键id值
     * @param oriFileName
     *            ：文件名称
     * @param uploadFileName
     *            ：存储文件夹名称
     * @param ywbz :
     *            业务备注，用于存储所属业务模块主键值
     * @param context :
     *            烽火台数据总线
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus updateFile(String ysbh_pk, String oriFileName,
            String uploadFileName, String ywbz, TxnContext context)
                                                                   throws TxnException {
        return updateFile(null, ysbh_pk, oriFileName, uploadFileName, ywbz,
                          context);
    }

    /**
     * 更新附件，只修改附件名称信息，用于烽火台Action交易调用二进制流方式上传附
     * 
     * @param fileStream :
     *            上传文件二进制流
     * @param ysbh_pk :
     *            附件主键id值
     * @param oriFileName
     *            ：文件名称
     * @param uploadFileName
     *            ：存储文件夹名称
     * @param ywbz :
     *            业务备注，用于存储所属业务模块主键值
     * @param context :
     *            烽火台数据总线
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus updateFile(InputStream fileStream, String ysbh_pk,
            String oriFileName, String uploadFileName, String ywbz,
            TxnContext context) throws TxnException {
        // 设置修改时间
        String scxgsj = CalendarUtil.getCurrentDateTime();
        
        
        /**
         * bug修改：修改附件名称有英文逗号时文件名错乱问题
         * 修改人：方爱丹
         * 修改时间：2007-8-4
         */
        log.debug("替换英文逗号、英文单引号开始： "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("替换英文逗号、英文单引号开始结束："+oriFileName);
        
        // 组装附件信息
        DataBus indb = new DataBus();        
        indb.put(VoXtCcglWjys.ITEM_YSBH_PK, ysbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLJ, "");
        if (uploadFileName != null && !"".equals(uploadFileName)) {
            indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        }
        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        
        
        indb.put(VoXtCcglWjys.ITEM_SCXGSJ, scxgsj);
        context.addRecord("wjys", indb);
        
        // 查询原附件信息,用于删除原附件
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);
        try{
            // 获取文件存储原存储路径信息
            table.executeFunction(SELECT_FUNCTION_PK, context, indb, "wjys");
        } catch (TxnDataException ex) {
            log.error(ex);
            log.error("附件映射记录为空!");
            /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                                            BJAISConfig.get("ggkz.ccgl.wjys.select.ysnullerror"));
            }else{
                throw ex;
            }*/
        }
        try {
            // 若修改传递的文件流不为空，则更新上传目录上原文件
            // 否则不更改目录下的文件
            if (fileStream != null && (fileStream.read() != -1)) {
                // 根据文件映射记录组装附件在磁盘空间的二级存储路径
                String cclj = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLJ);
                String wybs = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_WYBS);
                
                // 根据类别编号查询根目录
                String lbpk = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
                String rootPath = getRootPathByLbPk(lbpk,context);
                
                // 原附件存放路径信息
                String oldprePath = rootPath + FileConstant.PATH_SEPERATOR + cclj;
                
                String newprePath = root + FileConstant.PATH_SEPERATOR + cclj;
                
                
                // 删除原附件
                delFile(oldprePath + FileConstant.PATH_SEPERATOR + wybs);

                String filePath = newprePath + FileConstant.PATH_SEPERATOR + uploadFileName;
                // 创建新附件
                createFile(fileStream, filePath);
            }
        } catch (IOException e) {
            log.error(e);
            log.error("文件流关闭错误!");
            /*throw new TxnErrorException(
                                        ErrorConstant.FILE_CREATEPATH_ERROR,
                                        BJAISConfig.get("ggkz.ccgl.wjys.io.closeerror"));*/
        }
       
        // 修改文件名称
        String updateFunction = UPDATE_FILE_FUNCTION;
        
        // 修改文件唯一标识和文件名称
        if (uploadFileName != null && !"".equals(uploadFileName)) {
            indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
            updateFunction = UPDATE_FILEWYBS_FUNCTION;
        }

        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        
        // 更新附件映射信息
        table.executeFunction(updateFunction , context, indb, "wjys");
        
        // 回传附件id/name值
        DataBus result = new DataBus();
        result.put(FileConstant.file_id, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
           result.put(FileConstant.file_name, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_WJMC));

        // 清除context中的文件映射表数据
        context.remove("wjys");
        return result;
    }

    /**
     * 修改单个附件
     * 
     * @param voFile
     *            ：烽火台上传文件VO对象
     * @param ysbh_pk :
     *            附件映射主键PK值
     * @return DataBus : 结点名如下 附件映射主键-FileConstant.file_id，附件名-FileConstant.file_name；
     *         字段名由sys.FileConstant维护
     * @throws TxnException
     */
    public DataBus updateFile(VoFileInfo voFile, String ysbh_pk)
                                                                throws TxnException {
        if (voFile == null || voFile.getOriFileName().trim().length() == 0) {
            return null;
        }
        // 获取上传附件信息
        String oriFileName = voFile.getOriFileName().trim();// 附件名称(原始文件名)
        String tempFileName = voFile.getValue(VoCtrl.ITEM_SVR_FILENAME);// 临时文件名称
        String uploadFileName = tempFileName.substring(tempFileName
                .lastIndexOf(FileConstant.PATH_SEPERATOR) + 1);

        
        /**
         * bug修改：修改附件名称有英文逗号时文件名错乱问题
         * 修改人：方爱丹
         * 修改时间：2007-8-4
         */
        log.debug("替换英文逗号、英文单引号开始： "+oriFileName);     
        oriFileName = UploadFilter.instance().filterFileNameForEnglishComma(oriFileName);
        oriFileName = UploadFilter.instance().filterFileNameForEnglishSingleQuotes(oriFileName);
        log.debug("替换英文逗号、英文单引号开始结束："+oriFileName);
        
        String scxgsj = CalendarUtil.getCurrentDateTime(); // 设置修改时间
        
        // 组装附件信息
        TxnContext context = new TxnContext();
        DataBus indb = new DataBus();
        indb.put(VoXtCcglWjys.ITEM_YSBH_PK, ysbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
        indb.put(VoXtCcglWjys.ITEM_CCLJ, "");
        indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        indb.put(VoXtCcglWjys.ITEM_SCXGSJ, scxgsj);
        // 设置查询回传数据
        context.addRecord("wjys", indb);

        // 查询原附件信息,用于删除原附件
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);
        try{
            // 获取文件存储原存储路径信息 
            table.executeFunction(SELECT_FUNCTION_PK, context, indb, "wjys");
        } catch (TxnDataException ex) {
            log.error(ex);
            log.error("附件映射记录为空!");
            /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                                            BJAISConfig.get("ggkz.ccgl.wjys.select.ysnullerror"));
            }else{
                throw ex;
            }*/
        }
        // 根据文件映射记录组装附件在磁盘空间的二级存储路径
        String cclj = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLJ);
        String wybs = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_WYBS);

        // 根据类别编号查询根目录
        String lbpk = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
        String rootPath = getRootPathByLbPk(lbpk,context);
        
        String oldprePath = rootPath + FileConstant.PATH_SEPERATOR + cclj;
        String newprePath = root + FileConstant.PATH_SEPERATOR + cclj;
        
        // 删除原附件
        delFile(oldprePath + FileConstant.PATH_SEPERATOR + wybs);

        String filePath = newprePath + FileConstant.PATH_SEPERATOR + uploadFileName;
        // 创建新附件
        createFile(tempFileName, filePath);

        // 删除临时文件
        delFile(tempFileName);

        indb.put(VoXtCcglWjys.ITEM_WYBS, uploadFileName);
        indb.put(VoXtCcglWjys.ITEM_WJMC, oriFileName);
        
        // 更新附件映射信息
        table.executeFunction(UPDATE_FILEWYBS_FUNCTION, context, indb, "wjys");

        log.debug("修改附件结点值： " + context.getRecord("wjys"));
        DataBus result = new DataBus();
        // 回传附件id/name值
        result.put(FileConstant.file_id, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_YSBH_PK));
           result.put(FileConstant.file_name, context.getRecord("wjys")
                   .getValue(VoXtCcglWjys.ITEM_WJMC));

        // 清除context中的文件映射表数据
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
    public void deleteFile(String ysbh_pks) throws TxnException {
        TxnContext context = new TxnContext();
        
        // 判断附件ID值是否为空
        if(ysbh_pks == null || ysbh_pks.length()<=0){
        	log.debug("附件映射记录为空!");
        	/*throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
            BJAISConfig.get("ggkz.ccgl.wjys.select.ysidnullerror"));*/
        	
        }
            
        
        // 拆分附件值，间隔符为英文半角逗号
        String[] ysbhs = ysbh_pks.split(FileConstant.ID_SEPERATOR);
        log.debug("待删除附件主键值：" + ysbh_pks);
        // 循环删除
        for (int i = 0; i < ysbhs.length; i++) {           
            DataBus indb = new DataBus();
            log.debug("附件值 :　" + ysbhs[i]);
            // 若单个附件主键值为空转到下一个主键值
            if (ysbhs[i] == null || ysbhs[i].length() <= 0) {
                continue;
            }

            // 组装文件映射记录数据
            indb.put(VoXtCcglWjys.ITEM_YSBH_PK, ysbhs[i]);
            indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, cclbbh_pk);
            indb.put(VoXtCcglWjys.ITEM_WYBS, "");
            indb.put(VoXtCcglWjys.ITEM_CCLJ, "");
            context.addRecord("wjys", indb);
            
            // 查询原附件信息,用于删除原附件
            BaseTable table = TableFactory.getInstance()
                    .getTableObject(this, WJYS_TABLE);
            try{
                // 获取文件存储原存储路径信息 
                table.executeFunction(SELECT_FUNCTION_PK, context, indb, "wjys");
            } catch (TxnDataException ex) {
                log.error(ex);
                log.debug("附件映射记录为空!");
                /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                    throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                                                BJAISConfig.get("ggkz.ccgl.wjys.select.ysnullerror"));
                }else{
                    throw ex;
                }*/
            }
            // 组装文件存储的全路径
            String cclj = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLJ);
            String wybs = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_WYBS);

            // 根据类别编号查询根目录
            String lbpk = context.getRecord("wjys").getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
            String rootPath = getRootPathByLbPk(lbpk,context);
            
            String prePath = rootPath + FileConstant.PATH_SEPERATOR + cclj;
            // 删除附件
            delFile(prePath + FileConstant.PATH_SEPERATOR + wybs);
            log.debug("删除附件路径:  " + prePath + FileConstant.PATH_SEPERATOR + wybs);
            // 删除附件记录
            table.executeFunction(DELETE_FUNCTION, context, indb, "wjys");

            // 清除context中的文件映射表数据
            context.remove("wjys");
        }
    }

    /**
     * 根据主键值获取附件二进制流
     * @param fileID 附件主键值
     * @return
     * @throws TxnException
     */
    public InputStream getRemoteStreamByFileID(String fileID)
                                                             throws TxnException {
        InputStream result = null;
        TxnContext context = new TxnContext();
        
        // 根据附件id获得全路径
        String path = getFullPathByFileID(fileID,context);
        
        // 根据全路径获得附件二进制流
        result = getRemoteStreamByFilePath(path);
        
        return result;
    }
    
    public InputStream getRemoteStreamByFilePath(String fullPath)
    throws TxnException {
        
        InputStream result = null;
        // 根据全路径生成文件流
        try {
            result = new FileInputStream(new File(fullPath));
        } catch (FileNotFoundException e) {
            log.error("根据全路径生成文件流："+e.fillInStackTrace());
            log.error("通过附件ID无法找到对应的附件实体，请核实后再试!");
            //throw new TxnErrorException(ErrorConstant.FILE_READERROR,BJAISConfig.get("ggkz.ccgl.wjys.io.copyfileerror"));
        }

        return result;
    }

    /**
     * 返回附件全路径 增加时间:20070131 by ada
     * 
     * @param fileID :
     *            附件ID
     * @param context :
     *            烽火台数据结构对象
     * @return String : 附件全路径
     * @throws TxnException
     */
    public String getFullPathByFileID(String fileID, TxnContext context)
                                                                        throws TxnException {
        String path = null;

        DataBus indb = new DataBus();
        // 组装存储文件映射对象数据库对象
        indb.put(VoXtCcglWjys.ITEM_YSBH_PK, fileID);
        indb.put(VoXtCcglWjys.ITEM_CCLBBH_PK, "");
        indb.put(VoXtCcglWjys.ITEM_CCLJ, "");
        indb.put(VoXtCcglWjys.ITEM_WYBS, "");  
        context.addRecord("input", indb);
        context.addRecord("wjys", indb);
        
        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJYS_TABLE);
        try{
            // 获取文件存储原存储路径信息 
            table.executeFunction(SELECT_FUNCTION_PK, context, "input", "wjys");       
        } catch (TxnDataException ex) {
            log.error(ex);
            log.error("附件映射记录为空!");
            /*if (ex.getErrCode().compareTo(ErrorConstant.ECODE_SQL_NOTFOUND) == 0) {
                throw new TxnErrorException(ErrorConstant.SQL_SELECT_NOROW,
                                            BJAISConfig.get("ggkz.ccgl.wjys.select.ysnullerror"));
            }else{
                throw ex;
            }*/
        }
            DataBus db = context.getRecord("wjys");

        // 根据文件映射记录组装附件在磁盘空间的二级存储路径
        String cclj = db.getValue(VoXtCcglWjys.ITEM_CCLJ);
        String wybs = db.getValue(VoXtCcglWjys.ITEM_WYBS);
        
        // 根据类别编号查询根目录
        String lbpk = db.getValue(VoXtCcglWjys.ITEM_CCLBBH_PK);
        String rootPath = getRootPathByLbPk(lbpk,context);

        // 根据文件类型存储根目录和二级存储路径组装文件存储全路径
        path = rootPath.concat(FileConstant.PATH_SEPERATOR).concat(cclj).concat(FileConstant.PATH_SEPERATOR).concat(wybs);
        log.debug("附件id: " + fileID + " 文件全路径 ：" + path);
        
        

        // 清除context中的文件映射表数据
        context.remove("input");
        context.remove("wjys");
        return path;
    }

    /**
     * 根据文件类别名称获取存储根目录及文件类别编号pk
     * 
     * @param fileType
     * @throws TxnException
     */
    private void getRootPathByType(String fileType) throws TxnException {

        BaseTable table = TableFactory.getInstance().getTableObject(this,
                                                                    WJLB_TABLE);
        // 根据文件类型名称查询最新版本文件类型
        //String sql = SQLConfig.get("604050200-0001", fileType);
        String sql = "select xt_ccgl_wjlb.* from xt_ccgl_wjlb where cclbmc ='"+fileType+"' and lbmcbb= (select max(lbmcbb) from xt_ccgl_wjlb where cclbmc ="+fileType+"') ";
       
        DataBus db = new DataBus();
        table.executeSelect(sql, db, "wjlb");
        DataBus wjlb = db.getRecord("wjlb");
        
        // 判断查询最新版本文件类型是否存在
        if (wjlb == null || wjlb.isEmpty()){
        	log.debug("文件类别不存在!");
        	/*throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
                    BJAISConfig.get(WJLBNULLERROR));*/
        }
            

        // 设置文件类型根路径、类型id、二级目录规则信息
        root = wjlb.getValue(VoXtCcglWjlb.ITEM_CCGML);
        cclbbh_pk = wjlb.getValue(VoXtCcglWjlb.ITEM_CCLBBH_PK);
        ejmlgz = wjlb.getValue(VoXtCcglWjlb.ITEM_EJMLGZ);
        
        log.debug("根目录： " + root);
        log.debug("存储类别编号主键值： " + cclbbh_pk);
        log.debug("二级目录规则： " + ejmlgz);
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

    /*
     * 在文件系统中创建文件 @param srcFileName ：文件数据源全路径，包括文件名 @param filePath
     * ：文件系统中存储目的全路径，包括文件名
     */
    private void createFile(String srcFileName, String filePath) throws TxnErrorException {
        
        InputStream in = null; // 输入流
        OutputStream out = null; // 输出流
        try {
            log.debug(" 创建文件全路径： " + filePath);          
            // 创建路径
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            in = new FileInputStream(srcFileName); // 输入流为源文件
            out = new FileOutputStream(file); // 输出流为服务器端存储位置

            // 二进制流复制
            exchangeStream(in, out);
            
        } catch (Exception ex) {
                log.error("附件正文创建错误: "+ex);
                log.error("附件创建失败!");
            //throw new TxnErrorException(ErrorConstant.ACTION_ADDCODE_ERROR,BJAISConfig.get("ggkz.ccgl.wjys.io.createerror"));
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
                                            ErrorConstant.FILE_CREATEPATH_ERROR,
                                            BJAISConfig.get("ggkz.ccgl.wjys.io.closeerror"));*/
            }
        }
    }

    /*
     * 在文件系统中创建文件 @param InputStream ：文件数据源二进制数据流 @param filePath
     * ：文件系统中存储目的全路径，包括文件名
     */
    private void createFile(InputStream in, String filePath) throws TxnErrorException {
        OutputStream out = null; // 输出流
        try {
            log.debug("磁盘创建上传文件路径： " + filePath);
            // 创建路径
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            
            out = new FileOutputStream(file); // 输出流为服务器端存储位置
            
            // 二进制流复制
            exchangeStream(in, out);

        }catch (Exception ex) {
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
                log.error("关闭文件流错误： "+ioe);
                /*throw new TxnErrorException(
                                            ErrorConstant.FILE_CREATEPATH_ERROR,
                                            BJAISConfig.get("ggkz.ccgl.wjys.io.closeerror"));*/
            }
        }
    }

    /*
     * 删除文件系统中的文件 @param fullPath ：文件在文件系统中的全路径，包括文件名 @return
     */
    private boolean delFile(String fullPath) {
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
    private String getSecondDir(String ejml) {
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
        createFolder(root + FileConstant.PATH_SEPERATOR + secondDir + FileConstant.PATH_SEPERATOR);
        log.debug("附件二级目录：" + secondDir);
        return secondDir;
    }

    /*
     * 根据二级目录规则=日期规则生成二级目录，格式为：年/月/日。如：2006/2006-11/2006-11-20 @return
     */
    private String getDirByTime() {
        String year = CalendarUtil.getCurrYear();
        String month = CalendarUtil.getCurrMonth();
        String yyyymmdd = CalendarUtil
                .getCalendarByFormat(CalendarUtil.FORMAT11);
        String dir = year + FileConstant.PATH_SEPERATOR + year + "-" + month + FileConstant.PATH_SEPERATOR + yyyymmdd;

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
       // String sql = SQLConfig.get("604050200-0003", cclbbh_pk);
        String sql = "select ccgml from xt_ccgl_wjlb where cclbbh_pk = '"+cclbbh_pk+"'";
        table.executeSelect(sql, context, "rootwjlb");
        
        DataBus wjlb = context.getRecord("rootwjlb");        
        // 判断查询最新版本文件类型是否存在
        if (wjlb == null || wjlb.isEmpty()){
        	
        	log.debug("文件类别为空");
        	/*throw new TxnErrorException(ErrorConstant.ACTION_NO_DATA,
                    BJAISConfig.get(WJLBNULLERROR));*/
        }
            

        // 设置文件类型根路径、类型id、二级目录规则信息
        rootPath = wjlb.getValue(VoXtCcglWjlb.ITEM_CCGML);
        context.remove("rootwjlb");     
        return rootPath;
    }

}
