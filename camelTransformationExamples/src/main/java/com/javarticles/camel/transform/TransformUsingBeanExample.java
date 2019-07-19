package com.javarticles.camel.transform;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;
import org.apache.camel.util.jndi.JndiContext;

public class TransformUsingBeanExample {
    public static void main(String[] args) throws Exception {
        JndiContext jndiContext = new JndiContext();
        jndiContext.bind("uppercase", new UpperCase());
        CamelContext camelContext = new DefaultCamelContext(jndiContext);
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start").log("Transform ${body} to upperCase")
                            .transform(method("uppercase")).to("stream:out");
                }
            });
            camelContext.start();
            ProducerTemplate template = new DefaultProducerTemplate(
                    camelContext);
            template.start();
            template.sendBody("direct:start", "hello world");
        } finally {
            camelContext.stop();
        }
    }
}
