package com.esp.server.web.util;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: service-demo
 * @Auther: GERRY
 * @Date: 2018/11/20 16:41
 * @Description:
 */
public class GeneralConvert {

    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static DozerBeanMapper dozer;

    static {
        if (dozer == null) {
            dozer = new DozerBeanMapper();
            List<String> mappingFileUrls = Arrays.asList("dozer-date.xml");
            dozer.setMappingFiles(mappingFileUrls);
        }
    }
    /**
     * 对象通用转换
     *
     * @param source           源对象
     * @param destinationClass 目标类
     * @param <T>
     * @return 返回得到destinationClass
     */
    public static <T> T converter(Object source, Class<T> destinationClass) {
        if(null == source){
            return null;
        }
        T convObj = dozer.map(source, destinationClass);
        return convObj;
    }

    /**
     * 集合转换
     *
     * @param sourceList       源集合
     * @param destinationClass 目标类
     * @param <T>
     * @return 返回得到destinationClass的集合结果
     */
    public static <T> List<T> convert2List(List<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        sourceList.forEach(source -> {
            destinationList.add(GeneralConvert.converter(source, destinationClass));
        });
        return destinationList;
    }
}
