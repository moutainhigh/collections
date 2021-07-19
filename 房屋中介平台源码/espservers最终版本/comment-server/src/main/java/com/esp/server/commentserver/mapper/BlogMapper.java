package com.esp.server.commentserver.mapper;

import com.esp.server.commentserver.common.mapper.CommentBaseMapper;
import com.esp.server.commentserver.domain.Blog;
import com.esp.server.commentserver.model.LimitOffset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper extends CommentBaseMapper<Blog> {
  
  List<Blog> selectBlog(@Param("blog") Blog blog, @Param("pageParams") LimitOffset limitOffset);
  
  Long selectBlogCount(Blog query);

}
