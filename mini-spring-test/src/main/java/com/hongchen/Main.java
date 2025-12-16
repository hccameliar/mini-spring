package com.hongchen;

import com.hongchen.service.UserService;
import com.minispring.context.ApplicationContext;
import com.minispring.context.support.ClassPathXmlApplicationContext;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        UserService userService = (UserService) context.getBean("userService");
        userService.doSomething();
    }
}
