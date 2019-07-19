package com.javarticles.camel.sql.component.select;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelSqlSelectExample {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final DataSource dataSource = (DataSource) context.getBean("dataSource");
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.getComponent("sql", SqlComponent.class).setDataSource(dataSource);
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {                    
                    from("direct:sqlParam")
                    .to("sql:select * from articles where category = #")
                    .process(new Processor() {
                    	public void process(Exchange exchange) throws Exception {
                    		System.out.println("\n==========");
                    		System.out.println("::: CamelSqlSelectExample Class > process method :::");
                    		System.out.println("----------");
                    		System.out.println(exchange.getIn().getBody().getClass());
                    		System.out.println("----------");
                    		System.out.println(exchange.getIn().getBody());
                    		System.out.println("==========\n");
                    	}
                    });
                }
            });
            camelContext.start();
            ProducerTemplate template = new DefaultProducerTemplate(camelContext);
            template.start();
            template.sendBody("direct:sqlParam", "camel");
        } finally {
            camelContext.stop();
            context.close();
        }
    }
}
