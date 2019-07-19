package com.javarticles.camel.jdbc.component.select;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class RowProcessor implements Processor {

    @SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws Exception {
        Map<String, Object> row = exchange.getIn().getBody(Map.class);
        System.out.println("\n==========");
        System.out.println("::: RowProcessor Class > process method :::");
        System.out.println("----------");
        System.out.println("Processing " + row);
        System.out.println("==========\n");
        Article article = new Article();        
        article.setAuthor((String) row.get("AUTHOR"));
        article.setCategory((String) row.get("CATEGORY"));
        article.setName((String) row.get("NAME"));
        article.setTags((String) row.get("TAGS"));
        article.setId((Long) row.get("ID"));        
        exchange.getOut().setBody(article);
    }

}
