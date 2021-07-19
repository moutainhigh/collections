package com.esp.server.web.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/12/1 23:16
 * @Description:
 */
public class ListUtil {

    /**
     * 把一个MultipartFile类型集合转换为MultipartFile类型数组
     * @param list
     * @return
     */
    public static MultipartFile[] converterListToMulFileArray(List<? extends MultipartFile> list) {
        MultipartFile[] multipartFiles = new MultipartFile[list.size()];
        return list.toArray(multipartFiles);
    }
}
