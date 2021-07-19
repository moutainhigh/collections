package com.example.bootssm2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_book")
public class BookInfo {
    @Id // 设定为主键
    private Integer bookId;
    private String bookName;
    private String bookAuthor;
    private BigDecimal bookPrice;
    private Date bookDate;
}
