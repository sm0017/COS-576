package com.spring.aop.catche.exa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MyCacheClient {
    public static void main(String[] args)
    {
        ApplicationContext springcontext = new ClassPathXmlApplicationContext("springConfig.xml");
       
        MyCache myCache = (MyCache)springcontext.getBean("myAOPCache");

        myCache.put("1", "1");
        myCache.put("2", "2");
        myCache.put("3", "3");
        myCache.put("4", "4");
        myCache.put("5", "5");
        myCache.put("6", "6");
        myCache.put("7", "7");
        myCache.put("8", "8");
        myCache.put("9", "9");
        myCache.put("10", "10");
        System.out.println((String)myCache.get("1"));
        System.out.println((String)myCache.get("2"));
        System.out.println((String)myCache.get("10"));
        myCache.put("11", "11");
        System.out.println((String)myCache.get("1"));
        System.out.println((String)myCache.get("2"));
        System.out.println((String)myCache.get("10"));
        System.out.println((String)myCache.get("11"));

    }

}