package com.javarticles.camel.jdbc.component.insert.xpath;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class CamelJdbcInsertXPathExample {
    public static void main(String[] args) throws Exception {
        StringBuffer articlesXmlSb = new StringBuffer();
        articlesXmlSb.append("  <blog>                                                  ");
	    articlesXmlSb.append("      <article>                                           ");
	    articlesXmlSb.append("          <category>spring integration</category>         ");
	    articlesXmlSb.append("          <title>SpringInt Splitter</title>               ");
	    articlesXmlSb.append("          <tags>spring,spring integration</tags>          ");
	    articlesXmlSb.append("      </article>                                          ");
	    articlesXmlSb.append("      <article>                                           ");
	    articlesXmlSb.append("          <category>java</category>                       ");
	    articlesXmlSb.append("          <title>Lambda</title>                           ");
	    articlesXmlSb.append("          <tags>java8,lambda</tags>                       ");
	    articlesXmlSb.append("      </article>                                          ");
	    articlesXmlSb.append("      <article>                                           ");
	    articlesXmlSb.append("          <category>camel</category>                      ");
	    articlesXmlSb.append("          <title>Camel Multicast</title>                  ");
	    articlesXmlSb.append("          <tags>camel,eip</tags></article>                ");
	    articlesXmlSb.append("      <article>                                           ");
	    articlesXmlSb.append("          <category>Hibernate</category>                  ");
	    articlesXmlSb.append("          <title>Hibernate Spring Inetgartion</title>     ");
	    articlesXmlSb.append("          <tags>java persistence,spring,hibernate</tags>  ");
	    articlesXmlSb.append("      </article>                                          ");
	    articlesXmlSb.append("  </blog>                                                 ");
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CamelContext camelContext = new SpringCamelContext(appContext) ;
        try {
            ProducerTemplate template = appContext.getBean(ProducerTemplate.class);
            camelContext.start();
            template.start();
            template.sendBody("direct:xmlsource", articlesXmlSb.toString());
            Thread.sleep(3000);
            JdbcTemplate jdbcTemplate = (JdbcTemplate) appContext.getBean("jdbcTemplate");
            List<Article> articles = jdbcTemplate.query("select * from articles", new ArticleRowMapper());
            System.out.println("Select * from Articles");
            System.out.println(articles);
            template.stop();
        } finally {
        	camelContext.stop();
        }
    }
}
