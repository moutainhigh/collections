package com.example.bootssm2;

import com.example.bootssm2.domain.BookInfo;
import com.example.bootssm2.mapper.BookInfoMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Arrays;

@SpringBootApplication
@MapperScan("com.example.bootssm2.mapper")
public class BootSsm2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext
                context = SpringApplication.run(BootSsm2Application.class, args);
        BookInfoMapper mapper = context.getBean(BookInfoMapper.class);
        BookInfo book1 = mapper.selectByPrimaryKey(2);
        BookInfo book2 = mapper.selectByPrimaryKey(4);
        BookInfo book3 = mapper.selectByPrimaryKey(5);

        mapper.insertList(Arrays.asList(book1, book2, book3));
    }
}
