package com.javarticles.camel.loadbalance;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelLoadbalanceRandomExample {
	
    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();        
        try {
            camelContext.addRoutes(new RouteBuilder() {
                public void configure() {                    
                    from("timer:myTimer?period=1s")
                    .loadBalance()
                    .random()
                    .to("direct:a")
                    .to("direct:b")
                    .to("direct:c");
                    
                    from("direct:a")
                    .setBody()
                    .constant("Endpoint Direct a")
                    .to("stream:out");
                    
                    from("direct:b")
                    .setBody()
                    .constant("Endpoint Direct b")
                    .to("stream:out");
                    
                    from("direct:c")
                    .setBody()
                    .constant("Endpoint Direct c")
                    .to("stream:out");
                }
            });
            camelContext.start();
            Thread.sleep(10000);
        } finally {
            camelContext.stop();
        }
    }

}
