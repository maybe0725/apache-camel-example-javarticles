package com.javarticles.camel.transform;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

public class ConstantTransformExample {
    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .log("Article name is ${body}")
                    .choice()
                        .when().simple("${body} contains 'Camel'")
                            .transform(constant("Yes"))
                            .to("stream:out")
                        .otherwise()
                            .transform(constant("No"))
                            .to("stream:out")
                    .end();
                }
            });
            camelContext.start();
            ProducerTemplate template = new DefaultProducerTemplate(camelContext);
            template.start();
            template.sendBody("direct:start", "Camel Components");
            template.sendBody("direct:start", "Spring Integration");
        } finally {
            camelContext.stop();
        }
    }
}
