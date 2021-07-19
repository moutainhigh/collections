package com.esp.server.houseserver.service;

import com.esp.server.houseserver.domain.City;
import com.esp.server.houseserver.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
  
  @Autowired(required = false)
  private CityMapper cityMapper;
  
  public List<City> getAllCitys(){
    City query = new City();
    return cityMapper.select(query);
  }

}
