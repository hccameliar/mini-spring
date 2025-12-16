package com.minispring.test.listener;

import com.minispring.context.ApplicationListener;
import com.minispring.test.event.UserRegisteredEvent;

public class EmailListener implements ApplicationListener<UserRegisteredEvent> {

    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        System.out.println("邮件发送给" + event.getUsername() + " 邮箱：" + event.getEmail());
        throw new RuntimeException("模拟错误----------");
    }
}
