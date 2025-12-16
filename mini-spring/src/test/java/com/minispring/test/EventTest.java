package com.minispring.test;

import com.minispring.context.support.ClassPathXmlApplicationContext;
import com.minispring.test.event.UserRegisteredEvent;
import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void testEvent() {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:event-test.xml");

        context.publishEvent(new UserRegisteredEvent(context, "jane", "123@gmail.com"));
    }
}
