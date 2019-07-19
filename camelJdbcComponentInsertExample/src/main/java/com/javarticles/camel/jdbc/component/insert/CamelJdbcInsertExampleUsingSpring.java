package com.javarticles.camel.jdbc.component.insert;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class CamelJdbcInsertExampleUsingSpring {
    public static final void main(String[] args) throws Exception {
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext) ;
        try {            
        	camelContext.start();
            Thread.sleep(3000);
            JdbcTemplate jdbcTemplate = (JdbcTemplate) appContext.getBean("jdbcTemplate");
            List<Article> articles =jdbcTemplate.query("select * from articles", new ArticleRowMapper());
            System.out.println(articles);
        } finally {
        	camelContext.stop();
        }
    }
}
