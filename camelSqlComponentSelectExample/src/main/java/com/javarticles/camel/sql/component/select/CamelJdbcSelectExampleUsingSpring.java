package com.javarticles.camel.sql.component.select;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelJdbcSelectExampleUsingSpring {
	@SuppressWarnings("unchecked")
	public static final void main(String[] args) throws Exception {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("camelContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext) ;
        try {            
            camelContext.start();
            ProducerTemplate template = appContext.getBean(ProducerTemplate.class);
            template.start();
            List<Article> articles = (List<Article>) template.requestBody("direct:sqlParam", "camel");
            for (Article article : articles) {
            	System.out.println("\n==========");
            	System.out.println("::: CamelJdbcSelectExampleUsingSpring Class > main method :::");
            	System.out.println("----------");
                System.out.println(article);
                System.out.println("==========\n");
            }
            Thread.sleep(2000);
            template.stop();
        } finally {
            camelContext.stop();
        }
    }
}
