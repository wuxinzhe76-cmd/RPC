package com.charles.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.charles")
public class SpringServer {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context =  new AnnotationConfigApplicationContext(SpringServer.class);
//        context.wait();
    }
}
