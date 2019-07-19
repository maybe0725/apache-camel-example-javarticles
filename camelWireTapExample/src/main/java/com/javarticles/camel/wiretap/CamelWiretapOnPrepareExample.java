package com.javarticles.camel.wiretap;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

public class CamelWiretapOnPrepareExample {
	
    public static final void main(String[] args) throws Exception {
    	
        JndiContext jndiContext = new JndiContext();
        jndiContext.bind("myBean", new MyBean());
        
        CamelContext camelContext = new DefaultCamelContext(jndiContext);
        
        try {
        	
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .log("Send '${body}' to tap router")
                    .wireTap("direct:tap")
                    .onPrepare(new MyPayloadClonePrepare())
                    .end()
                    .delay(1000)
                    .log("Output of main '${body}'");

                    from("direct:tap")
                    .log("Tap router received '${body}'")
                    .bean(MyBean.class, "addThree")
                    .log("Output of tap '${body}'");
                }
            });
            camelContext.start();
            
            ProducerTemplate template = camelContext.createProducerTemplate();
            MyPayload payload = new MyPayload("One");
            template.sendBody("direct:start", payload)
            ;
            System.out.println("Final payload: " + payload.getValue());
        } finally {
            camelContext.stop();
        }
    }
}
