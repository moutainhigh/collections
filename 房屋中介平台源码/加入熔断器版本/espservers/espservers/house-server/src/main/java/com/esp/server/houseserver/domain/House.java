package com.esp.server.houseserver.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Data
public class House {

  @Id
  @KeySql(useGeneratedKeys = true)
  private Long id;
  private Integer type;
  private Integer price;
  private String  name;
  private String images;
  private Integer area;
  private Integer beds;
  private Integer baths;
  private Double  rating;
  private String  remarks;
  private String  properties;
  private String  floorPlan;
  private String  tags;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date    createTime;
  private Integer cityId;
  private Integer    communityId;
  private String     address;
  private Integer state;

  /////////////// 自定义字段 ///////////////////
  private List<String> imageList = Lists.newArrayList();
  private List<String> floorPlanList = Lists.newArrayList();
  private List<String> featureList   = Lists.newArrayList();
  private boolean bookmarked;
  @Transient
  private Integer roundRating = 0;
  @Transient
  private String     firstImg;
  private List<Long> ids;
  @Transient
  private Long userId;
  @Transient
  private String  sort = "time_desc";//price_desc,price_asc,time_desc
  @Transient
  private String priceStr;

  public void setPrice(Integer price) {
    this.price = price;
    if (price != null) {
      this.priceStr = this.price + "万";
    }
  }

  public void setProperties(String properties) {
    this.properties = properties;
    if (StringUtils.isNotBlank(properties)) {
      this.featureList = Splitter.on(",").splitToList(properties);
    }

  }

  public void setFloorPlan(String floorPlan) {
    this.floorPlan = floorPlan;
    if (StringUtils.isNotBlank(floorPlan)) {
      this.floorPlanList = Splitter.on(",").splitToList(floorPlan);
    }

  }

  public void setImages(String images) {
    this.images = images;
    if (StringUtils.isNotBlank(images)) {
       List<String> list =  Splitter.on(",").splitToList(images);
       if (list.size() > 0) {
          this.firstImg = list.get(0);
          this.imageList = list;
       }
    }
  }

  public void setFeatureList(List<String> featureList) {
    this.featureList = featureList;
    this.properties = Joiner.on(",").join(featureList);
  }

  public void setRating(Double rating) {
    this.rating = rating;
    if (rating != null) {
      this.roundRating = (int) Math.round(rating);
    }
  }

}
