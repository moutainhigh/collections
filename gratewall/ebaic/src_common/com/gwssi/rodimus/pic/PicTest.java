package com.gwssi.rodimus.pic;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.gwssi.ebaic.domain.BeWkEntBO;
import com.gwssi.rodimus.pic.domain.ImageWidget;
import com.gwssi.rodimus.pic.domain.TextWidget;

public class PicTest {

	 /**  
     * @param args  
     */  
    public static void main(String[] args) {   
    	String root = "E:\\CertPrint\\";
        String bgImgPath = root + "AA_11.jpg";   
        File bg = new File(bgImgPath);
        
        BeWkEntBO bo = new BeWkEntBO();
        bo.setEntName("北京测试有限公司");
        bo.setEntType("aaa");
        
        
        // 文字
        List<TextWidget> strings = new ArrayList<TextWidget>();
        Font heiFont = new Font("黑体",Font.BOLD,50);
        strings.add(new TextWidget(1250,1050,"统一社会信用代码1",heiFont));
        
        int titleX = 400;
        int titleStartY = 1050 ;
        int titleLineHeight = 70 ;
        int lineCnt = 0;
        int lineGap = 40;
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,"名          称",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,"类          型",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,"住          所",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,"法 定 代 表 人",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,"注  册  资  本",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,"成  立  日  期",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,"营  业  期  限",heiFont));
        strings.add(new TextWidget(titleX,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,"经  营  范  围",heiFont));
        
        Font heiCommonFont = new Font("黑体",Font.BOLD,55);
        int contentStartX = titleX + 400 ;
        lineCnt = 0;
        strings.add(new TextWidget(contentStartX+170,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,bo.getEntName(),heiCommonFont));
        strings.add(new TextWidget(contentStartX+170,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,bo.getEntType(),heiCommonFont));
        strings.add(new TextWidget(contentStartX+170,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,bo.getEntName(),heiCommonFont));
        strings.add(new TextWidget(contentStartX+170,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,bo.getEntName(),heiCommonFont));
        strings.add(new TextWidget(contentStartX+170,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,"2016年08月18日",heiCommonFont));
        strings.add(new TextWidget(contentStartX+170,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,bo.getEntName(),heiCommonFont));
        strings.add(new TextWidget(contentStartX+170,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,bo.getEntName(),heiCommonFont));
        strings.add(new TextWidget(contentStartX+170,titleStartY + titleLineHeight*(++lineCnt)+lineGap*lineCnt,"阿阿阿阿阿阿阿阿阿阿阿阿阿啊啊",heiCommonFont));
        lineCnt = 0;
        //zxing 
        
        Font erWeiMa = new Font("黑体",Font.PLAIN,55);
        //二维码下方提示信息
        strings.add(new TextWidget(370, 2770, "在线扫码获取", erWeiMa));
        strings.add(new TextWidget(370, 2840, "详细信息", erWeiMa));
        strings.add(new TextWidget(370, 2970, "提示：每年1月1日至6月30日通过企业信用公示", erWeiMa));
        strings.add(new TextWidget(340, 3050, "系统", erWeiMa));
        strings.add(new TextWidget(340, 3130, "报送上一年度年度公告并公示", erWeiMa));
        
        //年月日
        strings.add(new TextWidget(1520, 2970, "2016", erWeiMa));
        strings.add(new TextWidget(1720, 2970, "08", erWeiMa));
        strings.add(new TextWidget(1870, 2970, "15", erWeiMa));
        
        
        // 图片
        List<ImageWidget> images = new ArrayList<ImageWidget>();
        String stampPicPath = root + "Lic_Stamp_Hd.png";  
        ImageWidget stampPic = new ImageWidget(1600,2600,1.0f,stampPicPath); 
        images.add(stampPic);
        
        //二维码 
        BarCodeUtil.genbarCode("1","E:\\CertPrint\\2.jpg", 370, 370);
        String stampPicPath1 = root + "2.jpg";  
        ImageWidget stampPic1 = new ImageWidget(390,2250,1.0f,stampPicPath1); 
        images.add(stampPic1);
        
        String targerPath = root + "target.jpg";    
        // 给图片添加水印   
        PicUtil.genCertPicture(bg, strings,images, targerPath);  
        System.out.println("done");
    }   
}
