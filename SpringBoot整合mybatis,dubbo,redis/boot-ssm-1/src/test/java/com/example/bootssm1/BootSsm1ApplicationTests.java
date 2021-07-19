package com.example.bootssm1;

import com.example.bootssm1.domain.BookInfo;
import com.example.bootssm1.mapper.BookMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootSsm1ApplicationTests {

    @Resource
    private BookMapper bookMapper;

    @Test
    public void selectOne() {
        BookInfo book = bookMapper.getBookById(2);
        System.out.println(book);
    }

    @Test
    public void save() {
        BookInfo book = bookMapper.getBookById(2);
        book.setBookName("新书");
        int row = bookMapper.saveBook(book);
        Assert.assertEquals(1, row);
    }

}
