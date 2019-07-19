package com.javarticles.camel.split.xml;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelSplitXmlUsingXPathExample {
    public static final void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:articles")
                    .split(xpath("/articles"))
                    .log("${body}")
                    .multicast()    
                    .to("direct:article") 
                    .to("direct:category") 
                    .to("direct:tags") 
                    .to("direct:camelTitles")                                          
                    .end();
                    
                    from("direct:article")
                    .log("Split by article Element")
                    .split(xpath("/articles/article"))
                    .log("${body}")                    
                    .end();
                    
                    from("direct:category")
                    .log("Get categories for each article")
                    .split(xpath("/articles/article"))
                    .setBody(xpath("/article/@category").stringResult())
                    .log("${body}")
                    .end();                    
                    
                    from("direct:tags")
                    .log("Get tags for each article")
                    .split(xpath("/articles/article/tags/text()"))
                    .log("${body}");      
                    
                    from("direct:camelTitles")
                    .log("Get camel article titles")
                    .split(xpath("/articles/article[@category='camel']"))
                    .setBody(xpath("/article/@title"))
                    .log("${body}");
                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            String filename = "target/classes/articles.xml";
            InputStream articleStream = new FileInputStream(filename);
            template.sendBody("direct:articles", articleStream);
        } finally {
            camelContext.stop();
        }
    }
}
