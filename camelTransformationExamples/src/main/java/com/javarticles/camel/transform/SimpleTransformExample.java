package com.javarticles.camel.transform;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class SimpleTransformExample {
    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .transform(simple("<out>${body}</out>"))
                    .to("stream:out");
                }
            });
            camelContext.start();
            ProducerTemplate template = new DefaultProducerTemplate(camelContext);
            template.start();
            template.sendBody("direct:start", "Hello");
        } finally {
            camelContext.stop();
        }
    }
}
