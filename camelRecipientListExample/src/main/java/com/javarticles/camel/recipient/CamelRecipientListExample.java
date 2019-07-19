package com.javarticles.camel.recipient;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelRecipientListExample {
    public static final void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .recipientList(header("departments"));
                    
                    from("direct:account")
                    .log("Account department notified '${body}'");
                    
                    from("direct:hr")
                    .log("HR department notified '${body}'");
                    
                    from("direct:manager")
                    .log("Manager notified '${body}'");
                }
            });
            ProducerTemplate template = camelContext.createProducerTemplate();
            camelContext.start();
            template.sendBodyAndHeader("direct:start", "Sam Joined", "departments", "direct:account,direct:hr,direct:manager");
        } finally {
            camelContext.stop();
        }
    }
}
