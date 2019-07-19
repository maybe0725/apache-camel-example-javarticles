package com.javarticles.camel.filter;

import org.apache.abdera.model.Entry;
import org.apache.camel.Exchange;

public class CamelArticles {
	
    public boolean filter(Exchange exchange) {
        Entry entry = exchange.getIn().getBody(Entry.class);
        String title = entry.getTitle();
        boolean camelArticles = title.toLowerCase().startsWith("camel");
        if (camelArticles) {
        	System.out.println("**************************************************");
            System.out.println("allow " + title);
            System.out.println("**************************************************");
        }
        return camelArticles;
    }
    
    public boolean filterOnlyCamelComponents(String body) {       
        boolean camelArticles = body.toLowerCase().startsWith("camel component");
        return camelArticles;
    }
}
