package com.javarticles.camel.sql.component.select;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;
import org.apache.camel.util.jndi.JndiContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelSqlSelectNamedParametersExample {
	
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
                    .to("sql:select * from articles where category=:#cat and author=:#authr?dataSource=ds")
                    .log("==========")
                    .log("::: CamelSqlSelectNamedParametersExample Class > configure method :::")
                    .log("----------")
                    .log("${body}")
                    .log("==========");
                }
            });
            camelContext.start();
            ProducerTemplate template = new DefaultProducerTemplate(camelContext);
            template.start();
            Map<String, String> params = new HashMap<String, String>();
            params.put("cat", "camel");            
            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("authr", "Joe");
            headers.put("cat", "spring");
            template.sendBodyAndHeaders("direct:sqlParam", params, headers);
        } finally {
            camelContext.stop();
            context.close();
        }
    }

}
