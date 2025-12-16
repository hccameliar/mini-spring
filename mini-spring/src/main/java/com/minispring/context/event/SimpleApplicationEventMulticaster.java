package com.minispring.context.event;

import com.minispring.context.ApplicationEvent;
import com.minispring.context.ApplicationListener;
import com.minispring.ioc.factory.BeanFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 简单的应用事件多播器实现
 * 维护一个监听器列表，在事件发生时通知所有匹配的监听器
 */
public class SimpleApplicationEventMulticaster implements ApplicationEventMulticaster {

    /**
     * 监听器列表
     */
    private final List<ApplicationListener<?>> listeners = new ArrayList<>();

    /**
     * Bean工厂，用于获取监听器
     */
    private BeanFactory beanFactory;

    /**
     * 默认构造函数
     */
    public SimpleApplicationEventMulticaster() {
    }

    /**
     * 构造函数
     *
     * @param beanFactory Bean工厂
     */
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 设置Bean工厂
     *
     * @param beanFactory Bean工厂
     */
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 添加事件监听器
     *
     * @param listener 要添加的监听器
     */
    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        listeners.add(listener);
    }

    /**
     * 移除事件监听器
     *
     * @param listener 要移除的监听器
     */
    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        listeners.remove(listener);
    }

    /**
     * 删除所有监听器
     */
    @Override
    public void removeAllListeners() {
        listeners.clear();
    }

    /**
     * 将事件多播给所有匹配的监听器
     *
     * @param event 要多播的事件
     */
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener listener : getApplicationListeners(event)) {
            invokeListener(listener, event);
        }
    }

    /**
     * 获取匹配事件的所有监听器
     *
     * @param event 事件
     * @return 匹配的监听器列表
     */
    private Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        List<ApplicationListener> allListeners = new ArrayList<>();
        for (ApplicationListener<?> listener : listeners) {
            if (supportsEvent(listener, event)) {
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    /**
     * 检查监听器是否支持给定的事件
     *
     * @param listener 监听器
     * @param event    事件
     * @return 如果支持返回true
     */
    private boolean supportsEvent(ApplicationListener<?> listener, ApplicationEvent event) {
        Class<?> listenerClass = listener.getClass();
        Class<?> eventClass = event.getClass();

        // 通过反射获取监听器实现的 ApplicationListener 接口的泛型参数
        java.lang.reflect.Type[] interfaces = listenerClass.getGenericInterfaces();
        for (java.lang.reflect.Type type : interfaces) {
            if (type instanceof java.lang.reflect.ParameterizedType parameterizedType) {

                // 检查是否是 ApplicationListener 接口
                if (parameterizedType.getRawType() == ApplicationListener.class) {
                    // 获取泛型参数类型（事件类型）
                    java.lang.reflect.Type[] actualTypes = parameterizedType.getActualTypeArguments();
                    if (actualTypes.length > 0 && actualTypes[0] instanceof Class<?> expectedEventType) {

                        // 如果期望的是 ApplicationEvent（无泛型约束），支持所有事件
                        if (expectedEventType == ApplicationEvent.class) {
                            return true;
                        }

                        // 检查事件类型是否匹配（支持类型继承）
                        // expectedEventType.isAssignableFrom(eventClass) 表示：
                        // eventClass 是 expectedEventType 或其子类
                        return expectedEventType.isAssignableFrom(eventClass);
                    }
                }
            }
        }

        // 如果无法获取泛型信息，为了安全，默认不支持
        // 这样可以避免类型转换异常
        return false;
    }

    /**
     * 调用监听器处理事件
     *
     * @param listener 监听器
     * @param event    事件
     */
    @SuppressWarnings("unchecked")
    private void invokeListener(ApplicationListener listener, ApplicationEvent event) {
        // 由于已经通过类型匹配，可以直接调用（性能更好）
        try {
            listener.onApplicationEvent(event);
        } catch (ClassCastException e) {
            // 如果仍然发生类型转换异常，说明类型匹配逻辑有问题
            System.err.println("类型转换异常 - 监听器: " + listener.getClass().getName() +
                    ", 事件: " + event.getClass().getName() +
                    ", 错误: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // 其他异常（监听器内部抛出的业务异常）
            System.err.println("处理事件时发生错误 - 监听器: " + listener.getClass().getName() +
                    ", 事件: " + event.getClass().getName() +
                    ", 错误: " + e.getMessage());
        }
    }
} 