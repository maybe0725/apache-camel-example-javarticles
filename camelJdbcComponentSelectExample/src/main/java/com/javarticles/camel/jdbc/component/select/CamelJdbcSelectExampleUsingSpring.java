package com.javarticles.camel.jdbc.component.select;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelJdbcSelectExampleUsingSpring {
    public static final void main(String[] args) throws Exception {
    	ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        try {            
        	appContext.start();
            Thread.sleep(2000);
        } finally {
        	appContext.stop();
        	appContext.close();
        }
    }
}
