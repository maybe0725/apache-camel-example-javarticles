package com.javarticles.camel.log;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelThroughputLoggerExample {
	
	public static final void main(String[] args) throws Exception {
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		CamelContext camelContext = new SpringCamelContext(appContext);
		
		try {
			ProducerTemplate template = appContext.getBean(ProducerTemplate.class);
			camelContext.start();			
			for (int i = 0; i <18000; i++) {
			    template.sendBody("activemq:queue:numbers", i);
			}
			Thread.sleep(10000);
		} finally {
			camelContext.stop();
		}
	}
}
