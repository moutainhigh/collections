package com.esp.server.houseserver.mapper;

import com.esp.server.houseserver.common.mapper.HouseBaseMapper;
import com.esp.server.houseserver.domain.City;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CityMapper extends HouseBaseMapper<City> {
}
