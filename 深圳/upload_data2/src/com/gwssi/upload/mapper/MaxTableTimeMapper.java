package com.gwssi.upload.mapper;

import java.util.List;
import java.util.Map;

import com.gwssi.upload.pojo.MaxTableTime;

public interface MaxTableTimeMapper {

	public void save(Map map);

	public List<MaxTableTime> getEntByParams(Map params);

}
