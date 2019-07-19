package com.javarticles.camel.split.xml.tokenize;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelSplitXmlTokenizeUsingSpringDSL {
	public static final void main(String[] args) throws Exception {
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext) ;
        try {            
        	camelContext.start();
            ProducerTemplate template = appContext.getBean(ProducerTemplate.class);
            template.start();
            String filename = "target/classes/articlesNs.xml";
            InputStream articleStream = new FileInputStream(filename);
            template.sendBody("direct:article", articleStream);
            template.stop();
        } finally {
        	camelContext.stop();
        }
    }
}
