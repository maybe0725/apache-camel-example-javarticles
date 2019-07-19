package com.javarticles.camel.sql.component.select;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;
import org.apache.camel.util.jndi.JndiContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelSqlSelectDataSourceExample {
	
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final DataSource dataSource = (DataSource) context.getBean("dataSource");
        JndiContext jndiContext = new JndiContext();
        jndiContext.bind("ds", dataSource);
        CamelContext camelContext = new DefaultCamelContext(jndiContext);
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {  
                    from("direct:sqlParam")
                    .to("sql:select * from articles where category=# and author=#?dataSource=ds")
                    .log("==========")
                    .log("::: CamelSqlSelectDataSourceExample Class > configure method :::")
                    .log("----------")
                    .log("${body}")
                    .log("==========");
                }
            });
            camelContext.start();
            ProducerTemplate template = new DefaultProducerTemplate(camelContext);
            template.start();
            List<String> params = new ArrayList<String>();
            params.add("camel");
            params.add("Joe");
            template.sendBody("direct:sqlParam", params);
        } finally {
            camelContext.stop();
            context.close();
        }
    }

}
