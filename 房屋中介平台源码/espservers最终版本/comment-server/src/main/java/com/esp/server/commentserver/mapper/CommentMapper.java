package com.esp.server.commentserver.mapper;

import com.esp.server.commentserver.common.mapper.CommentBaseMapper;
import com.esp.server.commentserver.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper extends CommentBaseMapper<Comment> {

  List<Comment> selectComments(@Param("houseId") long houseId, @Param("size") int size);
  
  List<Comment> selectBlogComments(@Param("blogId") long blogId, @Param("size") int size);
  
}

