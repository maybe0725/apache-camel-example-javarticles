package com.javarticles.camel.multicast;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

public class CamelMulticastExample {
	
    public static final void main(String[] args) throws Exception {
    	
        JndiContext jndiContext = new JndiContext();
        jndiContext.bind("myBean", new MyBean());
        
        CamelContext camelContext = new DefaultCamelContext(jndiContext);
        
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .multicast()
                    .to("direct:a", "direct:b", "direct:c");
                    
                    from("direct:a")
                    .to("bean:myBean?method=addFirst")
                    .setBody(simple("body: ${body}, thread: ${threadName}"))
                    .to("stream:out");
                    
                    from("direct:b")
                    .to("bean:myBean?method=addSecond")
                    .setBody(simple("body: ${body}, thread: ${threadName}"))
                    .to("stream:out");
                    
                    from("direct:c")
                    .to("bean:myBean?method=addThird")
                    .setBody(simple("body: ${body}, thread: ${threadName}"))
                    .to("stream:out");
                }
            });
            camelContext.start();
            
            ProducerTemplate template = camelContext.createProducerTemplate();
            template.sendBody("direct:start", "Multicast");
        } finally {
            camelContext.stop();
        }
    }
}
