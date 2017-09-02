package cn.jxh.stock.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StockPush {

    public static void main(String[] args) throws Exception {
        new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
    }

}
