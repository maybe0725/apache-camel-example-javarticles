package com.javarticles.camel.filter;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FeedFilterExample {
	
    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("atom:file:src/main/resources/feed.atom?splitEntries=true&consumer.delay=1000")
                    .to("seda:feeds");
                    
                    from("seda:feeds")
                    .filter().method(new CamelArticles(), "filter")
                    .to("seda:filteredArticles");
                    
                    from("seda:filteredArticles")
                    .to("stream:out");
            }});
            camelContext.start();
            Thread.sleep(5000);
        } finally {
            camelContext.stop();
        }
    }

}
