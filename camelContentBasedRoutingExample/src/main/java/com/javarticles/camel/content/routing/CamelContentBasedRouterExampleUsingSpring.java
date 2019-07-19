package com.javarticles.camel.content.routing;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelContentBasedRouterExampleUsingSpring {
    public static final void main(String[] args) throws Exception {
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext) ;
        try {
        	camelContext.start();
            ProducerTemplate template = appContext.getBean(ProducerTemplate.class);
            template.start();
            template.sendBody("direct:start", "Java Threads");
            template.sendBodyAndHeader("direct:start", "Camel Content Based Router", "views", 20000);
            template.sendBody("direct:start", "Spring Integration");
            template.stop();
        } finally {
        	camelContext.stop();
        }
    }
}
