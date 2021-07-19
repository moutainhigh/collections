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
 * �ϴ������ӿ� ʵ�ֵ������฽���ϴ����޸ġ�ɾ�� ���ô˽ӿڱ������ڴ�������װһ��UploadFileVO�����List����
 * 
 * @author Dongwn
 */
public class UploadHelper
{
	private final static Logger log = TxnLogger.getLogger( UploadHelper.class.getName());
	
	public UploadHelper() {

    }
	
	/**
     * ���浥һ���͵ĸ��� ��ҵ��ģ��ֻ�洢һ�����͸�����
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
     * ���浥һ���͵ĸ��� ��ҵ��ģ��ֻ�洢һ�����͸�����
     * 
     * @param context
     * @param fileVO
     * @param fileType
     * @param xm_fk ��Ŀ����
     * @return
     * @throws TxnException
     */
    public static UploadFileVO saveFile(TxnContext context,
            UploadFileVO fileVO, String fileType, String xm_fk) throws TxnException {

        UploadFileManagerEx manager = new UploadFileManagerEx(fileType);
        
        if (manager == null) {
            return null;
        }
        // �ϴ�����
        VoFileInfo[] files = context.getConttrolData().getUploadFileList();
        log.debug("�����ļ�  context="+context);
        log.debug("files  ="+files);
        if (files != null && files.length > 0) {

            for (int i = 0; i < files.length; i++) {
                log.debug("�������� : " + files.length);
                VoFileInfo file = files[i];
                String oriNAME = file.getOriFileName();
                log.debug("oriNAME=="+oriNAME);
                if(oriNAME==null||oriNAME.equals("")){
                	
                }else {
                	DataBus db = manager.saveFile( file, "", null, xm_fk, context);

                    // �������������Ӽ����
                    if (FileConstant.UPLOAD_FILESTATUS_SINGLE.equals(fileVO
                            .getFileStatus())) {
                        fileVO.setReturnId(db.getValue(FileConstant.file_id));
                        fileVO.setReturnName(db.getValue(FileConstant.file_name));
                    } else {
                        // �฽������ش�id��nameֵ ���Ӽ����","
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
                                           + "  == ���ظ�������: "
                                           + fileVO.getReturnName());
                    }
                }
                
            }
        } else {
            // ���޸����ϴ�������Ĭ�ϻش�ֵ
            fileVO.setReturnId("");
            fileVO.setReturnName("");
        }
        return fileVO;
    }
    
    /**
     * �޸ĸ��������޸�Action�ڵ��ã�����ɾ����������������� ����ֵ: UploadFileVO
     * 
     * @param context
     *            ����Action ��TxnContext����
     * @param voList
     *            �洢ҵ��ģ�鸽����ϢUploadFileVO�����List�б�
     * @param fileType
     *            �������
     * @return List ����UploadFileVO����ҵ��ģ���ͨ������UploadFileVO.getReturnId()
     *         ��UploadFileVO.getReturnName()������ô����ĸ���id��nameֵ����
     *         ���ݿ�洢���฽���ԡ�,��Ӣ�İ�Ƕ��ŷָ���
     * @throws TxnException
     */
    public static UploadFileVO updateFile(TxnContext context,
            UploadFileVO fileVO, String fileType) throws TxnException {
        return updateFile(context, fileVO, fileType, "");
    }
    /**
     * �޸ĸ��������޸�Action�ڵ��ã�����ɾ����������������� ����ֵ: UploadFileVO
     * 
     * @param context
     *            ����Action ��TxnContext����
     * @param voList
     *            �洢ҵ��ģ�鸽����ϢUploadFileVO�����List�б�
     * @param fileType
     *            �������
     * @param xm_fk ��Ŀ����
     * @return List ����UploadFileVO����ҵ��ģ���ͨ������UploadFileVO.getReturnId()
     *         ��UploadFileVO.getReturnName()������ô����ĸ���id��nameֵ����
     *         ���ݿ�洢���฽���ԡ�,��Ӣ�İ�Ƕ��ŷָ���
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
        log.debug("ԭ-returnId="+returnId);
        log.debug("ԭ-returnName="+returnName);
        // ��δ����ԭ�и���ֵ������Ĭ��ֵ
        if(returnId==null)
            returnId = "";
        if(returnName == null)
            returnName = "";
        
        // ���÷��س�ʼֵ ��ԭ����ֵ��ͬ
        fileVO.setReturnId(returnId);
        fileVO.setReturnName(returnName);

        // ��ȡҳ�洫�ݵ�ɾ���ֶΣ�������UploadFileManager.delete()����ɾ������
        if (fileVO != null && fileVO.getDeleteId() != null
            && fileVO.getDeleteId().length() != 0) {
            String[] delIds = fileVO.getDeleteId().split(FileConstant.ID_SEPERATOR);

            // ɾ������
            if (delIds != null && delIds.length > 0) {
            	log.debug("��ɾ��fileid="+delIds);
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

                // ɾ������ʵ��
                manager.deleteFile(fileVO.getDeleteId(), context);

            }
        }

        // �ϴ�����
        VoFileInfo[] files = context.getConttrolData().getUploadFileList();

        if (files != null && files.length > 0) {
            // ѭ�����������ϴ�����
            for (int i = 0; i < files.length; i++) {
                VoFileInfo file = files[i];
                String oriNAME = file.getOriFileName();
                log.debug("oriNAME=="+oriNAME);
                if(oriNAME==null||oriNAME.equals("")){
                	
                }else {
                // ����UploadFileManager.saveFile()���������ļ�
                DataBus db = manager.saveFile( file, "", null, xm_fk, context);

                // �������������Ӽ����
                if (FileConstant.UPLOAD_FILESTATUS_SINGLE.equals(fileVO
                        .getFileStatus())) {
                    returnId = db.getValue(FileConstant.file_id);
                    returnName = db.getValue(FileConstant.file_name);
                } else {
                    // �฽������ش�id��nameֵ ���Ӽ����","
                    
                    /**
                     * �жϴ����ID�����һλ�Ƿ�Ϊ��������������id�����һλ�Ǽ����������һ�������
                     * �޸����ڣ�20070628
                     * �޸��ˣ�������
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
        
        log.debug("�ϴ������������ƣ� "+fileVO.getRecordName());
        log.debug(" �ش�IDֵ���� "+fileVO.getReturnId());
        log.debug(" �ش����ơ��� "+fileVO.getReturnName());

        return fileVO;
    }

    
    /**
     * ɾ����һ���͸�����ҵ��ģ��ֻ�洢һ�����͸����� ��ɾ��ҵ���¼Action�ڵ��á����ȴ����ݿ��ڲ�����и���
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
        // ��ȡҳ�洫�ݵ�ɾ���ֶΣ�������UploadFileManager.delete()����ɾ������
        if (fileids != null && fileids.length() != 0) {
            String ids[] = fileids.split(FileConstant.ID_SEPERATOR);

            // ɾ������
            if (ids != null && ids.length > 0) {
                for (int j = 0; j < ids.length; j++) {
                    // ���ݵ�ֵ������
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
