package com.javarticles.camel.split.xml;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelSplitXmlNamespaceExample {
    public static final void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:article")
                    .log("Split by article Element")
                    .split(xpath("/c:articles/c:article")
                    .namespace("c", "http://javarticles.com/schema/articles"))
                    .log("${body}")                    
                    .end();                    
                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            String filename = "target/classes/articlesNs.xml";
            InputStream articleStream = new FileInputStream(filename);
            template.sendBody("direct:article", articleStream);
        } finally {
            camelContext.stop();
        }
    }
}
