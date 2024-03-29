package com.javarticles.camel.timer;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelTimerSqlQueryExample {
	public static final void main(String[] args) throws Exception {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("timerSelectQueryApplicationContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext);
        try {
            camelContext.start();
            Thread.sleep(5000);
        } finally {
            camelContext.stop();
        }
	}
}
