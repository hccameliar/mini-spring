package com.minispring.test.bean;

import com.minispring.beans.BeansException;
import com.minispring.context.aware.BeanFactoryAware;
import com.minispring.context.aware.BeanNameAware;
import com.minispring.ioc.factory.BeanFactory;

/**
 * Aware接口测试Bean类
 * 实现BeanNameAware和BeanFactoryAware接口
 */
public class AwareBean implements BeanNameAware, BeanFactoryAware {

    private String beanName;
    private BeanFactory beanFactory;

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("AwareBean.setBeanName: " + name);
        this.beanName = name;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("AwareBean.setBeanFactory: " + beanFactory);
        this.beanFactory = beanFactory;
    }
} 