package com.javarticles.camel.multicast;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

public class CamelMulticastPipelineExample {
	
    public static final void main(String[] args) throws Exception {
    	
        JndiContext jndiContext = new JndiContext();
        jndiContext.bind("myBean", new MyBean());
        jndiContext.bind("stringUtils", new StringUtils());
        
        CamelContext camelContext = new DefaultCamelContext(jndiContext);
        
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {
                    from("direct:start")
                    .multicast()
                    .pipeline()                    
                    .to("bean:myBean?method=addFirst")
                    .to("bean:stringUtils?method=upperCase")
                    .to("stream:out")
                    .end()
                    
                    .pipeline()
                    .to("bean:myBean?method=addSecond")
                    .to("bean:stringUtils?method=upperCase")
                    .to("stream:out")
                    .end()

                    .pipeline()
                    .to("bean:myBean?method=addThird")
                    .to("bean:stringUtils?method=upperCase")
                    .to("stream:out")
                    .end()
                    .end()
                    .setBody(simple("Final Output: ${body}"))
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
