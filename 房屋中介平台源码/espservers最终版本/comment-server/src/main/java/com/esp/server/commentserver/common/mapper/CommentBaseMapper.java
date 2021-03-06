package com.esp.server.commentserver.common.mapper;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @ProjectName: microservice-house
 * @Auther: GERRY
 * @Date: 2018/11/12 20:11
 * @Description: 所有mapper类的父类
 */
public interface CommentBaseMapper<T>
        extends BaseMapper<T>,
        MySqlMapper<T> {
}
