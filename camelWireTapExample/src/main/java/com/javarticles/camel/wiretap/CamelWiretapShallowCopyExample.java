package com.javarticles.camel.wiretap;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

public class CamelWiretapShallowCopyExample {
    public static final void main(String[] args) throws Exception {
        JndiContext jndiContext = new JndiContext();
        jndiContext.bind("myBean", new MyBean());
        CamelContext camelContext = new DefaultCamelContext(jndiContext);
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .log("Main route: Send '${body}' to tap router")
                    .wireTap("direct:tap")
                    .log("Main route: Add 'two' to '${body}'")
                    .bean(MyBean.class, "addTwo")
                    .log("Main route: Output '${body}'");

                    from("direct:tap")
                    .log("Tap Wire route: received '${body}'")
                    .log("Tap Wire route: Add 'three' to '${body}'")
                    .bean(MyBean.class, "addThree")
                    .log("Tap Wire route: Output '${body}'");
                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            MyPayload payload = new MyPayload("One");
            template.sendBody("direct:start", payload);
            System.out.println("Final payload: " + payload.getValue());
        } finally {
            camelContext.stop();
        }
    }
}
