package com.javarticles.camel.multicast;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

public class CamelMulticastExceptionExample {
	
    public static final void main(String[] args) throws Exception {
    	
        JndiContext jndiContext = new JndiContext();
        jndiContext.bind("myBean", new MyBean());
        
        CamelContext camelContext = new DefaultCamelContext(jndiContext);
        
        final NumberPayloadValidator numberPayloadValidator = new NumberPayloadValidator();
        
        try {
        	
            camelContext.addRoutes(new RouteBuilder() {
            	
                public void configure() {
                    onException(Exception.class)
                    .handled(true)
                    .to("log:onException")                     
                    .transform(constant("Exception thrown. Stop route"))
                    .to("stream:out");
                    
                    from("direct:start")
                    .multicast()
                    .to("direct:a", "direct:b")
                    .end()
                    .transform(simple("Final Output after multicast ${body}"))
                    .to("stream:out");
                    
                    from("direct:a")
                    .process(numberPayloadValidator)
                    .transform(simple("Received ${body} from direct:a"))
                    .to("stream:out");
                    
                    from("direct:b")
                    .transform(simple("Received ${body} from direct:b"))
                    .to("stream:out");
                }
            });
            camelContext.start();
            
            ProducerTemplate template = camelContext.createProducerTemplate();
            template.sendBody("direct:start", "1");
            template.sendBody("direct:start", "one");
        } finally {
            camelContext.stop();
        }
    }
}
