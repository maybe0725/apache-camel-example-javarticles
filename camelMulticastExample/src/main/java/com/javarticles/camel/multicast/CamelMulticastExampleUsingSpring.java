package com.javarticles.camel.multicast;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelMulticastExampleUsingSpring {
	
    public static final void main(String[] args) throws Exception {
    	
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext);
        
        try {
        	camelContext.start();           
            ProducerTemplate template = appContext.getBean(ProducerTemplate.class);
            template.sendBody("direct:start", "Multicast");
        } finally {
            camelContext.stop();
        }
    }
}
