package com.javarticles.camel.transform;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleTransformExampleUsingSpring {
    public static void main(String[] args) throws Exception {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext) ;
        try {
            camelContext.start();          
            ProducerTemplate template = appContext.getBean(ProducerTemplate.class);
            template.start();
            template.sendBody("direct:start", "Hello");
            template.stop();
        } finally {
            camelContext.stop();
        }
    }
}
