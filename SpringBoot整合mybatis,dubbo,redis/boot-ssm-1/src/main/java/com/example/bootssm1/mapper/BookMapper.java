package com.example.bootssm1.mapper;

import com.example.bootssm1.domain.BookInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // 把该接口生成的代理类的实例交给spring容器控制
public interface BookMapper {
    BookInfo getBookById(Integer bookId);
    int saveBook(BookInfo bookInfo);
    int batchBook(List<BookInfo> books);
}
