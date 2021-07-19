package com.esp.server.userserver.mapper;

import com.esp.server.userserver.common.mapper.UserBaseMapper;
import com.esp.server.userserver.domain.Agency;
import com.esp.server.userserver.domain.User;
import com.esp.server.userserver.model.PageParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: user-service
 * @Auther: GERRY
 * @Date: 2018/11/17 17:00
 * @Description:
 */
@Mapper
public interface AgencyMapper extends UserBaseMapper<Agency> {
    List<User> selectAgent(@Param("user") User user, @Param("pageParams") PageParams pageParams);

    Long selectAgentCount(@Param("user") User user);
}
