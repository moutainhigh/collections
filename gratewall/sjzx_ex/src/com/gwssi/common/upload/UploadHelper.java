package com.gwssi.common.upload;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gwssi.common.constant.FileConstant;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoFileInfo;



/**
 * 上传附件接口 实现单附件多附件上传、修改、删除 调用此接口必须先在代码内组装一个UploadFileVO对象的List参数
 * 
 * @author Dongwn
 */
public class UploadHelper
{
	private final static Logger log = TxnLogger.getLogger( UploadHelper.class.getName());
	
	public UploadHelper() {

    }
	
	/**
     * 保存单一类型的附件 （业务模块只存储一种类型附件）
     * 
     * @param context
     * @param fileVO
     * @param fileType
     * @return
     * @throws TxnException
     */
    public static UploadFileVO saveFile(TxnContext context,
            UploadFileVO fileVO, String fileType) throws TxnException {
  
        return saveFile(context,fileVO,fileType,"");
    }
    
    /**
     * 保存单一类型的附件 （业务模块只存储一种类型附件）
     * 
     * @param context
     * @param fileVO
     * @param fileType
     * @param xm_fk 项目主键
     * @return
     * @throws TxnException
     */
    public static UploadFileVO saveFile(TxnContext context,
            UploadFileVO fileVO, String fileType, String xm_fk) throws TxnException {

        UploadFileManagerEx manager = new UploadFileManagerEx(fileType);
        
        if (manager == null) {
            return null;
        }
        // 上传附件
        VoFileInfo[] files = context.getConttrolData().getUploadFileList();
        log.debug("保存文件  context="+context);
        log.debug("files  ="+files);
        if (files != null && files.length > 0) {

            for (int i = 0; i < files.length; i++) {
                log.debug("附件个数 : " + files.length);
                VoFileInfo file = files[i];
                String oriNAME = file.getOriFileName();
                log.debug("oriNAME=="+oriNAME);
                if(oriNAME==null||oriNAME.equals("")){
                	
                }else {
                	DataBus db = manager.saveFile( file, "", null, xm_fk, context);

                    // 单附件处理，不加间隔符
                    if (FileConstant.UPLOAD_FILESTATUS_SINGLE.equals(fileVO
                            .getFileStatus())) {
                        fileVO.setReturnId(db.getValue(FileConstant.file_id));
                        fileVO.setReturnName(db.getValue(FileConstant.file_name));
                    } else {
                        // 多附件处理回传id，name值 ，加间隔符","
                        if (fileVO.getReturnId() == null
                            || fileVO.getReturnId().trim().length() == 0) {

                            fileVO.setReturnId(FileConstant.ID_SEPERATOR);
                            fileVO.setReturnName(FileConstant.ID_SEPERATOR);
                        }

                        fileVO
                                .setReturnId(fileVO.getReturnId()
                                        .concat(
                                                db.getValue(FileConstant.file_id)
                                                        .concat(FileConstant.ID_SEPERATOR)));
                        fileVO.setReturnName(fileVO.getReturnName()
                                .concat(
                                        db.getValue(FileConstant.file_name)
                                                .concat(FileConstant.ID_SEPERATOR)));
                        log.debug(fileVO.getRecordName()
                                           + "  == 返回附件名称: "
                                           + fileVO.getReturnName());
                    }
                }
                
            }
        } else {
            // 若无附件上传，设置默认回传值
            fileVO.setReturnId("");
            fileVO.setReturnName("");
        }
        return fileVO;
    }
    
    /**
     * 修改附件，在修改Action内调用，包括删除附件添加新增附件 返回值: UploadFileVO
     * 
     * @param context
     *            调用Action 的TxnContext对象
     * @param voList
     *            存储业务模块附件信息UploadFileVO对象的List列表
     * @param fileType
     *            附件类别
     * @return List 包含UploadFileVO对象。业务模块可通过调用UploadFileVO.getReturnId()
     *         和UploadFileVO.getReturnName()方法获得处理后的附件id和name值，供
     *         数据库存储。多附件以“,”英文半角逗号分隔。
     * @throws TxnException
     */
    public static UploadFileVO updateFile(TxnContext context,
            UploadFileVO fileVO, String fileType) throws TxnException {
        return updateFile(context, fileVO, fileType, "");
    }
    /**
     * 修改附件，在修改Action内调用，包括删除附件添加新增附件 返回值: UploadFileVO
     * 
     * @param context
     *            调用Action 的TxnContext对象
     * @param voList
     *            存储业务模块附件信息UploadFileVO对象的List列表
     * @param fileType
     *            附件类别
     * @param xm_fk 项目主键
     * @return List 包含UploadFileVO对象。业务模块可通过调用UploadFileVO.getReturnId()
     *         和UploadFileVO.getReturnName()方法获得处理后的附件id和name值，供
     *         数据库存储。多附件以“,”英文半角逗号分隔。
     * @throws TxnException
     */
    public static UploadFileVO updateFile(TxnContext context,
            UploadFileVO fileVO, String fileType, String xm_fk) throws TxnException {

        UploadFileManagerEx manager = new UploadFileManagerEx(fileType);

        if (manager == null) {
            return null;
        }

        String returnId = fileVO.getOriginId();
        String returnName = fileVO.getOriginName();
        log.debug("原-returnId="+returnId);
        log.debug("原-returnName="+returnName);
        // 若未传递原有附件值，设置默认值
        if(returnId==null)
            returnId = "";
        if(returnName == null)
            returnName = "";
        
        // 设置返回初始值 与原附件值相同
        fileVO.setReturnId(returnId);
        fileVO.setReturnName(returnName);

        // 获取页面传递的删除字段，并调用UploadFileManager.delete()方法删除附件
        if (fileVO != null && fileVO.getDeleteId() != null
            && fileVO.getDeleteId().length() != 0) {
            String[] delIds = fileVO.getDeleteId().split(FileConstant.ID_SEPERATOR);

            // 删除附件
            if (delIds != null && delIds.length > 0) {
            	log.debug("待删除fileid="+delIds);
                String[] idTemps = returnId.split(FileConstant.ID_SEPERATOR);
                String[] nameTemps = returnName.split(FileConstant.ID_SEPERATOR);
                Map oriTemps = new HashMap();
                for (int i = 0; i < idTemps.length; i++) {
                    if (idTemps[i] != null && idTemps[i].length() > 0)
                        oriTemps.put(idTemps[i], nameTemps[i]);
                }
                for (int i = 0; i < delIds.length; i++) {
                    if (delIds[i] != null && delIds[i].length() > 0)
                        oriTemps.remove(delIds[i]);
                }
                String newIds = "";
                String newNames = "";
                if (oriTemps.size() > 0) {
                    Set keys = oriTemps.keySet();
                    for (Iterator it = keys.iterator(); it.hasNext();) {
                        String nextId = (String) it.next();
                        newIds = newIds + nextId + FileConstant.ID_SEPERATOR;
                        newNames = newNames + oriTemps.get(nextId) + FileConstant.ID_SEPERATOR;
                    }
                    newIds = FileConstant.ID_SEPERATOR + newIds;
                    newNames = FileConstant.ID_SEPERATOR + newNames;
                }

                returnId = newIds;
                returnName = newNames;

                // 删除附件实体
                manager.deleteFile(fileVO.getDeleteId(), context);

            }
        }

        // 上传附件
        VoFileInfo[] files = context.getConttrolData().getUploadFileList();

        if (files != null && files.length > 0) {
            // 循环保存所有上传附件
            for (int i = 0; i < files.length; i++) {
                VoFileInfo file = files[i];
                String oriNAME = file.getOriFileName();
                log.debug("oriNAME=="+oriNAME);
                if(oriNAME==null||oriNAME.equals("")){
                	
                }else {
                // 调用UploadFileManager.saveFile()方法保存文件
                DataBus db = manager.saveFile( file, "", null, xm_fk, context);

                // 单附件处理，不加间隔符
                if (FileConstant.UPLOAD_FILESTATUS_SINGLE.equals(fileVO
                        .getFileStatus())) {
                    returnId = db.getValue(FileConstant.file_id);
                    returnName = db.getValue(FileConstant.file_name);
                } else {
                    // 多附件处理回传id，name值 ，加间隔符","
                    
                    /**
                     * 判断处理的ID串最后一位是否为间隔符，如果存在id且最后一位非间隔符，增加一个间隔符
                     * 修改日期：20070628
                     * 修改人：方爱丹
                     */
                    boolean hasSeper = false;
                    if(returnId.length()>0 && (FileConstant.ID_SEPERATOR).equals( returnId.substring( returnId.length()-1 ) ))
                        hasSeper = true;
                    
                    if (returnId.trim().length() == 0 || (!hasSeper)) {
                        returnId = returnId.concat(FileConstant.ID_SEPERATOR);
                        returnName = returnName.concat(FileConstant.ID_SEPERATOR);
                    }
                    log.debug("returnId="+returnId);
                    log.debug("db.getValue(FileConstant.file_id)="+db.getValue(FileConstant.file_id));
                    returnId = returnId.concat(db.getValue(FileConstant.file_id)
                            .concat(FileConstant.ID_SEPERATOR));
                    
                    log.debug("12returnName="+returnName);
                    log.debug("12getValue(FileConstant.file_name)="+db.getValue(FileConstant.file_name));
                    returnName = returnName.concat(db
                            .getValue(FileConstant.file_name).concat(FileConstant.ID_SEPERATOR));
                }
                }
            }
        }

        fileVO.setReturnId(returnId);
        fileVO.setReturnName(returnName);
        
        log.debug("上传附件类型名称： "+fileVO.getRecordName());
        log.debug(" 回传ID值　： "+fileVO.getReturnId());
        log.debug(" 回传名称　： "+fileVO.getReturnName());

        return fileVO;
    }

    
    /**
     * 删除单一类型附件（业务模块只存储一种类型附件） 在删除业务记录Action内调用。首先从数据库内查出所有附件
     * 
     * @param context
     * @param voList
     * @param fileType
     * @return
     * @throws TxnException
     */
    public static boolean deleteFile(TxnContext context, String fileids,
            String fileType) throws TxnException {
        boolean result = false;
        UploadFileManagerEx manager = new UploadFileManagerEx(fileType);

        if (manager == null) {
            return false;
        }
        // 获取页面传递的删除字段，并调用UploadFileManager.delete()方法删除附件
        if (fileids != null && fileids.length() != 0) {
            String ids[] = fileids.split(FileConstant.ID_SEPERATOR);

            // 删除附件
            if (ids != null && ids.length > 0) {
                for (int j = 0; j < ids.length; j++) {
                    // 传递的值无意义
                    if (ids[j].length() <= 0) {
                        continue;
                    }
                    manager.deleteFile(ids[j], context);
                }
            }
        }

        return result;
    }
    
    public static void getFileInfo(TxnContext context, String idColumn,
            String nameColumn, String returnColumn) throws TxnException {

        String[] fileids = idColumn.split(FileConstant.ID_SEPERATOR);
        String[] filenames = nameColumn.split(FileConstant.ID_SEPERATOR);

        Recordset rs = new Recordset();

        for (int i = 0; i < fileids.length; i++) {
            if (fileids[i].length() > 0) {
                DataBus db = new DataBus();
                db.setValue(FileConstant.file_id, fileids[i]);
                db.setValue(FileConstant.file_name, filenames[i]);
                rs.add(db);
            }
        }
        context.addRecord(returnColumn, rs);

    }
}
