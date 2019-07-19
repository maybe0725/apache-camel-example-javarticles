package com.javarticles.camel.recipient;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelRecipientListExampleUsingSpring {
    public static final void main(String[] args) throws Exception {
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext) ;
        try {
        	camelContext.start();
            ProducerTemplate template = appContext.getBean(ProducerTemplate.class);
            template.start();
            System.out.println("************************");
            Employee sam = new Employee("Sam");
            sam.setNew(true);
            sam.setMessage("Joined");
            
            template.sendBody("direct:start", sam);
            
            System.out.println("************************");
            
            Employee john = new Employee("John");
            john.setOnLeave(true);
            john.setMessage("On Leave");
            template.sendBody("direct:start", john);
            
            System.out.println("************************");
            
            Employee roy = new Employee("Roy");
            roy.setPromoted(true);
            roy.setMessage("Promoted");
            template.sendBody("direct:start", roy);
            
            System.out.println("************************");
            
            Employee ram = new Employee("Ram");
            ram.setResigning(true);
            ram.setMessage("Resigning");
            template.sendBody("direct:start", ram);
            
            System.out.println("************************");
            
            template.stop();
        } finally {
        	camelContext.stop();
        }
    }
}
