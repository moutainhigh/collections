package com.esp.server.commentserver.service;

import com.esp.server.commentserver.domain.Blog;
import com.esp.server.commentserver.mapper.BlogMapper;
import com.esp.server.commentserver.model.LimitOffset;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
  
  @Autowired(required = false)
  private BlogMapper blogMapper;


  private void populate(List<Blog> blogs) {
    if (!blogs.isEmpty()) {
      blogs.stream().forEach(item -> {
        String stripped =  Jsoup.parse(item.getContent()).text();
        item.setDigest(stripped.substring(0, Math.min(stripped.length(),40)));
        String tags = item.getTags();
        item.getTagList().addAll(Lists.newArrayList(Splitter.on(",").split(tags)));
      });
    }
  }


  /**
   * 查询博客列表
   * 数据填空处理
   * @param blog
   * @param limit
   * @param offset
   * @return
   */
  public Pair<List<Blog>, Long> queryBlog(Blog blog, Integer limit, Integer offset) {
    List<Blog> blogs = blogMapper.selectBlog(blog, LimitOffset.build(limit, offset));
    populate(blogs);
    Long count =  blogMapper.selectBlogCount(blog);
    return ImmutablePair.of(blogs, count);
  }


  /**
   * 根据博客id查询博客详情
   * @param id
   * @return
   */
  public Blog queryOneBlog(Integer id) {
    Blog query = new Blog();
    query.setId(id);
    List<Blog> blogs = blogMapper.selectBlog(query, LimitOffset.build(1, 0));
    if (!blogs.isEmpty()) {
       return blogs.get(0);
    }
    return null;
  }

}
