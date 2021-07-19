package com.ye.test;

import com.ye.util.PingYinTools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

//https://www.cnblogs.com/zfy0098/p/5522969.html
public class Pinyin {

    public static void main(String[] args) {

        String cnStr = "À³ÉðÍ¨Áé";
        System.out.println(PingYinTools.getPingYin(cnStr));
        System.out.println(PingYinTools.getPinYinHeadChar(cnStr));
    }

}
