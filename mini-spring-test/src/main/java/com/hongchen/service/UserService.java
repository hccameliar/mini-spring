package com.hongchen.service;

import com.hongchen.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    // 提供 setter 供 XML 注入
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void doSomething() {
        System.out.println("UserService 开始调用");
        userRepository.printInfo();
    }
}
