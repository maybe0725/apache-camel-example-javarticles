package com.javarticles.camel.filter;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FeedFilterExampleUsingSpring {
	
    public static void main(String[] args) throws Exception {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext);
        try {
            camelContext.start();          
            Thread.sleep(4000);
        } finally {
            camelContext.stop();
        }
    }

}
